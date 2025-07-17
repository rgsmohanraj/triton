*** Settings ***  
Resource           ../../keywords/common.robot     
Resource          ../../PageObjects/assignmentPage.robot   

*** Keywords ***
From Ops_checker_lead assign Ops_checker
    [Documentation]    Assign to Ops checker
    # Set Selenium Speed    0.4s
    ${userName}=         Set Variable    ${opsChkLeadUser}
    ${password}=         Set Variable    ${opsChkLeadPw}
    ${userSelect}=       Set Variable    OPERATION CHECKER
    assignmentPage.Search the case       ${userName}    ${password}    ${userSelect}   ${caseName} 
    ${userName}=         Set Variable    ${opsChkUser}
    ${password}=         Set Variable    ${opsChkPw}
    # loginPage.Open triton in browser
    loginPage.login to Triton            ${userName}    ${password} 

Ops_checker Approval
    [Documentation]                      Anchor Ops checker Approval stage
    Sleep    2
    Set Selenium Speed                   0.3s
    Wait Until Element Is Visible        xpath://span[text()='Inbox']    timeout=10s
    Click Element                        xpath://span[text()='Inbox']
    Sleep    2
    Run Keyword    Select the case in Inbox

    Wait Until Element Is Visible        xpath:(//button[text()='Next'])[4]    timeout=10s
    Set Focus To Element                 xpath:(//button[text()='Next'])[4]
    Sleep                                1
    Click Element                        xpath:(//button[text()='Next'])[4]

    # Wait Until Page Contains Element     xpath://label[text()='GST Details :']
    Wait Until Element Is Visible        xpath:(//button[text()='Next'])[4]    timeout=10s
    Set Focus To Element                 xpath:(//button[text()='Next'])[4]
    Sleep                                1
    Click Element                        xpath:(//button[text()='Next'])[4]

    # Wait Until Page Contains Element     xpath://label[text()='Funding Type']
    Wait Until Element Is Visible        xpath:(//button[text()='Next'])[4]    timeout=10s
    Set Focus To Element                 xpath:(//button[text()='Next'])[4]
    Sleep                                1
    Click Element                        xpath:(//button[text()='Next'])[4]

    #credit norms screen
    Set Focus To Element                 xpath:(//button[text()='Next'])[4]
    Sleep                                1
    Click Element                        xpath:(//button[text()='Next'])[4]
    #beneficiary screen
    Set Focus To Element                 xpath:(//button[text()='Next'])[4]
    Sleep                                1
    Click Element                        xpath:(//button[text()='Next'])[4]
    Sleep    2
    
    Wait Until Element Is Visible   xpath: (//*[@type='button'])[1]                   timeout=15s
    ${scrollInbox}=    Run Keyword And Return Status     Click Element                    xpath:(//*[@type='button'])[1]
    Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript               var xpath = "(//*[@type='button'])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element             xpath:(//*[@type='button'])[1]
    Run Keyword If    '${scrollInbox}' == 'False'        Click Element                    xpath:(//*[@type='button'])[1]

    Wait Until Element Is Visible   xpath: (//*[@type='button'])[2]                   timeout=15s
    ${scrollInbox}=    Run Keyword And Return Status     Click Element                    xpath:(//*[@type='button'])[2]
    Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript               var xpath = "(//*[@type='button'])[2]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element             xpath:(//*[@type='button'])[2]
    Run Keyword If    '${scrollInbox}' == 'False'        Click Element                    xpath:(//*[@type='button'])[2]

    #document screen
    Execute Javascript   window.scrollTo(0,document.body.scrollHeight)     
    Set Focus To Element                 xpath:(//button[text()='Next'])[4]
    Sleep                                2
    Click Element                        xpath:(//button[text()='Next'])[4]

    Execute Javascript   window.scrollTo(0,document.body.scrollHeight)     
    Set Focus To Element                 xpath://button[text()='Approve']
    Sleep                                2
    Click Element                        xpath://button[text()='Approve']
    #remarks
    Input Text                           xpath://*[@class='swal2-textarea']      Approved
    Click Element                        xpath://button[text()='Submit']
    Log                                  Approved by Operation checker
    sleep    19
    Logout from Triton