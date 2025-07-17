package org.vcpl.triton.counterParty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.counterParty.data.SoftPolicyFilterMasterData;
import org.vcpl.triton.counterParty.entity.SoftPolicyFilterMasterEntity;
import org.vcpl.triton.counterParty.repository.SoftPolicyFilterMasterRepository;
import org.vcpl.triton.counterParty.repository.SoftPolicyMasterSubTypeRepository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SoftPolicyFilterMasterService implements ISoftPolicyFilterMasterService{

    private static final Logger logger = LoggerFactory.getLogger(SoftPolicyFilterMasterService.class);

    @Autowired
    private SoftPolicyFilterMasterRepository softPolicyFilterMasterRepository;

    @Autowired
    private SoftPolicyMasterSubTypeRepository softPolicyMasterSubTypeRepository;

    @Override
    public List<SoftPolicyFilterMasterEntity> getAllFilters() {
        logger.info(" | URL | /softPolicyFilterMaster | OPERATION | " + "GET softPolicyFilterMaster");
        List<SoftPolicyFilterMasterEntity> softPolicyFilterMasterEntities = softPolicyFilterMasterRepository.findAll();
        return softPolicyFilterMasterEntities;
    }

    @Override
    public SoftPolicyFilterMasterEntity getsoftPolicyFilterMasterById(long id) {
        logger.info(" | URL | /softPolicyFilterMaster/{id} | OPERATION | " + "GETById softPolicyFilterMaster");
        return this.softPolicyFilterMasterRepository.findById(id).orElse(null);
    }

    @Override
    public List<SoftPolicyFilterMasterEntity> findBySoftPolicyFilterType(String type) {
        logger.info(" | URL | /softPolicyFilterMaster/{id} | OPERATION | " + "GETByType softPolicyFilterMaster");
        List<SoftPolicyFilterMasterEntity> softPolicyFilterMasterEntities = this.softPolicyFilterMasterRepository.findByType(type);
        return softPolicyFilterMasterEntities;
    }

    @Override
    public SoftPolicyFilterMasterEntity saveSoftPolicyFilterMaster(SoftPolicyFilterMasterData softPolicyFilterMasterData) {
        logger.info(" | URL | /softPolicyFilterMaster | OPERATION | " + "POST softPolicyFilterMaster");
        SoftPolicyFilterMasterEntity softPolicyFilterMasterEntity = softPolicyFilterMasterRepository.save(transform(softPolicyFilterMasterData));
        return softPolicyFilterMasterEntity;
    }
    private SoftPolicyFilterMasterEntity transform(SoftPolicyFilterMasterData softPolicyFilterMasterData) {
        SoftPolicyFilterMasterEntity softPolicyFilterMasterEntity = new SoftPolicyFilterMasterEntity();
        Date date = new Date();
        softPolicyFilterMasterEntity.setSoftPolicyMasterSubTypeEntity(softPolicyMasterSubTypeRepository.getById(softPolicyFilterMasterData.getSoftPolicySubTypeId()));
//        softPolicyFilterMasterEntity.setSoftPolicySubTypeId(softPolicyFilterMasterData.getSoftPolicySubTypeId());
        softPolicyFilterMasterEntity.setType(softPolicyFilterMasterData.getType());
        softPolicyFilterMasterEntity.setFormula(softPolicyFilterMasterData.getFormula());
        softPolicyFilterMasterEntity.setCreatedAt(new Timestamp(date.getTime()));
        softPolicyFilterMasterEntity.setCreatedBy(softPolicyFilterMasterData.getCreatedBy());
        softPolicyFilterMasterEntity.setUpdatedAt(new Timestamp(date.getTime()));
        softPolicyFilterMasterEntity.setUpdatedBy(softPolicyFilterMasterData.getUpdatedBy());
        return softPolicyFilterMasterEntity;
    }

    @Override
    public SoftPolicyFilterMasterEntity updateSoftPolicyFilterMaster(SoftPolicyFilterMasterData softPolicyFilterMasterData, long id) {
        logger.info(" | URL | /softPolicyFilterMaster/{id} | OPERATION | " + "PUT softPolicyFilterMaster");

        SoftPolicyFilterMasterEntity softPolicyFilterMasterEntity = softPolicyFilterMasterRepository.save(updateTransform(softPolicyFilterMasterData,id));
        return softPolicyFilterMasterEntity;
    }
    private SoftPolicyFilterMasterEntity updateTransform(SoftPolicyFilterMasterData softPolicyFilterMasterData, long id) {
        SoftPolicyFilterMasterEntity softPolicyFilterMasterEntity = softPolicyFilterMasterRepository.findById(id).get();
        Date date = new Date();
        softPolicyFilterMasterEntity.setSoftPolicyMasterSubTypeEntity(softPolicyMasterSubTypeRepository.getById(softPolicyFilterMasterData.getSoftPolicySubTypeId()));
//        softPolicyFilterMasterEntity.setSoftPolicySubTypeId(softPolicyFilterMasterData.getSoftPolicySubTypeId());
        softPolicyFilterMasterEntity.setType(softPolicyFilterMasterData.getType());
        softPolicyFilterMasterEntity.setFormula(softPolicyFilterMasterData.getFormula());
        softPolicyFilterMasterEntity.setCreatedAt(new Timestamp(date.getTime()));
        softPolicyFilterMasterEntity.setCreatedBy(softPolicyFilterMasterData.getCreatedBy());
        softPolicyFilterMasterEntity.setUpdatedAt(new Timestamp(date.getTime()));
        softPolicyFilterMasterEntity.setUpdatedBy(softPolicyFilterMasterData.getUpdatedBy());
        return softPolicyFilterMasterEntity;
    }

    @Override
    public String deleteSoftPolicyFilter(long id) {
        logger.info(" | URL | /softPolicyFilterMaster/{id} | OPERATION | " + "DELETE softPolicyFilterMaster");
        softPolicyFilterMasterRepository.deleteById(id);
        return id + "Removed";
    }
}
