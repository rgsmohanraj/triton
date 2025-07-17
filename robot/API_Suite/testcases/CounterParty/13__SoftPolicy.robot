*** Settings ***
Resource               ../../keywords/variables.robot
Suite Setup            CPA_Authentication
*** Keywords ***
CPA_Authentication
    ${username}    Set Variable       cpa_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}
*** Variables ***
${custType_dealer}                     1
${custType_vendor}                     2 

${spId_bureau_comm}                    148
${value_bureau_comm}                   6
${spId_SMA0}                           149       
${value_SMA0}                          0
${spId_SMA1}                           150           
${value_SMA1}                          4
${spId_SMA2_6m}                        151           
${value_SMA2_6m}                       1
${spId_SMA2_6to12m}                    152           
${value_SMA2_6to12m}                   2
${spId_CurrentOD_comm}                 153           
${value_CurrentOD_comm}                50000
${spId_CurrentODCC_comm}               154           
${value_CurrentODCC_comm}              5000
${spId_24m_Subs_comm}                  155           
${value_24m_Subs_comm}                 0
${spId_48m_Wilful_comm}                156          
${value_48m_Wilful_comm}               0
${spId_Bureau_con}                     157
${value_Bureau_con}                    99
${spId_CurrentOD_con}                  158           
${value_CurrentOD_con}                50000
${spId_CurrentODCC_con}                159           
${value_CurrentODCC_con}               5000
${spId_24m_Subs_con}                   160           
${value_24m_Subs_con}                  0    
${spId_36m_Wilful_con}                 161    
${value_36m_Wilful_con}                0
${spId_EPFO}                           162           
${value_EPFO}                          6
${spId_GST}                            163           
${value_GST}                           3
${spId_State}                          164     
${value_State}                         Bihar
${spId_Age}                            165     
${value_Age}                           21
        
${value_bureau_comm_update}            8    
${value_SMA0_update}                   8         
${value_SMA1_update}                   8        
${value_SMA2_6m_update}                8           
${value_SMA2_6to12m_update}            8          
${value_CurrentOD_comm_update}         45000
${value_CurrentODCC_comm_update}       45000           
${value_24m_Subs_comm_update}          8        
${value_48m_Wilful_comm_update}        8
${value_Bureau_con_update}             8           
${value_CurrentOD_con_update}          8           
${value_CurrentODCC_con_update}        8           
${value_24m_Subs_con_update}           8        
${value_36m_Wilful_con_update}         8           
${value_EPFO_update}                   8           
${value_GST_update}                    8     
${value_State_update}                  Delhi     
${value_Age_update}                    8

