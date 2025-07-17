package org.vcpl.triton.counterParty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.counterParty.controller.FundReqMasterController;
import org.vcpl.triton.counterParty.data.FundReqMasterData;
import org.vcpl.triton.counterParty.entity.FundReqMasterEntity;
import org.vcpl.triton.counterParty.repository.FundReqMasterRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class FundReqMasterService implements IFundReqMasterService {

    private static final Logger logger = LoggerFactory.getLogger(FundReqMasterService.class);


    @Autowired
    private FundReqMasterRepository fundRequirementMasterRepository;


    @Override
    public List<FundReqMasterEntity> getAllProduct() {
        logger.info(" | URL | /fundReqMaster | OPERATION | " + "GET fundRequirementMaster");
        return this.fundRequirementMasterRepository.findAll();
    }

    @Override
    public FundReqMasterEntity getFundReqMasterById(long id) {
        logger.info(" | URL | /fundReqMaster/{id} | OPERATION | " + "GETById fundRequirementMaster");
        return fundRequirementMasterRepository.findById(id).orElse(null);
    }

    @Override
    public FundReqMasterEntity saveFundReqMaster(FundReqMasterData fundRequirementMasterData) {
        logger.info(" | URL | /fundReqMaster | OPERATION | " + "POST fundRequirementMaster");
        FundReqMasterEntity fundRequirementMasterEntity = fundRequirementMasterRepository.save(transform(fundRequirementMasterData));
            return fundRequirementMasterEntity;
        }

    private FundReqMasterEntity transform(FundReqMasterData fundRequirementMasterData) {
        FundReqMasterEntity fundRequirementMasterEntity = new FundReqMasterEntity();
        Date date = new Date();
        fundRequirementMasterEntity.setQuestions(fundRequirementMasterData.getQuestions());
        fundRequirementMasterEntity.setDisplayName(fundRequirementMasterData.getDisplayName());
        fundRequirementMasterEntity.setDataType(fundRequirementMasterData.getDataType());
        fundRequirementMasterEntity.setRequirementName(fundRequirementMasterData.getRequirementName());
        fundRequirementMasterEntity.setDescription(fundRequirementMasterData.getDescription());
        fundRequirementMasterEntity.setStatus(fundRequirementMasterData.getStatus());
        fundRequirementMasterEntity.setCreatedBy(fundRequirementMasterData.getCreatedBy());
        fundRequirementMasterEntity.setCreatedAt(new Timestamp(date.getTime()));
        fundRequirementMasterEntity.setUpdatedBy(fundRequirementMasterData.getUpdatedBy());
        fundRequirementMasterEntity.setUpdatedAt(new Timestamp(date.getTime()));
//        fundRequirementMasterEntity.set(customerInfoFileRepository.getById(anchorAddressData.getCustId()));

        return fundRequirementMasterEntity;
    }


    @Override
    public String deleteFundReqMaster(long id) {
        logger.info(" | URL | /fundReqMaster/{id} | OPERATION | " + "DELETE fundRequirementMaster");
        fundRequirementMasterRepository.deleteById(id);
            return id + "Removed";
        }

    @Override
    public FundReqMasterEntity updateFundReqMaster(FundReqMasterData fundRequirementMasterData, long id) {
        logger.info(" | URL | /fundReqMaster/{id} | OPERATION | " + "PUT fundRequirementMaster");
        FundReqMasterEntity fundRequirementMasterEntity = fundRequirementMasterRepository.save(updateTransform(fundRequirementMasterData,id));
            return fundRequirementMasterEntity;
        }
    private FundReqMasterEntity updateTransform(FundReqMasterData fundRequirementMasterData, long id) {
        FundReqMasterEntity fundRequirementMasterEntity = fundRequirementMasterRepository.findById(id).get();
        Date date = new Date();
        fundRequirementMasterEntity.setQuestions(fundRequirementMasterData.getQuestions());
        fundRequirementMasterEntity.setDisplayName(fundRequirementMasterData.getDisplayName());
        fundRequirementMasterEntity.setDataType(fundRequirementMasterData.getDataType());
        fundRequirementMasterEntity.setRequirementName(fundRequirementMasterData.getRequirementName());
        fundRequirementMasterEntity.setDescription(fundRequirementMasterData.getDescription());
        fundRequirementMasterEntity.setStatus(fundRequirementMasterData.getStatus());
        fundRequirementMasterEntity.setCreatedBy(fundRequirementMasterData.getCreatedBy());
        fundRequirementMasterEntity.setCreatedAt(new Timestamp(date.getTime()));
        fundRequirementMasterEntity.setUpdatedBy(fundRequirementMasterData.getUpdatedBy());
        fundRequirementMasterEntity.setUpdatedAt(new Timestamp(date.getTime()));
        return fundRequirementMasterEntity;
//        termSheetEntity.setCustomerInfoEntity(customerInfoFileRepository.getById(anchorAddressData.getCustId()));
    }


}
