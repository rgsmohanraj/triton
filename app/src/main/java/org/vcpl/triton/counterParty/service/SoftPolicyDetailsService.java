package org.vcpl.triton.counterParty.service;

import oracle.jdbc.driver.json.binary.JsonpOsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.CreditNormsDetailsRepository;
import org.vcpl.triton.counterParty.data.SoftPolicyDetailsData;
import org.vcpl.triton.counterParty.data.SoftPolicyDetailsMasterData;
import org.vcpl.triton.counterParty.entity.ProposedProductsEntity;
import org.vcpl.triton.counterParty.entity.SoftPolicyDetailsEntity;
import org.vcpl.triton.counterParty.entity.SoftPolicyFilterMasterEntity;
import org.vcpl.triton.counterParty.entity.SoftPolicyMasterSubTypeEntity;
import org.vcpl.triton.counterParty.repository.CPBasicDetailsRepository;
import org.vcpl.triton.counterParty.repository.ProposedProductsRepository;
import org.vcpl.triton.counterParty.repository.SoftPolicyDetailsRepository;
import org.vcpl.triton.counterParty.repository.SoftPolicyMasterSubTypeRepository;

import javax.script.ScriptException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class SoftPolicyDetailsService implements ISoftPolicyDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(SoftPolicyDetailsService.class);


    @Autowired
    private SoftPolicyDetailsRepository softPolicyDetailsRepository;

    @Autowired
    private SoftPolicyMasterSubTypeRepository softPolicyMasterSubTypeRepository;

    @Autowired
    private CPBasicDetailsRepository cpBasicDetailsRepository;

    @Autowired
    private SoftPolicyFilterMasterService softPolicyFilterMasterService;

    @Autowired
    private ProposedProductsService proposedProductsService;

    @Autowired
    private ProposedProductsRepository proposedProductsRepository;

    @Autowired
    private CreditNormsDetailsRepository creditNormsDetailsRepository;

    @Autowired
    private ApplicationRepository applicationRepository;


    @Override
    public List<SoftPolicyDetailsEntity> getAllSoftPolicyDetails() {
        logger.info(" | URL | /softPolicyDetails | OPERATION | " + "GET softPolicyDetails");
        return this.softPolicyDetailsRepository.findAll();

    }

    @Override
    public String getSoftPolicyById(long id) {
        logger.info(" | URL | /softPolicyDetails/{id} | OPERATION | " + "GETById softPolicyDetails");
        try {
            Collection<SoftPolicyDetailsEntity> softPolicyDetailsEntities = softPolicyDetailsRepository.findByFId(id);
            JSONObject json = new JSONObject();
            if (softPolicyDetailsEntities != null && softPolicyDetailsEntities.size() > 0) {
                JSONArray array = new JSONArray();
                for (SoftPolicyDetailsEntity s : softPolicyDetailsEntities) {
                    JSONObject json1 = new JSONObject();
                    json1.put("id", s.getId());
                    json1.put("value", s.getValue());
                    json1.put("appId", s.getApplicationEntity().getId());
                    json1.put("softPolicyId", s.getSoftPolicyMasterSubTypeEntity().getId());
                    array.put(json1);
                }
                json.put("softPolicyDetailsDataList", array);
            } else {
                return "No Value present";
            }
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String softPolicyFilter(SoftPolicyDetailsMasterData softPolicyDetailsMasterData, Long appId) throws ScriptException {

        JSONObject json = new JSONObject();
        Boolean dealer = false;
        Boolean vendor = false;
        Collection<ProposedProductsEntity> proposedProductList = proposedProductsRepository.getByAppId(appId);
        for (ProposedProductsEntity proposedProductsEntity: proposedProductList){
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
        try {
//                List<SoftPolicyFilterMasterEntity> softPolicyFilterMasterEntities = new ArrayList<SoftPolicyFilterMasterEntity>();
            if(dealer && vendor){

                JSONArray array1 = new JSONArray();

                for(int i=1;i < 3; i++){
//                    JSONArray typeArray = new JSONArray();
                    List<SoftPolicyFilterMasterEntity> softPolicyFilterMasterEntities = softPolicyFilterMasterService.findBySoftPolicyFilterType(String.valueOf(i));
                    JSONArray array = new JSONArray();
                    JSONObject typeJson = new JSONObject();
                    for (SoftPolicyFilterMasterEntity master : softPolicyFilterMasterEntities) {
                        for (SoftPolicyDetailsData details : softPolicyDetailsMasterData.getSoftPolicyDetailsDataList()) {
                            if ((master.getSoftPolicySubTypeId()).equals(details.getSoftPolicyId())) {
                                if (master.getSoftPolicyMasterSubTypeEntity().getDataType().equals("Integer")) {
                                    if (!master.getFormula().isEmpty()) {
                                        String condition = master.getFormula().replaceAll("value", details.getValue()) + ";";

                                        Context rhinoContext = Context.enter();
                                        Scriptable scope = rhinoContext.initStandardObjects();

                                        Object result = rhinoContext.evaluateString(scope, condition, "JavaScript", 1, null);
                                        boolean isConditionTrue = (boolean) result;
                                        Context.exit();
                                        JSONObject json1 = new JSONObject();
                                        json1.put("type", master.getSoftPolicyTypeDisplayName());
                                        json1.put("subType", master.getSoftPolicySubTypeDisplayName());
                                        if (isConditionTrue) {
                                            json1.put("result", true);
                                        } else {
                                            json1.put("result", false);
                                        }
                                        json1.put("softPolicyValue", details.getValue());
                                        json1.put("softPolicyFormula", master.getFormula());
                                        array.put(json1);
                                    }
                                } else if (master.getSoftPolicyMasterSubTypeEntity().getDataType().equals("multiString")) {
                                    if (!master.getFormula().isEmpty()) {
                                        JSONObject json1 = new JSONObject();
                                        json1.put("type", master.getSoftPolicyTypeDisplayName());
                                        json1.put("subType", master.getSoftPolicySubTypeDisplayName());
                                        String[] strArray = null;
                                        strArray = master.getFormula().split(",");
                                        List<String> strList = Arrays.asList(strArray);
                                        if (strList.contains(details.getValue())) {
                                            json1.put("result", false);
                                        } else {
                                            json1.put("result", true);
                                        }
                                        json1.put("softPolicyValue", details.getValue());
                                        json1.put("softPolicyFormula", master.getFormula());
                                        array.put(json1);
                                    }
                                }

                            }
                        }
                    }
                    if(i == 1){
                        typeJson.put("dealer",array);
                    } else if (i == 2) {
                        typeJson.put("vendor",array);
                    }
                    array1.put(typeJson);
                }
                json.put("flag",true);
                json.put("softPolicyResult", array1);
            }else {
                JSONArray array = new JSONArray();
                JSONArray array1 = new JSONArray();
                JSONObject typeJson = new JSONObject();
                String custType = null;
                if(dealer) {
                    custType = "1";
                }
                else if(vendor){
                    custType = "2";
                }
                List<SoftPolicyFilterMasterEntity> softPolicyFilterMasterEntities = softPolicyFilterMasterService.findBySoftPolicyFilterType(custType);

                for (SoftPolicyFilterMasterEntity master : softPolicyFilterMasterEntities) {
                    for (SoftPolicyDetailsData details : softPolicyDetailsMasterData.getSoftPolicyDetailsDataList()) {
                        if ((master.getSoftPolicySubTypeId()).equals(details.getSoftPolicyId())) {
                            if (master.getSoftPolicyMasterSubTypeEntity().getDataType().equals("Integer")) {
                                if (!master.getFormula().isEmpty()) {
                                    String condition = master.getFormula().replaceAll("value", details.getValue()) + ";";

                                    Context rhinoContext = Context.enter();
                                    Scriptable scope = rhinoContext.initStandardObjects();

                                    Object result = rhinoContext.evaluateString(scope, condition, "JavaScript", 1, null);
                                    boolean isConditionTrue = (boolean) result;
                                    Context.exit();
                                    JSONObject json1 = new JSONObject();
                                    json1.put("type", master.getSoftPolicyTypeDisplayName());
                                    json1.put("subType", master.getSoftPolicySubTypeDisplayName());
                                    if (isConditionTrue) {
                                        json1.put("result", true);
                                    } else {
                                        json1.put("result", false);
                                    }
                                    json1.put("softPolicyValue", details.getValue());
                                    json1.put("softPolicyFormula", master.getFormula());
                                    array.put(json1);
                                }
                            } else if (master.getSoftPolicyMasterSubTypeEntity().getDataType().equals("multiString")) {
                                if (!master.getFormula().isEmpty()) {
                                    JSONObject json1 = new JSONObject();
                                    json1.put("type", master.getSoftPolicyTypeDisplayName());
                                    json1.put("subType", master.getSoftPolicySubTypeDisplayName());
                                    String[] strArray = null;
                                    strArray = master.getFormula().split(",");
                                    List<String> strList = Arrays.asList(strArray);
                                    if (strList.contains(details.getValue())) {
                                        json1.put("result", false);
                                    } else {
                                        json1.put("result", true);
                                    }
                                    json1.put("softPolicyValue", details.getValue());
                                    json1.put("softPolicyFormula", master.getFormula());
                                    array.put(json1);
                                }
                            }

                        }
                    }
                }
                array1.put(array);
                json.put("flag",false);
                json.put("softPolicyResult", array1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    @Override
    public List<Long> saveSoftPolicyDetails(SoftPolicyDetailsMasterData softPolicyDetailsMasterData) {
        logger.info(" | URL | /softPolicyDetails | OPERATION | " + "POST softPolicyDetails");
            List<Long> entityIdLst = new ArrayList<Long>();
            for (SoftPolicyDetailsData softPolicyDetailsData1 : softPolicyDetailsMasterData.getSoftPolicyDetailsDataList()) {
                entityIdLst.add(softPolicyDetailsRepository.save(transform(softPolicyDetailsData1)).getId());
            }
            return entityIdLst;
        }

    private SoftPolicyDetailsEntity transform(SoftPolicyDetailsData softPolicyDetailsData) {
        SoftPolicyDetailsEntity softPolicyDetailsEntity = new SoftPolicyDetailsEntity();
        Date date = new Date();
        softPolicyDetailsEntity.setValue(softPolicyDetailsData.getValue());
        softPolicyDetailsEntity.setCreatedBy(softPolicyDetailsData.getCreatedBy());
        softPolicyDetailsEntity.setCreatedAt(new Timestamp(date.getTime()));
        softPolicyDetailsEntity.setUpdatedBy(softPolicyDetailsData.getUpdatedBy());
        softPolicyDetailsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        softPolicyDetailsEntity.setSoftPolicyMasterSubTypeEntity(softPolicyMasterSubTypeRepository.getById(softPolicyDetailsData.getSoftPolicyId()));
        softPolicyDetailsEntity.setApplicationEntity(applicationRepository.findById(softPolicyDetailsData.getAppId()).orElseThrow());

        return softPolicyDetailsEntity;
    }


    @Override
    public String deleteSoftPolicyDetails(long id) {
        logger.info(" | URL | /softPolicyDetails/{id} | OPERATION | " + "DELETE softPolicyDetails");
        softPolicyDetailsRepository.deleteById(id);
            return id + "Removed";
        }

    @Override
    public List<Long> updateSoftPolicyDetails(SoftPolicyDetailsMasterData softPolicyDetailsMasterData) {
        logger.info(" | URL | /softPolicyDetails/{id} | OPERATION | " + "PUT softPolicyDetails");

        List<Long> entityIdLst = new ArrayList<Long>();
        for(SoftPolicyDetailsData softPolicyDetailsData : softPolicyDetailsMasterData.getSoftPolicyDetailsDataList()) {
            entityIdLst.add(softPolicyDetailsRepository.save(updateTransform(softPolicyDetailsData,softPolicyDetailsData.getId())).getId());
        }
        return entityIdLst;
    }
    private SoftPolicyDetailsEntity updateTransform(SoftPolicyDetailsData softPolicyDetailsData, long id) {
        SoftPolicyDetailsEntity softPolicyDetailsEntity = softPolicyDetailsRepository.findById(id).get();
        Date date = new Date();
        softPolicyDetailsEntity.setValue(softPolicyDetailsData.getValue());
        softPolicyDetailsEntity.setCreatedBy(softPolicyDetailsData.getCreatedBy());
        softPolicyDetailsEntity.setCreatedAt(new Timestamp(date.getTime()));
        softPolicyDetailsEntity.setUpdatedBy(softPolicyDetailsData.getUpdatedBy());
        softPolicyDetailsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        softPolicyDetailsEntity.setSoftPolicyMasterSubTypeEntity(softPolicyMasterSubTypeRepository.getById(softPolicyDetailsData.getSoftPolicyId()));
        softPolicyDetailsEntity.setApplicationEntity(applicationRepository.getById(softPolicyDetailsData.getAppId()));
        return softPolicyDetailsEntity;
    }


}
