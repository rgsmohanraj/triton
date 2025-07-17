package org.vcpl.triton.counterParty.service;

import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mozilla.javascript.Scriptable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.vcpl.triton.anchor.entity.CreditNormsDetailsEntity;
import org.vcpl.triton.anchor.entity.ProgramNormsEntity;
import org.vcpl.triton.anchor.repository.CreditNormsDetailsRepository;
import org.vcpl.triton.anchor.repository.ProgramNormsRepository;
import org.vcpl.triton.counterParty.data.CreditPolicyDetailsData;
import org.vcpl.triton.counterParty.data.CreditPolicyDetailsMasterData;
import org.vcpl.triton.counterParty.data.CreditPolicyMasterData;
import org.vcpl.triton.counterParty.entity.*;
import org.vcpl.triton.counterParty.repository.*;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentListEntity;
import org.vcpl.triton.validation.ResponseControllerService;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service
public class CreditPolicyMasterService implements ICreditPolicyMaster{

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private CreditPolicyMasterRepository creditPolicyMasterRepository;

    @Autowired
    private CreditPolicyConfigRepo creditPolicyConfigRepo;

    @Autowired
    private CreditPolicyFilterRepo creditPolicyFilterRepo;

    @Autowired
    private ProposedProductsService proposedProductsService;

    @Autowired
    private CreditNormsDetailsRepository creditNormsDetailsRepository;

    @Autowired
    private ProgramNormsRepository programNormsRepository;

    @Autowired
    private CPBasicDetailsRepository cpBasicDetailsRepository;

    @Autowired
    private ProposedProductsRepository proposedProductsRepository;


    @Override
    public List<CreditPolicyMasterEntity> getAllCreditPolicyMaster() {
        return this.creditPolicyMasterRepository.findAll();
    }

