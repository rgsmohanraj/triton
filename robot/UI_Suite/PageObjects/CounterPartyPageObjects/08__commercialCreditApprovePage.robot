*** Settings ***
Resource         ../../keywords/common.robot
Resource         ../../PageObjects/assignmentPage.robot
Resource         ../../PageObjects/CounterPartyPageObjects/01__businessPage.robot
 
*** Keywords ***
CommercialCommitteeApproval
    Set Selenium Speed    0.2s
    [Documentation]     Counter party eighth stage assign to credit head
    ${userName}=     Set Variable    ${commercialCommitUser}
    ${password}=     Set Variable    ${commercialPw}
    loginPage.Open triton in browser
    loginPage.login to Triton    ${userName}    ${password}
    #Anchor Select
    Wait Until Element Is Visible         xpath://span[text()='Inbox']                   timeout=15s
    Set Focus To Element                  xpath://span[text()='Inbox']
    Click Element                         xpath://span[text()='Inbox']
    Sleep    2
    Run Keyword    Select the case in Inbox                            
    #Basic Details
    Wait Until Page Contains Element      xpath:(//span[contains(text(),'CP Details')])[8]     timeout=10s
     #cp details
        Sleep    2
    ${Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[5]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    5
    IF  '${Next}'=='True'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[5]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[5]
    Click Element                          xpath:(//button[contains(text(),'Next')])[5]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[6]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    3 
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[6]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[6]
    Click Element                          xpath:(//button[contains(text(),'Next')])[6]
    END
    Sleep    3
    Wait Until Page Contains Element      xpath:(//span[contains(text(),'Commercial CC approval')])[1]     timeout=10s
     #cam details
        Sleep    2
    ${Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[5]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${Next}'=='True'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[5]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[5]
    Click Element                          xpath:(//button[contains(text(),'Next')])[5]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[6]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[6]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[6]
    Click Element                          xpath:(//button[contains(text(),'Next')])[6]
    END
    Sleep    2
    #Commercial CC Approval
    Sleep        2
    Execute Javascript                   window.scrollTo(0,document.body.scrollHeight)
    Wait Until Element Is Visible         xpath:(//button[text()='Submit'])[1]           timeout=10s
    Click Element                         xpath:(//button[text()='Submit'])[1]
    Input Text                            xpath://textarea[@class='swal2-textarea']      Approved  
    Wait Until Element Is Visible         xpath:(//button[text()='Submit'])[2]           timeout=10s
    Click Element                         xpath:(//button[text()='Submit'])[2]
    Sleep        3
    loginPage.Logout from Triton

CreditCommitteeApproval
    Set Selenium Speed    0.2s
    [Documentation]     Credit committee approval
    ${userName}=     Set Variable    ${creditCommitUser}
    ${password}=     Set Variable    ${creditPw}
    #  loginPage.Open triton in browser
    loginPage.login to Triton    ${userName}    ${password}
    #Anchor Select
    Wait Until Element Is Visible         xpath://span[text()='Inbox']                   timeout=15s
    Set Focus To Element                  xpath://span[text()='Inbox']
    Click Element                         xpath://span[text()='Inbox']
    Sleep    2
    Run Keyword    Select the case in Inbox                            
     #Basic Details
    Wait Until Page Contains Element      xpath:(//span[contains(text(),'CP Details')])[8]     timeout=10s
     #cp details
        Sleep    2
    ${Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[5]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    3
    IF  '${Next}'=='True'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[5]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[5]
    Click Element                          xpath:(//button[contains(text(),'Next')])[5]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[6]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[6]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[6]
    Click Element                          xpath:(//button[contains(text(),'Next')])[6]
    END
    Sleep    2
    Wait Until Page Contains Element      xpath:(//span[contains(text(),'Credit CC approval')])[1]     timeout=10s
     #cam details
        Sleep    2
    ${Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[5]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${Next}'=='True'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[5]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[5]
    Click Element                          xpath:(//button[contains(text(),'Next')])[5]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[6]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[6]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[6]
    Click Element                          xpath:(//button[contains(text(),'Next')])[6]
    END
    Sleep    2
    #Commercial CC Approval
    Sleep        2
    Execute Javascript                   window.scrollTo(0,document.body.scrollHeight)
    Wait Until Element Is Visible         xpath:(//button[text()='Submit'])[1]           timeout=10s
    Click Element                         xpath:(//button[text()='Submit'])[1]
    Input Text                            xpath://textarea[@class='swal2-textarea']      Approved  
    Wait Until Element Is Visible         xpath:(//button[text()='Submit'])[2]           timeout=10s
    Click Element                         xpath:(//button[text()='Submit'])[2]
    Sleep      3
    loginPage.Logout from Triton

    
