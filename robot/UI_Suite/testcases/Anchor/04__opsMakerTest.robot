*** Settings *** 
Resource     ../../PageObjects/loginPage.robot 
Resource     ../../PageObjects/AnchorPageObjects/04__opsMakerPage.robot

*** Test Cases ***
Test Beneficiary details and Document upload
    [Documentation]  Stage_5 Beneficiary details and  Document upload
    ${caseName}    Get Variable Value    ${anchorName}
    Set Test Variable    ${caseName}
    04__opsMakerPage.From Ops_maker_lead assign Ops_maker
    ${accNum}=        FakerLibrary.Credit Card Number
    Set Test Variable    ${accNum}
    04__opsMakerPage.Beneficiary details and Document upload      ${accNum}
