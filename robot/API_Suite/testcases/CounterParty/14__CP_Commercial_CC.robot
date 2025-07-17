*** Settings ***
Resource             ../../keywords/variables.robot
Test Template        TC_CP_commercial_cc_positive_id
Suite Setup            Business_Authentication
*** Keywords ***
 Business_Authentication
    ${username}    Set Variable       business_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}
*** Test Cases ***                             ${custId}          ${productRemarks}         ${anchorNameRemarks}     ${regularLimitRemarks}     ${adhocLimitRemarks}       ${creditPeriodRemarks}    ${doorRemarks}       ${invoiceAgeingRemarks}     ${marginRemarks}      ${interestRateRemarks}     ${pfRemarks}     ${renewalRemarks}       ${interestTypeRemarks}         ${renewalPeriodRemarks}     
TC_CPCC_70_randomnumbers_appId              ${Anchor_cust_id}        10                            40                          12                            30                    20                        10                    20                            30                    20                    20                30                        30                            30                            
 