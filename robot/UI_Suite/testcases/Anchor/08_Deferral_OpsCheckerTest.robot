*** Settings ***
Resource     ../../PageObjects/loginPage.robot
Resource     ../../PageObjects/AnchorPageObjects/08_Deferral_OpsCheckerPage.robot


*** Test Cases ***
Deferral Operation Checker
    [Documentation]    Deferral operation Maker
    ${caseName}    Get Variable Value    ${anchorName}
    Set Test Variable    ${caseName}
    08_Deferral_OpsCheckerPage.Deferral ops checker