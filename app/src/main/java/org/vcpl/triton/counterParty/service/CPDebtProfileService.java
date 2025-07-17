package org.vcpl.triton.counterParty.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.counterParty.data.CPDebtProfileData;
import org.vcpl.triton.counterParty.data.CPDebtProfileMasterData;
import org.vcpl.triton.counterParty.entity.CPDebtProfileEntity;
import org.vcpl.triton.counterParty.repository.CPBasicDetailsRepository;
import org.vcpl.triton.counterParty.repository.CPDebtProfileRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CPDebtProfileService implements ICPDebtProfile {

    private static final Logger logger = LoggerFactory.getLogger(CPDebtProfileService.class);


    @Autowired
    CPDebtProfileRepository cpDebtProfileRepository;

    @Autowired
    private CPBasicDetailsRepository cpBasicDetailsRepository;

    @Autowired
    private ApplicationRepository applicationRepository;


    @Override
    public List<CPDebtProfileEntity> getAllCPDebtProfile() {
        logger.info(" | URL | /cpDebtProfile | OPERATION | " + "GET cpDebtProfile");
        return this.cpDebtProfileRepository.findAll();
    }

    @Override
    public Collection<CPDebtProfileEntity> findByCpDebtProfile(long id) {
        logger.info(" | URL | /cpDebtProfile/{id} | OPERATION | " + "GETById cpDebtProfile");
            return cpDebtProfileRepository.findByFid(id);
        }

    @Override
    public List<Long> saveCpDebtProfile(CPDebtProfileMasterData cpDebtProfileMasterData, long id) {

        logger.info(" | URL | /cpDebtProfile/{id} | OPERATION | " + "POST cpDebtProfile");

        List<Long> entityIdLst = new ArrayList<Long>();
        for(CPDebtProfileData cpDebtProfileData : cpDebtProfileMasterData.getCpDebtProfileDataList()) {
            entityIdLst.add(cpDebtProfileRepository.save(transform(cpDebtProfileData,id)).getId());
        }
            return entityIdLst;
        }
    private CPDebtProfileEntity transform(CPDebtProfileData cpDebtProfileData, long id) {
        CPDebtProfileEntity cpDebtProfileEntity=new CPDebtProfileEntity();
        cpDebtProfileEntity.setEmi(cpDebtProfileData.getEmi());
        //cpDebtProfileEntity.setSeq(cpDebtProfileData.getSeq());
        cpDebtProfileEntity.setBankFI(cpDebtProfileData.getBankFI());
        cpDebtProfileEntity.setFacilityType(cpDebtProfileData.getFacilityType());
        cpDebtProfileEntity.setTenure(cpDebtProfileData.getTenure());
        cpDebtProfileEntity.setSanctionDate(cpDebtProfileData.getSanctionDate());
        cpDebtProfileEntity.setSanctionLimit(cpDebtProfileData.getSanctionLimit());
        cpDebtProfileEntity.setOutstandingOnDate(cpDebtProfileData.getOutstandingOnDate());
        cpDebtProfileEntity.setEmi(cpDebtProfileData.getEmi());
        cpDebtProfileEntity.setInterestRate(cpDebtProfileData.getInterestRate());
        cpDebtProfileEntity.setSecurity(cpDebtProfileData.getSecurity());
        cpDebtProfileEntity.setSpecificLimit(cpDebtProfileData.getSpecificLimit());
        cpDebtProfileEntity.setCmpdInt("Yes");
        cpDebtProfileEntity.setCmpdOvdInt("No");
        cpDebtProfileEntity.setRemarks(cpDebtProfileData.getRemarks());
        cpDebtProfileEntity.setApplicationEntity(applicationRepository.findById(id).orElseThrow());

        return cpDebtProfileEntity;
    }

    @Override
    public List<Long> updateCpDebtProfile(CPDebtProfileMasterData cpDebtProfileMasterData, Long appId) {
        logger.info(" | URL | /cpDebtProfile/{id} | OPERATION | " + "PUT cpDebtProfile");
        List<Long> entityIdLst = new ArrayList<Long>();
        for(CPDebtProfileData cpDebtProfileData : cpDebtProfileMasterData.getCpDebtProfileDataList()) {
            JSONObject containerObject = new JSONObject(cpDebtProfileData);
            if(containerObject.has("id")){
                entityIdLst.add(cpDebtProfileRepository.save(updateTransform(cpDebtProfileData,cpDebtProfileData.getId())).getId());
            }else {
                entityIdLst.add(cpDebtProfileRepository.save(transform(cpDebtProfileData,appId)).getId());
            }
        }
            return entityIdLst;
    }


    private CPDebtProfileEntity updateTransform(CPDebtProfileData cpDebtProfileData, long id) {
        CPDebtProfileEntity cpDebtProfileEntity=this.cpDebtProfileRepository.findById(id).get();
        cpDebtProfileEntity.setEmi(cpDebtProfileData.getEmi());
        //cpDebtProfileEntity.setSeq(cpDebtProfileData.getSeq());
        cpDebtProfileEntity.setBankFI(cpDebtProfileData.getBankFI());
        cpDebtProfileEntity.setFacilityType(cpDebtProfileData.getFacilityType());
        cpDebtProfileEntity.setTenure(cpDebtProfileData.getTenure());
        cpDebtProfileEntity.setSanctionDate(cpDebtProfileData.getSanctionDate());
        cpDebtProfileEntity.setSanctionLimit(cpDebtProfileData.getSanctionLimit());
        cpDebtProfileEntity.setOutstandingOnDate(cpDebtProfileData.getOutstandingOnDate());
        cpDebtProfileEntity.setInterestRate(cpDebtProfileData.getInterestRate());
        cpDebtProfileEntity.setSecurity(cpDebtProfileData.getSecurity());
        cpDebtProfileEntity.setSpecificLimit(cpDebtProfileData.getSpecificLimit());
        cpDebtProfileEntity.setCmpdInt("Yes");
        cpDebtProfileEntity.setCmpdOvdInt("No");
        cpDebtProfileEntity.setRemarks(cpDebtProfileData.getRemarks());
        cpDebtProfileEntity.setApplicationEntity(applicationRepository.getById(cpDebtProfileData.getAppId()));
        return cpDebtProfileEntity;
    }


    @Override
    public String deleteCPDebtProfile(long id) {
        logger.info(" | URL | /cpDebtProfile/{id} | OPERATION | " + "DELETE cpDebtProfile");

        cpDebtProfileRepository.deleteById(id);
            return id + " Successfully Deleted";
        }

    @Override
    public String deleteDebtProfile(long id) {
        logger.info(" | URL | /cpDebtProfile/{id} | OPERATION | " + "DELETE cpDebtProfile");
        cpDebtProfileRepository.deleteDebtProfile(id);
        return "success";
    }
}
