package org.vcpl.triton.dms.docManagement.service;

import com.amazonaws.services.s3.AmazonS3;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.dms.dmsMaster.repository.DocumentListRepository;
import org.vcpl.triton.dms.docManagement.data.OtherDocumentData;
import org.vcpl.triton.dms.docManagement.data.OtherDocumentMasterData;
import org.vcpl.triton.dms.docManagement.entity.DeferralReportsEntity;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentMasterEntity;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentReportsEntity;
import org.vcpl.triton.dms.docManagement.repository.OtherDocumentMasterRepository;
import org.vcpl.triton.dms.docManagement.repository.OtherDocumentReportsRepository;
import org.vcpl.triton.security.config.DatasourceForDbConnect;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;


public class OtherDocumentMasterService implements IOtherDocumentMaster {

    @Autowired
    OtherDocumentMasterRepository otherDocumentMasterRepository;

    @Autowired
    OtherDocumentReportsRepository otherDocumentReportsRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    DocumentListRepository documentListRepository;

    @Autowired
    IDeferralReports deferralReportsService;

    private String bucketName = null;

    private static AmazonS3 s3;

    public OtherDocumentMasterService(DatasourceForDbConnect data, AmazonS3 s3) {
        this.s3 = s3;
        bucketName = data.getBucketName();
    }

    @Override
    public Collection<OtherDocumentMasterEntity> getOtherDocumentMaster() {
        return otherDocumentMasterRepository.findAll();
    }

    @Override
    public Collection<OtherDocumentMasterEntity> getOtherDocumentMaster(Long appId) {
        return otherDocumentMasterRepository.getDeferralReport(appId);
    }

    @Override
    public List<Long> saveOtherDocumentMaster(OtherDocumentMasterData otherDocumentMasterData) {
        List<Long> entityIdLst = new ArrayList<Long>();
        for (OtherDocumentData otherDocumentData : otherDocumentMasterData.getOtherDocumentDataList()) {
            entityIdLst.add(otherDocumentMasterRepository.save(transform(otherDocumentData, otherDocumentMasterData.getRmName())).getId());
        }
        return entityIdLst;
    }

    public OtherDocumentMasterEntity transform(OtherDocumentData otherDocumentData,String rmName) {
        OtherDocumentMasterEntity other = otherDocumentMasterRepository.getMasterWithName(otherDocumentData.getDisplayName().trim(),otherDocumentData.getAppId());
        if(other == null) {
            OtherDocumentMasterEntity otherDocumentMasterEntity = new OtherDocumentMasterEntity();
            int seqNo;
            OtherDocumentMasterEntity pre = otherDocumentMasterRepository.getSeqNo(otherDocumentData.getAppId());
            if (pre == null) {
                seqNo = 1;
            } else {
                seqNo = pre.getSequenceNo() + 1;
            }
            Date date = new Date();
            otherDocumentMasterEntity.setApplicationEntity(applicationRepository.findById(otherDocumentData.getAppId()).orElseThrow());
            otherDocumentMasterEntity.setDocumentListEntity(documentListRepository.findById(otherDocumentData.getDocListId()).orElseThrow());
            otherDocumentMasterEntity.setSequenceNo(seqNo);
            if(otherDocumentData.getStatus() == 0) {
                otherDocumentMasterEntity.setDeferralType(1);
            }else{
                otherDocumentMasterEntity.setDeferralType(0);
            }
            otherDocumentMasterEntity.setDisplayName(otherDocumentData.getDisplayName().trim());
            otherDocumentMasterEntity.setName((otherDocumentData.getDisplayName()).replaceAll("[^.,a-zA-Z0-9]+", ""));
            if(otherDocumentData.getInitialTime() != null){
                otherDocumentMasterEntity.setInitialTime(LocalDate.parse(otherDocumentData.getInitialTime()));
            }
            if(otherDocumentData.getRevisedTime() != null) {
                otherDocumentMasterEntity.setRevisedTime(LocalDate.parse(otherDocumentData.getRevisedTime()));
            }
            otherDocumentMasterEntity.setStatus(otherDocumentData.getStatus());
            otherDocumentMasterEntity.setRmName(rmName);
            otherDocumentMasterEntity.setCreatedBy(otherDocumentData.getCreatedBy());
            otherDocumentMasterEntity.setCreatedAt(new Timestamp(date.getTime()));

            return otherDocumentMasterEntity;
        }else{
            OtherDocumentMasterEntity otherDocumentMasterEntity = otherDocumentMasterRepository.findById(other.getId()).orElseThrow();
            Date date = new Date();
            otherDocumentMasterEntity.setApplicationEntity(applicationRepository.findById(otherDocumentData.getAppId()).orElseThrow());
            otherDocumentMasterEntity.setDocumentListEntity(documentListRepository.findById(otherDocumentData.getDocListId()).orElseThrow());
            otherDocumentMasterEntity.setSequenceNo(otherDocumentMasterEntity.getSequenceNo());
            if(otherDocumentData.getStatus() == 0) {
                otherDocumentMasterEntity.setDeferralType(1);
            }else{
                otherDocumentMasterEntity.setDeferralType(0);
            }
            otherDocumentMasterEntity.setDisplayName(otherDocumentData.getDisplayName().trim());
            otherDocumentMasterEntity.setName((otherDocumentData.getDisplayName()).replaceAll("[^.,a-zA-Z0-9]+", ""));
            if(otherDocumentData.getInitialTime() != null){
                otherDocumentMasterEntity.setInitialTime(LocalDate.parse(otherDocumentData.getInitialTime()));
            }
            if(otherDocumentData.getRevisedTime() != null) {
                otherDocumentMasterEntity.setRevisedTime(LocalDate.parse(otherDocumentData.getRevisedTime()));
            }
            otherDocumentMasterEntity.setStatus(otherDocumentData.getStatus());
            otherDocumentMasterEntity.setRmName(rmName);
            otherDocumentMasterEntity.setCreatedBy(otherDocumentData.getCreatedBy());
            otherDocumentMasterEntity.setCreatedAt(new Timestamp(date.getTime()));

            return otherDocumentMasterEntity;
        }
    }

