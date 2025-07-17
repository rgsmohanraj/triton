*** Settings ***
Resource    ../../PageObjects/loginPage.robot
Resource    ../../PageObjects/AnchorPageObjects/06_DeferralCommitteePage.robot

*** Test Cases ***
Deferral_Committee_Lead
    [Documentation]    Deferral_Committee_Lead
    ${caseName}    Get Variable Value    ${anchorName}
    Set Test Variable    ${caseName}
    06_DeferralCommitteePage.Deferral_Committee_Lead

