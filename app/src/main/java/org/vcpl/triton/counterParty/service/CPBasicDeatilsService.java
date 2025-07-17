package org.vcpl.triton.counterParty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;
import org.vcpl.triton.counterParty.data.CPBasicDetailsData;
import org.vcpl.triton.counterParty.entity.CPBasicDetailsEntity;
import org.vcpl.triton.counterParty.repository.CPBasicDetailsRepository;

import java.util.Collection;

@Service
public class CPBasicDeatilsService implements ICPBasicDetails {

    private static final Logger logger = LoggerFactory.getLogger(CPBasicDeatilsService.class);


    @Autowired
    CPBasicDetailsRepository cpBasicDetailsRepository;


    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;




    @Override
    public CPBasicDetailsEntity getCPDetails(long id) {
        logger.info(" | URL | /cpBasicDetails | OPERATION | " + "GET cpBasicDetails");
         return this.cpBasicDetailsRepository.findCpBasicDetails(id);
    }

    @Override
    public Collection<CPBasicDetailsEntity> getCPBasicDetailsById(long id) {
        logger.info(" | URL | /cpBasicDetails/{id} | OPERATION | " + "GETById cpBasicDetails");
        try {
            return cpBasicDetailsRepository.findByFId(id);
        }catch (Exception ex){
            logger.error(" | URL | /cpBasicDetails/{id} | OPERATION | " + " Error |" + ex.getMessage());
        }
        return null;
    }



    @Override
    public ApplicationEntity saveCPDetails(CPBasicDetailsData cpBasicDetail) {
        logger.info(" | URL | cpBasicDetails | OPERATION | " + "POST cpBasicDetails");

        CustomerInfoEntity customerInfoEntity = new CustomerInfoEntity();
        customerInfoEntity.setCustomerName(cpBasicDetail.getCompanyName());
        customerInfoEntity.setCin(cpBasicDetail.getCinNumber());
        customerInfoEntity.setPan(cpBasicDetail.getPanNumber());
        customerInfoEntity.setProduct("SCF");
        customerInfoEntity.setDedupeStatus("In-Active");
//        customerInfoEntity.setFacilityTenure(cpBasicDetail.getFacilityTenure());
        customerInfoEntity = customerInfoRepository.save(customerInfoEntity);


        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setCustomerInfoEntity(customerInfoRepository.getById(customerInfoEntity.getId()));
        applicationEntity.setAppType(1);
        applicationEntity.setType(2);
        applicationEntity.setSeqNo(1);
        applicationEntity = applicationRepository.save(applicationEntity);

        CPBasicDetailsEntity cpBasicDetails=cpBasicDetailsRepository.save(transform(cpBasicDetail,applicationEntity.getId()));
            return applicationEntity;
        }
    private CPBasicDetailsEntity transform(CPBasicDetailsData cpData,long id){
        CPBasicDetailsEntity cpDetails=new CPBasicDetailsEntity();
        cpDetails.setPanNumber(cpData.getPanNumber());
        cpDetails.setCompanyName(cpData.getCompanyName());
        cpDetails.setGstNumber(cpData.getGstNumber());
        cpDetails.setCinNumber(cpData.getCinNumber());
        cpDetails.setConstitution(cpData.getConstitution());
        cpDetails.setCity(cpData.getCity());
        cpDetails.setState(cpData.getState());
        cpDetails.setSource(cpData.getSource());
        cpDetails.setSubSource(cpData.getSubSource());
        cpDetails.setRmName(cpData.getRmName());
        cpDetails.setCusContName(cpData.getCusContName());
        cpDetails.setCusContactNumber(cpData.getCusContactNumber());
        cpDetails.setCusContactEmail(cpData.getCusContactEmail());
        cpDetails.setActivity(cpData.getActivity());
        cpDetails.setApplicationEntity(applicationRepository.findById(id).orElseThrow());
//        cpDetails.setApplicationEntity(applicationRepository.getById(cpData.getAppId()));

        return cpDetails;
    }