*** Test Cases *** 
TC_SP01_SoftPolicy_details_create 
        [Documentation]     Creating softpolicy detail
        create session      createSoftPolicy      ${CP_base_url}

        ${bureau_comm}=                                   Create Dictionary      softPolicyId=${spId_bureau_comm}                     appId=${cp_app_id}    value=${value_bureau_comm}
        ${SMA-0/1-30 12 Months}=                          Create Dictionary      softPolicyId=${spId_SMA0}                            appId=${cp_app_id}    value=${value_SMA0}
        ${SMA-1/31-60 12 months}=                         Create Dictionary      softPolicyId=${spId_SMA1}                            appId=${cp_app_id}    value=${value_SMA1}
        ${SMA-2/61-90 6 month}=                           Create Dictionary      softPolicyId=${spId_SMA2_6m}                         appId=${cp_app_id}    value=${value_SMA2_6m}
        ${SMA-2/61-90 6-12 months}=                       Create Dictionary      softPolicyId=${spId_SMA2_6to12m}                     appId=${cp_app_id}    value=${value_SMA2_6to12m}
        ${CurrentOD_comm}=                                Create Dictionary      softPolicyId=${spId_CurrentOD_comm}                  appId=${cp_app_id}    value=${value_CurrentOD_comm}
        ${Current OD_credit card}=                        Create Dictionary      softPolicyId=${spId_CurrentODCC_comm}                appId=${cp_app_id}    value=${value_CurrentODCC_comm}
        ${24 months:Substd, doubtful, loss, restructured/write off_comm}=        Create Dictionary      softPolicyId=${spId_24m_Subs_comm}   appId=${cp_app_id}    value=${value_24m_Subs_comm}
        ${48 months:Wilful or suit filed_comm}=           Create Dictionary      softPolicyId=${spId_48m_Wilful_comm}                 appId=${cp_app_id}    value=${value_48m_Wilful_comm}
        ${Bureau_con}=                                    Create Dictionary      softPolicyId=${spId_Bureau_con}                      appId=${cp_app_id}    value=${value_Bureau_con}
        ${CurrentOD_con}=                                 Create Dictionary      softPolicyId=${spId_CurrentOD_con}                   appId=${cp_app_id}    value=${value_CurrentOD_con}
        ${CurrentOD_credit card_con}=                     Create Dictionary      softPolicyId=${spId_CurrentODCC_con}                 appId=${cp_app_id}    value=${value_CurrentODCC_con}
        ${24 months:Substd, doubtful, loss, restructured/write off_con}=         Create Dictionary      softPolicyId=${spId_24m_Subs_con}   appId=${cp_app_id}    value=${value_24m_Subs_con}
        ${36 months:Wilful or suit filed_con}=            Create Dictionary      softPolicyId=${spId_36m_Wilful_con}                  appId=${cp_app_id}    value=${value_36m_Wilful_con}
        ${Latest EPFO delay in months}=                   Create Dictionary      softPolicyId=${spId_EPFO}                            appId=${cp_app_id}    value=${value_EPFO}
        ${Latest GST Payment delay in months}=            Create Dictionary      softPolicyId=${spId_GST}                             appId=${cp_app_id}    value=${value_GST}
        ${State should be in the serviceable area}=       Create Dictionary      softPolicyId=${spId_State}                           appId=${cp_app_id}    value=${value_State}
        ${Age of Main Promoter: In Years}=                Create Dictionary      softPolicyId=${spId_Age}                             appId=${cp_app_id}    value=${value_Age}

        ${list}=            Create List          ${bureau_comm}    ${SMA-0/1-30 12 Months}    ${SMA-1/31-60 12 months}      ${SMA-2/61-90 6 month}  ${SMA-2/61-90 6-12 months}    ${CurrentOD_comm}    ${Current OD_credit card}    ${24 months:Substd, doubtful, loss, restructured/write off_comm}    ${48 months:Wilful or suit filed_comm}      ${Bureau_con}    ${CurrentOD_con}     ${CurrentOD_credit card_con}    ${24 months:Substd, doubtful, loss, restructured/write off_con}    ${36 months:Wilful or suit filed_con}      ${Latest EPFO delay in months}   ${Latest GST Payment delay in months}   ${State should be in the serviceable area}    ${Age of Main Promoter: In Years}    
        &{data}=            Create Dictionary    softPolicyDetailsDataList=${list}
        ${header}=          Create Dictionary    Authorization=${Token}      content-type=application/json
        ${response}=        Post Request         createSoftPolicy               /softPolicyDetails     data=&{data}    headers=${header}
        ${status_code}=     convert to string    ${response.status_code}
        Should Be Equal     ${status_code}    ${expected_code}
        
