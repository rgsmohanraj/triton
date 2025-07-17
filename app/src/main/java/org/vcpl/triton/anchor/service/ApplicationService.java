package org.vcpl.triton.anchor.service;

import org.joda.time.LocalDateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.data.ApplicationData;
import org.vcpl.triton.anchor.entity.AnchorGstEntity;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;
import org.vcpl.triton.counterParty.repository.CPBasicDetailsRepository;
import org.vcpl.triton.workflow.entity.WFApprovalStatusEntity;
import org.vcpl.triton.workflow.repository.WFApprovalStatusRepository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class ApplicationService implements IApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private CPBasicDetailsRepository cpBasicDetailsRepository;

    @Autowired
    private WFApprovalStatusRepository wfApprovalStatusRepository;


    @Override
    public List<ApplicationEntity> getAllApplicationDetails() {
        logger.info(" | URL | getAllApplicationDetails | OPERATION | " + "GET Application");

        List<ApplicationEntity> applicationEntities = this.applicationRepository.findAll();
        return applicationEntities;
    }

    @Override
    public List<ApplicationEntity> findByAnchor() {
        logger.info(" | URL | getAllApplicationDetails | OPERATION | " + "GET Application");
        List<ApplicationEntity> applicationEntities = this.applicationRepository.findByAnchor();
        return applicationEntities;
    }

    @Override
    public List<ApplicationEntity> findByCp() {
        logger.info(" | URL | getAllApplicationDetails | OPERATION | " + "GET Application");
        List<ApplicationEntity> applicationEntities = this.applicationRepository.findByCp();
        return applicationEntities;
    }


    @Override
    public ApplicationEntity getApplicationDetailsById(long id) {
        logger.info(" | URL | getApplicationDetailsById | OPERATION | " + "GETById Application");
            return applicationRepository.findById(id).orElse(null);

    }

    @Override
    public ApplicationEntity saveApplicationDetails(ApplicationData applicationData) {
        logger.info(" | URL | saveApplicationDetails | OPERATION | " + "Save Application");
        ApplicationEntity applicationEntity = applicationRepository.save(transform(applicationData));
            return applicationEntity;
    }

    private ApplicationEntity transform(ApplicationData applicationData) {

        ApplicationEntity applicationEntity = new ApplicationEntity();

        applicationEntity.setType(applicationData.getType());
        applicationEntity.setAppType(applicationData.getAppType());
        applicationEntity.setCreatedAt(LocalDate.now());
        applicationEntity.setCreatedBy(applicationData.getCreatedBy());
        applicationEntity.setUpdatedAt(LocalDate.now());
        applicationEntity.setUpdatedBy(applicationData.getUpdatedBy());
        applicationEntity.setCustomerInfoEntity(customerInfoRepository.findById(applicationData.getCustId()).orElseThrow());

        ApplicationEntity applicationEntities = applicationRepository.findByCustId(applicationData.getCustId());
        applicationEntity.setSeqNo(applicationEntities.getSeqNo()+1);
        if(applicationEntities == null){
            applicationEntity.setWfType(0);
            return applicationEntity;
        }else {
            ApplicationEntity application = applicationRepository.findByAppTypeCustId(applicationData.getCustId(),applicationData.getAppType());
            if(application == null){
                applicationEntity.setWfType(1);
            }else{
                applicationEntity.setWfType(application.getWfType()+1);
            }
        }

        CustomerInfoEntity customerInfoEntity = customerInfoRepository.findById(applicationData.getCustId()).get();
        customerInfoEntity.setStatus(false);
        customerInfoRepository.save(customerInfoEntity);

        return applicationEntity;
    }

    @Override
    public ApplicationEntity updateApplicationDetails(ApplicationData applicationData, long id) {
        ApplicationEntity applicationEntity = applicationRepository.save(updateTransform(applicationData, id));
        return applicationEntity;
    }

    private ApplicationEntity updateTransform(ApplicationData applicationData, long id) {
        ApplicationEntity applicationEntity = applicationRepository.findById(id).get();
        Date date = new Date();
        applicationEntity.setType(applicationData.getType());
        applicationEntity.setAppType(applicationData.getAppType());
//        applicationEntity.setCreatedBy(applicationData.getCreatedBy());
//        applicationEntity.setUpdatedBy(applicationData.getUpdatedBy());
//        applicationEntity.setCreatedAt(new Timestamp(date.getTime()));
//        applicationEntity.setUpdatedAt(new Timestamp(date.getTime()));
//        applicationEntity.setCustomerInfoEntity(customerInfoRepository.getById(applicationData.getCustId()));
        return applicationEntity;
    }

    @Override
    public String getApplicationIds(Long custId) {
        JSONObject json=new JSONObject();
        JSONArray array = new JSONArray();
        Integer seqNo = applicationRepository.findMaxSeqNo(custId);
        JSONObject json1 = new JSONObject();
        if(seqNo>1) {
            List<ApplicationEntity> applicationEntityList = applicationRepository.findApplicationIds(custId);
            if (applicationEntityList.get(0).getSeqNo() > applicationEntityList.get(1).getSeqNo()) {
                JSONObject json2 = new JSONObject();
                JSONArray array1 = new JSONArray();
                json2.put("seqNo", applicationEntityList.get(0).getSeqNo());
                json2.put("appId", applicationEntityList.get(0).getId());
                json2.put("type", applicationEntityList.get(0).getType());
                json2.put("appType", applicationEntityList.get(0).getAppType());
                json2.put("wfType", applicationEntityList.get(0).getWfType());
                array1.put(json2);
                json1.put("newAppId", array1);

                JSONObject json3 = new JSONObject();
                JSONArray array2 = new JSONArray();
                json3.put("seqNo", applicationEntityList.get(1).getSeqNo());
                json3.put("appId", applicationEntityList.get(1).getId());
                json3.put("type", applicationEntityList.get(1).getType());
                json3.put("appType", applicationEntityList.get(1).getAppType());
                json3.put("wfType", applicationEntityList.get(1).getWfType());
                array2.put(json3);
                json1.put("oldAppId", array2);
            } else if (applicationEntityList.get(0).getSeqNo() < applicationEntityList.get(1).getSeqNo()) {
                JSONObject json2 = new JSONObject();
                JSONArray array1 = new JSONArray();
                json2.put("seqNo", applicationEntityList.get(0).getSeqNo());
                json2.put("appId", applicationEntityList.get(0).getId());
                json2.put("type", applicationEntityList.get(0).getType());
                json2.put("appType", applicationEntityList.get(0).getAppType());
                json2.put("wfType", applicationEntityList.get(0).getWfType());
                array1.put(json2);
                json1.put("oldAppId", array1);

                JSONObject json3 = new JSONObject();
                JSONArray array2 = new JSONArray();
                json3.put("seqNo", applicationEntityList.get(1).getSeqNo());
                json3.put("appId", applicationEntityList.get(1).getId());
                json3.put("type", applicationEntityList.get(1).getType());
                json3.put("appType", applicationEntityList.get(1).getAppType());
                json3.put("wfType", applicationEntityList.get(1).getWfType());
                array2.put(json3);
                json1.put("newAppId", array2);
            }
        }
        else{
            ApplicationEntity applicationEntity = applicationRepository.findByCustId(custId);
            JSONObject json4 = new JSONObject();
            JSONArray array3 = new JSONArray();
            JSONArray array4 = new JSONArray();
            json4.put("seqNo", applicationEntity.getSeqNo());
            json4.put("appId", applicationEntity.getId());
            json4.put("type", applicationEntity.getType());
            json4.put("appType", applicationEntity.getAppType());
            json4.put("wfType", applicationEntity.getWfType());
            array3.put(json4);
            json1.put("oldAppId", array3);
            json1.put("newAppId", array4);
        }
        array.put(json1);
        json.put("appList", array);
        return json.toString();
    }

    @Override
    public String findAllApplicationIds(Long custId) throws ParseException {
        JSONObject json1=new JSONObject();
        JSONArray array1 = new JSONArray();
        List<ApplicationEntity> applicationEntityList = applicationRepository.findAllApplicationIds(custId);
        if(!applicationEntityList.isEmpty() && applicationEntityList!=null) {
            for (ApplicationEntity applicationEntity : applicationEntityList) {
                JSONObject json2=new JSONObject();
                json2.put("seqNo", applicationEntity.getSeqNo());
                json2.put("appId", applicationEntity.getId());
                json2.put("type", applicationEntity.getType());
                json2.put("appType", applicationEntity.getAppType());
                json2.put("wfType", applicationEntity.getWfType());
                json2.put("customer", applicationEntity.getCustomerInfoEntity().getCustomerName());
                json2.put("custId", applicationEntity.getCustomerInfoEntity().getId());
                WFApprovalStatusEntity wfApprovalStatusEntity = wfApprovalStatusRepository.findByApplicationId(applicationEntity.getId());
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = inputFormat.parse(wfApprovalStatusEntity.getUpdatedTime());
                String formattedDate = outputFormat.format(date);
                if(wfApprovalStatusEntity.getStatus()==-2){
                    json2.put("defDate", "");
                    json2.put("appDate", "");
                    json2.put("status", "Rejected");
                }
                else if((wfApprovalStatusEntity.getStage().getId()==27 || wfApprovalStatusEntity.getStage().getId()==28) && wfApprovalStatusEntity.getStatus()==2){
                    json2.put("appDate", "");
                    json2.put("defDate", formattedDate);
                    json2.put("status", "Completed");
                }
                else if((wfApprovalStatusEntity.getStage().getId()==26 || wfApprovalStatusEntity.getStage().getId()==21) && wfApprovalStatusEntity.getStatus()==2){
                    json2.put("defDate", "");
                    json2.put("appDate", formattedDate);
                    json2.put("status", "Completed");
                }
                else{
                    json2.put("defDate", "");
                    json2.put("appDate", "");
                    json2.put("status", "In Progress");
                }
                array1.put(json2);
            }
            json1.put("appDetails",array1);
        }
        return json1.toString();
    }

    @Override
    public String deleteApplicationDetails(long id) {
        applicationRepository.deleteById(id);
        return id + "Removed";
    }

    @Override
    public Collection<ApplicationEntity> getApplicationDetails(long id) {
        logger.info(" | URL | getApplicationDetails | OPERATION | " + "GET Application");
        return applicationRepository.findByAppId(id);
    }

}
