*** Settings ***
Resource        ../../keywords/common.robot
Resource        ../../PageObjects/loginPage.robot
Resource        ../../PageObjects/AnchorPageObjects/07_Deferral_OpsMakerPage.robot

*** Keywords ***
Deferral ops checker
    Def_Ops_Checker login 
    Basicdetails 
    Deferral Document Details
    Defl_Remarks 
  # For Return Flow

Def_Ops_Checker login 
    [Documentation]    Login User
    Open triton in browser 
    ${userName}=     Set Variable    ${opsChkLeadUser}
    ${password}=     Set Variable    ${opsChkLeadPw}
    login to Triton    ${userName}    ${password}

Basicdetails
    [Documentation]    Basic Details
    Wait Until Element Is Visible   xpath://*[text()='Document']                  timeout=15s
    ${scrollInbox}=    Run Keyword And Return Status     Click Element                    xpath://*[text()='Document']
    Sleep    2
    Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element             xpath://*[text()='Document']
    Run Keyword If    '${scrollInbox}' == 'False'        Click Element                    xpath://*[text()='Document']
    Sleep    2
    Click Element    xpath://a//span[text()='Deferral']
    Sleep   2
    Click Element        xpath://div[@id='defAnchorCount']
    Run Keyword    Select the case in Inbox
    Sleep  2
    Execute Javascript         window.scrollTo(0,document.body.scrollHeight)
    Sleep  2
    Click Button    xpath:(//button[text()='Next'])[2]
Deferral Document Details
    [Documentation]    Deferral Document Details
    Sleep    2
    Execute Javascript         window.scrollTo(0,-document.body.scrollHeight)
    Sleep    2
    Execute JavaScript    var xpath = "//i[@class='fa fa-download']"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    Set Focus To Element    xpath://i[@class='fa fa-download']
    Sleep    2
    Wait Until Page Contains Element    xpath://i[@class='fa fa-download']   timeout=30s
    Sleep    2
    Click Element    xpath://i[@class='fa fa-download']
    Sleep    2
    Execute JavaScript    var xpath = "(//button[text()='Next'])[2]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    Click Button    xpath:(//button[text()='Next'])[2]
    Sleep    2
Defl_Remarks 
    [Documentation]    Deferral Remarks
    Click Button    xpath://button[text()='Submit']
    Sleep    2
    Input Text      xpath://textarea[@class='swal2-textarea']    okay
    Sleep    2
    Click Button    xpath:(//button[text()='Submit'])[2]
    Sleep    2
    # Logout from Triton
For Return Flow
    [Documentation]    Return Flow
    Def_Ops_Checker login 
    Basicdetails
    Click Button    xpath://button[text()='Return']
    Input Text      xpath://textarea[@class='swal2-textarea']    Plz verify once
    Sleep    1
    Click Button    xpath://button[text()='Submit']
    07_Deferral_OpsMakerPage.Deferral ops maker
    

    


