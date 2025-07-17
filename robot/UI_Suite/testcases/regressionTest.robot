*** Settings ***  
Resource          ../keywords/common.robot
Resource          ../PageObjects/AnchorPageObjects/01__anchorDetailsExcel.robot
Resource          ../PageObjects/AnchorPageObjects/01__anchorFileUploadPage.robot
Resource          ../PageObjects/AnchorPageObjects/02__creditNormsPage.robot
Resource          ../PageObjects/AnchorPageObjects/03__creditSubCommitteePage.robot
Resource          ../PageObjects/AnchorPageObjects/04__opsMakerPage.robot
Resource          ../PageObjects/AnchorPageObjects/05__opsCheckerPage.robot
Resource          ../PageObjects/AnchorPageObjects/06_DeferralCommitteePage.robot
Resource          ../PageObjects/AnchorPageObjects/07_Deferral_OpsMakerPage.robot
Resource          ../PageObjects/AnchorPageObjects/08_Deferral_OpsCheckerPage.robot
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
Resource          ../PageObjects/CounterPartyPageObjects/12_CP_DefcmtLeadPage.robot
Resource          ../PageObjects/CounterPartyPageObjects/13_CP_DefmakerPage.robot
Resource          ../PageObjects/CounterPartyPageObjects/14_CP_DefcheckerPage.robot



*** Keywords ***
Anchor onboarding flow
    [Documentation]    Anchor onboarding into Triton
    loginPage.Open browser and login to triton
    01__anchorDetailsExcel.Generate Anchor name,PAN and CIN Numbers
    Open Workbook    ${CURDIR}\\..\\PageObjects\\AnchorPageObjects\\Anchor File Upload.xlsx
    ${anchorName}    Read From Cell    A2   
    ${pan}           Read From Cell    B2   
    ${cin}           Read From Cell    C2  
    Close Workbook
    # ${anchorName}=  Set Variable   David Ltd
    Set Test Variable    ${anchorName}
    ${caseName}=    Get Variable Value    ${anchorName}
    Set Test Variable    ${caseName}
    01__anchorFileUploadPage.Anchor Details File Upload   ${anchorName}   ${pan}   ${cin}
    02__creditNormsPage.From CPA lead assign to CPA
    02__creditNormsPage.Create credit norms    
    03__creditSubCommitteePage.From Credit Underwriter lead assign
    03__creditSubCommitteePage.Credit subcommitee approval
    04__opsMakerPage.From Ops_maker_lead assign Ops_maker
     ${accNum}=        FakerLibrary.Credit Card Number
    Set Test Variable    ${accNum}
    04__opsMakerPage.Beneficiary details and Document upload      ${accNum}
    05__opsCheckerPage.From Ops_checker_lead assign Ops_checker
    05__opsCheckerPage.Ops_checker Approval
    06_DeferralCommitteePage.Deferral_Committee_Lead
    07_Deferral_OpsMakerPage.Deferral ops maker
    08_Deferral_OpsCheckerPage.Deferral ops checker
    Log    Anchor onboarded into Triton

Counter party onboarding flow
    [Documentation]    Counter party onboarding into Triton
    # ${anchorName}=    Set Variable    Jessica Ltd
    # Set Test Variable    ${anchorName}
    # ${cpName}=  Set Variable      Shelby Hinton Ltd
    # ${cpFName}=    FakerLibrary.Name
    # ${cpName}=     Catenate    ${cpFName} Ltd
    Set Test Variable    ${cpName}
    ${caseName}=    Get Variable Value    ${cpName}
    Set Test Variable    ${caseName}
    # 01__businessPage.Counterparty_business_stage
    # 02__underWriterPage.From credit_head_lead to credit_head
    # 02__underWriterPage.cp_underwriter
    # 03__creditCPAPage.From CPA lead to CPA    
    # 03__creditCPAPage.CP_Credit CPA    
    04__underWriterOverridePage.From Credit_Underwriter_lead to Credit_Underwriter or Credit_Underwriter_lead to Credit_Underwriter
    04__underWriterOverridePage.Soft Policy Check    
    02__creditNormsPage.From CPA lead assign to CPA
    05__camUploadPage.Cam Upload
    06__underWriterReviewPage.Underwriter Review
    07__underWriterPDReviewPage.To credit underwriter or risk underwriter lead
    07__underWriterPDReviewPage.cp_underwriter_pd_review_stage7
    08__commercialCreditApprovePage.CommercialCommitteeApproval
    08__commercialCreditApprovePage.CreditCommitteeApproval
    09__RMCustomerAcceptancePage.From Business_lead assign to Business
    09__RMCustomerAcceptancePage.CommercialCC_Remarks
    04__opsMakerPage.From Ops_maker_lead assign Ops_maker 
    ${accNum}=     FakerLibrary.Credit Card Number   
    10__CPOpsMakerPage.Cp_Beneficiary Details & DocumentUpload    ${accNum} 
    # 05__opsCheckerPage.From Ops_checker_lead assign Ops_checker
    11__CpOpsCheckerPage.Ops Checker Approval
    12_CP_DefcmtLeadPage.CP Deferral Flow
    13_CP_DefmakerPage.CP_Deferral Ops Maker
    14_CP_DefcheckerPage.CP_Deferral ops checker
    Log    Counter party onboarded into Triton
*** Test Cases ***
Triton Regression test
    [Documentation]    Regression test for Triton
    # Anchor onboarding flow
    Counter party onboarding flow
    Log   Anchor and Counter party onboarded into Triton
    

    
