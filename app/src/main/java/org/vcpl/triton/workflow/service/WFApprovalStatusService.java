package org.vcpl.triton.workflow.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.entity.ProgramNormsEntity;
import org.vcpl.triton.anchor.helper.CustomerInfo;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;
import org.vcpl.triton.anchor.repository.ProgramNormsRepository;
import org.vcpl.triton.anchor.service.ApplicationService;
import org.vcpl.triton.anchor.service.CustomerInfoService;
import org.vcpl.triton.counterParty.entity.AssignUnderwriterEntity;
import org.vcpl.triton.counterParty.entity.TermSheetEntity;
import org.vcpl.triton.counterParty.repository.TermSheetRepository;
import org.vcpl.triton.counterParty.service.AssignUnderwriterService;
import org.vcpl.triton.dms.docManagement.entity.DeferralReportsEntity;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentMasterEntity;
import org.vcpl.triton.dms.docManagement.service.DeferralReportsService;
import org.vcpl.triton.dms.docManagement.service.OtherDocumentMasterService;
import org.vcpl.triton.login.service.LoginService;
import org.vcpl.triton.notification.service.EmailService;
import org.vcpl.triton.rbac.entity.GroupEntity;
import org.vcpl.triton.rbac.repository.GroupRepositiory;
import org.vcpl.triton.rbac.service.GroupService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.workflow.data.WFApprovalStatusData;
import org.vcpl.triton.workflow.entity.DeferralWorkflowEntity;
import org.vcpl.triton.workflow.entity.WFApprovalStatusEntity;
import org.vcpl.triton.workflow.entity.WorkFlowStageEntity;
import org.vcpl.triton.workflow.repository.DeferralWorkflowStatusRepository;
import org.vcpl.triton.workflow.repository.WFApprovalStatusRepository;
import org.vcpl.triton.workflow.repository.WorkflowStageRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class WFApprovalStatusService implements  IWFApprovalStatus{

    private static final Logger logger = LoggerFactory.getLogger(WFApprovalStatusService.class);

    @Autowired
    private WFApprovalStatusRepository wfApprovalStatusRepository;

    @Autowired
    private DeferralWorkflowStatusRepository deferralWorkflowStatusRepository;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private WorkflowStageRepository workflowStageRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepositiory groupRepositiory;

    @Autowired
    private LoginService loginService;

    @Autowired
    private AssignUnderwriterService assignUnderwriterService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private DeferralReportsService deferralReportsService;

    @Autowired
    private OtherDocumentMasterService otherDocumentMasterService;

    @Autowired
    CustomerInfoService customerInfoService;

    @Autowired
    CustomerInfoRepository customerInfoRepository;

    @Autowired
    TermSheetRepository termSheetRepository;

    @Autowired
    ProgramNormsRepository programNormsRepository;

    public List<WFApprovalStatusEntity> getHistoryOfWFStatus(long id){
        return this.wfApprovalStatusRepository.findAllByApplicationId(id);
    }


    public String getLeadLastWFStatus(String email,String token) {
        JSONObject json=new JSONObject();
        try{
            String jwt=loginService.decodeJWT(token);
            JSONObject json2=new JSONObject(jwt);
            List<?> role = loginService.getRole(json2);
            List<WFApprovalStatusEntity> wfApprovalStatusEntities = this.wfApprovalStatusRepository.getLeadLastWFStatus(role);
            if (wfApprovalStatusEntities.size() > 0) {
                JSONArray array = new JSONArray();
                for (WFApprovalStatusEntity entity : wfApprovalStatusEntities) {
                    JSONObject json1 = new JSONObject();
                    json1.put("id", entity.getId());
                    json1.put("customerName", entity.getAppId().getCustomerInfoEntity().getCustomerName());
                    json1.put("customerType", entity.getAppId().getType());
                    json1.put("currentStage", entity.getStage().getStageId());
                    json1.put("status", entity.getStatus());
                    json1.put("remarks", entity.getRemarks());
                    json1.put("appId", entity.getAppId().getId());
                    json1.put("nextStageId", entity.getStage().getId());
                    json1.put("currentStageLead",entity.getCurrentStageTeam());
                    json1.put("approvedStatus",entity.getApprovedStatus());
                    List<DeferralReportsEntity> deferralReport=deferralReportsService.getDeferralPendingReport(entity.getAppId().getId(),0);
                    List<OtherDocumentMasterEntity> otherDeferralReport= otherDocumentMasterService.findPendingOtherDoc(entity.getAppId().getId(),0L);
                    json1.put("deferralStatus", !deferralReport.isEmpty() || !otherDeferralReport.isEmpty());
                    array.put(json1);
                }
                json.put("wfTableDataList", array);
                json.put("status", true);
            }
        }catch (Exception e){
            e.printStackTrace();
            json.put("status",false);
        }
        return json.toString();
    }

    public Collection<WFApprovalStatusEntity> getRemarks(long appId) {
        Collection<WFApprovalStatusEntity> wfApprovalStatusEntities = wfApprovalStatusRepository.getRemarks(appId);
        return wfApprovalStatusEntities;
    }

    @Override
    public String getLastWFStatus(String email) {
        JSONObject json=new JSONObject();
        try{
            List<WFApprovalStatusEntity> wfApprovalStatusEntities=this.wfApprovalStatusRepository.getLastWFStatus(email);
            if(wfApprovalStatusEntities.size()>0){
                JSONArray array = new JSONArray();
                for(WFApprovalStatusEntity entity:wfApprovalStatusEntities){
                    JSONObject json1=new JSONObject();
                    json1.put("id",entity.getId());
                    json1.put("seqNo",entity.getAppId().getSeqNo());
                    json1.put("customerName", entity.getAppId().getCustomerInfoEntity().getCustomerName());
                    json1.put("customerType",entity.getAppId().getType());
                    json1.put("applicationType",entity.getAppId().getAppType());
                    json1.put("wfType",entity.getAppId().getWfType());
                    json1.put("currentStage",entity.getStage().getStageId());
                    json1.put("status",entity.getStatus());
                    json1.put("remarks",entity.getRemarks());
                    json1.put("appId",entity.getAppId().getId());
                    json1.put("custId",entity.getAppId().getCustomerInfoEntity().getId());
                    json1.put("nextStageId",entity.getStage().getId());
                    json1.put("currentStageLead",entity.getCurrentStageTeam());
                    json1.put("approvedStatus",entity.getApprovedStatus());

                    List<DeferralReportsEntity> deferralReport=deferralReportsService.getDeferralPendingReport(entity.getAppId().getId(),0);
                    List<OtherDocumentMasterEntity> otherDeferralReport= otherDocumentMasterService.findPendingOtherDoc(entity.getAppId().getId(),0L);
                    json1.put("deferralStatus", !deferralReport.isEmpty() || !otherDeferralReport.isEmpty());

                    array.put(json1);
                }
                json.put("wfTableDataList",array);
                json.put("status",true);
            }
        }catch (Exception e){
            e.printStackTrace();
            json.put("status",false);
        }
        return json.toString();
    }

    public String findAnchorName(long id) {
//        JSONObject json = new JSONObject();
        List<Object[]> customerInfoEntity = wfApprovalStatusRepository.findAnchorName(id);
        if(customerInfoEntity.size()>0){
//            JSONArray array = new JSONArray();
            for(Object[] obj:customerInfoEntity){
                return obj[0].toString();
//                JSONObject obj1 = new JSONObject();
//                obj1.put("CustomerName",obj[0]);
//                array.put(obj1);
            }
//            json.put("anchorName",array);
        }
//        return json.toString();
        return null;
    }

    @Override
    public WFApprovalStatusEntity saveWFRenewalFlow(Boolean genType,WFApprovalStatusData wfApprovalStatusData) throws UnsupportedEncodingException {
        WFApprovalStatusEntity pre = wfApprovalStatusRepository.findByApplicationId(wfApprovalStatusData.getAppId());
        WFApprovalStatusEntity wfApprovalStatusEntity = new WFApprovalStatusEntity();
        if (pre == null) {
            if (wfApprovalStatusData.getStatus() == 0) {
                try {
                    String currentStageTeam = "";
                    if (wfApprovalStatusData.getStageId() == 2) {
                        currentStageTeam = "ANCHOR_CPA_LEAD";
                    } else {
                        if(genType && wfApprovalStatusData.getStageId() == 35){
                            currentStageTeam = "CP_CPA_LEAD";
                        }else if(wfApprovalStatusData.getStageId() == 6){
                            currentStageTeam = "CP_BUSINESS_LEAD";
                        }else{
                            currentStageTeam = "CP_BUSINESS_LEAD";
                        }
                    }

                    wfApprovalStatusEntity.setSeqNumber(1);
                    wfApprovalStatusEntity.setStage(workflowStageRepository.getById(wfApprovalStatusData.getStageId()));
                    wfApprovalStatusEntity.setDate(wfApprovalStatusData.getLocalDateAndTime());
                    wfApprovalStatusEntity.setStatus(wfApprovalStatusData.getStatus());
                    wfApprovalStatusEntity.setAppId(applicationRepository.getById(wfApprovalStatusData.getAppId()));
                    wfApprovalStatusEntity.setApproverInfo(wfApprovalStatusData.getApproverInfo());
                    wfApprovalStatusEntity.setRemarks(wfApprovalStatusData.getRemarks());
                    wfApprovalStatusEntity.setCurrentStageTeam(currentStageTeam);
                    wfApprovalStatusEntity.setApprovedStatus(true);
                    wfApprovalStatusEntity.preUpdate();
                    wfApprovalStatusEntity.prePersist();

                    wfApprovalStatusEntity = this.wfApprovalStatusRepository.save(wfApprovalStatusEntity);
//                    wfApprovalStatusEntity = this.wfApprovalStatusRepository.save(transform(wfApprovalStatusData, pre, currentStageTeam));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else{
            wfApprovalStatusEntity = saveWFApproval(wfApprovalStatusData);
        }
        return wfApprovalStatusEntity;
    }

    public WFApprovalStatusEntity saveWFApproval(WFApprovalStatusData wfApprovalStatusData) throws UnsupportedEncodingException {
        logger.info("URL : /saveWFApprovalSatge | OPERATION : POST | Login Id : [{}] | Login Action : [{}]",wfApprovalStatusData.getApproverInfo(),wfApprovalStatusData.getStatus());
        WFApprovalStatusEntity pre = wfApprovalStatusRepository.findByApplicationId(wfApprovalStatusData.getAppId());
        if(pre==null)
        {
            pre = wfApprovalStatusRepository.getWorkflowByAppId(wfApprovalStatusData.getAppId());
        }
        ApplicationEntity applicationEntity = applicationService.getApplicationDetailsById(wfApprovalStatusData.getAppId());
        WFApprovalStatusEntity wfApprovalStatusEntity = new WFApprovalStatusEntity();
        String nextTeamLead=wfApprovalStatusData.getNextApproverInfo();
        wfApprovalStatusData.setApprovedStatus(true);
        String currentApproverName = wfApprovalStatusData.getApproverInfo();
        String nextApproverName = "";
        String anchorName = findAnchorName(wfApprovalStatusData.getAppId());

        if(pre==null){
            if(wfApprovalStatusData.getStatus()==2) {
                try {
                    String currentStageTeam="";
                    if(wfApprovalStatusData.getStageId()==1){
                        currentStageTeam="ANCHOR_CPA_LEAD";
                    }else {
                        currentStageTeam="CP_BUSINESS_LEAD";
                    }
                    currentApproverName = wfApprovalStatusData.getApproverInfo();
                    wfApprovalStatusEntity = this.wfApprovalStatusRepository.save(transform(wfApprovalStatusData,pre,currentStageTeam));
//                    sendMail(wfApprovalStatusData.getApproverInfo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    wfApprovalStatusData.setStatus(0);
                    Long stage = changesStageValue(wfApprovalStatusData.getStageId());
                    pre=wfApprovalStatusEntity;
                    wfApprovalStatusData.setStageId(stage);
                    wfApprovalStatusData.setRemarks("Work in Progress");
                    wfApprovalStatusData=this.groupService.changeNextStageLeadEmail(wfApprovalStatusData);
                    wfApprovalStatusData.setApproverInfo(wfApprovalStatusData.getNextApproverInfo());
//                    wfApprovalStatusData.setNextApproverInfo(nextTeamLead);
                    nextApproverName = wfApprovalStatusData.getApproverInfo();
                    this.wfApprovalStatusRepository.save(transform(wfApprovalStatusData,pre,nextTeamLead));
                    emailService.sendEmail(wfApprovalStatusData.getAppId(),anchorName,currentApproverName,nextApproverName, 2L,"S1");
                    emailService.sendEmail(wfApprovalStatusData.getAppId(),anchorName,nextApproverName,currentApproverName,2L,"S2");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else if(wfApprovalStatusData.getStatus()==2 && pre!=null && (pre.getStage().getId()) == wfApprovalStatusData.getStageId()){
            try{
                Date expDate = null;
//                wfApprovalStatusData=this.groupService.changeNextStageLeadEmail(wfApprovalStatusData);
                wfApprovalStatusEntity = this.wfApprovalStatusRepository.save(updateTransform(wfApprovalStatusData,pre));
                currentApproverName=wfApprovalStatusData.getApproverInfo();
                // To do - calculate tenure for anchor.
                if(wfApprovalStatusData.getStageId()==27){
                    Collection<ProgramNormsEntity> programNormsEntities = programNormsRepository.findByCiId(wfApprovalStatusData.getAppId());
                    if(programNormsEntities.size()>0){
                        expDate = customerInfoService.getBusinessExpiryDate(wfApprovalStatusData.getAppId(),
                                programNormsRepository.getMinProductExpiry(wfApprovalStatusData.getAppId()));
                        for(ProgramNormsEntity programNormsEntity : programNormsEntities){
                            Date prodExpiryDate = customerInfoService.getBusinessExpiryDate(wfApprovalStatusData.getAppId(),
                                    programNormsEntity.getProductExpiry());
                            programNormsEntity.setExpiryDate(prodExpiryDate);
                            programNormsRepository.save(programNormsEntity);
                        }
                        Long custId = applicationRepository.findCustIdByAppId(wfApprovalStatusData.getAppId());
                        CustomerInfoEntity customerInfoEntity = customerInfoRepository.getById(custId);
                        customerInfoEntity.setBusinessExpiry(expDate);
                        customerInfoEntity.setStatus(true);
                        customerInfoRepository.save(customerInfoEntity);
                    }

                } else if (wfApprovalStatusData.getStageId()==28) {
                    List<TermSheetEntity> termSheetEntityList = termSheetRepository.findByAppId(wfApprovalStatusData.getAppId());
                    if(termSheetEntityList.size()>0){
                        expDate = customerInfoService.getBusinessExpiryDate(wfApprovalStatusData.getAppId(),
                                Integer.parseInt(termSheetRepository.getMinFacilityTenure(wfApprovalStatusData.getAppId())));

                        for(TermSheetEntity termSheetEntity : termSheetEntityList){
                            Date prodExpDate = customerInfoService.getBusinessExpiryDate(wfApprovalStatusData.getAppId(), Integer.parseInt(termSheetEntity.getRenewalPeriod()));
                            termSheetEntity.setExpiryDate(prodExpDate);
                            termSheetRepository.save(termSheetEntity);
                        }
                        Long custId = applicationRepository.findCustIdByAppId(wfApprovalStatusData.getAppId());
                        CustomerInfoEntity customerInfoEntity = customerInfoRepository.getById(custId);
                        customerInfoEntity.setBusinessExpiry(expDate);
                        customerInfoEntity.setStatus(true);
                        customerInfoRepository.save(customerInfoEntity);
                    }
                }

            }catch(Exception e){
                e.printStackTrace();
            }
            try {
                if (wfApprovalStatusData.getStageId() == 23) {
                    List<GroupEntity> nextApproverPermission = groupRepositiory.findNextStageApprovers("CP_CREDIT_APPROVAL_COMMITTEE_LEAD");
                    if (nextApproverPermission.size() > 0) {
                        Long nextStageId = wfApprovalStatusData.getStageId();
                        for (GroupEntity group : nextApproverPermission) {
                            Long stage = changesStageValue(nextStageId);
                            wfApprovalStatusData.setRemarks("Work In Progress");
                            wfApprovalStatusData.setStatus(0);
                            wfApprovalStatusData.setStageId(stage);
                            wfApprovalStatusData.setNextApproverInfo(group.getGroupName());
                            wfApprovalStatusData.setApprovedStatus(false);
                            boolean flag = true;
                            if (pre.getStage().getStageId().equals("A5") || pre.getStage().getStageId().equals("CP11")) {
                                findDeferralValue(wfApprovalStatusData,pre,anchorName);
                                flag = false;
                            }
                            if (flag) {
                                wfApprovalStatusData = this.groupService.changeNextStageLeadEmail(wfApprovalStatusData);
                                wfApprovalStatusData.setApproverInfo(wfApprovalStatusData.getNextApproverInfo());
                                wfApprovalStatusEntity = this.wfApprovalStatusRepository.save(transform(wfApprovalStatusData, pre, group.getGroupName()));
                                //sendMail(wfApprovalStatusData.getApproverInfo());
                                nextApproverName = wfApprovalStatusData.getApproverInfo();
                                emailService.sendEmail(wfApprovalStatusData.getAppId(), anchorName, nextApproverName, currentApproverName, 2L, "S2");
                            }
                        }
                        emailService.sendEmail(wfApprovalStatusData.getAppId(), anchorName, currentApproverName, nextApproverName, 2L, "S1");
                    }
                } else {
                    if (wfApprovalStatusData.getStageId() == 9 || wfApprovalStatusData.getStageId() == 24 || wfApprovalStatusData.getStageId() ==22) {
                        Optional<AssignUnderwriterEntity> underWriter = this.assignUnderwriterService.getAssignUnderwriter(wfApprovalStatusData.getAppId());
                        Long stage = changesStageValue(wfApprovalStatusData.getStageId());
                        wfApprovalStatusData.setRemarks("Work In Progress");
                        wfApprovalStatusData.setStatus(0);
                        wfApprovalStatusData.setStageId(stage);
                        wfApprovalStatusData.setNextApproverInfo(wfApprovalStatusData.getNextApproverInfo());
                        wfApprovalStatusData.setApprovedStatus(true);
                        boolean flag = !pre.getStage().getStageId().equals("A5") && !pre.getStage().getStageId().equals("CP11");
                        if (flag) {
//                            wfApprovalStatusData = this.groupService.changeNextStageLeadEmail(wfApprovalStatusData);
                            wfApprovalStatusData.setApproverInfo(underWriter.get().getAssignTo());
                            wfApprovalStatusEntity = this.wfApprovalStatusRepository.save(transform(wfApprovalStatusData, pre, nextTeamLead));
                            nextApproverName = wfApprovalStatusData.getApproverInfo();
                            emailService.sendEmail(wfApprovalStatusData.getAppId(), anchorName, currentApproverName, nextApproverName, 2L, "S1");
                            emailService.sendEmail(wfApprovalStatusData.getAppId(), anchorName, nextApproverName, currentApproverName, 2L, "S2");
                        }
                    } else {
                        Long stage = changesStageValue(wfApprovalStatusData.getStageId());
                        if(stage!=null){
                            wfApprovalStatusData.setRemarks("Work In Progress");
                            wfApprovalStatusData.setStatus(0);
                            wfApprovalStatusData.setStageId(stage);
                            wfApprovalStatusData.setNextApproverInfo(wfApprovalStatusData.getNextApproverInfo());
                            wfApprovalStatusData.setApprovedStatus(true);
                            boolean flag = true;
                            if (pre.getStage().getStageId().equals("A5") || pre.getStage().getStageId().equals("CP11")) {
                                flag = false;
                                findDeferralValue(wfApprovalStatusData, pre, anchorName);
                            } else if (pre.getStage().getStageId().equals("A6") || pre.getStage().getStageId().equals("CP12")) {
                                flag = false;
                                emailService.sendEmail(wfApprovalStatusData.getAppId(), anchorName, currentApproverName, nextApproverName, 2L, "O");
                            }
                            if (flag) {
                                wfApprovalStatusData = this.groupService.changeNextStageLeadEmail(wfApprovalStatusData);
                                wfApprovalStatusData.setApproverInfo(wfApprovalStatusData.getNextApproverInfo());
                                wfApprovalStatusEntity = this.wfApprovalStatusRepository.save(transform(wfApprovalStatusData, pre, nextTeamLead));
                                nextApproverName = wfApprovalStatusData.getApproverInfo();
                                emailService.sendEmail(wfApprovalStatusData.getAppId(), anchorName, currentApproverName, nextApproverName, 2L, "S1");
                                emailService.sendEmail(wfApprovalStatusData.getAppId(), anchorName, nextApproverName, currentApproverName, 2L, "S2");
                            }
                        }else{
                            emailService.sendEmail(wfApprovalStatusData.getAppId(), anchorName, currentApproverName, nextApproverName, 2L, "O");
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(wfApprovalStatusData.getStatus()==-2 && pre.getId()>0){
            //---- reject ----
            if(wfApprovalStatusData.getStageId()==18) {
                wfApprovalStatusEntity = rejectWFApproval(wfApprovalStatusData, pre,anchorName);
            }else {
                wfApprovalStatusEntity  = rejectWFApproval(wfApprovalStatusData, pre,anchorName);
                wfApprovalStatusEntity = this.wfApprovalStatusRepository.save(wfApprovalStatusEntity);
                emailService.sendEmail(wfApprovalStatusData.getAppId(),anchorName,currentApproverName,nextApproverName, -2L,"R");
//            sendMail(wfApprovalStatusData.getApproverInfo());
            }
        }
        else if(wfApprovalStatusData.getStatus()==-1 && pre.getId()>0){
            //---- return ----
            List<WFApprovalStatusEntity> wf= returnWF(wfApprovalStatusData,pre,anchorName);
            wfApprovalStatusEntity = wf.get(0);
        }
        else if(wfApprovalStatusData.getStatus()==1&&pre.getId()>0){
            //----- Approved ------
            wfApprovalStatusEntity = approvalWF(wfApprovalStatusData,pre,anchorName);
        }
        return wfApprovalStatusEntity;
    }

    public Collection<DeferralReportsEntity> findDeferralValue(WFApprovalStatusData wfApprovalStatusData,WFApprovalStatusEntity wfApprovalStatusEntity,String anchorName) throws UnsupportedEncodingException {
        Collection<DeferralReportsEntity> deferralReport = deferralReportsService.getCompeletedDeferralDataByAppIdAndStatus(wfApprovalStatusData.getAppId());
        List<OtherDocumentMasterEntity> otherDeferralReport= otherDocumentMasterService.findPendingOtherDoc(wfApprovalStatusData.getAppId(),0L);
        if(!deferralReport.isEmpty() || !otherDeferralReport.isEmpty()) {
            defferalWorkFlow(wfApprovalStatusData,wfApprovalStatusEntity,anchorName);
        }else{
            //Expiry Date Calculation.
            Date expDate = null;
            // To do - calculate tenure for anchor.
            if(wfApprovalStatusEntity.getStage().getStageId().equals("A5")){
                Collection<ProgramNormsEntity> programNormsEntities = programNormsRepository.findByCiId(wfApprovalStatusData.getAppId());
                if(programNormsEntities.size()>0){
                    expDate = customerInfoService.getBusinessExpiryDate(wfApprovalStatusData.getAppId(),
                            programNormsRepository.getMinProductExpiry(wfApprovalStatusData.getAppId()));
                    for(ProgramNormsEntity programNormsEntity : programNormsEntities){
                        Date prodExpiryDate = customerInfoService.getBusinessExpiryDate(wfApprovalStatusData.getAppId(),
                                programNormsEntity.getProductExpiry());
                        programNormsEntity.setExpiryDate(prodExpiryDate);
                        programNormsRepository.save(programNormsEntity);
                    }
                    Long custId = applicationRepository.findCustIdByAppId(wfApprovalStatusData.getAppId());
                    CustomerInfoEntity customerInfoEntity = customerInfoRepository.getById(custId);
                    customerInfoEntity.setBusinessExpiry(expDate);
                    customerInfoEntity.setStatus(true);
                    customerInfoRepository.save(customerInfoEntity);
                }
            } else if (wfApprovalStatusEntity.getStage().getStageId().equals("CP11")) {
                List<TermSheetEntity> termSheetEntityList = termSheetRepository.findByAppId(wfApprovalStatusData.getAppId());
                if(termSheetEntityList.size()>0){
                    expDate = customerInfoService.getBusinessExpiryDate(wfApprovalStatusData.getAppId(),
                            Integer.parseInt(termSheetRepository.getMinFacilityTenure(wfApprovalStatusData.getAppId())));

                    for(TermSheetEntity termSheetEntity : termSheetEntityList){
                        Date prodExpDate = customerInfoService.getBusinessExpiryDate(wfApprovalStatusData.getAppId(), Integer.parseInt(termSheetEntity.getRenewalPeriod()));
                        termSheetEntity.setExpiryDate(prodExpDate);
                        termSheetRepository.save(termSheetEntity);
                    }
                    Long custId = applicationRepository.findCustIdByAppId(wfApprovalStatusData.getAppId());
                    CustomerInfoEntity customerInfoEntity = customerInfoRepository.getById(custId);
                    customerInfoEntity.setBusinessExpiry(expDate);
                    customerInfoEntity.setStatus(true);
                    customerInfoRepository.save(customerInfoEntity);
                }
            }

        }
        return deferralReport;
    }

    public WFApprovalStatusData defferalWorkFlow(WFApprovalStatusData wfApprovalStatusData, WFApprovalStatusEntity pre, String anchorName) throws UnsupportedEncodingException {
        String nextApproverName="";
        String currentApprover=wfApprovalStatusData.getApproverInfo();
        String nextTeam = wfApprovalStatusData.getNextApproverInfo();
        WorkFlowStageEntity stage = changesStageValue(wfApprovalStatusData);
        wfApprovalStatusData.setRemarks("Work In Progress");
        wfApprovalStatusData.setStatus(0);
        wfApprovalStatusData.setStageId(stage.getId());
        wfApprovalStatusData.setNextApproverInfo(wfApprovalStatusData.getNextApproverInfo());
        wfApprovalStatusData.setApprovedStatus(true);
        boolean flag = !pre.getStage().getStageId().equals("A6") && !pre.getStage().getStageId().equals("CP12");
        if (flag) {
            wfApprovalStatusData = this.groupService.changeNextStageLeadEmail(wfApprovalStatusData);
            wfApprovalStatusData.setApproverInfo(wfApprovalStatusData.getNextApproverInfo());
            pre = this.wfApprovalStatusRepository.save(transform(wfApprovalStatusData, pre, nextTeam));
            nextApproverName = wfApprovalStatusData.getApproverInfo();
            emailService.sendEmail(wfApprovalStatusData.getAppId(), anchorName, currentApprover, nextApproverName, 2L, "S1");
            emailService.sendEmail(wfApprovalStatusData.getAppId(), anchorName, nextApproverName, currentApprover, 2L, "S1");
        }
        return wfApprovalStatusData;
    }


    public WFApprovalStatusEntity approvalWF(WFApprovalStatusData wfApprovalStatusData, WFApprovalStatusEntity wfApprovalStatusEntity, String customerInfoEntity) throws UnsupportedEncodingException {
        Optional<WFApprovalStatusEntity> pendingApprovalData = wfApprovalStatusRepository.findApprovedPendingValue(wfApprovalStatusData.getAppId(),wfApprovalStatusData.getNextApproverInfo());
        wfApprovalStatusData.setApprovedStatus(true);
        wfApprovalStatusData.setStatus(2);
        wfApprovalStatusEntity = this.wfApprovalStatusRepository.save(updateTransform(wfApprovalStatusData,pendingApprovalData.get()));
        String currentApprover=wfApprovalStatusData.getApproverInfo();
        String nextApprover="";
        List<WFApprovalStatusEntity> checkPendingApprovalData= wfApprovalStatusRepository.findApprovedPendingValue(wfApprovalStatusData.getAppId());
        if(checkPendingApprovalData.isEmpty()) {
            Long stage = changesStageValue(wfApprovalStatusData.getStageId());
            wfApprovalStatusData.setRemarks("Work In Progress");
            wfApprovalStatusData.setStatus(0);
            wfApprovalStatusData.setStageId(stage);
            Optional<GroupEntity> wfnextApproval=changesStageValueWithEntity(stage);
            wfApprovalStatusData.setNextApproverInfo(wfnextApproval.get().getGroupName());
            boolean flag = true;
            if (wfApprovalStatusEntity.getStage().getStageId().equals("A5") || wfApprovalStatusEntity.getStage().getStageId().equals("CP11")) {
                flag = false;
            }
            if (flag) {
                wfApprovalStatusData = this.groupService.changeNextStageLeadEmail(wfApprovalStatusData);
                wfApprovalStatusData.setApproverInfo(wfApprovalStatusData.getNextApproverInfo());
                wfApprovalStatusEntity = this.wfApprovalStatusRepository.save(transform(wfApprovalStatusData, wfApprovalStatusEntity, wfnextApproval.get().getGroupName()));
                nextApprover=wfApprovalStatusData.getApproverInfo();
                emailService.sendEmail(wfApprovalStatusData.getAppId(),customerInfoEntity,currentApprover,nextApprover,1L,"A1");
                emailService.sendEmail(wfApprovalStatusData.getAppId(),customerInfoEntity,nextApprover,currentApprover, 1L,"A2");
            }
        }else{
            wfApprovalStatusData = this.groupService.changeNextStageLeadEmail(wfApprovalStatusData);
            emailService.sendEmail(wfApprovalStatusData.getAppId(),customerInfoEntity,currentApprover,checkPendingApprovalData.get(0).getApproverInfo(), 1L,"A1");
        }
        return wfApprovalStatusEntity;
    }


    public WorkFlowStageEntity changesStageValue(WFApprovalStatusData wfApprovalStatusData){
        String stageName="";
        int stageEndWith;
        List<WorkFlowStageEntity> currentStageData = workflowStageRepository.findByStageValue(wfApprovalStatusData.getStageId());
        if(currentStageData!=null){
            String tempLastChar;
            if(currentStageData.get(0).getStageId().length()==2){
                tempLastChar= currentStageData.get(0).getStageId().substring(currentStageData.get(0).getStageId().length()-1);
                stageEndWith=Integer.parseInt(tempLastChar);
                stageEndWith=stageEndWith+1;
                stageName="A"+stageEndWith;
            }else if(currentStageData.get(0).getStageId().length()==3){
                tempLastChar= currentStageData.get(0).getStageId().substring(currentStageData.get(0).getStageId().length()-1);
                stageEndWith=Integer.parseInt(tempLastChar);
                stageEndWith+=1;
                stageName="CP"+stageEndWith;
            }else if(currentStageData.get(0).getStageId().length()==4){
                tempLastChar= currentStageData.get(0).getStageId().substring(currentStageData.get(0).getStageId().length()-2);
                stageEndWith=Integer.parseInt(tempLastChar);
                stageEndWith=stageEndWith!=12?stageEndWith+1:stageEndWith;
                stageName="CP"+stageEndWith;
            }
        }
        return workflowStageRepository.findByStageId(stageName);
//        return stageEntity;
    }

    public Long changesStageValue(Long stageId){
        String stageName="";
        int stageEndWith;
        List<WorkFlowStageEntity> currentStageData = workflowStageRepository.findByStageValue(stageId);
        if(currentStageData!=null){
            String tempLastChar;
            if(currentStageData.get(0).getStageId().length()==2){
                tempLastChar= currentStageData.get(0).getStageId().substring(currentStageData.get(0).getStageId().length()-1);
                stageEndWith=Integer.parseInt(tempLastChar);
                stageEndWith=stageEndWith!=5?stageEndWith+1:stageEndWith;
                stageName="A"+stageEndWith;
            }else if(currentStageData.get(0).getStageId().length()==3){
                tempLastChar= currentStageData.get(0).getStageId().substring(currentStageData.get(0).getStageId().length()-1);
                stageEndWith=Integer.parseInt(tempLastChar);
                stageEndWith+=1;
                stageName="CP"+stageEndWith;
            }else if(currentStageData.get(0).getStageId().length()==4){
                tempLastChar= currentStageData.get(0).getStageId().substring(currentStageData.get(0).getStageId().length()-2);
                stageEndWith=Integer.parseInt(tempLastChar);
                stageEndWith=stageEndWith!=12?stageEndWith+1:stageEndWith;
                stageName="CP"+stageEndWith;
            }
        }
        WorkFlowStageEntity stageEntity=workflowStageRepository.findByStageId(stageName);
        if(stageEntity!=null)
            return stageEntity.getId();
        else
            return null;
    }

    public Optional<GroupEntity> changesStageValueWithEntity(Long stageId){
//        String stageName="";
//        int stageEndWith;
        List<WorkFlowStageEntity> currentStageData = workflowStageRepository.findByStageValue(stageId);
//        if(currentStageData!=null){
//            String tempLastChar;
//            if(currentStageData.get(0).getStageId().length()==2){
//                tempLastChar= currentStageData.get(0).getStageId().substring(currentStageData.get(0).getStageId().length()-1);
//                stageEndWith=Integer.valueOf(tempLastChar);
//                stageEndWith=stageEndWith!=5?stageEndWith+1:stageEndWith;
//                stageName="A"+stageEndWith;
//            }else if(currentStageData.get(0).getStageId().length()==3){
//                tempLastChar= currentStageData.get(0).getStageId().substring(currentStageData.get(0).getStageId().length()-1);
//                stageEndWith=Integer.valueOf(tempLastChar);
//                stageEndWith+=1;
//                stageName="CP"+stageEndWith;
//            }else if(currentStageData.get(0).getStageId().length()==4){
//                stageName="CP"+11;
//            }
//        }
        WorkFlowStageEntity stageEntity=workflowStageRepository.findByStageId(currentStageData.get(0).getStageId());
        return groupRepositiory.findById(stageEntity.getGroupId().getId());
//        return stageEntity;
    }

    public List<WFApprovalStatusEntity> returnWF(WFApprovalStatusData wfApprovalStatusData, WFApprovalStatusEntity wfApprovalStatusEntity,String customerInfoEntity) throws UnsupportedEncodingException{
        List<WFApprovalStatusEntity> previousApprovedStage=new ArrayList<WFApprovalStatusEntity>();
        try {
            String currentApprover="";
            String nextApprover="";
            String tempApprover="";
            if (wfApprovalStatusData.getStageId() == 18) {
                List<WFApprovalStatusEntity> wfApproval = wfApprovalStatusRepository.findByPreviousAprroverStageByMultipleVerificationData(wfApprovalStatusData.getStageId(), wfApprovalStatusData.getAppId());
                for (WFApprovalStatusEntity work : wfApproval) {
                    wfApprovalStatusData.setStatus(-1);
                    wfApprovalStatusData.setApprovedStatus(true);
                    if (work.getCurrentStageTeam().equals(wfApprovalStatusData.getNextApproverInfo())) {
                        wfApprovalStatusData.setApproverInfo(wfApprovalStatusData.getApproverInfo());
                        wfApprovalStatusData.setRemarks(wfApprovalStatusData.getRemarks());
                        currentApprover=wfApprovalStatusData.getApproverInfo();
                        tempApprover=work.getApproverInfo();
                    }else{
                        currentApprover=wfApprovalStatusData.getApproverInfo();
                        tempApprover=work.getApproverInfo();
                        wfApprovalStatusData.setRemarks(wfApprovalStatusData.getRemarks());
                        wfApprovalStatusData.setApproverInfo(work.getApproverInfo());
                    }
                    wfApprovalStatusRepository.save(updateTransform(wfApprovalStatusData, work));
//                    emailService.sendEmail(wfApprovalStatusData.getAppId(),customerInfoEntity,currentApprover,"", wfApprovalStatusData.getStatus(),"RT1");
                }
            } else {
                currentApprover=wfApprovalStatusData.getApproverInfo();
                wfApprovalStatusRepository.save(updateTransform(wfApprovalStatusData, wfApprovalStatusEntity));
//                emailService.sendEmail(wfApprovalStatusData.getAppId(),customerInfoEntity,currentApprover,wfApprovalStatusData.getApproverInfo(), wfApprovalStatusData.getStatus(),"RT1");
            }


//            New Row will be created

            Long preStageId = findPreviousStageValue(wfApprovalStatusData.getStageId());
            Optional<WorkFlowStageEntity> workFlowStage = workflowStageRepository.findById(preStageId);
            if(preStageId==18) {
                previousApprovedStage = wfApprovalStatusRepository.findByPreviousAprroverStage(preStageId, wfApprovalStatusData.getAppId());
                int seq = wfApprovalStatusEntity.getSeqNumber();
                for (WFApprovalStatusEntity work : previousApprovedStage) {
                    wfApprovalStatusEntity.setSeqNumber(seq);
                    wfApprovalStatusData.setStageId(work.getStage().getId());
                    wfApprovalStatusData.setRemarks("Work in Progress");
                    wfApprovalStatusData.setStatus(0);
                    if (work.getStage().getId() == 18) {
                        wfApprovalStatusData.setApprovedStatus(false);
                    } else {
                        wfApprovalStatusData.setApprovedStatus(true);
                    }
                    wfApprovalStatusData.setApproverInfo(work.getApproverInfo());
                    wfApprovalStatusRepository.save(transform(wfApprovalStatusData, wfApprovalStatusEntity, work.getCurrentStageTeam()));
                    nextApprover=wfApprovalStatusData.getApproverInfo();
                    int count = 1;
                    if(count==1)
                        emailService.sendEmail(wfApprovalStatusData.getAppId(),customerInfoEntity,nextApprover,currentApprover, wfApprovalStatusData.getStatus(),"RT2");
                    else
                        emailService.sendEmail(wfApprovalStatusData.getAppId(),customerInfoEntity,nextApprover,tempApprover, wfApprovalStatusData.getStatus(),"RT2");
                }
                emailService.sendEmail(wfApprovalStatusData.getAppId(),customerInfoEntity,currentApprover, nextApprover,wfApprovalStatusData.getStatus(),"RT1");
            }else {
                previousApprovedStage = wfApprovalStatusRepository.findByPreviousAprroverStage(preStageId, wfApprovalStatusData.getAppId());
                int seq = wfApprovalStatusEntity.getSeqNumber();
                for (WFApprovalStatusEntity work : previousApprovedStage) {
                    wfApprovalStatusEntity.setSeqNumber(seq);
                    wfApprovalStatusData.setStageId(work.getStage().getId());
//                        wfApprovalStatusData.setRemarks(wfApprovalStatusData.getRemarks());
                    wfApprovalStatusData.setRemarks("Work in Progress");
                    wfApprovalStatusData.setStatus(0);
                    wfApprovalStatusData.setApprovedStatus(true);
                    wfApprovalStatusData.setApproverInfo(work.getApproverInfo());
                    wfApprovalStatusRepository.save(transform(wfApprovalStatusData, wfApprovalStatusEntity, work.getCurrentStageTeam()));
                    nextApprover=wfApprovalStatusData.getApproverInfo();
                    emailService.sendEmail(wfApprovalStatusData.getAppId(),customerInfoEntity,nextApprover,currentApprover, -1L,"RT2");
                    emailService.sendEmail(wfApprovalStatusData.getAppId(),customerInfoEntity,nextApprover,tempApprover, -1L,"RT2");
                    emailService.sendEmail(wfApprovalStatusData.getAppId(),customerInfoEntity,currentApprover,nextApprover, -1L,"RT1");
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return previousApprovedStage;
    }

    private WFApprovalStatusEntity updateTransform(WFApprovalStatusData wfApprovalStatusData,WFApprovalStatusEntity wfApprovalStatusEntity) {

        WFApprovalStatusEntity entity=wfApprovalStatusRepository.findById(wfApprovalStatusEntity.getId()).orElse(null);
        try {
            entity.setStage(workflowStageRepository.getById((wfApprovalStatusData.getStageId())));
            entity.setApproverInfo(wfApprovalStatusData.getApproverInfo());
            entity.setStatus(wfApprovalStatusData.getStatus());
            entity.setRemarks(wfApprovalStatusData.getRemarks());
            entity.setDate(wfApprovalStatusData.getLocalDateAndTime());
            entity.setAppId(applicationRepository.getById(wfApprovalStatusData.getAppId()));
            entity.setCurrentStageTeam(wfApprovalStatusEntity.getCurrentStageTeam());
            if (wfApprovalStatusData.getStatus() == -2) {
                entity.setSeqNumber(wfApprovalStatusEntity.getSeqNumber());
            } else {
                entity.setSeqNumber(entity.getSeqNumber());
            }
            entity.setApprovedStatus(wfApprovalStatusData.getApprovedStatus());
            entity.preUpdate();
            entity.setCreatedTime(entity.getCreatedTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        return entity;
    }

    private WFApprovalStatusEntity transform(WFApprovalStatusData wfApprovalStatusData, WFApprovalStatusEntity pre,String nextTeamLead){
        WFApprovalStatusEntity entity=new WFApprovalStatusEntity();
        try{
            if(wfApprovalStatusData.getAppId()>0) {
                if(wfApprovalStatusData.getStatus()==-1)
                {
                    entity.setStage(workflowStageRepository.getById(wfApprovalStatusData.getStageId()));
                }else {
                    entity.setStage(workflowStageRepository.getById(wfApprovalStatusData.getStageId()));
                }
                entity.setDate(wfApprovalStatusData.getLocalDateAndTime());
                entity.setStatus(wfApprovalStatusData.getStatus());
                entity.setAppId(applicationRepository.getById(wfApprovalStatusData.getAppId()));
                entity.setApproverInfo(wfApprovalStatusData.getApproverInfo());
                entity.setRemarks(wfApprovalStatusData.getRemarks());
                if(pre==null) {
                    entity.setSeqNumber(1);
                }else{
                    entity.setSeqNumber(pre.getSeqNumber()+1);
                }
                entity.setCurrentStageTeam(nextTeamLead);
                entity.setApprovedStatus(wfApprovalStatusData.getApprovedStatus());
                entity.preUpdate();
                entity.prePersist();
            }else{
                throw new DataIntegrityViolationException("Not Valid Customer");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return entity;
    }

    private Long findPreviousStageValue(Long stageId){
        Optional<WorkFlowStageEntity> currentStageData = workflowStageRepository.findById(stageId);
        String stageName="";
        int stageEndWith;
//        List<WorkFlowStageEntity> currentStageData = workflowStageRepository.findByStageValue(stageId);
        if(currentStageData!=null){
            String tempLastChar;
            if(currentStageData.get().getStageId().length()==2){
                tempLastChar= currentStageData.get().getStageId().substring(currentStageData.get().getStageId().length()-1);
                stageEndWith=Integer.valueOf(tempLastChar);
                stageEndWith=stageEndWith!=1?stageEndWith-1:stageEndWith;
                stageName="A"+stageEndWith;
            }else if(currentStageData.get().getStageId().length()==3){
                tempLastChar= currentStageData.get().getStageId().substring(currentStageData.get().getStageId().length()-1);
                stageEndWith=Integer.valueOf(tempLastChar);
                stageEndWith-=1;
                stageName="CP"+stageEndWith;
            }else if(currentStageData.get().getStageId().length()==4){
                tempLastChar= currentStageData.get().getStageId().substring(currentStageData.get().getStageId().length()-2);
                stageEndWith=Integer.valueOf(tempLastChar);
                stageEndWith-=1;
                stageName="CP"+stageEndWith;
            }
        }
        WorkFlowStageEntity stageEntity=workflowStageRepository.findByStageId(stageName);
        return stageEntity.getId();
    }

    private WFApprovalStatusEntity returnWFApproval(WFApprovalStatusData wfApprovalStatusData, WFApprovalStatusEntity wfApprovalStatusEntity) throws UnsupportedEncodingException {
        Long stageId = wfApprovalStatusData.getStageId();
        String preLead = wfApprovalStatusData.getNextApproverInfo();
        Optional<WFApprovalStatusEntity> preApprover= wfApprovalStatusRepository.findByPreviousAprrover(wfApprovalStatusData.getAppId());
        try {
            wfApprovalStatusData.setStageId(wfApprovalStatusData.getStageId());
            WFApprovalStatusEntity entity = updateTransform(wfApprovalStatusData, wfApprovalStatusEntity);
            this.wfApprovalStatusRepository.save(entity);
            wfApprovalStatusData.setRemarks("Work in Progress");
            wfApprovalStatusData.setStatus(0);
            wfApprovalStatusData.setStageId(preApprover.get().getStage().getId());
            wfApprovalStatusData.setApproverInfo(preApprover.get().getApproverInfo());
//            wfApprovalStatusData = this.groupService.changeNextStageLeadEmail(wfApprovalStatusData);
//            wfApprovalStatusData.setNextApproverInfo(preApprover.get().getCurrentStageTeam());
        }catch (Exception e){
            logger.error("Exception : {}",e);
            e.printStackTrace();
        }
        return this.wfApprovalStatusRepository.save(transform(wfApprovalStatusData, preApprover.get(),preApprover.get().getCurrentStageTeam()));
    }

    private WFApprovalStatusEntity rejectWFApproval(WFApprovalStatusData wfApprovalStatusData,WFApprovalStatusEntity wfApprovalStatusEntity,String anchorName) {
        if(wfApprovalStatusData.getStageId() == 18){
            List<WFApprovalStatusEntity> workFlowData = this.wfApprovalStatusRepository.findByPreviousAprroverStageByMultipleVerificationData(wfApprovalStatusData.getStageId(),wfApprovalStatusData.getAppId());
            for(WFApprovalStatusEntity work : workFlowData) {
                work.setApprovedStatus(true);
//                WFApprovalStatusEntity entity = rejectWFApproval(wfApprovalStatusData, work);
                work.setStatus(-2);
                if(wfApprovalStatusData.getNextApproverInfo().equals(work.getCurrentStageTeam())){
                    work.setApproverInfo(wfApprovalStatusData.getApproverInfo());
                }
                work.preUpdate();
                emailService.sendEmail(wfApprovalStatusData.getAppId(),anchorName,work.getApproverInfo(),"", -2L,"R");
                this.wfApprovalStatusRepository.save(work);
            }
            return workFlowData.get(0);
        }else {
            return updateTransform(wfApprovalStatusData, wfApprovalStatusEntity);
        }
    }

    public String sendMail(String email){
        final String uri = "http://localhost:8080/notification/email/send/" + email;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }



    @Override
    public Collection<WFApprovalStatusEntity> findExistingAnchor(String stage,String status) {
        logger.info(" | URL | findExistingAnchor | OPERATION | " + "GETById findExistingAnchor");
        try {
            if(status.equals("2")) {
                if(stage.equals("A5")){
                    return wfApprovalStatusRepository.findAnchor();
                }else if(stage.equals("CP11")){
                    return wfApprovalStatusRepository.findCounterParty();
                }
//                return wfApprovalStatusRepository.findAnchor(stage);
            }else if(status.equals("-2") || status.equals("0")){
                return wfApprovalStatusRepository.findAnchorStatus(status);
            }
        } catch (Exception ex) {
            logger.error(" | URL |  findExistingAnchor | OPERATION | " + " Error |" + ex.getMessage());
        }
        return null;
    }

    @Override
    public WFApprovalStatusEntity changeAssigne(Map<Long, String> idWithEmail, HttpServletResponse response) {
        Long id = Long.valueOf(idWithEmail.get("id"));
        String email = idWithEmail.get("email");

        WFApprovalStatusEntity wfApprovalStatusEntity = wfApprovalStatusRepository.findById(id).orElse(null);
        WFApprovalStatusEntity updatedWfApprovalStatusEntity= new WFApprovalStatusEntity();
        updatedWfApprovalStatusEntity.setId(wfApprovalStatusEntity.getId());
        updatedWfApprovalStatusEntity.setAppId(wfApprovalStatusEntity.getAppId());
        updatedWfApprovalStatusEntity.setApproverInfo(email);
        updatedWfApprovalStatusEntity.setSeqNumber(wfApprovalStatusEntity.getSeqNumber());
        updatedWfApprovalStatusEntity.setStatus(wfApprovalStatusEntity.getStatus());
        updatedWfApprovalStatusEntity.setDate(wfApprovalStatusEntity.getDate());
        updatedWfApprovalStatusEntity.setRemarks(wfApprovalStatusEntity.getRemarks());
        updatedWfApprovalStatusEntity.setStage(wfApprovalStatusEntity.getStage());
        updatedWfApprovalStatusEntity.setCurrentStageTeam(wfApprovalStatusEntity.getCurrentStageTeam());
        updatedWfApprovalStatusEntity.setApprovedStatus(wfApprovalStatusEntity.getApprovedStatus());
        updatedWfApprovalStatusEntity.setCreatedTime(wfApprovalStatusEntity.getCreatedTime());
        updatedWfApprovalStatusEntity.preUpdate();

        return wfApprovalStatusRepository.save(updatedWfApprovalStatusEntity);
    }

    @Override
    public Collection<WFApprovalStatusEntity> findAnchorAsDescOrder() {
        logger.info(" | URL | findAnchorAsDescOrder | OPERATION | " + "GETById findExistingAnchor");
        try {
            return wfApprovalStatusRepository.findAnchorAsDescOrder();
        } catch (Exception ex) {
            logger.error(" | URL |  findAnchorAsDescOrder | OPERATION | " + " Error |" + ex.getMessage());
        }
        return null;
    }

    @Override
    public String getWFStatus(Long id) {
        JSONObject json=new JSONObject();
        try {
            List<WFApprovalStatusEntity> wfApprovalStatusEntities = wfApprovalStatusRepository.getWFStatus(id);
            List<DeferralWorkflowEntity> deferralWorkflowEntities = deferralWorkflowStatusRepository.getWFStatus(id);
            if(wfApprovalStatusEntities.size() == 0 && deferralWorkflowEntities.size() == 0) {
                json.put("custId",id);
                json.put("status",true);
            }else{
                json.put("custId",id);
                json.put("status",false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return json.toString();
    }

    @Override
    public List<WFApprovalStatusEntity> findOnboardedCustomers(int customerType) {
        List<WFApprovalStatusEntity> wfApprovalStatusEntityList = new ArrayList<>();
        List<CustomerInfoEntity> customerInfoEntities = customerInfoRepository.viewActiveCustomers(customerType);
        for(CustomerInfoEntity customerInfoEntity: customerInfoEntities){
            ApplicationEntity applicationEntity = applicationRepository.findByCustId(customerInfoEntity.getId());
            WFApprovalStatusEntity wfApprovalStatusEntity = wfApprovalStatusRepository.getWorkflowByAppId(applicationEntity.getId());
            if(wfApprovalStatusEntity!=null){
                wfApprovalStatusEntityList.add(wfApprovalStatusEntity);
            }
        }
        return wfApprovalStatusEntityList;
    }
}