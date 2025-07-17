*** Settings ***
Resource               ../../keywords/variables.robot
Suite Setup            CPA_Authentication
*** Keywords ***
CPA_Authentication
    ${username}    Set Variable       cpa_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}

*** Variables ***
${expt_display_name1_type}      Commercial Bureau
${expt_display_name2_type}      Consumer Bureau: For Main Promoter     
${expt_display_name3_type}      Geo Restriction
${expt_display_name4_type}      Statutory Defaults
${expt_display_name5_type}      Age of Promoter
${expt_display_name1_subtype}   Bureau Score
${expt_display_name2_subtype}   SMA-0/1-30 DPD in the last 12 Months
${expt_display_name3_subtype}   SMA-1/31-60 DPD in the last 12 months 
${expt_display_name4_subtype}   SMA-2/61-90 DPD in the last 6 month 
${expt_display_name5_subtype}   SMA-2/61-90 DPD in 6-12 months
${expt_display_name6_subtype}   Current OD 
${expt_display_name7_subtype}   Current OD for credit card 
${expt_display_name8_subtype}   In last 24 months: Substd, doubtful, loss, restructured/write off 
${expt_display_name9_subtype}   In the last 48 months: Wilful or suit filed 
${expt_display_name10_subtype}  Bureau Score
${expt_display_name11_subtype}  Current OD 
${expt_display_name12_subtype}  Current OD for credit card 
${expt_display_name13_subtype}  In last 24 months : Substd, doubtful, loss, restructured/write off 
${expt_display_name14_subtype}  In the last 36 months: Wilful or suit filed 
${expt_display_name15_subtype}  Latest EPFO delay in months 
${expt_display_name16_subtype}  Latest GST Payment delay in months 
${expt_display_name17_subtype}  State should be in the serviceable area
${expt_display_name18_subtype}  Age of Main Promoter: In Years

*** Test Cases ***

TC_SP132_CounterParty_SoftPolicy_DisplayName1_Validation
    [Documentation]    SoftPolicy display name1 validation
    ${Softpolicy_master_list}=   softpolicy_master_type
    Should Be Equal    ${Softpolicy_master_list}[0]  ${expt_display_name1_type}

TC_SP133_CounterParty_SoftPolicy_DisplayName2_Validation
    [Documentation]    SoftPolicy display name2 validation
    ${Softpolicy_master_list}=   softpolicy_master_type
    Should Be Equal    ${Softpolicy_master_list}[1]  ${expt_display_name2_type}

TC_SP134_CounterParty_SoftPolicy_DisplayName3_Validation
    [Documentation]    SoftPolicy display name3 validation
    ${Softpolicy_master_list}=   softpolicy_master_type
    Should Be Equal    ${Softpolicy_master_list}[2]  ${expt_display_name3_type}

TC_SP135_CounterParty_SoftPolicy_DisplayName4_Validation
    [Documentation]    SoftPolicy display name4 validation
    ${Softpolicy_master_list}=   softpolicy_master_type
    Should Be Equal    ${Softpolicy_master_list}[3]  ${expt_display_name4_type}

TC_SP136_CounterParty_SoftPolicy_DisplayName5_Validation
    [Documentation]    SoftPolicy display name5 validation
    ${Softpolicy_master_list}=   softpolicy_master_type
    Should Be Equal    ${Softpolicy_master_list}[4]  ${expt_display_name5_type}

TC_SP137_CounterParty_SoftPolicy_DisplayName1_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[0]  ${expt_display_name1_subtype}

TC_SP138_CounterParty_SoftPolicy_DisplayName2_subtype_Validation
    [Documentation]    SoftPolicy display name2 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[1]  ${expt_display_name2_subtype}

TC_SP139_CounterParty_SoftPolicy_DisplayName3_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[2]  ${expt_display_name3_subtype}

TC_SP140_CounterParty_SoftPolicy_DisplayName4_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[3]  ${expt_display_name4_subtype}

TC_SP141_CounterParty_SoftPolicy_DisplayName5_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[4]  ${expt_display_name5_subtype}

TC_SP142_CounterParty_SoftPolicy_DisplayName6_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[5]  ${expt_display_name6_subtype}

TC_SP143_CounterParty_SoftPolicy_DisplayName7_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[6]  ${expt_display_name7_subtype}

TC_SP144_CounterParty_SoftPolicy_DisplayName8_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[7]  ${expt_display_name8_subtype}

TC_SP145_CounterParty_SoftPolicy_DisplayName9_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[8]  ${expt_display_name9_subtype}

TC_SP146_CounterParty_SoftPolicy_DisplayName10_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[9]  ${expt_display_name10_subtype}

TC_SP147_CounterParty_SoftPolicy_DisplayName11_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[10]  ${expt_display_name11_subtype}

TC_SP148_CounterParty_SoftPolicy_DisplayName12_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[11]  ${expt_display_name12_subtype}

TC_SP149_CounterParty_SoftPolicy_DisplayName13_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[12]  ${expt_display_name13_subtype}

TC_SP150_CounterParty_SoftPolicy_DisplayName14_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[13]  ${expt_display_name14_subtype}

TC_SP151_CounterParty_SoftPolicy_DisplayName15_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[14]  ${expt_display_name15_subtype}

TC_SP152_CounterParty_SoftPolicy_DisplayName16_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[15]  ${expt_display_name16_subtype}

TC_SP153_CounterParty_SoftPolicy_DisplayName17_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[16]  ${expt_display_name17_subtype}

TC_SP154_CounterParty_SoftPolicy_DisplayName18_subtype_Validation
    [Documentation]    SoftPolicy display name1 subtype validation
    ${Softpolicy_master_subtype_list}=     softpolicy_master_sub_type
    Should Be Equal    ${Softpolicy_master_subtype_list}[17]  ${expt_display_name18_subtype}

    