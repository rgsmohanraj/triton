# *** Settings ***
# Documentation     A test suite for anchor file upload
# Library           SeleniumLibrary  

# *** Variables ***
# ${browser}        chrome
# &{url}            QA1=http://10.100.10.41:8282/triton/index.html     QA2=http://10.100.10.54:8282/triton/index.html
# ${anchorName}=    Bumble Bee
# ${pan}=           BUMFG4151Z  
# ${cin}=           U13445SS9312PLB156733

# *** Test Cases ***
# Test_Anchor_File_Upload_in_UI
#     [Documentation]    Anchor file Upload in UI
#     Set Selenium Implicit Wait    10 seconds
#     Open Browser and login to Triton
#     De_Dupe check
#     Upload File
#     Sleep    10s
#     # Check in Anchor details
#     Logout and close browser

# *** Keywords ***
# Open Browser and login to Triton
#     Open Browser    ${url.QA1}    ${browser}
#     Maximize Browser Window
#     Set Selenium Implicit Wait    10 seconds
#     Input Text      xpath://input[@formcontrolname='email']       cpa_lead
#     Input Text      xpath://input[@formcontrolname='password']    admin@1234
#     Sleep           3
#     Click Element   xpath://span[text()='Login']
#     Sleep            5
    
# De_Dupe check
#     Click Element   xpath://span[text()='New Anchor']
#     Sleep            3
#     Input Text      name:anchorName2    ${anchorName}
#     Sleep            2
#     Input Text      name:panNumber2    ${pan}
#     Sleep            2
#     Input Text      name:cinNumber2    ${cin}
#     Sleep            2
#     Execute Javascript    window.scrollTo(0,500)
#     Sleep            2
#     Click Element   xpath://button[text()='De-Dupe']
#     Sleep            1
#     # Click Element    xpath://*[@id="canvas-bookmark"]/div/div[2]/main/app-new-anchor-details/div/div/div/div/div/div/aw-wizard/div/aw-wizard-step/form/div/div/ngx-tabset/div/ngx-tab[1]/div/div/button
#     # Click Element    xpath:(//div[@class='ng-star-inserted']//child::button)[1]
#     Sleep            5
#     Wait Until Page Contains     New Anchor

# Upload File
#     # Click Element    xpath://input[@class="form-control"]
#     Sleep            5
#     Choose File      xpath://input[@class="form-control"]    ${CURDIR}\\NewAnchorFile.xlsx
#     Sleep            5
#     Click Element    xpath://button[text()='Upload']
#     Sleep            5
#     Input Text       xpath://textarea[@class='swal2-textarea']     Anchor onboard file
#     Sleep            2
#     Click Element    xpath://button[text()='Submit']


# Check in Anchor details
#     Click Element    xpath://span[text()='Anchor Details']
#     Sleep            5
#     Element Should Be Visible    xpath://td[text()='${anchorName}']
#     Sleep            2
#     Log To Console    File uploaded

# Logout and close browser
#     Mouse Over      xpath://*[text()='Admin ']
#     Click Element   xpath://span[text()='Log Out']
#     Close Browser




