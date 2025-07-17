package org.vcpl.triton.counterParty.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.entity.ProgramNormsEntity;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;
import org.vcpl.triton.anchor.repository.ProgramNormsRepository;
import org.vcpl.triton.anchor.service.ApplicationService;
import org.vcpl.triton.counterParty.data.ProposedProductsData;
import org.vcpl.triton.counterParty.data.ProposedProductsMasterData;
import org.vcpl.triton.counterParty.entity.ProposedProductsEntity;
import org.vcpl.triton.counterParty.repository.CPBasicDetailsRepository;
import org.vcpl.triton.counterParty.repository.ProposedProductsRepository;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class ProposedProductsService implements IProposedProductsService {

    private static final Logger logger = LoggerFactory.getLogger(ProposedProductsService.class);


    @Autowired
    private ProposedProductsRepository proposedProductsRepository;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private CPBasicDetailsRepository cpBasicDetailsRepository;

    @Autowired
    private ProgramNormsRepository programNormsRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    ApplicationService applicationService;

    @Override
    public List<ProposedProductsEntity> getProposedProduct() {
        logger.info(" | URL | /proposedProductDetails | OPERATION | " + "GET proposedProducts");
            return this.proposedProductsRepository.findAll();
        }

    @Override
    public ProposedProductsEntity getProposedProductById(Long id) {
        logger.info(" | URL | /proposedProductDetail/{id} | OPERATION | " + "GETById proposedProducts");
            return this.proposedProductsRepository.findById(id).orElse(null);
        }

    @Override
    public Collection<ProposedProductsEntity> findProposedProductsByCiId(long id) {
        logger.info(" | URL | /proposedProductsById/{id} | OPERATION | " + "GETById proposedProducts");
            return proposedProductsRepository.findByFId(id);
        }

    @Override
    public Collection<ProposedProductsEntity> findActiveProposedProducts(Long id) {
        logger.info(" | URL | /proposedProductsById/{id} | OPERATION | " + "GETById proposedProducts");
        return proposedProductsRepository.findActiveProposedProducts(id);
    }

    @Override
    public String findCreditNormsId(long id) {
        logger.info(" | URL | /getCreditNormsIds/{id} | OPERATION | " + "GETById proposedProducts");

        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        List<Object[]> list = proposedProductsRepository.findCreditNormsId(id);
        if (list.size() > 0) {
//            JSONArray array = new JSONArray();
            for (Object[] obj : list) {
                JSONObject obj1 = new JSONObject();
                obj1.put("anchorId", obj[0]);
                obj1.put("creditAppId", obj[1]);
                array.put(obj1);
            }
//            json.put("creditNormsIdList", array);
        }
        try {
            return array.toString();
        } catch (Exception ex) {
            logger.error(" | URL | /getCreditNormsIds/{id} | OPERATION | " + " Error |" + ex.getMessage());

        }
        return null;
//        return  proposedProductsRepository.findCreditNormsId(id);
    }


    @Override
    public String gotoRiskOrCredit(long id) {
        double recourse = 0;
        double nonRecourse = 0;
        Boolean bool = false;
        Collection<ProposedProductsEntity> proposedProductsEntities = proposedProductsRepository.findByFId(id);
        for (ProposedProductsEntity proposedProductsEntity : proposedProductsEntities) {
            Collection<ProgramNormsEntity> programNormsEntities = programNormsRepository.findByCustId(proposedProductsEntity.getCustomerInfoEntity().getId());
            for (ProgramNormsEntity programNormsEntity : programNormsEntities) {
                if (proposedProductsEntity.getProduct().equals(programNormsEntity.getSubProduct())) {
                    if (programNormsEntity.getTransactionType().equals("Recourse")) {
                        try {
                            recourse += Double.valueOf(proposedProductsEntity.getProposed());
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid input. Cannot convert to integer.");
                        }
                    } else if (programNormsEntity.getTransactionType().equals("Non Recourse")) {
                        try {
                            nonRecourse += Double.valueOf(proposedProductsEntity.getProposed());
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid input. Cannot convert to integer.");
                        }
                    }
                }
            }
        }
        if(recourse > 50000000 || nonRecourse > 40000000){
            bool = true;
        }else{
            bool = false;
        }
        JSONObject json = new JSONObject();
        JSONObject json1 = new JSONObject();
        JSONArray array = new JSONArray();

        json.put("appId", id);
        json.put("goto",bool);
        json.put("recourseAmount", recourse);
        json.put("nonRecourseAmount", nonRecourse);
        array.put(json);

        json1.put("riskOrCredit",array);

        return json1.toString();
    }

    @Override
    public List<Long> saveProposedProduct(ProposedProductsMasterData proposedProductsMasterData, long id) {

        logger.info(" | URL | /proposedProductDetails/{id} | OPERATION | " + "POST proposedProducts");

        String createdBy = proposedProductsMasterData.getCreatedBy();
//        String createdAt = anchorGstMasterData.getCreatedAt();
        List<Long> entityIdLst = new ArrayList<Long>();
        for (ProposedProductsData proposedProductsData : proposedProductsMasterData.getProposedProductsDataList()) {
            entityIdLst.add(proposedProductsRepository.save(transform(proposedProductsData, createdBy, id)).getId());
        }
            return entityIdLst;
        }

    public ProposedProductsEntity transform(ProposedProductsData proposedProductsData, String createdBy, long id) {


        ProposedProductsEntity proposedProductsEntity = new ProposedProductsEntity();
        Date date = new Date();
        proposedProductsEntity.setType(proposedProductsData.getType());
        proposedProductsEntity.setProposed(proposedProductsData.getProposed());
        proposedProductsEntity.setProduct(proposedProductsData.getProduct());
//        proposedProductsEntity.setProposalType(proposedProductsData.getProposalType());
//        proposedProductsEntity.setCreatedAt(new Timestamp(date.getTime()));
        proposedProductsEntity.setCreatedBy(createdBy);
//        proposedProductsEntity.setUpdatedAt(new Timestamp(date.getTime()));
        proposedProductsEntity.setCreatedAt(new Timestamp(date.getTime()));
        proposedProductsEntity.setApplicationEntity(applicationRepository.findById(id).orElseThrow());
        proposedProductsEntity.setCustomerInfoEntity(customerInfoRepository.findById(proposedProductsData.getCustId()).orElseThrow());
        return proposedProductsEntity;
    }

    @Override
    public List<Long> updateProposedProduct(ProposedProductsMasterData proposedProductsMasterData, Long appId) {
        logger.info(" | URL | /proposedProductDetails/{id} | OPERATION | " + "PUT proposedProducts");
        List<Long> entityIdLst = new ArrayList<Long>();
        for (ProposedProductsData proposedProductsData : proposedProductsMasterData.getProposedProductsDataList()) {
            JSONObject containerObject= new JSONObject(proposedProductsData);
            if(containerObject.has("id") || proposedProductsData.getId() != null){
                entityIdLst.add(proposedProductsRepository.save(updateTransform(proposedProductsData,proposedProductsData.getId())).getId());
            }else {
                entityIdLst.add(proposedProductsRepository.save(transform(proposedProductsData,proposedProductsMasterData.getCreatedBy(),appId)).getId());
            }
        }
        return entityIdLst;
    }

    private ProposedProductsEntity updateTransform(ProposedProductsData proposedProductsData, long id) {

        ProposedProductsEntity proposedProductsEntity = proposedProductsRepository.findById(id).get();
        Date date = new Date();
        proposedProductsEntity.setType(proposedProductsData.getType());
        proposedProductsEntity.setProposed(proposedProductsData.getProposed());
        proposedProductsEntity.setProduct(proposedProductsData.getProduct());
        proposedProductsEntity.setCreditPolicyCheck(proposedProductsData.getCreditPolicyCheck());
//        proposedProductsEntity.setProposalType(proposedProductsData.getProposalType());
        proposedProductsEntity.setCreatedAt(new Timestamp(date.getTime()));
//        proposedProductsEntity.setCreatedBy(proposedProductsData.getCreatedBy());
        proposedProductsEntity.setUpdatedAt(new Timestamp(date.getTime()));
//        proposedProductsEntity.setUpdatedBy(proposedProductsData.getUpdatedBy());
        proposedProductsEntity.setApplicationEntity(applicationRepository.getById(proposedProductsData.getAppId()));
        proposedProductsEntity.setCustomerInfoEntity(customerInfoRepository.getById(proposedProductsData.getCustId()));
        return proposedProductsEntity;
    }

    @Override
    public List<Long> updateCreditProposedProduct(ProposedProductsMasterData proposedProductsMasterData) {
        logger.info(" | URL | /proposedProductDetails/{id} | OPERATION | " + "PUT proposedProducts");
        List<Long> entityIdLst = new ArrayList<Long>();
        for (ProposedProductsData proposedProductsData : proposedProductsMasterData.getProposedProductsDataList()) {
            entityIdLst.add(proposedProductsRepository.save(updateCreditTransform(proposedProductsData,proposedProductsData.getId())).getId());
        }
        return entityIdLst;
    }

    private ProposedProductsEntity updateCreditTransform(ProposedProductsData proposedProductsData, long id) {

        ProposedProductsEntity proposedProductsEntity = proposedProductsRepository.findById(id).get();
        Date date = new Date();
        proposedProductsEntity.setVintageWithAnchor(proposedProductsData.getVintageWithAnchor());
        proposedProductsEntity.setMinMonthlyAnchor(proposedProductsData.getMinMonthlyAnchor());
        proposedProductsEntity.setAnchorRelationship(proposedProductsData.getAnchorRelationship());
        proposedProductsEntity.setCreditPolicyCheck(proposedProductsData.getCreditPolicyCheck());
        proposedProductsEntity.setUpdatedAt(new Timestamp(date.getTime()));

        return proposedProductsEntity;
    }

    @Override
    public String deleteProposedProduct(long id) {
        logger.info(" | URL | /proposedProductDetails/{id} | OPERATION | " + "DELETE LimitEligibility");
        proposedProductsRepository.deleteProposedById(id);
            return "success";
        }

    @Override
    public String proposedProductDelete(ProposedProductsData proposedProductsData, Long custId) {
        String result = applicationService.getApplicationIds(custId);
        JSONObject jsonObject = new JSONObject(result);
        JSONArray resultList = jsonObject.getJSONArray("appList");
        JSONArray newId = new JSONArray();
        JSONArray oldId = new JSONArray();
        Long appValueId = 0L;
        for (int i = 0; i < resultList.length(); i++) {
            JSONObject item = resultList.getJSONObject(i);
//            System.out.println("Real Value:" + item);
            newId = item.getJSONArray("newAppId");
            oldId = item.getJSONArray("oldAppId");
        }

        JSONObject idValue = newId.getJSONObject(0);
        appValueId = idValue.getLong("appId");
//        System.out.println("New AppId:" + appValueId);

        JSONObject oldValue = oldId.getJSONObject(0);
        Long oldValueId = oldValue.getLong("appId");
//        System.out.println("Old AppId:" + oldValueId);
        Boolean isDeleted = false;
        JSONObject json=new JSONObject();
        JSONArray array=new JSONArray();
        JSONObject json1=new JSONObject();
        Collection<ProposedProductsEntity> proposedProductsEntities = proposedProductsRepository.getByAppId(oldValueId);
        if(proposedProductsEntities.size()>0){
            isDeleted = proposedProductsEntities.stream().noneMatch(prod -> ((prod.getProduct().equalsIgnoreCase(proposedProductsData.getProduct()))
            && (prod.getCustomerInfoEntity().getId() == proposedProductsData.getCustId())));
        }
        if (isDeleted) {
            if(proposedProductsData.getId()!=null){
                proposedProductsRepository.deleteProposedById(proposedProductsData.getId());
            }
            json1.put("status",true);
            json1.put("message","success");
            array.put(json1);
        } else {
            json1.put("status",true);
            json1.put("message","fail");
            array.put(json1);
        }
        json.put("result",array);
        return json.toString();
    }

}