    @Override
    public List<Long> updateOtherDocumentMaster(OtherDocumentMasterData otherDocumentMasterData) {
        List<Long> updateOtherArrayList = new ArrayList<>();
        for (OtherDocumentData otherDocumentData : otherDocumentMasterData.getOtherDocumentDataList()){
            JSONObject containerObject = new JSONObject(otherDocumentData);
            if(containerObject.has("id")){
                updateOtherArrayList.add(otherDocumentMasterRepository.save(updateTransforms(otherDocumentData, otherDocumentMasterData.getRmName())).getId());
            }else {
                updateOtherArrayList.add(otherDocumentMasterRepository.save(transform(otherDocumentData, otherDocumentMasterData.getRmName())).getId());
            }
        }
        return updateOtherArrayList;
    }

    private OtherDocumentMasterEntity updateTransforms(OtherDocumentData otherDocumentData, String rmName) {
        String result = changeS3FilePath(otherDocumentData);
        if(result.equals("success")){
            OtherDocumentMasterEntity otherDocumentMasterEntity = otherDocumentMasterRepository.findById(otherDocumentData.getId()).get();
            Date date = new Date();
            otherDocumentMasterEntity.setApplicationEntity(applicationRepository.findById(otherDocumentData.getAppId()).orElseThrow());
            otherDocumentMasterEntity.setDocumentListEntity(documentListRepository.findById(otherDocumentData.getDocListId()).orElseThrow());
            otherDocumentMasterEntity.setSequenceNo(otherDocumentMasterEntity.getSequenceNo());
            if(otherDocumentData.getStatus() == 0) {
                otherDocumentMasterEntity.setDeferralType(1);
            }else{
                otherDocumentMasterEntity.setDeferralType(0);
            }
            otherDocumentMasterEntity.setDisplayName(otherDocumentData.getDisplayName());
            otherDocumentMasterEntity.setName((otherDocumentData.getDisplayName()).replaceAll("[^.,a-zA-Z0-9]+", ""));
            if(otherDocumentData.getInitialTime() != null){
                otherDocumentMasterEntity.setInitialTime(LocalDate.parse(otherDocumentData.getInitialTime()));
            }
            if(otherDocumentData.getRevisedTime() != null) {
                otherDocumentMasterEntity.setRevisedTime(LocalDate.parse(otherDocumentData.getRevisedTime()));
            }
            otherDocumentMasterEntity.setStatus(otherDocumentData.getStatus());
            otherDocumentMasterEntity.setRmName(rmName);
            otherDocumentMasterEntity.setUpdatedBy(otherDocumentData.getUpdatedBy());
            otherDocumentMasterEntity.setUpdatedAt(new Timestamp(date.getTime()));

            return otherDocumentMasterEntity;
        }else {
            return otherDocumentMasterRepository.findById(otherDocumentData.getId()).get();
        }
    }

