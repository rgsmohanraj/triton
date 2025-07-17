*** Settings ***
Resource    ../../keywords/variables.robot
Suite Setup        CPA_Authentication


*** Keywords ***
CPA_Authentication
    ${username}    Set Variable       cpa_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}
*** Test Cases ***
TC_CN_46_CreditNorms_readall
    [Documentation]    Credit Norms Readall  
    Create Session     creditNorms_details                         ${base_url}
    ${header}          Create Dictionary    Authorization=${Token}    Content-Type=application/json     
    ${response}=       Get Request            creditNorms_details         /creditNormsMaster        headers=${header}
    ${status_code}=    convert to string                          ${response.status_code}
    should be equal    ${status_code}                             ${expected_code}