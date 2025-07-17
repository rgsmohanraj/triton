package org.vcpl.triton.anchor.service;


import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.data.AnchorKeyData;
import org.vcpl.triton.anchor.data.AnchorKeyMasterData;
import org.vcpl.triton.anchor.data.ProgramNormsData;
import org.vcpl.triton.anchor.data.ProgramNormsMasterData;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.BeneficiaryEntity;
import org.vcpl.triton.anchor.entity.ProgramNormsEntity;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;
import org.vcpl.triton.anchor.repository.ProgramNormsRepository;

import java.util.*;


@Service
public class ProgramNormsService implements IProgramNorms {

    private static final Logger logger = LoggerFactory.getLogger(ProgramNormsService.class);

    @Autowired
    private ProgramNormsRepository programDetailsRepository;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationService applicationService;


    @Override
    public List<ProgramNormsEntity> getAllProduct() {
        return this.programDetailsRepository.findAll();
    }

    @Override
    public Collection<ProgramNormsEntity> getProgarmDetails(long id) {
        logger.info(" | URL | getProgarmDetails | OPERATION | " + "GETByFid getProgarmDetails");
        return programDetailsRepository.findByCiId(id);
    }

    @Override
    public ProgramNormsEntity getProgramDetailsById(long id) {
        return programDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public String getProgarmDetailsByCustId(long id) {
        logger.info(" | URL | /softPolicyDetails/{id} | OPERATION | " + "GETById softPolicyDetails");
        try {
            Collection<ProgramNormsEntity> programNormsEntities = programDetailsRepository.findByCustId(id);
            JSONObject json = new JSONObject();
            if (programNormsEntities != null && programNormsEntities.size() > 0) {
                JSONArray array = new JSONArray();
                for (ProgramNormsEntity p : programNormsEntities) {
                    JSONObject json1 = new JSONObject();
                    json1.put("subProduct", p.getSubProduct());
                    json1.put("recourseType", p.getTransactionType());
                    json1.put("fundingPercent", p.getFundingPercent());
                    json1.put("interestOwnerShip", p.getInterestOwnerShip());
                    json1.put("graceDays", p.getCounterPartyGracePeriod());
                    json1.put("applicationInterest", p.getInterestAppTye());
                    array.put(json1);
                }
                json.put("programNormsArray", array);
            }
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ProgramNormsEntity saveProgramDetails(ProgramNormsData programNormsData) {
        logger.info(" | URL | saveProgramDetails | OPERATION | " + "saveProgramDetails");
        ProgramNormsEntity programDetailsEntity = programDetailsRepository.save(transform(programNormsData));
        return programDetailsEntity;
    }

    private ProgramNormsEntity transform(ProgramNormsData programNormsData) {
        ProgramNormsEntity programNormsEntity = new ProgramNormsEntity();
        Date date = new Date();

        programNormsEntity.setFundingType(programNormsData.getFundingType());
        programNormsEntity.setOverallProgLimit(programNormsData.getOverallProgLimit());
        programNormsEntity.setRegularProgLimit(programNormsData.getRegularProgLimit());
        programNormsEntity.setAdhocProgLimit(programNormsData.getAdhocProgLimit());
//        programNormsEntity.setAnchorOnboardingDate(new Timestamp(date.getTime()));
        programNormsEntity.setCpMinLimit(programNormsData.getCpMinLimit());
        programNormsEntity.setCpMaxLimit(programNormsData.getCpMaxLimit());
        programNormsEntity.setPricingRoiMax(programNormsData.getPricingRoiMax());
        programNormsEntity.setPricingRoiMin(programNormsData.getPricingRoiMin());
        programNormsEntity.setInterestAppTye(programNormsData.getInterestAppTye());
        programNormsEntity.setInterestOwnerShip(programNormsData.getInterestOwnerShip());
        programNormsEntity.setPenalInterestOwnerShip(programNormsData.getPenalInterestOwnerShip());
        programNormsEntity.setApplicationEntity(applicationRepository.getById(programNormsData.getAppId()));
        programNormsEntity.setPenalInterest(programNormsData.getPenalInterest());
        programNormsEntity.setTenure(programNormsData.getTenure());
        programNormsEntity.setInvoiceAgeing(programNormsData.getInvoiceAgeing());
        programNormsEntity.setDoorToDoor(programNormsData.getDoorToDoor());
        programNormsEntity.setSecurityCovgSecondry(programNormsData.getSecurityCovgSecondry());
        programNormsEntity.setSecurityCovgPrimary(programNormsData.getSecurityCovgSecondry());
        programNormsEntity.setInterestCalculation(programNormsData.getInterestCalculation());
        programNormsEntity.setInterestPaymentFrequency(programNormsData.getInterestPaymentFrequency());
        programNormsEntity.setFundingPercent(programNormsData.getFundingPercent());
        programNormsEntity.setSubProduct(programNormsData.getSubProduct());
//        programNormsEntity.setRepaymentNature(programNormsData.getRepaymentNature());
        programNormsEntity.setRepaymentType(programNormsData.getRepaymentType());
        return programNormsEntity;
////        programDetailsEntity.setSecurityCovgPrimary(programDetailsData.getSecurityCovgPrimary());
////        programDetailsEntity.setSecurityCovgSecondry(programDetailsData.getSecurityCovgSecondry());
//
////        String str="";
////        for(int i=0;i<programNormsData.getSubProduct().length;i++){
////            str+=programNormsData.getSecurityCovgPrimaryArr()[i]+",";
////        }
////        str=str.endsWith(",")?str.substring(0,str.length()-1):str;
////        programNormsEntity.setSecurityCovgPrimary(str);
//
////        String str1="";
////        for(int i=0;i<programNormsData.getSecurityCovgSecondryArr().length;i++){
////            str1+=programNormsData.getSecurityCovgSecondryArr()[i]+",";
////        }
////        str1=str1.endsWith(",")?str1.substring(0,str1.length()-1):str1;
////        programNormsEntity.setSecurityCovgSecondry(str1);
//        programNormsEntity.setProcessingFeesMin(programNormsData.getProcessingFeesMin());
//        programNormsEntity.setProcessingFeesMax(programNormsData.getProcessingFeesMax());
////        programNormsEntity.setInterestCalculation(programNormsData.getInterestCalculation());
//        programNormsEntity.setCounterPartyGracePeriod(programNormsData.getCounterPartyGracePeriod());
//        programNormsEntity.setMaxDrawdown(programNormsData.getMaxDrawdown());
//        programNormsEntity.setStopSupplyTriggerDays(programNormsData.getStopSupplyTriggerDays());
//        programNormsEntity.setRepaymentType(programNormsData.getRepaymentType());
//        programNormsEntity.setRepaymentNature(programNormsData.getRepaymentNature());
//        programNormsEntity.setApplicationEntity(applicationRepository.getById(programNormsData.getAppId()));
////        programDetailsEntity.setProductEntity(productRepository.getById(programDetailsData.getProductId()));
////        programNormsEntity.setCreatedAt(new Timestamp(date.getTime()));
////        programNormsEntity.setCreatedBy(programNormsData.getCreatedBy());
////        programNormsEntity.setUpdatedAt(new Timestamp(date.getTime()));
////        programNormsEntity.setUpdatedBy(programNormsData.getUpdatedBy());
//        return programNormsEntity;
    }


    public ProgramNormsEntity findById(long id, String productName) {
        return programDetailsRepository.fidByAppId(id, productName);
    }


    @Override
    public List<Long> updateProgramDetails(ProgramNormsMasterData programNormsMasterData) {
        String updatedBy = programNormsMasterData.getUpdatedBy();
        String createdBy = programNormsMasterData.getCreatedBy();
        List<Long> entityIdLst = new ArrayList<Long>();
        for (ProgramNormsData programNormsData : programNormsMasterData.getProgramNormsDataList()) {
            JSONObject containerObject = new JSONObject(programNormsData);
            if (containerObject.has("id") || programNormsData.getId() != null) {
                entityIdLst.add(programDetailsRepository.save(updateTransform(programNormsData, updatedBy, createdBy)).getId());
            } else {
                entityIdLst.add(programDetailsRepository.save(saveTransform(programNormsData, createdBy)).getId());
            }
        }
        return entityIdLst;

    }

    @Override
    public List<Long> saveAnchorProgram(ProgramNormsMasterData programNormsMasterData) {
        String createdBy = programNormsMasterData.getCreatedBy();
        List<Long> entityIdLst = new ArrayList<Long>();
        for (ProgramNormsData programNormsData : programNormsMasterData.getProgramNormsDataList()) {
            entityIdLst.add(programDetailsRepository.save(saveTransform(programNormsData, createdBy)).getId());
        }
        return entityIdLst;

    }


    private ProgramNormsEntity saveTransform(ProgramNormsData programNormsData, String createdBy) {
        ProgramNormsEntity programNormsEntity = new ProgramNormsEntity();

        programNormsEntity.setFundingType(programNormsData.getFundingType());
        programNormsEntity.setOverallProgLimit(programNormsData.getOverallProgLimit());
        programNormsEntity.setRegularProgLimit(programNormsData.getRegularProgLimit());
        programNormsEntity.setAdhocProgLimit(programNormsData.getAdhocProgLimit());
        programNormsEntity.setAnchorOnboardingDate(programNormsData.getAnchorOnboardingDate());
        programNormsEntity.setCpMinLimit(programNormsData.getCpMinLimit());
        programNormsEntity.setCpMaxLimit(programNormsData.getCpMaxLimit());
        programNormsEntity.setPricingRoiMax(programNormsData.getPricingRoiMax());
        programNormsEntity.setPricingRoiMin(programNormsData.getPricingRoiMin());
        programNormsEntity.setInterestAppTye(programNormsData.getInterestAppTye());
        programNormsEntity.setInterestOwnerShip(programNormsData.getInterestOwnerShip());
        programNormsEntity.setPenalInterestOwnerShip(programNormsData.getPenalInterestOwnerShip());
        programNormsEntity.setInterestPaymentFrequency(programNormsData.getInterestPaymentFrequency());
        programNormsEntity.setApplicationEntity(applicationRepository.getById(programNormsData.getAppId()));
        programNormsEntity.setPenalInterest(programNormsData.getPenalInterest());
        programNormsEntity.setTenure(programNormsData.getTenure());
        programNormsEntity.setInvoiceAgeing(programNormsData.getInvoiceAgeing());
        programNormsEntity.setDoorToDoor(programNormsData.getDoorToDoor());
        programNormsEntity.setFundingPercent(programNormsData.getFundingPercent());

        programNormsEntity.setSecurityCovgPrimary(programNormsData.getSecurityCovgPrimary());
        programNormsEntity.setSecurityCovgSecondry(programNormsData.getSecurityCovgSecondry());
        programNormsEntity.setProcessingFeesMin(programNormsData.getProcessingFeesMin());
        programNormsEntity.setProcessingFeesMax(programNormsData.getProcessingFeesMax());
        programNormsEntity.setInterestCalculation(programNormsData.getInterestCalculation());
        programNormsEntity.setCounterPartyGracePeriod(programNormsData.getCounterPartyGracePeriod());
        programNormsEntity.setMaxDrawdown(programNormsData.getMaxDrawdown());
        programNormsEntity.setStopSupplyTriggerDays(programNormsData.getStopSupplyTriggerDays());
        programNormsEntity.setRepaymentType(programNormsData.getRepaymentType());
        programNormsEntity.setSubProduct(programNormsData.getSubProduct());
        programNormsEntity.setProgramType(programNormsData.getProgramType());
        programNormsEntity.setTransactionType(programNormsData.getTransactionType());
        programNormsEntity.setInterimReviewFrequency(programNormsData.getInterimReviewFrequency());
        programNormsEntity.setNextInterimReviewOn(programNormsData.getNextInterimReviewOn());
        programNormsEntity.setCmpdInt(programNormsData.getCmpdInt());
        programNormsEntity.setCmpOvdInt(programNormsData.getCmpOvdInt());
        programNormsEntity.setInterestRate(programNormsData.getInterestRate());
        programNormsEntity.setProductExpiry(programNormsData.getProductExpiry());
        return programNormsEntity;
    }


    private ProgramNormsEntity updateTransform(ProgramNormsData programNormsData, String updatedBy, String createdBy) {
        ProgramNormsEntity programNormsEntity = programDetailsRepository.findById(programNormsData.getId()).get();
        programNormsEntity.setFundingType(programNormsData.getFundingType());
        programNormsEntity.setOverallProgLimit(programNormsData.getOverallProgLimit());
        programNormsEntity.setRegularProgLimit(programNormsData.getRegularProgLimit());
        programNormsEntity.setAdhocProgLimit(programNormsData.getAdhocProgLimit());
        programNormsEntity.setAnchorOnboardingDate(programNormsData.getAnchorOnboardingDate());
        programNormsEntity.setCpMinLimit(programNormsData.getCpMinLimit());
        programNormsEntity.setCpMaxLimit(programNormsData.getCpMaxLimit());
        programNormsEntity.setPricingRoiMax(programNormsData.getPricingRoiMax());
        programNormsEntity.setPricingRoiMin(programNormsData.getPricingRoiMin());
        programNormsEntity.setInterestAppTye(programNormsData.getInterestAppTye());
        programNormsEntity.setInterestOwnerShip(programNormsData.getInterestOwnerShip());
        programNormsEntity.setPenalInterestOwnerShip(programNormsData.getPenalInterestOwnerShip());
        programNormsEntity.setInterestPaymentFrequency(programNormsData.getInterestPaymentFrequency());
        programNormsEntity.setApplicationEntity(applicationRepository.getById(programNormsData.getAppId()));
        programNormsEntity.setPenalInterest(programNormsData.getPenalInterest());
        programNormsEntity.setTenure(programNormsData.getTenure());
        programNormsEntity.setInvoiceAgeing(programNormsData.getInvoiceAgeing());
        programNormsEntity.setDoorToDoor(programNormsData.getDoorToDoor());
        programNormsEntity.setFundingPercent(programNormsData.getFundingPercent());
        programNormsEntity.setSecurityCovgPrimary(programNormsData.getSecurityCovgPrimary());
        programNormsEntity.setSecurityCovgSecondry(programNormsData.getSecurityCovgSecondry());
        programNormsEntity.setProcessingFeesMin(programNormsData.getProcessingFeesMin());
        programNormsEntity.setProcessingFeesMax(programNormsData.getProcessingFeesMax());
        programNormsEntity.setInterestCalculation(programNormsData.getInterestCalculation());
        programNormsEntity.setCounterPartyGracePeriod(programNormsData.getCounterPartyGracePeriod());
        programNormsEntity.setMaxDrawdown(programNormsData.getMaxDrawdown());
        programNormsEntity.setStopSupplyTriggerDays(programNormsData.getStopSupplyTriggerDays());
        programNormsEntity.setRepaymentType(programNormsData.getRepaymentType());
        programNormsEntity.setProgramType(programNormsData.getProgramType());
        programNormsEntity.setTransactionType(programNormsData.getTransactionType());
        programNormsEntity.setInterimReviewFrequency(programNormsData.getInterimReviewFrequency());
        programNormsEntity.setNextInterimReviewOn(programNormsData.getNextInterimReviewOn());
        programNormsEntity.setCmpdInt(programNormsData.getCmpdInt());
        programNormsEntity.setCmpOvdInt(programNormsData.getCmpOvdInt());
        programNormsEntity.setInterestRate(programNormsData.getInterestRate());
        programNormsEntity.setProductExpiry(programNormsData.getProductExpiry());
        return programNormsEntity;
    }

    @Override
    public String deleteProgramNorms(long id) {
        programDetailsRepository.deleteById(id);
        return id + "Removed";
    }

    public String getCondition(Long id, String product, Long custId) {

        String result = applicationService.getApplicationIds(custId);
        JSONObject jsonObject = new JSONObject(result);
        JSONArray resultList = jsonObject.getJSONArray("appList");
        JSONArray newId = new JSONArray();
        JSONArray oldId = new JSONArray();
        Long appValueId = 0L;
        for (int i = 0; i < resultList.length(); i++) {
            JSONObject item = resultList.getJSONObject(i);
//            System.out.println("Real Value:" + item);
            newId = item.getJSONArray("newAppId");
            oldId = item.getJSONArray("oldAppId");
        }

        JSONObject idValue = newId.getJSONObject(0);
        appValueId = idValue.getLong("appId");
//        System.out.println("New AppId:" + appValueId);

        JSONObject oldValue = oldId.getJSONObject(0);
        Long oldValueId = oldValue.getLong("appId");
//        System.out.println("Old AppId:" + oldValueId);
        Boolean isDeleted = false;
        JSONObject json=new JSONObject();
        JSONArray array=new JSONArray();
        JSONObject json1=new JSONObject();
        List<ProgramNormsEntity> progNormsList = programDetailsRepository.getByAppId(oldValueId);
        if(progNormsList.size()>0) {
            isDeleted = progNormsList.stream().noneMatch(prog -> prog.getSubProduct().equalsIgnoreCase(product));
        }
//            System.out.println("isDeleted : "+isDeleted);
            if (isDeleted) {
                if(id!=null){
                    programDetailsRepository.deleteByIdAndAppId(id, appValueId);
                }
                json1.put("status",true);
                json1.put("message","success");
                array.put(json1);
            } else {
                json1.put("status",true);
                json1.put("message","fail");
                array.put(json1);
            }
            json.put("result",array);
            return json.toString();
    }
}
