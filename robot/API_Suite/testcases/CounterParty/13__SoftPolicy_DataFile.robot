*** Settings ***
Resource               ../../keywords/variables.robot
Test Template          TC_SP131_softPolicy_details_negative testing
Suite Setup            CPA_underwriter_Authentication
*** Keywords ***
CPA_underwriter_Authentication
    ${username}    Set Variable       cpa_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}

*** Variables ***

${Bureau_score_id}=             148
${SMA0_id}=                     149
${SMA1_id}=                     150
${SMA2_id}=                     151
${SMA2(6-12_id)}=               152
${Current OD_id}=               153
${Current OD(CC)_id}=           154
${24_Subs}=                     155
${wilful_id}=                   156
${BureauScore-cons_id}=         157
${Current OD-cons_id}=          158
${Current OD(CC)-cons_id}=      159
${24_Subs-cons_id}=             160
${Wilful-cons_id}=              161
${EPFO delay_id}=               162
${GST delay_id}=                163
${State_id}=                    164
${Age_id}=                      165
           
*** Test Cases ***                                              softPolicyId	       	          appId                   value
TC_SP06_ALphabets Bureau Score Commercial                       ${Bureau_score_id}	      	      ${cp_app_id}             xyz
TC_SP07_Missing Bureau Score Commercial                         ${Bureau_score_id}               ${cp_app_id}             ${EMPTY}
TC_SP08_AlphaNumeric Bureau Score Commercial                    ${Bureau_score_id}               ${cp_app_id}             12abc
TC_SP09_Symbols in Bureau Score Commercial                      ${Bureau_score_id}               ${cp_app_id}             ^&*12
TC_SP10_Negative value in Bureau Score Commercial               ${Bureau_score_id}               ${cp_app_id}             -78987
TC_SP11_Invalid value in Bureau Score Commercial                ${Bureau_score_id}               ${cp_app_id}             12.908
TC_SP12_Invalid length in Bureau Score Commercial               ${Bureau_score_id}               ${cp_app_id}             12345678901234567890 

TC_SP13_Alphabet SMA-0                                          ${SMA0_id}	                      ${cp_app_id} 	           jkl
TC_SP14_Missing SMA-0                                           ${SMA0_id}                       ${cp_app_id}             ${EMPTY}
TC_SP15_AlphaNumeric SMA-0                                      ${SMA0_id}                       ${cp_app_id}             12sds
TC_SP16_Symbols in SMA-0                                        ${SMA0_id}                       ${cp_app_id}             *()op90
TC_SP17_Negative value in SMA-0                                 ${SMA0_id}                       ${cp_app_id}             -9097
TC_SP18_Invalid value in SMA-0                                  ${SMA0_id}                       ${cp_app_id}             12.908
TC_SP19_Invalid length in SMA-0                                 ${SMA0_id}                       ${cp_app_id}             12345678901234567890    

TC_SP20_Alphabet SMA-1                                          ${SMA1_id}                       ${cp_app_id}             ops
TC_SP21_Missing SMA-1                                           ${SMA1_id}                       ${cp_app_id}             ${EMPTY}
TC_SP23_AlphaNumeric SMA-1                                      ${SMA1_id}                       ${cp_app_id}             89080PLWjk
TC_SP24_Symbols in SMA-1                                        ${SMA1_id}                       ${cp_app_id}             %^&%*&90hj
TC_SP25_Negative value in SMA-1                                 ${SMA1_id}                       ${cp_app_id}             -09121
TC_SP26_Invalid value in SMA-1                                  ${SMA1_id}                       ${cp_app_id}             90.90990
TC_SP27_Invalid length in SMA-1                                 ${SMA1_id}                       ${cp_app_id}             12345678901234567890

TC_SP28_Alphabet SMA-2                                          ${SMA2_id}	                      ${cp_app_id} 	           eps
TC_SP29_Missing SMA-2                                           ${SMA2_id}                       ${cp_app_id}             ${EMPTY}
TC_SP30_AlphaNumeric SMA-2                                      ${SMA2_id}                       ${cp_app_id}             89080PLWjk
TC_SP31_Symbols in SMA-2                                        ${SMA2_id}                       ${cp_app_id}             %^&%*&90hj
TC_SP32_Negative value in SMA-2                                 ${SMA2_id}                       ${cp_app_id}             -09121
TC_SP33_Invalid value in SMA-2                                  ${SMA2_id}                       ${cp_app_id}             90.90990
TC_SP34_Invalid length in SMA-2                                 ${SMA2_id}                       ${cp_app_id}             12345678901234567890

