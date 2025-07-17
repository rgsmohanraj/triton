*** Settings ***
Resource        ../../keywords/common.robot
Resource        ../../PageObjects/AnchorPageObjects/02__creditNormsPage.robot
Resource     ../CounterPartyPageObjects/01__businessPage.robot

*** Variables ***
# Limit Eligibility
${CurrentLimit}           1230.23    
${ProposedLimit}          1340.23    
${eligibleLimit}          10000000    
${adhocLimit}             10000000    
${doorToDoor}               2
${invoiceAgeing}            12
${expectedGrowthInTo}       13
${montlyAvgToWithAnchor}   124556
${approExWCLmt}            12456
${exiSCFLmtFrmOthers}      23455
${modelLimit}              1000000
${anchorRecomAmount}       1000000
${expMontTurnOvrWithAnc}   1000000
${modelAdhocLimit}         1000000
#Term Sheet
${tShtAdhocLimit}          1000000
${tShtCreditPeriod}          4
${tShtDoorToDoor}            2
${tShInvoice}                12
${tShtInterest}              23.24
${termSheetPf}              12.23
${tShtRenewal}              14
${IntRateType}              Floating
${tShtRenwalPeriod}          12
${tShtArrInvFund}            13
${tShtGraceDays}             2
${termSheetRegLmt}          1234
${termSheetMargin}           12

#Collateral
${primarySecurity}            Test
${secondarySecurity}          Test
${guarantees}                 Test            
${pre-disbtcreditcond}        Test
${financialcovenants}         Test
${timeforcompliance}            4
${postdisbcreditcond}         Test

#Credit Policy
${vintageWithAnchor}           1
${minMonthlyAnchor}           120000
${anchorRelaship}             Low
${BusinVintinMonth}            2
${PAT1}                        12        
${ATNW}                   1234
${InwrdChqBou}                2
${BankChurn}                 12
${com_curntod_crdcard}       23253
${com_curntod_Noncrdcard}    24355
${com_31-60dpd}               2    
${com_61-90dpd}               7
${com_61-90dpd_6-12}          3 
${com_last24months}           4
${com_last48months}           12
${cons_BureauScore}           300
${cons_curtod_crdcard}       23253
${cons_curtod_Noncditcrd}    24355        
${cons_last24months}           4
${cons_last48months}           12
${GSTpay}                    23    
${EPFO}                      1000        
${TotalInwrdchk}              345    
${user1}                    credit_underwriter_lead


*** Keywords ***
Underwriter Review
    [Documentation]    Underwriter Review
    UnderWriter_Login
    CP List
    CP Details
    View CAM
    Limit Eligiblity
    Term Sheet
    Collateral
    Credit Policy
    Remarks Screen
UnderWriter_Login
    Log     \n Proposed amount in keyword: ${proposed_amt}
    IF  ${proposed_amt}<=40000000
        ${userName1}=      Set Variable      ${creditUWLeadUser}
        ${password1}=      Set Variable      ${creditUWLeadPw}
     ELSE
        ${userName1}=      Set Variable      ${riskULUser}
        ${password1}=      Set Variable      ${riskULPw}    
    END
    IF  '${userName1}'=='${user1}'
        ${userSelect1}=                      Set Variable    CREDIT UNDERWRITER
        ${userName2}=    Set Variable    ${creditUnderwriterUser}
        ${password2}=    Set Variable    ${creditUnderwriterPw}
    ELSE
        ${userSelect1}=                      Set Variable    RISK UNDERWRITER
        ${userName2}=    Set Variable    ${riskUnderwriterUser}
        ${password2}=    Set Variable    ${riskUnderwriterPw}    
    END  
    assignmentPage.Search the case      ${userName1}    ${password1}    ${userSelect1}    ${caseName}
    loginPage.login to Triton   ${userName2}     ${password2}

