*** Settings ***
Resource        ../../keywords/common.robot
Resource        ../../PageObjects/loginPage.robot
Resource        ../../PageObjects/CounterPartyPageObjects/05__camUploadPage.robot
Resource        ../../PageObjects/AnchorPageObjects/02__creditNormsPage.robot


*** Test Cases ***
TC for Campload
    [Documentation]    CAM Upload
    ${caseName}=    Get Variable Value    ${cpName}
    Set Test Variable    ${caseName}
    02__creditNormsPage.From CPA lead assign to CPA
    05__camUploadPage.Cam Upload 
    