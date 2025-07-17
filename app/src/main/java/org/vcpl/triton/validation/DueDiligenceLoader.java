package org.vcpl.triton.validation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.vcpl.triton.counterParty.data.DueDiligenceDetailsData;
import org.vcpl.triton.counterParty.data.DueDiligenceDetailsMasterData;
import org.vcpl.triton.counterParty.entity.DueDiligenceMasterEntity;
import org.vcpl.triton.counterParty.repository.DueDiligenceMasterRepository;
import org.vcpl.triton.exception.InvalidJsonException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DueDiligenceLoader implements ApplicationRunner {
    @Autowired
    private Gson gsonConverter;

    @Autowired
    private DueDiligenceMasterRepository dueDiligenceMasterRepository;

    private static Map<Long, DueDiligenceMasterEntity> dueDiligenceDataMap = new ConcurrentHashMap<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<DueDiligenceMasterEntity> dueDiligenceConfigList = dueDiligenceMasterRepository.findAll();
        loadDueDiligenceMasterDataMap(dueDiligenceConfigList);
    }

    private void loadDueDiligenceMasterDataMap(List<DueDiligenceMasterEntity> partnerConfigList)
    {
        for(DueDiligenceMasterEntity dueDiligenceConfig : partnerConfigList)
        {
            if(!dueDiligenceDataMap.containsKey(dueDiligenceConfig.getName()))
            {
                dueDiligenceDataMap.put(dueDiligenceConfig.getId(),dueDiligenceConfig);
            }
            else
            {
                dueDiligenceDataMap.put(dueDiligenceConfig.getId(),dueDiligenceConfig);
            }
        }
    }

    public Map<Long, DueDiligenceMasterEntity> getDueDiligenceDataMap()
    {
        return dueDiligenceDataMap;
    }

    public String checkSupportedParameters(String jsonString) {
        DueDiligenceDetailsMasterData dueDiligenceDetailsMasterData = gsonConverter.fromJson(jsonString, DueDiligenceDetailsMasterData.class);
        List<DueDiligenceDetailsData> dueDiligenceDetailsData = dueDiligenceDetailsMasterData.getDueDiligenceDetailsDataList();
        for (DueDiligenceDetailsData dueDiligenceDetailsData1 : dueDiligenceDetailsData) {
            Long dueDiligenceMasterId = dueDiligenceDetailsData1.getDdId();
            DueDiligenceMasterEntity dueDiligenceMaster = dueDiligenceDataMap.get(dueDiligenceMasterId);
            if (null == dueDiligenceMaster) {
                throw new InvalidJsonException();
            }
            String value = dueDiligenceDetailsData1.getValue();
            String regex = dueDiligenceMaster.getRegex();
            String dataType = dueDiligenceMaster.getDatatype();
            Boolean bool = validateValue(value, regex, dataType);
            if (bool) {
                continue;
            } else {
                return "Enter Valid Value for '" + dueDiligenceMaster.getDisplayName() + "'";
            }
        }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {
        }.getType();
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

