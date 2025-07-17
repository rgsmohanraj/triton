package org.vcpl.triton.counterParty.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;
import org.vcpl.triton.counterParty.data.TermSheetData;
import org.vcpl.triton.counterParty.data.TermSheetMasterData;
import org.vcpl.triton.counterParty.entity.TermSheetEntity;
import org.vcpl.triton.counterParty.repository.CPBasicDetailsRepository;
import org.vcpl.triton.counterParty.repository.TermSheetRepository;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TermSheetService implements TermsSheetServiceI {
    private static final Logger logger = LoggerFactory.getLogger(TermSheetService.class);


    @Autowired
    private TermSheetRepository termSheetRepository;

    @Autowired
    private CPBasicDetailsRepository cpBasicDetailsRepository;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public List<TermSheetEntity> getAllProduct()
    {
        logger.info(" | URL | /termSheet | OPERATION | " + "GET termSheet");
        return this.termSheetRepository.findAll();

    }


    @Override
    public List<TermSheetEntity> getTermSheetById(long id) {
        logger.info(" | URL | /termSheetGet/{id} | OPERATION | " + "GETById termSheet");

        List<TermSheetEntity> termSheetEntities = termSheetRepository.findByAppId(id);
            return termSheetEntities;
        }

    @Override
    public TermSheetEntity getAddressDetailsById(long id) {
        logger.info(" | URL | /termSheet/{id} | OPERATION | " + "GETById termSheet");
        return termSheetRepository.findById(id).orElse(null);
    }

    @Override
    public List<Long> saveTermSheet(TermSheetMasterData termSheetMasterData,long id) {
        logger.info(" | URL | /termSheet/{id} | OPERATION | " + "POST termSheet");
            String createdBy = termSheetMasterData.getCreatedBy();
        List<Long> entityIdLst = new ArrayList<Long>();
        for(TermSheetData termSheetData : termSheetMasterData.getTermSheetDataList()) {
            entityIdLst.add(termSheetRepository.save(transform(termSheetData,id,createdBy)).getId());
        }
            return entityIdLst;
        }

    private TermSheetEntity transform(TermSheetData termSheetData,long id,String createdBy) {
        TermSheetEntity termSheetEntity = new TermSheetEntity();
        Date date = new Date();
        termSheetEntity.setProduct(termSheetData.getProduct());
        termSheetEntity.setRegularLimit(termSheetData.getRegularLimit());
        termSheetEntity.setAdhocLimit(termSheetData.getAdhocLimit());
        termSheetEntity.setCreditPeriod(termSheetData.getCreditPeriod());
        termSheetEntity.setDoorToDoor(termSheetData.getDoorToDoor());
        termSheetEntity.setInvoiceAgeing(termSheetData.getInvoiceAgeing());
        termSheetEntity.setMargin(termSheetData.getMargin());
        termSheetEntity.setInterestRate(termSheetData.getInterestRate());
        termSheetEntity.setPf(termSheetData.getPf());
        termSheetEntity.setRenewal(termSheetData.getRenewal());
        termSheetEntity.setInterestRateType(termSheetData.getInterestRateType());
        termSheetEntity.setRenewalPeriod(termSheetData.getRenewalPeriod());
        termSheetEntity.setApplyOfInterest(termSheetData.getApplyOfInterest());
        termSheetEntity.setInterestBorneBy(termSheetData.getInterestBorneBy());
        termSheetEntity.setPenaltyBorneBy(termSheetData.getPenaltyBorneBy());
//        termSheetEntity.setRepymntFrom(termSheetData.getRepymntFrom());
        termSheetEntity.setInvoiceFunding(termSheetData.getInvoiceFunding());
        termSheetEntity.setGraceDays(termSheetData.getGraceDays());
        termSheetEntity.setCreatedBy(createdBy);
        termSheetEntity.setCreatedAt(new Timestamp(date.getTime()));
//        termSheetEntity.setUpdatedBy(termSheetData.getUpdatedBy());
        termSheetEntity.setUpdatedAt(new Timestamp(date.getTime()));
        termSheetEntity.setApplicationEntity(applicationRepository.findById(id).orElseThrow());
        termSheetEntity.setCustomerInfoEntity(customerInfoRepository.findById(termSheetData.getCustId()).orElseThrow());

        return termSheetEntity;
    }


    @Override
    public String deleteTermSheet(long id) {
        logger.info(" | URL | /termSheet/{id} | OPERATION | " + "DELETE termSheet");

        termSheetRepository.deleteById(id);
            return id + "Removed";
        }

    @Override
    public List<Long> updateTermSheet(TermSheetMasterData termSheetMasterData) {
        logger.info(" | URL | /termSheet/{id} | OPERATION | " + "PUT termSheet");

        List<Long> entityIdLst = new ArrayList<Long>();
        for(TermSheetData termSheetData : termSheetMasterData.getTermSheetDataList()) {
            entityIdLst.add(termSheetRepository.save(updateTransform(termSheetData,termSheetData.getId())).getId());
        }
            return entityIdLst;
        }

    private TermSheetEntity updateTransform(TermSheetData termSheetData, long id) {
        TermSheetEntity termSheetEntity = termSheetRepository.findById(id).get();
        Date date = new Date();
        termSheetEntity.setProduct(termSheetData.getProduct());
        termSheetEntity.setRegularLimit(termSheetData.getRegularLimit());
        termSheetEntity.setAdhocLimit(termSheetData.getAdhocLimit());
        termSheetEntity.setCreditPeriod(termSheetData.getCreditPeriod());
        termSheetEntity.setDoorToDoor(termSheetData.getDoorToDoor());
        termSheetEntity.setInvoiceAgeing(termSheetData.getInvoiceAgeing());
        termSheetEntity.setMargin(termSheetData.getMargin());
        termSheetEntity.setInterestRate(termSheetData.getInterestRate());
        termSheetEntity.setPf(termSheetData.getPf());
        termSheetEntity.setRenewal(termSheetData.getRenewal());
        termSheetEntity.setInterestRateType(termSheetData.getInterestRateType());
        termSheetEntity.setRenewalPeriod(termSheetData.getRenewalPeriod());
        termSheetEntity.setApplyOfInterest(termSheetData.getApplyOfInterest());
        termSheetEntity.setInterestBorneBy(termSheetData.getInterestBorneBy());
        termSheetEntity.setPenaltyBorneBy(termSheetData.getPenaltyBorneBy());
//        termSheetEntity.setRepymntFrom(termSheetData.getRepymntFrom());
        termSheetEntity.setInvoiceFunding(termSheetData.getInvoiceFunding());
        termSheetEntity.setGraceDays(termSheetData.getGraceDays());
//        termSheetEntity.setCreatedBy(createdBy);
        termSheetEntity.setCreatedAt(new Timestamp(date.getTime()));
//        termSheetEntity.setUpdatedBy(termSheetData.getUpdatedBy());
        termSheetEntity.setUpdatedAt(new Timestamp(date.getTime()));
        termSheetEntity.setApplicationEntity(applicationRepository.getById(termSheetData.getAppId()));
        termSheetEntity.setCustomerInfoEntity(customerInfoRepository.getById(termSheetData.getCustId()));
        return termSheetEntity;
    }

    }
