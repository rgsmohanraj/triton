package org.vcpl.triton.counterParty.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.entity.CreditNormsDetailsEntity;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.CreditNormsDetailsRepository;
import org.vcpl.triton.counterParty.data.*;
import org.vcpl.triton.counterParty.entity.CreditPolicyDetailsEntity;
import org.vcpl.triton.counterParty.entity.CreditPolicyMasterEntity;
import org.vcpl.triton.counterParty.entity.SoftPolicyMasterSubTypeEntity;
import org.vcpl.triton.counterParty.repository.CreditPolicyDetailsRepository;
import org.vcpl.triton.counterParty.repository.CreditPolicyMasterRepository;
import org.vcpl.triton.counterParty.repository.ProposedProductsRepository;

import java.sql.Timestamp;
import java.util.*;


@Service
public class CreditPolicyDetailsService implements ICreditPolicyDetailsService{

    @Autowired
    private ProposedProductsService proposedProductsService;

    @Autowired
    private ProposedProductsRepository proposedProductsRepository;

    @Autowired
    private CreditNormsDetailsRepository creditNormsDetailsRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CreditPolicyMasterRepository creditPolicyMasterRepository;

    @Autowired
    private CreditPolicyDetailsRepository creditPolicyDetailsRepository;

    private static final Logger logger = LoggerFactory.getLogger(CreditPolicyDetailsService.class);

