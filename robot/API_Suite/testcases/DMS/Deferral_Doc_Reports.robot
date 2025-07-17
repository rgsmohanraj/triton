*** Settings ***
Resource            ../../keywords/variables.robot
Suite Setup           CPA_Authentication


*** Keywords ***
CPA_Authentication
    ${username}    Set Variable       cpa_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}

*** Test Cases ***

TC_DMS_01_DeferralReport
    [Documentation]    deferralReport
    create session     DefReport     ${DMS_Url}
    ${response}=       GET On Session      DefReport     /dms/deferralReport/${cp_app_id}
    ${status_code}=    convert to string    ${response.status_code}
    should be equal    ${status_code}       ${expected_code}


TC_DMS_02_ExistingDeferralReportusingStatus
    [Documentation]    DeferralDetails
    create session     deferralReport     ${DMS_Url}
    ${response}=       GET On Session      deferralReport     /dms/deferralReport/${cp_app_id}/3
    ${status_code}=    convert to string    ${response.status_code}
    should be equal    ${status_code}       ${expected_code}

TC_DMS_03_existingDeferralDetails
    [Documentation]    existingDetails
    create session     Existing     ${DMS_Url}
    ${response}=       GET On Session      Existing     /dms/existingDeferralDetails/3
    ${status_code}=    convert to string    ${response.status_code}
    should be equal    ${status_code}       ${expected_code}