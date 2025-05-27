package com.likelion.demo.global.crawler;

import com.likelion.demo.domain.contest.entity.Contest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class LinkareerCrawler {

    private static final int MAX_PAGES = 3;    // 최대 페이지 수
    private static final int MAX_CONTESTS = 30; // 최대 크롤링 개수

    public List<Contest> crawl() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);
        List<Contest> contests = new ArrayList<>();

        try {
            for (int page = 1; page <= MAX_PAGES && contests.size() < MAX_CONTESTS; page++) {
                String listUrl = "https://linkareer.com/list/contest"
                        + "?filterType=CATEGORY&orderBy_direction=DESC"
                        + "&orderBy_field=VIEW_COUNT&page=" + page;
                driver.get(listUrl);
                Thread.sleep(3000);

                List<WebElement> cards = driver.findElements(By.cssSelector("div.list-wrapper > div"));
                if (cards.isEmpty()) break;

                List<String> links = new ArrayList<>();
                for (WebElement card : cards) {
                    try {
                        String href = card.findElement(By.cssSelector("a.image-link"))
                                .getAttribute("href");
                        if (!href.startsWith("http")) {
                            href = "https://linkareer.com" + href;
                        }
                        links.add(href);
                    } catch (Exception e) {
                        System.out.println("카드 링크 추출 실패");
                    }
                }

                for (String linkUrl : links) {
                    if (contests.size() >= MAX_CONTESTS) break;

                    try {
                        driver.get(linkUrl);
                        Thread.sleep(2000);

                        // ── 제목, 주최, 썸네일 ──
                        String title = driver.findElement(By.cssSelector("h1"))
                                .getText().trim();
                        String organizer = driver.findElement(
                                        By.cssSelector(".organization-info > h2"))
                                .getText().trim();

                        String thumbnailUrl = "";
                        List<WebElement> thumbEls = driver.findElements(
                                By.cssSelector("img.card-image"));
                        if (!thumbEls.isEmpty()) {
                            thumbnailUrl = thumbEls.get(0).getAttribute("src");
                        }

                        // ── 접수기간 ──
                        String startDateStr = "";
                        String endDateStr = "";
                        try {
                            WebElement period = driver.findElement(By.xpath(
                                    "//dt[contains(text(), '접수기간')]/following-sibling::dd[1]"));
                            List<WebElement> spans = period.findElements(By.tagName("span"));
                            for (int i = 0; i < spans.size(); i++) {
                                String cls = spans.get(i).getAttribute("class");
                                if ("start-at".equals(cls)) {
                                    startDateStr = spans.get(i + 1).getText().trim();
                                } else if ("end-at".equals(cls)) {
                                    endDateStr = spans.get(i + 1).getText().trim();
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("시작/마감일 추출 실패: " + linkUrl);
                        }

                        LocalDate startDate = startDateStr.isEmpty()
                                ? LocalDate.now()
                                : LocalDate.parse(startDateStr.replace(".", "-"));
                        LocalDate endDate = endDateStr.isEmpty()
                                ? LocalDate.now().plusDays(30)
                                : LocalDate.parse(endDateStr.replace(".", "-"));

                        // ── 조회수 ──
                        int hits = 0;
                        List<WebElement> hitEls = driver.findElements(
                                By.cssSelector("span.count[aria-label='조회수']"));
                        if (!hitEls.isEmpty()) {
                            String hitsStr = hitEls.get(0).getText()
                                    .replaceAll(",", "").trim();
                            hits = Integer.parseInt(hitsStr);
                        }

                        // ── 공모분야 ──
                        String category = "";
                        List<WebElement> catEls = driver.findElements(By.xpath(
                                "//dt[contains(text(),'공모분야')]/following-sibling::dd[1]"));
                        if (!catEls.isEmpty()) {
                            category = catEls.get(0).getText().trim();
                        }

                        // ── D-Day ──
                        String Dday = "";
                        List<WebElement> ddayEls = driver.findElements(
                                By.cssSelector("strong.recruitText"));
                        if (!ddayEls.isEmpty()) {
                            Dday = ddayEls.get(0).getText().trim();
                        }

                        // ── detailUrl 추출 ──
                        String detailUrl = "";
                        List<WebElement> aEls = driver.findElements(By.xpath(
                                "//dt[contains(text(),'홈페이지')]/following-sibling::dd[1]//a"));
                        if (!aEls.isEmpty() && aEls.get(0).getAttribute("href").startsWith("http")) {
                            detailUrl = aEls.get(0).getAttribute("href");
                        } else {
                            detailUrl = fetchViaApplyButton(driver, linkUrl);
                        }


                        // ── Contest 객체 생성 ──
                        Contest contest = Contest.builder()
                                .name(title)
                                .startDate(startDate)
                                .endDate(endDate)
                                .organizer(organizer)
                                .category(category)
                                .thumbnailUrl(thumbnailUrl)
                                .detailUrl(detailUrl)
                                .hits(hits)
                                .Dday(Dday)
                                .build();

                        contests.add(contest);

                    } catch (Exception e) {
                        System.out.println("상세페이지 오류: " + linkUrl);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return contests;
    }
    /**
     * '홈페이지 지원' 버튼 클릭으로 열린 새 탭/창 혹은 같은 탭의 URL을 가져오는 유틸.
     */
    private String fetchViaApplyButton(WebDriver driver, String linkUrl) {
        try {
            String original = driver.getWindowHandle();
            Set<String> before = driver.getWindowHandles();

            WebElement btn = driver.findElement(By.cssSelector("button.apply-button"));
            btn.click();
            Thread.sleep(2000);

            Set<String> after = driver.getWindowHandles();
            // 새로 열린 핸들 찾기
            after.removeAll(before);

            String newUrl;
            if (!after.isEmpty()) {
                String win = after.iterator().next();
                driver.switchTo().window(win);
                newUrl = driver.getCurrentUrl();
                driver.close();
                driver.switchTo().window(original);
            } else {
                // 같은 탭에서 바로 이동된 경우
                newUrl = driver.getCurrentUrl();
                driver.navigate().back(); // 원래 페이지로 복귀
            }
            return newUrl;
        } catch (Exception ex) {
            System.out.println("지원 버튼 URL 추출 실패: "
                    + linkUrl + " / " + ex.getMessage());
            return "";
        }
    }
}
