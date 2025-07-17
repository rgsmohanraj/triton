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
import org.vcpl.triton.anchor.repository.CreditNormsMasterRepository;
import org.vcpl.triton.exception.InvalidJsonException;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.hibernate.query.criteria.internal.ValueHandlerFactory.isNumeric;

@Component
public class CreditNormsLoader implements ApplicationRunner {

    @Autowired
    private Gson gsonConverter;

    @Autowired
    private CreditNormsMasterRepository creditNormsMasterRepository;

    private static Map<Long, CreditNormsMasterEntity> creditNormsDataMap = new ConcurrentHashMap<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<CreditNormsMasterEntity> creditNormsConfigList = creditNormsMasterRepository.findAll();
        loadCreditNormsDataMap(creditNormsConfigList);

    }

    private void loadCreditNormsDataMap(List<CreditNormsMasterEntity> partnerConfigList)
    {

        for(CreditNormsMasterEntity creditNormsConfig : partnerConfigList)
        {
            if(!creditNormsDataMap.containsKey(creditNormsConfig.getName()))
            {

                creditNormsDataMap.put(creditNormsConfig.getId(),creditNormsConfig);
            }
            else
            {
                creditNormsDataMap.put(creditNormsConfig.getId(),creditNormsConfig);
            }

        }
    }

 /*   //private Collection<> getCreditNormsSet()
    {
       return creditNormsDataMap.keySet();
    }*/

    public Map<Long, CreditNormsMasterEntity> getCreditNormsDataMap()
    {
        return creditNormsDataMap;
    }

    public String checkSupportedParameters(String jsonString)
    {
        CreditNormsDetailsMasterData creditNormsDetailsMasterData = gsonConverter.fromJson(jsonString, CreditNormsDetailsMasterData.class);
        List<CreditNormsDetailsData> creditNormsDetailsData = creditNormsDetailsMasterData.getCreditNormsDetailsDataList();
        for(CreditNormsDetailsData creditNormsDetailsData1 : creditNormsDetailsData){
            Long cnMasterId = creditNormsDetailsData1.getCnMasterId();
            CreditNormsMasterEntity creditNorms = creditNormsDataMap.get(cnMasterId);
            if(null == creditNorms)
            {
                throw new InvalidJsonException();
            }
            String value = creditNormsDetailsData1.getValue();
            String regex = creditNorms.getRegex();
            String dataType = creditNorms.getDatatype();
            Boolean bool = validateValue(value,regex,dataType);
            if(bool){
                continue;
            }else {
                return "Enter Valid Value for '" + creditNorms.getDisplayName()+"'";
            }
        }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        if (StringUtils.isBlank(jsonString)) {
            throw new InvalidJsonException();
        }
//        final Map<String, Object> requestMap = this.gsonConverter.fromJson(jsonString, typeOfMap);
//
//        final List<String> unsupportedParameterList = new ArrayList<>();
//        for (final String providedParameter : requestMap.keySet()) {
//            if (!getCreditNormsSet().contains(providedParameter)) {
//                unsupportedParameterList.add(providedParameter);
//            }
//        }
//        if (!unsupportedParameterList.isEmpty()) {
//            throw new UnsupportedParameterException(unsupportedParameterList);
//        }

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
    }

}
//        if(dataType.equals("Integer")){
////          Long val = Long.parseLong(value);
////          if(val <= 999999999 && 1 <= val && isNumeric(val)){
//                b=value.matches(regex);
//                b=!(value.isEmpty());
////          }
//        }else if(dataType.equals("Float")){
////            Float val = Float.parseFloat(value);
////            if(val <= 999999999 && 1 >= val && isNumeric(val)){
//            b=value.matches(regex);
//            b=!(value.isEmpty());
////            }
//        }else if(dataType.equals("String")){
//            b=value.matches(regex);
//            b=!(value.isEmpty());
//        }

