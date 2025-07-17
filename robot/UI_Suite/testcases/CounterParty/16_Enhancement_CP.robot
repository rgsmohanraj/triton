*** Settings ***  
Resource    ../../PageObjects/CounterPartyPageObjects/01__businessPage.robot
Resource    ../../PageObjects/CounterPartyPageObjects/02__underWriterPage.robot
Resource    ../../PageObjects/CounterPartyPageObjects/03__creditCPAPage.robot
Resource    ../../PageObjects/CounterPartyPageObjects/04__underWriterOverridePage.robot
Resource    ../../PageObjects/CounterPartyPageObjects/05__camUploadPage.robot
Resource    ../../PageObjects/CounterPartyPageObjects/06__underWriterReviewPage.robot
Resource    ../../PageObjects/CounterPartyPageObjects/07__underWriterPDReviewPage.robot
Resource    ../../PageObjects/CounterPartyPageObjects/08__commercialCreditApprovePage.robot
Resource    ../../PageObjects/CounterPartyPageObjects/09__RMCustomerAcceptancePage.robot
Resource    ../../PageObjects/CounterPartyPageObjects/10__CPOpsMakerPage.robot
Resource    ../../PageObjects/CounterPartyPageObjects/11__CPOpsCheckerPage.robot
Resource    ../../PageObjects/CounterPartyPageObjects/12_CP_DefcmtLeadPage.robot
Resource    ../../PageObjects/CounterPartyPageObjects/13_CP_DefmakerPage.robot
Resource    ../../PageObjects/CounterPartyPageObjects/14_CP_DefcheckerPage.robot
Resource    ../../PageObjects/CounterPartyPageObjects/16_Enhancement_PO_CP.robot

*** Test Cases ***

CP_Enhance_FLow
    
        16_Enhancement_PO_CP.Enhancement_Flow_CP
    
        ${caseName}    Get Variable Value    ${cpName}
        Set Test Variable    ${caseName}
        02__underWriterPage.From credit_head_lead to credit_head
        02__underWriterPage.cp_underwriter
        
        ${userName}=     Set Variable    ${cpaLeadUser}
        ${password}=     Set Variable    ${cpaLeadPw}
        loginPage.login to Triton        ${userName}    ${password}
        ${caseName}=    Get Variable Value    ${cpName}
        Set Test Variable    ${caseName}
        03__creditCPAPage.CP_Credit CPA

        ${caseName}=    Get Variable Value    ${cpName}
        Set Test Variable    ${caseName}
        04__underWriterOverridePage.From Credit_Underwriter_lead to Credit_Underwriter or Credit_Underwriter_lead to Credit_Underwriter
        04__underWriterOverridePage.Soft Policy Check

        ${caseName}=    Get Variable Value    ${cpName}
        Set Test Variable    ${caseName}
        02__creditNormsPage.From CPA lead assign to CPA
        05__camUploadPage.Cam Upload 

        ${caseName}=    Get Variable Value    ${cpName}        
        Set Test Variable    ${caseName}        
        06__underWriterReviewPage.Underwriter Review  

        ${caseName}    Get Variable Value    ${cpName}
        Set Test Variable    ${caseName}
        07__underWriterPDReviewPage.To credit underwriter or risk underwriter lead
        07__underWriterPDReviewPage.cp_underwriter_pd_review_stage7

        ${caseName}=    Get Variable Value    ${cpName}
        Set Test Variable    ${caseName}
        08__commercialCreditApprovePage.CommercialCommitteeApproval
        08__commercialCreditApprovePage.CreditCommitteeApproval

         ${caseName}=         Get Variable Value    ${cpName}
        Set Test Variable    ${caseName}
        09__RMCustomerAcceptancePage.CommercialCC_Remarks

        ${caseName}=         Get Variable Value    ${cpName}
        Set Test Variable    ${caseName}
        ${accNum}=           FakerLibrary.Credit Card Number
        10__CPOpsMakerPage.Cp_Beneficiary Details & DocumentUpload    ${accNum}

        ${caseName}=          Get Variable Value    ${cpName}
        Set Test Variable     ${caseName}
        11__CpOpsCheckerPage.Ops Checker Approval

        ${caseName}    Get Variable Value    ${anchorName}
        Set Test Variable    ${caseName}
        12_CP_DefcmtLeadPage.CP Deferral Flow

        ${caseName}    Get Variable Value    ${anchorName}
        Set Test Variable    ${caseName}
        13_CP_DefmakerPage.CP_Deferral Ops Maker

        ${caseName}    Get Variable Value    ${anchorName}
        Set Test Variable    ${caseName}
        14_CP_DefcheckerPage.CP_Deferral ops checker