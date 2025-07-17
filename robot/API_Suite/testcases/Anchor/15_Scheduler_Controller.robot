*** Settings ***
Resource    ../../keywords/variables.robot
Suite Setup    CPA_Authentication

*** Variables ***
${email}=    cpa_lead@vivriticapital.com
${Cus_Type}=     1
    

*** Keywords ***
CPA_Authentication
    ${username}    Set Variable       cpa_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}

*** Test Cases ***
Get_Mail_Notification
    [Documentation]    Get mail notification
    create session     mailnotification      ${Schdlr_url}
    ${header}=    Create Dictionary    Authorization=${Token}   Content-Type=application/json
    ${response}=       GET On Session      mailnotification          getEmailNotificationData    headers=${header}
    ${status_code}=    convert to string         ${response.status_code}
    should be equal    ${status_code}       ${expected_code}

CP_Initiate_WF
    [Documentation]    cp Initiate workflow
    create session     Initiworkflow      ${Schdlr_url}
    ${header}=    Create Dictionary    Authorization=${Token}   Content-Type=application/json
    ${response}=       GET On Session      Initiworkflow          initiateCpWorkFlow    headers=${header}
    ${status_code}=    convert to string         ${response.status_code}
    should be equal    ${status_code}       ${expected_code}

Sendmail_Pendingdata
    [Documentation]    Sendmail_Pendingdata
     create session     sendmailpending      ${Schdlr_url}
    ${header}=    Create Dictionary    Authorization=${Token}   Content-Type=application/json
    ${response}=       GET On Session      sendmailpending          sendMailforPendingData    headers=${header}
    ${status_code}=    convert to string         ${response.status_code}
    should be equal    ${status_code}       ${expected_code}