    public String getAllCreditPolicyFilter(long id) {
        try {
            JSONArray array = new JSONArray();
            List<Object> arrayList = new ArrayList<>();
            Collection<ProposedProductsEntity> proposedProductList = proposedProductsRepository.getByAppId(id);
            for(ProposedProductsEntity proposedProductsEntity: proposedProductList) {

                Long custId = proposedProductsEntity.getCustomerInfoEntity().getId();
                String productName = proposedProductsEntity.getProduct();
                String anchorRelationship = proposedProductsEntity.getAnchorRelationship();
                CPBasicDetailsEntity cpBasicDetailsEntity = cpBasicDetailsRepository.findByFIdDetails(id);
                String assessmentType = cpBasicDetailsEntity.getAssessmentType();
                ProgramNormsEntity programNormsEntity = programNormsRepository.fidByAppId(custId, productName);
                String cpType = programNormsEntity.getSubProduct();
                String cpType1 = "";
                if (cpType.equals("Dealer Invoice Finance") || cpType.equals("Dealer Purchase Order Finance") || cpType.equals("Anchor Sales Bill Discounting")) {
                    cpType1 = "Dealer";
                } else {
                    cpType1 = "Vendor";
                }
                String recourse = programNormsEntity.getTransactionType();
                CreditPolicyConfigEntity creditPolicyConfigEntity = creditPolicyConfigRepo.findConfigId(anchorRelationship, assessmentType, cpType1, recourse);
                if (creditPolicyConfigEntity != null) {
                    long confId = creditPolicyConfigEntity.getId();

                    List<CreditPolicyFilters> creditPolicyFiltersList = creditPolicyFilterRepo.findFilter(confId);
                    if (creditPolicyFiltersList != null) {
                        for (CreditPolicyFilters creditPolicyFilters : creditPolicyFiltersList) {
                            JSONObject json1 = new JSONObject();
                            if(!arrayList.contains(creditPolicyFilters.getCreditPolicyMasterEntity().getId())){
                                arrayList.add(creditPolicyFilters.getCreditPolicyMasterEntity().getId());
                                json1.put("id", creditPolicyFilters.getCreditPolicyMasterEntity().getId());
                                json1.put("displayName", creditPolicyFilters.getCreditPolicyMasterEntity().getDisplayName());
                                json1.put("status", creditPolicyFilters.getCreditPolicyMasterEntity().getStatus());
                                json1.put("dataType", creditPolicyFilters.getCreditPolicyMasterEntity().getDataType());
                                array.put(json1);
                            }
                        }
                    }
                }
            }
            return array.toString();
        }catch (NullPointerException e){
            return null;
        }
    }

//    public CreditPolicyConfigEntity getAllCreditPolicy(String anchorRelationship,String assessmentType,String cpType,String recourse) {
//        CreditPolicyConfigEntity creditPolicyConfigEntities = creditPolicyConfigRepo.findConfig(anchorRelationship, assessmentType, cpType, recourse);
//        return creditPolicyConfigEntities;
//    }
    public   String  creditPolicyFilter(CreditPolicyDetailsMasterData creditPolicyDetailsMasterData,long apppId) throws ScriptException {
        JSONObject json = new JSONObject();
        try {
            Collection<ProposedProductsEntity> proposedProductList = proposedProductsRepository.getByAppId(apppId);
            JSONArray array1 = new JSONArray();
            String totalInwardCheques = null;
            for(CreditPolicyDetailsData creditPolicyDetailsData : creditPolicyDetailsMasterData.getCreditPolicyDetailsData()){
                if ((creditPolicyDetailsData.getCpMasterId()).equals(28L)) { // Total Inward Cheques
                    totalInwardCheques = creditPolicyDetailsData.getValue();
                }
            }
            for (ProposedProductsEntity proposedProductsEntity: proposedProductList) {
                Long custId = null;
                Boolean dealer = false;
                Boolean vendor = false;
                String productName = null;
                String cpType = null;
                String anchorName = null;
                JSONObject json2 = new JSONObject();
                JSONArray array = new JSONArray();

                productName = proposedProductsEntity.getProduct();
                custId = proposedProductsEntity.getCustomerInfoEntity().getId();
                anchorName = proposedProductsEntity.getCustomerInfoEntity().getCustomerName();
                if (productName.equalsIgnoreCase("Dealer Invoice Finance") ||
                        productName.equalsIgnoreCase("Dealer Purchase Order Finance") ||
                        productName.equalsIgnoreCase("Anchor Sales Bill Discounting")) {
                    dealer = true;
                } else if (productName.equalsIgnoreCase("Vendor Invoice Finance") ||
                        productName.equalsIgnoreCase("Vendor Purchase Order Finance") ||
                        productName.equalsIgnoreCase("Anchor Purchase Bill Discounting")) {
                    vendor = true;
                }
                ProgramNormsEntity programNormsEntity = programNormsRepository.fidByAppId(custId, productName);
                CPBasicDetailsEntity cpBasicDetailsEntity = cpBasicDetailsRepository.findByFIdDetails(apppId);

                String recourse = programNormsEntity.getTransactionType();
//            String recourse ="Non Recourse";
                BigDecimal creditPeriod = programNormsEntity.getTenure();

                String assessmentType = cpBasicDetailsEntity.getAssessmentType();
                String anchorRelationship = proposedProductsEntity.getAnchorRelationship();
                Double vintageWithAnchor = proposedProductsEntity.getVintageWithAnchor();
                Double minMonthlyPurchase = proposedProductsEntity.getMinMonthlyAnchor();
                if (dealer) {
                    cpType = "Dealer";
                } else if (vendor) {
                    cpType = "Vendor";
                }
                String activity = cpBasicDetailsEntity.getActivity();

                CreditPolicyConfigEntity creditPolicyConfigEntities = creditPolicyConfigRepo.findConfigId(anchorRelationship, assessmentType, cpType, recourse);
                if(creditPolicyConfigEntities != null) {
                    long id = creditPolicyConfigEntities.getId();
                        List<CreditNormsDetailsEntity> creditNormsDetailsEntities = creditNormsDetailsRepository.findByCreditNorms(custId);
                        for (CreditNormsDetailsEntity creditNormsDetailsEntity1 : creditNormsDetailsEntities) {
                            if (creditNormsDetailsEntity1.getCreditNormsMasterEntity().getId().equals(263L)) {
                                String CreditNorms = creditNormsDetailsEntity1.getValue();
                                double VintageWithAnchorvalue = Double.parseDouble(CreditNorms);
                                double ii = vintageWithAnchor;
                                Boolean condition = (ii >= VintageWithAnchorvalue);
                                JSONObject json1 = new JSONObject();
                                json1.put("displayName", "Min Vintage with Anchor (in Months)");
                                if (condition) {
                                    json1.put("result", true);
                                } else {
                                    json1.put("result", false);
                                }
                                json1.put("value", vintageWithAnchor);
//                                json1.put("formula", filter.getFormula());
                                array.put(json1);
                            }
                            if (creditNormsDetailsEntity1.getCreditNormsMasterEntity().getId().equals(266L)) {
                                String CreditNorms = creditNormsDetailsEntity1.getValue();
                                double MinWithAnchorvalue = Double.parseDouble(CreditNorms);
                                //String value1 = data.getValue();
                                double ii = minMonthlyPurchase;
                                Boolean condition = (ii >= MinWithAnchorvalue);
                                JSONObject json1 = new JSONObject();
                                json1.put("displayName", "Min Monthly Purchase from Anchor (Rs in Lakhs)");
                                if (condition) {
                                    json1.put("result", true);
                                } else {
                                    json1.put("result", false);
                                }
                                json1.put("value", minMonthlyPurchase);
//                                json1.put("formula", filter.getFormula());
                                array.put(json1);
                            }

                        }
//                    }
                    List<CreditPolicyFilters> CreditPolicyFilters = creditPolicyFilterRepo.findFilterId(id);
                    for (CreditPolicyFilters filter : CreditPolicyFilters) {
                        for (CreditPolicyDetailsData data : creditPolicyDetailsMasterData.getCreditPolicyDetailsData()) {
                            if ((filter.getCreditPolicyMasterEntity().getId()).equals(data.getCpMasterId())) {
                                if (filter.getCreditPolicyMasterEntity().getDataType().equals("Integer")) {
                                    if (!filter.getFormula().isEmpty()) {
                                        String condition = filter.getFormula().replaceAll("value", data.getValue()) + ";";
                                        Context context = Context.enter();
                                        Scriptable scope = context.initStandardObjects();
                                        Object result = context.evaluateString(scope, condition, "JavaScript", 1, null);
                                        boolean isConditionTrue = (boolean) result;
                                        Context.exit();
                                        JSONObject json1 = new JSONObject();
                                        json1.put("displayName", filter.getCreditPolicyMasterEntity().getDisplayName());
                                        if (isConditionTrue) {
                                            json1.put("result", true);
                                        } else {
                                            json1.put("result", false);
                                        }
                                        json1.put("value", data.getValue());
                                        json1.put("formula", filter.getFormula());
                                        array.put(json1);
                                    }
                                }
                                if (filter.getCreditPolicyMasterEntity().getDataType().equals("CheckPolicy")) {
                                    if (!filter.getFormula().isEmpty()) {
                                        if ((filter.getCreditPolicyMasterEntity().getId()).equals(4L)) {
                                            for (CreditPolicyDetailsData pat : creditPolicyDetailsMasterData.getCreditPolicyDetailsData()) {
                                                if ((pat.getCpMasterId().equals(6L))) {//Sales Growth
                                                    String patValue = pat.getValue();
                                                    double i = Double.parseDouble(patValue);
                                                    if (i <= 0) {
                                                        String value = data.getValue();
                                                        Double creditValue = Double.parseDouble(value);
                                                        Boolean condition = creditValue >= 0;
                                                        JSONObject json1 = new JSONObject();
                                                        json1.put("displayName", filter.getCreditPolicyMasterEntity().getDisplayName());
                                                        if (condition) {
                                                            json1.put("result", true);
                                                        } else {
                                                            json1.put("result", false);
                                                        }
                                                        json1.put("value", data.getValue());
                                                        json1.put("formula", filter.getFormula());
                                                        array.put(json1);
                                                    }
                                                    if (i > 0) {
                                                        String value = data.getValue();
                                                        double creditValue = Double.parseDouble(value);
                                                        Boolean condition = creditValue > -10;
                                                        JSONObject json1 = new JSONObject();
                                                        json1.put("displayName", filter.getCreditPolicyMasterEntity().getDisplayName());
                                                        if (condition) {
                                                            json1.put("result", true);
                                                        } else {
                                                            json1.put("result", false);
                                                        }
                                                        json1.put("value", data.getValue());
                                                        json1.put("formula", filter.getFormula());
                                                        array.put(json1);

                                                    }

                                                }
                                            }
                                        }
//
//                                        if ((filter.getCreditPolicyMasterEntity().getId()).equals(10L)) { // Total Inward Cheques
//                                            totalInwardCheques = data.getValue();
//                                        }

                                        if ((filter.getCreditPolicyMasterEntity().getId()).equals(10L) && !assessmentType.equalsIgnoreCase("KYC")) { //Inward Cheque Bounces
                                            int a = 4;
    //                                int inwardCheque = 1000;
                                            double totalInward = Double.parseDouble(totalInwardCheques);
                                            double max = Math.max((totalInward * 0.04), a);
                                            String value = data.getValue();
                                            Double i = Double.parseDouble(value);
                                            Boolean condition = (i <= max);
                                            JSONObject json1 = new JSONObject();
                                            json1.put("displayName", filter.getCreditPolicyMasterEntity().getDisplayName());
                                            if (condition) {
                                                json1.put("result", true);
                                            } else {
                                                json1.put("result", false);
                                            }
                                            json1.put("value", data.getValue());
                                            json1.put("formula", filter.getFormula());
                                            array.put(json1);
                                        }
                                        if ((filter.getCreditPolicyMasterEntity().getId()).equals(8L)) {
                                            if (activity.equals("Manufacturers")) {
                                                String value = data.getValue();
                                                double i = Double.parseDouble(value);
                                                Boolean condition = (i > 0) & (i <= 5.0);
                                                JSONObject json1 = new JSONObject();
                                                json1.put("displayName", filter.getCreditPolicyMasterEntity().getDisplayName());
                                                if (condition) {
                                                    json1.put("result", true);
                                                } else {
                                                    json1.put("result", false);
                                                }
                                                json1.put("value", data.getValue());
                                                json1.put("formula", filter.getFormula());
                                                array.put(json1);
                                            } else {
                                                String value = data.getValue();
                                                double i = Double.parseDouble(value);
                                                Boolean condition = (i > 0) & (i <= 6.0);
                                                JSONObject json1 = new JSONObject();
                                                json1.put("displayName", filter.getCreditPolicyMasterEntity().getDisplayName());
                                                if (condition) {
                                                    json1.put("result", true);
                                                } else {
                                                    json1.put("result", false);
                                                }
                                                json1.put("value", data.getValue());
                                                json1.put("formula", filter.getFormula());
                                                array.put(json1);
                                            }
                                        }
                                        if ((filter.getCreditPolicyMasterEntity().getId()).equals(9L)) { // Working Capital Cycle
    //                                int creditPeriod = 1000; //creditNorms
    //                                int creditPeriodValue = Integer.parseInt(creditPeriod);
                                            String value = data.getValue();
                                            BigDecimal i = BigDecimal.valueOf(Long.parseLong(value));
                                            Boolean condition = (i.compareTo(creditPeriod) <= 0);
                                            JSONObject json1 = new JSONObject();
                                            json1.put("displayName", filter.getCreditPolicyMasterEntity().getDisplayName());
                                            if (condition) {
                                                json1.put("result", true);
                                            } else {
                                                json1.put("result", false);
                                            }
                                            json1.put("value", data.getValue());
                                            json1.put("formula", filter.getFormula());
                                            array.put(json1);
                                        }
                                        }
                                }
                            }
                        }
                    }
                }else{
                    List<CreditNormsDetailsEntity> creditNormsDetailsEntities = creditNormsDetailsRepository.findByCreditNorms(custId);
                    for (CreditNormsDetailsEntity creditNormsDetailsEntity1 : creditNormsDetailsEntities) {
                        if (creditNormsDetailsEntity1.getCreditNormsMasterEntity().getId().equals(263L)) {
                            String CreditNorms = creditNormsDetailsEntity1.getValue();
                            double VintageWithAnchorvalue = Double.parseDouble(CreditNorms);
                            double ii = vintageWithAnchor;
                            Boolean condition = (ii >= VintageWithAnchorvalue);
                            JSONObject json1 = new JSONObject();
                            json1.put("displayName", "Vintage with Anchor (in Months)");
                            if (condition) {
                                json1.put("result", true);
                            } else {
                                json1.put("result", false);
                            }
                            json1.put("value", vintageWithAnchor);
//                                json1.put("formula", filter.getFormula());
                            array.put(json1);
                        }
                        if (creditNormsDetailsEntity1.getCreditNormsMasterEntity().getId().equals(266L)) {
                            String CreditNorms = creditNormsDetailsEntity1.getValue();
                            double MinWithAnchorvalue = Double.parseDouble(CreditNorms);
                            //String value1 = data.getValue();
                            double ii = minMonthlyPurchase;
                            Boolean condition = (ii >= MinWithAnchorvalue);
                            JSONObject json1 = new JSONObject();
                            json1.put("displayName", "Min Monthly Purchase from Anchor (Rs in Lakhs)");
                            if (condition) {
                                json1.put("result", true);
                            } else {
                                json1.put("result", false);
                            }
                            json1.put("value", minMonthlyPurchase);
//                                json1.put("formula", filter.getFormula());
                            array.put(json1);
                        }

                    }
                }
                json2.put("proposedPrimaryId",proposedProductsEntity.getId());
                json2.put("status", proposedProductsEntity.getCreditPolicyCheck());
                json2.put("anchorName", anchorName);
                json2.put("data", array);
                array1.put(json2);
                //array1.put(array);
            }
            json.put("creditPolicyResults", array1);
        }catch (Exception e) {
        e.printStackTrace();
    }
        return json.toString();
    }

