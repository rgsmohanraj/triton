*** Settings ***
Resource           ../../keywords/variables.robot
Suite Setup       CPA_Authentication

*** Keywords ***
CPA_Authentication
    ${username}    Set Variable       cpa_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}
*** Variables ***            
${MinVin}           13             #Months
${MinPur}           1200  
${ProPan}           Required 
${ProOvd}           Required
${BusPan}           Required
${GstCert}          Required 

*** Test Cases ***
TC_CN_01_CreditNorms_readById
    [Tags]  SANITY
    [Documentation]      Read beneficiary by id
    create session       creditNorms_details                         ${base_url}
    ${header}            Create Dictionary    Authorization=${Token}    Content-Type=application/json
    ${response}=         GET On Session                             creditNorms_details           /creditNormsDetailsByFId/${Anchor_App_id}      headers=${header}  
    ${json-data}=        Convert String To Json     ${response.content}
    ${id_value}=         Get Value From Json    ${json-data}    $[0].id
    Log To Console       ${id_value}
    ${CN_PutId}=            Get From List    ${id_value}           0
    Log To Console          ${CN_PutId}
    log      ${CN_PutId}
    Set Global Variable     ${CN_PutId}
    Log To Console          ${CN_PutId}

    ${id_value1}=            Get Value From Json    ${json-data}    $[1].id
    Log To Console           ${id_value1}
    ${CN_PutId1}=            Get From List    ${id_value1}         0
    Log To Console           ${CN_PutId1}
    log  ${CN_PutId1}  
    Set Global Variable      ${CN_PutId1}
    Log To Console           ${CN_PutId1}  

    ${id_value2}=          Get Value From Json    ${json-data}    $[2].id
    Log To Console         ${id_value2}
    ${CN_PutId2}=          Get From List    ${id_value2}         0
    Log To Console         ${CN_PutId2}
    log  ${CN_PutId2}  
    Set Global Variable       ${CN_PutId2}
    Log To Console            ${CN_PutId2}

    ${id_value3}=      Get Value From Json    ${json-data}    $[3].id
    Log To Console       ${id_value3}
    ${CN_PutId3}=            Get From List    ${id_value3}         0
    Log To Console       ${CN_PutId3}
    log  ${CN_PutId3}  
    Set Global Variable  ${CN_PutId3}
    Log To Console        ${CN_PutId3}

    ${id_value4}=      Get Value From Json    ${json-data}    $[4].id
    Log To Console       ${id_value4}
    ${CN_PutId4}=            Get From List    ${id_value4}         0
    Log To Console       ${CN_PutId4}
    log  ${CN_PutId4}  
    Set Global Variable   ${CN_PutId4}
    Log To Console        ${CN_PutId4}

    ${id_value5}=      Get Value From Json    ${json-data}    $[5].id
    Log To Console       ${id_value5}
    ${CN_PutId5}=            Get From List    ${id_value5}         0
    Log To Console       ${CN_PutId5}
    log  ${CN_PutId5}  
    Set Global Variable   ${CN_PutId5}
    Log To Console        ${CN_PutId5}
    ${status_code}=    convert to string      ${response.status_code}
    should be equal     ${status_code}          ${expected_code}

TC_CN_02_AnchorCreditNorms_Update
    [Documentation]     CreditNorms Update
    create session             creditNorms_detail     ${base_url}
    ${MinVitageAnchor}=        Create Dictionary      appId=${Anchor_App_id}    cnMasterId=263       id=${CN_PutId}      value=${MinVin}        
    ${MinMonthlyPruchase}=     Create Dictionary      appId=${Anchor_App_id}    cnMasterId=266       id=${CN_PutId1}     value=${MinPur}
    ${gurantorPAN}=            Create Dictionary      appId=${Anchor_App_id}    cnMasterId=389       id=${CN_PutId2}     value=${ProPan}
    ${gurantorOVD}=            Create Dictionary      appId=${Anchor_App_id}    cnMasterId=390       id=${CN_PutId3}     value=${ProOvd}
    ${BusinessPAN}=            Create Dictionary      appId=${Anchor_App_id}    cnMasterId=391       id=${CN_PutId4}     value=${BusPan}
    ${GstCerticate}=           Create Dictionary      appId=${Anchor_App_id}    cnMasterId=392       id=${CN_PutId5}     value=${GstCert}
    ${creditList}=             Create List            ${MinVitageAnchor}      ${MinMonthlyPruchase}     ${gurantorPAN}     ${gurantorOVD}     ${BusinessPAN}     ${GstCerticate}
    ${data}=                   Create Dictionary      createdBy=QA      creditNormsDetailsDataList=${creditList}    updatedBy=QA    
    ${header}=                 Create Dictionary      Authorization=${Token}     content-type=application/json
    ${response}=               Put Request            creditNorms_detail      /creditNormsDetails       data=${data}    headers=${header}
    ${status_code}=            convert to string      ${response.status_code}
    should be equal            ${status_code}         ${expected_code}