TC_SP35_Alphabet SMA-2(6-12 months)                             ${SMA2(6-12_id)}	              ${cp_app_id} 	           eps
TC_SP36_Missing SMA-2(6-12 months)                              ${SMA2(6-12_id)}                 ${cp_app_id}             ${EMPTY}
TC_SP37_AlphaNumeric SMA-2(6-12 months)                         ${SMA2(6-12_id)}                 ${cp_app_id}             89080PLWjk
TC_SP38_Symbols in SMA-2(6-12 months)                           ${SMA2(6-12_id)}                 ${cp_app_id}             %^&%*&90hj
TC_SP39_Negative value in SMA-2(6-12 months)                    ${SMA2(6-12_id)}                 ${cp_app_id}             -09121
TC_SP40_Invalid value in SMA-2(6-12 months)                     ${SMA2(6-12_id)}                 ${cp_app_id}             90.90990
TC_SP41_Invalid length in SMA-2(6-12 months)                    ${SMA2(6-12_id)}                 ${cp_app_id}             12345678901234567890

TC_SP42_Alphabet Current OD                                     ${Current OD_id}	              ${cp_app_id} 	           abc
TC_SP43_Missing Current OD                                      ${Current OD_id}                 ${cp_app_id}             ${EMPTY}
TC_SP44_AlphaNumeric Current OD                                 ${Current OD_id}                 ${cp_app_id}             12abc
TC_SP45_Symbols in Current OD                                   ${Current OD_id}                 ${cp_app_id}             &*1  
TC_SP46_Negative value in Current OD                            ${Current OD_id}                 ${cp_app_id}            -72727 
TC_SP47_Invalid value in Current OD                             ${Current OD_id}                 ${cp_app_id}             12.34 
TC_SP48_Invalid length in Current OD                            ${Current OD_id}                ${cp_app_id}             12345678901234567890

TC_SP49_Alphabet Current OD for CC                              ${Current OD(CC)_id}	          ${cp_app_id} 	           abc
TC_SP50_Missing Current OD for CC                               ${Current OD(CC)_id}             ${cp_app_id}             ${EMPTY}
TC_SP51_AlphaNumeric Current OD for CC                          ${Current OD(CC)_id}             ${cp_app_id}             12abc
TC_SP52_Symbols in Current OD for CC                            ${Current OD(CC)_id}             ${cp_app_id}             &*1  
TC_SP53_Negative value in Current OD for CC                     ${Current OD(CC)_id}             ${cp_app_id}             -72727 
TC_SP54_Invalid value in Current OD for CC                      ${Current OD(CC)_id}             ${cp_app_id}             12.34 
TC_SP55_Invalid length in Current OD for CC                     ${Current OD(CC)_id}             ${cp_app_id}             12345678901234567890

TC_SP56_Alphabet 24m_Subs                                       ${24_Subs}	                  ${cp_app_id} 	           qwe
TC_SP57_Missing 24m_Subs                                         ${24_Subs}                   ${cp_app_id}             ${EMPTY}
TC_SP58_AlphaNumeric 24m_Subs                                    ${24_Subs}                   ${cp_app_id}             12abc
TC_SP59_Symbols 24m_Subs                                         ${24_Subs}                   ${cp_app_id}             &*1  
TC_SP60_Negative value in 24m_Subs                               ${24_Subs}                   ${cp_app_id}             -72727 
TC_SP61_Invalid value in 24m_Subs                                ${24_Subs}                   ${cp_app_id}             12.34 
TC_SP62_Invalid length in 24m_SUbs                               ${24_Subs}                   ${cp_app_id}             12345678901234567890

