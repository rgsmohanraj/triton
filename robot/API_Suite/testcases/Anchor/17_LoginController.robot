*** Settings ***
Resource    ../../keywords/variables.robot
Suite Setup    CPA_Authentication

*** Variables ***
${email}=    cpa_lead@vivriticapital.com
${Cus_Type}=     1
    

*** Keywords ***
CPA_Authentication
    ${username}    Set Variable       cpa_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}

*** Test Cases ***
Login 
     Create Session    login     ${DMS_Url}
    ${body}=     create dictionary     appId=${Anchor_App_id}     username=cpa_lead    password=admin@1234
    ${header}=     create dictionary     Authorization=${Token}    Content-Type=application/json
    ${response}=     POST On Session    login     /doLogin    json=${body}     headers=${header}
    Should Be Equal As Strings     ${response.status_code}     ${expected_code}