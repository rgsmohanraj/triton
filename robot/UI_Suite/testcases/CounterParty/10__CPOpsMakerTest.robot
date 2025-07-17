*** Settings ***
Resource       ../../PageObjects/CounterPartyPageObjects/10__CPOpsMakerPage.robot
Resource       ../../PageObjects/AnchorPageObjects/04__opsMakerPage.robot
*** Test Cases ***
Operation_Maker stage
    [Documentation]      Counter party ninth stage
    ${caseName}=         Get Variable Value    ${cpName}
    Set Test Variable    ${caseName}
    04__opsMakerPage.From Ops_maker_lead assign Ops_maker
    ${accNum}=           FakerLibrary.Credit Card Number
    10__CPOpsMakerPage.Cp_Beneficiary Details & DocumentUpload    ${accNum}