    @Override
    public CreditPolicyMasterEntity saveCreditPolicyMaster(CreditPolicyMasterData creditPolicyMasterData) {
        CreditPolicyMasterEntity creditPolicyMasterEntity = new CreditPolicyMasterEntity();
        Date date = new Date();
        creditPolicyMasterEntity.setPolicyName(creditPolicyMasterData.getPolicyName());
        creditPolicyMasterEntity.setPolicyType(creditPolicyMasterData.getPolicyType());
        creditPolicyMasterEntity.setDisplayName(creditPolicyMasterData.getDisplayName());
        creditPolicyMasterEntity.setMandatory(creditPolicyMasterData.getMandatory());
        creditPolicyMasterEntity.setStatus(creditPolicyMasterData.getStatus());
        creditPolicyMasterEntity.setDataType(creditPolicyMasterData.getDataType());
        creditPolicyMasterEntity.setRegex(creditPolicyMasterData.getRegex());
        creditPolicyMasterEntity.setCreatedBy(creditPolicyMasterData.getCreatedBy());
        creditPolicyMasterEntity.setCreatedAt(new Timestamp(date.getTime()));
        creditPolicyMasterEntity.setUpdatedBy(creditPolicyMasterData.getUpdatedBy());
        creditPolicyMasterEntity.setUpdatedAt(new Timestamp(date.getTime()));

        return creditPolicyMasterRepository.save(creditPolicyMasterEntity);
    }