TC_SP02_SoftPolicy_details_read 
        [Documentation]     Getting softpolicy detail
        create session      createSoftPolicy      ${CP_base_url}
        ${header}=          Create Dictionary    Authorization=${Token}      content-type=application/json
        ${response}=        Get Request         createSoftPolicy               /softPolicyDetails/${cp_app_id}        headers=${header}
        ${json_data}=     Convert String To Json    ${response.content}
        Log To Console    ${json_data}

        FOR    ${index}    IN RANGE    0    18
        ${id_value}=    Get Value From Json    ${json_data}    softPolicyDetailsDataList[${index}].id
        ${soft_putId}=            Get From List    ${id_value}           0
        Set Suite Variable    ${soft_putId_${index}}    ${soft_putId}
        Log To Console     ${index}: ${soft_putId}
        END

        Set Global Variable    ${soft_putId_${0}}
        Set Global Variable    ${soft_putId_${1}}
        Set Global Variable    ${soft_putId_${2}}
        Set Global Variable    ${soft_putId_${3}}
        Set Global Variable    ${soft_putId_${4}}
        Set Global Variable    ${soft_putId_${5}}
        Set Global Variable    ${soft_putId_${6}}
        Set Global Variable    ${soft_putId_${7}}
        Set Global Variable    ${soft_putId_${8}}
        Set Global Variable    ${soft_putId_${9}}
        Set Global Variable    ${soft_putId_${10}}
        Set Global Variable    ${soft_putId_${11}}
        Set Global Variable    ${soft_putId_${12}}
        Set Global Variable    ${soft_putId_${13}}
        Set Global Variable    ${soft_putId_${14}}
        Set Global Variable    ${soft_putId_${15}}
        Set Global Variable    ${soft_putId_${16}}
        Set Global Variable    ${soft_putId_${17}}
        
        ${status_code}=     convert to string    ${response.status_code}
        Should Be Equal     ${status_code}    ${expected_code}
 
TC_SP03_RunSoftPolicy_details  
        [Documentation]     Running softpolicy detail
        create session      runSoftPolicy      ${CP_base_url}

        ${bureau_comm}=                                   Create Dictionary      softPolicyId=${spId_bureau_comm}           appId=${cp_app_id}    value=${value_bureau_comm}
        ${SMA-0/1-30 12 Months}=                          Create Dictionary      softPolicyId=${spId_SMA0}                  appId=${cp_app_id}    value=${value_SMA0}
        ${SMA-1/31-60 12 months}=                         Create Dictionary      softPolicyId=${spId_SMA1}                  appId=${cp_app_id}    value=${value_SMA1}
        ${SMA-2/61-90 6 month}=                           Create Dictionary      softPolicyId=${spId_SMA2_6m}               appId=${cp_app_id}    value=${value_SMA2_6m}
        ${SMA-2/61-90 6-12 months}=                       Create Dictionary      softPolicyId=${spId_SMA2_6to12m}           appId=${cp_app_id}    value=${value_SMA2_6to12m}
        ${CurrentOD_comm}=                                Create Dictionary      softPolicyId=${spId_CurrentOD_comm}        appId=${cp_app_id}    value=$${value_CurrentOD_comm}
        ${Current OD_credit card}=                        Create Dictionary      softPolicyId=${spId_CurrentODCC_comm}      appId=${cp_app_id}    value=${value_CurrentODCC_comm}
        ${24 months:Substd, doubtful, loss, restructured/write off_comm}=        Create Dictionary      softPolicyId=${spId_24m_Subs_comm}   appId=${cp_app_id}    value=${value_24m_Subs_comm}
        ${48 months:Wilful or suit filed_comm}=           Create Dictionary      softPolicyId=${spId_48m_Wilful_comm}       appId=${cp_app_id}    value=${value_48m_Wilful_comm}
        ${Bureau_con}=                                    Create Dictionary      softPolicyId=${spId_Bureau_con}            appId=${cp_app_id}    value=${value_Bureau_con}
        ${CurrentOD_con}=                                 Create Dictionary      softPolicyId=${spId_CurrentOD_con}         appId=${cp_app_id}    value=${value_Current OD_con}
        ${CurrentOD_credit card_con}=                     Create Dictionary      softPolicyId=${spId_CurrentODCC_con}       appId=${cp_app_id}    value=${value_CurrentODCC_con}
        ${24 months:Substd, doubtful, loss, restructured/write off_con}=         Create Dictionary      softPolicyId=${spId_24m_Subs_con}   appId=${cp_app_id}    value=${value_24m_Subs_con}
        ${36 months:Wilful or suit filed_con}=            Create Dictionary      softPolicyId=${spId_36m_Wilful_con}        appId=${cp_app_id}    value=${value_36m_Wilful_con}
        ${Latest EPFO delay in months}=                   Create Dictionary      softPolicyId=${spId_EPFO}                  appId=${cp_app_id}    value=${value_EPFO}
        ${Latest GST Payment delay in months}=            Create Dictionary      softPolicyId=${spId_GST}                   appId=${cp_app_id}    value=${value_GST}
        ${State should be in the serviceable area}=       Create Dictionary      softPolicyId=${spId_State}                 appId=${cp_app_id}    value=${value_State}
        ${Age of Main Promoter: In Years}=                Create Dictionary      softPolicyId=${value_Age}                  appId=${cp_app_id}    value=${value_Age}

        ${list}=            Create List          ${bureau_comm}    ${SMA-0/1-30 12 Months}    ${SMA-1/31-60 12 months}      ${SMA-2/61-90 6 month}  ${SMA-2/61-90 6-12 months}    ${CurrentOD_comm}    ${Current OD_credit card}    ${24 months:Substd, doubtful, loss, restructured/write off_comm}    ${48 months:Wilful or suit filed_comm}      ${Bureau_con}    ${CurrentOD_con}     ${CurrentOD_credit card_con}    ${24 months:Substd, doubtful, loss, restructured/write off_con}    ${36 months:Wilful or suit filed_con}      ${Latest EPFO delay in months}   ${Latest GST Payment delay in months}   ${State should be in the serviceable area}    ${Age of Main Promoter: In Years}    
        &{data}=            Create Dictionary    softPolicyDetailsDataList=${list}
        ${header}=          Create Dictionary    Authorization=${Token}      content-type=application/json
        ${response}=        Post Request         runSoftPolicy               /runSoftPolicy/${custType_dealer}     data=&{data}    headers=${header}
        ${status_code}=     convert to string    ${response.status_code}
        Should Be Equal     ${status_code}    ${expected_code}
        

