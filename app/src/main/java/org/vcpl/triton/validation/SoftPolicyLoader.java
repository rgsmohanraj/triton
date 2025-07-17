package org.vcpl.triton.validation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.vcpl.triton.counterParty.data.CollateralDetailsData;
import org.vcpl.triton.counterParty.data.CollateralDetailsMasterData;
import org.vcpl.triton.counterParty.data.SoftPolicyDetailsData;
import org.vcpl.triton.counterParty.data.SoftPolicyDetailsMasterData;
import org.vcpl.triton.counterParty.entity.CollateralMasterEntity;
import org.vcpl.triton.counterParty.entity.SoftPolicyMasterSubTypeEntity;
import org.vcpl.triton.counterParty.repository.SoftPolicyMasterSubTypeRepository;
import org.vcpl.triton.exception.InvalidJsonException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SoftPolicyLoader implements ApplicationRunner {
    @Autowired
    private Gson gsonConverter;

    @Autowired
    private SoftPolicyMasterSubTypeRepository softPolicyMasterSubTypeRepository;

    private static Map<Long, SoftPolicyMasterSubTypeEntity> softPolicyDataMap = new ConcurrentHashMap<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<SoftPolicyMasterSubTypeEntity> softPolicyConfigList = softPolicyMasterSubTypeRepository.findAll();
        loadSoftPolicyDataMap(softPolicyConfigList);
    }
    private void loadSoftPolicyDataMap(List<SoftPolicyMasterSubTypeEntity> partnerConfigList)
    {
        for(SoftPolicyMasterSubTypeEntity softPolicyConfig : partnerConfigList)
        {
            if(!softPolicyDataMap.containsKey(softPolicyConfig.getName()))
            {
                softPolicyDataMap.put(softPolicyConfig.getId(),softPolicyConfig);
            }
            else
            {
                softPolicyDataMap.put(softPolicyConfig.getId(),softPolicyConfig);
            }

        }
    }
    public Map<Long, SoftPolicyMasterSubTypeEntity> getSoftPolicyDataMap()
    {
        return softPolicyDataMap;
    }

    public String checkSupportedParameters(String jsonString)
    {
        SoftPolicyDetailsMasterData softPolicyDetailsMasterData = gsonConverter.fromJson(jsonString, SoftPolicyDetailsMasterData.class);
        List<SoftPolicyDetailsData> softPolicyDetailsData = softPolicyDetailsMasterData.getSoftPolicyDetailsDataList();
        List<SoftPolicyMasterSubTypeEntity> softPolicyMasterSubTypeEntities = softPolicyMasterSubTypeRepository.findAll();
        for(SoftPolicyMasterSubTypeEntity softPolicyMasterSubTypeEntity : softPolicyMasterSubTypeEntities){
            boolean msg = false;
            if(softPolicyMasterSubTypeEntity.getMandatory().equals("1")){
                for(SoftPolicyDetailsData softPolicyDetailsData0 : softPolicyDetailsData){
                    if(softPolicyDetailsData0.getSoftPolicyId().equals(softPolicyMasterSubTypeEntity.getId())){
                        msg = true;
                    }
                }
            }else{
                msg = true;
            }
            if(msg){
                continue;
            }else{
                return "Enter Value "+softPolicyMasterSubTypeEntity.getDisplayName();
            }
        }

        for(SoftPolicyDetailsData softPolicyDetailsData1 : softPolicyDetailsData){
            Long spId = softPolicyDetailsData1.getSoftPolicyId();
            if(null == spId)
            {
                throw new IllegalArgumentException();
            }
            SoftPolicyMasterSubTypeEntity softPolicyMaster = softPolicyDataMap.get(spId);
            if(null == softPolicyMaster)
            {
                throw new IllegalArgumentException();
            }
            String value = softPolicyDetailsData1.getValue();
            String regex = softPolicyMaster.getRegex();
            String dataType = softPolicyMaster.getDataType();
            Boolean bool = validateValue(value,regex,dataType);
            if(bool){
                continue;
            }else {
                return "Enter Valid Value for '" + softPolicyMaster.getDisplayName()+"'";
            }
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        if (StringUtils.isBlank(jsonString)) {
            throw new InvalidJsonException();
        }
        return "Validation Success";
    }
    
//    public String checkSupportedParameters(String jsonString)
//    {
//        SoftPolicyDetailsMasterData softPolicyDetailsMasterData = gsonConverter.fromJson(jsonString, SoftPolicyDetailsMasterData.class);
//        List<SoftPolicyDetailsData> softPolicyDetailsData = softPolicyDetailsMasterData.getSoftPolicyDetailsDataList();
//        for(SoftPolicyDetailsData softPolicyDetailsData1 : softPolicyDetailsData){
//            Long spId = softPolicyDetailsData1.getSoftPolicyId();
//            SoftPolicyMasterSubTypeEntity softPolicyMaster = softPolicyDataMap.get(spId);
//            if(null == softPolicyMaster)
//            {
//                throw new InvalidJsonException();
//            }
//            String value = softPolicyDetailsData1.getValue();
//            String regex = softPolicyMaster.getRegex();
//            String dataType = softPolicyMaster.getDataType();
//            Boolean bool = validateValue(value,regex,dataType);
//            if(bool){
//                continue;
//            }else {
//                return "Enter Valid Value for '" + softPolicyMaster.getDisplayName()+"'";
//            }
//        }
//        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
//        if (StringUtils.isBlank(jsonString)) {
//            throw new InvalidJsonException();
//        }
//        return "Validation Success";
//    }
    public boolean validateValue(String value, String regex,String dataType) {
        Boolean b = value.isEmpty();
        if (b) {
            return !b;
        } else {
            b = value.matches(regex);
            return b;
        }
    }
}
