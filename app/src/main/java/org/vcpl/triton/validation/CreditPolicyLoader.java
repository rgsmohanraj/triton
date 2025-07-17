package org.vcpl.triton.validation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.vcpl.triton.counterParty.data.*;
import org.vcpl.triton.counterParty.entity.CreditPolicyMasterEntity;
import org.vcpl.triton.counterParty.repository.CreditPolicyMasterRepository;
import org.vcpl.triton.exception.InvalidJsonException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CreditPolicyLoader implements ApplicationRunner{

    @Autowired
    private Gson gsonConverter;

    @Autowired
    private CreditPolicyMasterRepository creditPolicyMasterRepository;

    private static Map<Long, CreditPolicyMasterEntity> creditPolicyDataMap = new ConcurrentHashMap<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<CreditPolicyMasterEntity> creditPolicyConfigList = creditPolicyMasterRepository.findAll();
        loadCreditPolicyDataMap(creditPolicyConfigList);
    }
    private void loadCreditPolicyDataMap(List<CreditPolicyMasterEntity> partnerConfigList)
    {
        for(CreditPolicyMasterEntity creditPolicyConfig : partnerConfigList)
        {
            if(!creditPolicyDataMap.containsKey(creditPolicyConfig.getPolicyName()))
            {
                creditPolicyDataMap.put(creditPolicyConfig.getId(),creditPolicyConfig);
            }
            else
            {
                creditPolicyDataMap.put(creditPolicyConfig.getId(),creditPolicyConfig);
            }

        }
    }
    public Map<Long, CreditPolicyMasterEntity> getCreditPolicyDataMap()
    {
        return creditPolicyDataMap;
    }

    public String checkSupportedParameters(String jsonString)
    {
        CreditPolicyDetailsMasterData creditPolicyDetailsMasterData = gsonConverter.fromJson(jsonString, CreditPolicyDetailsMasterData.class);
        List<CreditPolicyDetailsData> creditPolicyDetailsData = creditPolicyDetailsMasterData.getCreditPolicyDetailsData();
        for(CreditPolicyDetailsData creditPolicyDetailsData1 : creditPolicyDetailsData){
            Long cpId = creditPolicyDetailsData1.getCpMasterId();
            CreditPolicyMasterEntity creditPolicyMaster = creditPolicyDataMap.get(cpId);
            if(null == creditPolicyMaster)
            {
                throw new InvalidJsonException();
            }
            String value = creditPolicyDetailsData1.getValue();
            String regex = creditPolicyMaster.getRegex();
            String dataType = creditPolicyMaster.getDataType();
            Boolean bool = validateValue(value,regex,dataType);
            if(bool){
                continue;
            }else {
                return "Enter Valid Value for '" + creditPolicyMaster.getDisplayName()+"'";
            }
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        if (StringUtils.isBlank(jsonString)) {
            throw new InvalidJsonException();
        }
        return "Validation Success";
    }

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
