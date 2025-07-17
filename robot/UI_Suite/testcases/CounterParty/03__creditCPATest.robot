*** Settings ***
Resource    ../../PageObjects/CounterPartyPageObjects/03__creditCPAPage.robot

*** Test Cases ***
TC_01_Credit CPA
    [Documentation]    Stage_3 Credit CPA flow
    ${caseName}=    Get Variable Value    ${cpName}
    Set Test Variable    ${caseName}
    03__creditCPAPage.From CPA lead to CPA
    03__creditCPAPage.CP_Credit CPA
  