CP List
    [Documentation]    CP List
    Set Selenium Speed    0.5s
    Sleep    5
    Set Focus To Element                    xpath://span[text()='Inbox'] 
    Wait Until Page Contains Element        xpath://span[text()='Inbox']    timeout=10s
    Click Element                           xpath://span[text()='Inbox'] 
    Sleep    1
    Run Keyword    Select the case in Inbox

CP Details 
    [Documentation]    CP Details
    Wait Until Page Contains Element       xpath:(//span[contains(text(),'CP Details')])[6]     timeout=10s
        Sleep    2
    ${uwrNext}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[5]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${uwrNext}'=='False'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[5]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[5]
    Click Element                          xpath:(//button[contains(text(),'Next')])[5]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[4]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[4]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[4]
    Click Element                          xpath:(//button[contains(text(),'Next')])[4]
    END
    Sleep    2
View CAM
    [Documentation]    View CAM 
    Sleep    1  
    Set Focus To Element        xpath:(//button[@class='btn btn-info me-2'])[1]
    Wait Until Page Contains Element    xpath:(//button[@class='btn btn-info me-2'])[1]
    Click Button                xpath:(//button[@class='btn btn-info me-2'])[1] 
        Sleep    2
    ${Next}=     Run Keyword And Return Status    Execute JavaScript       var xpath = "(//button[contains(text(),'Next')])[5]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${Next}' == 'False'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[5]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[5]
    Click Element                          xpath:(//button[contains(text(),'Next')])[5]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[4]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[4]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[4]
    Click Element                          xpath:(//button[contains(text(),'Next')])[4]
    END
    Sleep    2

Limit Eligiblity
   [Documentation]    Limit Eligibility 
    #Fund Request Details  
    Execute Javascript    window.scrollTo(0,-1000)
    Input Text    xpath:(//input[@type='text'])[2]    Test
    Input Text    xpath:(//input[@type='text'])[3]    Test    
    Input Text    xpath:(//input[@type='text'])[4]    Test    
    Input Text    xpath:(//input[@type='text'])[5]    Test
    Input Text    xpath:(//input[@type='text'])[6]    Test
    Sleep    2
    ${limitsave}=     Run Keyword And Return Status   Execute JavaScript                  var xpath = "(//button[contains(text(),'Save')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Run Keyword If    '${limitsave}'=='True'          Click Element                       xpath:(//button[contains(text(),'Save')])[1]
    Run Keyword If    '${limitsave}'=='False'         Execute JavaScript                  var xpath = "(//button[contains(text(),'update')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Run Keyword If    '${limitsave}'=='False'         Wait Until Element Is Visible       xpath:(//button[contains(text(),'update')])[1]    timeout=10s
    Run Keyword If    '${limitsave}'=='False'         Set Focus to Element                xpath:(//button[contains(text(),'update')])[1]
    Run Keyword If    '${limitsave}'=='False'         Click Element                       xpath:(//button[contains(text(),'update')])[1]
    Sleep    1
    Input Text    xpath://input[@ng-reflect-name='currentLimit0']         ${CurrentLimit}
    Input Text    xpath://input[@ng-reflect-name='proposedLimit0']        ${ProposedLimit}        
    Input Text    xpath://input[@ng-reflect-name='eligibleLimit0']        ${eligibleLimit}
    Input Text    xpath://input[@ng-reflect-name='adhocLimit0']           ${adhocLimit}
    Input Text    xpath://input[@ng-reflect-name='doorToDoor0']           ${doorToDoor} 
    Input Text    xpath://input[@ng-reflect-name='invoiceAgeing0']        ${invoiceAgeing}
    Input Text    xpath://input[@ng-reflect-name='expectedGrowthInTo0']   ${expectedGrowthInTo}
    Input Text    xpath://input[@ng-reflect-name='monthlyAverageToWithAnchor0']     ${montlyAvgToWithAnchor}
    Input Text    xpath://input[@ng-reflect-name='approtionedExisitingWCLimits0']   ${approExWCLmt}
    Input Text    xpath://input[@ng-reflect-name='exisitingSCFLimitFromOthers0']    ${exiSCFLmtFrmOthers}
    Input Text    xpath://input[@ng-reflect-name='modelLimit0']                     ${modelLimit}
    Input Text    xpath://input[@ng-reflect-name='anchorRecomendedAmount0']         ${anchorRecomAmount}
    Input Text    xpath://input[@ng-reflect-name='expectedMonthlyTurnOverWithAnc']  ${expMontTurnOvrWithAnc}
    Input Text    xpath://input[@ng-reflect-name='modelAdhocLimit0']                ${modelAdhocLimit}
    Sleep    2
    ${limitsave}=     Run Keyword And Return Status   Execute JavaScript                  var xpath = "(//button[contains(text(),'Save')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Run Keyword If    '${limitsave}'=='True'          Click Element                       xpath:(//button[contains(text(),'Save')])[1]
    Run Keyword If    '${limitsave}'=='False'         Execute JavaScript                  var xpath = "(//button[contains(text(),'Update')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Run Keyword If    '${limitsave}'=='False'         Wait Until Element Is Visible       xpath:(//button[contains(text(),'Update')])[1]    timeout=10s
    Run Keyword If    '${limitsave}'=='False'         Set Focus to Element                xpath:(//button[contains(text(),'Update')])[1]
    Run Keyword If    '${limitsave}'=='False'         Click Element                       xpath:(//button[contains(text(),'Update')])[1]
        Sleep    2
    ${Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[5]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${Next}'=='False'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[5]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[5]
    Click Element                          xpath:(//button[contains(text(),'Next')])[5]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[4]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[4]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[4]
    Click Element                          xpath:(//button[contains(text(),'Next')])[4]
    END
    Sleep    2   
        

Term Sheet
    [Documentation]    Term Sheet    
    Execute Javascript    window.scrollTo(0,-900)    
    Sleep    1    
    Input Text    xpath://input[@ng-reflect-name='termSheetAdhocLimit0']        ${tShtAdhocLimit}
    Input Text    xpath://input[@ng-reflect-name='termSheetCreditPeriod0']      ${tShtCreditPeriod} 
    Input Text    xpath://input[@ng-reflect-name='termSheetDoorToDoor0']        ${tShtDoorToDoor}
    Input Text    xpath://input[@ng-reflect-name='termSheetInvoice0']           ${tShInvoice}
    Input Text    xpath://input[@ng-reflect-name='termSheetInterest0']          ${tShtInterest}
    Input Text    xpath://input[@ng-reflect-name='termSheetPf0']                ${termSheetPf}
    Input Text    xpath://input[@ng-reflect-name='termSheetRenewal0']           ${tShtRenewal}
    Select From List By Label    xpath://select[@ng-reflect-name='termSheetInterestRateType0']   ${IntRateType} 
    Sleep    1
    Input Text    xpath://input[@ng-reflect-name='termSheetRenewalPeriod0']      ${tShtRenwalPeriod} 
    Input Text    xpath://input[@ng-reflect-name='termSheetArrInvoiceFunding0']  ${tShtArrInvFund}
    Input Text    xpath://input[@ng-reflect-name='GraceDaystermSheetArr0']       ${tShtGraceDays}
    Input Text    xpath://input[@ng-reflect-name='termSheetRegularLimit0']       ${termSheetRegLmt}
    Input Text    xpath://input[@ng-reflect-name='termSheetMargin0']             ${termSheetMargin} 
    Sleep    1
    ${termsheet}=     Run Keyword And Return Status   Execute JavaScript                  var xpath = "(//button[contains(text(),'Save')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Run Keyword If    '${termsheet}'=='True'          Click Element                       xpath:(//button[contains(text(),'Save')])[1]
    Run Keyword If    '${termsheet}'=='False'         Execute JavaScript                  var xpath = "(//button[contains(text(),'Update')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Run Keyword If    '${termsheet}'=='False'         Wait Until Element Is Visible       xpath:(//button[contains(text(),'Update')])[1]    timeout=10s
    Run Keyword If    '${termsheet}'=='False'         Set Focus to Element                xpath:(//button[contains(text(),'Update')])[1]
    Run Keyword If    '${termsheet}'=='False'         Click Element                       xpath:(//button[contains(text(),'Update')])[1]
        Sleep    2
    ${Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[5]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${Next}'=='False'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[5]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[5]
    Click Element                          xpath:(//button[contains(text(),'Next')])[5]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[4]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[4]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[4]
    Click Element                          xpath:(//button[contains(text(),'Next')])[4]
    END
    Sleep    2

Collateral    
    [Documentation]    collateral        
    Input Text    xpath://input[@id='primarySecurity']                    ${primarySecurity}
    Input Text    xpath://input[@id='secondarySecurity']                  ${secondarySecurity}
    Input Text    xpath://input[@id='guarantees']                         ${guarantees}
    Input Text    xpath://input[@id='pre-disbursementcreditconditions']   ${pre-disbtcreditcond}
    Input Text    xpath://input[@id='financialcovenants']                 ${financialcovenants}
    Input Text    xpath://input[@id='timeforcompliance']                  ${timeforcompliance}
    Input Text    xpath://input[@id='postdisbursmentcreditconditions']    ${postdisbcreditcond} 
    Sleep    1
    ${collateral}=     Run Keyword And Return Status   Execute JavaScript                  var xpath = "(//button[contains(text(),'Save')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Run Keyword If    '${collateral}'=='True'          Click Element                       xpath:(//button[contains(text(),'Save')])[1]
    Run Keyword If    '${collateral}'=='False'         Execute JavaScript                  var xpath = "(//button[contains(text(),'Update')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Run Keyword If    '${collateral}'=='False'         Wait Until Element Is Visible       xpath:(//button[contains(text(),'Update')])[1]    timeout=10s
    Run Keyword If    '${collateral}'=='False'         Set Focus to Element                xpath:(//button[contains(text(),'Update')])[1]
    Run Keyword If    '${collateral}'=='False'         Click Element                       xpath:(//button[contains(text(),'Update')])[1]
        Sleep    2
    ${Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[5]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${Next}'=='False'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[5]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[5]
    Click Element                          xpath:(//button[contains(text(),'Next')])[5]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[4]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[4]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[4]
    Click Element                          xpath:(//button[contains(text(),'Next')])[4]
    END
    Sleep    2
    
  
Credit Policy
    [Documentation]    Credit Policy
    Input Text      xpath://input[@ng-reflect-name='vintageWithAnchor0']      ${vintageWithAnchor}
    Input Text      xpath://input[@ng-reflect-name='minMonthlyAnchor0']       ${minMonthlyAnchor}           
    Select From List By Value    xpath://select[@ng-reflect-name='anchorRelationship0']       ${anchorRelaship} 
    Sleep    1
    ${termsheet}=     Run Keyword And Return Status   Execute JavaScript                  var xpath = "(//button[contains(text(),'Save')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Run Keyword If    '${termsheet}'=='True'          Click Element                       xpath:(//button[contains(text(),'Save')])[1]
    Run Keyword If    '${termsheet}'=='False'         Execute JavaScript                  var xpath = "(//button[contains(text(),'Update')])[1]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Run Keyword If    '${termsheet}'=='False'         Wait Until Element Is Visible       xpath:(//button[contains(text(),'Update')])[1]    timeout=10s
    Run Keyword If    '${termsheet}'=='False'         Set Focus to Element                xpath:(//button[contains(text(),'Update')])[1]
    Run Keyword If    '${termsheet}'=='False'         Click Element                       xpath:(//button[contains(text(),'Update')])[1]
    Sleep    1 
   Input Text    xpath://input[@id='Business Vintage (in Months)']             ${BusinVintinMonth}
   ${ATNW}=              Run Keyword And Return Status       Input Text    xpath://input[@id='ATNW']                                 ${ATNW}
   Run Keyword If    '${ATNW}'=='False'                      log To Console        No value for ATNW 
   ${turnover}=          Run Keyword And Return Status       Input Text    xpath://input[@id="Turnover (in Rs)"]                     ${PAT1}
   Run Keyword If    '${turnover}'=='False'                  log To Console        No value for turnover 
   ${totaldebt}=         Run Keyword And Return Status       Input Text    xpath://input[@id="Total Debt/GST Turnover (in %)"]       ${PAT1}
   Run Keyword If    '${totaldebt}'=='False'                 log To Console        No value for total debt 
   ${sales_growth}=      Run Keyword And Return Status       Input Text    xpath://input[@id='Sales Growth (in %)']                  ${PAT1}
   Run Keyword If    '${totaldebt}'=='False'                 log To Console        No value for total debt 
   ${PAT}=      Run Keyword And Return Status       Input Text    xpath://input[@id='PAT']                                           ${PAT1}
   Run Keyword If    '${PAT}'=='False'              log To Console        No value for PAT 
   ${currentratio}=      Run Keyword And Return Status       Input Text    xpath://input[@id='Current Ratio']                        ${PAT1}
   Run Keyword If    '${currentratio}'=='False'              log To Console        No value for current ratio 
   ${tol/atnw}=          Run Keyword And Return Status       Input Text    xpath://input[@id='TOL/ATNW']                             ${PAT1}
   Run Keyword If    '${tol/atnw}'=='False'                  log To Console        No value for tol/ATNW 
   ${WCC}=              Run Keyword And Return Status        Input Text    xpath://input[@id='Working Capital Cycle (in Days)']      ${PAT1}
   Run Keyword If    '${WCC}'=='False'                       log To Console        No value for WCC 
   ${commer60-90 6-12}=  Run Keyword And Return Status       Input Text    xpath://input[@id="Commercial : SMA-2/61-90 DPD in 6-12 months (in Counts)"]          ${PAT1}
   Run Keyword If    '${commer60-90 6-12}'=='False'          log To Console        No value for Commercial : SMA-2/61-90 DPD in 6-12 months 
   ${ICB}=               Run Keyword And Return Status       Input Text    xpath://input[@id='Inward Cheque Bounces']                ${InwrdChqBou}
   Run Keyword If    '${ICB}'=='False'                       log To Console        No value for ICB 
   ${churn}=             Run Keyword And Return Status       Input Text    xpath://input[@id='Bank Churn (in %)']                    ${BankChurn}
   Run Keyword If    '${churn}'=='False'                     log To Console        No value for bank churn 
   ${COD_credit}=        Run Keyword And Return Status       Input Text    xpath://input[@id='Commercial : Current Overdues - Credit Card (in Rs)']              ${com_curntod_crdcard}
   Run Keyword If    '${COD_credit}'=='False'                log To Console        No value for COD_credit
   ${COD_Non-credit}=    Run Keyword And Return Status       Input Text    xpath://input[@id='Commercial : Current Overdues - Non Credit Card (in Rs)']          ${com_curntod_Noncrdcard}
   Run Keyword If    '${COD_Non-credit}'=='False'            log To Console        No value for COD_Non-credit
   ${SMA-1/31-60}=       Run Keyword And Return Status       Input Text    xpath://input[@id='Commercial : SMA-1/31-60 DPD in the last 12 months (in Counts)']   ${com_31-60dpd}
   Run Keyword If    '${SMA-1/31-60}'=='False'               log To Console        No value for SMA-1/31-60
   ${SMA-2/61-90}=       Run Keyword And Return Status       Input Text    xpath://input[@id='Commercial : SMA-2/61-90 DPD in the last 6 month (in Counts)']     ${com_61-90dpd_6-12}
   Run Keyword If    '${SMA-2/61-90}'=='False'               log To Console        No value for SMA-2/61-90
   ${comm_24}=           Run Keyword And Return Status       Input Text    xpath://input[@id='Commercial : Substd, doubtful, loss, restructured/write off: In last 24 months (in Counts)']    ${com_last24months}
   Run Keyword If    '${comm_24}'=='False'                   log To Console        No value for comm_24
   ${comm_48}=           Run Keyword And Return Status       Input Text    xpath://input[@id='Commercial : Wilful or suitfiled: In the last 48 months (in Counts)']     ${com_last48months}
   Run Keyword If    '${comm_48}'=='False'                   log To Console        No value for comm_48
   ${cons_bureau}=       Run Keyword And Return Status       Input Text    xpath://input[@id='Consumer : Bureau Score']                                                 ${cons_BureauScore} 
   Run Keyword If    '${cons_bureau}'=='False'               log To Console        No value for cons_bureau
   ${cons_OD_credit}=    Run Keyword And Return Status       Input Text    xpath://input[@id='Consumer : Current Overdues - Credit Card (in Rs)']                       ${cons_curtod_crdcard}
   Run Keyword If    '${cons_OD_credit}'=='False'            log To Console        No value for cons_OD_credit
   ${cons_OD_noncredit}=  Run Keyword And Return Status      Input Text    xpath://input[@id='Consumer : Current Overdues - Non Credit Card (in Rs)']                   ${cons_curtod_Noncditcrd}
   Run Keyword If    '${cons_OD_noncredit}'=='False'         log To Console        No value for cons_OD_noncredit
   ${cons_24}=            Run Keyword And Return Status      Input Text    xpath://input[@id='Consumer : Substd, doubtful, loss, restructured/write off: In last 24 months (in Counts)']       ${cons_last24months}
   Run Keyword If    '${cons_24}'=='False'                   log To Console        No value for cons_24
   ${cons_48}=            Run Keyword And Return Status      Input Text    xpath://input[@id='Consumer : Wilful or suit filed: In the last 36 months (in Counts)']       ${cons_last48months}
   Run Keyword If    '${cons_48}'=='False'                   log To Console        No value for cons_48
   ${GST}=                Run Keyword And Return Status      Input Text    xpath://input[@id='Latest GST Payment (in Months)']            ${GSTpay}
   Run Keyword If    '${GST}'=='False'                       log To Console        No value for GST
   ${EPFO}=               Run Keyword And Return Status      Input Text    xpath://input[@id='Latest EPFO Payment (in Months)']           ${EPFO}
   Run Keyword If    '${EPFO}'=='False'                      log To Console        No value for EPFO
   ${TIC}=                Run Keyword And Return Status      Input Text    xpath://input[@id='Total Inward Cheques']                      ${TotalInwrdchk}
   Run Keyword If    '${TIC}'=='False'                       log To Console        No value for TIC
    Sleep    2
    Set Focus To Element    xpath://button[text()='Run Credit Policy']
    Wait Until Page Contains Element    xpath://button[text()='Run Credit Policy']    timeout=16s
    Click Button  xpath://button[text()='Run Credit Policy']
    Sleep    1
    Execute JavaScript                     var xpath = "//button[contains(text(),'Approve')]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Click Element           xpath://button[contains(text(),'Approve')]
        Sleep    2
    ${Next}=     Run Keyword And Return Status    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[5]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    IF  '${Next}'=='False'
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[5]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[5]
    Click Element                          xpath:(//button[contains(text(),'Next')])[5]
    ELSE
    Execute JavaScript                     var xpath = "(//button[contains(text(),'Next')])[4]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2   
    Wait Until Page Contains Element       xpath:(//button[contains(text(),'Next')])[4]
    Set Focus To Element                   xpath:(//button[contains(text(),'Next')])[4]
    Click Element                          xpath:(//button[contains(text(),'Next')])[4]
    END
    Sleep    2
Remarks Screen
    [Documentation]    Remarks Screen
    Set Focus To Element    xpath://button[text()='Submit' and @type='button']
    Wait Until Page Contains Element    xpath://button[text()='Submit' and @type='button']       timeout=20s
    Click Button    xpath://button[text()='Submit' and @type='button']
    Sleep    1
    Input Text    xpath://textarea[@class='swal2-textarea']    Approved
    Click Button    xpath:(//button[text()='Submit'])[2]
    Sleep    4
    Logout from Triton