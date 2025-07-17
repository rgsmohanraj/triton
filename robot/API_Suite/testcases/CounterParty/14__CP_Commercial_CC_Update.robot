*** Settings ***
Resource                ../../keywords/variables.robot
Test Setup              Anchor_CommercialCC_ReturnId_for_PUT
Suite Setup            Business_Authentication
*** Keywords ***
 Business_Authentication
    ${username}    Set Variable       business_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}

*** Test Cases ***                               
Commercial_cc_update
    TC_CP_commercial_cc_Update                     ${Anchor_cust_id}           10                            40                          12                            30                    20                        10                    20                            30                    20                    20                30                        30                            30                            
 

*** Keywords ***
TC_CP_commercial_cc_Update
    [Arguments]      ${custId}          ${productRemarks}         ${anchorNameRemarks}     ${regularLimitRemarks}     ${adhocLimitRemarks}       ${creditPeriodRemarks}    ${doorRemarks}       ${invoiceAgeingRemarks}     ${marginRemarks}      ${interestRateRemarks}     ${pfRemarks}     ${renewalRemarks}       ${interestTypeRemarks}         ${renewalPeriodRemarks}     
    [Documentation]        counter party commercial cc update
    Create Session        cp_commercial_cc_Update     ${CP_base_url}  
    ${CommercialCC}=           Create Dictionary       custId=${custId}          productRemarks=${productRemarks}         anchorNameRemarks=${anchorNameRemarks}     regularLimitRemarks=${regularLimitRemarks}     adhocLimitRemarks=${adhocLimitRemarks}       creditPeriodRemarks=${creditPeriodRemarks}       doorRemarks=${doorRemarks}       invoiceAgeingRemarks=${invoiceAgeingRemarks}     marginRemarks=${marginRemarks}      interestRateRemarks=${interestRateRemarks}     pfRemarks=${pfRemarks}     renewalRemarks=${renewalRemarks}       interestTypeRemarks=${interestTypeRemarks}         renewalPeriodRemarks=${renewalPeriodRemarks}     
    ${entry}=           Create List     ${CommercialCC}  
    ${body}=            Create Dictionary        commercialDataList=${entry}
    ${header}=          Create Dictionary    Authorization=${Token}    content-type=application/json
    ${response}=        Put Request    cp_commercial_cc_Update    /commercialCc    json=${body}    headers=${header}
    ${statuscode}=      Convert To String    ${response.status_code}
    Should Be Equal     ${statuscode}    ${expected_code}


Anchor_CommercialCC_ReturnId_for_PUT
    [Documentation]   Getting the Commercial CC details
    create session    CP_CC  ${CP_base_url}
    ${header}=        Create Dictionary    Authorization=${Token}    Content-Type=application/json
    ${response}=      GET On Session      CP_CC         /commercialCc/${cp_app_id}     headers=${header}
    ${json-data}=     Convert String To Json  ${response.content}
    ${id_value}=      Get Value From Json    ${json-data}    $[0].id
    ${CC_putID}=            Get From List    ${id_value}           0
    log  ${CC_putID}
    Set Global Variable   ${CC_putID}  