package org.vcpl.triton.counterParty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.vcpl.triton.counterParty.data.DueDiligenceMasterData;
import org.vcpl.triton.counterParty.entity.DueDiligenceMasterEntity;
import org.vcpl.triton.counterParty.repository.DueDiligenceMasterRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class DueDiligenceMasterService implements IDueDiligenceMasterService {

    private static final Logger logger = LoggerFactory.getLogger(DueDiligenceMasterService.class);


    @Autowired
    private DueDiligenceMasterRepository dueDelegenceMasterRepository;

    @Override
    public List<DueDiligenceMasterEntity> getAllProduct() {
        logger.info(" | URL | /dueDiligenceMaster | OPERATION | " + "GET dueDiligenceMaster");
            return this.dueDelegenceMasterRepository.findAll(Sort.by(Sort.Direction.ASC, "sequence"));
        }

    @Override
    public DueDiligenceMasterEntity getdueDiligenceMasterById(Long id)
    {
        logger.info(" | URL | /dueDiligenceMaster/{id} | OPERATION | " + "GETById dueDiligenceMaster");
            return this.dueDelegenceMasterRepository.findById(id).orElse(null);
        }

    @Override
    public DueDiligenceMasterEntity saveDueDiligenceMaster(@RequestBody DueDiligenceMasterData dueDiligenceMasterData) {
        logger.info(" | URL | /dueDiligenceMaster | OPERATION | " + "POST dueDiligenceMaster");
        DueDiligenceMasterEntity dueDiligenceMasterEntity = dueDelegenceMasterRepository.save(transform(dueDiligenceMasterData));
            return dueDiligenceMasterEntity;
        }
        private DueDiligenceMasterEntity transform(DueDiligenceMasterData dueDiligenceMasterData) {
            DueDiligenceMasterEntity dueDiligenceMasterEntity = new DueDiligenceMasterEntity();
            Date date = new Date();
            dueDiligenceMasterEntity.setSequence(dueDiligenceMasterData.getSequence());
            dueDiligenceMasterEntity.setDatatype(dueDiligenceMasterData.getDatatype());
            dueDiligenceMasterEntity.setDescription(dueDiligenceMasterData.getDescription());
            dueDiligenceMasterEntity.setDisplayName(dueDiligenceMasterData.getDisplayName());
            dueDiligenceMasterEntity.setName(dueDiligenceMasterData.getName());
            dueDiligenceMasterEntity.setStatus(dueDiligenceMasterData.getStatus());
            dueDiligenceMasterEntity.setRegex(dueDiligenceMasterData.getRegex());
            dueDiligenceMasterEntity.setCreatedAt(new Timestamp(date.getTime()));
//            dueDelegenceMasterEntity.setCreatedAt(dueDelegenceMasterData.getCreatedAt());
            dueDiligenceMasterEntity.setCreatedBy(dueDiligenceMasterData.getCreatedBy());
            dueDiligenceMasterEntity.setUpdatedAt(new Timestamp(date.getTime()));
//            dueDelegenceMasterEntity.setUpdatedAt(dueDelegenceMasterData.getUpdatedAt());
            dueDiligenceMasterEntity.setUpdatedBy(dueDiligenceMasterData.getUpdatedBy());
            return dueDiligenceMasterEntity;
    }

    @Override
    public DueDiligenceMasterEntity updateduediligenceMaster(DueDiligenceMasterData dueDiligenceMasterData, long id) {
        logger.info(" | URL | /dueDiligenceMaster/{id} | OPERATION | " + "PUT dueDiligenceMaster");
        DueDiligenceMasterEntity dueDiligenceMasterEntity = dueDelegenceMasterRepository.save(updateTransform(dueDiligenceMasterData, id));
            return dueDiligenceMasterEntity;
        }

    private DueDiligenceMasterEntity updateTransform(DueDiligenceMasterData dueDiligenceMasterData, long id) {
        DueDiligenceMasterEntity dueDiligenceMasterEntity = dueDelegenceMasterRepository.findById(id).get();
        Date date = new Date();
        dueDiligenceMasterEntity.setSequence(dueDiligenceMasterData.getSequence());
        dueDiligenceMasterEntity.setDatatype(dueDiligenceMasterData.getDatatype());
        dueDiligenceMasterEntity.setDescription(dueDiligenceMasterData.getDescription());
        dueDiligenceMasterEntity.setDisplayName(dueDiligenceMasterData.getDisplayName());
        dueDiligenceMasterEntity.setName(dueDiligenceMasterData.getName());
        dueDiligenceMasterEntity.setStatus(dueDiligenceMasterData.getStatus());
        dueDiligenceMasterEntity.setRegex(dueDiligenceMasterData.getRegex());
        dueDiligenceMasterEntity.setCreatedAt(new Timestamp(date.getTime()));
//            dueDelegenceMasterEntity.setCreatedAt(dueDelegenceMasterData.getCreatedAt());
        dueDiligenceMasterEntity.setCreatedBy(dueDiligenceMasterData.getCreatedBy());
        dueDiligenceMasterEntity.setUpdatedAt(new Timestamp(date.getTime()));
//            dueDelegenceMasterEntity.setUpdatedAt(dueDelegenceMasterData.getUpdatedAt());
        dueDiligenceMasterEntity.setUpdatedBy(dueDiligenceMasterData.getUpdatedBy());
        return dueDiligenceMasterEntity;
//
//        Date date = new Date();
//        dueDelegenceMasterEntity.setSequence(dueDelegenceMasterData.getSequence());
//        dueDelegenceMasterEntity.setDatatype(dueDelegenceMasterData.getDatatype());
//        dueDelegenceMasterEntity.setDescription(dueDelegenceMasterData.getDescription());
//        dueDelegenceMasterEntity.setDisplayName(dueDelegenceMasterData.getDisplayName());
//        dueDelegenceMasterEntity.setName(dueDelegenceMasterData.getName());
//        dueDelegenceMasterEntity.setStatus(dueDelegenceMasterData.getStatus());
//        dueDelegenceMasterEntity.setCreatedAt(dueDelegenceMasterData.getCreatedAt());
//        dueDelegenceMasterEntity.setCreatedBy(dueDelegenceMasterData.getCreatedBy());
//        dueDelegenceMasterEntity.setUpdatedAt(dueDelegenceMasterData.getUpdatedAt());
//        dueDelegenceMasterEntity.setUpdatedBy(dueDelegenceMasterData.getUpdatedBy());
//        return dueDelegenceMasterEntity;
    }
    @Override
    public String deleteDueDiligenceMaster(long id) {
        logger.info(" | URL | /dueDiligenceMaster/{id} | OPERATION | " + "DELETE dueDiligenceMaster");
        dueDelegenceMasterRepository.deleteById(id);
            return id + "Removed";
        }
}
