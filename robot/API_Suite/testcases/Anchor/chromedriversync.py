from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager


driver = webdriver.Chrome(ChromeDriverManager().install())
driver.maximize_window()
driver.get("http://www.google.com")
# driver.quit()

a = "aef"
