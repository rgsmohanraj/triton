*** Settings ***  
Resource     ../../PageObjects/AnchorPageObjects/01__anchorFileUploadPage.robot
Test Setup    Open browser and login to triton

*** Test Cases ***
Test_Anchor_File_Upload_in_UI
    [Documentation]    Anchor file Upload in UI
    ${caseName}    Get Variable Value    ${anchorName}
    Set Test Variable    ${caseName}
    01__anchorFileUploadPage.Anchor Details File Upload   ${anchorName1}   ${pan}   ${cin}

  




