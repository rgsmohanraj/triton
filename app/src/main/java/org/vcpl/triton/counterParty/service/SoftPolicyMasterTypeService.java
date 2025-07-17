package org.vcpl.triton.counterParty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vcpl.triton.counterParty.entity.SoftPolicyMasterTypeEntity;
import org.vcpl.triton.counterParty.repository.SoftPolicyMasterTypeRepository;
import org.vcpl.triton.dms.dmsMaster.exception.ResourceNotFoundException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SoftPolicyMasterTypeService implements ISoftPolicyMasterType{

    private static final Logger logger = LoggerFactory.getLogger(SoftPolicyMasterTypeService.class);


    @Autowired
    SoftPolicyMasterTypeRepository softPolicyMasterTypeRepository;

    @Override
    public List<SoftPolicyMasterTypeEntity> getsoftPolicyMasterType() {
        logger.info(" | URL | /softPolicyMasterType | OPERATION | " + "GET softPolicyMasterType");
            return softPolicyMasterTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "sequanceNo"));

        }


    @Override
    public SoftPolicyMasterTypeEntity createSoftPolicyMasterType(SoftPolicyMasterTypeEntity softPolicyMasterTypeEntity) {
        logger.info(" | URL | /softPolicyMasterType | OPERATION | " + "POST softPolicyMasterType");


        Date date = new Date();
        softPolicyMasterTypeEntity.setCreatedAt(new Timestamp(date.getTime()));
            return softPolicyMasterTypeRepository.save(softPolicyMasterTypeEntity);
        }

    @Override
    public Optional<SoftPolicyMasterTypeEntity> getSoftPolicyMasterTypeById(Long spTypeId) {
        logger.info(" | URL | /softPolicyMasterType/{spTypeId | OPERATION | " + "GETById softPolicyMasterType");

        if (!softPolicyMasterTypeRepository.existsById(spTypeId)) {
            throw new ResourceNotFoundException("SoftPolicy with id " + spTypeId + " not found");
        }
            return softPolicyMasterTypeRepository.findById(spTypeId);
        }

    @Override
    public SoftPolicyMasterTypeEntity updateDocumentTypeById(Long spTypeId, SoftPolicyMasterTypeEntity softPolicyMasterTypeRequest) {
        logger.info(" | URL | /softPolicyMasterType/{spTypeId} | OPERATION | " + "PUT softPolicyMasterType");

        if (!softPolicyMasterTypeRepository.existsById(spTypeId)) {
            throw new ResourceNotFoundException("SoftPolicy with id " + spTypeId + " not found");
        }
        Optional<SoftPolicyMasterTypeEntity> softPolicyMasterType = softPolicyMasterTypeRepository.findById(spTypeId);

        if (!softPolicyMasterType.isPresent()) {
            throw new ResourceNotFoundException("SoftPolicy with id " + spTypeId + " not found");
        }
        Date date = new Date();

        SoftPolicyMasterTypeEntity softPolicyMasterTypeEntity1 = softPolicyMasterType.get();
        softPolicyMasterTypeEntity1.setName(softPolicyMasterTypeRequest.getName());
        softPolicyMasterTypeEntity1.setDisplayName(softPolicyMasterTypeRequest.getDisplayName());
        softPolicyMasterTypeEntity1.setSequanceNo(softPolicyMasterTypeRequest.getSequanceNo());
        softPolicyMasterTypeEntity1.setUpdatedAt(new Timestamp(date.getTime()));
        return softPolicyMasterTypeRepository.save(softPolicyMasterTypeEntity1);
    }

    public ResponseEntity<Object> deleteSoftPolicyMasterTypeById(long spTypeId) {
        logger.info(" | URL | /softPolicyMasterType/{dtId} | OPERATION | " + "DELETE softPolicyMasterSubType");

        if (!softPolicyMasterTypeRepository.existsById(spTypeId)) {
            throw new ResourceNotFoundException("SoftPolicy with id " + spTypeId + " not found");
        }

        softPolicyMasterTypeRepository.deleteById(spTypeId);
        return ResponseEntity.ok().build();
    }
}
