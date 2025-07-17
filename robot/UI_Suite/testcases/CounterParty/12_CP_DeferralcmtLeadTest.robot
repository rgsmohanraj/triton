*** Settings ***
Resource    ../../PageObjects/loginPage.robot
Resource    ../../PageObjects/CounterPartyPageObjects/12_CP_DefcmtLeadPage.robot

*** Test Cases ***
CP_Deferral Committee Lead Testcase
    [Documentation]    Deferral_Committee_Lead
    ${caseName}    Get Variable Value    ${cpName}
    Set Test Variable    ${caseName}
    12_CP_DefcmtLeadPage.CP Deferral Flow
