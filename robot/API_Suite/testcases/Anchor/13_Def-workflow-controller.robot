*** Settings ***
Resource        ../../keywords/variables.robot
Suite Setup     Operation_maker_Authentication


*** Variables ***
${email}=      operation_maker_lead@vivriticapital.com
${id}=        52568         #for this Id we can use the primary Id which is generated against the app id


*** Keywords ***
Operation_maker_Authentication
    ${username}    Set Variable       operation_maker_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}


*** Test Cases ***

TC_DL_01_FinalDeferralWFStatus_By_email
    [Documentation]    FinalDeferralWFStatus
    create session     GetFinalDeferral    ${Def_url}
    ${header}=         create dictionary       Authorization=${Token}      content-type=application/json
    ${response}=       GET On Session              GetFinalDeferral    /getFinalDeferralWFStatus/${email}     headers=${header}
    ${status_code}=    convert to string    ${response.status_code}
    should be equal    ${status_code}       ${expected_code}

TC_DL_02_FinalWFStatus_By_Lead
    [Documentation]    FinalDeferralWFStatus
    create session     FinalDefWFStatus   ${Def_url}
    ${header}=         create dictionary       Authorization=${Token}      content-type=application/json
    ${response}=       GET On Session              FinalDefWFStatus        /getFinalWFStatusByLead/${email}     headers=${header}
    ${status_code}=    convert to string    ${response.status_code}
    should be equal    ${status_code}       ${expected_code}

TC_DL_03_GetHistoryOfWFStatus_By_ID
    [Documentation]    HistoryOfWFStatus_By_ID
    create session     HistoryOfWFStatus    ${Def_url}
    ${header}=         create dictionary       Authorization=${Token}      content-type=application/json
    ${response}=       GET On Session              HistoryOfWFStatus    /getHistoryOfWFStatus/${cp_app_id}     headers=${header}
    ${status_code}=    convert to string    ${response.status_code}
    should be equal    ${status_code}       ${expected_code}

TC_DL_04_Get_Remarks_By_ID
    [Documentation]    GetRemarks_By_ID
    create session     GetRemarks    ${Def_url}
    ${header}=         create dictionary       Authorization=${Token}      content-type=application/json
    ${response}=       GET On Session              GetRemarks         /getRemarks/${cp_app_id}     headers=${header}
    ${status_code}=    convert to string    ${response.status_code}
    should be equal    ${status_code}       ${expected_code}

TC_DL_05__SaveDeferralWorkflow
    [Documentation]    SaveDeferralWorkflow
    create session    DeferralWorkflow      ${Def_url}    
    &{data}=    Create Dictionary     appId=${cp_app_id}    approvedStatus=${true}   approverInfo=QA    nextApproverInfo=QATest    remarks=None     stageId=0    status=0 
    ${header}=         create dictionary    Authorization=${Token}    content-type=application/json
    ${response}=       POST On Session         DeferralWorkflow       /saveDeferralWorkflow      json=${data}       headers=${header}
    ${status_code}=    convert to string       ${response.status_code}
    should be equal    ${status_code}          ${expected_code}

TC_DL_06_ChangeAssignee
    [Documentation]   DeferralFlow Changeassignee
    Create Session    changeassignee    ${Def_url}
    ${body}=    Create Dictionary    appId=${Anchor_App_id}    
    ${header}=     create dictionary     Authorization=${Token}    Content-Type=application/json
    ${response}=     PUT On Session    ChangeAssignee     /changeAssigne/${id}/${email}   json=${body}     headers=${header}
    Should Be Equal As Strings     ${response.status_code}     ${expected_code}