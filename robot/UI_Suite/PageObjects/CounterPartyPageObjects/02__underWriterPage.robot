*** Settings ***  
Resource           ../../keywords/common.robot      
Resource           ../assignmentPage.robot
Resource           ../CounterPartyPageObjects/01__businessPage.robot

*** Variables ***
${user}=    credit_head_lead

*** Keywords ***
From credit_head_lead to credit_head
    [Documentation]                     Assigning credit head by Credit head lead  
    Log     \n Proposed amount in keyword: ${proposed_amt}
    IF  ${proposed_amt}<=40000000
        ${userName1}=      Set Variable      ${creditHLUser}
        ${password1}=      Set Variable      ${creditHLPw}
    ELSE
         ${userName1}=      Set Variable      ${riskHeadUser}
         ${password1}=      Set Variable      ${riskHeadPw}
    END
    IF     '${userName1}'=='${user}'
        ${userSelect1}=                      Set Variable    CREDIT HEAD
        ${userName2}=    Set Variable    Credit_head
        ${password2}=    Set Variable    ${creditHeadPw}
    ELSE
        ${userSelect1}=                      Set Variable    RISK HEAD
        ${userName2}=    Set Variable    Risk_head
        ${password2}=    Set Variable    ${riskHeadPw}    
    END  
    assignmentPage.Search the case      ${userName1}    ${password1}    ${userSelect1}    ${caseName}
    loginPage.login to Triton   ${userName2}     ${password2}

cp_underwriter
    [Documentation]                     Counter party underwriter
    Wait Until Page Contains Element    xpath://*[contains(text(),'Counter Party')]    timeout=15s
    Set Focus To Element                xpath://span[text()='Inbox']
    Click Element                       xpath://span[text()='Inbox']
    Sleep                               4
    Run Keyword    Select the case in Inbox
    Sleep    2
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    3
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[1]        timeout=10s
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[1]
    Click Element                          xpath:(//button[contains(text(),'Next')])[1]
    Sleep    2
    Execute Javascript                  window.scrollTo(0,document.body.scrollHeight)
    Wait Until Page Contains Element    xpath://*[text()='Assign To']                    timeout=10s
    ${Assign_case1}=    Run Keyword If      ${proposed_amt}<=40000000   Run Keyword And Return Status   Select From List By Label           Xpath://select[@type="text"]           CREDIT UNDERWRITER_LEAD
    ${Assign_case1}=     Run Keyword If     '${Assign_case1}' == 'False'   Select From List By Label          Xpath://select[@type="text"]            RISK UNDERWRITER_LEAD
    Sleep                               2
    Click Button                        xpath://button[contains(text(),'Save')]
    Click Button                        xpath://button[@type="submit"][1]
    Execute Javascript                  window.scrollTo(0,document.body.scrollHeight)
    Sleep                               2
    Set Focus To Element                xpath://button[@type="button"][1]                 #assign cpa
    Click Button                        xpath://button[@type="button"][1]
    Input Text                          xpath:/html/body/div[2]/div/textarea       ${all_characters}        #remarks
    Set Focus To Element                xpath:(//button[text()='Submit'])[2]
    Click Element                       xpath:(//button[text()='Submit'])[2]
    Log                                 Counter party underwriter stage completed
    Sleep                               5
    Logout from Triton