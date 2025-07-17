package org.vcpl.triton.anchor.service;


import org.apache.poi.xssf.extractor.XSSFExportToXml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.data.*;
import org.vcpl.triton.anchor.entity.AnchorAuthorizedEntity;
import org.vcpl.triton.anchor.entity.AnchorBasicEntity;
import org.vcpl.triton.anchor.repository.AnchorAuthorizedRepository;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class AnchorAuthorizedService implements IAnchorAuthorized{

    private static final Logger logger = LoggerFactory.getLogger(AnchorAuthorizedService.class);

    @Autowired
    private AnchorAuthorizedRepository anchorAuthorizedRepository;

    @Autowired
    private ApplicationRepository applicationRepository;



    @Override
    public List<AnchorAuthorizedEntity> getAllProduct()
    {
        return this.anchorAuthorizedRepository.findAll();
    }

    @Override
    public Collection<AnchorAuthorizedEntity> getanchorAuthorized (long id) {
        logger.info(" | URL | getanchorAuthorized | OPERATION | "+"GETByFid AnchorAuthorized");
            return anchorAuthorizedRepository.findByCiId(id);
    }

    @Override
    public List<Long> saveAnchorAuthorized(AnchorAuthorizedMasterData anchorAuthorizedMasterData, long id) {
        int count=1;
        String createdBy = anchorAuthorizedMasterData.getCreatedBy();
        String updatedBy = anchorAuthorizedMasterData.getUpdatedBy();
//        String createdAt = anchorKeyMasterData.getCreatedAt();
        List<Long> entityIdLst = new ArrayList<Long>();
        for(AnchorAuthorizedData anchorAuthorizedData : anchorAuthorizedMasterData.getAnchorAuthDataList()) {
            entityIdLst.add(anchorAuthorizedRepository.save(transform(anchorAuthorizedData,id,  updatedBy,createdBy,count)).getId());
            count++;
        }
        return entityIdLst;
    }
    private AnchorAuthorizedEntity transform(AnchorAuthorizedData anchorAuthorizedData, long id,String CreatedBy ,String UpdatedBy, int count) {
        AnchorAuthorizedEntity anchorAuthorizedEntity = new AnchorAuthorizedEntity();
        anchorAuthorizedEntity.setApplicationEntity(applicationRepository.findById(anchorAuthorizedData.getAppId()).orElseThrow());
        Date date = new Date();
        anchorAuthorizedEntity.setName(anchorAuthorizedData.getName());
        anchorAuthorizedEntity.setMobile(anchorAuthorizedData.getMobile());
        anchorAuthorizedEntity.setEmailId(anchorAuthorizedData.getEmailId());
        anchorAuthorizedEntity.setIndemnityDoc(anchorAuthorizedData.getIndemnityDoc());
        anchorAuthorizedEntity.setType(anchorAuthorizedData.getType());
        anchorAuthorizedEntity.setCreatedBy(anchorAuthorizedData.getCreatedBy());
        anchorAuthorizedEntity.setCreatedAt(new Timestamp(date.getTime()));
        return anchorAuthorizedEntity;
    }

    @Override
    public AnchorAuthorizedEntity getAnchorAuthorizedById(long id) {
        logger.info(" | URL | getAnchorAuthorizedById | OPERATION | "+"GETById AnchorAuthorized");
        return anchorAuthorizedRepository.findById(id).orElse(null);
    }

    @Override
    public List<Long> updateAnchorAuthorizedEntity(AnchorAuthorizedMasterData  anchorAuthorizedMasterData) {
        int count=1;
        String updatedBy = anchorAuthorizedMasterData.getUpdatedBy();
        String createdBy = anchorAuthorizedMasterData.getCreatedBy();
        List<Long> entityIdLst = new ArrayList<Long>();
        for(AnchorAuthorizedData anchorAuthorizedData : anchorAuthorizedMasterData.getAnchorAuthDataList()) {
            entityIdLst.add(anchorAuthorizedRepository.save(updateTransform (anchorAuthorizedData, updatedBy,createdBy,count)).getId());
            count++;
        }
        return entityIdLst;

    }
    private AnchorAuthorizedEntity updateTransform(AnchorAuthorizedData anchorAuthorizedData,String updatedBy ,String createdBy,int count) {
        AnchorAuthorizedEntity anchorAuthorizedEntity = anchorAuthorizedRepository.findById(anchorAuthorizedData.getId()).get();
        anchorAuthorizedEntity.setApplicationEntity(applicationRepository.findById(anchorAuthorizedData.getAppId()).orElseThrow());
        Date date = new Date();
        anchorAuthorizedEntity.setName(anchorAuthorizedData.getName());
        anchorAuthorizedEntity.setMobile(anchorAuthorizedData.getMobile());
        anchorAuthorizedEntity.setEmailId(anchorAuthorizedData.getEmailId());
        anchorAuthorizedEntity.setIndemnityDoc(anchorAuthorizedData.getIndemnityDoc());
        anchorAuthorizedEntity.setType(anchorAuthorizedData.getType());
        anchorAuthorizedEntity.setUpdatedAt(new Timestamp(date.getTime()));
        anchorAuthorizedEntity.setApplicationEntity(applicationRepository.getById(anchorAuthorizedData.getAppId()));
        return anchorAuthorizedEntity;
    }

    @Override
    public String deleteAnchorAuthorized(long id) {
        anchorAuthorizedRepository.deleteById(id);
        return id + "Removed";
    }


}