TC_SP63_Alphabet Wilful or suit filed	                         ${wilful_id}	                  ${cp_app_id} 	           asdf
TC_SP64_Missing Wilful or suit filed	                         ${wilful_id}                     ${cp_app_id}             ${EMPTY}
TC_SP65_AlphaNumeric Wilful or suit filed	                     ${wilful_id}                     ${cp_app_id}             12abc
TC_SP66_Symbols Wilful or suit filed	                         ${wilful_id}                     ${cp_app_id}             &*1  
TC_SP67_Negative value in Wilful or suit filed	                 ${wilful_id}                     ${cp_app_id}             -72727 
TC_SP68_Invalid value in Wilful or suit filed	                 ${wilful_id}                     ${cp_app_id}             12.34 
TC_SP69_Invalid length in Wilful or suit filed                   ${wilful_id}                     ${cp_app_id}             12345678901234567890

TC_SP70_ALphabet Bureau Score Consumer                      	${BureauScore-cons_id}	      	  ${cp_app_id}             xyz
TC_SP71_Missing Bureau                                          ${BureauScore-cons_id}           ${cp_app_id}             ${EMPTY}
TC_SP72_AlphaNumeric BUreau Score Consumer                      ${BureauScore-cons_id}           ${cp_app_id}             12abc
TC_SP73_Symbols in Bureau Score Consumer                        ${BureauScore-cons_id}           ${cp_app_id}             ^&*12
TC_SP74_Negative value in Bureau Score Consumer                 ${BureauScore-cons_id}           ${cp_app_id}             -78987
TC_SP75_Invalid value in Bureau Score Consumer	                 ${BureauScore-cons_id}           ${cp_app_id}             12.34
TC_SP76_Invalid length in Bureau Score Consumer                 ${BureauScore-cons_id}                     ${cp_app_id}             12345678901234567890

TC_SP77_Alphabet Current OD Consumer	                         ${Current_OD-cons_id}	          ${cp_app_id} 	           jdk
TC_SP78_Missing Current OD Consumer                             ${Current_OD-cons_id}            ${cp_app_id}             ${EMPTY}
TC_SP79_AlphaNumeric Current OD Consumer                        ${Current_OD-cons_id}            ${cp_app_id}             12abc
TC_SP80_Symbols in Current OD Consumer                          ${Current_OD-cons_id}            ${cp_app_id}             &*1  
TC_SP81_Negative value in Current OD Consumer                   ${Current_OD-cons_id}            ${cp_app_id}             -72727 
TC_SP82_Invalid value in Current OD Consumer                    ${Current_OD-cons_id}            ${cp_app_id}             12.34
TC_SP83_Invalid length in Current OD Consumer                   ${Current_OD-cons_id}                    ${cp_app_id}             12345678901234567890

TC_SP84_Alphabet Current ODCC Consumer	                          ${Current OD(CC)-cons_id}          ${cp_app_id} 	           jdk
TC_SP85_Alphabet Current ODCC Consumer	                          ${Current OD(CC)-cons_id}	          ${cp_app_id}             ${EMPTY}
TC_SP86_AlphaNumeric Current ODCC Consumer                       ${Current OD(CC)-cons_id}            ${cp_app_id}             12abc
TC_SP87_Symbols in Current ODCC Consumer                         ${Current OD(CC)-cons_id}            ${cp_app_id}             &*1  
TC_SP88_Negative value in Current ODCC Consumer                  ${Current OD(CC)-cons_id}            ${cp_app_id}             -72727 
TC_SP89_Invalid value in Current ODCC Consumer                   ${Current OD(CC)-cons_id}            ${cp_app_id}             12.34   
TC_SP90_Invalid length in Current ODCC Consumer                  ${Current OD(CC)-cons_id}                    ${cp_app_id}             12345678901234567890

TC_SP91_Alphabet 24m_Subs Consumer                              ${24_Subs-cons_id}	          ${cp_app_id} 	           qwe
TC_SP92_Missing 24m_Subs Consumer                               ${24_Subs-cons_id}              ${cp_app_id}             ${EMPTY}
TC_SP93_AlphaNumeric 24m_Subs Consumer                          ${24_Subs-cons_id}              ${cp_app_id}             12abc
TC_SP94_Symbols 24m_Subs Consumer                               ${24_Subs-cons_id}              ${cp_app_id}             &*1  
TC_SP95_Negative value in 24m_Subs Consumer                     ${24_Subs-cons_id}              ${cp_app_id}             -72727 
TC_SP96_Invalid value in 24m_Subs Consumer                      ${24_Subs-cons_id}              ${cp_app_id}             12.34 
TC_SP97_Invalid length in 24m_Subs Consumer                     ${24_Subs-cons_id}                     ${cp_app_id}             12345678901234567890

