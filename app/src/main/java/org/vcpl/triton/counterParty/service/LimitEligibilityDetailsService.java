package org.vcpl.triton.counterParty.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.entity.ProgramNormsEntity;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;
import org.vcpl.triton.anchor.repository.ProgramNormsRepository;
import org.vcpl.triton.counterParty.data.LimitEligibilityDetailsData;
import org.vcpl.triton.counterParty.data.LimitEligibilityMasterData;
import org.vcpl.triton.counterParty.entity.LimitEligibilityDetailsEntity;
import org.vcpl.triton.counterParty.entity.ProposedProductsEntity;
import org.vcpl.triton.counterParty.repository.CPBasicDetailsRepository;
import org.vcpl.triton.counterParty.repository.LimitEligibilityDetailsRepository;
import org.vcpl.triton.counterParty.repository.ProposedProductsRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class LimitEligibilityDetailsService implements ILimitEligibilityDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(LimitEligibilityDetailsService.class);


    @Autowired
    private LimitEligibilityDetailsRepository limitEligibilityDetailsRepository;

    @Autowired
    private CPBasicDetailsRepository cpBasicDetailsRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    ProposedProductsRepository proposedProductsRepository;

    @Autowired
    ProgramNormsRepository programNormsRepository;

    @Override
    public List<LimitEligibilityDetailsEntity> getLimitEligibilityDetails() {
        logger.info(" | URL | /limitEligibility | OPERATION | " + "GET LimitEligibility");
        return this.limitEligibilityDetailsRepository.findAll();
    }

    @Override
    public LimitEligibilityDetailsEntity getlimitEligibilityDetailsById(Long id) {
        logger.info(" | URL | /limitEligibility/{id} | OPERATION | " + "GETById LimitEligibility");
        return this.limitEligibilityDetailsRepository.findById(id).orElse(null);
    }


    public List<LimitEligibilityDetailsEntity> getTermSheetById(long id) {
        List<LimitEligibilityDetailsEntity> limitEligibilityDetailsEntities = limitEligibilityDetailsRepository.findByAppId(id);
        return  limitEligibilityDetailsEntities;
    }

    public  String  getAnchorList(long id)
    {

        JSONArray array = new JSONArray();
        JSONArray array1 = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        Collection<ProposedProductsEntity> proposedProductsEntities = proposedProductsRepository.findByFId(id);

        for(ProposedProductsEntity proposedProductsEntity :proposedProductsEntities){
            JSONObject json = new JSONObject();
            json.put("productName",proposedProductsEntity.getProduct());
            json.put("customerId",proposedProductsEntity.getCustomerInfoEntity().getId());
            if((proposedProductsEntity.getProduct().equals("Dealer Purchase Order Finance"))||(proposedProductsEntity.getProduct().equals("Vendor Purchase Order Finance"))){
                json.put("status",false);
            }else {
                json.put("status",true);
            }
//            json.put("Customer Id",proposedProductsEntity.getCustomerInfoEntity());
            //CustomerInfoEntity customerId = proposedProductsEntity.getCustomerInfoEntity();
            String anchorName= customerInfoRepository.anchorNameLimitEligibility(proposedProductsEntity.getCustomerInfoEntity().getId());
            ProgramNormsEntity programNorms = programNormsRepository.fidByAppId(proposedProductsEntity.getCustomerInfoEntity().getId(),proposedProductsEntity.getProduct());

            json.put("anchorName",anchorName);

            json.put("tenure",programNorms.getTenure());
            json.put("applicationInterest",programNorms.getInterestAppTye());
            json.put("interestBorneBy",programNorms.getInterestOwnerShip());
            json.put("loginLimitAmount",proposedProductsEntity.getProposed());
            array.put(json);
        }
        array1.put(array);
        JSONObject json1 = new JSONObject();
        json1.put("LimitEligibility", array);
        return json1.toString();
    }

    @Override
    public List<Long> saveLimitEligibilityDetails(LimitEligibilityMasterData limitEligibilityMasterData, long id) {
        logger.info(" | URL | /limitEligibility/{id} | OPERATION | " + "POST LimitEligibility");
        String createdBy = limitEligibilityMasterData.getCreatedBy();
        List<Long> entityIdLst = new ArrayList<Long>();
        for(LimitEligibilityDetailsData limitEligibilityDetailsData : limitEligibilityMasterData.getLimitEligibilityDetailsData()) {
            entityIdLst.add(limitEligibilityDetailsRepository.save(transform(limitEligibilityDetailsData,id,createdBy)).getId());
        }
            return entityIdLst;
        }
    public LimitEligibilityDetailsEntity transform(LimitEligibilityDetailsData limitEligibilityDetailsData, long id,String createdBy) {

        LimitEligibilityDetailsEntity limitEligibilityDetailsEntity = new LimitEligibilityDetailsEntity();
        Date date = new Date();
        limitEligibilityDetailsEntity.setProduct(limitEligibilityDetailsData.getProduct());
        limitEligibilityDetailsEntity.setCurrentLimit(limitEligibilityDetailsData.getCurrentLimit());
        limitEligibilityDetailsEntity.setProposedLimit(limitEligibilityDetailsData.getProposedLimit());
        limitEligibilityDetailsEntity.setEligibleLimit(limitEligibilityDetailsData.getEligibleLimit());
        limitEligibilityDetailsEntity.setAdhocLimit(limitEligibilityDetailsData.getAdhocLimit());
        limitEligibilityDetailsEntity.setCreditPeriod(limitEligibilityDetailsData.getCreditPeriod());
        limitEligibilityDetailsEntity.setDoorToDoor(limitEligibilityDetailsData.getDoortoDoor());
        limitEligibilityDetailsEntity.setInvoiceAgeing(limitEligibilityDetailsData.getInvoiceAgeing());
        limitEligibilityDetailsEntity.setInvoiceAgeing(limitEligibilityDetailsData.getInvoiceAgeing());
        limitEligibilityDetailsEntity.setMargin(limitEligibilityDetailsData.getMargin());
        limitEligibilityDetailsEntity.setExpectedGrowth(limitEligibilityDetailsData.getExpectedGrowth());
        limitEligibilityDetailsEntity.setMonthlyAverage(limitEligibilityDetailsData.getMonthlyAverage());
        limitEligibilityDetailsEntity.setCalculatedLimitWoSetOff(limitEligibilityDetailsData.getCalculatedLimitWoSetOff());
        limitEligibilityDetailsEntity.setApprotionedLimits(limitEligibilityDetailsData.getApprotionedLimits());
        limitEligibilityDetailsEntity.setExistingScfLimits(limitEligibilityDetailsData.getExistingScfLimits());
        limitEligibilityDetailsEntity.setModelLimit(limitEligibilityDetailsData.getModelLimit());
        limitEligibilityDetailsEntity.setCustomerRequestedAmount(limitEligibilityDetailsData.getLoginLimitAmount());
        limitEligibilityDetailsEntity.setAnchorRecommendedAmount(limitEligibilityDetailsData.getAnchorRecommendedAmount());

        limitEligibilityDetailsEntity.setReceivables(limitEligibilityDetailsData.getReceivables());
        limitEligibilityDetailsEntity.setCreditor(limitEligibilityDetailsData.getCreditor());
        limitEligibilityDetailsEntity.setInventory(limitEligibilityDetailsData.getInventory());
        limitEligibilityDetailsEntity.setModelAdhocLimit(limitEligibilityDetailsData.getModelAdhocLimit());
        limitEligibilityDetailsEntity.setExpectedMonthlyTurnOverWithAnchor(limitEligibilityDetailsData.getExpectedMonthlyTurnOverWithAnchor());
//        limitEligibilityDetailsEntity.setCreatedAt(new Timestamp(date.getTime()));
        limitEligibilityDetailsEntity.setCreatedBy(createdBy);
//        limitEligibilityDetailsEntity.setUpdatedAt(new Timestamp(date.getTime()));
//        limitEligibilityDetailsEntity.setUpdatedBy(limitEligibilityDetailsData.getUpdatedBy());
        limitEligibilityDetailsEntity.setApplicationEntity(applicationRepository.findById(id).orElseThrow());
        limitEligibilityDetailsEntity.setCustomerInfoEntity(customerInfoRepository.findById(limitEligibilityDetailsData.getCustId()).orElseThrow());

        return limitEligibilityDetailsEntity;
    }

    @Override
    public List<Long> updateLimitEligibilityDetails(LimitEligibilityMasterData limitEligibilityMasterData) {
        logger.info(" | URL | /limitEligibility/{id} | OPERATION | " + "PUT LimitEligibility");
        List<Long> entityIdLst = new ArrayList<Long>();
        for(LimitEligibilityDetailsData limitEligibilityDetailsData : limitEligibilityMasterData.getLimitEligibilityDetailsData()) {
            entityIdLst.add(limitEligibilityDetailsRepository.save(updateTransform(limitEligibilityDetailsData,limitEligibilityDetailsData.getId())).getId());
        }
        return entityIdLst;
    }
    private LimitEligibilityDetailsEntity updateTransform(LimitEligibilityDetailsData limitEligibilityDetailsData,long id) {
        LimitEligibilityDetailsEntity limitEligibilityDetailsEntity = limitEligibilityDetailsRepository.findById(id).get();

            Date date = new Date();
        limitEligibilityDetailsEntity.setProduct(limitEligibilityDetailsData.getProduct());
        limitEligibilityDetailsEntity.setCurrentLimit(limitEligibilityDetailsData.getCurrentLimit());
        limitEligibilityDetailsEntity.setProposedLimit(limitEligibilityDetailsData.getProposedLimit());
        limitEligibilityDetailsEntity.setEligibleLimit(limitEligibilityDetailsData.getEligibleLimit());
        limitEligibilityDetailsEntity.setAdhocLimit(limitEligibilityDetailsData.getAdhocLimit());
        limitEligibilityDetailsEntity.setCreditPeriod(limitEligibilityDetailsData.getCreditPeriod());
        limitEligibilityDetailsEntity.setDoorToDoor(limitEligibilityDetailsData.getDoortoDoor());
        limitEligibilityDetailsEntity.setInvoiceAgeing(limitEligibilityDetailsData.getInvoiceAgeing());
        limitEligibilityDetailsEntity.setInvoiceAgeing(limitEligibilityDetailsData.getInvoiceAgeing());
        limitEligibilityDetailsEntity.setMargin(limitEligibilityDetailsData.getMargin());
        limitEligibilityDetailsEntity.setExpectedGrowth(limitEligibilityDetailsData.getExpectedGrowth());
        limitEligibilityDetailsEntity.setMonthlyAverage(limitEligibilityDetailsData.getMonthlyAverage());
        limitEligibilityDetailsEntity.setCalculatedLimitWoSetOff(limitEligibilityDetailsData.getCalculatedLimitWoSetOff());
        limitEligibilityDetailsEntity.setApprotionedLimits(limitEligibilityDetailsData.getApprotionedLimits());
        limitEligibilityDetailsEntity.setExistingScfLimits(limitEligibilityDetailsData.getExistingScfLimits());
        limitEligibilityDetailsEntity.setModelLimit(limitEligibilityDetailsData.getModelLimit());
        limitEligibilityDetailsEntity.setCustomerRequestedAmount(limitEligibilityDetailsData.getLoginLimitAmount());
        limitEligibilityDetailsEntity.setAnchorRecommendedAmount(limitEligibilityDetailsData.getAnchorRecommendedAmount());

        limitEligibilityDetailsEntity.setReceivables(limitEligibilityDetailsData.getReceivables());
        limitEligibilityDetailsEntity.setCreditor(limitEligibilityDetailsData.getCreditor());
        limitEligibilityDetailsEntity.setInventory(limitEligibilityDetailsData.getInventory());
        limitEligibilityDetailsEntity.setModelAdhocLimit(limitEligibilityDetailsData.getModelAdhocLimit());
        limitEligibilityDetailsEntity.setExpectedMonthlyTurnOverWithAnchor(limitEligibilityDetailsData.getExpectedMonthlyTurnOverWithAnchor());
        limitEligibilityDetailsEntity.setCreatedAt(new Timestamp(date.getTime()));
//        limitEligibilityDetailsEntity.setCreatedBy(limitEligibilityDetailsData.getCreatedBy());
        limitEligibilityDetailsEntity.setUpdatedAt(new Timestamp(date.getTime()));
//        limitEligibilityDetailsEntity.setUpdatedBy(limitEligibilityDetailsData.getUpdatedBy());
        limitEligibilityDetailsEntity.setApplicationEntity(applicationRepository.getById(limitEligibilityDetailsData.getAppId()));
        limitEligibilityDetailsEntity.setCustomerInfoEntity(customerInfoRepository.getById(limitEligibilityDetailsData.getCustId()));
        return limitEligibilityDetailsEntity;
    }

    @Override
    public String deleteLimitEligibilityDetails(long id) {
        logger.info(" | URL | /limitEligibility/{id} | OPERATION | " + "DELETE LimitEligibility");
        limitEligibilityDetailsRepository.deleteById(id);
            return id + " Removed";
        }
}