*** Settings ***  
Resource           ../../keywords/common.robot    
Resource           ../loginPage.robot

*** Variables ***
${pan}                AKSJB7891H
${cin}                U18862DL1088RFR091089
${anchorName1}        patch 123

*** Keywords ***
De_Dupe check
    [Documentation]    Anchor de-dupe check
    [Arguments]   ${anchorName}   ${pan}   ${cin}
    Set Selenium Speed    0.4s
    # Wait Until Element Is Visible        xpath://button[contains(text(),' New Anchor')]       timeout=10s
    Click Button                         xpath://button[contains(text(),' New Anchor')]
    Wait Until Element Is Visible        name:anchorName2    timeout=5s
    Input Text      name:anchorName2     ${anchorName}
    Input Text      name:panNumber2      ${pan}
    Input Text      name:cinNumber2      ${cin}
    Sleep    1
    Set Focus To Element                 xpath://button[text()='De-Dupe']
    Sleep    1
    Click Element                        xpath://button[text()='De-Dupe']
    ${dedupeStatus}=    Run Keyword And Return Status    Wait Until Element Is Visible    xpath://label[text()=' Existing Customer Details:']
    Run Keyword If    '${dedupeStatus}'== 'True'    Already a customer
    # Wait Until Page Contains     New Anchor

Upload File
    [Documentation]     Anchor file upload
    # Set Selenium Speed    0.4s
    Wait Until Element Is Visible    xpath://input[@class="form-control"]    timeout=10s
    Choose File                      xpath://input[@class="form-control"]    ${CURDIR}\\Anchor File Upload.xlsx
    Sleep      1
    Click Element                    xpath://button[text()='Upload']
    Sleep      1
    ${uploadErrorStatus}=    Run Keyword And Return Status    Wait Until Element Is Visible    xpath://th[text()='Error Message']
    Run Keyword If    '${uploadErrorStatus}'=='True'    Log error in file upload

Check in Anchor details
    [Documentation]    Verify anchor onboard
    # Set Selenium Speed    0.4s
    # Wait Until Element Is Visible         Successfully Uploaded    timeout=20s
    # Click Element                  xpath://button[contains(text(),'Upload')]
    Wait Until Element Is Visible    xpath://textarea[@class='swal2-textarea']     timeout=20s
    Sleep    2  
    Input Text                       xpath://textarea[@class='swal2-textarea']       Approved
    Click Element                    xpath://button[contains(text(),'Submit')]
    Sleep    30s
    Log  File successfully uploaded

Log error in file upload
    [Documentation]  If anchor file upload error capture screenshot
    Wait Until Element Is Visible    xpath://th[text()='Error Message']
    Capture Page Screenshot

Already a customer
     [Documentation]    verify already anchor onboard into Triton
    Wait Until Page Contains Element    xpath://label[text()=' Existing Customer Details:']
    Wait Until Element Is Visible       xpath://label[text()='${anchorName1}']
    Capture Page Screenshot
    Log     Already ${anchorName} is a customer

Anchor Details File Upload
    [Documentation]    Anchor details file upload
    [Arguments]   ${anchorName}   ${pan}   ${cin}
    De_Dupe check    ${anchorName}   ${pan}   ${cin}
    Upload File
    Check in Anchor details
    Sleep    40s
    Logout from Triton