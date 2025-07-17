*** Settings ***
Resource    ../../keywords/variables.robot
Suite Setup           CPA_Authentication



*** Keywords ***
CPA_Authentication
    ${username}    Set Variable       cpa_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}

*** Variables ***
${doctype}=      kycDocuments
${docsubtype}=   AOA
${filename}=     sample.txt
${param}=        ?appId=${Anchor_cust_id}
${Json_data}=    {"appId":9345,"docTypeId":16130,"docTypeName":"anchorDocuments","docCategoryId":16131,"docCategoryName":"kycDocuments","docListId":16135,"docListName":"AOA","fileName":"sample.txt",key:2}

*** Test Cases ***
# TC_DMS_01_Upload_Document_File     
#     [Documentation]    Anchor  Document   upload
#     create session     Document_upload     ${DMS_Url}
#     ${file}=           Get File For Streaming Upload        ${CURDIR}\\${filename}
#     ${data}=           Create Dictionary       documentReportsData=${Json_data}
#     ${header}          Create Dictionary       Authorization=${Token}       Content-Type=multipart/form-data
#     ${response}=       Post Request            Document_upload      /dms/uploadFile      data=${data}       headers=${header}
#     ${status_code}=    convert to string       ${response.status_code}
#     should be equal    ${status_code}          ${server_err_code}    

TC_DMS_02_Test_Download_File
    [Documentation]    Download the File GET request
    create session     DownloadFile     ${DMS_Url}
    ${response}=       GET On Session      DownloadFile     /dms/customerDocReports${param}
    ${status_code}=    convert to string    ${response.status_code}
    should be equal    ${status_code}       ${expected_code}

# TC_DMS_03_Test_customerFileList_Download
#     [Documentation]    Getting DMS customer FileList details
#     create session     customerFileList     ${DMS_Url}    
#     ${response}=       GET On Session       customerFileList    /dms/download/${Anchor_cust_id}/${doctype}/${docsubtype}/${filename}
#     ${status_code}=    convert to string    ${response.status_code}
#     should be equal    ${status_code}       ${expected_code}

TC_DMS_04_Test_DocumentType_ReadAll
    [Documentation]    Getting Documents details
    create session     Documents        ${DMS_Url}
    ${response}=       GET On Session       Documents     /dms/getAllFiles
    ${status_code}=    convert to string    ${response.status_code}
    should be equal    ${status_code}       ${expected_code}


    