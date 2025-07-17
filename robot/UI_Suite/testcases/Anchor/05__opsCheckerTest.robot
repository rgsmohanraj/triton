*** Settings ***  
Resource    ../../PageObjects/AnchorPageObjects/05__opsCheckerPage.robot

*** Test Cases ***
Test Ops checker approval
    [Documentation]    Operation checker approval stage
    ${caseName}        Get Variable Value    ${anchorName}
    Set Test Variable  ${caseName}
    05__opsCheckerPage.From Ops_checker_lead assign Ops_checker
    05__opsCheckerPage.Ops_checker Approval