TC_SP04_SoftPolicy_details_validation   
        [Documentation]     validate softpolicy detail 
        create session      validateSoftPolicy      ${CP_base_url}

        ${bureau_comm}=                                   Create Dictionary      softPolicyId=${spId_bureau_comm}           appId=${cp_app_id}    id=17430    value=${value_bureau_comm}
        ${SMA-0/1-30 12 Months}=                          Create Dictionary      softPolicyId=${spId_SMA0}                  appId=${cp_app_id}    id=17435    value=${value_SMA0}
        ${SMA-1/31-60 12 months}=                         Create Dictionary      softPolicyId=${spId_SMA1}                  appId=${cp_app_id}    id=17432    value=${value_SMA1}  
        ${SMA-2/61-90 6 month}=                           Create Dictionary      softPolicyId=${spId_SMA2_6m}               appId=${cp_app_id}    id=17434    value=${value_SMA2_6m}
        ${SMA-2/61-90 6-12 months}=                       Create Dictionary      softPolicyId=${spId_SMA2_6to12m}           appId=${cp_app_id}    id=17436    value=${value_SMA2_6to12m}
        ${CurrentOD_comm}=                                Create Dictionary      softPolicyId=${spId_CurrentOD_comm}        appId=${cp_app_id}    id=17431    value=${value_CurrentOD_comm}
        ${Current OD_credit card}=                        Create Dictionary      softPolicyId=${spId_CurrentODCC_comm}      appId=${cp_app_id}    id=17428    value=${value_CurrentODCC_comm}
        ${24m_comm}=                                      Create Dictionary      softPolicyId=${spId_24m_Subs_comm}         appId=${cp_app_id}    id=17433    value=${value_24m_Subs_comm}
        ${48 months:Wilful or suit filed_comm}=           Create Dictionary      softPolicyId=${spId_48m_Wilful_comm}       appId=${cp_app_id}    id=17429    value=${value_48m_Wilful_comm}
        ${Bureau_con}=                                    Create Dictionary      softPolicyId=${spId_Bureau_con}            appId=${cp_app_id}    id=17438    value=${value_Bureau_con}
        ${CurrentOD_con}=                                 Create Dictionary      softPolicyId=${spId_CurrentOD_con}         appId=${cp_app_id}    id=17440    value=${value_Current OD_con}
        ${CurrentOD_credit card_con}=                     Create Dictionary      softPolicyId=${spId_CurrentODCC_con}       appId=${cp_app_id}    id=17441    value=${value_CurrentODCC_con}
        ${24m_con}=                                       Create Dictionary      softPolicyId=${spId_24m_Subs_con}         appId=${cp_app_id}    id=17437    value=${value_24m_Subs_con}
        ${36 months:Wilful or suit filed_con}=            Create Dictionary      softPolicyId=${spId_36m_Wilful_con}        appId=${cp_app_id}    id=17439    value=${value_36m_Wilful_con}
        ${Latest EPFO delay in months}=                   Create Dictionary      softPolicyId=${spId_EPFO}                  appId=${cp_app_id}    id=17444    value=${value_EPFO}
        ${Latest GST Payment delay in months}=            Create Dictionary      softPolicyId=${spId_GST}                   appId=${cp_app_id}    id=17443    value=${value_GST}
        ${State should be in the serviceable area}=       Create Dictionary      softPolicyId=${spId_State}                 appId=${cp_app_id}    id=17442    value=${value_State}
        ${Age of Main Promoter: In Years}=                Create Dictionary      softPolicyId=${spId_Age}                  appId=${cp_app_id}    id=17445    value=${value_Age}

        ${list}=            Create List          ${bureau_comm}    ${SMA-0/1-30 12 Months}    ${SMA-1/31-60 12 months}      ${SMA-2/61-90 6 month}  ${SMA-2/61-90 6-12 months}    ${CurrentOD_comm}    ${Current OD_credit card}    ${24m_comm}    ${48 months:Wilful or suit filed_comm}      ${Bureau_con}    ${CurrentOD_con}     ${CurrentOD_credit card_con}    ${24m_con}    ${36 months:Wilful or suit filed_con}      ${Latest EPFO delay in months}   ${Latest GST Payment delay in months}   ${State should be in the serviceable area}    ${Age of Main Promoter: In Years}    
        &{data}=            Create Dictionary    softPolicyDetailsDataList=${list}
        ${header}=          Create Dictionary    Authorization=${Token}      content-type=application/json
        ${response}=        Post Request        validateSoftPolicy      /validateSoftPolicy     data=&{data}    headers=${header}
        ${status_code}=     convert to string    ${response.status_code}
        Should Be Equal     ${status_code}    ${expected_code}    

