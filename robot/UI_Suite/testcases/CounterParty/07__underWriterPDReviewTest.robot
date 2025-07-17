*** Settings ***  

Resource    ../../PageObjects/CounterPartyPageObjects/07__underWriterPDReviewPage.robot


*** Test Cases ***

Counterparty_underwriter_stage_7
    [Documentation]    Counter party underwriter
    ${caseName}    Get Variable Value    ${cpName}
    Set Test Variable    ${caseName}
    07__underWriterPDReviewPage.To credit underwriter or risk underwriter lead
    07__underWriterPDReviewPage.cp_underwriter_pd_review_stage7
