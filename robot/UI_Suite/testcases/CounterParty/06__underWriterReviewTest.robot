*** Settings ***
Resource        ../../keywords/common.robot
Resource        ../../PageObjects/loginPage.robot
Resource        ../../PageObjects/CounterPartyPageObjects/06__underWriterReviewPage.robot


*** Test Cases ***
TC for UnderwriterReview
    [Documentation]    UnderwriterReview    
    ${caseName}=    Get Variable Value    ${cpName}        
    Set Test Variable    ${caseName}        
    06__underWriterReviewPage.Underwriter Review   
    
