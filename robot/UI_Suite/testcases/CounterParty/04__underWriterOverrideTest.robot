*** Settings ***

Resource    ../../PageObjects/CounterPartyPageObjects/04__underWriterOverridePage.robot

*** Test Cases ***

TC_01_Underwriter Override
    [Documentation]    Stage_4 UnderWriter Override
    Set Selenium Speed    1s
     ${caseName}=    Get Variable Value    ${cpName}
    Set Test Variable    ${caseName}
    04__underWriterOverridePage.From Credit_Underwriter_lead to Credit_Underwriter or Credit_Underwriter_lead to Credit_Underwriter
    04__underWriterOverridePage.Soft Policy Check

     