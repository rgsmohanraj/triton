*** Settings ***  
Resource          ../keywords/common.robot    
Resource          ../PageObjects/loginPage.robot
Resource          ../PageObjects/AnchorPageObjects/01__anchorDetailsExcel.robot
Resource          ../PageObjects/AnchorPageObjects/01__anchorFileUploadPage.robot
Resource          ../PageObjects/AnchorPageObjects/02__creditNormsPage.robot
Resource          ../PageObjects/AnchorPageObjects/03__creditSubCommitteePage.robot
Resource          ../PageObjects/AnchorPageObjects/04__opsMakerPage.robot
Resource          ../PageObjects/AnchorPageObjects/05__opsCheckerPage.robot

*** Keywords ***
Anchor onboarding flow
    [Documentation]    Anchor onboarding into Triton
    Set Selenium Speed    0.3s
    # ${anchorName}=    Set Variable    Brian Limited
    loginPage.Open browser and login to triton
    01__anchorDetailsExcel.Generate Anchor name,PAN and CIN Numbers
    Open Workbook    ${CURDIR}\\..\\PageObjects\\AnchorPageObjects\\AnchorDetails.xlsx
    ${anchorName}    Read From Cell    A2   
    ${pan}           Read From Cell    B2   
    ${cin}           Read From Cell    C2  
    Set Test Variable    ${anchorName}
    ${caseName}=    Get Variable Value    ${anchorName}
    Set Test Variable    ${caseName}
    Close Workbook
    01__anchorFileUploadPage.Anchor Details File Upload   ${anchorName}   ${pan}   ${cin}
    02__creditNormsPage.From CPA lead assign to CPA
    02__creditNormsPage.Create credit norms
    03__creditSubCommitteePage.From Credit Underwriter lead assign
    03__creditSubCommitteePage.Credit subcommitee approval
    04__opsMakerPage.From Ops_maker_lead assign Ops_maker
    ${accNum}=    FakerLibrary.Numerify    text=############
    04__opsMakerPage.Beneficiary details and Document upload    ${anchorName}  ${bankName}   ${accNum}  ${bankBranch}  ${fileLocation}
    05__opsCheckerPage.From Ops_checker_lead assign Ops_checker
    05__opsCheckerPage.Ops_checker Approval
    loginPage.login to Triton    ${userName}    ${password}
    Wait Until Element Is Visible    xpath://td[text()='${anchorName}']    timeout=10s
    Click Element                    xpath://td[text()='${anchorName}']
    Wait Until Element Is Visible    xpath://h6[text()='Anchor Details: ${anchorName}'] 
    Capture Page Screenshot
    Logout from Triton
    
*** Test Cases ***
Test Anchor onboarding flow
    [Documentation]    Test Anchor Onboarding flow
    Anchor onboarding flow
    Log    Anchor onboarded into Triton

