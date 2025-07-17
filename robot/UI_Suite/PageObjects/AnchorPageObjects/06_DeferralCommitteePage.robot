*** Settings ***
Resource           ../../keywords/common.robot
Resource     ../../PageObjects/loginPage.robot

*** Keywords ***
Deferral_Committee_Lead
    deferral_login
    Deferral_Basic_Details
    Defl_Approved ETA
    Defr_Remarks
deferral_login
     [Documentation]    Login User
    Open triton in browser  
    ${userName}=     Set Variable    ${deferralCommitLeadUser}
    ${password}=     Set Variable    ${deferralCommitLeadPw}
    login to Triton    ${userName}    ${password}
Deferral_Basic_Details
    [Documentation]    Basic Details
    Wait Until Element Is Visible        xpath://span[text()='Inbox']    timeout=10s
    Click Element                        xpath://span[text()='Inbox']
    Sleep    2
    Run Keyword    Select the case in Inbox
    Sleep    1
    Execute Javascript         window.scrollTo(0,document.body.scrollHeight)
    Sleep    2
    Click Button    xpath:(//button[text()='Next'])[5]
Defl_Approved ETA
    [Documentation]    Deferral Approved ETA
    Sleep    2
    Execute Javascript         window.scrollTo(200,200)
    Sleep    1
    Click Button    xpath:(//button[text()='Approve'])[1]
    Sleep    2
    Click Button    xpath:(//button[text()='Approve'])[2]
    Sleep    2
    Execute JavaScript    var xpath = "(//button[text()='Waive Off'])[2]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    Click Button    xpath:(//button[text()='Waive Off'])[2]
    Sleep    2
    Execute JavaScript    var xpath = "(//button[text()='Next'])[5]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    Click Button    xpath:(//button[text()='Next'])[5]
    Sleep    2
Defr_Remarks
    [Documentation]    Deferral Remarks 
    Execute JavaScript    var xpath = "//button[text()='Submit']"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    Click Button    xpath://button[text()='Submit']
    Input Text    xpath://textarea[@class='swal2-textarea']    okay
    Sleep    2
    Click Button    xpath:(//button[text()='Submit'])[2]
    sleep    10
    Logout from Triton
