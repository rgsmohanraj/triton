package org.vcpl.triton.anchor.service;


import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.data.AnchorAddressData;
import org.vcpl.triton.anchor.data.AnchorAddressMasterData;
import org.vcpl.triton.anchor.entity.AnchorAddressEntity;
import org.vcpl.triton.anchor.repository.AnchorAddressRepository;
import org.vcpl.triton.anchor.repository.ApplicationRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class AnchorAddressService implements IAnchorAddress{

    private static final Logger logger = LoggerFactory.getLogger(AnchorAddressService.class);

    @Autowired
    private AnchorAddressRepository anchorAddressDetailsRepository;

    @Autowired
    private ApplicationRepository applicationRepository;


    @Override
    public List<AnchorAddressEntity> getAllProduct()
    {
        return this.anchorAddressDetailsRepository.findAll();
    }

    @Override
    public Collection<AnchorAddressEntity> getanchorAddressDetailsById(long id) {
        return  anchorAddressDetailsRepository.findByCiId(id);
    }

    @Override
    public AnchorAddressEntity getAddressDetailsById(long id) {
        logger.info(" | URL | getAddressDetailsById | OPERATION | "+"Get AddressDetailsById");
          return anchorAddressDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public AnchorAddressEntity saveAnchorAddressDetails(AnchorAddressData anchorAddressData) {
        AnchorAddressEntity anchorAddressEntity = new AnchorAddressEntity();

        Date date = new Date();
        anchorAddressEntity.setAddressLine1(anchorAddressData.getAddressLine1());
        anchorAddressEntity.setAddressLine2(anchorAddressData.getAddressLine2());
        anchorAddressEntity.setApplicationEntity(applicationRepository.findById(anchorAddressData.getAppId()).orElseThrow());
        anchorAddressEntity.setCity(anchorAddressData.getCity());
        anchorAddressEntity.setState(anchorAddressData.getState());
        anchorAddressEntity.setCountry(anchorAddressData.getCountry());
        anchorAddressEntity.setPinCode(anchorAddressData.getPinCode());
        anchorAddressEntity.setCreatedAt(new Timestamp(date.getTime()));

        return anchorAddressDetailsRepository.save(anchorAddressEntity);
    }

    @Override
    public AnchorAddressEntity updateAnchorAddress(AnchorAddressData anchorAddressData) {
        AnchorAddressEntity anchorAddressEntity = anchorAddressDetailsRepository.findById(anchorAddressData.getId()).get();

        Date date = new Date();
        anchorAddressEntity.setAddressLine1(anchorAddressData.getAddressLine1());
        anchorAddressEntity.setAddressLine2(anchorAddressData.getAddressLine2());
        anchorAddressEntity.setApplicationEntity(applicationRepository.findById(anchorAddressData.getAppId()).orElseThrow());
        anchorAddressEntity.setCity(anchorAddressData.getCity());
        anchorAddressEntity.setState(anchorAddressData.getState());
        anchorAddressEntity.setCountry(anchorAddressData.getCountry());
        anchorAddressEntity.setPinCode(anchorAddressData.getPinCode());
        anchorAddressEntity.setUpdatedAt(new Timestamp(date.getTime()));

        return anchorAddressDetailsRepository.save(anchorAddressEntity);
    }

    @Override
    public String deleteAnchorAddress(long id) {
        anchorAddressDetailsRepository.deleteById(id);
        return id + "Removed";
    }

    @Override
    public String getPincodeDetails(String pinCode) {
        List<Object[]>  result = anchorAddressDetailsRepository.findByPinCode(pinCode);
        JSONObject json=new JSONObject();
        if(result!=null && result.size()>0){
            JSONArray array=new JSONArray();
            for(Object[] obj : result){
                JSONObject json1=new JSONObject();
                json1.put("city",obj[2]);
                json1.put("state",obj[3]);
                json1.put("status","Success");
                array.put(json1);
            }
            json.put("addressDetails",array);
        }else{
            JSONArray array=new JSONArray();
            JSONObject json1=new JSONObject();
            json1.put("status","Fail");
            array.put(json1);
            json.put("addressDetails",array);
        }
        return json.toString();
    }

    @Override
    public List<Long>  updateMultipleAddress(AnchorAddressMasterData anchorAddressMasterData) {

        String updatedBy = anchorAddressMasterData.getUpdatedBy();
        String createdBy = anchorAddressMasterData.getCreatedBy();
        List<Long> entityIdLst = new ArrayList<Long>();
        for(AnchorAddressData anchorAddressData : anchorAddressMasterData.getAnchorAddressData()) {
            entityIdLst.add(anchorAddressDetailsRepository.save(upTransform (anchorAddressData, updatedBy,createdBy)).getId());
        }
        return entityIdLst;
    }

    private AnchorAddressEntity upTransform(AnchorAddressData anchorAddressData,String updatedBy,String createdBy) {
        AnchorAddressEntity anchorAddressDetailsEntity = anchorAddressDetailsRepository.findById(anchorAddressData.getId()).get();
        Date date = new Date();
        anchorAddressDetailsEntity.setApplicationEntity(applicationRepository.findById(anchorAddressData.getAppId()).orElseThrow());
        anchorAddressDetailsEntity.setAddressLine2(anchorAddressData.getAddressLine2());
        anchorAddressDetailsEntity.setAddressLine1(anchorAddressData.getAddressLine1());
        anchorAddressDetailsEntity.setState(anchorAddressData.getState());
        anchorAddressDetailsEntity.setCity(anchorAddressData.getCity());
        anchorAddressDetailsEntity.setCountry(anchorAddressData.getCountry());
        anchorAddressDetailsEntity.setPinCode(anchorAddressData.getPinCode());
        anchorAddressDetailsEntity.setUpdatedBy(updatedBy);
        anchorAddressDetailsEntity.setCreatedBy(createdBy);
//        anchorAddressDetailsEntity.setCreatedBy(anchorAddressDetailsEntity.getCreatedBy());
//        anchorAddressDetailsEntity.setCreatedAt(anchorAddressDetailsEntity.getCreatedAt());
//        anchorAddressDetailsEntity.setUpdatedBy(anchorAddressData.getUpdatedBy());
//        anchorAddressDetailsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        anchorAddressDetailsEntity.setApplicationEntity(applicationRepository.getById(anchorAddressData.getAppId()));
        return anchorAddressDetailsEntity;
    }
}
