*** Settings ***
Resource            ../../keywords/variables.robot
Suite Setup           CPA_Authentication


*** Keywords ***
CPA_Authentication
    ${username}    Set Variable       cpa_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}

*** Test Cases ***

TC_DMS_01_Other_Docs_For_OpsChecker
    [Documentation]    otherDocsForOpsChecker
    create session     otherDocs     ${DMS_Url}
    ${response}=       GET On Session      otherDocs     /dms/otherDocsForOpsChecker/${cp_app_id}
    ${status_code}=    convert to string    ${response.status_code}
    should be equal    ${status_code}       ${expected_code}

    
TC_DMS_02_Other_Docs_Master
    [Documentation]    otherDocumentsMaster
    create session     OTHERDocsMaster     ${DMS_Url}
    ${response}=       GET On Session      OTHERDocsMaster     /dms/otherDocumentMaster/${cp_app_id}
    ${status_code}=    convert to string    ${response.status_code}
    should be equal    ${status_code}       ${expected_code}