    @Override
    public CPBasicDetailsEntity saveRenewalBasicDetails(CPBasicDetailsData cpBasicDetailsData) {
        CPBasicDetailsEntity cpBasicDetailsEntity = new CPBasicDetailsEntity();

        Long custId = applicationRepository.findCustIdByAppId(cpBasicDetailsData.getAppId());
        CustomerInfoEntity customerInfoEntity = customerInfoRepository.getById(custId);
        customerInfoEntity.setCustomerName(cpBasicDetailsData.getCompanyName());
//        customerInfoEntity.setFacilityTenure(cpBasicDetailsData.getFacilityTenure());
        customerInfoRepository.save(customerInfoEntity);

        cpBasicDetailsEntity.setPanNumber(cpBasicDetailsData.getPanNumber());
        cpBasicDetailsEntity.setCompanyName(cpBasicDetailsData.getCompanyName());
        cpBasicDetailsEntity.setGstNumber(cpBasicDetailsData.getGstNumber());
        cpBasicDetailsEntity.setCinNumber(cpBasicDetailsData.getCinNumber());
        cpBasicDetailsEntity.setConstitution(cpBasicDetailsData.getConstitution());
        cpBasicDetailsEntity.setCity(cpBasicDetailsData.getCity());
        cpBasicDetailsEntity.setState(cpBasicDetailsData.getState());
        cpBasicDetailsEntity.setSource(cpBasicDetailsData.getSource());
        cpBasicDetailsEntity.setSubSource(cpBasicDetailsData.getSubSource());
        cpBasicDetailsEntity.setRmName(cpBasicDetailsData.getRmName());
        cpBasicDetailsEntity.setCusContName(cpBasicDetailsData.getCusContName());
        cpBasicDetailsEntity.setCusContactNumber(cpBasicDetailsData.getCusContactNumber());
        cpBasicDetailsEntity.setCusContactEmail(cpBasicDetailsData.getCusContactEmail());
        cpBasicDetailsEntity.setActivity(cpBasicDetailsData.getActivity());
        cpBasicDetailsEntity.setAssessmentType(cpBasicDetailsData.getAssessmentType());
        cpBasicDetailsEntity.setCounterPartyType(cpBasicDetailsData.getCounterPartyType());
        cpBasicDetailsEntity.setApplicationEntity(applicationRepository.findById(cpBasicDetailsData.getAppId()).orElseThrow());

        return cpBasicDetailsRepository.save(cpBasicDetailsEntity);
    }

    @Override
    public String deleteCPBasicDetails(long id) {
        logger.info(" | URL | /cpBasicDetails/{id} | OPERATION | " + "DELETE cpBasicDetails");

        cpBasicDetailsRepository.deleteById(id);
            return id + " Successfully Deleted".toString();
        }

    @Override
    public CPBasicDetailsEntity updateCPBasicDetails(CPBasicDetailsData cpBasicDetailsData) {
        logger.info(" | URL | /cpBasicDetails/{id} | OPERATION | " + "PUT cpBasicDetails");

        CustomerInfoEntity customerInfoEntity = customerInfoRepository.findById(cpBasicDetailsData.getCustId()).get();
        customerInfoEntity.setCustomerName(cpBasicDetailsData.getCompanyName());
//        customerInfoEntity.setFacilityTenure(cpBasicDetailsData.getFacilityTenure());
//        customerInfoEntity.setCin(cpBasicDetailsData.getCinNumber());
//        customerInfoEntity.setPan(cpBasicDetailsData.getPanNumber());
//        customerInfoEntity.setProduct("SCF");
//        customerInfoEntity.setStatus("Active");
        customerInfoRepository.save(customerInfoEntity);
//
//
//        ApplicationEntity applicationEntity = applicationRepository.findById(cpBasicDetailsData.getAppId()).get();
//        applicationEntity.setCustomerInfoEntity(customerInfoRepository.getById(customerInfoEntity.getId()));
//        applicationEntity.setAppType(1);
//        applicationEntity.setType(2);
//        applicationEntity = applicationRepository.save(applicationEntity);

        CPBasicDetailsEntity cpBasicDetails=cpBasicDetailsRepository.save(updateTransform(cpBasicDetailsData,cpBasicDetailsData.getId()));
        return cpBasicDetails;
        }

    private CPBasicDetailsEntity updateTransform(CPBasicDetailsData data, long id) {
        CPBasicDetailsEntity cpData=this.cpBasicDetailsRepository.findById(id).get();

        cpData.setPanNumber(data.getPanNumber());
        cpData.setCompanyName(data.getCompanyName());
        cpData.setGstNumber(data.getGstNumber());
        cpData.setCinNumber(data.getCinNumber());
        cpData.setConstitution(data.getConstitution());
        cpData.setCity(data.getCity());
        cpData.setState(data.getState());
        cpData.setSource(data.getSource());
        cpData.setSubSource(data.getSubSource());
        cpData.setRmName(data.getRmName());
        cpData.setCusContName(data.getCusContName());
        cpData.setCusContactNumber(data.getCusContactNumber());
        cpData.setCusContactEmail(data.getCusContactEmail());
        cpData.setActivity(data.getActivity());
        cpData.setAssessmentType(data.getAssessmentType());
        cpData.setAnchorRelationShip(data.getAnchorRelationShip());
        cpData.setTotalInwardCheques(data.getTotalInwardCheques());
        cpData.setCounterPartyType(data.getCounterPartyType());
        cpData.setApplicationEntity(applicationRepository.getById(data.getAppId()));
        return cpData;
    }
}
