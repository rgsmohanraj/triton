*** Settings ***
Resource    ../../keywords/variables.robot
Suite Setup    CPA_Authentication

*** Variables ***
${email}=    balaji.ramalingam@vivriticapital.com
${Cus_Type}=     1
${Id}=    51007

    

*** Keywords ***
CPA_Authentication
    ${username}    Set Variable       cpa_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}

*** Test Cases ***
WF_Exs_Achor_In_Desc
    [Documentation]    existingAnchorInDesc
    create session     existingAnchorInDesc      ${wf_url}
    ${header}=    Create Dictionary    Authorization=${Token}   Content-Type=application/json
    ${response}=       GET On Session      existingAnchorInDesc           existingAnchorInDesc    headers=${header}
    ${status_code}=    convert to string                          ${response.status_code}
    should be equal    ${status_code}                             ${expected_code}

WF_FindAnchorname
    [Documentation]    Find Anchor Name
    create session     Findanchorname      ${wf_url}
    ${header}=    Create Dictionary    Authorization=${Token}   Content-Type=application/json
    ${response}=       GET On Session      Findanchorname           findAnchorName/${Anchor_App_id}    headers=${header}
    ${status_code}=    convert to string                    ${response.status_code}
    should be equal    ${status_code}    ${expected_code}

WF_Final_Status_byEmail
    [Documentation]    Final status email
    create session     Finalstatusmail      ${wf_url}
    ${header}=    Create Dictionary    Authorization=${Token}   Content-Type=application/json
    ${response}=       GET On Session      Finalstatusmail           getFinalWFStatus/${email}    headers=${header}
    ${status_code}=    convert to string                    ${response.status_code}
    should be equal    ${status_code}    ${expected_code}

WF_FinalStatus_Email_byLead
    [Documentation]    Final status of email by lead
    Create Session    finalstatusemailbylead    ${wf_url}
    ${header}=    Create Dictionary    Authorization=${Token}   Content-Type=application/json
    ${response}=       GET On Session      finalstatusemailbylead           getFinalWFStatusByLead/${email}    headers=${header}
    ${status_code}=    convert to string                    ${response.status_code}
    should be equal    ${status_code}    ${expected_code}

WF_History_Status
    [Documentation]    History of WF Status
    Create Session    historyofwfstatus    ${wf_url}
    ${header}=    Create Dictionary    Authorization=${Token}   Content-Type=application/json
    ${response}=       GET On Session      historyofwfstatus          getHistoryOfWFStatus/${Anchor_App_id}    headers=${header}
    ${status_code}=    convert to string                    ${response.status_code}
    should be equal    ${status_code}    ${expected_code}

WF_Remarks
    [Documentation]    get remarks
    Create Session    getremarks    ${wf_url}
    ${header}=    Create Dictionary    Authorization=${Token}   Content-Type=application/json
    ${response}=       GET On Session      getremarks          getRemarks/${Anchor_App_id}    headers=${header}
    ${status_code}=    convert to string                    ${response.status_code}
    should be equal    ${status_code}    ${expected_code}

WF_Status
    [Documentation]    Get WF status
    Create Session    getwfstatus    ${wf_url}
    ${header}=    Create Dictionary    Authorization=${Token}   Content-Type=application/json
    ${response}=       GET On Session      getwfstatus          getWFStatus/${Anchor_App_id}    headers=${header}
    ${status_code}=    convert to string                    ${response.status_code}
    should be equal    ${status_code}    ${expected_code}

WF_OnboardedCustomer
    [Documentation]    WF_Onboarded Customer
    Create Session    WFonboarededCus    ${wf_url}
    ${header}=    Create Dictionary    Authorization=${Token}   Content-Type=application/json
    ${response}=       GET On Session      WFonboarededCus          onBoardedCustomers/${Cus_Type}    headers=${header}
    ${status_code}=    convert to string                    ${response.status_code}
    should be equal    ${status_code}    ${expected_code}

WF_Saveapproval
    [Documentation]    Save WF Approval stage
    Create Session    saveapprovalstage     ${wf_url}
    ${body}=     create dictionary     appId=${Anchor_App_id}     approvedStatus=true     approverInfo=cpa_lead@vivriticapital.com    nextApproverInfo=ANCHOR_CREDIT_UNDERWRITER_LEAD     remarks=ok    stageId=3    status=2
    ${header}=     create dictionary     Authorization=${Token}    Content-Type=application/json
    ${response}=     POST On Session    saveapprovalstage     /saveWFApprovalSatge    json=${body}     headers=${header}
    Should Be Equal As Strings     ${response.status_code}     ${expected_code}

WF_RenewalFlow_True
    [Documentation]  Save WF Renewal Flow for True
    Create Session    savewfrenewal     ${wf_url}
    ${body}=     create dictionary     appId=${Anchor_App_id}     approvedStatus=true     approverInfo=cpa_lead@vivriticapital.com    nextApproverInfo=ANCHOR_CREDIT_UNDERWRITER_LEAD     remarks=ok    stageId=3    status=2
    ${header}=     create dictionary     Authorization=${Token}    Content-Type=application/json
    ${response}=     POST On Session    savewfrenewal     /saveWFRenewalFlow/true    json=${body}     headers=${header}
    Should Be Equal As Strings     ${response.status_code}     ${expected_code}

WF_RenewalFlow_False
    [Documentation]  Save WF Renewal Flow for False
    Create Session    savewfrenewal     ${wf_url}
    ${body}=     create dictionary     appId=${Anchor_App_id}     approvedStatus=true     approverInfo=cpa_lead@vivriticapital.com    nextApproverInfo=ANCHOR_CREDIT_UNDERWRITER_LEAD     remarks=ok    stageId=3    status=2
    ${header}=     create dictionary     Authorization=${Token}    Content-Type=application/json
    ${response}=     POST On Session    savewfrenewal     /saveWFRenewalFlow/false    json=${body}     headers=${header}
    Should Be Equal As Strings     ${response.status_code}     ${expected_code}

WF_Change Assignee
    [Documentation]    Change of Assignee
    Create Session    ChangeAssignee    ${wf_url}
    ${body}=    Create Dictionary    appId=${Anchor_App_id}    
    ${header}=     create dictionary     Authorization=${Token}    Content-Type=application/json
    ${response}=     PUT On Session    ChangeAssignee     /changeAssigne/${Id}/${email}   json=${body}     headers=${header}
    Should Be Equal As Strings     ${response.status_code}     ${expected_code}



