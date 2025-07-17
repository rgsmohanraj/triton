*** Settings ***
Resource           ../../keywords/common.robot
Resource     ../../PageObjects/loginPage.robot


*** Keywords ***
Deferral ops maker
    Def_Ops_Maker login 
    Def_Ops_Maker Basicdetails
    Def_Operation_Maker
    Deferral_Maker_Remarks
Def_Ops_Maker login 
    [Documentation]    Login User
    Open triton in browser  
    ${userName}=     Set Variable    ${opsMakerLeadUser}
    ${password}=     Set Variable    ${opsMakerLeadPw}
    login to Triton    ${userName}    ${password}
Def_Ops_Maker Basicdetails
    [Documentation]     Basic Details
    Wait Until Element Is Visible   xpath://*[text()='Document']                  timeout=15s
    ${scrollInbox}=    Run Keyword And Return Status     Click Element                    xpath://*[text()='Document']
    Sleep    2
    Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element     xpath://*[text()='Document']
    Run Keyword If    '${scrollInbox}' == 'False'        Click Element      xpath://*[text()='Document']
    Sleep    2
    Click Element    xpath://a//span[text()='Deferral']
    Sleep    1
    Click Element    xpath://div[@id='defAnchorCount']
    Run Keyword    Select the case in Inbox
    Sleep   1
    Execute Javascript         window.scrollTo(0,document.body.scrollHeight)
    Sleep   1
    Click Button    xpath:(//button[text()='Next'])[1]
    Sleep   1
Def_Operation_Maker
    [Documentation]    Deferral operation maker
    Execute Javascript         window.scrollTo(0,-document.body.scrollHeight)
    Sleep    2 

    Comment    Networth Certificate

    Wait Until Element Is Visible    xpath://*[contains(text(),'Networth certificate (if applicable)')]                 timeout=10s
    ${scrollInbox}=    Run Keyword And Return Status      Wait Until Element Is Visible            xpath://*[contains(text(),'Networth certificate (if applicable)')]
    ${Date}=    Get Current Date
    ${day}=    Get Substring    ${Date}    8    10
    ${month}=    Get Substring    ${Date}    6    7
    ${year}=    Get Substring    ${Date}     0    4
    Run Keyword If    '${scrollInbox}' == 'True'     Input Text                 xpath://input[@id="16159A"]   ${day}0${month}0${year}
    Sleep    2
    Run Keyword If    '${scrollInbox}' == 'True'     Choose File                xpath://input[@id="16159V"]    ${CURDIR}//Anchor File Upload.xlsx
    Sleep    2
    Run Keyword If    '${scrollInbox}' == 'True'     Execute Javascript         window.scrollTo(0,document.body.scrollHeight)
    Sleep    2
    Run Keyword If    '${scrollInbox}' == 'False'      Log                     \nNetworth Certificate Document is not applicable
    Sleep    2

    Comment    ChargeFiling Certificate

    Wait Until Element Is Visible    xpath://*[contains(text(),'Charge Filing')]                 timeout=10s
    ${scrollInbox}=    Run Keyword And Return Status      Wait Until Element Is Visible            xpath://*[contains(text(),'Charge Filing')]
    ${Date}=    Get Current Date
    ${day}=    Get Substring    ${Date}    8    10
    ${month}=    Get Substring    ${Date}    6    7
    ${year}=    Get Substring    ${Date}     0    4
    Run Keyword If    '${scrollInbox}' == 'True'     Input Text                 xpath://input[@id="20161A"]   ${day}0${month}0${year}
    Sleep    2
    Run Keyword If    '${scrollInbox}' == 'True'     Choose File                xpath://input[@id="20161V"]    ${CURDIR}//Anchor File Upload.xlsx
    Sleep    2
    Run Keyword If    '${scrollInbox}' == 'True'     Execute Javascript         window.scrollTo(0,document.body.scrollHeight)
    Sleep    2
    Run Keyword If    '${scrollInbox}' == 'False'      Log                     \nchargefiling Certificate Document is not applicable
    Sleep    2
    
    
    Click Button    xpath:(//button[text()='Next'])[1]
    Sleep    2

Deferral_Maker_Remarks
    [Documentation]    Deferral Remarks 
    Click Button    xpath://button[text()='Submit']
    Input Text      xpath://textarea[@class='swal2-textarea']    okay 
    Sleep    1
    Click Button    xpath:(//button[text()='Submit'])[2]
    sleep    8
    Logout from Triton
 #  Click Button    xpath://button[text()='Cancel']
    
