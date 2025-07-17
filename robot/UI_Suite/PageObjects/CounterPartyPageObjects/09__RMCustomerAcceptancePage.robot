*** Settings ***
Resource         ../../keywords/common.robot
Resource         ../../PageObjects/assignmentPage.robot

*** Keywords ***
From Business_lead assign to Business
    # Set Selenium Speed     0.2s
    [Documentation]     Counter party ninth stage CC_remarks
    ${userName}=     Set Variable    ${businessLeadUser}
    ${password}=     Set Variable    ${businessLeadPw}
    ${userSelect}=   Set Variable    Business Business
    assignmentPage.Search the case   ${userName}    ${password}    ${userSelect}    ${caseName}
    ${userName}=     Set Variable    ${businessUser}
    ${password}=     Set Variable    ${businessPw}
    # loginPage.Open triton in browser
    loginPage.login to Triton    ${userName}    ${password} 
   
CommercialCC_Remarks
    [Documentation]        Commercial CC_remarks and submit
    Sleep    2
    Click Element                       xpath://span[text()='Inbox']
    Sleep    2
    Run Keyword    Select the case in Inbox
    #CP Details
    Wait Until Page Contains Element      xpath:(//span[contains(text(),'CP Details')])[9]     timeout=10s
     #cp details
    Sleep    2
    ${Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[5]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${Next}'=='True'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[5]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[5]
    Click Element                          xpath:(//button[contains(text(),'Next')])[5]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[6]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[6]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[6]
    Click Element                          xpath:(//button[contains(text(),'Next')])[6]
    END
    Sleep    2
    Wait Until Page Contains Element      xpath:(//span[contains(text(),'Commercial CC')])     timeout=10s
    Sleep    2
    ${Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[5]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${Next}'=='True'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[5]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[5]
    Click Element                          xpath:(//button[contains(text(),'Next')])[5]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[6]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[6]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[6]
    Click Element                          xpath:(//button[contains(text(),'Next')])[6]
    END
    Sleep    2
    
    Wait Until Element Is Visible       xpath://button[text()='Submit']          timeout=10s
    Set Focus To Element                xpath://button[text()='Submit']
    Sleep    2
    Click Element                       xpath://button[text()='Submit']
    Input Text                          xpath://textarea[@class='swal2-textarea']     Approved
    Wait Until Element Is Visible       xpath:(//button[text()='Submit'])[2]          timeout=10s
    Set Focus To Element                xpath:(//button[text()='Submit'])[2]
    Click Element                       xpath:(//button[text()='Submit'])[2]
    Sleep   3
    Logout from Triton
    
    