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

@Component
public class LinkareerCrawler {

    private static final int MAX_PAGES = 3; // 최대 3페이지
    private static final int MAX_CONTESTS = 30; // 최대 50개까지

    public List<Contest> crawl() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);
        List<Contest> contests = new ArrayList<>();

        try {
            for (int page = 1; page <= 2; page++) {
                String listUrl = "https://linkareer.com/list/contest?filterType=CATEGORY&orderBy_direction=DESC&orderBy_field=VIEW_COUNT&page=" + page;
                driver.get(listUrl);
                Thread.sleep(3000);

                List<WebElement> cards = driver.findElements(By.cssSelector("div.list-wrapper > div"));
                if (cards.isEmpty()) break;

                List<String> links = new ArrayList<>();
                for (WebElement card : cards) {
                    try {
                        String href = card.findElement(By.cssSelector("a.image-link")).getAttribute("href");
                        String linkUrl = href.startsWith("http") ? href : "https://linkareer.com" + href;
                        links.add(linkUrl);
                    } catch (Exception e) {
                        System.out.println("카드 링크 추출 실패");
                    }
                }

                for (String linkUrl : links) {
                    try {
                        driver.get(linkUrl);
                        Thread.sleep(2000);

                        String title = driver.findElement(By.cssSelector("h1")).getText().trim();
                        String organizer = driver.findElement(By.cssSelector(".organization-info > h2")).getText().trim();

                        String thumbnailUrl = "";
                        List<WebElement> thumbnailEls = driver.findElements(By.cssSelector("img.card-image"));
                        if (!thumbnailEls.isEmpty()) {
                            thumbnailUrl = thumbnailEls.get(0).getAttribute("src");
                        }

                        String startDateStr = "";
                        String endDateStr = "";
                        try {
                            WebElement recruitPeriod = driver.findElement(By.xpath("//dt[contains(text(), '접수기간')]/following-sibling::dd[1]"));
                            List<WebElement> dateSpans = recruitPeriod.findElements(By.tagName("span"));
                            for (int j = 0; j < dateSpans.size(); j++) {
                                String label = dateSpans.get(j).getAttribute("class");
                                if ("start-at".equals(label)) {
                                    startDateStr = dateSpans.get(j + 1).getText().trim();
                                } else if ("end-at".equals(label)) {
                                    endDateStr = dateSpans.get(j + 1).getText().trim();
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

                        String hitsStr = "0";
                        List<WebElement> hitEls = driver.findElements(By.cssSelector("span.count[aria-label='조회수']"));
                        if (!hitEls.isEmpty()) {
                            hitsStr = hitEls.get(0).getText().replaceAll(",", "").trim();
                        }
                        int hits = Integer.parseInt(hitsStr);

                        String category = "";
                        List<WebElement> categoryEls = driver.findElements(By.xpath("//dt[contains(text(),'공모분야')]/following-sibling::dd[1]"));
                        if (!categoryEls.isEmpty()) {
                            category = categoryEls.get(0).getText().trim();
                        }

                        String Dday = "";
                        List<WebElement> ddayEls = driver.findElements(By.cssSelector("strong.recruitText"));
                        if (!ddayEls.isEmpty()) {
                            Dday = ddayEls.get(0).getText().trim();
                        }

                        Contest contest = Contest.builder()
                                .name(title)
                                .startDate(startDate)
                                .endDate(endDate)
                                .organizer(organizer)
                                .category(category)
                                .thumbnailUrl(thumbnailUrl)
                                .linkUrl(linkUrl)
                                .status("모집 중")
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
}
