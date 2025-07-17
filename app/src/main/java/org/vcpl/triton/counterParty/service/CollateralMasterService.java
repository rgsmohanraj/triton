package org.vcpl.triton.counterParty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.vcpl.triton.counterParty.data.CollateralMasterData;
import org.vcpl.triton.counterParty.entity.CollateralMasterEntity;
import org.vcpl.triton.counterParty.repository.CollateralMasterRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class CollateralMasterService implements ICollateralMasterService {
    private static final Logger logger = LoggerFactory.getLogger(CollateralMasterService.class);


    @Autowired
    private CollateralMasterRepository collateralMasterRepository;

    @Override
    public List<CollateralMasterEntity> getAllProduct()
    {
        logger.info(" | URL | /collateralMaster | OPERATION | " + "GET collateralMaster");

            return this.collateralMasterRepository.findAll(Sort.by(Sort.Direction.ASC, "sequence"));
        }

    @Override
    public CollateralMasterEntity getCollateralMasterById(Long id)
    {
        logger.info(" | URL | /collateralMaster/{id} | OPERATION | " + "GETById collateralMaster");
        return this.collateralMasterRepository.findById(id).orElse(null);
    }

    @Override
    public CollateralMasterEntity saveCollateralMaster(@RequestBody CollateralMasterData collateralMasterData) {
        logger.info(" | URL | /collateralMaster | OPERATION | " + "POST collateralMaster");

        CollateralMasterEntity collateralMasterEntity = collateralMasterRepository.save(transform(collateralMasterData));
            return collateralMasterEntity;
        }
    private CollateralMasterEntity transform(CollateralMasterData collateralMasterData) {
        CollateralMasterEntity collateralMasterEntity = new CollateralMasterEntity();
        Date date = new Date();
        collateralMasterEntity.setSequence(collateralMasterData.getSequence());
        collateralMasterEntity.setDatatype(collateralMasterData.getDatatype());
        collateralMasterEntity.setDescription(collateralMasterData.getDescription());
        collateralMasterEntity.setDisplayName(collateralMasterData.getDisplayName());
        collateralMasterEntity.setName(collateralMasterData.getName());
        collateralMasterEntity.setStatus(collateralMasterData.getStatus());
        collateralMasterEntity.setRegex(collateralMasterData.getRegex());
        collateralMasterEntity.setCreatedAt(new Timestamp(date.getTime()));
//            dueDelegenceMasterEntity.setCreatedAt(dueDelegenceMasterData.getCreatedAt());
        collateralMasterEntity.setCreatedBy(collateralMasterData.getCreatedBy());
        collateralMasterEntity.setUpdatedAt(new Timestamp(date.getTime()));
//            dueDelegenceMasterEntity.setUpdatedAt(dueDelegenceMasterData.getUpdatedAt());
        collateralMasterEntity.setUpdatedBy(collateralMasterData.getUpdatedBy());
        return collateralMasterEntity;
    }

    @Override
    public CollateralMasterEntity updateCollateralMaster(CollateralMasterData collateralMasterData, long id) {
        logger.info(" | URL | /collateralMaster/{id} | OPERATION | " + "PUT collateralMaster");

        CollateralMasterEntity collateralMasterEntity = collateralMasterRepository.save(updateTransform(collateralMasterData,id));
            return collateralMasterEntity;
        }

    private CollateralMasterEntity updateTransform(CollateralMasterData collateralMasterData, long id) {
        CollateralMasterEntity collateralMasterEntity = collateralMasterRepository.findById(id).get();
        Date date = new Date();
        collateralMasterEntity.setSequence(collateralMasterData.getSequence());
        collateralMasterEntity.setDatatype(collateralMasterData.getDatatype());
        collateralMasterEntity.setDescription(collateralMasterData.getDescription());
        collateralMasterEntity.setDisplayName(collateralMasterData.getDisplayName());
        collateralMasterEntity.setName(collateralMasterData.getName());
        collateralMasterEntity.setStatus(collateralMasterData.getStatus());
        collateralMasterEntity.setRegex(collateralMasterData.getRegex());
        collateralMasterEntity.setCreatedAt(new Timestamp(date.getTime()));
//            dueDelegenceMasterEntity.setCreatedAt(dueDelegenceMasterData.getCreatedAt());
        collateralMasterEntity.setCreatedBy(collateralMasterData.getCreatedBy());
        collateralMasterEntity.setUpdatedAt(new Timestamp(date.getTime()));
//            dueDelegenceMasterEntity.setUpdatedAt(dueDelegenceMasterData.getUpdatedAt());
        collateralMasterEntity.setUpdatedBy(collateralMasterData.getUpdatedBy());
        return collateralMasterEntity;
    }
    @Override
    public String deleteCollateralMaster(long id) {
        logger.info(" | URL | /collateralMaster/{id} | OPERATION | " + "DELETE collateralMaster");

        collateralMasterRepository.deleteById(id);
            return id + "Removed";
        }
}
