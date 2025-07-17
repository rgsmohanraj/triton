*** Settings ***
Resource          ../../keywords/common.robot    
Resource          ../../PageObjects/assignmentPage.robot

*** Variables ***
${file_location}    ${CURDIR}\\UATFile.xlsx
${all_characters}     case UPP1234567890!@#$%^&*()_-+=[]\[]\{}|;'':"<>?,./`~\|"

*** Keywords ***

Renewal_Flow_CP
            
    [Documentation]    Business User Login
       # Set Selenium Speed    0.4s
    loginPage.Open triton in browser
    loginPage.login to Triton        ${businessLeadUser}    ${businessLeadPw}     
    Wait Until Element Is Visible     xpath://span[contains(text(),'Counter party Details')]      timeout=10s
    Sleep    2
    Execute JavaScript    var xpath = "//td[contains(text(),'${cpName}')]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);        
    Wait Until Element Is Visible     xpath://td[contains(text(),'${cpName}')]    timeout=10s
    Sleep    2
    Click Element                     xpath://td[contains(text(),'${cpName}')]
    Sleep    1
    Click Element                     xpath://div[contains(text(),'Renewal/Enhancement ')]
    Sleep    2
    Click Element                     xpath://input[@value="renewal"]              
    Sleep    1
    Click Element                     xpath://button[contains(text(),'Submit')]
    Sleep    2
    Click Element                     xpath://select[@id='constitution1']
    Sleep    1
    Click Element                     xpath://option[@value='Registered Partnership']
    Sleep    1
    Wait Until Element Is Visible        xpath://button[contains(text(),'Save')]    timeout=10s
    Execute Javascript   window.scrollTo(0,document.body.scrollHeight)
    Sleep    2
    Set Focus To Element                 xpath://button[contains(text(),'Save')]
    Click Element                        xpath://button[contains(text(),'Save')]
    Sleep    2
    Set Focus To Element                 xpath:(//button[text()='Next'])[1]
    Click Element                        xpath:(//button[text()='Next'])[1]
    Wait Until Element Is Visible        xpath://button[contains(text(),'Save')]    timeout=10s
    Execute Javascript   window.scrollTo(0,document.body.scrollHeight)
    Sleep    2
    Set Focus To Element                 xpath://button[contains(text(),'Save')]
    Click Element                        xpath://button[contains(text(),'Save')]
    Set Focus To Element                 xpath:(//button[text()='Next'])[1]
    Click Element                        xpath:(//button[text()='Next'])[1]
    Sleep    2
        ${FIle_Upload1}=     Run Keyword And Return Status     Select From List By Label           xpath://select[@type="text"]      Cashflow 
    IF  ${FIle_Upload1}==True
        FOR    ${i}  IN RANGE        1    5
        Sleep                           2
        Set Focus To Element            xpath:(//input[@type='file'])[${i}]
        Choose File                     xpath:(//input[@type='file'])[${i}]         ${file_location}
        END
    END
    ${FIle_Upload2}=    Run Keyword If    '${FIle_Upload1}' == 'False'   Run Keyword And Return Status      Select From List By Label           xpath://select[@type="text"]       KYC
    IF  ${FIle_Upload2}==True
        FOR    ${i}  IN RANGE               1    5
        Sleep                           2
        Set Focus To Element            xpath:(//input[@type='file'])[${i}]
        Choose File                     xpath:(//input[@type='file'])[${i}]         ${file_location}
        END
    END
    ${FIle_Upload3}=    Run Keyword If    '${FIle_Upload2}' == 'False'    Run Keyword And Return Status     Select From List By Label           xpath://select[@type="text"]       Financial
    IF  ${FIle_Upload3}==True
        FOR    ${i}  IN RANGE               1    5
        Sleep                           2
        Set Focus To Element            xpath:(//input[@type='file'])[${i}]
        Choose File                     xpath:(//input[@type='file'])[${i}]         ${file_location}
        END
    END
    Log                                 Counter party file upload done
    Execute Javascript   window.scrollTo(0,document.body.scrollHeight)
    Sleep    2
    Wait Until Element Is Visible       xpath:(//button[@type='submit'])[1]    timeout=10s
    Set Focus To Element                xpath:(//button[@type='submit'])[1]
    Click Element                       xpath:(//button[@type='submit'])[1]
    Sleep                               2
    Click Element                       xpath:(//button[@type='button'])[1]
    Input Text                          xpath:(//textarea[@id="swal2-textarea"])       ${all_characters}        #remarks
    Set Focus To Element                xpath:(//button[@type="button"])[6]
    Wait Until Element Is Visible       xpath:(//button[@type="button"])[6]    timeout=10s
    Click Element                       xpath:(//button[@type="button"])[6]
    Log                                 Counter party business stage completed
    Sleep    10    
    Logout from Triton
    