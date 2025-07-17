package org.vcpl.triton.counterParty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.counterParty.data.CollateralDetailsData;
import org.vcpl.triton.counterParty.data.CollateralDetailsMasterData;
import org.vcpl.triton.counterParty.entity.CollateralDetailsEntity;
import org.vcpl.triton.counterParty.repository.CPBasicDetailsRepository;
import org.vcpl.triton.counterParty.repository.CollateralDetailsRepository;
import org.vcpl.triton.counterParty.repository.CollateralMasterRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class CollateralDetailsService implements ICollateralDetailsService{

    private static final Logger logger = LoggerFactory.getLogger(CollateralDetailsService.class);


    @Autowired
    private CollateralDetailsRepository collateralDetailsRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CPBasicDetailsRepository cpBasicDetailsRepository;

    @Autowired
    private CollateralMasterRepository collateralMasterRepository;

    @Override
    public List<CollateralDetailsEntity> getCollateralDetails()
    {
        logger.info(" | URL | /collateralDetails | OPERATION | " + "GET collateralDetails");
        return this.collateralDetailsRepository.findAll();

        }
    @Override
    public CollateralDetailsEntity getCollateralDetailsById(Long id) {
        logger.info(" | URL | /collateralDetails/{id} | OPERATION | " + "GETById collateralDetails");
        return this.collateralDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<CollateralDetailsEntity> getCollateralById(Long id) {
        logger.info(" | URL | /collateral/{id} | OPERATION | " + "GETById collateralDetails");
        return this.collateralDetailsRepository.findByFId(id);
    }


    @Override
    public List<Long> saveCollateralDetails(CollateralDetailsMasterData collateralDetailsMasterData) {
        logger.info(" | URL | /collateralDetails | OPERATION | " + "POST collateralDetails");

        List<Long> entityIdLst = new ArrayList<Long>();
        for(CollateralDetailsData collateralDetailsData : collateralDetailsMasterData.getCollateralDetailsDataList()) {
            entityIdLst.add(collateralDetailsRepository.save(transform(collateralDetailsData)).getId());
        }
            return entityIdLst;
        }
    private CollateralDetailsEntity transform(CollateralDetailsData collateralDetailsData) {
        CollateralDetailsEntity collateralDetailsEntity = new CollateralDetailsEntity();
        Date date = new Date();
        collateralDetailsEntity.setValue(collateralDetailsData.getValue());
        collateralDetailsEntity.setCreatedAt(new Timestamp(date.getTime()));
        collateralDetailsEntity.setCreatedBy(collateralDetailsData.getCreatedBy());
        collateralDetailsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        collateralDetailsEntity.setUpdatedBy(collateralDetailsData.getUpdatedBy());
        collateralDetailsEntity.setApplicationEntity(applicationRepository.findById(collateralDetailsData.getAppId()).orElseThrow());
        collateralDetailsEntity.setCollateralMasterEntity(collateralMasterRepository.findById(collateralDetailsData.getCmId()).orElseThrow());
        return collateralDetailsEntity;
    }

    @Override
    public List<Long> updateCollateralDetails(CollateralDetailsMasterData collateralDetailsMasterData) {
        logger.info(" | URL | /collateralDetails | OPERATION | " + "PUT collateralDetails");

        List<Long> entityIdLst = new ArrayList<Long>();
        for(CollateralDetailsData collateralDetailsData : collateralDetailsMasterData.getCollateralDetailsDataList()) {
            entityIdLst.add(collateralDetailsRepository.save(updateTransform(collateralDetailsData,collateralDetailsData.getId())).getId());
        }
        return entityIdLst;
    }
    private CollateralDetailsEntity updateTransform(CollateralDetailsData collateralDetailsData, long id) {
        CollateralDetailsEntity collateralDetailsEntity = collateralDetailsRepository.findById(id).get();

        Date date = new Date();
        collateralDetailsEntity.setValue(collateralDetailsData.getValue());
        collateralDetailsEntity.setCreatedAt(new Timestamp(date.getTime()));
        collateralDetailsEntity.setCreatedBy(collateralDetailsData.getCreatedBy());
        collateralDetailsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        collateralDetailsEntity.setUpdatedBy(collateralDetailsData.getUpdatedBy());
        collateralDetailsEntity.setApplicationEntity(applicationRepository.getById(collateralDetailsData.getAppId()));
        collateralDetailsEntity.setCollateralMasterEntity(collateralMasterRepository.getReferenceById(collateralDetailsData.getCmId()));

        return collateralDetailsEntity;
    }

//    @Override
//    public CollateralDetailsEntity updateCollateralDetails(CollateralDetailsData collateralDetailsData, long id) {
//        logger.info(" | URL | /collateralDetails/{id} | OPERATION | " + "PUT collateralDetails");
//
//        CollateralDetailsEntity dueDiligenceDetailsEntity = collateralDetailsRepository.save(updateTransform(collateralDetailsData,id));
//        return dueDiligenceDetailsEntity;
//    }
//    private CollateralDetailsEntity updateTransform(CollateralDetailsData collateralDetailsData, long id) {
//        CollateralDetailsEntity collateralDetailsEntity = collateralDetailsRepository.findById(id).get();
//
//        Date date = new Date();
//        collateralDetailsEntity.setValue(collateralDetailsData.getValue());
//        collateralDetailsEntity.setCreatedAt(new Timestamp(date.getTime()));
//        collateralDetailsEntity.setCreatedBy(collateralDetailsData.getCreatedBy());
//        collateralDetailsEntity.setUpdatedAt(new Timestamp(date.getTime()));
//        collateralDetailsEntity.setUpdatedBy(collateralDetailsData.getUpdatedBy());
//        collateralDetailsEntity.setApplicationEntity(applicationRepository.getById(collateralDetailsData.getAppId()));
//        collateralDetailsEntity.setCollateralMasterEntity(collateralMasterRepository.getReferenceById(collateralDetailsData.getCmId()));
//
//        return collateralDetailsEntity;
//    }
//
    @Override
    public String deleteCollateralDetails(long id) {
        logger.info(" | URL | /collateralDetails/{id} | OPERATION | " + "PUT collateralDetails");
        collateralDetailsRepository.deleteById(id);
            return id + "Removed";
        }
}
