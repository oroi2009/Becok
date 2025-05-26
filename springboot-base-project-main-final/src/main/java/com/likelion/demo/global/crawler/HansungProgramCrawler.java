package com.likelion.demo.global.crawler;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.io.FileWriter;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.regex.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.stream.Collectors;

@Component
public class HansungProgramCrawler {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "chromedriver"); // chromedriver 경로 설정


        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu");

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String baseUrl = "https://hsportal.hansung.ac.kr";
        List<JSONObject> programs = new ArrayList<>();

        int page = 1;

        while (true) {
            int beforeSize = programs.size();
            getProgramsFromPage(driver, wait, baseUrl, page, programs);
            if (programs.size() == beforeSize) break;
            System.out.println(page + "페이지 완료, 누적: " + programs.size() + "개");
            page++;
        }

        driver.quit();

        try (FileWriter file = new FileWriter("src/main/resources/programs.json")) {
            JSONArray array = new JSONArray();
            array.addAll(programs);
            file.write(array.toJSONString());
            System.out.println("JSON 저장 완료.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getProgramsFromPage(WebDriver driver, WebDriverWait wait, String baseUrl, int pageNum, List<JSONObject> programs) {
        driver.get(baseUrl + "/ko/program/all/list/0/" + pageNum);

        List<WebElement> cards = driver.findElements(By.cssSelector("div[data-module='eco'][data-role='item']"));
        List<JSONObject> basicInfos = new ArrayList<>();

        for (WebElement card : cards) {
            try {

                String title = card.findElement(By.cssSelector("b.title")).getText().trim();

                List<WebElement> labels = card.findElements(By.cssSelector("label.APPROACHING, label.APPROACHING_CLOSING, label.OPEN, label.SCHEDULED, label.APPROACH_CLOSING"));
                String point = null;
                for (WebElement label : labels) {
                    try {
                        String bText = label.findElement(By.tagName("b")).getText();
                        String fullText = label.getText().trim();
                        String remaining = fullText.replace(bText, "").trim();
                        Matcher matcher = Pattern.compile("\\d+").matcher(remaining);
                        if (matcher.find()) {
                            point = matcher.group();
                            break;
                        }
                    } catch (Exception ignored) {}
                }

                String style = card.findElement(By.className("cover")).getAttribute("style");
                String imageUrl = style.split("url\\(")[1].split("\\)")[0].replace("\"", "");
                imageUrl = imageUrl.startsWith("/") ? baseUrl + imageUrl : imageUrl;

                String detailUrl = card.findElement(By.cssSelector("a")).getAttribute("href");

                String recruitPeriod = "";
                OffsetDateTime deadline = null;
                List<WebElement> smallTags = card.findElements(By.tagName("small"));

                for (WebElement s : smallTags) {
                    if (s.getText().contains("신청 :")) {
                        List<WebElement> times = s.findElements(By.tagName("time"));
                        if (times.size() >= 2) {
                            String startStr = times.get(0).getAttribute("datetime");
                            String endStr = times.get(1).getAttribute("datetime");

                            OffsetDateTime start = OffsetDateTime.parse(startStr);
                            OffsetDateTime end = OffsetDateTime.parse(endStr);

                            recruitPeriod = start.toLocalDate() + " ~ " + end.toLocalDate();
                            deadline = end;
                        }
                        break;
                    }
                }

                JSONObject info = new JSONObject();
                info.put("title", title);
                info.put("point", point);
                info.put("image_url", imageUrl);
                info.put("detail_url", detailUrl);
                info.put("recruit_period", recruitPeriod);

                basicInfos.add(info);

            } catch (Exception e) {
                System.out.println("기본 정보 오류: " + e.getMessage());
            }
        }

        // 상세 페이지 열고 정보 추가
        for (JSONObject info : basicInfos) {
            String detailUrl = (String) info.get("detail_url");
            try {
                // 항상 메인 탭으로 이동 후 팝업 열기
                ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
                driver.switchTo().window(tabs.get(0));
                ((JavascriptExecutor) driver).executeScript("window.open(arguments[0])", detailUrl);

                tabs = new ArrayList<>(driver.getWindowHandles());
                driver.switchTo().window(tabs.get(tabs.size() - 1));

                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.category")));

                String description = "";
                try {
                    description = driver.findElement(By.cssSelector("label.hit")).getText().trim();
                } catch (Exception ignored) {}

                String target = "";
                try {
                    target = driver.findElement(By.cssSelector("li.target > span")).getText().trim();
                } catch (Exception ignored) {}

                String gradeGender = "";
                String department = "";
                try {
                    List<WebElement> lis = driver.findElements(By.cssSelector("ul > li"));
                    int targetIndex = -1;
                    int deptIndex = -1;
                    for (int i = 0; i < lis.size(); i++) {
                        String cls = lis.get(i).getAttribute("class");
                        if ("target".equals(cls)) targetIndex = i;
                        else if ("department".equals(cls)) deptIndex = i;
                    }
                    if (targetIndex >= 0 && targetIndex + 1 < deptIndex) {
                        gradeGender = lis.get(targetIndex + 1).findElement(By.tagName("span")).getText().trim();
                    }
                    department = driver.findElement(By.cssSelector("li.department > span")).getText().trim();
                } catch (Exception ignored) {}

                String tags = "";
                try {
                    List<WebElement> tagEls = driver.findElements(By.cssSelector("li.tag a"));
                    List<String> tagList = new ArrayList<>();
                    for (WebElement tagEl : tagEls) {
                        tagList.add(tagEl.getText().trim());
                    }
                    tags = tagList.stream()
                            .map(tag -> "#" + tag)
                            .collect(Collectors.joining(", "));
                } catch (Exception e) {
                    System.out.println("태그 파싱 오류: " + e.getMessage());
                }

                List<String> competencies = new ArrayList<>();
                try {
                    List<WebElement> rects = driver.findElements(By.cssSelector("g.highcharts-series rect"));
                    for (WebElement rect : rects) {
                        String height = rect.getAttribute("height").replaceAll("[^\\d.]", ""); // 숫자만 추출
                        int rounded = (int) Math.round(Double.parseDouble(height));
                        competencies.add(String.valueOf(rounded));
                    }
                } catch (Exception ignored) {}

                driver.close();
                driver.switchTo().window(tabs.get(0));

                info.put("target", target);
                info.put("grade_gender", gradeGender);
                info.put("department", department);
                info.put("tags", tags);
                info.put("competencies", competencies);
                info.put("description", description);

                programs.add(info);

            } catch (Exception e) {
                System.out.println("상세 팝업 오류: " + e.getMessage());
                try {
                    driver.close();
                    ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
                    driver.switchTo().window(tabs.get(0));
                } catch (Exception ignored) {}
            }
        }
    }
}