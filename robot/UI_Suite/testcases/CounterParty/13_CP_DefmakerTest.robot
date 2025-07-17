*** Settings ***
Resource    ../../PageObjects/loginPage.robot
Resource    ../../PageObjects/CounterPartyPageObjects/13_CP_DefmakerPage.robot

*** Test Cases ***
CP_Deferral Operation Maker Testcase
    [Documentation]    Deferral_Committee_Lead
    ${caseName}    Get Variable Value    ${cpName}
    Set Test Variable    ${caseName}
    13_CP_DefmakerPage.CP_Deferral Ops Maker