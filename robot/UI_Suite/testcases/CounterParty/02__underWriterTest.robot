*** Settings ***  
Resource    ../../PageObjects/CounterPartyPageObjects/02__underWriterPage.robot



*** Test Cases ***
Counterparty_underwriter_stage_2
    [Documentation]    Counter party underwriter   
    ${caseName}    Get Variable Value    ${cpName}
    Set Test Variable    ${caseName}
    02__underWriterPage.From credit_head_lead to credit_head
    02__underWriterPage.cp_underwriter