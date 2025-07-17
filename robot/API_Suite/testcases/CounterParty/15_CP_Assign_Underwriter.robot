*** Settings ***
Resource    ../../keywords/variables.robot
Suite Setup    CPA_Authentication



*** Variables ***
${put_id}=    72884

*** Keywords ***
CPA_Authentication
    ${username}    Set Variable       cpa_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}

*** Test Cases ***
Get_Assignunder_Writer
    [Documentation]    AssignUnderwriter_Get
    create session     Assignunderwriter      ${CP_base_url}
    ${header}=    Create Dictionary    Authorization=${Token}   Content-Type=application/json
    ${response}=       GET On Session      Assignunderwriter          /assignUnderwriter/${cp_app_id}   headers=${header}
    ${status_code}=    convert to string        ${response.status_code}
    should be equal    ${status_code}          ${expected_code}

Post_Assignunder_Writer
    [Documentation]    AssignUnderwriter_Post
    Create Session    postAssign     ${CP_base_url}
    ${body}=     create dictionary     appId=${cp_app_id}     assignTo=credit_underwriter_lead@vivriticapital.com     createdBy=Tester    updatedBy=Durgaa     
    ${header}=     create dictionary     Authorization=${Token}    Content-Type=application/json
    ${response}=     POST On Session    postAssign     /assignUnderwriter   json=${body}     headers=${header}
    Should Be Equal As Strings     ${response.status_code}     ${expected_code}

Put_AssignUnder_Writer
    [Documentation]    AssignUnderWriter_PUT
    Create Session    putassign    ${CP_base_url}
    ${body}=    Create Dictionary   appId=${cp_app_id}    assignTo=credit_underwriter_lead@vivriticapital.com    createdBy=Durgaa    id=${put_id}   updatedBy=pavi
    ${header}=     create dictionary     Authorization=${Token}    Content-Type=application/json
    ${response}=     PUT On Session    putassign     /assignUnderwriter   json=${body}     headers=${header}
    Should Be Equal As Strings     ${response.status_code}     ${expected_code}





