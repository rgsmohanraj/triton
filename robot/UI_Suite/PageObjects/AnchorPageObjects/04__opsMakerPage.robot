*** Settings ***  
Resource          ../../keywords/common.robot    
Resource          ../../PageObjects/assignmentPage.robot

*** Variables ***  
${bankName}=         AXIS BANK
${bankBranch}=       1234
${ifscCode}=         UTIB0001165
${filePath}=         ${CURDIR}\\Sample.pdf


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
    
Beneficiary details and Document upload
        [Arguments]    ${accNum}
        [Documentation]    Anchor beneficiary details and document upload
        # Set Selenium Speed    0.4s
        Sleep    2
        Wait Until Element Is Visible        xpath://span[text()='Inbox']    timeout=10s
        Click Element                        xpath://span[text()='Inbox']
        Sleep    2
        Run Keyword        Select the case in Inbox
        Sleep    2
        Wait Until Element Is Visible   xpath: (//button[text()='Next'])[3]                   timeout=15s
        ${scrollInbox}=    Run Keyword And Return Status     Click Element                    xpath:(//button[text()='Next'])[3]
        Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript               var xpath = "(//button[text()='Next'])[3]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element             xpath:(//button[text()='Next'])[3]
        Run Keyword If    '${scrollInbox}' == 'False'        Click Element                    xpath:(//button[text()='Next'])[3]

        Wait Until Page Contains Element     xpath://label[text()='GST Details :']
        Wait Until Element Is Visible   xpath: (//button[text()='Next'])[3]                   timeout=15s
        ${scrollInbox}=    Run Keyword And Return Status     Click Element                    xpath:(//button[text()='Next'])[3]
        Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript               var xpath = "(//button[text()='Next'])[3]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element             xpath:(//button[text()='Next'])[3]
        Run Keyword If    '${scrollInbox}' == 'False'        Click Element                    xpath:(//button[text()='Next'])[3]
        
        # Wait Until Page Contains Element     xpath://label[text()='Funding Type']
        Wait Until Element Is Visible   xpath: (//button[text()='Next'])[3]                   timeout=15s
        ${scrollInbox}=    Run Keyword And Return Status     Click Element                    xpath:(//button[text()='Next'])[3]
        Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript               var xpath = "(//button[text()='Next'])[3]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element             xpath:(//button[text()='Next'])[3]
        Run Keyword If    '${scrollInbox}' == 'False'        Click Element                    xpath:(//button[text()='Next'])[3]
        Sleep    2

        #Credit norms
        # Wait Until Page Contains Element     xpath://*[text()='Gst Certificate']      timeout=10s
        Wait Until Element Is Visible   xpath: (//button[text()='Next'])[3]                   timeout=15s
        ${scrollInbox}=    Run Keyword And Return Status     Click Element                    xpath:(//button[text()='Next'])[3]
        Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript               var xpath = "(//button[text()='Next'])[3]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element             xpath:(//button[text()='Next'])[3]
        Run Keyword If    '${scrollInbox}' == 'False'        Click Element                    xpath:(//button[text()='Next'])[3]
        Sleep    2
        
        #Beneficiary
        Input Text                           name:beneficiaryName                              ${cpName}
        Sleep    1
        Select From List By Label            name:bankDetails                                  ${bankName}
        Sleep    1                                                                              
        Input Text                           name:bankIfscCode                                 ${ifscCode}
        Sleep    1
        Input Text                           name:bankAccountNumber                            ${accNum}
        
        Execute Javascript                  window.scrollTo(0,document.body.scrollHeight)
        Sleep    2
        ${benStatus}=     Run Keyword And Return Status   Click Element                       xpath://*[contains(text(),'Save')]
        Run Keyword If    '${benStatus}'=='False'         Execute JavaScript                  window.scrollTo(0,document.body.scrollHeight)
        Run Keyword If    '${benStatus}'=='False'         Wait Until Element Is Visible       xpath:(//button[contains(text(),'Update')])[2]    timeout=10s
        Run Keyword If    '${benStatus}'=='False'         Set Focus to Element                xpath:(//button[contains(text(),'Update')])[2]
        Run Keyword If    '${benStatus}'=='False'         Click Element                       xpath:(//button[contains(text(),'Update')])[2]

        Wait Until Element Is Visible   xpath: (//button[text()='Next'])[3]                   timeout=15s
        ${scrollInbox}=    Run Keyword And Return Status     Click Element                    xpath:(//button[text()='Next'])[3]
        Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript               window.scrollTo(0,document.body.scrollHeight)
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element             xpath:(//button[text()='Next'])[3]
        Run Keyword If    '${scrollInbox}' == 'False'        Click Element                    xpath:(//button[text()='Next'])[3]                                      
        Sleep    2
        
        FOR  ${i}  IN RANGE    1    33 
            IF  '${i}' != '31'
                Set Focus To Element             xpath:(//input[@type='file'])[${i}]
                Choose File                      xpath:(//input[@type='file'])[${i}]           ${filePath}   
            END
        END      
        
        Comment    Networth Certificate
        Wait Until Element Is Visible    xpath://*[@id='16159A']                  timeout=15s
        ${scrollInbox}=    Run Keyword And Return Status     Click Element                     xpath://*[@id='16159A']
        Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript               window.scrollTo(0,document.body.scrollHeight)

        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element              xpath://*[@id='16159A']
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Click Element                     xpath://*[@id='16159A']
        Sleep    1
        ${Networth_date}        Get Current Date
        ${day}=     Get Substring    ${Networth_date}    8    10
        ${month}=   Get Substring    ${Networth_date}    5    7
        Input Text   xpath://*[@id='16159C']     ${day}0${month}

        Comment    Insurance Certificate
        Wait Until Element Is Visible    xpath://*[@id='16160B']                 timeout=15s
        ${scrollInbox}=    Run Keyword And Return Status     Click Element                     xpath://*[@id='16160B']
        Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript                var xpath = "//*[@id='16160B']"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);

        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element              xpath://*[@id='16160B']
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Click Element                     xpath://*[@id='16160B']
        Sleep    2

        Execute JavaScript    var xpath = "//*[@class='ms-3 fa fa-plus-circle pull-right']"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
        Sleep    2


        Comment   CERSAI
        ${scrollInbox}=    Run Keyword And Return Status     Wait Until Element Is Visible    xpath://*[contains(text(),'CERSAI ')]                  timeout=10s
        Sleep    2                                             
        Run Keyword If    '${scrollInbox}' == 'True'        Click Element                     xpath://*[@id="20160D"]
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'True'        Set Focus to Element              xpath://*[contains(text(),'Completed')][1]     
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'True'        Click Element                     xpath://*[contains(text(),'Completed')][1]            
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'True'        Input Text                        xpath://*[@id="20160E"]        1234
        Sleep    3
        ${Date}=    Get Current Date
        ${day}=     Get Substring    ${Date}    8    10
        ${month}=   Get Substring    ${Date}    6    7
        ${year}=    Get Substring    ${Date}    0    4
        Run Keyword If    '${scrollInbox}' == 'True'        Input Text         xpath:(//*[@id='20160F'])   ${day}0${month}0${year}
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'True'        Choose File        xpath://input[@id='20160']   ${CURDIR}//Anchor File Upload.xlsx
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'         Log             \nDocument-Cerasi is not applicable
        Sleep    2


        Comment    CHARGE FILLING
        
        Sleep    2
        ${scrollInbox}=    Run Keyword And Return Status      Wait Until Element Is Visible     xpath://*[contains(text(),'Charge Filling ')]                 timeout=10s                   
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'True'        Click Element                       xpath://*[@id="20161D"] 
        Run Keyword If    '${scrollInbox}' == 'True'        Set Focus to Element                xpath://*[@id="20161D"]
        Sleep    2 
        Run Keyword If    '${scrollInbox}' == 'True'        Select From List By Value           xpath:(//select[@type='text'])[4]        No                  
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'True'        Input Text                          xpath://*[@id="20161E"]        1234
        Sleep    2
        ${Date}=    Get Current Date
        ${day}=    Get Substring    ${Date}    8    10
        ${month}=    Get Substring    ${Date}    6    7
        ${year}=    Get Substring    ${Date}     0    4
        Run Keyword If    '${scrollInbox}' == 'True'            Input Text    xpath:(//*[@id='20161F'])   ${day}0${month}0${year}
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'True'         Choose File      xpath://input[@id='20161']    ${CURDIR}//Anchor File Upload.xlsx
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'         Log             \nDocument-Charge Filling is not applicable
        Sleep    2
      
        Execute JavaScript    var xpath = "//*[@class='ms-3 fa fa-plus-circle pull-right']"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
        Sleep    2

        Comment      Other document

        Click Element  xpath://*[@class='ms-3 fa fa-plus-circle pull-right']
        Sleep    2
        Execute JavaScript    var xpath = "//*[@id='16166A0']"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
        Sleep    2
        Set Focus To Element    xpath://*[@id='16166A0']
        Click Element  xpath://*[@id='16166A0']
        Sleep    2
        Execute JavaScript    var xpath = "//*[@id='16166C0'] "; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
        Sleep    2
        Input Text     xpath://*[@id='16166C0']        Test_Deferral_File
        Sleep    2
        ${Date}=    Get Current Date
        ${day}=     Get Substring    ${Date}    8    10
        ${month}=   Get Substring    ${Date}    5    7
        Sleep    2
        Input Text   xpath://*[@id='16166D0']    ${day}0${month}

        Sleep    2
        Wait Until Element Is Visible    xpath://*[@name='rmName']            timeout=15s
        ${scrollInbox}=    Run Keyword And Return Status     Click Element                     xpath://*[@name='rmName']
        Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript                var xpath = "//*[@name='rmName']"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);

        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element              xpath://*[@name='rmName']
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Click Element                     xpath://*[@name='rmName']
        Sleep    2
        
        Wait Until Element Is Visible    xpath://*[text()='${RM_name}']                timeout=15s
        ${scrollInbox}=    Run Keyword And Return Status     Click Element                     xpath://*[text()='${RM_name}']
        Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript               var xpath = "//*[text()='${RM_name}']"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);

        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element              xpath://*[text()='${RM_name}']
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Click Element                     xpath://*[text()='${RM_name}']
        Sleep    2
        
        Wait Until Element Is Visible   xpath: (//button[text()='Next'])[3]                   timeout=15s
        ${scrollInbox}=    Run Keyword And Return Status     Click Element                    xpath:(//button[text()='Next'])[3]
        Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript               var xpath = "(//button[text()='Next'])[3]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);


        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element             xpath:(//button[text()='Next'])[3]
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Click Element                    xpath:(//button[text()='Next'])[3]
        Sleep    2

        Wait Until Element Is Visible   xpath://button[text()='Submit']                      timeout=15s
        ${scrollInbox}=    Run Keyword And Return Status     Click Element                    xpath://button[text()='Submit'] 
        Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript               var xpath = "//button[text()='Submit']"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element             xpath://button[text()='Submit']
        Run Keyword If    '${scrollInbox}' == 'False'        Click Element                    xpath://button[text()='Submit']

        Input Text                           xpath://textarea[@class='swal2-textarea']         Documents uploaded successfully

        Wait Until Element Is Visible   xpath: (//button[text()='Submit'])[2]                   timeout=15s
        ${scrollInbox}=    Run Keyword And Return Status     Click Element                    xpath:(//button[text()='Submit'])[2]
        Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript               var xpath = "(//button[text()='Submit'])[2]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
        Sleep    2
        Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element             xpath:(//button[text()='Submit'])[2]
        Run Keyword If    '${scrollInbox}' == 'False'        Click Element                    xpath:(//button[text()='Submit'])[2]
        sleep    10
        Logout from Triton