    @Override
    public CreditPolicyMasterEntity updateCreditPolicyMaster(CreditPolicyMasterData creditPolicyMasterData, long id) {
        CreditPolicyMasterEntity creditPolicyMasterEntity = creditPolicyMasterRepository.findById(id).get();
        Date date = new Date();
        creditPolicyMasterEntity.setPolicyName(creditPolicyMasterData.getPolicyName());
        creditPolicyMasterEntity.setPolicyType(creditPolicyMasterData.getPolicyType());
        creditPolicyMasterEntity.setDisplayName(creditPolicyMasterData.getDisplayName());
        creditPolicyMasterEntity.setMandatory(creditPolicyMasterData.getMandatory());
        creditPolicyMasterEntity.setStatus(creditPolicyMasterData.getStatus());
        creditPolicyMasterEntity.setDataType(creditPolicyMasterData.getDataType());
        creditPolicyMasterEntity.setRegex(creditPolicyMasterData.getRegex());
        creditPolicyMasterEntity.setCreatedBy(creditPolicyMasterData.getCreatedBy());
        creditPolicyMasterEntity.setCreatedAt(new Timestamp(date.getTime()));
        creditPolicyMasterEntity.setUpdatedBy(creditPolicyMasterData.getUpdatedBy());
        creditPolicyMasterEntity.setUpdatedAt(new Timestamp(date.getTime()));

        return creditPolicyMasterRepository.save(creditPolicyMasterEntity);
    }

    @Override
    public String deleteCreditPolicyMaster(long id) {
        creditPolicyMasterRepository.deleteById(id);
        return "Credit Policy Master " + id + " Delete";
    }
}
