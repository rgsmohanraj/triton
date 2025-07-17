*** Settings ***
Resource        ../../keywords/common.robot
Resource        ../../PageObjects/loginPage.robot

*** Keywords ***
CP_Deferral ops checker
    CP_Def_Ops_Checker login 
    CP_Def_Ops_Checker Basicdetails
    CP_Deferral Document Details
    CP_Checker Deferral Remarks 

CP_Def_Ops_Checker login 
    [Documentation]    Login User
    Open triton in browser  
    ${userName}=     Set Variable    ${opsChkLeadUser}
    ${password}=     Set Variable    ${opsChkLeadPw}
    login to Triton    ${userName}    ${password}
CP_Def_Ops_Checker Basicdetails
    [Documentation]     Basic Details
    Wait Until Element Is Visible   xpath://*[text()='Document']                  timeout=15s
    ${scrollInbox}=    Run Keyword And Return Status     Click Element                    xpath://*[text()='Document']
    Sleep    2
    Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element     xpath://*[text()='Document']
    Run Keyword If    '${scrollInbox}' == 'False'        Click Element      xpath://*[text()='Document']
    Sleep    2
    Click Element    xpath://a//span[text()='Deferral']
    Sleep    1
    Click Element    xpath://div[@id='defCPCount']
    Wait Until Page Contains Element        xpath://td[contains(text(),'${caseName}')]    timeout=20s
    Execute Javascript    window.scrollTo(0,5000)
    Set Focus To Element    xpath://td[contains(text(),'${caseName}')] 
    Click Element         xpath://td[contains(text(),'${caseName}')]
    Sleep    3
    Execute Javascript         window.scrollTo(0,document.body.scrollHeight)
    Sleep   1
    Click Button    xpath:(//button[text()='Next'])[2]
CP_Deferral Document Details
    [Documentation]    Deferral Document Details
    Sleep    1
    Execute Javascript         window.scrollTo(0,-document.body.scrollHeight)
    Set Focus To Element    xpath://i[@class='fa fa-download']
    Wait Until Page Contains Element    xpath://i[@class='fa fa-download']   timeout=30s
    Click Element    xpath://i[@class='fa fa-download']
    Execute Javascript         window.scrollTo(0,document.body.scrollHeight)
    Sleep    2
    Click Button    xpath:(//button[text()='Next'])[2]
CP_Checker Deferral Remarks 
     [Documentation]    Deferral Remarks
    Execute Javascript         window.scrollTo(0,document.body.scrollHeight)
    Click Button    xpath://button[text()='Submit']
    Sleep    1
    Input Text      xpath://textarea[@class='swal2-textarea']    okay
    Click Button    xpath:(//button[text()='Submit'])[2]
    Sleep    3    
    Logout from Triton
