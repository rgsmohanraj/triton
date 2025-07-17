*** Settings ***  
Resource          ../keywords/common.robot    
Resource          ../PageObjects/loginPage.robot
Resource          ../PageObjects/CounterPartyPageObjects/01__businessPage.robot
Resource          ../PageObjects/CounterPartyPageObjects/02__underWriterPage.robot
Resource          ../PageObjects/CounterPartyPageObjects/03__creditCPAPage.robot
Resource          ../PageObjects/CounterPartyPageObjects/04__underWriterOverridePage.robot
Resource          ../PageObjects/CounterPartyPageObjects/05__camUploadPage.robot
Resource          ../PageObjects/CounterPartyPageObjects/06__underWriterReviewPage.robot
Resource          ../PageObjects/CounterPartyPageObjects/07__underWriterPDReviewPage.robot
Resource          ../PageObjects/CounterPartyPageObjects/08__commercialCreditApprovePage.robot
Resource          ../PageObjects/CounterPartyPageObjects/09__RMCustomerAcceptancePage.robot
Resource          ../PageObjects/CounterPartyPageObjects/10__CPOpsMakerPage.robot
Resource          ../PageObjects/CounterPartyPageObjects/11__CPOpsCheckerPage.robot
Resource          ../PageObjects/AnchorPageObjects/04__opsMakerPage.robot
Resource          ../PageObjects/AnchorPageObjects/05__opsCheckerPage.robot

*** Keywords ***
Counter party onboarding flow
    [Documentation]    Counter party onboarding into Triton
    ${anchorName}=    Set Variable    Thomas
    Set Test Variable    ${anchorName}
    # ${cpName}=  Set Variable      ArnoldWhite
    ${cpName}=    FakerLibrary.Company
    Set Test Variable    ${cpName}
    ${caseName}=    Get Variable Value    ${cpName}
    Set Test Variable    ${caseName}
    01__businessPage.Counterparty_business_stage
    02__underWriterPage.From credit_head_lead to credit_head
    02__underWriterPage.cp_underwriter
    03__creditCPAPage.From CPA lead to CPA
    03__creditCPAPage.CP_Credit CPA
    04__underWriterOverridePage.From Credit_head_lead to Risk_Underwriter
    04__underWriterOverridePage.Soft Policy Check
    02__creditNormsPage.From CPA lead assign to CPA
    05__camUploadPage.Cam Upload
    06__underWriterReviewPage.From_Credit head lead_assigning to_Credit head
    06__underWriterReviewPage.Underwriter Review
    07__underWriterPDReviewPage.From credit_head_lead to risk_underwriter
    07__underWriterPDReviewPage.cp_underwriter_pd_review_stage7
    08__commercialCreditApprovePage.Assign to credit_head or risk_head
    08__commercialCreditApprovePage.CommercialCreditApproval
    09__RMCustomerAcceptancePage.From Business lead assign to business
    09__RMCustomerAcceptancePage.CommercialCC_Remarks
    04__opsMakerPage.From Ops_maker_lead assign Ops_maker
    10__CPOpsMakerPage.DocumentUpload
    05__opsCheckerPage.From Ops_checker_lead assign Ops_checker
    11__CPOpsCheckerPage.Ops Checker Approval

*** Test Cases ***
Test Counter party flow
    [Documentation]    Test Anchor Onboarding flow
    Counter party onboarding flow
    Log    Counter party onboarded into Triton
    