    @Override
    public String findByCpCreditPolicyDetails(long id) {
        logger.info(" | URL | /cpDebtProfile/{id} | OPERATION | " + "GETById cpDebtProfile");
//            return creditPolicyDetailsRepository.findByAppId(id);
        try {
            Collection<CreditPolicyDetailsEntity> creditPolicyDetailsEntities = creditPolicyDetailsRepository.findByAppId(id);
            JSONObject json=new JSONObject();
            if(creditPolicyDetailsEntities!=null && creditPolicyDetailsEntities.size()>0){
                JSONArray array=new JSONArray();
                for(CreditPolicyDetailsEntity  s : creditPolicyDetailsEntities){
                    JSONObject json1=new JSONObject();
                    json1.put("id",s.getId());
                    json1.put("value",s.getValue());
                    json1.put("appId",s.getApplicationEntity().getId());
                    json1.put("creditPolicyId",s.getCreditPolicyMasterEntity().getId());
                    json1.put("scpDisplayName",s.getCreditPolicyMasterEntity().getDisplayName());
                    json1.put("cpName",s.getCreditPolicyMasterEntity().getPolicyName());
                    json1.put("dataType",s.getCreditPolicyMasterEntity().getDataType());
                    array.put(json1);
                }
                json.put("creditPolicyArray",array);
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
    public String creditPolicyFilter(CreditPolicyDetailsMasterData creditPolicyDetailsMasterData, long id) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        String list = proposedProductsService.findCreditNormsId(id);
        JSONArray jsonArray = new JSONArray(list);
        if (list.length() > 0) {
            for (int i=0;i<jsonArray.length();i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONArray array1 = new JSONArray();
                Long appId = obj.getLong("creditAppId");
                List<CreditNormsDetailsEntity> creditDetailsList = creditNormsDetailsRepository.findByCiIdArray(appId);

                List<CreditPolicyMasterEntity> creditPolicyMasterEntities = creditPolicyMasterRepository.findAll();
                if(creditPolicyDetailsMasterData!=null && creditPolicyDetailsMasterData.getCreditPolicyDetailsData().size()>0) {
                    for (CreditPolicyMasterEntity master : creditPolicyMasterEntities) {
                        for (CreditPolicyDetailsData details : creditPolicyDetailsMasterData.getCreditPolicyDetailsData()) {
                            if ( master.getId() == details.getCpMasterId()) {
                                if (master.getFilters().equals(">=")) {
                                    for(CreditNormsDetailsEntity creditNormsDetails : creditDetailsList) {
                                        JSONObject json1 = new JSONObject();
                                        if (creditNormsDetails.getCreditNormsMasterEntity().getName().equals(master.getPolicyName())) {
                                            json1.put("anchorName", creditNormsDetails.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
                                            json1.put("type", master.getDisplayName());
                                            if(Integer.parseInt(details.getValue()) >= Integer.parseInt(creditNormsDetails.getValue())){
                                                json1.put("result",true);
                                            }else {
                                                json1.put("result",false);
                                            }
                                            json1.put("creditPolicyValue",details.getValue());
                                            json1.put("creditNormsValue",creditNormsDetails.getValue());
                                            array1.put(json1);

                                        }
                                    }
                                } else if (master.getFilters().equals("<=")) {
                                    for(CreditNormsDetailsEntity creditNormsDetails : creditDetailsList) {
                                        JSONObject json1 = new JSONObject();
                                        if (creditNormsDetails.getCreditNormsMasterEntity().getName().equals(master.getPolicyName())) {
                                            json1.put("anchorName", creditNormsDetails.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
                                            json1.put("type", master.getDisplayName());
                                            if(Integer.parseInt(details.getValue()) <= Integer.parseInt(creditNormsDetails.getValue())){
                                                json1.put("result",true);
                                            }else {
                                                json1.put("result",false);
                                            }
                                            json1.put("creditPolicyValue",details.getValue());
                                            json1.put("creditNormsValue",creditNormsDetails.getValue());
                                            array1.put(json1);

                                        }
                                    }
                                } else if (master.getFilters().equals(">")) {
                                    for(CreditNormsDetailsEntity creditNormsDetails : creditDetailsList) {
                                        JSONObject json1 = new JSONObject();
                                        if (creditNormsDetails.getCreditNormsMasterEntity().getName().equals(master.getPolicyName())) {
                                            json1.put("anchorName", creditNormsDetails.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
                                            json1.put("type", master.getDisplayName());
                                            if(Integer.parseInt(details.getValue()) > Integer.parseInt(creditNormsDetails.getValue())){
                                                json1.put("result",true);
                                            }else {
                                                json1.put("result",false);
                                            }
                                            json1.put("creditPolicyValue",details.getValue());
                                            json1.put("creditNormsValue",creditNormsDetails.getValue());
                                            array1.put(json1);

                                        }
                                    }
                                } else if (master.getFilters().equals("<")) {
                                    for(CreditNormsDetailsEntity creditNormsDetails : creditDetailsList) {
                                        JSONObject json1 = new JSONObject();
                                        if (creditNormsDetails.getCreditNormsMasterEntity().getName().equals(master.getPolicyName())) {
                                            json1.put("anchorName", creditNormsDetails.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
                                            json1.put("type", master.getDisplayName());
                                            if(Integer.parseInt(details.getValue()) < Integer.parseInt(creditNormsDetails.getValue())){
                                                json1.put("result",true);
                                            }else {
                                                json1.put("result",false);
                                            }
                                            json1.put("creditPolicyValue",details.getValue());
                                            json1.put("creditNormsValue",creditNormsDetails.getValue());
                                            array1.put(json1);

                                        }
                                    }
                                } else if (master.getFilters().equals("+")) {
                                    for(CreditNormsDetailsEntity creditNormsDetails : creditDetailsList) {
                                        JSONObject json1 = new JSONObject();
                                        if (creditNormsDetails.getCreditNormsMasterEntity().getName().equals(master.getPolicyName())) {
                                            json1.put("anchorName", creditNormsDetails.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
                                            json1.put("type", master.getDisplayName());
                                            if(Integer.parseInt(details.getValue()) > 0.0){
                                                json1.put("result",true);
                                            }else {
                                                json1.put("result",false);
                                            }
                                            json1.put("creditPolicyValue",details.getValue());
                                            json1.put("creditNormsValue",creditNormsDetails.getValue());
                                            array1.put(json1);

                                        }
                                    }
                                } else if (master.getFilters().equals("Verified")) {
                                    for(CreditNormsDetailsEntity creditNormsDetails : creditDetailsList) {
                                        JSONObject json1 = new JSONObject();
                                        if (creditNormsDetails.getCreditNormsMasterEntity().getName().equals(master.getPolicyName())) {
                                            json1.put("anchorName", creditNormsDetails.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
                                            json1.put("type", master.getDisplayName());
                                            if(creditNormsDetails.getValue().equals("Required") && details.getValue().equals("Verified")){
                                                json1.put("result",true);
                                            }else if(creditNormsDetails.getValue().equals("Required") && details.getValue().equals("Not Verified")){
                                                json1.put("result",false);
                                            }else if(creditNormsDetails.getValue().equals("Not Required") && details.getValue().equals("Verified")){
                                                json1.put("result",true);
                                            }else if(creditNormsDetails.getValue().equals("Not Required") && details.getValue().equals("Not Verified")){
                                                json1.put("result",true);
                                            }
                                            json1.put("creditPolicyValue",details.getValue());
                                            json1.put("creditNormsValue",creditNormsDetails.getValue());
                                            array1.put(json1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                array.put(array1);
            }
            json.put("creditPolicyResult",array);
        }
        return json.toString();
    }

    @Override
    public List<Long> saveCreditPolicyDetails(CreditPolicyDetailsMasterData creditPolicyDetailsMasterData) {
        logger.info(" | URL | /saveCreditPolicyDetails | OPERATION | " + "saveCreditPolicyDetails");
        List<Long> entityIdLst = new ArrayList<Long>();
        for (CreditPolicyDetailsData creditPolicyDetailsData1 : creditPolicyDetailsMasterData.getCreditPolicyDetailsData()) {
            entityIdLst.add(creditPolicyDetailsRepository.save(transform(creditPolicyDetailsData1)).getId());
        }
            return entityIdLst;
        }

    private CreditPolicyDetailsEntity transform(CreditPolicyDetailsData creditPolicyDetailsData) {
        CreditPolicyDetailsEntity creditPolicyDetailsEntity = new CreditPolicyDetailsEntity();
        Date date = new Date();
        creditPolicyDetailsEntity.setValue(creditPolicyDetailsData.getValue());
        creditPolicyDetailsEntity.setCreatedAt(new Timestamp(date.getTime()));
        creditPolicyDetailsEntity.setCreatedBy(creditPolicyDetailsData.getCreatedBy());
        creditPolicyDetailsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        creditPolicyDetailsEntity.setUpdatedBy(creditPolicyDetailsData.getUpdatedBy());
        creditPolicyDetailsEntity.setApplicationEntity(applicationRepository.findById(creditPolicyDetailsData.getAppId()).orElseThrow());
        creditPolicyDetailsEntity.setCreditPolicyMasterEntity(creditPolicyMasterRepository.findById(creditPolicyDetailsData.getCpMasterId()).orElseThrow());
        return creditPolicyDetailsEntity;
    }

    @Override
    public List<Long> updateCreditPolicyDetails(CreditPolicyDetailsMasterData creditPolicyDetailsMasterData) {
        logger.info(" | URL | /updateCreditPolicyDetails | OPERATION | " + "updateCreditPolicyDetails");
        List<Long> entityIdLst = new ArrayList<Long>();
        for (CreditPolicyDetailsData creditPolicyDetailsData : creditPolicyDetailsMasterData.getCreditPolicyDetailsData()) {
            entityIdLst.add(creditPolicyDetailsRepository.save(updateTransform(creditPolicyDetailsData,creditPolicyDetailsData.getId())).getId());
        }
        return entityIdLst;
    }
    private CreditPolicyDetailsEntity updateTransform(CreditPolicyDetailsData creditPolicyDetailsData, long id) {
        CreditPolicyDetailsEntity creditPolicyDetailsEntity = creditPolicyDetailsRepository.findById(id).get();

        Date date = new Date();
        creditPolicyDetailsEntity.setValue(creditPolicyDetailsData.getValue());
        creditPolicyDetailsEntity.setCreatedAt(new Timestamp(date.getTime()));
        creditPolicyDetailsEntity.setCreatedBy(creditPolicyDetailsData.getCreatedBy());
        creditPolicyDetailsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        creditPolicyDetailsEntity.setUpdatedBy(creditPolicyDetailsData.getUpdatedBy());
        creditPolicyDetailsEntity.setApplicationEntity(applicationRepository.getById(creditPolicyDetailsData.getAppId()));
        creditPolicyDetailsEntity.setCreditPolicyMasterEntity(creditPolicyMasterRepository.getReferenceById(creditPolicyDetailsData.getCpMasterId()));

        return creditPolicyDetailsEntity;
    }

}
