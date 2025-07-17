*** Settings ***  
Resource          ../../keywords/common.robot    
Resource          ../../PageObjects/assignmentPage.robot
Resource          ../../PageObjects/AnchorPageObjects/02__creditNormsPage.robot
*** Keywords ***
From Credit Underwriter lead assign
    Set Selenium Speed    0.4s
    ${userName}=     Set Variable    ${creditUWLeadUser}
    ${password}=     Set Variable    ${creditUWLeadPw}
    ${userSelect}=   Set Variable    CREDIT UNDERWRITER
    assignmentPage.Search the case   ${userName}    ${password}    ${userSelect}   ${caseName}
    ${userName}=     Set Variable    ${creditUW}
    ${password}=     Set Variable    ${creditUWPw}
    # loginPage.Open triton in browser
    loginPage.login to Triton        ${userName}    ${password}

Credit subcommitee approval
    [Documentation]    Anchor credit subcommitee approval stage
    # Set Selenium Speed    0.4s
    Sleep    2
    Wait Until Element Is Visible    xpath://span[text()='Inbox']    timeout=10s
    Click Element                    xpath://span[text()='Inbox']
    Sleep    2
    Run Keyword    Select the case in Inbox
    Wait Until Element Is Visible        xpath:(//button[text()='Next'])[2]    timeout=10s
    Sleep    2s
    Execute Javascript   window.scrollTo(0,document.body.scrollHeight)
    Sleep    2s
    Execute Javascript   window.scrollTo(0,1000)
    Set Focus To Element                 xpath:(//button[text()='Next'])[2]
    Click Element                        xpath:(//button[text()='Next'])[2]
    Execute Javascript   window.scrollTo(0,1000)
    Set Focus To Element    xpath:(//button[text()='Next'])[2]
    Click Button    xpath:(//button[text()='Next'])[2]
     Execute Javascript   window.scrollTo(0,1000)
    Set Focus To Element    xpath:(//button[text()='Next'])[2]
    Click Button    xpath:(//button[text()='Next'])[2]
    Execute Javascript   window.scrollTo(0,1000)
    Set Focus To Element    xpath:(//button[text()='Next'])[2]
    Click Button    xpath:(//button[text()='Next'])[2]
    Execute Javascript   window.scrollTo(0,document.body.scrollHeight)
    Sleep    2s
    Execute Javascript   window.scrollTo(0,1000)
    Set Focus To Element                 xpath://button[text()='Approve']
    Click Element                        xpath://button[text()='Approve']
    Wait Until Element Is Visible        //textarea[@class='swal2-textarea']
    Input Text                           //textarea[@class='swal2-textarea']    Approved
    Click Element                        xpath://button[text()='Submit']
    Sleep    3
    Log      Credit subcommitee approved
    sleep    18
    Logout from Triton