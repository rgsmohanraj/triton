*** Settings ***
Resource     ../../PageObjects/loginPage.robot
Resource     ../../PageObjects/CounterPartyPageObjects/14_CP_DefcheckerPage.robot

*** Test Cases ***
CP_Deferral Operation Checker
    [Documentation]    Deferral operation Maker
    ${caseName}    Get Variable Value    ${cpName}
    Set Test Variable    ${caseName}
    14_CP_DefcheckerPage.CP_Deferral ops checker