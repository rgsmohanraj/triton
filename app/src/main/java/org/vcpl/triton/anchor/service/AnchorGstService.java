package org.vcpl.triton.anchor.service;


import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.data.AnchorGstDetailsData;
import org.vcpl.triton.anchor.data.AnchorGstMasterData;
import org.vcpl.triton.anchor.entity.AnchorGstEntity;
import org.vcpl.triton.anchor.repository.AnchorGstRepository;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class AnchorGstService implements IAnchorGst{

    private static final Logger logger = LoggerFactory.getLogger(AnchorGstService.class);

    @Autowired
    private AnchorGstRepository anchorGstDetailsRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
        public List<AnchorGstEntity> getAllProduct ()
        {
            return this.anchorGstDetailsRepository.findAll();
        }

    @Override
    public Collection<AnchorGstEntity> getanchorGstDetails(long id) {
        logger.info(" | URL | getanchorGstDetails | OPERATION | "+"GET AnchorAddress");
            return anchorGstDetailsRepository.findByCiId(id);
    }


    @Override
    public List<Long>  saveAnchorGstDetails(AnchorGstMasterData anchorGstMasterData, long id) {

        String createdBy = anchorGstMasterData.getCreatedBy();
//        String createdAt = anchorGstMasterData.getCreatedAt();
        List<Long> entityIdLst = new ArrayList<Long>();
        for(AnchorGstDetailsData anchorGstDetailsData : anchorGstMasterData.getAnchorGstDetailsDataList()) {
            entityIdLst.add(anchorGstDetailsRepository.save(transform1(anchorGstDetailsData,id, createdBy)).getId());
        }
        return entityIdLst;
    }

    private AnchorGstEntity transform1(AnchorGstDetailsData anchorGstDetailsData, long id, String createdBy) {
        AnchorGstEntity anchorGstDetailsEntity = new AnchorGstEntity();
        anchorGstDetailsEntity.setApplicationEntity(applicationRepository.findById(anchorGstDetailsData.getAppId()).orElseThrow());
        Date date = new Date();
        anchorGstDetailsEntity.setAddressLine1(anchorGstDetailsData.getAddressLine1());
        anchorGstDetailsEntity.setAddressLine2(anchorGstDetailsData.getAddressLine2());
        anchorGstDetailsEntity.setCity(anchorGstDetailsData.getCity());
        anchorGstDetailsEntity.setCountry(anchorGstDetailsData.getCountry());
        anchorGstDetailsEntity.setPincode(anchorGstDetailsData.getPinCode());
        anchorGstDetailsEntity.setState(anchorGstDetailsData.getState());
//        anchorGstDetailsEntity.setCreatedBy(createdBy);
        anchorGstDetailsEntity.setCreatedAt(new Timestamp(date.getTime()));
        anchorGstDetailsEntity.setGstNumber(anchorGstDetailsData.getGstNumber());
//        anchorGstDetailsEntity.setCustomerInfoEntity(customerInfoRepository.getById(anchorGstDetailsData.getCustId()));
        anchorGstDetailsEntity.setGstAcctHolderName(anchorGstDetailsData.getGstAccntHolderName());
        return anchorGstDetailsEntity;
    }

    @Override
    public String deleteAnchorDetails(long id) {
        anchorGstDetailsRepository.deleteById(id);
        return id + "Removed";
    }

    @Override
    public List<Long>  updateAnchorGst (AnchorGstMasterData anchorGstMasterData) {
        String updatedBy = anchorGstMasterData.getUpdatedBy();
//    String updatedAt = anchorGstMasterData.getUpdatedAt();
        List<Long> entityIdLst = new ArrayList<Long>();
        for(AnchorGstDetailsData anchorGstDetailsData : anchorGstMasterData.getAnchorGstDetailsDataList()) {
            entityIdLst.add(anchorGstDetailsRepository.save(updateTransform(anchorGstDetailsData,updatedBy)).getId());
        }
        return entityIdLst;
    }


    private AnchorGstEntity updateTransform(AnchorGstDetailsData anchorGstDetailsData, String createdBy) {
        AnchorGstEntity anchorGstDetailsEntity = anchorGstDetailsRepository.findById(anchorGstDetailsData.getId()).get();
        Date date = new Date();
        anchorGstDetailsEntity.setAddressLine1(anchorGstDetailsData.getAddressLine1());
        anchorGstDetailsEntity.setAddressLine2(anchorGstDetailsData.getAddressLine2());
        anchorGstDetailsEntity.setCity(anchorGstDetailsData.getCity());
        anchorGstDetailsEntity.setCountry(anchorGstDetailsData.getCountry());
        anchorGstDetailsEntity.setPincode(anchorGstDetailsData.getPinCode());
        anchorGstDetailsEntity.setState(anchorGstDetailsData.getState());
//        anchorGstDetailsEntity.setCreatedBy(createdBy);
        anchorGstDetailsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        anchorGstDetailsEntity.setGstNumber(anchorGstDetailsData.getGstNumber());
        anchorGstDetailsEntity.setGstAcctHolderName(anchorGstDetailsData.getGstAccntHolderName());
        anchorGstDetailsEntity.setApplicationEntity(applicationRepository.getById(anchorGstDetailsData.getAppId()));
        return anchorGstDetailsEntity;
    }

    @Override
    public AnchorGstEntity getanchorGstDetailsById(long id) {
        return anchorGstDetailsRepository.findById(id).orElse(null);
    }

}




