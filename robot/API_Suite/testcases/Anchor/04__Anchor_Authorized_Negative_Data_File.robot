*** Settings ***
Resource    ../../keywords/variables.robot
Test Template     TC_AA_05_AnchorAuthorized_Update_Negative
Suite Setup        CPA_Authentication


*** Variables ***
${Authorized_putId}     7957

*** Keywords ***
CPA_Authentication
    ${username}    Set Variable       cpa_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}

*** Test Cases ***                ${appId}              ${createdBy}         ${emailId}           ${Authorized_putId}         ${indemnityDoc}          ${mobile}          ${name}         ${type}         ${updatedBy}
TC_AA_06_EmptyAppID               ${EMPTY}                  QA               test@gmai.com        ${Authorized_putId}              yes                 9944563456         Vijay           new                  QA
TC_AA_07_EmptyEmailId             ${Anchor_App_id}          test             ${EMPTY}             ${Authorized_putId}              no                  9944563456         Vijay           new                  test
TC_AA_08_EmptyMobileNo            ${Anchor_App_id}          test             v.p@capital.com      ${Authorized_putId}              no                  ${EMPTY}           Sesh            new                  test
TC_AA_09_EmptyName                ${Anchor_App_id}          test             QA@gmail.com         ${Authorized_putId}              no                  9944563456         ${EMPTY}        new                  test
TC_AA_10_EmptyType                ${Anchor_App_id}          test             test@gmail.com       ${Authorized_putId}              no                  9944563456         Vijay           ${EMPTY}             test
TC_AA_11_EmptyAll                 ${EMPTY}                  test             ${EMPTY}             ${EMPTY}                        ${EMPTY}                ${EMPTY}        ${EMPTY}        ${EMPTY}             ${EMPTY}