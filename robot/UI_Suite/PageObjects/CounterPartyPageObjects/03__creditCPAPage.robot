*** Settings ***
Resource    ../../keywords/common.robot
Resource    ../../PageObjects/assignmentPage.robot

*** Keywords ***
From CPA lead to CPA
    [Documentation]    Assigning CPA by CPA Lead
    ${userName}=    Set Variable    ${cpaLeadUser}
    ${password}=    Set Variable    ${cpaLeadPw}
    ${userSelect}=  Set Variable    CPA
    assignmentPage.Search the case    ${userName}    ${password}    ${userSelect}    ${caseName}
    ${userName}=    Set Variable    ${cpaUser}
    ${password}=    Set Variable    ${cpaPw}
   
    # loginPage.Open triton in browser
    loginPage.login to Triton   ${userName}     ${password}

CP_Credit CPA
    [Documentation]    Creating Soft policy
    Set Selenium Speed    0.5s
    Wait Until Page Contains Element       xpath://span[contains(text(),'Inbox')]     timeout=15s
    Set Focus To Element                   xpath://span[contains(text(),'Inbox')]
    Click Element                          xpath://span[contains(text(),'Inbox')]
    Run Keyword    Select the case in Inbox
    #CP Details
    Wait Until Page Contains Element   xpath:(//span[contains(text(),'CP Details')])[2]              timeout=15s
    Sleep    2
    ${cr3Next}=     Run Keyword And Return Status    Execute JavaScript        var xpath = "(//button[contains(text(),'Next')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${cr3Next}' == 'True'
     Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[1]        timeout=10s
     Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[1]
     Sleep    2
     Click Element                          xpath:(//button[contains(text(),'Next')])[1]
    ELSE
     Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[2]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
     Sleep    2  
     Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[2]       timeout=10s
     Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[2]
     Click Element                          xpath:(//button[contains(text(),'Next')])[2]
    END
    Sleep    2
    #Proposal Table
    Sleep    2
    ${cr2Next}=     Run Keyword And Return Status    Execute JavaScript        var xpath = "(//button[contains(text(),'Next')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${cr2Next}' == 'True'
     Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[1]        timeout=10s
     Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[1]
     Sleep    2
     Click Element                          xpath:(//button[contains(text(),'Next')])[1]
    ELSE
     Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[2]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
     Sleep    2  
     Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[2]       timeout=10s
     Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[2]
     Click Element                          xpath:(//button[contains(text(),'Next')])[2]
    END
    Sleep    2
    #Document Checklist
    # Wait Until Page Contains Element    xpath://*[text()='Provisional Financials']        timeout=10s
    FOR  ${i}  IN RANGE    1    3
        Set Focus To Element            xpath:(//*[@type='button'])[${i}]
        Click Element                   xpath:(//*[@type='button'])[${i}]
    END
    Sleep    2
    ${cr1Next}=     Run Keyword And Return Status    Execute JavaScript        var xpath = "(//button[contains(text(),'Next')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${cr1Next}' == 'True'
     Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[1]        timeout=10s
     Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[1]
     Sleep    2
     Click Element                          xpath:(//button[contains(text(),'Next')])[1]
    ELSE
     Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[2]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
     Sleep    2  
     Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[2]       timeout=10s
     Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[2]
     Click Element                          xpath:(//button[contains(text(),'Next')])[2]
    END
    Sleep    2
    Set Focus To Element           xpath://input[@id="comCurrentOD"]      
    Input Text                     xpath://input[@id="comCurrentOD"]                   50000
    Set Focus To Element           xpath://input[@id="comSDLR"]                                      #Commercial Bureau
    Input Text                     xpath://input[@id="comSDLR"]                        0
    Set Focus To Element           xpath://input[@id="comWilfulOrSuitFiled"]
    Input Text                     xpath://input[@id="comWilfulOrSuitFiled"]           0
    Set Focus To Element           xpath://input[@id="comCurrentODForCreditCard"]
    Input Text                     xpath://input[@id="comCurrentODForCreditCard"]      50000
    Set Focus To Element           xpath://input[@id="smaA"]
    Input Text                     xpath://input[@id="smaA"]                           10
    Set Focus To Element           xpath://input[@id="smaC"]
    Input Text                     xpath://input[@id="smaC"]                           1
    Set Focus To Element           xpath://input[@id="comBureauScore"]
    Input Text                     xpath://input[@id="comBureauScore"]                 6
    Set Focus To Element           xpath://input[@id="smaD"]
    Input Text                     xpath://input[@id="smaD"]                           2
    Set Focus To Element           xpath://input[@id="smaB"]
    Input Text                     xpath://input[@id="smaB"]                           4
    Sleep                          2
    Set Focus To Element           xpath://input[@id="ConsuSDLR"]
    Input Text                     xpath://input[@id="ConsuSDLR"]                      0
    Set Focus To Element           xpath://input[@id="consuWilfulOrSuitFiled"]                        #Consumer Bureau For Primary Promoter
    Input Text                     xpath://input[@id="consuWilfulOrSuitFiled"]         0
    Set Focus To Element           xpath://input[@id="ConsuBureauScore"]
    Input Text                     xpath://input[@id="ConsuBureauScore"]               650
    Set Focus To Element           xpath://input[@id="consuCurrentOD"]
    Input Text                     xpath://input[@id="consuCurrentOD"]                 50000
    Set Focus To Element           xpath://input[@id="consuCurrentODForCredit"]
    Input Text                     xpath://input[@id="consuCurrentODForCredit"]        50000
    Sleep    2  
    Set Focus To Element           xpath://input[@id="latestGSTPaymentDelayInMonths"]
    Input Text                     xpath://input[@id="latestGSTPaymentDelayInMonths"]   3  
    Set Focus To Element           xpath://input[@id="latestEFODelayInMonths"]                 #Statutory Defaults
    Input Text                     xpath://input[@id="latestEFODelayInMonths"]          6
    Set Focus To Element           xpath://input[@id="ageOfMainPromoterInYears"]
    Input Text                     xpath://input[@id="ageOfMainPromoterInYears"]        25
    Wait Until Page Contains Element       xpath://*[text()='Run Soft Policy']
    Set Focus To Element           xpath://button[contains(text(),'Run Soft Policy')]
    Click Element                  xpath://button[contains(text(),'Run Soft Policy')]
    Sleep    2
    ${crNext}=     Run Keyword And Return Status    Execute JavaScript        var xpath = "(//button[contains(text(),'Next')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${crNext}' == 'True'
     Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[1]        timeout=10s
     Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[1]
     Sleep    2
     Click Element                          xpath:(//button[contains(text(),'Next')])[1]
    ELSE
     Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[2]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
     Sleep    2  
     Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[2]       timeout=10s
     Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[2]
     Click Element                          xpath:(//button[contains(text(),'Next')])[2]
    END
    Sleep    2
    Wait Until Page Contains Element     xpath:(//*[@type='button'])[1]
    Set Focus To Element           xpath:(//*[@type='button'])[1]
    Click Button                   xpath:(//*[@type='button'])[1]
    Input Text                     xpath://*[@class='swal2-textarea']         Approved
    Wait Until Page Contains Element    xpath:(//*[text()='Submit'])[2]    timeout=15s
    Set Focus To Element           xpath:(//*[text()='Submit'])[2]
    Click Button                   xpath:(//*[text()='Submit'])[2]
    Sleep    2
    Logout from Triton

My Keyword
    [Arguments]    ${argument}    ${val}
    ${response} =        CP_Credit CPA    ${argument}
    Should Match    ${response}    ${val}  