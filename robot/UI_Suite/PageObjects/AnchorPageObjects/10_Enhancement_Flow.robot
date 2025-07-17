*** Settings ***
Resource          ../../keywords/common.robot    
Resource          ../../PageObjects/assignmentPage.robot

*** Keywords ***

Enhancement_FLow
    [Documentation]    Assign case to CPA
       # Set Selenium Speed    0.4s
    loginPage.Open triton in browser
    loginPage.login to Triton        ${userName}    ${password}     
    Wait Until Element Is Visible     xpath://h5[contains(text(),'Anchor List')]      timeout=10s
    Sleep    2
    Execute JavaScript    var xpath = "//td[contains(text(),'${anchorName}')]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);        
    Wait Until Element Is Visible     xpath://td[contains(text(),'${anchorName}')]    timeout=10s
    Sleep    2
    Click Element                     xpath://td[contains(text(),'${anchorName}')]
    Sleep    1
    Click Element                     xpath://div[contains(text(),'Renewal/Enhancement ')]
    Sleep    2
    Click Element                     xpath://input[@value="enhancement"]              
    Sleep    1
    Click Element                     xpath://button[contains(text(),'Submit')]
    Sleep    1
    Wait Until Element Is Visible        xpath://button[contains(text(),'Save')]    timeout=10s
    Execute Javascript   window.scrollTo(0,document.body.scrollHeight)
    Sleep    2
    Set Focus To Element                 xpath://button[contains(text(),'Save')]
    Click Element                        xpath://button[contains(text(),'Save')]
    Sleep    2
    Set Focus To Element                 xpath:(//button[text()='Next'])[1]
    Click Element                        xpath:(//button[text()='Next'])[1]
    Wait Until Page Contains Element     xpath://span[text()='Anchor Details']
    Wait Until Element Is Visible        xpath://button[contains(text(),'Save')]    timeout=10s
    Execute Javascript   window.scrollTo(0,document.body.scrollHeight)
    Sleep    2
    Set Focus To Element                 xpath://button[contains(text(),'Save')]
    Click Element                        xpath://button[contains(text(),'Save')]
    Sleep    2
    Click Element                        xpath:(//button[text()='Next'])[1]
    Wait Until Page Contains Element     xpath://label[text()='Funding Type']
    Wait Until Element Is Visible        xpath://button[contains(text(),'Save')]   timeout=10s
    Execute Javascript   window.scrollTo(0,document.body.scrollHeight)
    Sleep    2
    Set Focus To Element                 xpath://button[contains(text(),'Save')]
    Click Element                        xpath://button[contains(text(),'Save')]
    Sleep    2
    Click Element                        xpath:(//button[text()='Next'])[1]
    Sleep    3
        
   
    
