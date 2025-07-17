*** Settings ***
Resource    ../../keywords/variables.robot
Test Template     TC_CN_CreditNorms_DataFile_Negative_Validation
Suite Setup        CPA_Authentication

*** Keywords ***
CPA_Authentication
    ${username}    Set Variable       cpa_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}
*** Variables ***
${MinVint}           263             #Months
${MinPurc}           266  
${ProPanc}           389 
${PromOvd}           390
${BusiPan}           391
${Gst_Cert}          392            #Dropdown

*** Test Cases ***                                        appId                      value           cnMasterId
TC_CN_01_Missing Minimum vintage with Anchor             ${Anchor_App_id}           ${EMPTY}         ${MinVint}   
TC_CN_01_Invalid Minimum vintage with Anchor             ${Anchor_App_id}           1.45             ${MinVint}
TC_CN_01_AlphaNumeric value in Anchor vintage            ${Anchor_App_id}           12ab             ${MinVint}
TC_CN_01_Symbols in Anchor vintage                       ${Anchor_App_id}           23#$             ${MinVint}
TC_CN_01_Negative value in Anchor vintage                ${Anchor_App_id}           -12              ${MinVint}
TC_CN_01_Invalid length for Anchor vintage               ${Anchor_App_id}           126363674        ${MinVint}

TC_CN_02_Missing Min Annual purchase                     ${Anchor_App_id}           ${EMPTY}         ${MinPurc}                                                            
TC_CN_02_Symbols Min Annual purchase                     ${Anchor_App_id}           12%^             ${MinPurc}
TC_CN_02_AlphaNumeric Min Annual purchase                ${Anchor_App_id}           12qta            ${MinPurc}
TC_CN_02_Negative value in Min Annual purchase           ${Anchor_App_id}           -123             ${MinPurc}    

TC_CN_03_Missing Promoter/Gurantor PAN                   ${Anchor_App_id}           ${EMPTY}         ${ProPanc}
TC_CN_04_Missing Promoter/Gurantor OVD                   ${Anchor_App_id}           ${EMPTY}         ${PromOVD}
TC_CN_05_Missing Business PAN                            ${Anchor_App_id}           ${EMPTY}         ${BusiPan}
TC_CN_06_Missing Gst Certificate                         ${Anchor_App_id}           ${EMPTY}         ${Gst_Cert}


















