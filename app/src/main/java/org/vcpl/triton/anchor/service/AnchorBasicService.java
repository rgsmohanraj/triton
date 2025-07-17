package org.vcpl.triton.anchor.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.data.AnchorBasicDetailsData;
import org.vcpl.triton.anchor.entity.AnchorBasicEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.repository.AnchorBasicRepository;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class AnchorBasicService implements IAnchorBasic {
    private static final Logger logger = LoggerFactory.getLogger(AnchorBasicService.class);


    @Autowired
    private AnchorBasicRepository anchorBasicDetailsRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Override
    public List<AnchorBasicEntity> getAllProduct()
    {
        logger.info(" | URL | getAllProduct | OPERATION | "+"GET AnchorBasic");
            return this.anchorBasicDetailsRepository.findAll();
    }

    @Override
    public Collection<AnchorBasicEntity> getanchorBasicDetails(long id) {
        logger.info(" | URL | getanchorBasicDetails | OPERATION | "+"GETByFid AnchorBasic");
            return anchorBasicDetailsRepository.findByCiId(id);
    }

    @Override
    public AnchorBasicEntity saveBasicDetails(AnchorBasicDetailsData anchorBasicDetailsData) {
        logger.info(" | URL | saveBasicDetails | OPERATION | "+"SAVE AnchorBasic");
        AnchorBasicEntity anchorBasicFileEntity = anchorBasicDetailsRepository.save(transform(anchorBasicDetailsData));
        return anchorBasicFileEntity;
    }

    private AnchorBasicEntity transform(AnchorBasicDetailsData anchorBasicDetailsData) {
        AnchorBasicEntity anchorBasicDetailsEntity = new AnchorBasicEntity();
        Date date = new Date();
        anchorBasicDetailsEntity.setApplicationEntity(applicationRepository.findById(anchorBasicDetailsData.getAppId()).orElseThrow());
        Long custId = applicationRepository.findCustIdByAppId(anchorBasicDetailsData.getAppId());
        CustomerInfoEntity customerInfoEntity= customerInfoRepository.getById(custId);
        customerInfoEntity.setCustomerName(anchorBasicDetailsData.getAnchorName());
        customerInfoRepository.save(customerInfoEntity);
        anchorBasicDetailsEntity.setAnchorName(anchorBasicDetailsData.getAnchorName());
        anchorBasicDetailsEntity.setIndustry(anchorBasicDetailsData.getIndustry());
        anchorBasicDetailsEntity.setSector(anchorBasicDetailsData.getSubIndustry());
        anchorBasicDetailsEntity.setCin(anchorBasicDetailsData.getCin());
        anchorBasicDetailsEntity.setIncorpDate(anchorBasicDetailsData.getIncorporationDate());
        anchorBasicDetailsEntity.setPan(anchorBasicDetailsData.getPan());
        anchorBasicDetailsEntity.setActivity(anchorBasicDetailsData.getActivity());
        anchorBasicDetailsEntity.setConstitution(anchorBasicDetailsData.getConstitution());
        anchorBasicDetailsEntity.setBusinessExpiry(anchorBasicDetailsData.getBusinessExpiry());
//        anchorBasicDetailsEntity.setCreatedBy(anchorBasicDetailsData.getCreatedBy());
        anchorBasicDetailsEntity.setCreatedAt(new Timestamp(date.getTime()));
//        anchorBasicDetailsEntity.setCustomerInfoEntity(customerInfoRepository.getById(anchorBasicDetailsData.getCustId()));
        return anchorBasicDetailsEntity;
    }

    @Override
    public AnchorBasicEntity getAnchorDetailsById(long id) {
        return anchorBasicDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public AnchorBasicEntity updateAnchorDetails(AnchorBasicDetailsData anchorBasicDetailsData) {
        AnchorBasicEntity anchorBasicDetailsEntity = anchorBasicDetailsRepository.save(updateTransform(anchorBasicDetailsData));
        return anchorBasicDetailsEntity;

    }


    private AnchorBasicEntity updateTransform(AnchorBasicDetailsData anchorBasicDetailsData) {
        AnchorBasicEntity anchorBasicDetailsEntity = anchorBasicDetailsRepository.findById(anchorBasicDetailsData.getId()).get();
        Date date = new Date();
        Long custId = applicationRepository.findCustIdByAppId(anchorBasicDetailsData.getAppId());
        CustomerInfoEntity customerInfoEntity= customerInfoRepository.getById(custId);
        customerInfoEntity.setCustomerName(anchorBasicDetailsData.getAnchorName());
        customerInfoRepository.save(customerInfoEntity);
        anchorBasicDetailsEntity.setAnchorName(anchorBasicDetailsData.getAnchorName());
        anchorBasicDetailsEntity.setIndustry(anchorBasicDetailsData.getIndustry());
        anchorBasicDetailsEntity.setSector(anchorBasicDetailsData.getSubIndustry());
        anchorBasicDetailsEntity.setCin(anchorBasicDetailsData.getCin());
        anchorBasicDetailsEntity.setIncorpDate(anchorBasicDetailsData.getIncorporationDate());
        anchorBasicDetailsEntity.setPan(anchorBasicDetailsData.getPan());
        anchorBasicDetailsEntity.setActivity(anchorBasicDetailsData.getActivity());
        anchorBasicDetailsEntity.setConstitution(anchorBasicDetailsData.getConstitution());
//        anchorBasicDetailsEntity.setUpdatedBy(anchorBasicDetailsData.getUpdatedBy());
        anchorBasicDetailsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        anchorBasicDetailsEntity.setBusinessExpiry(anchorBasicDetailsData.getBusinessExpiry());
        anchorBasicDetailsEntity.setApplicationEntity(applicationRepository.getById(anchorBasicDetailsData.getAppId()));
        return anchorBasicDetailsEntity;
    }

}
