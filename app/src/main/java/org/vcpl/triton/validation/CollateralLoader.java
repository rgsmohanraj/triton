package org.vcpl.triton.validation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.vcpl.triton.anchor.data.CreditNormsDetailsData;
import org.vcpl.triton.anchor.data.CreditNormsDetailsMasterData;
import org.vcpl.triton.anchor.entity.CreditNormsMasterEntity;
import org.vcpl.triton.counterParty.data.CollateralDetailsData;
import org.vcpl.triton.counterParty.data.CollateralDetailsMasterData;
import org.vcpl.triton.counterParty.entity.CollateralMasterEntity;
import org.vcpl.triton.counterParty.repository.CollateralMasterRepository;
import org.vcpl.triton.exception.InvalidJsonException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CollateralLoader implements ApplicationRunner {

    @Autowired
    private Gson gsonConverter;

    @Autowired
    private CollateralMasterRepository collateralMasterRepository;

    private static Map<Long, CollateralMasterEntity> collateralDataMap = new ConcurrentHashMap<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<CollateralMasterEntity> collateralConfigList = collateralMasterRepository.findAll();
        loadCollateralDataMap(collateralConfigList);
    }
    private void loadCollateralDataMap(List<CollateralMasterEntity> partnerConfigList)
    {
        for(CollateralMasterEntity collateralConfig : partnerConfigList)
        {
            if(!collateralDataMap.containsKey(collateralConfig.getName()))
            {
                collateralDataMap.put(collateralConfig.getId(),collateralConfig);
            }
            else
            {
                collateralDataMap.put(collateralConfig.getId(),collateralConfig);
            }

        }
    }
    public Map<Long, CollateralMasterEntity> getCollateralDataMap()
    {
        return collateralDataMap;
    }

    public String checkSupportedParameters(String jsonString)
    {
        CollateralDetailsMasterData collateralDetailsMasterData = gsonConverter.fromJson(jsonString, CollateralDetailsMasterData.class);
        List<CollateralDetailsData> collateralDetailsData = collateralDetailsMasterData.getCollateralDetailsDataList();
        for(CollateralDetailsData collateralDetailsData1 : collateralDetailsData){
            Long cmId = collateralDetailsData1.getCmId();
            CollateralMasterEntity collateralMaster = collateralDataMap.get(cmId);
            if(null == collateralMaster)
            {
                throw new InvalidJsonException();
            }
            String value = collateralDetailsData1.getValue();
            String regex = collateralMaster.getRegex();
            String dataType = collateralMaster.getDatatype();
            Boolean bool = validateValue(value,regex,dataType);
            if(bool){
                continue;
            }else {
                return "Enter Valid Value for '" + collateralMaster.getDisplayName()+"'";
            }
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        if (StringUtils.isBlank(jsonString)) {
            throw new InvalidJsonException();
        }
        return "Validation Success";
    }
    public boolean validateValue(String value, String regex,String dataType){
        Boolean b = value.isEmpty();
        if(b){
            return !b;
        }else{
            b=value.matches(regex); 
            return b;
        }

//        Boolean b = false;
//        if(dataType.equals("Integer")){
//            b=value.matches(regex);
//        }else if(dataType.equals("Float")){
//            b=value.matches(regex);
//        }else if(dataType.equals("String")){
//            b=value.matches(regex);
//        }
//        return b;
    }
}
