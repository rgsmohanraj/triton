*** Settings ***
Resource       ../../PageObjects/CounterPartyPageObjects/11__CPOpsCheckerPage.robot
Resource       ../../PageObjects/AnchorPageObjects/05__opsCheckerPage.robot

*** Test Cases ***
Operation_checker_lead
    [Documentation]       Counter party 11th stage
    ${caseName}=          Get Variable Value    ${cpName}
    Set Test Variable     ${caseName}
    11__CpOpsCheckerPage.Ops Checker Approval