from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from webdriver_manager.chrome import ChromeDriverManager
from datetime import datetime, timedelta
import json

import time
import re

options = Options()
options.add_argument('--headless')
options.add_argument('--disable-gpu')


driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)
driver.implicitly_wait(5)  # 암묵적 대기 (5초)
wait = WebDriverWait(driver, 10)

base_url = "https://hsportal.hansung.ac.kr"
programs = []

def get_programs_from_page(page_num):
    driver.get(f"{base_url}/ko/program/all/list/0/{page_num}")
    time.sleep(2)  # 페이지 로딩 대기

    cards = driver.find_elements(By.CSS_SELECTOR, 'div[data-module="eco"][data-role="item"]')

    # 1) 기본 정보만 미리 수집해 리스트에 저장
    basic_infos = []
    for card in cards:
        try:
            title = card.find_element(By.CSS_SELECTOR, "b.title").text.strip()

            points_labels = card.find_elements(By.CSS_SELECTOR, "label.APPROACHING, label.APPROACHING_CLOSING, label.OPEN, label.SCHEDULED, label.APPROACH_CLOSING")
            point = None
            deadline = None  # 날짜 데이
            today = datetime.today()
            for label in points_labels:
                try:
                    b_text = label.find_element(By.TAG_NAME, "b").text
                except:
                    continue

    
                full_text = label.text.strip()
                text_without_b = full_text.replace(b_text, "").strip()
                # 포인트 추출
                nums = re.findall(r'\d+', text_without_b)
                if nums:
                    point = nums[0]
                    break
                

            style = card.find_element(By.CLASS_NAME, "cover").get_attribute("style")
            img_url = style.split('url(')[-1].split(')')[0].replace('"', '')
            img_url = base_url + img_url if img_url.startswith('/') else img_url

            detail_link = card.find_element(By.CSS_SELECTOR, "a").get_attribute("href")

            dates = card.find_elements(By.TAG_NAME, "small")
            recruit_period = ""
            for s in dates:
                if "신청 :" in s.text:
                    # time 태그들만 추출
                    time_tags = s.find_elements(By.TAG_NAME, "time")

                    if len(time_tags) >= 2:
                        start_str = time_tags[0].get_attribute("datetime")
                        end_str = time_tags[1].get_attribute("datetime")
                        start_dt = datetime.fromisoformat(start_str)
                        end_dt = datetime.fromisoformat(end_str)

                        start_fmt = start_dt.strftime("%Y-%m-%d")
                        end_fmt = end_dt.strftime("%Y-%m-%d") 
                        recruit_period = f"{start_fmt} ~ {end_fmt}"
                        deadline = end_dt
                    break

            basic_infos.append({
                "title": title,
                "point": point,
                "image_url": img_url,
                "detail_url": detail_link,
                "recruit_period": recruit_period,
            })
        except Exception as e:
            print(f"기본 정보 오류: {e}")
            continue

    # 2) 상세페이지 팝업 열기 및 상세 정보 수집
    for info in basic_infos:
        detail_link = info['detail_url']
        try:
            # 항상 메인 창으로 전환 후 팝업 열기
            driver.switch_to.window(driver.window_handles[0])
            driver.execute_script("window.open(arguments[0]);", detail_link)
            driver.switch_to.window(driver.window_handles[-1])

            wait.until(EC.presence_of_element_located((By.CSS_SELECTOR, "div.category")))

            try:
                hit_text = driver.find_element(By.CSS_SELECTOR, "label.hit").text.strip()
            except:
                hit_text = ""
            
            try:
                target = driver.find_element(By.CSS_SELECTOR, "li.target > span").text.strip()
            except:
                target = ""

            lis = driver.find_elements(By.CSS_SELECTOR, "ul > li")  # 모든 li 수집

            target_index = -1
            department_index = -1
            for i, li in enumerate(lis):
                try:
                    li_class = li.get_attribute("class")
                    if li_class == "target":
                        target_index = i
                    elif li_class == "department":
                        department_index = i
                except:
                    continue

            # target과 department 사이에 있는 li 하나만 있다고 했으니
            grade_gender = ""
            if 0 <= target_index < department_index:
                try:
                    middle_li = lis[target_index + 1]
                    grade_gender = middle_li.find_element(By.TAG_NAME, "span").text.strip()
                except:
                    pass

            try:
                department = driver.find_element(By.CSS_SELECTOR, "li.department > span").text.strip()
            except:
                department = ""

            try:
                tag_elements = driver.find_elements(By.CSS_SELECTOR, "li.tag a")
                tag_list = [tag.text.strip() for tag in tag_elements if tag.text.strip()]
                tags = ", ".join(tag_list)  # 혹은 리스트 형태로 쓰고 싶으면 join 생략
            except Exception as e:
                print("태그 파싱 오류:", e)
                tags = ""

            competencies = []
            try:
                rects = driver.find_elements(By.CSS_SELECTOR, "g.highcharts-series rect")
                for rect in rects:
                    height = rect.get_attribute("height")
                    competencies.append(height)
            except:
                competencies = []

            # 팝업 닫고 메인 창으로 복귀
            driver.close()
            driver.switch_to.window(driver.window_handles[0])

            info.update({
                "target": target,
                "grade_gender": grade_gender,
                "department": department,
                "tags": tags,
                #"format": format_text,
                "competencies": competencies,
                "description": hit_text 

            })

            programs.append(info)

        except Exception as e:
            print(f"상세 팝업 처리 오류: {e}")
            try:
                driver.close()
                driver.switch_to.window(driver.window_handles[0])
            except:
                pass
            continue
        
print("Start scraping page 1...")
page = 1
while True:
    prev_len = len(programs)
    get_programs_from_page(page)
    if len(programs) == prev_len:
        break
    print(f"{page}페이지 완료, 누적: {len(programs)}개")
    page += 1

print("Finished scraping.")
driver.quit()

with open("../main/resources/programs.json", "w", encoding="utf-8") as f:
    json.dump(programs, f, ensure_ascii=False, indent=2)

# 출력
for p in programs:
    print(p)