TC_SP98_Alphabet Wilful or suit filed Consumer                  ${Wilful-cons_id}	              ${cp_app_id} 	           asdf
TC_SP99_Missing Wilful or suit filed Consumer	                 ${Wilful-cons_id}                ${cp_app_id}             ${EMPTY}
TC_SP100_AlphaNumeric Wilful or suit filed Consumer	             ${Wilful-cons_id}                ${cp_app_id}             12abc
TC_SP101_Symbols Wilful or suit filed Consumer	                 ${Wilful-cons_id}                ${cp_app_id}             &*1  
TC_SP102_Negative value in Wilful or suit filed Consumer	     ${Wilful-cons_id}                ${cp_app_id}             -72727 
TC_SP103_Invalid value in Wilful or suit filed Consumer         ${Wilful-cons_id}                    ${cp_app_id}             12345678901234567890

TC_SP104_Alphabet Latest EPFO Delay in Months	                ${EPFO delay_id}            	      ${cp_app_id} 	        poi
TC_SP105_Missing Latest EPFO Delay in Months	                ${EPFO delay_id}            	      ${cp_app_id} 	        ${EMPTY}
TC_SP106_ALphaNumeric Latest EPFO Delay in Months	            ${EPFO delay_id}            	      ${cp_app_id} 	        12jkl
TC_SP107_Symbols Latest EPFO Delay in Months	                ${EPFO delay_id}            	      ${cp_app_id} 	        *()*)*
TC_SP108_Negative Latest EPFO Delay in Months	                ${EPFO delay_id}            	      ${cp_app_id} 	        -923980
TC_SP109_Invalid Latest EPFO Delay in Months	                ${EPFO delay_id}            	      ${cp_app_id} 	        12.908
TC_SP110_Invalid Latest EPFO Delay in Months                    ${EPFO delay_id}                     ${cp_app_id}             12345678901234567890

TC_SP111_ALphabet Latest GST Payment Delay                       ${GST delay_id}            	      ${cp_app_id} 	        stv
TC_SP112_Missing Latest GST Payment Delay                        ${GST delay_id}            	      ${cp_app_id} 	        ${EMPTY}
TC_SP113_Alphanumeric Latest GST Payment Delay                   ${GST delay_id}                	  ${cp_app_id} 	        89iop
TC_SP114_Symbols Latest GST Payment Delay                        ${GST delay_id}            	      ${cp_app_id} 	        &@8
TC_SP115_Negative Latest GST Payment Delay                       ${GST delay_id}            	      ${cp_app_id} 	        -23817
TC_SP116_Invalid Latest GST Payment Delay                        ${GST delay_id}                   ${cp_app_id} 	        90.2398
TC_SP117_Invalid Latest GST Payment Delay                        ${GST delay_id}                     ${cp_app_id}             12345678901234567890

TC_SP118_Numeric State 	                                         ${State_id}	                      ${cp_app_id} 	        ${20}
TC_SP119_Missing State                                           ${State_id}            	          ${cp_app_id} 	        ${EMPTY}
TC_SP120_Alphanumeric State                                      ${State_id}                       ${cp_app_id} 	        89iop
TC_SP121_Symbols State                                           ${State_id}            	          ${cp_app_id} 	        &@8
TC_SP122_Negative State                                          ${State_id}           	          ${cp_app_id} 	        -23817
TC_SP123_Invalid State                                           ${State_id}            	          ${cp_app_id} 	        90.2398
TC_SP124_Invalid State                                           ${State_id}                     ${cp_app_id}             12345678901234567890

TC_SP125_Missing Age	                    ${Age_id}	                      ${cp_app_id} 	        ${EMPTY}                            
TC_SP126_Alphanumeric Age	                ${Age_id}	                      ${cp_app_id} 	        23809pk
TC_SP127_Symbols Age	                    ${Age_id}	                      ${cp_app_id} 	        %&*@#
TC_SP128_Negative Age	                    ${Age_id}	                      ${cp_app_id} 	        -2983798
TC_SP129_Invalid Age	                    ${Age_id}	                      ${cp_app_id} 	        982.232
TC_SP130_Invalid Age                        ${Age_id}                         ${cp_app_id}             12345678901234567890


