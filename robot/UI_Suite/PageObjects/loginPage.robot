*** Settings ***  
Resource           ../keywords/common.robot  

*** Variables ***
${Browser}=    chrome


*** Keywords ***
Open triton in browser
    Open Browser     ${URL}     ${Browser}     options=add_experimental_option("detach", True)
    Maximize Browser Window
    # Open Browser     ${URL}    headlesschrome      add_argument("--headless")
    # Maximize Browser Window
    # Set Window Size    1920    1080
    # Sleep           2

login to Triton
    [Arguments]    ${userName}    ${password}
    Wait Until Element Is Visible          xpath://input[@ng-reflect-name="email"]     timeout=20s
    Set Focus To Element    xpath://input[@ng-reflect-name="email"]
    Sleep    2
    Input Text      xpath://input[@ng-reflect-name="email"]       ${userName}
    Input Text      xpath://input[@formcontrolname='password']    ${password}
    Sleep           2
    Click Element   xpath://span[text()='Login']

Logout from Triton
    Wait Until Element Is Visible    xpath://*[text()='Exit ']    timeout=50s
    Mouse Over      xpath://*[text()='Exit ']
    #  Sleep           30s
    Wait Until Element Is Visible   xpath://span[text()='Log Out']    timeout=40s
    Click Element   xpath://span[text()='Log Out']

Open browser and login to triton
    Open triton in browser    
    login to Triton    ${userName}    ${password}    