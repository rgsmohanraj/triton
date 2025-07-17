package org.vcpl.triton.anchor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.data.CreditNormsMasterData;
import org.vcpl.triton.anchor.entity.CreditNormsMasterEntity;
import org.vcpl.triton.anchor.repository.CreditNormsMasterRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class CreditNormsMasterService implements ICreditNormsMaster{

    @Autowired
    private CreditNormsMasterRepository creditNormsMasterRepository;

    @Override
    public List<CreditNormsMasterEntity> getAllCreditNormsMaster() {
        return this.creditNormsMasterRepository.findAll(Sort.by(Sort.Direction.ASC, "sequence"));
    }

    @Override
    public CreditNormsMasterEntity findCreditNormsMasterById(long id) {
        return this.creditNormsMasterRepository.findById(id).orElse(null);
    }

    @Override
    public CreditNormsMasterEntity saveCreditNormsMaster(CreditNormsMasterData creditNormsMasterData) {
        CreditNormsMasterEntity creditNormsMasterEntity = creditNormsMasterRepository.save(transform(creditNormsMasterData));
        return creditNormsMasterEntity;
    }
    private CreditNormsMasterEntity transform(CreditNormsMasterData creditNormsMasterData) {
        CreditNormsMasterEntity creditNormsMasterEntity = new CreditNormsMasterEntity();
        Date date = new Date();
        creditNormsMasterEntity.setSequence(creditNormsMasterData.getSequence());
        creditNormsMasterEntity.setName(creditNormsMasterData.getName());
        creditNormsMasterEntity.setDatatype(creditNormsMasterData.getDatatype());
        creditNormsMasterEntity.setStatus(creditNormsMasterData.getStatus());
        creditNormsMasterEntity.setDisplayName(creditNormsMasterData.getDisplayName());
        creditNormsMasterEntity.setCreatedBy("Balaji");
        creditNormsMasterEntity.setCreatedAt(new Timestamp(date.getTime()));
        creditNormsMasterEntity.setUpdatedBy("Balaji");
        creditNormsMasterEntity.setUpdatedAt(new Timestamp(date.getTime()));
        return creditNormsMasterEntity;
    }

    @Override
    public CreditNormsMasterEntity updateCreditNormsMaster(CreditNormsMasterData creditNormsMasterData, long id) {
        CreditNormsMasterEntity creditNormsMasterEntity = creditNormsMasterRepository.save(updateTransform(creditNormsMasterData,id));
        return creditNormsMasterEntity;
    }
    private CreditNormsMasterEntity updateTransform(CreditNormsMasterData creditNormsMasterData, long id) {
        CreditNormsMasterEntity creditNormsMasterEntity = creditNormsMasterRepository.findById(id).get();
        Date date = new Date();
        creditNormsMasterEntity.setSequence(creditNormsMasterData.getSequence());
        creditNormsMasterEntity.setName(creditNormsMasterData.getName());
        creditNormsMasterEntity.setDatatype(creditNormsMasterData.getDatatype());
        creditNormsMasterEntity.setStatus(creditNormsMasterData.getStatus());
        creditNormsMasterEntity.setDisplayName(creditNormsMasterData.getDisplayName());
        creditNormsMasterEntity.setCreatedBy("Balaji");
        creditNormsMasterEntity.setCreatedAt(new Timestamp(date.getTime()));
        creditNormsMasterEntity.setUpdatedBy("Balaji");
        creditNormsMasterEntity.setUpdatedAt(new Timestamp(date.getTime()));
        return creditNormsMasterEntity;
    }

    @Override
    public String deleteCreditNormsMaster(long id) {
        creditNormsMasterRepository.deleteById(id);
        return id + "Removed";
    }
}

