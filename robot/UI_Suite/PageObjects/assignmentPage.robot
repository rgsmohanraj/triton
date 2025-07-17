*** Settings ***  
Resource          ../keywords/common.robot    
Resource          ../PageObjects/loginPage.robot
Resource          ../PageObjects/AnchorPageObjects/01__anchorFileUploadPage.robot

*** Keywords ***    
Search the case
    [Arguments]    ${userName}    ${password}    ${userSelect}  ${caseName}
    loginPage.Open triton in browser
    loginPage.login to Triton    ${userName}    ${password}
    Wait Until Element Is Visible    xpath://span[text()='Assignment']    timeout=15s
    Click Element                    xpath://span[text()='Assignment']
    ${selectionStatus}=    Run Keyword And Return Status  Wait Until Element Is Visible   xpath://td[contains(text(),'${caseName}')]   timeout=15s
    Run Keyword If    '${selectionStatus}' == 'True'    Assignment the case     ${userSelect}  ${caseName}
    Run Keyword If    '${selectionStatus}' == 'False'       loginPage.Logout from Triton    
    Run Keyword If    '${selectionStatus}' == 'False'   Log    There is no ${caseName} case                   

Assignment the case

    [Arguments]    ${userSelect}    ${caseName}
    Wait Until Element Is Visible   xpath://td[contains(text(),'${caseName}')] 
    Sleep    3
    ${scrollCase}=     Run Keyword And Return Status     Click Element   xpath://td[contains(text(),'${caseName}')] 
    Run Keyword If     '${scrollCase}' == 'False'    Execute JavaScript    var xpath = "//td[contains(text(),'${caseName}')]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Run Keyword If    '${scrollCase}' == 'False'     Click Element   xpath://td[contains(text(),'${caseName}')] 
    Click Element                    xpath://select[@name='userGrpData']
    Sleep    1
    Click Element                    xpath://option[contains(text(),'${userSelect}')]
    Sleep    1
    Click Element                    xpath://button[text()='Assign']
    Sleep    1
    Log     Case assigned to ${userSelect}
    Logout from Triton