    public String changeS3FilePath(OtherDocumentData otherDocumentData){
        Optional<OtherDocumentMasterEntity> otherDocumentMasterEntity = otherDocumentMasterRepository.findById(otherDocumentData.getId());
        Collection<OtherDocumentReportsEntity> otherDocumentReportsEntities = otherDocumentReportsRepository.findCustomerDocReports(otherDocumentData.getAppId());
        for(OtherDocumentReportsEntity d : otherDocumentReportsEntities){
            if(d.getOtherDocumentMasterEntity().getId().equals(otherDocumentMasterEntity.get().getId())) {
                String oldFolderPath;
                String newFolderPath;
                Long appId = d.getApplicationEntity().getId();
                String docTypeNameOld = d.getDocumentTypeEntity().getName();
                String docCategoryNameOld = d.getDocumentCategoryEntity().getName();
                String docListNameOld = d.getDocumentListEntity().getName();
                String documentNameOld = d.getOtherDocumentMasterEntity().getName();
                String originalFilename = d.getFileName();

                oldFolderPath = appId + "/" + docCategoryNameOld + "/" + docListNameOld + "/" + documentNameOld + "/" + originalFilename;
                String docTypeNameNew = d.getDocumentTypeEntity().getName();
                String docCategoryNameNew = d.getDocumentCategoryEntity().getName();
                String docListNameNew = d.getDocumentListEntity().getName();
                String documentNameNew = otherDocumentData.getDisplayName().replaceAll("[^.,a-zA-Z0-9]+", "");

                newFolderPath = appId + "/" + docCategoryNameNew + "/" + docListNameNew + "/" + documentNameNew + "/" + originalFilename;
                if(!oldFolderPath.equals(newFolderPath)){
                    s3.copyObject(bucketName, oldFolderPath, bucketName, newFolderPath);

                    s3.deleteObject(bucketName, oldFolderPath);
                }
            }
        }
        return "success";
    }

    public List<Long> updateOtherDefDoc(OtherDocumentMasterData otherDocumentMasterData) {
        List<Long> updateOtherDocArrayList = new ArrayList<>();
        for (OtherDocumentData otherDocumentData : otherDocumentMasterData.getOtherDocumentDataList()) {
            updateOtherDocArrayList.add(otherDocumentMasterRepository.save(updateTransform(otherDocumentData, otherDocumentMasterData.getRmName())).getId());
        }
        return updateOtherDocArrayList;
    }

    public void updateOtherDeferralStatus(OtherDocumentMasterData otherDocumentMasterData) {
        for (OtherDocumentData otherDocumentData : otherDocumentMasterData.getOtherDocumentDataList()) {
            OtherDocumentMasterEntity otherDocumentMasterEntity = otherDocumentMasterRepository.findById(otherDocumentData.getId()).get();
            otherDocumentMasterEntity.setStatus(otherDocumentData.getStatus());
            if(otherDocumentMasterEntity.getRevisedTime()!=null){
                otherDocumentMasterEntity.setInitialTime(otherDocumentMasterEntity.getRevisedTime());
                otherDocumentMasterEntity.setRevisedTime(null);
                otherDocumentMasterEntity.setNewRevisedTime(null);
            }
            Date date = new Date();
            otherDocumentMasterEntity.setUpdatedAt(new Timestamp(date.getTime()));
            otherDocumentMasterRepository.save(otherDocumentMasterEntity);
        }
    }

    private OtherDocumentMasterEntity updateTransform(OtherDocumentData otherDocumentData, String rmName) {
        OtherDocumentMasterEntity otherDocumentMasterEntity = otherDocumentMasterRepository.findById(otherDocumentData.getId()).get();

        otherDocumentMasterEntity.setApplicationEntity(applicationRepository.findById(otherDocumentData.getAppId()).orElseThrow());
        otherDocumentMasterEntity.setDocumentListEntity(documentListRepository.findById(otherDocumentData.getDocListId()).orElseThrow());
        otherDocumentMasterEntity.setRmName(rmName);
        otherDocumentMasterEntity.setStatus(otherDocumentData.getStatus());
        otherDocumentMasterEntity.setDisplayName(otherDocumentData.getDisplayName());
        Date date = new Date();
        otherDocumentMasterEntity.setUpdatedAt(new Timestamp(date.getTime()));
        if(otherDocumentData.getExRevisedTime()!=null){
            otherDocumentMasterEntity.setNewRevisedTime(otherDocumentData.getExRevisedTime());
        }
        return otherDocumentMasterEntity;
    }

