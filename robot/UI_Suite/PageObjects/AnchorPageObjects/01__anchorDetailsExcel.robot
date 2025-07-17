*** Settings ***
Resource           ../../keywords/common.robot  
Library    OperatingSystem

*** Keywords ***
Generate Anchor name,PAN and CIN Numbers
    ${a}=    Generate Random String     5    [UPPER]
    ${b}=    Generate Random String     4    [NUMBERS]
    ${c}=    Generate Random String     1    [UPPER]
    ${pan}=             Catenate     ${a}${b}${c}
    
    ${d}=    Generate Random String     5    [NUMBERS]
    ${e}=    Generate Random String     4    [NUMBERS]
    ${f}=    Generate Random String     6    [NUMBERS]
    ${cin}=             Catenate     U${d}DL${e}RFR${f}
    Log      \nRES:${pan}  
    Log      \nRES:${cin}
    ${anchorNameFname}=    FakerLibrary.First Name
    ${anchorSuffix}=    Set Variable   Limited
    ${anchorName}=    Catenate    ${anchorNameFname} ${anchorSuffix}
    Log    ${anchorName}

    Open Workbook    ${CURDIR}\\Anchor File Upload.xlsx
    Switch sheet    sheet_name=Customer Information
    Log Opened Workbooks
    Write To Cell    A2    ${anchorName}
    Write To Cell    B2    ${pan}
    Write To Cell    C2    ${cin}
    Save
    Close Workbook  
    Set Test Variable    ${anchorName}    
    Set Test Variable    ${pan}    
    Set Test Variable    ${cin}
    Return From Keyword      ${anchorName}    ${pan}    ${cin} 
    