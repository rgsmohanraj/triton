package org.vcpl.triton.counterParty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vcpl.triton.counterParty.controller.SoftPolicyMasterSubTypeController;
import org.vcpl.triton.counterParty.entity.SoftPolicyMasterSubTypeEntity;
import org.vcpl.triton.counterParty.entity.SoftPolicyMasterTypeEntity;
import org.vcpl.triton.counterParty.repository.SoftPolicyMasterSubTypeRepository;
import org.vcpl.triton.counterParty.repository.SoftPolicyMasterTypeRepository;
import org.vcpl.triton.dms.dmsMaster.exception.ResourceNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SoftPolicyMasterSubTypeService implements ISoftPolicyMasterSubType{

    private static final Logger logger = LoggerFactory.getLogger(SoftPolicyMasterSubTypeService.class);


    @Autowired
    SoftPolicyMasterTypeRepository softPolicyMasterTypeRepository;

    @Autowired
    SoftPolicyMasterSubTypeRepository softPolicyMasterSubTypeRepository;

    @Override
    public List<SoftPolicyMasterSubTypeEntity> getAllSoftPolicyMasterSubType() {
        logger.info(" | URL | /softPolicyMasterSubType | OPERATION | " + "GET softPolicyMasterSubType");
        return softPolicyMasterSubTypeRepository.findAll();
    }

    @Override
    public SoftPolicyMasterSubTypeEntity createSoftPolicyMasterSubType(Long spTypeId, SoftPolicyMasterSubTypeEntity softPolicyMasterSubTypeEntity) {
        logger.info(" | URL | /softPolicyMasterSubType/{spTypeId} | OPERATION | " + "GETById softPolicyMasterSubType");

        Set<SoftPolicyMasterSubTypeEntity> softPolicyMasterSubTypeEntities = new HashSet<>();
        SoftPolicyMasterTypeEntity softPolicyMasterTypeEntity1 = new SoftPolicyMasterTypeEntity();

        Optional<SoftPolicyMasterTypeEntity> byId = softPolicyMasterTypeRepository.findById(spTypeId);
        if (!byId.isPresent()) {
            throw new ResourceNotFoundException("SoftPolicy with id " + spTypeId + " does not exist");
        }
        SoftPolicyMasterTypeEntity softPolicyMasterTypeEntity = byId.get();

        softPolicyMasterSubTypeEntity.setSoftPolicyMasterTypeEntity(softPolicyMasterTypeEntity);

        SoftPolicyMasterSubTypeEntity softPolicyMasterSubTypeEntity1 = softPolicyMasterSubTypeRepository.save(softPolicyMasterSubTypeEntity);

        softPolicyMasterSubTypeEntities.add(softPolicyMasterSubTypeEntity1);

        softPolicyMasterTypeEntity1.setSoftPolicyMasterSubTypeEntities(softPolicyMasterSubTypeEntities);
       return softPolicyMasterSubTypeEntity1;
   }

    @Override
    public Optional<SoftPolicyMasterSubTypeEntity> getSoftPolicyMasterSubTypeById(Long spSubTypeId) {
        logger.info(" | URL | /softPolicyMasterSubType/{spSubTypeId} | OPERATION | " + "GETById softPolicyMasterSubType");

        if (!softPolicyMasterSubTypeRepository.existsById(spSubTypeId)) {
            throw new ResourceNotFoundException("SoftPolicy with id " + spSubTypeId + " not found");
        }
            return softPolicyMasterSubTypeRepository.findById(spSubTypeId);
        }

    @Override
    public SoftPolicyMasterSubTypeEntity updateSoftPolicyMasterSubType(Long spSubTypeId, SoftPolicyMasterSubTypeEntity softPolicyMasterSubTypeRequest) {
        logger.info(" | URL | /softPolicyMasterSubType/{spSubTypeId} | OPERATION | " + "PUT softPolicyMasterSubType");


        if (!softPolicyMasterSubTypeRepository.existsById(spSubTypeId)) {
            throw new ResourceNotFoundException("SoftPolicy with id " + spSubTypeId + " not found");
        }
        Optional<SoftPolicyMasterSubTypeEntity> softPolicyMasterSubTypeEntity = softPolicyMasterSubTypeRepository.findById(spSubTypeId);

        if (!softPolicyMasterSubTypeEntity.isPresent()) {
            throw new ResourceNotFoundException("SoftPolicy with id " + spSubTypeId + " not found");
        }

        SoftPolicyMasterSubTypeEntity softPolicyMasterSubTypeEntity1 = softPolicyMasterSubTypeEntity.get();
        softPolicyMasterSubTypeEntity1.setName(softPolicyMasterSubTypeRequest.getName());
        softPolicyMasterSubTypeEntity1.setDisplayName(softPolicyMasterSubTypeRequest.getDisplayName());
        softPolicyMasterSubTypeEntity1.setSequanceNo(softPolicyMasterSubTypeRequest.getSequanceNo());
        softPolicyMasterSubTypeEntity1.setDataType(softPolicyMasterSubTypeRequest.getDataType());
        softPolicyMasterSubTypeEntity1.setRegex(softPolicyMasterSubTypeRequest.getRegex());
        softPolicyMasterSubTypeEntity1.setMandatory(softPolicyMasterSubTypeRequest.getMandatory());

        return softPolicyMasterSubTypeRepository.save(softPolicyMasterSubTypeEntity1);
    }

    @Override
    public ResponseEntity<Object> deleteSoftPolicyMasterSubTypeById(long spSubTypeId) {
        logger.info(" | URL | /softPolicyMasterSubType/{spSubTypeId} | OPERATION | " + "DELETE softPolicyMasterSubType");

        if (!softPolicyMasterSubTypeRepository.existsById(spSubTypeId)) {
            throw new ResourceNotFoundException("SoftPolicy with id " + spSubTypeId + " not found");
        }

        softPolicyMasterSubTypeRepository.deleteById(spSubTypeId);
            return ResponseEntity.ok().build();
        }
}
