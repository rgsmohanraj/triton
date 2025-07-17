*** Settings ***
Resource        ../../keywords/common.robot
Resource        ../../PageObjects/assignmentPage.robot

*** Variables ***
${Bank/FI}            HDFC Bank
${FacilityType}       TERMLOAN
${Tenure}               12
${Date Sanction}      14-02-2023
${LmtSanction}         1000000
${OS as On}            1000000
${EMI}                   12
${Interest}            12.24
${Security}             Yes
${GnrlLmtCal}           Yes
${Remarks}              Test

*** Keywords ***
Cam Upload
  [Documentation]    CAM Upload
  CP Details & List
  Cp Details_ref
  Override SoftPolicy
  Debt Profile
  CAM Upload Testcase
  # Logout and close browser
  Close Window
    
CP Details & List
    [Documentation]    CP Details & List
    Set Selenium Speed    0.5s
    Wait Until Page Contains Element        xpath://span[text()='Inbox']    timeout=20s
    Set Focus To Element                    xpath://span[text()='Inbox'] 
    Click Element                           xpath://span[text()='Inbox']

    Run Keyword    Select the case in Inbox
    
Cp Details_ref
    [Documentation]    CP Details
    Wait Until Page Contains Element       xpath:(//label[contains(text(),'Counter Party Detail:')])[2]     timeout=10s
        Sleep    2
    ${cuNext}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[3]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${cuNext}'=='True'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[3]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[3]
    Click Element                          xpath:(//button[contains(text(),'Next')])[3]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[4]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[4]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[4]
    Click Element                          xpath:(//button[contains(text(),'Next')])[4]
    END
    Sleep    2
    

Override SoftPolicy
    [Documentation]    Override Softpolicy
    Wait Until Page Contains Element       xpath:(//span[contains(text(),'Override Soft Policy')])[2]     timeout=10s
    Sleep    2
    ${cu1Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[3]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${cu1Next}'=='True'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[3]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[3]
    Click Element                          xpath:(//button[contains(text(),'Next')])[3]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[4]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[4]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[4]
    Click Element                          xpath:(//button[contains(text(),'Next')])[4]
    END
    Sleep    2
    
Debt Profile
    [Documentation]    Debt Profile
    Input Text    xpath://input[@ng-reflect-name='bankFI0']      ${Bank/FI}
    Sleep    1
    Select From List By Value    xpath://select[@ng-reflect-name='facilityType0']         ${FacilityType}
    Input Text    xpath://input[@ng-reflect-name='tenure0']                ${Tenure}
    Input Text    xpath://input[@ng-reflect-name='dateSanction0']          ${Date Sanction}
    Input Text    xpath://input[@ng-reflect-name='limitSanctioned0']       ${LmtSanction}
    Input Text    xpath://input[@ng-reflect-name='OSasOn0']                ${OS as On}
    Input Text    xpath://input[@ng-reflect-name='emi0']                   ${EMI}
    Input Text    xpath://input[@ng-reflect-name='interest0']              ${Interest}
    Input Text    xpath://input[@ng-reflect-name='security0']              ${Security}
    Input Text    xpath://input[@ng-reflect-name='specificLimit0']         ${GnrlLmtCal}
    Input Text    xpath://input[@ng-reflect-name='remarks0']               ${Remarks}
    Execute Javascript                  window.scrollTo(0,document.body.scrollHeight)
    Sleep    2
    ${debtprofile}=     Run Keyword And Return Status   Click Element                       xpath://button[contains(text(),'Save')]
    Run Keyword If    '${debtprofile}'=='False'         Execute JavaScript                  window.scrollTo(0,document.body.scrollHeight)
    Run Keyword If    '${debtprofile}'=='False'         Wait Until Element Is Visible       xpath://button[contains(text(),'Update')]    timeout=10s
    Run Keyword If    '${debtprofile}'=='False'         Set Focus to Element                xpath://button[contains(text(),'Update')]
    Run Keyword If    '${debtprofile}'=='False'         Click Element                       xpath://button[contains(text(),'Update')]
    Sleep    2
    ${cu2Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[3]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${cu2Next}'=='True'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[3]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[3]
    Click Element                          xpath:(//button[contains(text(),'Next')])[3]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[4]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[4]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[4]
    Click Element                          xpath:(//button[contains(text(),'Next')])[4]
    END
    Sleep    2
CAM Upload Testcase
  [Documentation]    Cam Upload 
    Sleep    1
    Choose File        id:16293       ${CURDIR}\\Test_File.xlsx
    Sleep    3
    # Execute Javascript   window.scrollTo(0,document.body.scrollHeight)
    # Sleep    2
    # Click Button    xpath:(//button[text()='Next'])[3]
    ${cu4Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[3]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${cu4Next}'=='True'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[3]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[3]
    Click Element                          xpath:(//button[contains(text(),'Next')])[3]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[4]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[4]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[4]
    Click Element                          xpath:(//button[contains(text(),'Next')])[4]
    END
    Sleep    2 
    Set Focus To Element               xpath:(//button[text()='Submit'])[1]
    Wait Until Page Contains Element    xpath:(//button[text()='Submit'])[1]    timeout=15s
    Sleep    2
    Execute Javascript   window.scrollTo(0,document.body.scrollHeight)
    Click Button    xpath://button[text()='Submit']
    Input Text      xpath://*[@class='swal2-textarea']       Approved
    Click Button    xpath:(//button[text()='Submit'])[2]
    Sleep    3
    Logout from Triton
    
    