*** Settings ***  
Resource    ../../PageObjects/CounterPartyPageObjects/01__businessPage.robot

*** Test Cases ***
Test_CP_business_in_UI
    [Documentation]    counter party business stage 
    Set Selenium Speed    0.3s
    ${caseName}=    Get Variable Value    ${cpName}
    Set Test Variable    ${caseName}
    01__businessPage.Counterparty_business_stage
