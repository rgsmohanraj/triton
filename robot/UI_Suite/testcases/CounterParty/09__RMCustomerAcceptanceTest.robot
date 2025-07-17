*** Settings ***
Resource       ../../PageObjects/CounterPartyPageObjects/09__RMCustomerAcceptancePage.robot

*** Test Cases ***
Commercial CC Remarks
    [Documentation]      Counter party ninth stage
    ${caseName}=         Get Variable Value    ${cpName}
    Set Test Variable    ${caseName}
    09__RMCustomerAcceptancePage.From Business_lead assign to Business
    09__RMCustomerAcceptancePage.CommercialCC_Remarks