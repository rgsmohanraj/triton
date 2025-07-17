*** Settings ***  
Resource        ../../PageObjects/assignmentPage.robot
Resource        ../../PageObjects/AnchorPageObjects/02__creditNormsPage.robot

*** Test Cases ***
Test credit norms
    [Documentation]    Anchor credit norms test
    ${caseName}    Get Variable Value    ${anchorName}
    Set Test Variable    ${caseName}
    02__creditNormsPage.From CPA lead assign to CPA
    02__creditNormsPage.Create credit norms  
    
    