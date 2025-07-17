*** Settings ***
Resource     ../../PageObjects/loginPage.robot
Resource     ../../PageObjects/AnchorPageObjects/09_Renewal_Flow.robot
Resource     ../../PageObjects/AnchorPageObjects/02__creditNormsPage.robot
Resource     ../../PageObjects/AnchorPageObjects/03__creditSubCommitteePage.robot
Resource     ../../PageObjects/AnchorPageObjects/04__opsMakerPage.robot
Resource     ../../PageObjects/AnchorPageObjects/05__opsCheckerPage.robot
Resource     ../../PageObjects/AnchorPageObjects/06_DeferralCommitteePage.robot
Resource     ../../PageObjects/AnchorPageObjects/07_Deferral_OpsMakerPage.robot
Resource     ../../PageObjects/AnchorPageObjects/08_Deferral_OpsCheckerPage.robot

*** Test Cases ***

Renewal
       ${caseName}    Get Variable Value    ${anchorName}
       Set Test Variable    ${caseName}
       09_Renewal_Flow.renewal_fLow

       02__creditNormsPage.Credit norms details  ${vintageName}  ${vintageBusiness} 
       02__creditNormsPage.Save credit norms details

       
       ${userName}=     Set Variable    ${creditUWLeadUser}
       ${password}=     Set Variable    ${creditUWLeadPw}
       loginPage.login to Triton        ${userName}    ${password}
       03__creditSubCommitteePage.Credit subcommitee approval  
       ${caseName}    Get Variable Value    ${anchorName}
       Set Test Variable    ${caseName}

       ${userName}=     Set Variable    ${opsMakerLeadUser}
       ${password}=     Set Variable    ${opsMakerLeadPw}
       loginPage.login to Triton        ${userName}    ${password}
       ${accNum}=        FakerLibrary.Credit Card Number
       Set Test Variable    ${accNum}
       04__opsMakerPage.Beneficiary details and Document upload      ${accNum}

       ${userName}=     Set Variable    ${opsChkLeadUser}
       ${password}=     Set Variable    ${opsChkLeadPw}
       loginPage.login to Triton        ${userName}    ${password}
       ${caseName}        Get Variable Value    ${anchorName}
       Set Test Variable  ${caseName}
       05__opsCheckerPage.Ops_checker Approval

       ${userName}=     Set Variable    ${deferralCommitLeadUser}
       ${password}=     Set Variable    ${deferralCommitLeadPw}
       login to Triton    ${userName}    ${password}
       ${caseName}    Get Variable Value    ${anchorName}
       Set Test Variable    ${caseName}
       06_DeferralCommitteePage.Deferral_Basic_Details
       06_DeferralCommitteePage.Deferral Approved ETA
       06_DeferralCommitteePage.Deferral Remarks

       ${userName}=     Set Variable    ${opsMakerLeadUser}
       ${password}=     Set Variable    ${opsMakerLeadPw}
       login to Triton    ${userName}    ${password}
       ${caseName}    Get Variable Value    ${anchorName}
       Set Test Variable    ${caseName}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
       07_Deferral_OpsMakerPage.Def_Ops_Maker Basicdetails
       07_Deferral_OpsMakerPage.Deferral_Operation_Maker
       07_Deferral_OpsMakerPage.Deferral_Maker_Remarks

       ${caseName}    Get Variable Value    ${anchorName}
       Set Test Variable    ${caseName}
       08_Deferral_OpsCheckerPage.Deferral ops checker
