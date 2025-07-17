package org.vcpl.triton.dms.docManagement.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.dms.dmsMaster.repository.DocumentCategoryRepository;
import org.vcpl.triton.dms.dmsMaster.repository.DocumentListRepository;
import org.vcpl.triton.dms.dmsMaster.repository.DocumentTypeRepository;
import org.vcpl.triton.dms.docManagement.data.OtherDocumentReportsData;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentMasterEntity;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentReportsEntity;
import org.vcpl.triton.dms.docManagement.repository.OtherDocumentMasterRepository;
import org.vcpl.triton.dms.docManagement.repository.OtherDocumentReportsRepository;
import org.vcpl.triton.dms.docValidation.OtherDocValidationService;
import org.vcpl.triton.security.config.DatasourceForDbConnect;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.sql.Timestamp;
import java.util.*;

public class OtherDocumentReportsService implements IOtherDocumentReports{

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    OtherDocumentReportsRepository otherDocumentReportsRepository;

    @Autowired
    OtherDocumentMasterRepository otherDocumentMasterRepository;

    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @Autowired
    DocumentCategoryRepository documentCategoryRepository;

    @Autowired
    DocumentListRepository documentListRepository;

    @Autowired
    OtherDocumentMasterService otherDocumentMasterService;

    @Autowired
    OtherDocValidationService otherDocValidationService;

    private String bucketName = null;

    private static AmazonS3 s3;

    public OtherDocumentReportsService(DatasourceForDbConnect data, AmazonS3 s3) {
        this.s3 = s3;
        bucketName = data.getBucketName();
    }

//    public OtherDocumentReportsService() {
//        s3 = AmazonS3ClientBuilder.defaultClient();
//    }

    @Override
    public JSONObject saveFile(OtherDocumentReportsData otherDocumentReportsData, MultipartFile file) {
        Long appId = otherDocumentReportsData.getAppId();
        String docTypeName = otherDocumentReportsData.getDocTypeName();
        String docCategoryName = otherDocumentReportsData.getDocCategoryName();
        String docListName = otherDocumentReportsData.getDocListName();

        JSONObject json=new JSONObject();

        JSONObject containerObject = new JSONObject(otherDocumentReportsData);
        if(containerObject.has("otherDocMasterId")){
            Optional<OtherDocumentMasterEntity> otherDocumentMasterEntity = otherDocumentMasterRepository.findById(otherDocumentReportsData.getOtherDocMasterId());
            String documentName = otherDocumentMasterEntity.get().getName();
            String Filename = file.getOriginalFilename();

            String[] myArr = {"pdf", "xls", "xlsx", "doc", "docx", "txt", "png", "jpg", "jpeg", "zip"};
            List<String> myEx = Arrays.asList(myArr);
            String fileName = file.getOriginalFilename();
            String extension = "";
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i + 1);
            }
            if (!myEx.contains(extension.toLowerCase())) {
                json.put("message","incorrectFormat");
                return json;
            }

            String originalFilename=Filename.replaceAll("[^.,a-zA-Z0-9]+", "_");