    public Collection<OtherDocumentMasterEntity> getOtherDeferralDocuments(Long appId,int status) {
        if(status==1){
            return otherDocumentMasterRepository.getOtherDeferralDocumentOpsMaker(appId);
        }
        else {
            return otherDocumentMasterRepository.getOtherDeferralDocument(appId, status);
        }
    }

    @Override
    public void updateOtherDocsStatus(Long appId) {
        Collection<OtherDocumentMasterEntity> otherDocsEntityList = otherDocumentMasterRepository.getDeferralReport(appId);
        Collection<OtherDocumentReportsEntity> otherDocsReportList = otherDocumentReportsRepository.findCustomerDocReports(appId);
        for(OtherDocumentMasterEntity otherDocumentMasterEntity: otherDocsEntityList){
            for(OtherDocumentReportsEntity otherDocumentReportsEntity: otherDocsReportList){
                if(otherDocumentMasterEntity.getId().equals(otherDocumentReportsEntity.getOtherDocumentMasterEntity().getId())){
                    otherDocumentMasterEntity.setStatus(2);
                }
            }
            if(otherDocumentMasterEntity.getNewRevisedTime()!=null){
                otherDocumentMasterEntity.setRevisedTime(otherDocumentMasterEntity.getNewRevisedTime());
            }
            Date date = new Date();
            otherDocumentMasterEntity.setUpdatedAt(new Timestamp(date.getTime()));
            otherDocumentMasterRepository.save(otherDocumentMasterEntity);
        }
    }

    public List<OtherDocumentMasterEntity> findPendingOtherDoc(Long appId,Long status){
        return otherDocumentMasterRepository.findPendingOtherDeferralDoc(appId,status);
    }

    @Override
    public String deleteOtherDocRecord(Long appId, Long id){
        if (!otherDocumentMasterRepository.existsById(id)) {
            return "documentUnavailable";
        }else{
            try {
                Collection<OtherDocumentReportsEntity> otherDocumentReportsEntities = otherDocumentReportsRepository.findDocReportsWithMasterId(id);
                for (OtherDocumentReportsEntity d : otherDocumentReportsEntities) {
//                    Long appId = d.getApplicationEntity().getId();
                    Long docTypeId = d.getDocumentTypeEntity().getId();
                    Long docCategoryId = d.getDocumentCategoryEntity().getId();
                    Long docListId = d.getDocumentListEntity().getId();
                    String fileName = d.getFileName();
                    OtherDocumentReportsEntity document = otherDocumentReportsRepository.getDocumentDetails(appId, docTypeId, docCategoryId, docListId, id, fileName);
                    if (document != null) {
                        String docTypeName = document.getDocumentCategoryEntity().getName();
                        String docListName = document.getDocumentListEntity().getName();
                        String docName = document.getOtherDocumentMasterEntity().getName();
                        String fileName1 = document.getFileName();
                        String filePath = appId + "/" + docTypeName + "/" + docListName + "/" + docName + "/" + fileName1;
                        otherDocumentReportsRepository.deleteDocDetails(appId, docTypeId, docCategoryId, docListId, id, fileName1);
                        s3.deleteObject(bucketName, filePath);
                    }
                }
                otherDocumentMasterRepository.deleteMasterById(id);
                return "success";
            }catch (Exception e){
                e.printStackTrace();
                return "Fail";
            }
        }
    }

    @Override
    public Boolean workflowDecisionDefOpsChecker(Long appId){
        Collection<OtherDocumentMasterEntity> otherDocsEntityList = otherDocumentMasterRepository.getOtherDeferralDocument(appId,0);
        Collection<DeferralReportsEntity> deferralEntityList = deferralReportsService.getDeferralReport(appId,0);
        return !otherDocsEntityList.isEmpty() || !deferralEntityList.isEmpty();
    }

    @Override
    public Collection<OtherDocumentMasterEntity> getOtherDocsForOpsChecker(Long appId){
        return otherDocumentMasterRepository.getOtherDocsForOpsChecker(appId);
    }
}