TC_SP05_SoftPolicy_details_update 
        [Documentation]     Updating softpolicy detail
        create session      updateSoftPolicy      ${CP_base_url}

        ${bureau_comm}=                                   Create Dictionary      softPolicyId=${spId_bureau_comm}           appId=${cp_app_id}    id=${soft_putId_${0}}    value=${value_bureau_comm_update}
        ${SMA-0/1-30 12 Months}=                          Create Dictionary      softPolicyId=${spId_SMA0}                  appId=${cp_app_id}    id=${soft_putId_${1}}    value=${value_SMA0_update}
        ${SMA-1/31-60 12 months}=                         Create Dictionary      softPolicyId=${spId_SMA1}                  appId=${cp_app_id}    id=${soft_putId_${2}}    value=${value_SMA1_update}  
        ${SMA-2/61-90 6 month}=                           Create Dictionary      softPolicyId=${spId_SMA2_6m}               appId=${cp_app_id}    id=${soft_putId_${3}}    value=${value_SMA2_6m_update}
        ${SMA-2/61-90 6-12 months}=                       Create Dictionary      softPolicyId=${spId_SMA2_6to12m}           appId=${cp_app_id}    id=${soft_putId_${4}}    value=${value_SMA2_6to12m_update}
        ${CurrentOD_comm}=                                Create Dictionary      softPolicyId=${spId_CurrentOD_comm}        appId=${cp_app_id}    id=${soft_putId_${5}}    value=${value_CurrentOD_comm_update}
        ${Current OD_credit card}=                        Create Dictionary      softPolicyId=${spId_CurrentODCC_comm}      appId=${cp_app_id}    id=${soft_putId_${6}}    value=${value_CurrentODCC_comm_update}
        ${24 months:Substd, doubtful, loss, restructured/write off_comm}=        Create Dictionary      softPolicyId=${spId_24m_Subs_comm}   appId=${cp_app_id}    id=${soft_putId_${7}}    value=${value_24m_Subs_comm_update}
        ${48 months:Wilful or suit filed_comm}=           Create Dictionary      softPolicyId=${spId_48m_Wilful_comm}       appId=${cp_app_id}    id=${soft_putId_${8}}    value=${value_48m_Wilful_comm_update}
        ${Bureau_con}=                                    Create Dictionary      softPolicyId=${spId_Bureau_con}            appId=${cp_app_id}    id=${soft_putId_${9}}    value=${value_Bureau_con_update}
        ${CurrentOD_con}=                                 Create Dictionary      softPolicyId=${spId_CurrentOD_con}         appId=${cp_app_id}    id=${soft_putId_${10}}    value=${value_Current OD_con_update}
        ${CurrentOD_credit card_con}=                     Create Dictionary      softPolicyId=${spId_CurrentODCC_con}       appId=${cp_app_id}    id=${soft_putId_${11}}    value=${value_CurrentODCC_con_update}
        ${24 months:Substd, doubtful, loss, restructured/write off_con}=         Create Dictionary      softPolicyId=${spId_24m_Subs_con}     appId=${cp_app_id}    id=${soft_putId_${12}}    value=${value_24m_Subs_con_update}
        ${36 months:Wilful or suit filed_con}=            Create Dictionary      softPolicyId=${spId_36m_Wilful_con}        appId=${cp_app_id}    id=${soft_putId_${13}}    value=${value_36m_Wilful_con_update}
        ${Latest EPFO delay in months}=                   Create Dictionary      softPolicyId=${spId_EPFO}                  appId=${cp_app_id}    id=${soft_putId_${14}}    value=${value_EPFO_update}
        ${Latest GST Payment delay in months}=            Create Dictionary      softPolicyId=${spId_GST}                   appId=${cp_app_id}    id=${soft_putId_${15}}    value=${value_GST_update}
        ${State should be in the serviceable area}=       Create Dictionary      softPolicyId=${spId_State}                 appId=${cp_app_id}    id=${soft_putId_${16}}    value=${value_State_update}
        ${Age of Main Promoter: In Years}=                Create Dictionary      softPolicyId=${spId_Age}                   appId=${cp_app_id}    id=${soft_putId_${17}}    value=${value_Age_update}

        ${list}=            Create List          ${bureau_comm}    ${SMA-0/1-30 12 Months}    ${SMA-1/31-60 12 months}      ${SMA-2/61-90 6 month}  ${SMA-2/61-90 6-12 months}    ${CurrentOD_comm}    ${Current OD_credit card}    ${24 months:Substd, doubtful, loss, restructured/write off_comm}    ${48 months:Wilful or suit filed_comm}      ${Bureau_con}    ${CurrentOD_con}     ${CurrentOD_credit card_con}    ${24 months:Substd, doubtful, loss, restructured/write off_con}    ${36 months:Wilful or suit filed_con}      ${Latest EPFO delay in months}   ${Latest GST Payment delay in months}   ${State should be in the serviceable area}    ${Age of Main Promoter: In Years}    
        &{data}=            Create Dictionary    softPolicyDetailsDataList=${list}
        ${header}=          Create Dictionary    Authorization=${Token}      content-type=application/json
        ${response}=        Put Request         updateSoftPolicy               /softPolicyDetails     data=&{data}    headers=${header}
        ${status_code}=     convert to string    ${response.status_code}
        Should Be Equal     ${status_code}    ${expected_code}
        