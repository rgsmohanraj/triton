*** Settings ***
Resource    ../resources/imports.robot

*** Variables ***
${browser}                  chrome
${url}=                     http://10.100.10.41:8282/triton/index.html 

${userName}=                cpa_lead    
${password}=                admin@1234    
# CPA Lead            
${cpaLeadUser}=             cpa_lead
${cpaLeadPw}=               admin@1234    
# CPA 
${cpaUser}=                 CPA
${cpaPw}=                   admin@1234
# credit_underwriter Lead
${creditUWLeadUser}=        credit_underwriter_lead
${creditUWLeadPw}=          admin@1234
# Credit Underwriter
${creditUW}=                credit_underwriter
${creditUWPw}=              admin@1234
# Operation maker Lead
${opsMakerLeadUser}=        operation_maker_lead
${opsMakerLeadPw}=          admin@1234
#Operation maker
${opsMakerUser}=            operation_maker
${opsMakerPw}=              admin@1234
# Operation checker lead
${opsChkLeadUser}=          operation_checker_lead
${opsChkLeadPw}=            admin@1234
#Operation checker
${opsChkUser}=              operation_checker
${opsChkPw}=                admin@1234
# Credit head lead
${creditHLUser}=            credit_head_lead
${creditHLPw}=              admin@1234    
#Credit Head
${creditHeadUser}=          credit_head
${creditHeadPw}=            admin@1234
# Business Lead    
${businessLeadUser}=        business_lead
${businessLeadPw}=          admin@1234    
#Business
${businessUser}=            business            
${businessPw}=              admin@1234
#Risk Underwriter
${riskUnderwriterUser}=     risk_underwriter
${riskUnderwriterPw}=       admin@1234
#Risk Underwriter Lead
${riskUnderwriterLeadUser}=     risk_underwriter_lead
${riskUnderwriterLeadPw}=       admin@1234
#Credit Underwriter
${creditUnderwriterUser}=       credit_underwriter
${creditUnderwriterPw}=         admin@1234
#Risk Underwriter Lead
${riskULUser}=                  risk_underwriter_lead
${riskULPw}=                    admin@1234
#Risk Head
${riskHeadUser}=                Risk head
${riskHeadPw}=                  admin@1234
#Commercial commitee
${commercialCommitUser}=        commercial_approval_committee_lead
${commercialPw}=                admin@1234
#Credit Committe
${creditCommitUser}=            credit_approval_committee_lead
${creditPw}=                    admin@1234
#Deferral Committee Lead
${deferralCommitLeadUser}=      deferral_committee_lead@vivriticapital.com
${deferralCommitLeadPw}=        admin@1234

${RM_name}=                     Durgaa Vadivel
${anchorName}=                  Julie Limited
${cpName}=                      Skyhigh Enterprise

# ${RM Name}=                     Durgaa Vadivel
# ${anchorName2}=                 Vendor Non-Reco Pvt Ltd
# ${anchorName3}=                 Dealer Non-Reco Pvt Ltd



