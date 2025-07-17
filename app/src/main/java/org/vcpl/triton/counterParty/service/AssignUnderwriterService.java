package org.vcpl.triton.counterParty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.counterParty.data.AssignUnderwriterData;
import org.vcpl.triton.counterParty.data.CommercialData;
import org.vcpl.triton.counterParty.entity.AssignUnderwriterEntity;
import org.vcpl.triton.counterParty.entity.CommercialEntity;
import org.vcpl.triton.counterParty.repository.AssignUnderwriterRepository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class AssignUnderwriterService implements IAssignUnderwriter{

    private static final Logger logger = LoggerFactory.getLogger(AssignUnderwriterService.class);

    @Autowired
    private AssignUnderwriterRepository assignUnderwriterRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public AssignUnderwriterEntity saveAssignUnderwriter(AssignUnderwriterData assignUnderwriterData) {
        logger.info(" | URL | /commercialCc | OPERATION | " + "POST CpCommercial");

        AssignUnderwriterEntity assignUnderwriterEntity = assignUnderwriterRepository.save(transform(assignUnderwriterData));
        return assignUnderwriterEntity;
    }

    private AssignUnderwriterEntity transform(AssignUnderwriterData assignUnderwriterData) {
        AssignUnderwriterEntity assignUnderwriterEntity = new AssignUnderwriterEntity();
        Date date = new Date();
        assignUnderwriterEntity.setAssignTo(assignUnderwriterData.getAssignTo());
        assignUnderwriterEntity.setCreatedBy(assignUnderwriterData.getCreatedBy());
        assignUnderwriterEntity.setUpdatedBy(assignUnderwriterData.getUpdatedBy());
        assignUnderwriterEntity.setUpdatedAt(new Timestamp(date.getTime()));
        assignUnderwriterEntity.setCreatedAt(new Timestamp(date.getTime()));
        assignUnderwriterEntity.setApplicationEntity(applicationRepository.findById(assignUnderwriterData.getAppId()).orElseThrow());
        return assignUnderwriterEntity;
    }

    @Override
    public Optional<AssignUnderwriterEntity> getAssignUnderwriter(long id) {
        logger.info(" | URL | assignUnderwriter | OPERATION | "+"GET AssignUnderwriter");
        return assignUnderwriterRepository.findByAppId(id);
    }

    @Override
    public AssignUnderwriterEntity updateAssignUnderwriter(AssignUnderwriterData assignUnderwriterData) {
        AssignUnderwriterEntity assignUnderwriterEntity = assignUnderwriterRepository.save(updateTransform(assignUnderwriterData, assignUnderwriterData.getId()));
        return assignUnderwriterEntity;

    }
    private AssignUnderwriterEntity updateTransform(AssignUnderwriterData assignUnderwriterData, long id) {
        AssignUnderwriterEntity assignUnderwriterEntity = assignUnderwriterRepository.findById(id).get();
        Date date = new Date();
        assignUnderwriterEntity.setAssignTo(assignUnderwriterData.getAssignTo());
        assignUnderwriterEntity.setCreatedBy(assignUnderwriterData.getCreatedBy());
        assignUnderwriterEntity.setUpdatedBy(assignUnderwriterData.getUpdatedBy());
        assignUnderwriterEntity.setUpdatedAt(new Timestamp(date.getTime()));
        assignUnderwriterEntity.setCreatedAt(new Timestamp(date.getTime()));
        assignUnderwriterEntity.setApplicationEntity(applicationRepository.findById(assignUnderwriterData.getAppId()).orElseThrow());
        return assignUnderwriterEntity;
    }
}
