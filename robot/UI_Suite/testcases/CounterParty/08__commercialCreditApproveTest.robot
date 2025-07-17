*** Settings ***
Resource     ../../PageObjects/CounterPartyPageObjects/08__commercialCreditApprovePage.robot

*** Test Cases ***
Cp_Commercial/Credit_Stage
    [Documentation]     Counter party eighth stage
    ${caseName}=    Get Variable Value    ${cpName}
    Set Test Variable    ${caseName}
    08__commercialCreditApprovePage.CommercialCommitteeApproval
    08__commercialCreditApprovePage.CreditCommitteeApproval