            String folderPath = null;
            if(otherDocumentReportsData.getKey()==1){
                folderPath = appId + "/" + docTypeName +"/" + docListName + "/"+ documentName + "/" + originalFilename;
            }else if(otherDocumentReportsData.getKey()==2){
                folderPath = appId + "/" + docCategoryName +"/" + docListName + "/"+ documentName + "/" + originalFilename;
            }
//            String result ="success";
            String result = otherDocValidationService.documentCheck(otherDocumentReportsData.getAppId(),otherDocumentReportsData.getDocTypeId(),otherDocumentReportsData.getDocCategoryId(),otherDocumentReportsData.getDocListId(),otherDocumentReportsData.getOtherDocMasterId(),originalFilename);
            if (result.equals("success")) {
                try {

                    File file1 = convertMultiPartToFile(file);
                    PutObjectResult putObjectResult = s3.putObject(bucketName, folderPath, file1);

                    Date date = new Date();
                    OtherDocumentReportsEntity otherDocumentReportsEntity = new OtherDocumentReportsEntity();
                    otherDocumentReportsEntity.setApplicationEntity(applicationRepository.findById(otherDocumentReportsData.getAppId()).orElseThrow());
                    otherDocumentReportsEntity.setDocumentTypeEntity(documentTypeRepository.findById(otherDocumentReportsData.getDocTypeId()).orElseThrow());
                    otherDocumentReportsEntity.setDocumentCategoryEntity(documentCategoryRepository.findById(otherDocumentReportsData.getDocCategoryId()).orElseThrow());
                    otherDocumentReportsEntity.setDocumentListEntity(documentListRepository.findById(otherDocumentReportsData.getDocListId()).orElseThrow());
                    otherDocumentReportsEntity.setOtherDocumentMasterEntity(otherDocumentMasterRepository.findById(otherDocumentReportsData.getOtherDocMasterId()).orElseThrow());
                    otherDocumentReportsEntity.setFileName(originalFilename);
                    otherDocumentReportsEntity.setCreatedBy("Balaji R");
                    otherDocumentReportsEntity.setCreatedAt(new Timestamp(date.getTime()));
                    otherDocumentReportsEntity.setUpdatedBy("Balaji R");
                    otherDocumentReportsEntity.setUpdatedAt(new Timestamp(date.getTime()));

                    otherDocumentReportsRepository.save(otherDocumentReportsEntity);

                    json.put("otherDocMasterId",otherDocumentReportsData.getOtherDocMasterId());
                    json.put("message",result);
                    return json;
                } catch (IOException e) {
                    throw  new RuntimeException(e);
                }
            }else {
                json.put("message",result);
                return json;
            }
        }else{
            for(var other : otherDocumentReportsData.getOtherDocumentDataList()){
                String val = otherDocValidationService.otherDocValidation(other,otherDocumentReportsData.getOtherDisplayName());
                if(val.equals("success")) {
                    if (other.getDisplayName().trim().equals(otherDocumentReportsData.getOtherDisplayName().trim())) {
                        Long docMasterId = otherDocumentMasterRepository.save(otherDocumentMasterService.transform(other, otherDocumentReportsData.getRmName())).getId();
                        Optional<OtherDocumentMasterEntity> otherDocumentMasterEntity = otherDocumentMasterRepository.findById(docMasterId);
                        String documentName = otherDocumentMasterEntity.get().getName();
                        String Filename = file.getOriginalFilename();

                        String[] myArr = {"pdf", "xls", "xlsx", "doc", "docx", "txt", "png", "jpg", "jpeg", "zip"};
                        List<String> myEx = Arrays.asList(myArr);
                        String fileName = file.getOriginalFilename();
                        String extension = "";
                        int i = fileName.lastIndexOf('.');
                        if (i > 0) {
                            extension = fileName.substring(i + 1);
                        }
                        if (!myEx.contains(extension.toLowerCase())) {
                            json.put("message","incorrectFormat");
                            return json;
                        }

                        String originalFilename = Filename.replaceAll("[^.,a-zA-Z0-9]+", "_");

                        String folderPath = null;
                        if (otherDocumentReportsData.getKey() == 1) {
                            folderPath = appId + "/" + docTypeName + "/" + docListName + "/" + documentName + "/" + originalFilename;
                        } else if (otherDocumentReportsData.getKey() == 2) {
                            folderPath = appId + "/" + docCategoryName + "/" + docListName + "/" + documentName + "/" + originalFilename;
                        }
//                        String result = "success";
                        String result = otherDocValidationService.documentCheck(otherDocumentReportsData.getAppId(),otherDocumentReportsData.getDocTypeId(),otherDocumentReportsData.getDocCategoryId(),otherDocumentReportsData.getDocListId(),docMasterId,originalFilename);
                        if (result.equals("success")) {
                            try {

                                File file1 = convertMultiPartToFile(file);
                                PutObjectResult putObjectResult = s3.putObject(bucketName, folderPath, file1);

                                Date date = new Date();
                                OtherDocumentReportsEntity otherDocumentReportsEntity = new OtherDocumentReportsEntity();
                                otherDocumentReportsEntity.setApplicationEntity(applicationRepository.findById(otherDocumentReportsData.getAppId()).orElseThrow());
                                otherDocumentReportsEntity.setDocumentTypeEntity(documentTypeRepository.findById(otherDocumentReportsData.getDocTypeId()).orElseThrow());
                                otherDocumentReportsEntity.setDocumentCategoryEntity(documentCategoryRepository.findById(otherDocumentReportsData.getDocCategoryId()).orElseThrow());
                                otherDocumentReportsEntity.setDocumentListEntity(documentListRepository.findById(otherDocumentReportsData.getDocListId()).orElseThrow());
                                otherDocumentReportsEntity.setOtherDocumentMasterEntity(otherDocumentMasterRepository.findById(docMasterId).orElseThrow());
                                otherDocumentReportsEntity.setFileName(originalFilename);
                                otherDocumentReportsEntity.setCreatedBy("Balaji R");
                                otherDocumentReportsEntity.setCreatedAt(new Timestamp(date.getTime()));
                                otherDocumentReportsEntity.setUpdatedBy("Balaji R");
                                otherDocumentReportsEntity.setUpdatedAt(new Timestamp(date.getTime()));

                                otherDocumentReportsRepository.save(otherDocumentReportsEntity);
                                json.put("otherDocMasterId",docMasterId);
                                json.put("message","success");
                                return json;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            json.put("message",result);
                            return json;
                        }
                    }
                }else{
                    json.put("message",val);
                    return json;
                }
            }

            json.put("message","Fail");
            return json;
        }
    }

    @Override
    public Optional<OtherDocumentMasterEntity> getOtherMaster(Long id) {
        return otherDocumentMasterRepository.findById(id);
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
    public File downloadFile(Long appId, String docType, String docList, String docName, String fileName) {
        String folderPath = appId + "/" + docType +"/" + docList + "/" + docName + "/" + fileName ;

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

    public String deleteS3File(OtherDocumentReportsData otherDocumentReportsData) {
        Long appId = otherDocumentReportsData.getAppId();
        Long docTypeId = otherDocumentReportsData.getDocTypeId();
        Long docCategoryId = otherDocumentReportsData.getDocCategoryId();
        Long docListId = otherDocumentReportsData.getDocListId();
        Long docMasterId = otherDocumentReportsData.getOtherDocMasterId();
        String fileName = otherDocumentReportsData.getFileName();
        int key = otherDocumentReportsData.getKey();
        OtherDocumentReportsEntity document = otherDocumentReportsRepository.getDocumentDetails(appId,docTypeId,docCategoryId,docListId,docMasterId,fileName);
        if(document != null) {
            String docTypeName = null;
            if(key == 1){
                docTypeName = document.getDocumentTypeEntity().getName();
            }else if(key==2){
                docTypeName = document.getDocumentCategoryEntity().getName();
            }
            String docListName = document.getDocumentListEntity().getName();
            String docName = document.getOtherDocumentMasterEntity().getName();
            String fileName1 = document.getFileName();
            String filePath = appId + "/" + docTypeName + "/" + docListName + "/" + docName + "/" + fileName1;
            otherDocumentReportsRepository.deleteDocDetails(appId,docTypeId,docCategoryId,docListId,docMasterId,fileName1);
            s3.deleteObject(bucketName, filePath);
            return "Success";
        }else {
            return "File Already Deleted";
        }
    }

    @Override
    public String customerDocReports(Long appId) {
        try{
            Collection<OtherDocumentReportsEntity> report = otherDocumentReportsRepository.findCustomerDocReports(appId);
            JSONObject json=new JSONObject();
            if(report!=null && report.size()>0){
                JSONArray array=new JSONArray();
                for(OtherDocumentReportsEntity d : report){
                    JSONObject json1=new JSONObject();
                    json1.put("id",d.getId());
                    json1.put("appId",d.getApplicationEntity().getId());
                    json1.put("customerName",d.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
                    json1.put("customerType",d.getDocumentTypeEntity().getType());

                    json1.put("docTypeId",d.getDocumentListEntity().getId());
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

                    json1.put("otherDocId",d.getOtherDocumentMasterEntity().getId());
                    json1.put("otherDocType",d.getOtherDocumentMasterEntity().getDeferralType());
                    json1.put("otherDoc",d.getOtherDocumentMasterEntity().getDisplayName());
                    json1.put("otherDocName",d.getOtherDocumentMasterEntity().getName());
                    json1.put("otherDocDefStatus",d.getOtherDocumentMasterEntity().getStatus());
                    json1.put("otherDocDefIniTime",d.getOtherDocumentMasterEntity().getInitialTime());
                    json1.put("otherDocDefRevTime",d.getOtherDocumentMasterEntity().getRevisedTime());
                    json1.put("otherDocRMName",d.getOtherDocumentMasterEntity().getRmName());

                    json1.put("deferral",d.getDocumentListEntity().getDeferral());
                    json1.put("defType",d.getDocumentListEntity().getType());
                    json1.put("defTime",d.getDocumentListEntity().getDeferralTime());
                    json1.put("fileName",d.getFileName());
                    json1.put("mandatory",d.getDocumentListEntity().getMandatory());
                    array.put(json1);
                }
                json.put("otherDocReports",array);
            }else{
                return "No Value present";
            }
            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
