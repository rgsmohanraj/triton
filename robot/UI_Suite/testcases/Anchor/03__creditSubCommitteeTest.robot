*** Settings ***  
Resource        ../../PageObjects/assignmentPage.robot
Resource        ../../PageObjects/AnchorPageObjects/03__creditSubCommitteePage.robot
*** Test Cases ***
Test credit sub commitee approval
    [Documentation]    Anchor credit subcommitee test
    ${caseName}    Get Variable Value    ${anchorName}
    Set Test Variable    ${caseName}
    03__creditSubCommitteePage.From Credit Underwriter lead assign
    03__creditSubCommitteePage.Credit subcommitee approval