package org.vcpl.triton.counterParty.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;
import org.vcpl.triton.counterParty.data.CommercialData;
import org.vcpl.triton.counterParty.data.CpBeneficiaryData;
import org.vcpl.triton.counterParty.entity.CPBasicDetailsEntity;
import org.vcpl.triton.counterParty.entity.CommercialEntity;
import org.vcpl.triton.counterParty.entity.CpBeneficiaryEntity;
import org.vcpl.triton.counterParty.repository.CpBeneficiaryRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class CpBeneficiaryService  implements ICpBeneficaryService{

    private static final Logger logger = LoggerFactory.getLogger(CpBeneficiaryService.class);

    @Autowired
    private CpBeneficiaryRepository cpBeneficiaryRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;


    @Override
    public CpBeneficiaryEntity getCPBeneficiaryDetailsById(long id) {
        logger.info(" | URL | /cpBeneficiaryDetails/{id} | OPERATION | " + "GETById cpBasicDetails");
        try {
            return cpBeneficiaryRepository.getById(id);
        }catch (Exception ex){
            logger.error(" | URL | /cpBeneficiaryDetails/{id} | OPERATION | " + " Error |" + ex.getMessage());
        }
        return null;
    }

    @Override
    public CpBeneficiaryEntity saveCpBeneficiaryDetails(CpBeneficiaryData cpBeneficiaryData) {
        logger.info(" | URL | /cpBeneficiary | OPERATION | " + "POST cpBeneficiary");

        CpBeneficiaryEntity cpBeneficiaryEntity = cpBeneficiaryRepository.save(transform(cpBeneficiaryData));
        return cpBeneficiaryEntity;
    }
    private CpBeneficiaryEntity transform(CpBeneficiaryData cpBeneficiaryData) {
        CpBeneficiaryEntity cpBeneficiaryEntity = new CpBeneficiaryEntity();
        Date date = new Date();
        cpBeneficiaryEntity.setCpBeneficiaryType(cpBeneficiaryData.getCpBeneficiaryType());
        cpBeneficiaryEntity.setCpBeneficiaryName(cpBeneficiaryData.getCpBeneficiaryName());
        cpBeneficiaryEntity.setCpBeneficiaryAcct(cpBeneficiaryData.getCpBeneficiaryAcct());
        cpBeneficiaryEntity.setCpBeneficiaryAcctType(cpBeneficiaryData.getCpBeneficiaryAcctType());
        cpBeneficiaryEntity.setCpBeneficiaryIfsc(cpBeneficiaryData.getCpBeneficiaryIfsc());
        cpBeneficiaryEntity.setCpBeneficiaryBankName(cpBeneficiaryData.getCpBeneficiaryBankName());
        cpBeneficiaryEntity.setCpBeneficiaryBranch(cpBeneficiaryData.getCpBeneficiaryBranch());
        cpBeneficiaryEntity.setCpBeneficiaryMicr(cpBeneficiaryData.getCpBeneficiaryMicr());
        cpBeneficiaryEntity.setCpBeneficiarySwiftCode(cpBeneficiaryData.getCpBeneficiarySwiftCode());
        cpBeneficiaryEntity.setApplicationEntity(applicationRepository.findById(cpBeneficiaryData.getAppId()).orElseThrow());
        return cpBeneficiaryEntity;
    }

    @Override
    public CpBeneficiaryEntity updateCpBeneficiaryDetails(CpBeneficiaryData cpBeneficiaryData) {
        CpBeneficiaryEntity cpBeneficiaryEntity = cpBeneficiaryRepository.save(updateTransform(cpBeneficiaryData,cpBeneficiaryData.getId()));
        return cpBeneficiaryEntity;
    }
    private CpBeneficiaryEntity updateTransform(CpBeneficiaryData cpBeneficiaryData, long id) {
        CpBeneficiaryEntity cpBeneficiaryEntity = cpBeneficiaryRepository.findById(id).get();

        Date date = new Date();
        cpBeneficiaryEntity.setCpBeneficiaryType(cpBeneficiaryData.getCpBeneficiaryType());
        cpBeneficiaryEntity.setCpBeneficiaryName(cpBeneficiaryData.getCpBeneficiaryName());
        cpBeneficiaryEntity.setCpBeneficiaryAcct(cpBeneficiaryData.getCpBeneficiaryAcct());
        cpBeneficiaryEntity.setCpBeneficiaryAcctType(cpBeneficiaryData.getCpBeneficiaryAcctType());
        cpBeneficiaryEntity.setCpBeneficiaryIfsc(cpBeneficiaryData.getCpBeneficiaryIfsc());
        cpBeneficiaryEntity.setCpBeneficiaryBankName(cpBeneficiaryData.getCpBeneficiaryBankName());
        cpBeneficiaryEntity.setCpBeneficiaryBranch(cpBeneficiaryData.getCpBeneficiaryBranch());
        cpBeneficiaryEntity.setCpBeneficiaryMicr(cpBeneficiaryData.getCpBeneficiaryMicr());
        cpBeneficiaryEntity.setCpBeneficiarySwiftCode(cpBeneficiaryData.getCpBeneficiarySwiftCode());
        cpBeneficiaryEntity.setApplicationEntity(applicationRepository.findById(cpBeneficiaryData.getAppId()).orElseThrow());
        return cpBeneficiaryEntity;
    }

}
