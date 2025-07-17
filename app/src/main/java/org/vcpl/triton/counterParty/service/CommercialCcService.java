package org.vcpl.triton.counterParty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.data.BeneficiaryData;
import org.vcpl.triton.anchor.entity.BeneficiaryEntity;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.counterParty.controller.CommercialCcController;
import org.vcpl.triton.counterParty.data.CommercialData;
import org.vcpl.triton.counterParty.data.CommercialMasterData;
import org.vcpl.triton.counterParty.data.TermSheetData;
import org.vcpl.triton.counterParty.data.TermSheetMasterData;
import org.vcpl.triton.counterParty.entity.CommercialEntity;
import org.vcpl.triton.counterParty.repository.CommercialCcRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class CommercialCcService implements ICommercialCc {

    private static final Logger logger = LoggerFactory.getLogger(CommercialCcService.class);


    @Autowired
    CommercialCcRepository commercialCcRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public List<CommercialEntity> getAllCommercialCc() {
        logger.info(" | URL | /commercialCc | OPERATION | " + "GET CpCommercial");
        return this.commercialCcRepository.findAll();
    }

//    @Override
//    public CommercialEntity getAllCommercialCcById(long id) {
//        logger.info(" | URL | /commercialCc/{id} | OPERATION | " + "GETById CpCommercial");
//        return commercialCcRepository.findById(id).orElse(null);
//    }

    @Override
    public Collection<CommercialEntity> getCommercial(long id) {
        logger.info(" | URL | getCommercial | OPERATION | "+"GET CommercialCc");
        return commercialCcRepository.findByCiId(id);
    }

    @Override
    public List<Long> saveCommercialCc(CommercialMasterData commercialMasterData, long id) {
//        logger.info(" | URL | /commercialCc | OPERATION | " + "POST CpCommercial");
//
//        CommercialEntity commercialEntity = commercialCcRepository.save(transform(commercialMasterData));
//        return commercialEntity;

        logger.info(" | URL | /commercialCc | OPERATION | " + "POST CpCommercial");
        String createdBy = commercialMasterData.getCreatedBy();
        List<Long> entityIdLst = new ArrayList<Long>();
        for(CommercialData commercialData : commercialMasterData.getCommercialDataList()) {
            entityIdLst.add(commercialCcRepository.save(transform(commercialData,id,createdBy)).getId());
        }
        return entityIdLst;
    }


    private CommercialEntity transform(CommercialData commercialData, long id, String createdBy) {
        CommercialEntity commercialEntity = new CommercialEntity();
        Date date = new Date();
        commercialEntity.setProductRemarks(commercialData.getProductRemarks());
        commercialEntity.setAnchorNameRemarks(commercialData.getAnchorNameRemarks());
        commercialEntity.setRegularLimitRemarks(commercialData.getRegularLimitRemarks());
        commercialEntity.setAdhocLimitRemarks(commercialData.getAdhocLimitRemarks());
        commercialEntity.setCreditPeriodRemarks(commercialData.getCreditPeriodRemarks());
        commercialEntity.setDoorRemarks(commercialData.getDoorRemarks());
        commercialEntity.setInvoiceAgeingRemarks(commercialData.getInvoiceAgeingRemarks());
        commercialEntity.setMarginRemarks(commercialData.getMarginRemarks());
        commercialEntity.setInterestRateRemarks(commercialData.getInterestRateRemarks());
        commercialEntity.setPfRemarks(commercialData.getPfRemarks());
        commercialEntity.setRenewalRemarks(commercialData.getRenewalRemarks());
        commercialEntity.setInterestTypeRemarks(commercialData.getInterestTypeRemarks());
        commercialEntity.setRenewalPeriodRemarks(commercialData.getRenewalPeriodRemarks());
        commercialEntity.setCreatedAt(new Timestamp(date.getTime()));
        commercialEntity.setCreatedBy(createdBy);
        commercialEntity.setUpdatedAt(new Timestamp(date.getTime()));
//        commercialEntity.setUpdatedBy(commercialData.getUpdatedBy());
        commercialEntity.setApplicationEntity(applicationRepository.findById(id).orElseThrow());
        return commercialEntity;
    }

//    @Override
//    public CommercialEntity updateCommercialDetails(CommercialData commercialData) {
//        CommercialEntity commercialEntity = commercialCcRepository.save(updateTransform(commercialData, commercialData.getId()));
//        return commercialEntity;
//
//    }

    public List<Long> updateCommercialDetails(CommercialMasterData commercialMasterData) {
        logger.info(" | URL | /commercialCc/{id} | OPERATION | " + "PUT commercialCc");

        List<Long> entityIdLst = new ArrayList<Long>();
        for(CommercialData commercialData : commercialMasterData.getCommercialDataList()) {
            entityIdLst.add(commercialCcRepository.save(updateTransform(commercialData,commercialData.getId())).getId());
        }
        return entityIdLst;
    }


    private CommercialEntity updateTransform(CommercialData commercialData, long id) {
        CommercialEntity commercialEntity = commercialCcRepository.findById(id).get();
        Date date = new Date();
        commercialEntity.setApplicationEntity(applicationRepository.getById(commercialData.getAppId()));
        commercialEntity.setProductRemarks(commercialData.getProductRemarks());
        commercialEntity.setAnchorNameRemarks(commercialData.getAnchorNameRemarks());
        commercialEntity.setRegularLimitRemarks(commercialData.getRegularLimitRemarks());
        commercialEntity.setAdhocLimitRemarks(commercialData.getAdhocLimitRemarks());
        commercialEntity.setCreditPeriodRemarks(commercialData.getCreditPeriodRemarks());
        commercialEntity.setDoorRemarks(commercialData.getDoorRemarks());
        commercialEntity.setInvoiceAgeingRemarks(commercialData.getInvoiceAgeingRemarks());
        commercialEntity.setMarginRemarks(commercialData.getMarginRemarks());
        commercialEntity.setInterestRateRemarks(commercialData.getInterestRateRemarks());
        commercialEntity.setPfRemarks(commercialData.getPfRemarks());
        commercialEntity.setRenewalRemarks(commercialData.getRenewalRemarks());
        commercialEntity.setInterestTypeRemarks(commercialData.getInterestTypeRemarks());
        commercialEntity.setRenewalPeriodRemarks(commercialData.getRenewalPeriodRemarks());
        commercialEntity.setApplicationEntity(applicationRepository.findById(commercialData.getAppId()).orElseThrow());
        return commercialEntity;
    }
}