package org.vcpl.triton.anchor.service;


import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.data.CustomerInfoData;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.entity.ProgramNormsEntity;
import org.vcpl.triton.anchor.page.CustomerInfoDetailsPage;
import org.vcpl.triton.anchor.page.CustomerInfoDetailsSearchCriteria;
import org.vcpl.triton.anchor.repository.*;
import org.vcpl.triton.counterParty.data.CreditPolicyDetailsData;
import org.vcpl.triton.counterParty.entity.CreditPolicyFilters;
import org.vcpl.triton.counterParty.entity.TermSheetEntity;
import org.vcpl.triton.counterParty.repository.TermSheetRepository;
import org.vcpl.triton.dms.docManagement.entity.DeferralReportsEntity;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentMasterEntity;
import org.vcpl.triton.dms.docManagement.service.DeferralReportsService;
import org.vcpl.triton.dms.docManagement.service.OtherDocumentMasterService;
import org.vcpl.triton.validation.ResponseControllerService;
import org.vcpl.triton.workflow.entity.WFApprovalStatusEntity;
import org.vcpl.triton.workflow.repository.WFApprovalStatusRepository;
import org.vcpl.triton.workflow.repository.WorkflowStageRepository;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CustomerInfoService implements ICustomerInfo {
    private static final Logger logger = LoggerFactory.getLogger(CustomerInfoService.class);

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private AnchorGstRepository anchorGstDetailsRepository;

    @Autowired
    private AnchorAddressRepository anchorAddressDetailsRepository;

    @Autowired
    private CustomerInfoDetailsCriteriaRepository customerInfoDetailsCriteriaRepository;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private WFApprovalStatusRepository wfApprovalStatusRepository;

    @Autowired
    private TermSheetRepository termSheetRepository;

    @Autowired
    DeferralReportsService deferralReportsService;

    @Autowired
    OtherDocumentMasterService otherDocumentMasterService;

    @Autowired
    ProgramNormsRepository programNormsRepository;


    @Override
    public List<CustomerInfoEntity> getAllProduct() {
        logger.info(" | URL | getAllProduct | OPERATION | " + "GET CustomerInfo");
        return this.customerInfoRepository.findAll();
    }

    @Override
    public Page<CustomerInfoEntity> getCustomer(CustomerInfoDetailsPage customerInfoDetailsPage,
                                                CustomerInfoDetailsSearchCriteria customerInfoDetailsSearchCriteria) {
        return customerInfoDetailsCriteriaRepository.findAllFilter(customerInfoDetailsPage, customerInfoDetailsSearchCriteria);
    }

    @Override
    public CustomerInfoEntity saveCustomerInfo(CustomerInfoData customerInfoData) {
        CustomerInfoEntity customerInfoFileEntity = customerInfoRepository.save(transform(customerInfoData));
        return customerInfoFileEntity;
    }

    private CustomerInfoEntity transform(CustomerInfoData customerInfoData) {
        CustomerInfoEntity customerInfoEntity = new CustomerInfoEntity();
        Date date = new Date();
        customerInfoEntity.setCustomerName(customerInfoData.getCustomerName());
        customerInfoEntity.setProduct(customerInfoData.getProduct());
        customerInfoEntity.setPan(customerInfoData.getPan());
        customerInfoEntity.setCin(customerInfoData.getCin());

//        customerInfoEntity.setApplicationEntity(applicationRepository.getById(customerInfoData.getAppId()));
        return customerInfoEntity;
    }

    @Override
    public String searchCustomerInfo(String query,Integer type,String stage,String status) {
        logger.info(" | URL | searchCustomerInfo | OPERATION | "+"GET searchCustomerInfo");
            if(status.equals("2")){
                List<Object[]> customerInfoEntities = customerInfoRepository.searchCustomerInfoSQL(type,stage,query);
                JSONObject json = new JSONObject();
                if (customerInfoEntities != null) {
                    JSONArray array = new JSONArray();
                    for (int i = 0; i < customerInfoEntities.size(); i++) {
                        JSONObject json1 = new JSONObject();
                        json1.put("id", customerInfoEntities.get(i)[0]);
                        json1.put("cin", customerInfoEntities.get(i)[1]);
                        json1.put("customerName", customerInfoEntities.get(i)[2]);
                        json1.put("pan", customerInfoEntities.get(i)[3]);
                        json1.put("product", customerInfoEntities.get(i)[4]);
                        json1.put("status", customerInfoEntities.get(i)[5]);
                        json1.put("appId", customerInfoEntities.get(i)[6]);

                        array.put(json1);
                    }
                    json.put("SearchResult", array);
                }
                return json.toString();
            } else if (status.equals("0") || status.equals("-2")) {
                List<Object[]> customerInfoEntities = customerInfoRepository.searchCustomerInfoInProgressAndRejectSQL(type,status,query);
                JSONObject json = new JSONObject();
                if (customerInfoEntities != null) {
                    JSONArray array = new JSONArray();
                    for (int i = 0; i < customerInfoEntities.size(); i++) {
                        JSONObject json1 = new JSONObject();
                        json1.put("id", customerInfoEntities.get(i)[0]);
                        json1.put("cin", customerInfoEntities.get(i)[1]);
                        json1.put("customerName", customerInfoEntities.get(i)[2]);
                        json1.put("pan", customerInfoEntities.get(i)[3]);
                        json1.put("product", customerInfoEntities.get(i)[4]);
                        json1.put("status", customerInfoEntities.get(i)[5]);
                        json1.put("appId", customerInfoEntities.get(i)[6]);

                        array.put(json1);
                    }
                    json.put("SearchResult", array);
                }
                return json.toString();
            }
        return query;
    }


    @Override
    public List<CustomerInfoEntity> deDupeCustomerInfo(String query) {
        List<CustomerInfoEntity> customerInfoEntities = customerInfoRepository.deDupeCustomerInfo(query);
        return customerInfoEntities;
    }

    @Override
    public CustomerInfoEntity getCustomerInfoById(long id) {
        return customerInfoRepository.findById(id).orElse(null);
    }

    @Override
    public CustomerInfoEntity updateCustomerInfo(CustomerInfoData customerInfoData) {
        CustomerInfoEntity customerInfoEntity = customerInfoRepository.save(updateTransform(customerInfoData));
        return customerInfoEntity;

    }

    private CustomerInfoEntity updateTransform(CustomerInfoData customerInfoData) {
        CustomerInfoEntity customerInfoEntity = customerInfoRepository.findById(customerInfoData.getId()).get();
        Date date = new Date();
        customerInfoEntity.setCustomerName(customerInfoData.getCustomerName());
        customerInfoEntity.setProduct(customerInfoData.getProduct());
        customerInfoEntity.setPan(customerInfoData.getPan());
        customerInfoEntity.setCin(customerInfoData.getCin());
        customerInfoEntity.setBusinessExpiry(customerInfoData.getBusinessExpiry());
        customerInfoEntity.setDedupeStatus(customerInfoData.getDedupeStatus());
        customerInfoEntity.setCreatedBy(customerInfoData.getCreatedBy());
        customerInfoEntity.setUpdatedBy(customerInfoData.getUpdatedBy());
        customerInfoEntity.setCreatedAt(new Timestamp(date.getTime()));
        customerInfoEntity.setUpdatedAt(new Timestamp(date.getTime()));
        return customerInfoEntity;
    }

    public List<CustomerInfoEntity> customerDetails(CustomerInfoData customerInfoData) {
        logger.info(" | URL | customerDetails | OPERATION | " + "POST CustomerDedupe");
        List<CustomerInfoEntity> customerInfoEntities = customerInfoRepository.customerInfoDetails(customerInfoData.getPan(), customerInfoData.getCin());

        for (CustomerInfoEntity customerInfo : customerInfoEntities) {
            Boolean CustomerInfoDetails = customerInfo.getDedupeStatus().equals("In-Active");
            if(CustomerInfoDetails == true){
                return customerInfoEntities;
            }else if(customerInfo == null || customerInfo.equals("")){
                return null;
            }
        }
        return customerInfoEntities;
    }

    /*
    For all existing onboarded customers, it calculates and updates the business expiry column.
     */
    public void updateExpiryDate(){
        List<CustomerInfoEntity> customerInfoEntities = customerInfoRepository.getAllCustomers();
        Integer facilityTenure = 12;
        for(CustomerInfoEntity customerInfoEntity: customerInfoEntities) {
            List<TermSheetEntity> termSheetEntityList = new ArrayList<>();
            ApplicationEntity applicationEntity = applicationRepository.findByCustId(customerInfoEntity.getId());
            if(applicationEntity.getType()==2){
                termSheetEntityList = termSheetRepository.findByAppId(applicationEntity.getId());
                if(termSheetEntityList.size()>0){
                    facilityTenure = Integer.parseInt(termSheetRepository.getMinFacilityTenure(applicationEntity.getId()));
                }
            }
            Date newDateTime = null;
            // To Do - calculate facility tenure for anchor in else if.
            if(applicationEntity.getType()==2){
                newDateTime = getBusinessExpiryDate(applicationEntity.getId(), facilityTenure);
            }
            else if(applicationEntity.getType()==1){
                Collection<ProgramNormsEntity> programNormsEntities = programNormsRepository.findByCiId(applicationEntity.getId());
                if(programNormsEntities.size()>0){
                    newDateTime = getBusinessExpiryDate(applicationEntity.getId(),
                            programNormsRepository.getMinProductExpiry(applicationEntity.getId()));
                    for(ProgramNormsEntity programNormsEntity : programNormsEntities){
                        Date prodExpiryDate = getBusinessExpiryDate(applicationEntity.getId(),
                                programNormsEntity.getProductExpiry());
                        programNormsEntity.setExpiryDate(prodExpiryDate);
                        programNormsRepository.save(programNormsEntity);
                    }
                }
            }

            customerInfoEntity.setBusinessExpiry(newDateTime);
            if(newDateTime != null){
                customerInfoEntity.setStatus(true);
            }
            else{
                customerInfoEntity.setStatus(false);
            }
            customerInfoRepository.save(customerInfoEntity);

            if(termSheetEntityList.size()>0 && applicationEntity.getType()==2){
                for(TermSheetEntity termSheetEntity : termSheetEntityList){
                    Date prodExpDate = getBusinessExpiryDate(termSheetEntity.getApplicationEntity().getId(), Integer.parseInt(termSheetEntity.getRenewalPeriod()));
                    termSheetEntity.setExpiryDate(prodExpDate);
                    termSheetRepository.save(termSheetEntity);
                }
            }

            logger.info("Updated business expiry for the customer {}", customerInfoEntity.getCustomerName());
        }
    }

    /*
    This method calculates the expiry date only for onboarded customers.
    @Param tenure is calculated based on each product's min renewal period
    value from the term sheet.
     */
    public Date getBusinessExpiryDate(Long appId, Integer tenure) {
        WFApprovalStatusEntity wfApprovalStatusEntity = wfApprovalStatusRepository.getWorkflowByAppId(appId);
        if (wfApprovalStatusEntity != null) {
            Boolean defStatus = false;
            List<DeferralReportsEntity> deferralReport = deferralReportsService.getDeferralPendingReport(appId, 0);
            List<OtherDocumentMasterEntity> otherDeferralReport = otherDocumentMasterService.findPendingOtherDoc(appId, 0L);
            if (deferralReport.size()>0 || otherDeferralReport.size()>0)
            {
                defStatus = true;
            }
            if((((wfApprovalStatusEntity.getStage().getId()==26 || wfApprovalStatusEntity.getStage().getId()==21) && !defStatus) || (wfApprovalStatusEntity.getStage().getId()==27 || wfApprovalStatusEntity.getStage().getId()==28)) && wfApprovalStatusEntity.getStatus() == 2 && wfApprovalStatusEntity.getDate() != null) {
                String onboardedDate = wfApprovalStatusEntity.getDate();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date givenDate = sdf.parse(onboardedDate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(givenDate);
                    calendar.add(Calendar.MONTH, tenure);
                    Date newDateTime = calendar.getTime();
                    logger.info("Business expiry calculated for the appId : {} and the exp date is : {}", appId, newDateTime);
                    return newDateTime;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
