*** Settings ***  
Resource          ../../keywords/common.robot    
Resource          ../../PageObjects/assignmentPage.robot
*** Variables ***
${vintageName}=                   12    
${vintageBusiness}=               120000
${selectOptionNR}=                Not Required
${selectOptionReq}=               Required

* Keywords ***
From CPA lead assign to CPA
    [Documentation]    Assign case to CPA
       # Set Selenium Speed    0.4s
    ${userName}=     Set Variable    ${cpaLeadUser}
    ${password}=     Set Variable    ${cpaLeadPw}
    ${userSelect}=   Set Variable    CPA
    assignmentPage.Search the case   ${userName}    ${password}    ${userSelect}    ${caseName}
    ${userName}=     Set Variable    ${cpaUser}
    ${password}=     Set Variable    ${cpaPw}
    # loginPage.Open triton in browser
    loginPage.login to Triton        ${userName}    ${password}

Navigate to credit norms page
    [Documentation]    Navigation to credit norms page
    Set Selenium Speed    0.4s
    Wait Until Element Is Visible    xpath://span[text()='Inbox']    timeout=10s
    Click Element                    xpath://span[text()='Inbox']
    # Wait Until Element Is Visible        xpath://td[text()='${anchorName}']    timeout=15s
    # Click Element                        xpath://td[text()='${anchorName}']
    Run Keyword    Select the case in Inbox  
    Wait Until Element Is Visible        xpath:(//button[text()='Next'])[1]    timeout=10s
    Execute Javascript   window.scrollTo(0,document.body.scrollHeight)
    Set Focus To Element                 xpath:(//button[text()='Next'])[1]
    Click Element                        xpath:(//button[text()='Next'])[1]
    Wait Until Page Contains Element     xpath://span[text()='Anchor Details']
    Wait Until Element Is Visible        xpath:(//button[text()='Next'])[1]    timeout=10s
    Execute Javascript   window.scrollTo(0,document.body.scrollHeight)
    Set Focus To Element                 xpath:(//button[text()='Next'])[1]
    Click Element                        xpath:(//button[text()='Next'])[1]
    Wait Until Page Contains Element     xpath://label[text()='Funding Type']
    Wait Until Element Is Visible        xpath:(//button[text()='Next'])[1]    timeout=10s
    Execute Javascript   window.scrollTo(0,document.body.scrollHeight)
    Set Focus To Element                 xpath:(//button[text()='Next'])[1]
    Click Element                        xpath:(//button[text()='Next'])[1]

Credit norms details
    [Documentation]    Anchor credit norms details
    [Arguments]    ${vintageName}  ${vintageBusiness}  
    # Set Selenium Speed    0.4s
    Sleep    2
    Wait Until Element Is Visible       xpath://input[@id="VintagewithAnchor"]    timeout=10s
    Input Text       xpath://input[@id="VintagewithAnchor"]                         ${vintageName}
    Sleep    1
    Input Text       xpath://input[@id="MonthlyPurchasefromAnchor"]              ${vintageBusiness}
    sleep            1    
    Set Focus To Element         xpath://select[@id="promoterGuarantorPAN"]
    Select From List By Label    xpath://select[@id="promoterGuarantorPAN"]         ${selectOptionNR}
    Sleep            1
    Set Focus To Element         xpath://select[@id="promoterGuarantorOVD"]    
    Select From List By Label    xpath://select[@id="promoterGuarantorOVD"]         ${selectOptionReq}
    Sleep            1
    Set Focus To Element         xpath://select[@id="businessPAN"]    
    Select From List By Label    xpath://select[@id="businessPAN"]                  ${selectOptionReq}
    Sleep            1  
    Set Focus To Element         xpath://select[@id="gstCertificate"]    
    Select From List By Label    xpath://select[@id="gstCertificate"]               ${selectOptionNR}
    Sleep            1
    Wait Until Element Is Visible        xpath://button[contains(text(),'Save')]    timeout=10s
    Execute Javascript   window.scrollTo(0,document.body.scrollHeight)
    Sleep            2
    Set Focus To Element         xpath://button[contains(text(),'Save')]
    Click Element                xpath://button[contains(text(),'Save')]
    Sleep            2
    Click Element           xpath://button[contains(text(),'Next')][1]

Save credit norms details
    [Documentation]    Save anchor credit norms details
    Execute Javascript      window.scrollTo(0,document.body.scrollHeight)
    Sleep    1
    Click Element                    xpath://button[contains(text(),'Submit')]
    Wait Until Element Is Visible    xpath://textarea[@class="swal2-textarea"]
    Input Text                       xpath://textarea[@class="swal2-textarea"]    Approved
    Sleep    1
    Click Element                    xpath:(//button[contains(text(),'Submit')])[2]
    Sleep    3
    Log      Credit norms completed
    sleep    10
    Wait Until Element Is Visible    xpath://p[contains(text(),Exit)][1]    timeout=15s
    Mouse Over                       xpath://p[contains(text(),Exit)][1]
    Wait Until Element Is Visible   xpath://span[text()='Log Out']    timeout=15s
    Click Element   xpath://span[text()='Log Out']

Invalid credit norms details
    [Documentation]    Anchor credit norms details negative test
    Sleep    1
    Element Should Not Be Visible        xpath://div[text()=' Successfully Saved ']
    ${creditNormsStatus}=    Run Keyword And Return Status    Wait Until Element Is Visible    xpath://div[text()=' Check once whether fill all field ']
    Run Keyword If    '${creditNormsStatus}'=='True'    Log    Check once whether fill all Credit Norms field
    Element Should Be Enabled            xpath://button[text()='Save']
    # Reload Page  
    Log      Negative testing for Credit norms

Create credit norms
    [Documentation]    Anchor credit norms details
    Navigate to credit norms page
    Credit norms details  ${vintageName}  ${vintageBusiness}  
    Save credit norms details