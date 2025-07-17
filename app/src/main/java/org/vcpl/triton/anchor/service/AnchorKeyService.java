package org.vcpl.triton.anchor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.data.AnchorKeyData;
import org.vcpl.triton.anchor.data.AnchorKeyMasterData;
import org.vcpl.triton.anchor.entity.AnchorKeyEntity;
import org.vcpl.triton.anchor.repository.AnchorKeyRepository;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service

public class AnchorKeyService implements IAnchorKey{

    private static final Logger logger = LoggerFactory.getLogger(AnchorKeyService.class);

    @Autowired
    private AnchorKeyRepository anchorKeyRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public List<AnchorKeyEntity> getAllProduct()
    {
        return this.anchorKeyRepository.findAll();
    }

    @Override
    public Collection<AnchorKeyEntity> getanchorKey(long id) {
        logger.info(" | URL | getanchorKey | OPERATION | "+" GetByFid AnchorKeyPerson");
            return anchorKeyRepository.findByCiId(id);
    }

    @Override
    public AnchorKeyEntity getAnchorById(Long id) {
        return anchorKeyRepository.findById(id).orElse(null);
    }

    @Override
    public String deleteAnchor(long id) {
        anchorKeyRepository.deleteById(id);
        return " custId " + id + " Deleted ";
    }

    @Override
    public List<Long>  saveAnchorKeyDetails(AnchorKeyMasterData anchorKeyMasterData, long id) {
        int count=1;
        String createdBy = anchorKeyMasterData.getCreatedBy();
        String updatedBy = anchorKeyMasterData.getUpdatedBy();
//        String createdAt = anchorKeyMasterData.getCreatedAt();
        List<Long> entityIdLst = new ArrayList<Long>();
        for(AnchorKeyData anchorKeyData : anchorKeyMasterData.getAnchorKeyDataList()) {
            entityIdLst.add(anchorKeyRepository.save(transform1(anchorKeyData,id,  updatedBy,createdBy,count)).getId());
            count++;
        }
        return entityIdLst;
    }
    private AnchorKeyEntity transform1(AnchorKeyData anchorKeyData, long id, String createdBy,String updatedBy,int count) {
        AnchorKeyEntity anchorKeyEntity = new AnchorKeyEntity();
        anchorKeyEntity.setApplicationEntity(applicationRepository.findById(anchorKeyData.getAppId()).orElseThrow());
        Date date = new Date();
        anchorKeyEntity.setType(anchorKeyData.getType());
        anchorKeyEntity.setName(anchorKeyData.getName());
        anchorKeyEntity.setMobile(anchorKeyData.getMobile());
        anchorKeyEntity.setEmailId(anchorKeyData.getEmailId());
        anchorKeyEntity.setCreatedBy(createdBy);
        anchorKeyEntity.setCreatedAt(new Timestamp(date.getTime()));

        return anchorKeyEntity;
    }


    @Override
    public List<Long>  updateAnchor(AnchorKeyMasterData anchorKeyMasterData) {
        int count=1;
        String updatedBy = anchorKeyMasterData.getUpdatedBy();
        String createdBy = anchorKeyMasterData.getCreatedBy();
//        String updatedAt = anchorKeyMasterData.getUpdatedAt();
        List<Long> entityIdLst = new ArrayList<Long>();
        for(AnchorKeyData anchorKeyData : anchorKeyMasterData.getAnchorKeyDataList()) {
            entityIdLst.add(anchorKeyRepository.save(updateTransform (anchorKeyData, updatedBy,createdBy,count)).getId());
            count++;
        }
        return entityIdLst;
    }



    private AnchorKeyEntity updateTransform (AnchorKeyData anchorKeyData, String updatedBy,String createdBy, int count) {
        AnchorKeyEntity anchorKeyEntity = anchorKeyRepository.findById(anchorKeyData.getId()).get();
        anchorKeyEntity.setApplicationEntity(applicationRepository.findById(anchorKeyData.getAppId()).orElseThrow());
        Date date = new Date();
        anchorKeyEntity.setType(anchorKeyData.getType());
        anchorKeyEntity.setName(anchorKeyData.getName());
        anchorKeyEntity.setMobile(anchorKeyData.getMobile());
        anchorKeyEntity.setEmailId(anchorKeyData.getEmailId());
        anchorKeyEntity.setUpdatedBy(updatedBy);
        anchorKeyEntity.setUpdatedAt(new Timestamp(date.getTime()));
//        anchorKeyEntity.setSequenceNo(count);
        anchorKeyEntity.setApplicationEntity(applicationRepository.getById(anchorKeyData.getAppId()));

        return anchorKeyEntity;

    }

}
