*** Settings ***
Resource           ../../keywords/common.robot
Resource     ../../PageObjects/loginPage.robot

*** Keywords ***
CP_Deferral Ops Maker
    CP_Def_OpsMaker login 
    CP_Def_OpsMaker Basicdetails
    CP_Defer_Operation_Maker
    CP_DeferralMaker_Remarks

CP_Def_OpsMaker login 
    [Documentation]    Login User
    Open triton in browser  
    ${userName}=     Set Variable    ${opsMakerLeadUser}
    ${password}=     Set Variable    ${opsMakerLeadPw}
    login to Triton    ${userName}    ${password}
CP_Def_OpsMaker Basicdetails
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
    Click Button    xpath:(//button[text()='Next'])[1]
CP_Defer_Operation_Maker
    [Documentation]    Deferral operation maker
    Execute Javascript         window.scrollTo(0,-document.body.scrollHeight)
    ${Date}=    Get Current Date
    ${day}=    Get Substring    ${Date}    8    10
    ${month}=    Get Substring    ${Date}    5    7
    Input Text    xpath:(//*[@type='date'])[1]    ${day}0${month}
    Sleep    1
    Choose File        xpath://input[@type='file']    ${CURDIR}//Test_File.xlsx
    Sleep    1
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[1]     timeout=15s
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[1]
    Click Element                          xpath:(//button[contains(text(),'Next')])[1] 
    Sleep    2
CP_DeferralMaker_Remarks
    [Documentation]    Deferral Remarks 
    Click Button    xpath://button[text()='Submit']
    Input Text      xpath://textarea[@class='swal2-textarea']    okay 
    Sleep    1
    Click Button    xpath:(//button[text()='Submit'])[2]
    Sleep    3
    Logout from Triton