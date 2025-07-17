*** Settings ***  
Resource     ../../keywords/common.robot    
Resource     ../assignmentPage.robot
Resource     ../CounterPartyPageObjects/01__businessPage.robot

*** Variables ***
${value}=     Free field
${user1}=     credit_underwriter_lead

*** Keywords ***
To credit underwriter or risk underwriter lead
    [Documentation]     Assigning to credit underwriter or risk underwriter lead
    Log     \n Proposed amount in keyword: ${proposed_amt}
    IF  ${proposed_amt}<=40000000
    ${userName1}=      Set Variable      ${creditUWLeadUser}
    ${password1}=      Set Variable      ${creditUWLeadPw}
    ELSE
        ${userName1}=      Set Variable      ${riskULUser}
    ${password1}=      Set Variable      ${riskULPw}
    END
    IF  '${userName1}'=='${user1}'
        ${userSelect1}=     Set Variable    CREDIT UNDERWRITER
        ${userName2}=    Set Variable    ${creditUnderwriterUser}
        ${password2}=    Set Variable    ${creditUnderwriterPw}
    ELSE
        ${userSelect1}=      Set Variable    RISK UNDERWRITER
        ${userName2}=    Set Variable    ${riskUnderwriterUser}
        ${password2}=    Set Variable    ${riskUnderwriterPw}    
    END  
    assignmentPage.Search the case      ${userName1}    ${password1}    ${userSelect1}    ${caseName}
    loginPage.login to Triton   ${userName2}     ${password2}

cp_underwriter_pd_review_stage7
    [Documentation]       Counter party pd review stage
    Wait Until Page Contains Element    xpath://*[contains(text(),'Counter Party')]    timeout=10s
    Set Focus To Element                xpath://span[text()='Inbox']
    Click Element                       xpath://span[text()='Inbox']
    Sleep    3                             
    Run Keyword    Select the case in Inbox
    Sleep    1
    Wait Until Page Contains Element      xpath:(//span[contains(text(),'CP Details')])[7]     timeout=10s
     #cp details
    Sleep    3
    # Execute Javascript                  window.scrollTo(0,document.body.scrollHeight)
    # Sleep    5
    # Click Button    xpath:(//button[text()='Next'])[5]
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
    Input Text                          xpath://input[@id="dateOfVisit"]                      12/10/2022
    Input Text                          xpath://input[@id="marketInformation"]                ${value}
    Input Text                          xpath://input[@id="promoterbackround"]                ${value}
    Input Text                          xpath://input[@id="nameOfThePerson"]                  ${value}
    Input Text                          xpath://input[@id="vclAttendees"]                     ${value}
    Input Text                          xpath://input[@id="addressOfPremisesVisited"]         ${value}
    Input Text                          xpath://input[@id="observationOnFacility"]            ${value}
    Input Text                          xpath://input[@id="businessModel"]                    ${value}
    Input Text                          xpath://input[@id="stockObservation"]                 ${value}
    Input Text                          xpath://input[@id="businessPlans"]                    ${value}
    Input Text                          xpath://input[@id="marketFeedbacks"]                  ${value}
    Sleep    2
    ${duediligence}=     Run Keyword And Return Status   Execute JavaScript                  var xpath = "(//button[contains(text(),'Save')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);    
    Sleep    2
    Run Keyword If    '${duediligence}'=='True'          Click Element                       xpath:(//button[contains(text(),'Save')])[1]          
    Run Keyword If    '${duediligence}'=='False'         Execute JavaScript                  var xpath = "(//button[contains(text(),'Update')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    Run Keyword If    '${duediligence}'=='False'         Wait Until Element Is Visible       xpath:(//button[contains(text(),'Update')])[1]    timeout=15s
    Run Keyword If    '${duediligence}'=='False'         Set Focus to Element                xpath:(//button[contains(text(),'Update')])[1]
    Run Keyword If    '${duediligence}'=='False'         Click Element                       xpath:(//button[contains(text(),'Update')])[1]
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
    
    #remarks
    Sleep     1
    Execute Javascript                  window.scrollTo(0,document.body.scrollHeight)
    Sleep     1
    Set Focus To Element                xpath://button[contains(text(),'Submit')]
    Click Element                       xpath://button[contains(text(),'Submit')]
    Input Text                          xpath://textarea[@class="swal2-textarea"]     Okay
    Set Focus To Element                xpath:(//button[contains(text(),'Submit')])[2]
    Click Element                       xpath:(//button[contains(text(),'Submit')])[2]
    Log                                 Counter party PD review stage completed
    Sleep    3
    Logout from Triton