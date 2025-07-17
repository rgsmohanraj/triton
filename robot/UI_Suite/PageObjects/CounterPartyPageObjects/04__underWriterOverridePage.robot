*** Settings ***
Resource    ../../keywords/common.robot
Resource    ../../PageObjects/assignmentPage.robot
Resource     ../CounterPartyPageObjects/01__businessPage.robot
*** Variables ***
${user1}=     credit_underwriter_lead

*** Keywords ***
From Credit_Underwriter_lead to Credit_Underwriter or Credit_Underwriter_lead to Credit_Underwriter
   [Documentation]    Running Credit underwriter lead or risk underwriter Lead according to 2nd stage
    # Open triton in browser
    Log     \n Proposed amount in keyword: ${proposed_amt}
    IF  ${proposed_amt}<=40000000
        ${userName1}=      Set Variable      ${creditUWLeadUser}
        ${password1}=      Set Variable      ${creditUWLeadPw}
    ELSE
         ${userName1}=      Set Variable      ${riskULUser}
         ${password1}=      Set Variable      ${riskULPw}
    END
    IF  '${userName1}'=='${user1}'
        ${userSelect1}=                      Set Variable    CREDIT UNDERWRITER
        ${userName2}=    Set Variable    ${creditUnderwriterUser}
        ${password2}=    Set Variable    ${creditUnderwriterPw}
    ELSE
        ${userSelect1}=       Set Variable    RISK UNDERWRITER
        ${userName2}=    Set Variable    ${riskUnderwriterUser}
        ${password2}=    Set Variable    ${riskUnderwriterPw}    
    END  
    assignmentPage.Search the case      ${userName1}    ${password1}    ${userSelect1}    ${caseName}
    loginPage.login to Triton   ${userName2}     ${password2}

Soft Policy Check
    [Documentation]    Soft policy Approval
    Set Selenium Speed    0.5s
    Wait Until Page Contains Element       xpath://span[contains(text(),'Inbox')]     timeout=20s
    Set Focus To Element                   xpath://span[contains(text(),'Inbox')]
    Click Element                          xpath://span[contains(text(),'Inbox')]
    Sleep                                  2
    Run Keyword    Select the case in Inbox
    #Approval stage
    Sleep                                  2
    Wait Until Page Contains Element      xpath:(//label[contains(text(),'Counter Party Detail:')])[1]
    Sleep    2
    ${uwoNext}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[2]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${uwoNext}'=='True'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[2]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[2]
    Click Element                          xpath:(//button[contains(text(),'Next')])[2]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[3]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[3]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[3]
    Click Element                          xpath:(//button[contains(text(),'Next')])[3]
    END
    Sleep    2
    Wait Until Page Contains Element      xpath:(//span[contains(text(),'Override Soft Policy')])[1]
    Sleep    2
    ${uwo1Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[2]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${uwo1Next}'=='True'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[2]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[2]
    Click Element                          xpath:(//button[contains(text(),'Next')])[2]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[3]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[3]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[3]
    Click Element                          xpath:(//button[contains(text(),'Next')])[3]
    END
    Sleep    2
    Execute JavaScript    var xpath = "(//button[contains(text(),'Approve')])"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Approve')])     timeout=10s
    Set Focus To Element                   xpath:(//button[contains(text(),'Approve')])
    Click Button                           xpath:(//button[contains(text(),'Approve')])
    Sleep                                  2

    #Remarks
    Input Text                             xpath://*[@class='swal2-textarea']    Approved
    #Submitting
    Wait Until Page Does Not Contain       xpath:(//*[text()='Submit'])[1]     timeout=10s
    Set Focus To Element                   xpath:(//*[text()='Submit'])[1]
    Click Button                           xpath:(//*[text()='Submit'])[1]
    Sleep  2
    Logout from Triton