*** Settings ***
Resource         ../../keywords/common.robot
Resource         ../../PageObjects/assignmentPage.robot

*** Keywords ***
Ops Checker Approval
    [Documentation]       Counter Party Ops_Checker Approval
    ${userName}=         Set Variable    ${opsChkLeadUser}
    ${password}=         Set Variable    ${opsChkLeadPw}
    loginPage.Open triton in browser
    loginPage.login to Triton            ${userName}    ${password} 
#Selecting counterparty
    Wait Until Element Is Visible       xpath://span[text()='Inbox']                      timeout=15s
    Set Focus To Element                xpath://span[text()='Inbox']
    Click Element                       xpath://span[text()='Inbox']
    Sleep    1
    Run Keyword    Select the case in Inbox
    Sleep    2
#CP_details
    Wait Until Page Contains Element      xpath:(//span[contains(text(),'CP Details')])[10]     timeout=10s
     #cp details
    Sleep    2
    ${Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[7]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${Next}'=='False'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[7]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[7]
    Click Element                          xpath:(//button[contains(text(),'Next')])[7]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[6]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[6]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[6]
    Click Element                          xpath:(//button[contains(text(),'Next')])[6]
    END
    Sleep    2
#Document Download
    FOR  ${i}  IN RANGE    1    2
        Wait Until Element Is Visible    xpath:(//button[@type='button'])[${i}]         timeout=10s
        Set Focus To Element             xpath:(//button[@type='button'])[${i}]
        Click Element                    xpath:(//button[@type='button'])[${i}]                
    END   
    Sleep    2
    ${Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[7]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${Next}'=='False'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[7]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[7]
    Click Element                          xpath:(//button[contains(text(),'Next')])[7]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[6]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[6]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[6]
    Click Element                          xpath:(//button[contains(text(),'Next')])[6]
    END
    Sleep    2
#Ops_checker_Approve
    Wait Until Element Is Visible       xpath://*[text()='Submit']                           timeout=20s
    Set Focus To Element                xpath://*[text()='Submit']
    Sleep    2
    Click Element                       xpath://*[text()='Submit']
#Ops_checker_submit
    Input Text                          xpath://*[@class='swal2-textarea']                   Approved
    Wait Until Element Is Visible       xpath:(//button[text()='Submit'])[2]                 timeout=10s  
    Set Focus To Element                xpath:(//button[text()='Submit'])[2]
    Click Element                       xpath:(//button[text()='Submit'])[2]
    Sleep    3
    Logout from Triton
