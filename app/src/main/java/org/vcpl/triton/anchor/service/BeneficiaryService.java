package org.vcpl.triton.anchor.service;



import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.data.BeneficiaryData;
import org.vcpl.triton.anchor.entity.BeneficiaryEntity;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.BeneficiaryRepository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BeneficiaryService implements IBeneficiary {

    private static final Logger logger = LoggerFactory.getLogger(BeneficiaryService.class);

    @Autowired
    private BeneficiaryRepository beneficiaryDetailsRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public List<BeneficiaryEntity> getAllProduct()
    {
        return this.beneficiaryDetailsRepository.findAll();
    }

    @Override
    public Collection<BeneficiaryEntity> getBeneficiary(long id) {
        logger.info(" | URL | getBeneficiary | OPERATION | "+"GET AnchorBeneficiary");
            return beneficiaryDetailsRepository.findByCiId(id);
    }

    public String getBankDetails() {
        logger.info(" | URL | getBeneficiary | OPERATION | "+"GET AnchorBeneficiary");
        JSONObject json = new JSONObject();
        List<Object[]> list =beneficiaryDetailsRepository.getAllBankDetails();
        if (list.size() > 0) {
            JSONArray array = new JSONArray();
            for (Object[] obj : list) {
                JSONObject obj1 = new JSONObject();
                obj1.put("bankName", obj[0]);
                obj1.put("id",obj[1]);
                array.put(obj1);
            }
            json.put("bankDetailsList", array);
        }
            return json.toString();
    }

    public String getBankDetailsWithIfsc(String ifsc,String bankCode) {
        JSONObject json = new JSONObject();
        List<Object[]> list =beneficiaryDetailsRepository.getBankDetails(ifsc,bankCode);
        if (list.size() > 0) {
            JSONArray array = new JSONArray();
            for (Object[] obj : list) {
                JSONObject obj1 = new JSONObject();
                obj1.put("status","Yes");
                obj1.put("BBM_CD", obj[1]);
                obj1.put("MAKERID",obj[2]);
                obj1.put("MAKEDATE", obj[3]);
                obj1.put("STATUS",obj[4]);
                obj1.put("AUTHORID", obj[5]);
                obj1.put("AUTHDATE",obj[6]);
                obj1.put("BBM_DESC", obj[7]);
                obj1.put("BBM_SR_NO",obj[9]);
                obj1.put("BBM_SRNO", obj[10]);
                obj1.put("BBM_ADDRESS1",obj[14]);
                obj1.put("BBM_TEL1", obj[19]);
                obj1.put("CG_COUNTRY",obj[30]);
                obj1.put("COMPANYID", obj[32]);
                obj1.put("IFSC_CODE",obj[34]);
                obj1.put("MICRCODE",obj[35]);
                array.put(obj1);
            }
            json.put("bankDetailsList", array);
        }
//        else{
//            JSONArray array = new JSONArray();
//            JSONObject obj1 = new JSONObject();
//            obj1.put("status", "No");
//            array.put(obj1);
//            json.put("bankDetailsList", array);
//        }
        return json.toString();
    }

    public String  getBranchDetails(long id) {
        logger.info(" | URL | getBeneficiary | OPERATION | "+"GET AnchorBeneficiary");
        JSONObject json = new JSONObject();
        List<Object[]> list =  beneficiaryDetailsRepository.getAllBranchDetails(id);
        if (list.size() > 0) {
            JSONArray array = new JSONArray();
            for (Object[] obj : list) {
                JSONObject obj1 = new JSONObject();
                obj1.put("BBM_DESC", obj[0]);
                obj1.put("IFSC_CODE",obj[1]);
                obj1.put("BBM_CD",obj[2]);
                array.put(obj1);
            }
            json.put("branchDetailsList", array);
        }
        return json.toString();
    }

    @Override
    public BeneficiaryEntity saveBeneficiaryDetails(BeneficiaryData beneficiaryData) {
        logger.info(" | URL | saveBeneficiaryDetails | OPERATION | "+"Save AnchorBeneficiary");
            BeneficiaryEntity beneficiaryFileEntity = beneficiaryDetailsRepository.save(transform(beneficiaryData));
            return beneficiaryFileEntity;
    }

    private BeneficiaryEntity transform(BeneficiaryData beneficiaryData) {
        BeneficiaryEntity beneficiaryFileEntity = new BeneficiaryEntity();
        Date date = new Date();
        beneficiaryFileEntity.setApplicationEntity(applicationRepository.findById(beneficiaryData.getAppId()).orElseThrow());
        beneficiaryFileEntity.setBenType(beneficiaryData.getBenType());
        beneficiaryFileEntity.setBenName(beneficiaryData.getBenName());
        beneficiaryFileEntity.setBankName(beneficiaryData.getBankName());
        beneficiaryFileEntity.setBankCode(beneficiaryData.getBankCode());
        beneficiaryFileEntity.setBankAcctNumber(beneficiaryData.getBankAcctNumber());
        beneficiaryFileEntity.setBankifscCode(beneficiaryData.getBankIfscCode());
        beneficiaryFileEntity.setBankBranchCode(beneficiaryData.getBankBranchCode());
        beneficiaryFileEntity.setBankBranchName(beneficiaryData.getBankBranchName());
//        beneficiaryFileEntity.setCreatedAt(new Timestamp(date.getTime()));
//        beneficiaryFileEntity.setCreatedBy(beneficiaryData.getCreatedBy());
//        beneficiaryFileEntity.setUpdatedAt(new Timestamp(date.getTime()));
//        beneficiaryFileEntity.setUpdatedBy(beneficiaryData.getUpdatedBy());
        return beneficiaryFileEntity;
    }


    @Override
    public Optional<BeneficiaryEntity> getBeneficiaryDetailById(long id) {
        return beneficiaryDetailsRepository.findById(id);
    }


    @Override
    public BeneficiaryEntity updateBeneficiaryDetails(BeneficiaryData beneficiaryData) {
        BeneficiaryEntity beneficiaryDetailsEntity = beneficiaryDetailsRepository.save(updateTransform(beneficiaryData, beneficiaryData.getId()));
        return beneficiaryDetailsEntity;

    }

    private BeneficiaryEntity updateTransform(BeneficiaryData beneficiaryData, long id) {
        BeneficiaryEntity beneficiaryFileEntity = beneficiaryDetailsRepository.findById(id).get();
        Date date = new Date();
        beneficiaryFileEntity.setApplicationEntity(applicationRepository.getById(beneficiaryData.getAppId()));
        beneficiaryFileEntity.setBenType(beneficiaryData.getBenType());
        beneficiaryFileEntity.setBenName(beneficiaryData.getBenName());
        beneficiaryFileEntity.setBankName(beneficiaryData.getBankName());
        beneficiaryFileEntity.setBankCode(beneficiaryData.getBankCode());
        beneficiaryFileEntity.setBankAcctNumber(beneficiaryData.getBankAcctNumber());
        beneficiaryFileEntity.setBankifscCode(beneficiaryData.getBankIfscCode());
        beneficiaryFileEntity.setBankBranchCode(beneficiaryData.getBankBranchCode());
        beneficiaryFileEntity.setBankBranchName(beneficiaryData.getBankBranchName());
//        beneficiaryFileEntity.setCreatedAt(new Timestamp( date.getTime()));
//        beneficiaryFileEntity.setCreatedBy(beneficiaryData.getCreatedBy());
//        beneficiaryFileEntity.setUpdatedAt(new Timestamp(date.getTime()));
//        beneficiaryFileEntity.setUpdatedBy(beneficiaryData.getUpdatedBy());
        return beneficiaryFileEntity;
    }

    @Override
    public String deleteBeneficiaryDetails(long id) {
        beneficiaryDetailsRepository.deleteById(id);
        return id + "Removed";
    }

}
