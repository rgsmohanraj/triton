package org.vcpl.triton.counterParty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.counterParty.data.DueDiligenceDetailsData;
import org.vcpl.triton.counterParty.data.DueDiligenceDetailsMasterData;
import org.vcpl.triton.counterParty.entity.DueDiligenceDetailsEntity;
import org.vcpl.triton.counterParty.repository.CPBasicDetailsRepository;
import org.vcpl.triton.counterParty.repository.DueDiligenceMasterRepository;
import org.vcpl.triton.counterParty.repository.DueDiligenceDetailsRepository;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class DueDiligenceDetailsService implements IDueDiligenceDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(DueDiligenceDetailsService.class);

    @Autowired
    private DueDiligenceDetailsRepository dueDiligenceDetailsRepository;


    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private DueDiligenceMasterRepository dueDelegenceMasterRepository;

    @Autowired
    private CPBasicDetailsRepository cpBasicDetailsRepository;

    @Override
    public List<DueDiligenceDetailsEntity> getDueDiligenceDetials() {
        logger.info(" | URL | /dueDiligenceDetails | OPERATION | " + "GET DueDiligenceDetails");
            return this.dueDiligenceDetailsRepository.findAll();
        }


    @Override
    public Collection<DueDiligenceDetailsEntity> getdueDiligenceDetailsById(Long id) {
        logger.info(" | URL | /dueDiligenceDetails | OPERATION | " + "GETById DueDiligenceDetails");
            return  this.dueDiligenceDetailsRepository.findByFid(id);
        }

    @Override
    public List<Long> saveDueDiligenceDetails(DueDiligenceDetailsMasterData dueDiligenceDetailsMasterData) {
        logger.info(" | URL | /dueDiligenceDetails | OPERATION | " + "POST DueDiligenceDetails");
        List<Long> entityIdLst = new ArrayList<Long>();
        for (DueDiligenceDetailsData dueDiligenceDetailsData : dueDiligenceDetailsMasterData.getDueDiligenceDetailsDataList()) {
            entityIdLst.add(dueDiligenceDetailsRepository.save(transform(dueDiligenceDetailsData)).getId());
        }
            return entityIdLst;
        }

    private DueDiligenceDetailsEntity transform(DueDiligenceDetailsData dueDiligenceDetailsData) {
        DueDiligenceDetailsEntity dueDiligenceDetailsEntity = new DueDiligenceDetailsEntity();
        Date date = new Date();
        dueDiligenceDetailsEntity.setValue(dueDiligenceDetailsData.getValue());
        dueDiligenceDetailsEntity.setCreatedAt(new Timestamp(date.getTime()));
        dueDiligenceDetailsEntity.setCreatedBy(dueDiligenceDetailsData.getCreatedBy());
        dueDiligenceDetailsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        dueDiligenceDetailsEntity.setUpdatedBy(dueDiligenceDetailsData.getUpdatedBy());
        dueDiligenceDetailsEntity.setApplicationEntity(applicationRepository.findById(dueDiligenceDetailsData.getAppId()).orElseThrow());
        dueDiligenceDetailsEntity.setDueDiligenceMasterEntity(dueDelegenceMasterRepository.findById(dueDiligenceDetailsData.getDdId()).orElseThrow());
        return dueDiligenceDetailsEntity;
    }

    @Override
    public List<Long> updateDueDiligenceDetails(DueDiligenceDetailsMasterData dueDiligenceDetailsMasterData) {
            List<Long> entityIdLst = new ArrayList<Long>();
            for(DueDiligenceDetailsData dueDiligenceDetailsData : dueDiligenceDetailsMasterData.getDueDiligenceDetailsDataList()) {
                entityIdLst.add(dueDiligenceDetailsRepository.save(updateTransform(dueDiligenceDetailsData,dueDiligenceDetailsData.getId())).getId());
            }
            return entityIdLst;
        }

    private DueDiligenceDetailsEntity updateTransform(DueDiligenceDetailsData dueDiligenceDetailsData, long id) {
        DueDiligenceDetailsEntity dueDiligenceDetailsEntity = dueDiligenceDetailsRepository.findById(id).get();

        Date date = new Date();
        dueDiligenceDetailsEntity.setValue(dueDiligenceDetailsData.getValue());
        dueDiligenceDetailsEntity.setCreatedAt(new Timestamp(date.getTime()));
        dueDiligenceDetailsEntity.setCreatedBy(dueDiligenceDetailsData.getCreatedBy());
        dueDiligenceDetailsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        dueDiligenceDetailsEntity.setUpdatedBy(dueDiligenceDetailsData.getUpdatedBy());
        dueDiligenceDetailsEntity.setApplicationEntity(applicationRepository.getById(dueDiligenceDetailsData.getAppId()));
        dueDiligenceDetailsEntity.setDueDiligenceMasterEntity(dueDelegenceMasterRepository.getReferenceById(dueDiligenceDetailsData.getDdId()));

        return dueDiligenceDetailsEntity;
    }

    @Override
    public String deleteDueDiligenceDetails(long id) {
        logger.info(" | URL | /dueDiligenceDetails/{id} | OPERATION | " + "DELETE DueDiligenceDetails");
        dueDiligenceDetailsRepository.deleteById(id);
            return id + "Removed";
        }
}
