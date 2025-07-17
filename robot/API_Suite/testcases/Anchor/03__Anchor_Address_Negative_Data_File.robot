*** Settings ***
Resource           ../../keywords/variables.robot
Test Template     TC_AD_05_AnchorAddressDetail_Update_Negative
Suite Setup       CPA_Authentication


*** Variables ***
${Address_putId}=       7956

*** Keywords ***
CPA_Authentication
    ${username}    Set Variable       cpa_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}
    
*** Test Cases ***                ${addressLine1}          ${addressLine2}         ${appId}            ${city}         ${country}          ${Address_putId}          ${pinCode}          ${state} 
# TC_AD_06_EmptyAddress1               ${EMPTY}                 T.Nagar           ${Anchor_App_id}       Chennai           India             ${Address_putId}            630106            Tamil Nadu
TC_AD_07_EmptyAppID                  Koyambedu                Market            ${Empty}               Madurai           India             ${Address_putId}            630106            Tamil Nadu    
TC_AD_08_EmptyCity                   Koyambedu                Market            ${Anchor_App_id}       ${EMPTY}          India             ${Address_putId}            630106            Tamil Nadu
TC_AD_09_EmptyCountry                Koyambedu                Market            ${Anchor_App_id}       Madurai           ${Empty}          ${Address_putId}            630106            Tamil Nadu
TC_AD_10_EmptyPinCode                Koyambedu                Market            ${Anchor_App_id}       Madurai           India             ${Address_putId}            ${Empty}          Tamil Nadu
TC_AD_11_EmptyState                  Koyambedu                Market            ${Anchor_App_id}       Madurai           India             ${Address_putId}            630106            ${Empty}
TC_AD_12_InvalidAnchorAppID          Theni                    Richie Street       9398#99              Singapore         India             ${Address_putId}            630106            Tamil Nadu
TC_AD_13_InvalidCity                 Karakiudi                Chennai           ${Anchor_App_id}       --&**             India             ${Address_putId}            630106            Tamil Nadu
TC_AD_14_InvalidCountry              Salem                    Richie Street     ${Anchor_App_id}       Chennai           --*&^%            ${Address_putId}            630106            Tamil Nadu
TC_AD_15_InvalidPincode              Theni                    Richie Street     ${Anchor_App_id}       Dindugal          India             ${Address_putId}            rr##$$$%          Tamil Nadu
TC_AD-16_InvalidSatate               Theni                    Richie Street     ${Anchor_App_id}       Dindugal          India             ${Address_putId}            630108            us##$$$%
TC_AD_17_EmptyAllFields              ${EMPTY}                 ${EMPTY}          ${EMPTY}               ${EMPTY}          ${EMPTY}          ${EMPTY}                    ${EMPTY}          ${EMPTY}
TC_AD_18_SymbolsAllFields            *&&&*                    (*&*&^            *&&**                   (*&*&*)          98*&               (*&&)                      (&(*&))           (*&(*&))                    
TC_AD_19_NumbersInAllFields          98797                    98788                6879877               98797           987987               78                        87987             7879
TC_AD_20_ZeroInAllFields               0                        0                    0                    0               0                   0                          0                  0  

