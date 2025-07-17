package org.vcpl.triton.dms.docManagement.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.ProgramNormsEntity;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.ProgramNormsRepository;
import org.vcpl.triton.counterParty.entity.ProposedProductsEntity;
import org.vcpl.triton.counterParty.repository.ProposedProductsRepository;
import org.vcpl.triton.dms.dmsMaster.repository.DocumentCategoryRepository;
import org.vcpl.triton.dms.dmsMaster.repository.DocumentListRepository;
import org.vcpl.triton.dms.dmsMaster.repository.DocumentTypeRepository;
import org.vcpl.triton.dms.docManagement.data.DocumentReportsData;
import org.vcpl.triton.dms.docManagement.entity.DocumentReportsEntity;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentReportsEntity;
import org.vcpl.triton.dms.docManagement.repository.DocumentReportsRepository;
import org.vcpl.triton.dms.docManagement.repository.OtherDocumentReportsRepository;
import org.vcpl.triton.dms.docValidation.DocValidationService;
import org.vcpl.triton.security.config.DatasourceForDbConnect;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DocumentReportsService implements IDocumentReports{
    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    DocumentReportsRepository documentReportsRepository;

    @Autowired
    OtherDocumentReportsRepository otherDocumentReportsRepository;

    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @Autowired
    DocumentCategoryRepository documentCategoryRepository;

    @Autowired
    DocumentListRepository documentListRepository;

    @Autowired
    DocValidationService docValidationService;

    @Autowired
    private ProposedProductsRepository proposedProductsRepository;

    @Autowired
    private ProgramNormsRepository programDetailsRepository;

    private String bucketName = null;

    private static AmazonS3 s3;

    public DocumentReportsService(DatasourceForDbConnect data, AmazonS3 s3) {
        this.s3 = s3;
        bucketName = data.getBucketName();
    }

    @Override
    public String saveFile(DocumentReportsData documentReportsData, MultipartFile file) {
        Long appId = documentReportsData.getAppId();
        String docTypeName = documentReportsData.getDocTypeName();
        String docCategoryName = documentReportsData.getDocCategoryName();
        String docListName = documentReportsData.getDocListName();
        String Filename = file.getOriginalFilename();

        String originalFilename=Filename.replaceAll("[^.,a-zA-Z0-9]+", "_");

        String folderPath = null;
        if(documentReportsData.getKey()==1){
            folderPath = appId + "/" + docTypeName +"/" + docListName + "/" + originalFilename;
        }else if(documentReportsData.getKey()==2){
            folderPath = appId + "/" + docCategoryName +"/" + docListName + "/" + originalFilename;
        }
        String result = docValidationService.documentCheck(documentReportsData.getAppId(),documentReportsData.getDocTypeId(),documentReportsData.getDocCategoryId(),documentReportsData.getDocListId(),originalFilename);
        if (result.equals("success")) {
            try {
                File file1 = convertMultiPartToFile(file);
                PutObjectResult putObjectResult = s3.putObject(bucketName, folderPath, file1);

                Date date = new Date();
                DocumentReportsEntity documentReportsEntity = new DocumentReportsEntity();
                documentReportsEntity.setApplicationEntity(applicationRepository.findById(documentReportsData.getAppId()).orElseThrow());
                documentReportsEntity.setDocumentTypeEntity(documentTypeRepository.findById(documentReportsData.getDocTypeId()).orElseThrow());
                documentReportsEntity.setDocumentCategoryEntity(documentCategoryRepository.findById(documentReportsData.getDocCategoryId()).orElseThrow());
                documentReportsEntity.setDocumentListEntity(documentListRepository.findById(documentReportsData.getDocListId()).orElseThrow());
                documentReportsEntity.setFileName(originalFilename);
                documentReportsEntity.setVersion("v1");
                documentReportsEntity.setCreatedBy("Balaji R");
                documentReportsEntity.setCreatedAt(new Timestamp(date.getTime()));
                documentReportsEntity.setUpdatedBy("Balaji R");
                documentReportsEntity.setUpdatedAt(new Timestamp(date.getTime()));
                documentReportsRepository.save(documentReportsEntity);
                return result;
            } catch (IOException e) {
                throw  new RuntimeException(e);
            }
        }else {
            return result;
        }
    }

    @Override
    public byte[] downloadFolder(String filename){
        S3Object object = s3.getObject(bucketName, filename);
        S3ObjectInputStream objectContent = object.getObjectContent();
        try {
            return IOUtils.toByteArray(objectContent);
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }
    }

    @Override
    public File downloadFile(Long appId, String docType, String docList, String fileName) {
        String folderPath = appId + "/" + docType +"/" + docList + "/" + fileName ;

        int leftLimit = 97; // numeral 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedFileName = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        S3Object object = s3.getObject(bucketName, folderPath);
        try {
            InputStream reader = new BufferedInputStream(
                    object.getObjectContent());
            String tempDir = System.getProperty("java.io.tmpdir");
            File file = new File(tempDir+"/"+generatedFileName+"."+extension);
            OutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
            int read = -1;
            while ( ( read = reader.read() ) != -1 ) {
                writer.write(read);
            }
            writer.flush();
            writer.close();
            reader.close();
            return file;
        } catch (IndexOutOfBoundsException e) {
            throw  new RuntimeException(e);
        } catch (FileAlreadyExistsException e2) {
            throw  new RuntimeException("Do you want to download again");
        }catch (AccessDeniedException e3){
            throw  new RuntimeException("Access Denied Exception");
        } catch (IOException e2) {
            throw  new RuntimeException(e2);
        }
    }

    @Override
    public String deleteS3File(DocumentReportsData documentReportsData) {
        Long appId = documentReportsData.getAppId();
        Long docTypeId = documentReportsData.getDocTypeId();
        Long docCategoryId = documentReportsData.getDocCategoryId();
        Long docListId = documentReportsData.getDocListId();
        String fileName = documentReportsData.getFileName();
        int key = documentReportsData.getKey();
        DocumentReportsEntity document = documentReportsRepository.getDocumentDetails(appId,docTypeId,docCategoryId,docListId,fileName);
        if(document != null) {
             String docTypeName = null;
            if(key == 1){
                docTypeName = document.getDocumentTypeEntity().getName();
            }else if(key==2){
                docTypeName = document.getDocumentCategoryEntity().getName();
            }
            String docListName = document.getDocumentListEntity().getName();
            String fileName1 = document.getFileName();
            String filePath = appId + "/" + docTypeName + "/" + docListName + "/" + fileName1;
            documentReportsRepository.deleteDocDetails(appId,docTypeId,docCategoryId,docListId,fileName1);
            s3.deleteObject(bucketName, filePath);
            return "Success";
        }else {
            return "File Already Deleted";
        }
    }

    @Override
    public List<String> listAllFiles() {
        ListObjectsV2Result listObjectsV2Result = s3.listObjectsV2(bucketName);
        return  listObjectsV2Result.getObjectSummaries().stream().map(S3ObjectSummary::getKey).collect(Collectors.toList());
    }

    private File convertMultiPartToFile(MultipartFile file ) throws IOException
    {
        String tempDir = System.getProperty("java.io.tmpdir");
        File convFile = new File( tempDir+"/"+file.getOriginalFilename() );
//        File convFile = new File( file.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }

    @Override
    public String customerDocReports(Long appId) {
        try{
            Collection<DocumentReportsEntity> report = documentReportsRepository.findCustomerDocReports(appId);
            JSONObject json=new JSONObject();
            if(report!=null && report.size()>0){
                JSONArray array=new JSONArray();
                for(DocumentReportsEntity d : report){
                    JSONObject json1=new JSONObject();
                    json1.put("id",d.getId());
                    json1.put("appId",d.getApplicationEntity().getId());
                    json1.put("customerName",d.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
                    json1.put("customerType",d.getDocumentTypeEntity().getType());
                    json1.put("docTypeId",d.getDocumentTypeEntity().getId());
                    json1.put("docType",d.getDocumentTypeEntity().getDisplayName());
                    json1.put("docTypeName",d.getDocumentTypeEntity().getName());
                    json1.put("docCategoryId",d.getDocumentCategoryEntity().getId());
                    json1.put("docCategoryType",d.getDocumentCategoryEntity().getCategory());
                    json1.put("docCategory",d.getDocumentCategoryEntity().getDisplayName());
                    json1.put("docCategoryName",d.getDocumentCategoryEntity().getName());
                    json1.put("docListId",d.getDocumentListEntity().getId());
                    json1.put("docListType",d.getDocumentListEntity().getCategory());
                    json1.put("docList",d.getDocumentListEntity().getDisplayName());
                    json1.put("docListName",d.getDocumentListEntity().getName());
                    json1.put("deferral",d.getDocumentListEntity().getDeferral());
                    json1.put("defType",d.getDocumentListEntity().getType());
                    json1.put("defTime",d.getDocumentListEntity().getDeferralTime());
                    json1.put("fileName",d.getFileName());
                    json1.put("mandatory",d.getDocumentListEntity().getMandatory());
                    json1.put("version",d.getVersion());
                    array.put(json1);
                }
                json.put("documentReports",array);
            }else{
                return "No Value present";
            }
            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String documentReports(Long appId) {
        JSONObject json=new JSONObject();
        JSONArray array = new JSONArray();
//        List<DocumentTypeEntity> documentTypeEntities = documentTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "type"));
        Collection<DocumentReportsEntity> report = documentReportsRepository.findCustomerDocReports(appId);
        Collection<OtherDocumentReportsEntity> otherReport = otherDocumentReportsRepository.findCustomerDocReports(appId);
        if(report!=null && report.size()>0 || otherReport!=null && otherReport.size()>0){
            for (DocumentReportsEntity d : report) {
                JSONObject json1 = new JSONObject();
                if(array.length() == 0){
                    JSONObject fileJson = new JSONObject();
                    JSONArray fileArray = new JSONArray();
                    json1.put("id", d.getId());
                    json1.put("appId", d.getApplicationEntity().getId());
                    json1.put("customerName", d.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
                    json1.put("customerType", d.getDocumentTypeEntity().getType());
                    json1.put("docTypeId", d.getDocumentTypeEntity().getId());
                    json1.put("docType", d.getDocumentTypeEntity().getDisplayName());
                    json1.put("docTypeName", d.getDocumentTypeEntity().getName());
                    json1.put("docCategoryId", d.getDocumentCategoryEntity().getId());
                    json1.put("docCategoryType", d.getDocumentCategoryEntity().getCategory());
                    json1.put("docCategory", d.getDocumentCategoryEntity().getDisplayName());
                    json1.put("docCategoryName", d.getDocumentCategoryEntity().getName());
                    json1.put("docListId", d.getDocumentListEntity().getId());
                    json1.put("docListType", d.getDocumentListEntity().getCategory());
                    json1.put("docList", d.getDocumentListEntity().getDisplayName());
                    json1.put("docListName", d.getDocumentListEntity().getName());
                    json1.put("deferral", d.getDocumentListEntity().getDeferral());
                    json1.put("defType", d.getDocumentListEntity().getType());
                    json1.put("defTime", d.getDocumentListEntity().getDeferralTime());

                    fileJson.put("fileName",d.getFileName());
                    fileArray.put(fileJson);
                    json1.put("fileNames", fileArray);

                    json1.put("mandatory", d.getDocumentListEntity().getMandatory());
                    json1.put("version", d.getVersion());
                    array.put(json1);
                }else{
                    JSONObject fileJson = new JSONObject();
                    Boolean flag = true;
                    for(int i=0;i<array.length();i++){
                        JSONObject jsonObject = (JSONObject) array.get(i);
                        if(jsonObject.get("docListId").equals(d.getDocumentListEntity().getId())){
                            fileJson.put("fileName",d.getFileName());
                            JSONArray fileArray = (JSONArray) jsonObject.get("fileNames");
                            fileArray.put(fileJson);
                            jsonObject.put("fileNames", fileArray);
                            array.put(i,jsonObject);
                            flag = false;
                            break;
                        }
                    }
                    if(flag){
                        JSONArray fileArray = new JSONArray();
                        json1.put("id", d.getId());
                        json1.put("appId", d.getApplicationEntity().getId());
                        json1.put("customerName", d.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
                        json1.put("customerType", d.getDocumentTypeEntity().getType());
                        json1.put("docTypeId", d.getDocumentTypeEntity().getId());
                        json1.put("docType", d.getDocumentTypeEntity().getDisplayName());
                        json1.put("docTypeName", d.getDocumentTypeEntity().getName());
                        json1.put("docCategoryId", d.getDocumentCategoryEntity().getId());
                        json1.put("docCategoryType", d.getDocumentCategoryEntity().getCategory());
                        json1.put("docCategory", d.getDocumentCategoryEntity().getDisplayName());
                        json1.put("docCategoryName", d.getDocumentCategoryEntity().getName());
                        json1.put("docListId", d.getDocumentListEntity().getId());
                        json1.put("docListType", d.getDocumentListEntity().getCategory());
                        json1.put("docList", d.getDocumentListEntity().getDisplayName());
                        json1.put("docListName", d.getDocumentListEntity().getName());
                        json1.put("deferral", d.getDocumentListEntity().getDeferral());
                        json1.put("defType", d.getDocumentListEntity().getType());
                        json1.put("defTime", d.getDocumentListEntity().getDeferralTime());

                        fileJson.put("fileName",d.getFileName());
                        fileArray.put(fileJson);
                        json1.put("fileNames", fileArray);

                        json1.put("mandatory", d.getDocumentListEntity().getMandatory());
                        json1.put("version", d.getVersion());
                        array.put(json1);
                    }
                }
            }
            for (OtherDocumentReportsEntity d : otherReport) {
                JSONObject json1 = new JSONObject();
                if(array.length() == 0) {
                    JSONObject fileJson = new JSONObject();
                    JSONArray fileArray = new JSONArray();
                    json1.put("id", d.getId());
                    json1.put("appId", d.getApplicationEntity().getId());
                    json1.put("customerName", d.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
                    json1.put("customerType", d.getDocumentTypeEntity().getType());

                    json1.put("docTypeId", d.getDocumentTypeEntity().getId());
                    json1.put("docType", d.getDocumentTypeEntity().getDisplayName());
                    json1.put("docTypeName", d.getDocumentTypeEntity().getName());

                    json1.put("docCategoryId", d.getDocumentCategoryEntity().getId());
                    json1.put("docCategoryType", d.getDocumentCategoryEntity().getCategory());
                    json1.put("docCategory", d.getDocumentCategoryEntity().getDisplayName());
                    json1.put("docCategoryName", d.getDocumentCategoryEntity().getName());

                    json1.put("docListId", d.getDocumentListEntity().getId());
                    json1.put("docListType", d.getDocumentListEntity().getCategory());
                    json1.put("docList", d.getDocumentListEntity().getDisplayName());
                    json1.put("docListName", d.getDocumentListEntity().getName());

                    json1.put("otherDocId", d.getOtherDocumentMasterEntity().getId());
                    json1.put("otherDocType", d.getOtherDocumentMasterEntity().getDeferralType());
                    json1.put("otherDoc", d.getOtherDocumentMasterEntity().getDisplayName());
                    json1.put("otherDocName", d.getOtherDocumentMasterEntity().getName());
                    json1.put("otherDocDefStatus", d.getOtherDocumentMasterEntity().getStatus());
                    json1.put("otherDocDefIniTime", d.getOtherDocumentMasterEntity().getInitialTime());
                    json1.put("otherDocDefRevTime", d.getOtherDocumentMasterEntity().getRevisedTime());
                    json1.put("otherDocRMName", d.getOtherDocumentMasterEntity().getRmName());

                    json1.put("deferral", d.getDocumentListEntity().getDeferral());
                    json1.put("defType", d.getDocumentListEntity().getType());
                    json1.put("defTime", d.getDocumentListEntity().getDeferralTime());

                    fileJson.put("fileName",d.getFileName());
                    fileArray.put(fileJson);
                    json1.put("fileNames", fileArray);
//                    json1.put("fileName", d.getFileName());

                    json1.put("mandatory", d.getDocumentListEntity().getMandatory());
                    array.put(json1);
                }else{
                    JSONObject fileJson = new JSONObject();
                    Boolean flag = true;
                    for(int i=0;i<array.length();i++){
                        JSONObject jsonObject = (JSONObject) array.get(i);
                        if (jsonObject.has("otherDocId")) {
                            if (jsonObject.get("otherDocId").equals(d.getOtherDocumentMasterEntity().getId())) {
                                fileJson.put("fileName", d.getFileName());
                                JSONArray fileArray = (JSONArray) jsonObject.get("fileNames");
                                fileArray.put(fileJson);
                                jsonObject.put("fileNames", fileArray);
                                array.put(i, jsonObject);
                                flag = false;
                                break;
                            }
                        }
                    }
                    if(flag){
                        JSONArray fileArray = new JSONArray();
                        json1.put("id", d.getId());
                        json1.put("appId", d.getApplicationEntity().getId());
                        json1.put("customerName", d.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
                        json1.put("customerType", d.getDocumentTypeEntity().getType());

                        json1.put("docTypeId", d.getDocumentTypeEntity().getId());
                        json1.put("docType", d.getDocumentTypeEntity().getDisplayName());
                        json1.put("docTypeName", d.getDocumentTypeEntity().getName());

                        json1.put("docCategoryId", d.getDocumentCategoryEntity().getId());
                        json1.put("docCategoryType", d.getDocumentCategoryEntity().getCategory());
                        json1.put("docCategory", d.getDocumentCategoryEntity().getDisplayName());
                        json1.put("docCategoryName", d.getDocumentCategoryEntity().getName());

                        json1.put("docListId", d.getDocumentListEntity().getId());
                        json1.put("docListType", d.getDocumentListEntity().getCategory());
                        json1.put("docList", d.getDocumentListEntity().getDisplayName());
                        json1.put("docListName", d.getDocumentListEntity().getName());

                        json1.put("otherDocId", d.getOtherDocumentMasterEntity().getId());
                        json1.put("otherDocType", d.getOtherDocumentMasterEntity().getDeferralType());
                        json1.put("otherDoc", d.getOtherDocumentMasterEntity().getDisplayName());
                        json1.put("otherDocName", d.getOtherDocumentMasterEntity().getName());
                        json1.put("otherDocDefStatus", d.getOtherDocumentMasterEntity().getStatus());
                        json1.put("otherDocDefIniTime", d.getOtherDocumentMasterEntity().getInitialTime());
                        json1.put("otherDocDefRevTime", d.getOtherDocumentMasterEntity().getRevisedTime());
                        json1.put("otherDocRMName", d.getOtherDocumentMasterEntity().getRmName());

                        json1.put("deferral", d.getDocumentListEntity().getDeferral());
                        json1.put("defType", d.getDocumentListEntity().getType());
                        json1.put("defTime", d.getDocumentListEntity().getDeferralTime());

                        fileJson.put("fileName",d.getFileName());
                        fileArray.put(fileJson);
                        json1.put("fileNames", fileArray);
//                    json1.put("fileName", d.getFileName());

                        json1.put("mandatory", d.getDocumentListEntity().getMandatory());
                        array.put(json1);
                    }
                }
            }
        }
        json.put("documentReports",array);
        return json.toString();
    }

    @Override
    public String getRenewalEnhancementDocReports(Long custId) {
        List<ApplicationEntity> applicationEntity = applicationRepository.findAllApplicationIds(custId);

        JSONObject json=new JSONObject();
        JSONArray array = new JSONArray();
        for(ApplicationEntity application : applicationEntity){
            Long appId = application.getId();
            Collection<DocumentReportsEntity> report = documentReportsRepository.findCustomerDocReports(appId);
            Collection<OtherDocumentReportsEntity> otherReport = otherDocumentReportsRepository.findCustomerDocReports(appId);
            JSONObject json2=new JSONObject();
            if(report!=null && report.size()>0 || otherReport!=null && otherReport.size()>0){
                JSONArray array1 = new JSONArray();
                for(DocumentReportsEntity d : report){
                    JSONObject json1=new JSONObject();
                    if(array1.length() == 0) {
                        JSONObject fileJson = new JSONObject();
                        JSONArray fileArray = new JSONArray();
                        json1.put("id", d.getId());
                        json1.put("appId", d.getApplicationEntity().getId());
                        json1.put("customerName", d.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
                        json1.put("customerType", d.getDocumentTypeEntity().getType());
                        json1.put("docTypeId", d.getDocumentTypeEntity().getId());
                        json1.put("docType", d.getDocumentTypeEntity().getDisplayName());
                        json1.put("docTypeName", d.getDocumentTypeEntity().getName());
                        json1.put("docCategoryId", d.getDocumentCategoryEntity().getId());
                        json1.put("docCategoryType", d.getDocumentCategoryEntity().getCategory());
                        json1.put("docCategory", d.getDocumentCategoryEntity().getDisplayName());
                        json1.put("docCategoryName", d.getDocumentCategoryEntity().getName());
                        json1.put("docListId", d.getDocumentListEntity().getId());
                        json1.put("docListType", d.getDocumentListEntity().getCategory());
                        json1.put("docList", d.getDocumentListEntity().getDisplayName());
                        json1.put("docListName", d.getDocumentListEntity().getName());
                        json1.put("deferral", d.getDocumentListEntity().getDeferral());
                        json1.put("defType", d.getDocumentListEntity().getType());
                        json1.put("defTime", d.getDocumentListEntity().getDeferralTime());

//                        json1.put("fileName", d.getFileName());
                        fileJson.put("fileName",d.getFileName());
                        fileArray.put(fileJson);
                        json1.put("fileNames", fileArray);

                        json1.put("mandatory", d.getDocumentListEntity().getMandatory());
                        json1.put("version", d.getVersion());
                        array1.put(json1);
                    }else{
                        JSONObject fileJson = new JSONObject();
                        Boolean flag = true;
                        for(int i=0;i<array1.length();i++){
                            JSONObject jsonObject = (JSONObject) array1.get(i);
                            if (jsonObject.has("docListId")) {
                                if (jsonObject.get("docListId").equals(d.getDocumentListEntity().getId())) {
                                    fileJson.put("fileName", d.getFileName());
                                    JSONArray fileArray = (JSONArray) jsonObject.get("fileNames");
                                    fileArray.put(fileJson);
                                    jsonObject.put("fileNames", fileArray);
                                    array1.put(i, jsonObject);
                                    flag = false;
                                    break;
                                }
                            }
                        }
                        if(flag) {
                            JSONArray fileArray = new JSONArray();
                            json1.put("id", d.getId());
                            json1.put("appId", d.getApplicationEntity().getId());
                            json1.put("customerName", d.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
                            json1.put("customerType", d.getDocumentTypeEntity().getType());
                            json1.put("docTypeId", d.getDocumentTypeEntity().getId());
                            json1.put("docType", d.getDocumentTypeEntity().getDisplayName());
                            json1.put("docTypeName", d.getDocumentTypeEntity().getName());
                            json1.put("docCategoryId", d.getDocumentCategoryEntity().getId());
                            json1.put("docCategoryType", d.getDocumentCategoryEntity().getCategory());
                            json1.put("docCategory", d.getDocumentCategoryEntity().getDisplayName());
                            json1.put("docCategoryName", d.getDocumentCategoryEntity().getName());
                            json1.put("docListId", d.getDocumentListEntity().getId());
                            json1.put("docListType", d.getDocumentListEntity().getCategory());
                            json1.put("docList", d.getDocumentListEntity().getDisplayName());
                            json1.put("docListName", d.getDocumentListEntity().getName());
                            json1.put("deferral", d.getDocumentListEntity().getDeferral());
                            json1.put("defType", d.getDocumentListEntity().getType());
                            json1.put("defTime", d.getDocumentListEntity().getDeferralTime());

//                        json1.put("fileName", d.getFileName());
                            fileJson.put("fileName",d.getFileName());
                            fileArray.put(fileJson);
                            json1.put("fileNames", fileArray);

                            json1.put("mandatory", d.getDocumentListEntity().getMandatory());
                            json1.put("version", d.getVersion());
                            array1.put(json1);
                        }
                    }
                }
                for(OtherDocumentReportsEntity d : otherReport){
                    JSONObject json1 = new JSONObject();
                    if(array1.length() == 0) {
                        JSONObject fileJson = new JSONObject();
                        JSONArray fileArray = new JSONArray();
                        json1.put("id", d.getId());
                        json1.put("appId", d.getApplicationEntity().getId());
                        json1.put("customerName", d.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
                        json1.put("customerType", d.getDocumentTypeEntity().getType());

                        json1.put("docTypeId", d.getDocumentListEntity().getId());
                        json1.put("docType", d.getDocumentTypeEntity().getDisplayName());
                        json1.put("docTypeName", d.getDocumentTypeEntity().getName());

                        json1.put("docCategoryId", d.getDocumentCategoryEntity().getId());
                        json1.put("docCategoryType", d.getDocumentCategoryEntity().getCategory());
                        json1.put("docCategory", d.getDocumentCategoryEntity().getDisplayName());
                        json1.put("docCategoryName", d.getDocumentCategoryEntity().getName());

                        json1.put("docListId", d.getDocumentListEntity().getId());
                        json1.put("docListType", d.getDocumentListEntity().getCategory());
                        json1.put("docList", d.getDocumentListEntity().getDisplayName());
                        json1.put("docListName", d.getDocumentListEntity().getName());

                        json1.put("otherDocId", d.getOtherDocumentMasterEntity().getId());
                        json1.put("otherDocType", d.getOtherDocumentMasterEntity().getDeferralType());
                        json1.put("otherDoc", d.getOtherDocumentMasterEntity().getDisplayName());
                        json1.put("otherDocName", d.getOtherDocumentMasterEntity().getName());
                        json1.put("otherDocDefStatus", d.getOtherDocumentMasterEntity().getStatus());
                        json1.put("otherDocDefIniTime", d.getOtherDocumentMasterEntity().getInitialTime());
                        json1.put("otherDocDefRevTime", d.getOtherDocumentMasterEntity().getRevisedTime());
                        json1.put("otherDocRMName", d.getOtherDocumentMasterEntity().getRmName());

                        json1.put("deferral", d.getDocumentListEntity().getDeferral());
                        json1.put("defType", d.getDocumentListEntity().getType());
                        json1.put("defTime", d.getDocumentListEntity().getDeferralTime());

//                        json1.put("fileName", d.getFileName());
                        fileJson.put("fileName",d.getFileName());
                        fileArray.put(fileJson);
                        json1.put("fileNames", fileArray);

                        json1.put("mandatory", d.getDocumentListEntity().getMandatory());
                        array1.put(json1);
                    }else{
                        JSONObject fileJson = new JSONObject();
                        Boolean flag = true;
                        for(int i=0;i<array1.length();i++){
                            JSONObject jsonObject = (JSONObject) array1.get(i);
                            if (jsonObject.has("otherDocId")) {
                                if (jsonObject.get("otherDocId").equals(d.getOtherDocumentMasterEntity().getId())) {
                                    fileJson.put("fileName", d.getFileName());
                                    JSONArray fileArray = (JSONArray) jsonObject.get("fileNames");
                                    fileArray.put(fileJson);
                                    jsonObject.put("fileNames", fileArray);
                                    array1.put(i, jsonObject);
                                    flag = false;
                                    break;
                                }
                            }
                        }
                        if(flag) {
                            JSONArray fileArray = new JSONArray();
                            json1.put("id", d.getId());
                            json1.put("appId", d.getApplicationEntity().getId());
                            json1.put("customerName", d.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
                            json1.put("customerType", d.getDocumentTypeEntity().getType());

                            json1.put("docTypeId", d.getDocumentListEntity().getId());
                            json1.put("docType", d.getDocumentTypeEntity().getDisplayName());
                            json1.put("docTypeName", d.getDocumentTypeEntity().getName());

                            json1.put("docCategoryId", d.getDocumentCategoryEntity().getId());
                            json1.put("docCategoryType", d.getDocumentCategoryEntity().getCategory());
                            json1.put("docCategory", d.getDocumentCategoryEntity().getDisplayName());
                            json1.put("docCategoryName", d.getDocumentCategoryEntity().getName());

                            json1.put("docListId", d.getDocumentListEntity().getId());
                            json1.put("docListType", d.getDocumentListEntity().getCategory());
                            json1.put("docList", d.getDocumentListEntity().getDisplayName());
                            json1.put("docListName", d.getDocumentListEntity().getName());

                            json1.put("otherDocId", d.getOtherDocumentMasterEntity().getId());
                            json1.put("otherDocType", d.getOtherDocumentMasterEntity().getDeferralType());
                            json1.put("otherDoc", d.getOtherDocumentMasterEntity().getDisplayName());
                            json1.put("otherDocName", d.getOtherDocumentMasterEntity().getName());
                            json1.put("otherDocDefStatus", d.getOtherDocumentMasterEntity().getStatus());
                            json1.put("otherDocDefIniTime", d.getOtherDocumentMasterEntity().getInitialTime());
                            json1.put("otherDocDefRevTime", d.getOtherDocumentMasterEntity().getRevisedTime());
                            json1.put("otherDocRMName", d.getOtherDocumentMasterEntity().getRmName());

                            json1.put("deferral", d.getDocumentListEntity().getDeferral());
                            json1.put("defType", d.getDocumentListEntity().getType());
                            json1.put("defTime", d.getDocumentListEntity().getDeferralTime());

    //                        json1.put("fileName", d.getFileName());
                            fileJson.put("fileName",d.getFileName());
                            fileArray.put(fileJson);
                            json1.put("fileNames", fileArray);

                            json1.put("mandatory", d.getDocumentListEntity().getMandatory());
                            array1.put(json1);
                        }

                    }
                }

                if(application.getAppType() == 2){
                    json2.put("seqNo",application.getSeqNo());
                    json2.put("appId",application.getId());
                    json2.put("appType",application.getAppType());
                    json2.put("wfType",application.getWfType());
                    json2.put("report",array1);
                }else if(application.getAppType() == 3){
                    json2.put("seqNo",application.getSeqNo());
                    json2.put("appId",application.getId());
                    json2.put("appType",application.getAppType());
                    json2.put("wfType",application.getWfType());
                    json2.put("report",array1);
                }else{
                    json2.put("seqNo",application.getSeqNo());
                    json2.put("appId",application.getId());
                    json2.put("appType",application.getAppType());
                    json2.put("wfType",application.getWfType());
                    json2.put("report",array1);
                }

                array.put(json2);
            }
            json.put("documentReports",array);
        }
        return json.toString();
    }

    public String assessmentCheck(String constitution, Long appId) {
        JSONObject json = new JSONObject();

        Boolean dealer = false;
        Boolean vendor = false;
        Boolean recourse = false;
        Boolean nonRecourse = false;
        String productType = "";
        Boolean assessmentKycFlag = false;
        Collection<ProposedProductsEntity> proposedProductList = proposedProductsRepository.getByAppId(appId);
        for (ProposedProductsEntity proposedProductsEntity: proposedProductList){

            ProgramNormsEntity programNorms = programDetailsRepository.fidByAppId(proposedProductsEntity.getCustomerInfoEntity().getId(),proposedProductsEntity.getProduct());
            if(programNorms.getTransactionType().equals("Recourse")){
                recourse = true;
            }else if(programNorms.getTransactionType().equals("Non Recourse")){
                nonRecourse = true;
            }
//            System.out.println("TransactionType -->"+programNorms.getTransactionType());

            String productName = proposedProductsEntity.getProduct();
            if(productName.equalsIgnoreCase("Dealer Invoice Finance") ||
                    productName.equalsIgnoreCase("Dealer Purchase Order Finance") ||
                    productName.equalsIgnoreCase("Anchor Sales Bill Discounting")){
                dealer = true;
            }
            else if(productName.equalsIgnoreCase("Vendor Invoice Finance") ||
                    productName.equalsIgnoreCase("Vendor Purchase Order Finance") ||
                    productName.equalsIgnoreCase("Anchor Purchase Bill Discounting")){
                vendor = true;
            }
        }

        if(recourse && !nonRecourse){
            assessmentKycFlag = true;
        }

        if(vendor && !dealer){
            productType = "Vendor Type";
        }else if((dealer && !vendor) || (vendor && dealer)){
            productType = "Dealers Type";
        }

        Collection<DocumentReportsEntity> report = documentReportsRepository.findCustomerDocReports(appId);
        if(report!=null && report.size()>0){

//            System.out.println("vendor -->"+vendor);
//            System.out.println("dealer -->"+dealer);
//            System.out.println("productType -->"+productType);
//            System.out.println("constitution -->"+constitution);
            Boolean flag = false;
            for(DocumentReportsEntity d : report){
                if(d.getDocumentTypeEntity().getType() == 2 || d.getDocumentTypeEntity().getType() == 3){
                    if(d.getDocumentTypeEntity().getType() == 2){
                        String docConstitution = d.getDocumentCategoryEntity().getDisplayName();
//                        System.out.println("KYC Document:");
//                        System.out.println("docConstitution -->"+docConstitution);
                        if(constitution.equals(docConstitution)){
                            json.put("status",false);
                            json.put("assessmentType", d.getDocumentListEntity().getCategory());
                            json.put("assessmentKycFlag",assessmentKycFlag);
                            json.put("vendorFlag",vendor);
                            json.put("dealerFlag",dealer);
                            json.put("productType",productType);
                        }else {
                            json.put("status", true);
                            json.put("assessmentKycFlag",assessmentKycFlag);
                            json.put("vendorFlag",vendor);
                            json.put("dealerFlag",dealer);
                            json.put("productType",productType);
                            json.put("assessmentType", d.getDocumentListEntity().getCategory());

                            return json.toString();
                        }
                    }else if(d.getDocumentTypeEntity().getType() == 3){
                        String docConstitution = d.getDocumentCategoryEntity().getCategory();
                        String docProductType = d.getDocumentCategoryEntity().getDisplayName();
                        String docAssessmentType = d.getDocumentListEntity().getCategory();
//                        System.out.println("Credit Document:");
//                        System.out.println("docConstitution -->"+docConstitution);
//                        System.out.println("docProductType -->"+docProductType);
//                        System.out.println("docAssessmentType -->"+docAssessmentType);
                        if(constitution.equals(docConstitution) && productType.equals(docProductType) && (assessmentKycFlag || !docAssessmentType.equals("KYC"))){
                            json.put("status",false);
                            json.put("assessmentType", d.getDocumentListEntity().getCategory());
                            json.put("assessmentKycFlag",assessmentKycFlag);
                            json.put("vendorFlag",vendor);
                            json.put("dealerFlag",dealer);
                            json.put("productType",productType);
                        }else {
                            json.put("status", true);
                            json.put("assessmentKycFlag",assessmentKycFlag);
                            json.put("vendorFlag",vendor);
                            json.put("dealerFlag",dealer);
                            json.put("productType",productType);
                            json.put("assessmentType", d.getDocumentListEntity().getCategory());

                        }
                        flag = false;
                        break;
                    }
                }else{
                    flag = true;
                }
            }

            if(flag){
                json.put("status",false);
                json.put("assessmentType", "none");
                json.put("vendorFlag",vendor);
                json.put("dealerFlag",dealer);
                json.put("productType",productType);
                json.put("assessmentKycFlag",assessmentKycFlag);
            }
        }else{
            json.put("status",false);
            json.put("assessmentType", "none");
            json.put("vendorFlag",vendor);
            json.put("dealerFlag",dealer);
            json.put("productType",productType);
            json.put("assessmentKycFlag",assessmentKycFlag);
        }
        return json.toString();
    }

    public String deleteCreditDocuments(Long appId) {
//        Long appId = documentReportsData.getAppId();

        Collection<DocumentReportsEntity> report = documentReportsRepository.findCustomerDocReports(appId);
        for(DocumentReportsEntity d : report){
            if(d.getDocumentTypeEntity().getType() == 3 || d.getDocumentTypeEntity().getType() == 2) {
                Long docTypeId = d.getDocumentTypeEntity().getId();
                Long docCategoryId = d.getDocumentCategoryEntity().getId();
                Long docListId = d.getDocumentListEntity().getId();
                String fileName = d.getFileName();

                int key = 1;
                DocumentReportsEntity document = documentReportsRepository.getDocumentDetails(appId, docTypeId, docCategoryId, docListId, fileName);
                if (document != null) {
                    String docTypeName = null;
                    if (key == 1) {
                        docTypeName = document.getDocumentTypeEntity().getName();
                    } else if (key == 2) {
                        docTypeName = document.getDocumentCategoryEntity().getName();
                    }
                    String docListName = document.getDocumentListEntity().getName();
                    String fileName1 = document.getFileName();
                    String filePath = appId + "/" + docTypeName + "/" + docListName + "/" + fileName1;
                    documentReportsRepository.deleteDocDetails(appId, docTypeId, docCategoryId, docListId, fileName1);
                    s3.deleteObject(bucketName, filePath);
                }
            }
        }
        JSONObject json = new JSONObject();
        json.put("message","Success");
        json.put("AppId",appId);
        return json.toString();
    }
}
