*** Settings ***  
Resource           ../../keywords/common.robot
Resource           ../../PageObjects/assignmentPage.robot    

*** Variables ***
${gst}              
${city}             Bangalore
${state}            Karnataka
${subsource}        External
${ccname}           Riya
${ccmobile}         8787878568
${ccmail}           pvi@gmail.com
${productName}      Vendor Purchase Order Finance
# ... Anchor Purchase Bill Discounting
${proposed_amt}     900000
${file_type}        Cashflow
${file_location}    ${CURDIR}\\UATFile.xlsx
${all_characters}     case UPP1234567890!@#$%^&*()_-+=[]\[]\{}|;'':"<>?,./`~\|"
*** Keywords ***
Counterparty_business_stage
    [Documentation]    counter party all modules of business stage
    Login as Business User
    Counterparty_Dedupe
    Counterparty_basic_details
    Proposal tab
    File_upload
    # Logout and close browser
    Close Window

Login as Business User
    [Documentation]     Business User Login
    ${userName}=        Set Variable    ${businessLeadUser}
    ${password}=        Set Variable    ${businessPw}
    loginPage.Open triton in browser
    loginPage.login to Triton           ${userName}    ${password}

Counterparty_Dedupe
    [Documentation]                     Counter party de-dupe
    Wait Until Page Contains Element    xpath://*[contains(text(),'Counter Party')]    timeout=10s
    Sleep                               3
    Click Element                       xpath://span[text()='New Counter party']
    Sleep                               3
    Select From List By Label           constitution    Proprietorship
    ${a}=    Generate Random String     5    [UPPER]
    ${b}=    Generate Random String     4    [NUMBERS]
    ${c}=    Generate Random String     1    [UPPER]
    ${pan_cp}=             Catenate     ${a}${b}${c}
    Log                                 \nRES:${pan_cp}
    Input Text                          name:panNumber2        ${pan_cp}
    Sleep                               1
    ${d}=    Generate Random String     5    [NUMBERS]
    ${e}=    Generate Random String     4    [NUMBERS]
    ${f}=    Generate Random String     6    [NUMBERS]
    ${cin_cp}=             Catenate     U${d}DL${e}RFR${f}
    Log                                 \nRES:${cin_cp}
    Input Text                          name:cinNumber2        ${cin_cp}
    Sleep                               2
    Set Focus To Element                xpath:(//div[@class='ng-star-inserted']//child::button)[1]
    Sleep                               1
    Click Element                       xpath:(//div[@class='ng-star-inserted']//child::button)[1]
    Sleep                               1
    Wait Until Page Contains            New CounterParty    timeout=10s
    Log                                 Counter party de-dupe completed

Counterparty_basic_details
    [Documentation]                     Counter party basic details
    ${companyName}=                     Set Variable    ${cpName}
    Log                                 \nRES:${companyName}
    Input Text                          name:companyName    ${companyName}
    Sleep                               1
    Input Text                          name:gstNumber1     ${gst}
    Select From List By Label           xpath://Select[@name="activity"]      Traders
    Input Text                          name:cpCity1        ${city}
    Select From List By Label           cpState       ${state}
    Sleep                               1
    Select From List By Label           source        Direct
    Input Text                          name:subSource      ${subsource}
    Select From List By Label           xpath://*[contains(text(),'RM Name')]/child::select    ${RM Name}
    Input Text                          name:custContactName      ${ccname}
    Input Text                          name:custContactMobile    ${ccmobile}
    Input Text                          name:custContactEmail     ${ccmail}
    Set Focus To Element                xpath://button[text()='Save']
    Sleep                               2
    Click Button                        xpath://button[text()='Save']
    Wait Until Page Contains            Successfully Saved                                    timeout=10s
    Set Focus To Element                xpath://button[text()='Next']
    Click Button                        xpath://button[text()='Next']
    Log                                 Counter party basic details filled

Proposal tab
    [Documentation]                     Counter party proposal tab
    Set Selenium Speed                  0.3s
    Wait Until Element Is Visible       xpath://label[text()='Anchor Proposed Details']
    Execute Javascript                  window.scrollTo(0,150)
    Sleep                               1
    Click Element                       xpath://label[text()='Anchor Proposed Details']//following::select[1]  
    Sleep                               1
    Click Element                       xpath://option[text()='${anchorName}']
    Sleep                               2
    Click Element                       xpath://*[@id="canvas-bookmark"]/div/div[2]/main/app-new-counter-party/div/div/div/div/div/div/aw-wizard/div/aw-wizard-step/form/div/div[2]/ngx-tabset/div/ngx-tab[2]/div/div/div/div[1]/div/div[2]/form/div[3]/select
    Sleep                               2
    ${Product_case1}=     Run Keyword And Return Status     Click Element    xpath://option[text()='Vendor Purchase Order Finance']
    ${Product_case2}=     Run Keyword If     '${Product_case1}' == 'False'   Run Keyword And Return Status    Click Element    xpath://option[text()='Anchor Purchase Bill Discounting']
    ${Product_case3}=     Run Keyword If     '${Product_case2}' == 'False'   Run Keyword And Return Status    Click Element    xpath://option[text()='Dealer Purchase Order Finance']
    ${Product_case4}=     Run Keyword If     '${Product_case3}' == 'False'   Run Keyword And Return Status    Click Element    xpath://option[text()='Dealer Invoice Finance']
    ${Product_case5}=     Run Keyword If     '${Product_case4}' == 'False'   Run Keyword And Return Status    Click Element    xpath://option[text()='Anchor Sales Bill Discounting']
    ${Product_case6}=     Run Keyword If     '${Product_case5}' == 'False'   Run Keyword And Return Status    Click Element    xpath://option[text()='Vendor Invoice Finance']
    Sleep                               2
    Input Text                          (//input[@class='form-control ng-untouched ng-pristine ng-invalid'])[1]          ${proposed_amt}
    Set Global Variable                 ${proposed_amt}
    Sleep                               2
    Set Focus To Element                xpath://button[text()='Save']
    Sleep                               2
    Click Button                        xpath://button[text()='Save']
    Wait Until Page Contains            Successfully Saved
    Set Focus To Element                xpath://button[text()='Next']
    Click Button                        xpath://button[text()='Next']
    Log                                 Counter party proposal tab completed

File_upload
    [Documentation]                     Counter party file upload
        ${FIle_Upload1}=     Run Keyword And Return Status     Select From List By Label           xpath://select[@type="text"]      Cashflow 
    IF  ${FIle_Upload1}==True
        FOR    ${i}  IN RANGE        1    16
        Sleep                           2
        Set Focus To Element            xpath:(//input[@type='file'])[${i}]
        Choose File                     xpath:(//input[@type='file'])[${i}]         ${file_location}
        END
    END
       ${FIle_Upload2}=    Run Keyword If    '${FIle_Upload1}' == 'False'   Run Keyword And Return Status      Select From List By Label           xpath://select[@type="text"]       KYC
    IF  ${FIle_Upload2}==True
        FOR    ${i}  IN RANGE               1    19
        Sleep                           2
        Set Focus To Element            xpath:(//input[@type='file'])[${i}]
        Choose File                     xpath:(//input[@type='file'])[${i}]         ${file_location}
        END
    END
        ${FIle_Upload3}=    Run Keyword If    '${FIle_Upload2}' == 'False'    Run Keyword And Return Status     Select From List By Label           xpath://select[@type="text"]       Financial
    IF  ${FIle_Upload3}==True
        FOR    ${i}  IN RANGE               1    27
        Sleep                           2
        Set Focus To Element            xpath:(//input[@type='file'])[${i}]
        Choose File                     xpath:(//input[@type='file'])[${i}]         ${file_location}
        END
    END
    Log                                 Counter party file upload done
    Execute Javascript   window.scrollTo(0,document.body.scrollHeight)
    Sleep    2
    Wait Until Element Is Visible       xpath:(//button[@type='submit'])[1]    timeout=10s
    Set Focus To Element                xpath:(//button[@type='submit'])[1]
    Click Element                       xpath:(//button[@type='submit'])[1]
    Sleep                               2
    Input Text                          xpath:(//textarea[@id="swal2-textarea"])       ${all_characters}        #remarks
    Set Focus To Element                xpath:(//button[@type="button"])[5]
    Wait Until Element Is Visible       xpath:(//button[@type="button"])[5]    timeout=10s
    Click Element                       xpath:(//button[@type="button"])[5]
    Log                                 Counter party business stage completed
    Sleep    10    
    Logout from Triton
    