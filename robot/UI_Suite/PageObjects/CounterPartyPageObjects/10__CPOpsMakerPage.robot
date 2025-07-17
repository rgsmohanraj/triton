*** Settings ***
Resource         ../../keywords/common.robot
Resource         ../../PageObjects/assignmentPage.robot

*** Variables ***
${filePath}=      ${CURDIR}\\CP_Roles.txt    
${bankName}       AXIS BANK
${ifscCode}       UTIB0000090
${rmName}         Durgaa Vadivel

*** Keywords ***
From Ops_maker_lead assign Ops_maker
    [Documentation]    Assign to Ops maker
    Set Selenium Speed    0.4s
    ${userName}=     Set Variable    ${opsMakerLeadUser}
    ${password}=     Set Variable    ${opsMakerLeadPw}
    ${userSelect}=   Set Variable    OPERATION MAKER
    assignmentPage.Search the case   ${userName}    ${password}    ${userSelect}   ${caseName} 
    ${userName}=     Set Variable    ${opsMakerUser}
    ${password}=     Set Variable    ${opsMakerPw}
    loginPage.login to Triton        ${userName}    ${password}     
 
 Cp_Beneficiary Details & DocumentUpload
    [Arguments]         ${accNum}
    [Documentation]     Counter Party Document Upload
    # ${userName}=     Set Variable    ${opsMakerLeadUser}
    # ${password}=     Set Variable    ${opsMakerLeadPw}
    # loginPage.Open triton in browser
    # loginPage.login to Triton        ${userName}    ${password}   
    #Selecting Counterparty
    Wait Until Element Is Visible        xpath://span[text()='Inbox']             timeout=15s
    Set Focus To Element                 xpath://span[text()='Inbox']
    Click Element                        xpath://span[text()='Inbox']
    Sleep    2
    Execute Javascript    window.scrollTo(0,document.body.scrollHeight)
    Run Keyword    Select the case in Inbox
    #Cp_Basic Details
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
    Input Text                           name:beneficiaryName                              ${cpName}
    Sleep    1
    Select From List By Label            name:bankDetails                                  ${bankName}
    Sleep    1                                                                              
    Input Text                           name:bankIfscCode                                 ${ifscCode}
    Sleep    1
    Input Text                           name:bankAccountNumber                            ${accNum}
    
    Execute Javascript                  window.scrollTo(0,document.body.scrollHeight)
    Sleep    2
    ${benStatus1}=     Run Keyword And Return Status   Click Element                       xpath://*[text()='Save']
    Run Keyword If    '${benStatus1}'=='False'         Execute JavaScript                  window.scrollTo(0,document.body.scrollHeight)
    Run Keyword If    '${benStatus1}'=='False'         Wait Until Element Is Visible       xpath://*[text()='Update']    timeout=10s
    Run Keyword If    '${benStatus1}'=='False'         Set Focus to Element                xpath://*[text()='Update']
    Run Keyword If    '${benStatus1}'=='False'         Click Element      xpath://*[text()='Update']
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

    FOR  ${i}  IN RANGE    1    22 
        IF  '${i}' != '9'
            Set Focus To Element             xpath:(//input[@type='file'])[${i}]
            Choose File                      xpath:(//input[@type='file'])[${i}]           ${filePath}   
        END
    END    
    
       #Networth Certificate
        Wait Until Element Is Visible    xpath://*[@id='16116A']                  timeout=15s
        ${scrollInbox}=    Run Keyword And Return Status     Click Element                     xpath://*[@id='16116A']
        Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript               window.scrollTo(0,document.body.scrollHeight)

        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element              xpath://*[@id='16116A']
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Click Element                     xpath://*[@id='16116A']
        Sleep    1
        ${Networth_date}        Get Current Date
        ${day}=     Get Substring    ${Networth_date}    8    10
        ${month}=   Get Substring    ${Networth_date}    5    7
        Click Element      xpath://*[@id='16116A']
        Input Text         xpath://*[@id='16116C']     ${day}0${month}
    
        #Insurance Certificate
        Wait Until Element Is Visible    xpath://*[@id='16117B']                 timeout=15s
        ${scrollInbox}=    Run Keyword And Return Status     Click Element                     xpath://*[@id='16117B']
        Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript                var xpath = "//*[@id='16117B']"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);

        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element              xpath://*[@id='16117B']
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Click Element                     xpath://*[@id='16117B']
        Sleep    1
        
        Execute JavaScript                     var xpath = "//*[@name='docRMName']"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
        Wait Until Element Is Visible    xpath://*[@name='docRMName']                timeout=15s
        ${scrollInbox}=    Run Keyword And Return Status     Click Element                     xpath://*[@name='docRMName'] 
        Run Keyword If    '${scrollInbox}' == 'True'        Set Focus to Element              xpath://*[@name='docRMName']
        Run Keyword If    '${scrollInbox}' == 'True'        Click Element                     xpath://*[text()='${rmName}']
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
        Wait Until Element Is Visible        xpath://*[text()='Submit']                 timeout=10s
        Set Focus To Element                 xpath://*[text()='Submit'] 
        Click Element                        xpath://*[text()='Submit']
        
        Input Text                           xpath://textarea[@class='swal2-textarea']         Documents uploaded successfully

        Wait Until Element Is Visible        xpath:(//button[text()='Submit'])[2]              timeout=10s
        Set Focus To Element                 xpath:(//button[text()='Submit'])[2]
        Click Element                        xpath:(//button[text()='Submit'])[2]
        Sleep    4
        Logout from Triton
    




