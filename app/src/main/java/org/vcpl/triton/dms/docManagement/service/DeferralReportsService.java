package org.vcpl.triton.dms.docManagement.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.dms.dmsMaster.repository.DocumentListRepository;
import org.vcpl.triton.dms.docManagement.data.DeferralReportsData;
import org.vcpl.triton.dms.docManagement.data.DeferralReportsMasterData;
import org.vcpl.triton.dms.docManagement.entity.DeferralReportsEntity;
import org.vcpl.triton.dms.docManagement.entity.DocumentReportsEntity;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentMasterEntity;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentReportsEntity;
import org.vcpl.triton.dms.docManagement.repository.DeferralReportsRepository;
import org.vcpl.triton.dms.docManagement.repository.DocumentReportsRepository;
import org.vcpl.triton.dms.docManagement.repository.OtherDocumentMasterRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class DeferralReportsService implements IDeferralReports{

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    DocumentListRepository documentListRepository;

    @Autowired
    DeferralReportsRepository deferralReportsRepository;

    @Autowired
    OtherDocumentMasterRepository otherDocumentMasterRepository;

    @Autowired
    DocumentReportsRepository documentReportsRepository;

    private static final Logger logger = LoggerFactory.getLogger(DeferralReportsService.class);

    @Override
    public Collection<DeferralReportsEntity> getDeferralReport(Long appId) {
        return deferralReportsRepository.getDeferralReport(appId);
    }

    @Override
    public String getDeferralReports(Long appId) {
        JSONObject json=new JSONObject();
        JSONArray array = new JSONArray();
        Collection<DeferralReportsEntity> report = deferralReportsRepository.getDeferralReport(appId);
        Collection<OtherDocumentMasterEntity> otherReport = otherDocumentMasterRepository.getDeferralReports(appId);
        if(report!=null && report.size()>0 || otherReport!=null && otherReport.size()>0){
            for(DeferralReportsEntity d : report){
                JSONObject json1=new JSONObject();
                json1.put("id",d.getId());
                json1.put("appId",d.getApplicationEntity().getId());
                json1.put("customerName",d.getApplicationEntity().getCustomerInfoEntity().getCustomerName());

//                json1.put("customerType",d.getDocumentTypeEntity().getType());
                json1.put("docListId",d.getDocumentListEntity().getId());
                json1.put("docListType",d.getDocumentListEntity().getCategory());
                json1.put("docList",d.getDocumentListEntity().getDisplayName());
                json1.put("docListName",d.getDocumentListEntity().getName());
                json1.put("deferral",d.getDocumentListEntity().getDeferral());
                json1.put("defType",d.getDocumentListEntity().getType());
                json1.put("defTime",d.getDocumentListEntity().getDeferralTime());
                json1.put("mandatory",d.getDocumentListEntity().getMandatory());

                json1.put("initialTime",d.getInitialTime());
                json1.put("revisedTime",d.getRevisedTime());
                json1.put("newRevisedTime",d.getNewRevisedTime());
                json1.put("status",d.getStatus());

                if(d.getStatus() == 0){
                    json1.put("message","In-Progress");
                }else if(d.getStatus() == 2){
                    json1.put("message","Completed");
                }else if(d.getStatus() == 3){
                    json1.put("message","Waived-Off");
                }else if(d.getStatus() == 1){
                    LocalDate date = LocalDate.now();
                    if(d.getRevisedTime() != null){
                        if(date.isBefore(d.getRevisedTime())){
                            json1.put("message","Approved");
                        }else{
                            json1.put("message","Overdue");
                        }
                    }else{
                        if(date.isBefore(d.getInitialTime())){
                            json1.put("message","Approved");
                        }else{
                            json1.put("message","Overdue");
                        }
                    }
                }

                json1.put("documentId",d.getDocumentId());
                json1.put("docCompletionDate",d.getDocCompletionDate());
                json1.put("rmName",d.getRmName());

                if(d.getUpdatedAt() != null) {
                    json1.put("processedDate",d.getUpdatedAt());
                }else {
                    json1.put("processedDate",d.getCreatedAt());
                }

                array.put(json1);
            }
            for(OtherDocumentMasterEntity d : otherReport){
                JSONObject json1=new JSONObject();
                json1.put("id",d.getId());
                json1.put("appId",d.getApplicationEntity().getId());
                json1.put("customerName",d.getApplicationEntity().getCustomerInfoEntity().getCustomerName());

//                json1.put("customerType",d.getDocumentTypeEntity().getType());
                json1.put("docListId",d.getDocumentListEntity().getId());
                json1.put("docListType",d.getDocumentListEntity().getCategory());
                json1.put("docList",d.getDocumentListEntity().getDisplayName());
                json1.put("docListName",d.getDocumentListEntity().getName());
                json1.put("deferral",d.getDocumentListEntity().getDeferral());
                json1.put("defType",d.getDocumentListEntity().getType());
                json1.put("defTime",d.getDocumentListEntity().getDeferralTime());
                json1.put("mandatory",d.getDocumentListEntity().getMandatory());

                json1.put("sequenceNo",d.getDocumentListEntity().getId());
                json1.put("deferralType",d.getDeferralType());
                json1.put("displayName",d.getDisplayName());
                json1.put("name",d.getName());

                json1.put("initialTime",d.getInitialTime());
                json1.put("revisedTime",d.getRevisedTime());
                json1.put("newRevisedTime",d.getNewRevisedTime());
                json1.put("status",d.getStatus());

                if(d.getStatus() == 0){
                    json1.put("message","In-Progress");
                }else if(d.getStatus() == 2){
                    json1.put("message","Completed");
                }else if(d.getStatus() == 3){
                    json1.put("message","Waived-Off");
                }else if(d.getStatus() == 1){
                    LocalDate date = LocalDate.now();
                    if(d.getRevisedTime() != null){
                        if(date.isBefore(d.getRevisedTime())){
                            json1.put("message","Approved");
                        }else{
                            json1.put("message","Overdue");
                        }
                    }else{
                        if(date.isBefore(d.getInitialTime())){
                            json1.put("message","Approved");
                        }else{
                            json1.put("message","Overdue");
                        }
                    }
                }

                json1.put("rmName",d.getRmName());

                if(d.getUpdatedAt() != null) {
                    json1.put("processedDate",d.getUpdatedAt());
                }else {
                    json1.put("processedDate",d.getCreatedAt());
                }

                array.put(json1);
            }
        }
        json.put("deferralReports",array);
        return json.toString();
    }

    @Override
    public Collection<DeferralReportsEntity> getDeferralReport(Long appId, int status) {
        if(status==1){
            return deferralReportsRepository.getDeferralReportsOpsMaker(appId);
        }
        else {
            return deferralReportsRepository.getDeferralReport(appId, status);
        }
    }

    @Override
    public List<Long> saveDeferralReports(DeferralReportsMasterData deferralReportsMasterData) {
        List<Long> entityIdLst = new ArrayList<Long>();
        for (DeferralReportsData deferralReportsData : deferralReportsMasterData.getDeferralReportsDataList()) {
            entityIdLst.add(deferralReportsRepository.save(transform(deferralReportsData, deferralReportsMasterData.getRmName())).getId());
        }
        return entityIdLst;
    }

    private DeferralReportsEntity transform(DeferralReportsData deferralReportsData,String rmName) {
        DeferralReportsEntity deferralReportsEntity = new DeferralReportsEntity();
        Date date = new Date();
        if(deferralReportsData.getStatus() != -1) {
            deferralReportsEntity.setApplicationEntity(applicationRepository.findById(deferralReportsData.getAppId()).orElseThrow());
            deferralReportsEntity.setDocumentListEntity(documentListRepository.findById(deferralReportsData.getDocListId()).orElseThrow());
//        deferralReportsEntity.setDocumentName(deferralReportsData.getDocumentName());
            deferralReportsEntity.setInitialTime(deferralReportsData.getInitialTime());
            deferralReportsEntity.setRevisedTime(deferralReportsData.getRevisedTime());
            deferralReportsEntity.setStatus(deferralReportsData.getStatus());
            deferralReportsEntity.setDocumentId(deferralReportsData.getDocumentId());
            deferralReportsEntity.setDocCompletionDate(deferralReportsData.getDocCompletionDate());
            if (deferralReportsData.getStatus() == 0) {
                deferralReportsEntity.setRmName(rmName);
            }
            deferralReportsEntity.setCreatedAt(new Timestamp(date.getTime()));
        }
        return  deferralReportsEntity;
    }

    public List<DeferralReportsEntity> getCompeletedDeferralDataByAppIdAndStatus(Long appId) {
        return deferralReportsRepository.getCompeletedDeferralDataByAppIdAndStatus(appId);
    }

    @Override
    public List<Long> updateDeferralDoc(DeferralReportsMasterData deferralReportsMasterData){
        List<Long> updateDefArrayList = new ArrayList<>();
        for (DeferralReportsData deferralReportsData : deferralReportsMasterData.getDeferralReportsDataList()){
            JSONObject containerObject = new JSONObject(deferralReportsData);
            if(containerObject.has("id")){
                updateDefArrayList.add(deferralReportsRepository.save(updateTransforms(deferralReportsData, deferralReportsMasterData.getRmName())).getId());
            }else {
                updateDefArrayList.add(deferralReportsRepository.save(transform(deferralReportsData, deferralReportsMasterData.getRmName())).getId());
            }
        }
        return updateDefArrayList;
    }

    private DeferralReportsEntity updateTransforms(DeferralReportsData deferralReportsData,String rmName){
        DeferralReportsEntity deferralReportsEntity = deferralReportsRepository.findById(deferralReportsData.getId()).get();

        Date date = new Date();
        if(deferralReportsData.getStatus() != -1) {
            deferralReportsEntity.setApplicationEntity(applicationRepository.findById(deferralReportsData.getAppId()).orElseThrow());
            deferralReportsEntity.setDocumentListEntity(documentListRepository.findById(deferralReportsData.getDocListId()).orElseThrow());
//        deferralReportsEntity.setDocumentName(deferralReportsData.getDocumentName());
            deferralReportsEntity.setInitialTime(deferralReportsData.getInitialTime());
            deferralReportsEntity.setRevisedTime(deferralReportsData.getRevisedTime());
            deferralReportsEntity.setStatus(deferralReportsData.getStatus());
            deferralReportsEntity.setDocumentId(deferralReportsData.getDocumentId());
            deferralReportsEntity.setDocCompletionDate(deferralReportsData.getDocCompletionDate());
            deferralReportsEntity.setRmName(rmName);
            deferralReportsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        }else if(deferralReportsData.getStatus() == -1){
            //If save under deferral try to set not deferral
            deferralReportsRepository.deletedeferralById(deferralReportsData.getId());
        }
        return deferralReportsEntity;
    }

    @Override
    public List<Long> updateDeferral(DeferralReportsMasterData deferralReportsMasterData){
        List<Long> updateDefArrayList = new ArrayList<>();
        for (DeferralReportsData deferralReportsData : deferralReportsMasterData.getDeferralReportsDataList()){
            updateDefArrayList.add(deferralReportsRepository.save(updateTransform(deferralReportsData, deferralReportsMasterData.getRmName())).getId());
        }
        return updateDefArrayList;
    }

    @Override
    public List<Long> saveNewDeferralDate(DeferralReportsMasterData deferralReportsMasterData){
        List<Long> updateDefArrayList = new ArrayList<>();
        for (DeferralReportsData deferralReportsData : deferralReportsMasterData.getDeferralReportsDataList()){
            updateDefArrayList.add(deferralReportsRepository.save(updateApprovedDate(deferralReportsData, deferralReportsMasterData.getRmName())).getId());
        }
        return updateDefArrayList;
    }

    private DeferralReportsEntity updateApprovedDate(DeferralReportsData deferralReportsData,String rmName){
        DeferralReportsEntity deferralReportsEntity = deferralReportsRepository.findById(deferralReportsData.getId()).get();

        deferralReportsEntity.setApplicationEntity(applicationRepository.findById(deferralReportsData.getAppId()).orElseThrow());
        deferralReportsEntity.setDocumentListEntity(documentListRepository.findById(deferralReportsData.getDocListId()).orElseThrow());
        deferralReportsEntity.setStatus(deferralReportsData.getStatus());
        if(deferralReportsData.getStatus()==1){
            if(deferralReportsEntity.getRevisedTime()!=null){
                deferralReportsEntity.setInitialTime(deferralReportsEntity.getRevisedTime());
                deferralReportsEntity.setRevisedTime(null);
                deferralReportsEntity.setNewRevisedTime(null);
            }
        }
        deferralReportsEntity.setRmName(rmName);
        Date date = new Date();
        deferralReportsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        return deferralReportsEntity;
    }

    private DeferralReportsEntity updateTransform(DeferralReportsData deferralReportsData,String rmName){
        DeferralReportsEntity deferralReportsEntity = deferralReportsRepository.findById(deferralReportsData.getId()).get();

        deferralReportsEntity.setApplicationEntity(applicationRepository.findById(deferralReportsData.getAppId()).orElseThrow());
        deferralReportsEntity.setDocumentListEntity(documentListRepository.findById(deferralReportsData.getDocListId()).orElseThrow());
//        if( deferralReportsData.getStatus() == 2 && deferralReportsData.getDocCompletionDate() != null && deferralReportsData.getDocumentId() != null
//                && (!deferralReportsData.getDocumentId().isEmpty())) {
//            deferralReportsEntity.setStatus(deferralReportsEntity.getStatus());
//        }else{
//            deferralReportsEntity.setStatus(deferralReportsData.getStatus());
//        }
        //deferralReportsEntity.setInitialTime(deferralReportsData.getInitialTime());
        if(deferralReportsData.getStatus() == 0){
            deferralReportsEntity.setStatus(deferralReportsData.getStatus());
        }
        if(deferralReportsData.getExRevisedTime()!=null) {
            deferralReportsEntity.setNewRevisedTime(deferralReportsData.getExRevisedTime());
        }
        deferralReportsEntity.setDocCompletionDate(deferralReportsData.getDocCompletionDate());
        deferralReportsEntity.setDocumentId(deferralReportsData.getDocumentId());
        deferralReportsEntity.setRmName(rmName);
        Date date = new Date();
        deferralReportsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        return deferralReportsEntity;
    }

    @Override
    public void updateDeferralStatus(Long appId){
        Collection<DeferralReportsEntity> deferralReportsEntities = deferralReportsRepository.getDeferralReport(appId);
        List<DocumentReportsEntity> documentReportsEntities = documentReportsRepository.findCustomerDocReports(appId);
        for (DeferralReportsEntity deferral : deferralReportsEntities) {
            if (deferral.getDocumentListEntity().getDeferral() == 1 && deferral.getDocumentListEntity().getType() == 0) {
                for (DocumentReportsEntity documentReports : documentReportsEntities) {
                    if (deferral.getDocumentListEntity().getId().equals(documentReports.getDocumentListEntity().getId())) {
                        deferral.setStatus(2);
                       // deferralReportsRepository.updateStatus(deferral.getId());
                    }
                }
            } else if (deferral.getDocumentListEntity().getDeferral() == 2 && deferral.getDocumentListEntity().getType() == 0) {
                if (deferral.getDocumentId() != null && !((deferral.getDocumentId()).replaceAll("\\s", "") == "")) {
                   // deferralReportsRepository.updateStatus(deferral.getId());
                    deferral.setStatus(2);
                }
            }
            if(deferral.getNewRevisedTime()!=null){
                deferral.setRevisedTime(deferral.getNewRevisedTime());
            }
            deferralReportsRepository.save(deferral);
        }
    }

    @Override
    public Collection<DeferralReportsEntity> getDeferralDocuments(Integer type, Integer status) {
//      deferralalldoccount =  deferralReportsRepository.getDeferralDocCount(type);
        JSONArray array = new JSONArray();
        // Type (1-Anchor) (2-CounterParty)
        // Status - (2-Approved)
        try {
            Collection<DeferralReportsEntity> deferralList = deferralReportsRepository.getDeferralDocs(type, status);
//        if (list.size() > 0) {
//            for (Object[] obj : list) {
//                count++;
//                JSONObject obj1 = new JSONObject();
//                obj1.put("Type", obj[0]);
//                array.put(obj1);
//            }
//            return count;
//        }
            return deferralList;
        }
        catch (Exception ex) {
            logger.error(" | URL |  findExistingAnchor | OPERATION | " + " Error |" + ex.getMessage());
        }
        return null;
    }

    @Override
    public Collection<DeferralReportsEntity> getDeferralDetails(Integer type){
        try {
            Collection<DeferralReportsEntity> deferralList = deferralReportsRepository.getDeferralDetails(type);
            return deferralList;
        }
        catch (Exception ex) {
            logger.error(" | URL |  findExistingAnchor | OPERATION | " + " Error |" + ex.getMessage());
        }
        return null;
    }

    public List<DeferralReportsEntity> getDeferralPendingReport(Long appId,Integer status){
        try{
            return deferralReportsRepository.getDeferralPendingReport(appId,status);
       }catch (Exception e){
            logger.error("Error Occurred in getDeferralPendingReport {}",appId+" "+ status);
            e.printStackTrace();
        }
        return null;
    }
}
