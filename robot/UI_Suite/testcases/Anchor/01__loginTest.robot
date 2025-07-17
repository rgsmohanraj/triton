*** Settings ***    
Resource           ../../PageObjects/loginPage.robot

*** Test Cases ***
Test login to triton
    [Documentation]    Login test in triton
    Set Selenium Speed    1s
    loginPage.Open browser and login to triton