*** Settings ***
Resource           ../../keywords/common.robot
Resource     ../../PageObjects/loginPage.robot

*** Keywords ***
CP Deferral Flow
    Def_Login
    CP Basic Details
    Deferral Approved ETA
    Deferral Remarks
Def_Login
    [Documentation]    Login User
    Open triton in browser  
    ${userName}=     Set Variable    ${deferralCommitLeadUser}
    ${password}=     Set Variable    ${deferralCommitLeadPw} 
    login to Triton    ${userName}    ${password} 
CP Basic Details 
    [Documentation]    Cp basic details
    Wait Until Element Is Visible        xpath://span[text()='Inbox']    timeout=10s
    Click Element                        xpath://span[text()='Inbox']
    Sleep    3
    Execute Javascript                  window.scrollTo(0,document.body.scrollHeight)
    Wait Until Page Contains Element        xpath://td[contains(text(),'${caseName}')]    timeout=20s
    Execute Javascript                  window.scrollTo(0,document.body.scrollHeight)
    Set Focus To Element    xpath://td[contains(text(),'${caseName}')] 
    Sleep    1
    Click Element         xpath://td[contains(text(),'${caseName}')] 
    Sleep    3
    Wait Until Page Contains Element      xpath:(//span[contains(text(),'Basic Details')])     timeout=10s
     #cp details
    Sleep    2
    ${Next}=     Run Keyword And Return Status    Execute JavaScript          var xpath = "(//button[contains(text(),'Next')])[6]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${Next}'=='False'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[6]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[6]
    Click Element                          xpath:(//button[contains(text(),'Next')])[6]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[7]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    3  
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[7]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[7]
    Click Element                          xpath:(//button[contains(text(),'Next')])[7]
    END
    Sleep    2
Deferral Approved ETA
    [Documentation]    Deferral Approved ETA
    Click Button    xpath:(//button[text()='Approve'])[1]
    Sleep    2
    ${Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[6]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${Next}'=='False'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[6]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[6]
    Click Element                          xpath:(//button[contains(text(),'Next')])[6]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[7]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[7]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[7]
    Click Element                          xpath:(//button[contains(text(),'Next')])[7]
    END
    Sleep    2
Deferral Remarks
    [Documentation]   Deferral Remarks
     Execute Javascript    window.scrollTo(0,document.body.scrollHeight)
    Sleep    2
    Click Button    xpath://button[text()='Submit']
    Input Text      xpath://textarea[@class='swal2-textarea']    okay
    Sleep    1
    Click Button    xpath:(//button[text()='Submit'])[2]
    Sleep    6
    Logout from Triton

