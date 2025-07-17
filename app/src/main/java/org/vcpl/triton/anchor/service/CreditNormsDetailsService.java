package org.vcpl.triton.anchor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.data.CreditNormsDetailsData;
import org.vcpl.triton.anchor.data.CreditNormsDetailsMasterData;
import org.vcpl.triton.anchor.entity.CreditNormsDetailsEntity;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.CreditNormsDetailsRepository;
import org.vcpl.triton.anchor.repository.CreditNormsMasterRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CreditNormsDetailsService implements ICreditNormsDetails{

    @Autowired
    private CreditNormsDetailsRepository  creditNormsDetailsRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CreditNormsMasterRepository creditNormsMasterRepository;

    @Override
    public List<CreditNormsDetailsEntity> getCreditNormsDetails() {
        return this.creditNormsDetailsRepository.findAll();
    }

    @Override
    public CreditNormsDetailsEntity getCreditNormsDetailsById(long id) {
        return this.creditNormsDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public List<CreditNormsDetailsEntity> findByIdcreditNormsDetails(long id) {
        return creditNormsDetailsRepository.findByCiIdArray(id);
    }

    @Override
    public List<Long> saveCreditNormsDetails(CreditNormsDetailsMasterData creditNormsDetailsMasterData) {
        List<Long> entityIdLst = new ArrayList<Long>();
        for(CreditNormsDetailsData creditNormsDetailsData : creditNormsDetailsMasterData.getCreditNormsDetailsDataList()) {
            entityIdLst.add(creditNormsDetailsRepository.save(transform(creditNormsDetailsData)).getId());
        }
        return entityIdLst;
    }
    private CreditNormsDetailsEntity transform(CreditNormsDetailsData creditNormsDetailsData) {
        CreditNormsDetailsEntity creditNormsDetailsEntity = new CreditNormsDetailsEntity();
        Date date = new Date();
        creditNormsDetailsEntity.setValue(creditNormsDetailsData.getValue());
        creditNormsDetailsEntity.setCreatedAt(new Timestamp(date.getTime()));
        creditNormsDetailsEntity.setCreatedBy(creditNormsDetailsData.getCreatedBy());
        creditNormsDetailsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        creditNormsDetailsEntity.setUpdatedBy(creditNormsDetailsData.getUpdatedBy());
        creditNormsDetailsEntity.setApplicationEntity(applicationRepository.findById(creditNormsDetailsData.getAppId()).orElseThrow());
        creditNormsDetailsEntity.setCreditNormsMasterEntity(creditNormsMasterRepository.findById(creditNormsDetailsData.getCnMasterId()).orElseThrow());
        return creditNormsDetailsEntity;
    }

    @Override
    public List<Long> updateCreditNormsDetails(CreditNormsDetailsMasterData creditNormsDetailsMasterData) {
        List<Long> entityIdLst = new ArrayList<Long>();
        for(CreditNormsDetailsData creditNormsDetailsData : creditNormsDetailsMasterData.getCreditNormsDetailsDataList()) {
            entityIdLst.add(creditNormsDetailsRepository.save(updateTransform(creditNormsDetailsData,creditNormsDetailsData.getId())).getId());
        }
        return entityIdLst;
    }
    private CreditNormsDetailsEntity updateTransform(CreditNormsDetailsData creditNormsDetailsData, long id) {
        CreditNormsDetailsEntity creditNormsDetailsEntity = creditNormsDetailsRepository.findById(id).get();

        Date date = new Date();
        creditNormsDetailsEntity.setValue(creditNormsDetailsData.getValue());
        creditNormsDetailsEntity.setCreatedAt(new Timestamp(date.getTime()));
        creditNormsDetailsEntity.setCreatedBy(creditNormsDetailsData.getCreatedBy());
        creditNormsDetailsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        creditNormsDetailsEntity.setUpdatedBy(creditNormsDetailsData.getUpdatedBy());
        creditNormsDetailsEntity.setApplicationEntity(applicationRepository.getById(creditNormsDetailsData.getAppId()));
         creditNormsDetailsEntity.setCreditNormsMasterEntity(creditNormsMasterRepository.getReferenceById(creditNormsDetailsData.getCnMasterId()));

        return creditNormsDetailsEntity;
    }

    @Override
    public String deleteCreditNormsDetails(long id) {
        creditNormsDetailsRepository.deleteById(id);
        return id + "Removed";
    }

}
