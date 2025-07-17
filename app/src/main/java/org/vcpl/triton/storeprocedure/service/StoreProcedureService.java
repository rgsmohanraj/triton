package org.vcpl.triton.storeprocedure.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.counterParty.entity.ProposedProductsEntity;
import org.vcpl.triton.counterParty.service.ProposedProductsService;
import org.vcpl.triton.security.config.AWSDBConfiguration;
import org.vcpl.triton.storeprocedure.entity.AnchorCodeEntity;
import org.vcpl.triton.storeprocedure.repository.AnchorCodeRepo;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;

@Service
public class StoreProcedureService {


    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private CompanyBankService companyBankService;

    @Autowired
    private CompanyAddressService companyAddressService;

    @Autowired
    private AnchorMasterService anchorMasterService;

    @Autowired
    private AnchorInterestService anchorInterestService;

    @Autowired
    private CounterPartyMaster counterPartyMaster;

    @Autowired
    private  CounterPartyInterest counterPartyInterest;

    @Autowired
    private AWSDBConfiguration jdbcConfig;

    @Autowired
    private AnchorCodeRepo anchorCodeRepo;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ProposedProductsService proposedProductsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreProcedureService.class);


    public Map<String, Object> anchorStoreProcedure(long id) throws SQLException, ClassNotFoundException {
        JdbcTemplate jdbc = jdbcConfig.JdbcStoreProcedure(jdbcConfig.storeProcedure());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbc).withCatalogName("ksf_uls_intf_pkg").withProcedureName("sp_comp_mst_wrapper");
        BigInteger cifId = companyMasterService.anchorCompanyMaster(id); //
//  //      ***********CompanyMasterDetails**********
        String companyName = companyMasterService.anchorCompanyMasterDetails(cifId);
        companyAddressService.anchorCompanyAddress(cifId,id);
//        companyAddressService.anchorMasterDetails(cifId);
        companyBankService.anchorCompanyBankDetails(cifId,id,companyName);
//
        Map<String,Object> objectMap =  jdbcCall.execute();
        LOGGER.info("***objectMap***"+objectMap);

        String result = objectMap.get("P_OUT_MSG").toString();
        if(result.equals("SUCCESS")){
            //***********AnchorDetails**********
            String accountCodes = anchorMasterService.saveAnchorMaster(cifId,id,companyName);
            LOGGER.info("Account codes" + accountCodes);
            JSONObject jsonObject = new JSONObject(accountCodes);
            JSONArray accountCodeList = jsonObject.getJSONArray("accountCodeList");

            // Iterating through the accountCodeList array
            for (int i = 0; i < accountCodeList.length(); i++) {
                AnchorCodeEntity anchorCodeEntity = new AnchorCodeEntity();
                anchorCodeEntity.setApplicationEntity(applicationRepository.findById(id).orElseThrow());
                JSONObject accountCodeObject = accountCodeList.getJSONObject(i);
                String accountCode = accountCodeObject.getString("accountCode");
                String productCode = accountCodeObject.getString("productCode");
                anchorCodeEntity.setVcplCode(accountCode);
                anchorCodeEntity.setProductCode(productCode);
                anchorCodeEntity.setCifId(cifId);
                anchorInterestService.saveAnchorInterest(cifId,id,accountCode,productCode);
                anchorCodeRepo.save(anchorCodeEntity);
            }

            SimpleJdbcCall jdbcCallAnchor = new SimpleJdbcCall(jdbc).withCatalogName("ksf_uls_intf_pkg").withProcedureName("sp_anchor_mst_wrapper");
            Map<String,Object> object =  jdbcCallAnchor.execute();
            LOGGER.info("***objectMap***"+object);
            return object;
        }
        return  objectMap;
    }

    public Map<String, Object> cpStoreProcedure(long id) throws SQLException, ClassNotFoundException {

        JdbcTemplate jdbc = jdbcConfig.JdbcStoreProcedure(jdbcConfig.storeProcedure());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbc).withCatalogName("ksf_uls_intf_pkg").withProcedureName("sp_comp_mst_wrapper");
        BigInteger cifId =companyMasterService.cpCompanyMasters(id);
        LOGGER.info("***********CifId************"+cifId);

//            companyMasterService.(cifId);
        String companyName = companyMasterService.anchorCompanyMasterDetails(cifId);
        companyAddressService.cpCompanyAddress(cifId,id);
        companyBankService.cpCompanyBankDetails(cifId,id,companyName);

        Map<String, Object> objectMap = jdbcCall.execute();
        LOGGER.info("***objectMap***" + objectMap);


//            LOGGER.info("Cust Ids : "+custIdList);
            String result = objectMap.get("P_OUT_MSG").toString();
            if (result.equals("SUCCESS")) {
//                Set<Long> custIdList = new HashSet<>();
                Collection<ProposedProductsEntity> proposedProductsList = proposedProductsService.findActiveProposedProducts(id);
                for(ProposedProductsEntity proposedProductsEntity: proposedProductsList){
//                    custIdList.add(proposedProductsEntity.getCustomerInfoEntity().getId());

                    String cpCodes = counterPartyMaster.saveCpMasterDetails(cifId, id, companyName, proposedProductsEntity.getCustomerInfoEntity().getId(),
                            proposedProductsEntity.getProduct());
                    JSONObject jsonObject = new JSONObject(cpCodes);
                    JSONArray cpCodeList = jsonObject.getJSONArray("cpCodesList");
                    for (int i = 0; i < cpCodeList.length(); i++) {
                        JSONObject accountCodeObject = cpCodeList.getJSONObject(i);
                        String accountCode = accountCodeObject.getString("acctCode");
                        String productCode = accountCodeObject.getString("productCode");
                        LOGGER.info("****cpAcctCode****" + accountCode);
                        counterPartyInterest.counterPartyInterest(cifId, accountCode, productCode, id, proposedProductsEntity.getCustomerInfoEntity().getId(),
                                proposedProductsEntity.getProduct());

                    }
                }
                SimpleJdbcCall jdbcCallAnchor = new SimpleJdbcCall(jdbc).withCatalogName("ksf_uls_intf_pkg").withProcedureName("sp_counter_party_wrapper");
                Map<String, Object> object = jdbcCallAnchor.execute();
                LOGGER.info("***objectMap***" + object);
                return object;
            }
            return objectMap;
        }
}
