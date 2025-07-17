package org.vcpl.triton.counterParty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.counterParty.data.FundReqDetailsData;
import org.vcpl.triton.counterParty.data.FundReqDetailsMasterData;
import org.vcpl.triton.counterParty.entity.FundReqDetailsEntity;
import org.vcpl.triton.counterParty.repository.CPBasicDetailsRepository;
import org.vcpl.triton.counterParty.repository.FundReqDetailsRepository;
import org.vcpl.triton.counterParty.repository.FundReqMasterRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FundReqDetailsService implements IFundReqDetailsService{

    private static final Logger logger = LoggerFactory.getLogger(FundReqDetailsService.class);


    @Autowired
    private FundReqDetailsRepository fundReqDetailRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CPBasicDetailsRepository cpBasicDeatilsRepository;

    @Autowired
    private FundReqMasterRepository fundRequirementMasterRepository ;

    @Override
    public List<FundReqDetailsEntity> getAllFundReqDetails() {
        return this.fundReqDetailRepository.findAll();
    }

    @Override
    public FundReqDetailsEntity getFundReqDetailsById(long id) {
        logger.info(" | URL | /fundReqDetails | OPERATION | " + "GET fundReqDetail");
        return fundReqDetailRepository.findById(id).orElse(null);
    }

    @Override
    public List<FundReqDetailsEntity> getFundReqDetailsByFId(long appId) {
        return fundReqDetailRepository.findByFId(appId);
    }

    @Override
    public List<Long> saveFundReqDetails(FundReqDetailsMasterData fundReqDetailsMasterData) {
        logger.info(" | URL | /fundReqDetails | OPERATION | " + "POST fundReqDetail");
        List<Long> entityIdLst = new ArrayList<Long>();
        for (FundReqDetailsData fundReqDetailData : fundReqDetailsMasterData.getFundReqDetailsDataList()) {
            entityIdLst.add(fundReqDetailRepository.save(transform(fundReqDetailData)).getId());
        }
            return entityIdLst;
        }

    private FundReqDetailsEntity transform(FundReqDetailsData fundReqDetailData) {
        FundReqDetailsEntity fundReqDetailEntity = new FundReqDetailsEntity();
        Date date = new Date();
        fundReqDetailEntity.setValue(fundReqDetailData.getValue());
        fundReqDetailEntity.setCreatedBy(fundReqDetailData.getCreatedBy());
        fundReqDetailEntity.setCreatedAt(new Timestamp(date.getTime()));
        fundReqDetailEntity.setUpdatedBy(fundReqDetailData.getUpdatedBy());
        fundReqDetailEntity.setUpdatedAt(new Timestamp(date.getTime()));
        fundReqDetailEntity.setFundRequirementMasterEntity(fundRequirementMasterRepository.findById(fundReqDetailData.getCpFrmId()).orElseThrow());
        fundReqDetailEntity.setApplicationEntity(applicationRepository.findById(fundReqDetailData.getAppId()).orElseThrow());
        return fundReqDetailEntity;
    }


    @Override
    public List<Long> updateFundReqDetails(FundReqDetailsMasterData fundReqDetailsMasterData) {
        logger.info(" | URL | /fundReqDetails/{id} | OPERATION | " + "PUT fundReqDetail");
        List<Long> entityIdLst = new ArrayList<Long>();
        for (FundReqDetailsData fundReqDetailData : fundReqDetailsMasterData.getFundReqDetailsDataList()) {
            entityIdLst.add(fundReqDetailRepository.save(updateTransform(fundReqDetailData,fundReqDetailData.getId())).getId());
        }
        return entityIdLst;
    }

    private FundReqDetailsEntity updateTransform(FundReqDetailsData fundReqDetailData, long id) {
        FundReqDetailsEntity fundReqDetailEntity = fundReqDetailRepository.findById(id).get();
        Date date = new Date();
        fundReqDetailEntity.setValue(fundReqDetailData.getValue());
        fundReqDetailEntity.setCreatedBy(fundReqDetailData.getCreatedBy());
        fundReqDetailEntity.setCreatedAt(new Timestamp(date.getTime()));
        fundReqDetailEntity.setUpdatedBy(fundReqDetailData.getUpdatedBy());
        fundReqDetailEntity.setUpdatedAt(new Timestamp(date.getTime()));
        fundReqDetailEntity.setFundRequirementMasterEntity(fundRequirementMasterRepository.getById(fundReqDetailData.getCpFrmId()));
        fundReqDetailEntity.setApplicationEntity(applicationRepository.getById(fundReqDetailData.getAppId()));
        return fundReqDetailEntity;
    }

    @Override
    public String deleteFundReqDetails(long id) {
        logger.info(" | URL | /fundReqDetails/{id} | OPERATION | " + "DELETE fundReqDetail");
        fundReqDetailRepository.deleteById(id);
            return "Credit Policy Master" + id + "Delete";
        }

}
