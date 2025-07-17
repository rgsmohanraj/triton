package org.vcpl.triton.workflow.service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.service.ApplicationService;
import org.vcpl.triton.dms.docManagement.entity.DeferralReportsEntity;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentMasterEntity;
import org.vcpl.triton.dms.docManagement.service.DeferralReportsService;
import org.vcpl.triton.dms.docManagement.service.OtherDocumentMasterService;
import org.vcpl.triton.login.service.LoginService;
import org.vcpl.triton.notification.service.EmailService;
import org.vcpl.triton.rbac.service.GroupService;
import org.vcpl.triton.workflow.data.WFApprovalStatusData;
import org.vcpl.triton.workflow.entity.DeferralWorkflowEntity;
import org.vcpl.triton.workflow.entity.WFApprovalStatusEntity;
import org.vcpl.triton.workflow.entity.WorkFlowStageEntity;
import org.vcpl.triton.workflow.repository.DeferralWorkflowStatusRepository;
import org.vcpl.triton.workflow.repository.WorkflowStageRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Ajith.Rameshkumar
 * This class using CRUD operations for Deferral Workflow
 */
@Service
@Slf4j
public class DeferralWorkflowService implements  IDeferralWorkflow{

    @Autowired
    private DeferralWorkflowStatusRepository deferralWorkflowStatusRepository;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private WFApprovalStatusService wfApprovalStatusService;

    @Autowired
    private WorkflowStageRepository workflowStageRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private DeferralReportsService deferralReportsService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private OtherDocumentMasterService otherDocumentMasterService;


    public List<DeferralWorkflowEntity> getHistoryOfWFStatus(long id){
        log.info("History of Workflow {} :" , id);
        try {
            return this.deferralWorkflowStatusRepository.findAllByApplicationId(id);
        }catch (Exception e){
            log.error("Error Occurred in getHistoryOfWFStatus {} : "+ id , e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author Ajith.Rameshkumar
     * @return List of DeferralWorkflowEntity
     * This method used for get all data in deferral workflow.
     * @param email
     */

    @Override
    public String getLastDeferralWFStatus(String email) {
        JSONObject json=new JSONObject();
        log.info("getLastDeferralWFStatus in service layer {} ",email);
        try{
            Integer anchorCount=0;
            Integer cpCount=0;
            List<DeferralWorkflowEntity> wfApprovalStatusEntities=this.deferralWorkflowStatusRepository.getLastWFStatus(email);
            if(wfApprovalStatusEntities.size()>0){
                JSONArray array = new JSONArray();
                for(DeferralWorkflowEntity entity:wfApprovalStatusEntities){
                    JSONObject json1=new JSONObject();
                    json1.put("id",entity.getId());
                    json1.put("customerName", entity.getAppId().getCustomerInfoEntity().getCustomerName());
                    json1.put("customerType",entity.getAppId().getType());
                    json1.put("pan",entity.getAppId().getCustomerInfoEntity().getPan());
                    json1.put("cin",entity.getAppId().getCustomerInfoEntity().getCin());
                    json1.put("currentStage",entity.getStage().getStageId());
                    json1.put("status",entity.getStatus());
                    json1.put("remarks",entity.getRemarks());
                    json1.put("appId",entity.getAppId().getId());
                    json1.put("nextStageId",entity.getStage().getId());
                    json1.put("currentStageLead",entity.getCurrentStageTeam());
                    json1.put("approvedStatus",entity.getApprovedStatus());
                    if(entity.getAppId().getType()==1){
                        anchorCount++;
                    }else if(entity.getAppId().getType()==2){
                        cpCount++;
                    }
                    array.put(json1);
                }
                json.put("wfTableDataList",array);
                json.put("status",true);
                json.put("anchorCount",anchorCount);
                json.put("cpCount",cpCount);
            }
        }catch (Exception e){
            log.error("Error Occurred in getLastDeferralWFStatus{} : ",e.getMessage());
            e.printStackTrace();
            json.put("status",false);
        }
        return json.toString();
    }


    public String getLeadLastWFStatus(String email,String token) {
        log.info("Get Last Workflow getLeadLastWFStatus {} ",email,token);
        JSONObject json=new JSONObject();
        try{
            Integer anchorCount=0;
            Integer cpCount=0;
            String jwt=loginService.decodeJWT(token);
            JSONObject json2=new JSONObject(jwt);
            List<?> role = loginService.getRole(json2);
            List<DeferralWorkflowEntity> wfApprovalStatusEntities = this.deferralWorkflowStatusRepository.getLeadLastWFStatus(role);
            if (wfApprovalStatusEntities.size() > 0) {
                JSONArray array = new JSONArray();
                for (DeferralWorkflowEntity entity : wfApprovalStatusEntities) {
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
                    if(entity.getAppId().getType()==1){
                        anchorCount++;
                    }else if(entity.getAppId().getType()==2){
                        cpCount++;
                    }
                    array.put(json1);
                }
                json.put("wfTableDataList", array);
                json.put("status", true);
                json.put("anchorCount",anchorCount);
                json.put("cpCount",cpCount);
            }
        }catch (Exception e){
            e.printStackTrace();
            json.put("status",false);
            log.error("Error Occurred  in getLeadLastWFStatus {} ",e.getMessage());
        }
        return json.toString();
    }

    public Collection<DeferralWorkflowEntity> getRemarks(long appId) {
        return deferralWorkflowStatusRepository.getRemarks(appId);
    }

    @Override
    public DeferralWorkflowEntity changeAssigne(Map<Long, String> idWithEmail, HttpServletResponse response) {
        log.info("Change Assignment {} ",idWithEmail);
        Long id = Long.valueOf(idWithEmail.get("id"));
        String email = idWithEmail.get("email");

        DeferralWorkflowEntity wfApprovalStatusEntity = deferralWorkflowStatusRepository.findById(id).orElse(null);
        DeferralWorkflowEntity updatedWfApprovalStatusEntity= new DeferralWorkflowEntity();
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

        return deferralWorkflowStatusRepository.save(updatedWfApprovalStatusEntity);
    }



    /**
     * @author Ajith.Rameshkumar
     * @return DeferralWorkflowEntity
     * This method used for posted in deferral workflow.
     * @param deferralWorkflowData
     */
    public DeferralWorkflowEntity saveDeferralWorkflow(WFApprovalStatusData deferralWorkflowData) {
        log.info("Service Layer of saveDeferralWorkflow {}" ,deferralWorkflowData);
        try{
            DeferralWorkflowEntity pre = this.deferralWorkflowStatusRepository.findByApplicationId(deferralWorkflowData.getAppId());
            if(pre!=null){
                pre = this.deferralWorkflowStatusRepository.getWorkflowByAppId(deferralWorkflowData.getAppId());
            }
            ApplicationEntity applicationEntity = this.applicationRepository.findById(deferralWorkflowData.getAppId()).orElse(null);
            DeferralWorkflowEntity wfApprovalStatusEntity = new DeferralWorkflowEntity();
            String nextTeamLead = deferralWorkflowData.getNextApproverInfo();
            deferralWorkflowData.setApprovedStatus(true);
            String currentApproverName = deferralWorkflowData.getApproverInfo();
            String nextApproverName = "";
            String anchorName = this.wfApprovalStatusService.findAnchorName(deferralWorkflowData.getAppId());
            if(pre == null){
                if(deferralWorkflowData.getStatus()==2){
                    try{
                        String currentStageTeam=applicationEntity.getType()==1?"ANCHOR_OPERATION_MAKER_LEAD":"CP_OPERATION_MAKER_LEAD";
                        deferralWorkflowData.setNextApproverInfo(currentStageTeam);
                        deferralWorkflowData = this.groupService.changeNextStageLeadEmail(deferralWorkflowData);
                        deferralWorkflowData.setApproverInfo(deferralWorkflowData.getNextApproverInfo());
                        deferralWorkflowData.setStatus(0);
                        deferralWorkflowData.setRemarks("Work-In-Progress");
                        currentApproverName =  deferralWorkflowData.getApproverInfo();
                        wfApprovalStatusEntity = this.deferralWorkflowStatusRepository.save(transform(deferralWorkflowData,pre,currentStageTeam));
                        this.emailService.sendEmail(deferralWorkflowData.getAppId(),anchorName,currentApproverName,nextApproverName, 2L,"S1");
                    }catch (Exception e){
                        log.error("Error saving deferral Workflow Occurred : {}  "+deferralWorkflowData,e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            else if(deferralWorkflowData.getStatus()==2 && (pre.getStage().getId() == deferralWorkflowData.getStageId()))
            {
                try{
                    wfApprovalStatusEntity  = this.deferralWorkflowStatusRepository.save(updateTransform(deferralWorkflowData,pre));
                    currentApproverName =deferralWorkflowData.getApproverInfo();
                }catch (Exception e){
                    log.error("Error saving deferral Workflow Occurred : {}  "+deferralWorkflowData,e.getMessage());
                    e.printStackTrace();
                }
                try{
                    List<DeferralReportsEntity> deferralReport = deferralReportsService.getDeferralPendingReport(deferralWorkflowData.getAppId(),0);
                    List<OtherDocumentMasterEntity> otherDeferralReport = otherDocumentMasterService.findPendingOtherDoc(deferralWorkflowData.getAppId(),0L);
                    WorkFlowStageEntity nextStageEntity = new WorkFlowStageEntity();
                    if(deferralReport.size()>0 || otherDeferralReport.size()>0) {
                        if (deferralWorkflowData.getNextApproverInfo().equals("CP_OPERATION_CHECKER_LEAD")
                                || deferralWorkflowData.getNextApproverInfo().equals("ANCHOR_OPERATION_CHECKER_LEAD")) {
                            if (deferralWorkflowData.getStageId() == 31 || deferralWorkflowData.getStageId() == 34) {
                                if (deferralWorkflowData.getStageId() == 31) {
                                    nextStageEntity = this.workflowStageRepository.findByStageId("DA1");

                                } else {
                                    nextStageEntity = this.workflowStageRepository.findByStageId("DCP1");
                                }
                            } else {
                                nextStageEntity = changeDeferralStageValue(deferralWorkflowData);
                            }

                            if (nextStageEntity != null) {
                                deferralWorkflowData.setRemarks("Work In Progress");
                                deferralWorkflowData.setStatus(0);
                                deferralWorkflowData.setStageId(nextStageEntity.getId());
                                deferralWorkflowData.setNextApproverInfo(deferralWorkflowData.getNextApproverInfo());
                                deferralWorkflowData.setApprovedStatus(true);
                                deferralWorkflowData = this.groupService.changeNextStageLeadEmail(deferralWorkflowData);
                                deferralWorkflowData.setApproverInfo(deferralWorkflowData.getNextApproverInfo());
                                wfApprovalStatusEntity = this.deferralWorkflowStatusRepository.save(transform(deferralWorkflowData, pre, nextTeamLead));
                                nextApproverName = deferralWorkflowData.getApproverInfo();
                                emailService.sendEmail(deferralWorkflowData.getAppId(), anchorName, currentApproverName, nextApproverName, 2L, "S1");
                                emailService.sendEmail(deferralWorkflowData.getAppId(), anchorName, nextApproverName, currentApproverName, 2L, "S1");
                            }
                        } else if (!deferralReport.isEmpty() || !otherDeferralReport.isEmpty()) {
                            if (deferralWorkflowData.getStageId() == 31 || deferralWorkflowData.getStageId() == 34) {
                                if (deferralWorkflowData.getStageId() == 31) {
                                    nextStageEntity = this.workflowStageRepository.findByStageId("DA1");

                                } else if(deferralWorkflowData.getStageId() == 34){
                                    nextStageEntity = this.workflowStageRepository.findByStageId("DCP1");
                                }
                            } else {
                                nextStageEntity = changeDeferralStageValue(deferralWorkflowData);
                            }

                            if (nextStageEntity != null) {
                                deferralWorkflowData.setRemarks("Work In Progress");
                                deferralWorkflowData.setStatus(0);
                                deferralWorkflowData.setStageId(nextStageEntity.getId());
                                deferralWorkflowData.setNextApproverInfo(deferralWorkflowData.getNextApproverInfo());
                                deferralWorkflowData.setApprovedStatus(true);
                                deferralWorkflowData = this.groupService.changeNextStageLeadEmail(deferralWorkflowData);
                                deferralWorkflowData.setApproverInfo(deferralWorkflowData.getNextApproverInfo());
                                wfApprovalStatusEntity = this.deferralWorkflowStatusRepository.save(transform(deferralWorkflowData, pre, nextTeamLead));
                                nextApproverName = deferralWorkflowData.getApproverInfo();
                                emailService.sendEmail(deferralWorkflowData.getAppId(), anchorName, currentApproverName, nextApproverName, 2L, "S1");
                                emailService.sendEmail(deferralWorkflowData.getAppId(), anchorName, nextApproverName, currentApproverName, 2L, "S1");
                            }
                        } else {
                            emailService.sendEmail(deferralWorkflowData.getAppId(), anchorName, currentApproverName, nextApproverName, 2L, "O");
                        }
                    }else{
                        deferralReport = deferralReportsService.getDeferralPendingReport(deferralWorkflowData.getAppId(),1);
                        otherDeferralReport = otherDocumentMasterService.findPendingOtherDoc(deferralWorkflowData.getAppId(),1L);
                        if(deferralReport .size() > 0 || otherDeferralReport.size()>0){
                            if (deferralWorkflowData.getStageId() == 31 || deferralWorkflowData.getStageId() == 34 ||
                                    deferralWorkflowData.getStageId() == 30 || deferralWorkflowData.getStageId() == 33 ) {
                                if (deferralWorkflowData.getStageId() == 30 || deferralWorkflowData.getStageId() == 31) {
                                    nextStageEntity = this.workflowStageRepository.findByStageId("DA1");

                                } else {
                                    nextStageEntity = this.workflowStageRepository.findByStageId("DCP1");
                                }
                            } else {
                                nextStageEntity = changeDeferralStageValue(deferralWorkflowData);
                            }

                            if (nextStageEntity != null) {
                                deferralWorkflowData.setRemarks("Work In Progress");
                                deferralWorkflowData.setStatus(0);
                                deferralWorkflowData.setStageId(nextStageEntity.getId());
                                deferralWorkflowData.setNextApproverInfo(deferralWorkflowData.getNextApproverInfo());
                                deferralWorkflowData.setApprovedStatus(true);
                                deferralWorkflowData = this.groupService.changeNextStageLeadEmail(deferralWorkflowData);
                                deferralWorkflowData.setApproverInfo(deferralWorkflowData.getNextApproverInfo());
                                wfApprovalStatusEntity = this.deferralWorkflowStatusRepository.save(transform(deferralWorkflowData, pre, nextTeamLead));
                                nextApproverName = deferralWorkflowData.getApproverInfo();
                                emailService.sendEmail(deferralWorkflowData.getAppId(), anchorName, currentApproverName, nextApproverName, 2L, "S1");
                                emailService.sendEmail(deferralWorkflowData.getAppId(), anchorName, nextApproverName, currentApproverName, 2L, "S1");
                            } else {
                                emailService.sendEmail(deferralWorkflowData.getAppId(), anchorName, currentApproverName, nextApproverName, 2L, "O");
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    log.error("Error saving deferral Workflow Occurred : {}  "+deferralWorkflowData,e.getMessage());
                }

            }
            else if(deferralWorkflowData.getStatus()==-2 && pre.getId()>0){
               return deferralWorkflowStatusRepository.save(updateTransform(deferralWorkflowData,pre));
            }
            else if(deferralWorkflowData.getStatus()==-1 && pre.getId()>0)
            {
                try{
                   return returnDefWF(deferralWorkflowData,pre,anchorName);
                }catch (Exception e){
                    log.error("Error saving deferral Workflow Occurred : {}  "+deferralWorkflowData,e.getMessage());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error saving deferral Workflow Occurred : {}  "+deferralWorkflowData,e.getMessage());
        }
        return null;
    }

    private DeferralWorkflowEntity returnDefWF(WFApprovalStatusData wfApprovalStatusData , DeferralWorkflowEntity wfApprovalStatusEntity, String customerInfoEntity) {
        String currentApprover= wfApprovalStatusData.getApproverInfo();
        DeferralWorkflowEntity deferralWorkflowEntity = new DeferralWorkflowEntity();
        try{
            deferralWorkflowStatusRepository.save(updateTransform(wfApprovalStatusData,wfApprovalStatusEntity));
            //New Row Created
            WorkFlowStageEntity preWorkFlowStage = findPreviousStageValue(wfApprovalStatusData);
            DeferralWorkflowEntity preCompletedWFStage = deferralWorkflowStatusRepository.findByPreviousAprroverStage(preWorkFlowStage.getId(), wfApprovalStatusData.getAppId());
            wfApprovalStatusEntity.setSeqNumber(wfApprovalStatusEntity.getSeqNumber());
            wfApprovalStatusData.setStageId(preCompletedWFStage.getStage().getId());
            wfApprovalStatusData.setRemarks("Work in Progress");
            wfApprovalStatusData.setStatus(0);
            wfApprovalStatusData.setApprovedStatus(true);
            wfApprovalStatusData.setApproverInfo(preCompletedWFStage.getApproverInfo());
            deferralWorkflowEntity = deferralWorkflowStatusRepository.save(transform(wfApprovalStatusData,wfApprovalStatusEntity,preCompletedWFStage.getCurrentStageTeam()));
            String nextApprover = wfApprovalStatusData.getApproverInfo();
            emailService.sendEmail(wfApprovalStatusData.getAppId(),customerInfoEntity,nextApprover,preCompletedWFStage.getApproverInfo(), -1L,"RT2");
            emailService.sendEmail(wfApprovalStatusData.getAppId(),customerInfoEntity,currentApprover,nextApprover, -1L,"RT1");
        }catch (Exception e){
            log.error("Error returnDefWF deferral Workflow Occurred : {}  "+wfApprovalStatusData,e.getMessage());
            e.printStackTrace();
        }
        return deferralWorkflowEntity;
    }


    private WorkFlowStageEntity findPreviousStageValue(WFApprovalStatusData stageId){
        Optional<WorkFlowStageEntity> currentStageData = workflowStageRepository.findById(stageId.getStageId());
        String stageName="";
        int stageEndWith;
        if(currentStageData.isPresent()){
            String tempLastChar;
//            if(currentStageData.get().getStageId().length()==2){
//                tempLastChar= currentStageData.get().getStageId().substring(currentStageData.get().getStageId().length()-1);
//                stageEndWith=Integer.parseInt(tempLastChar);
//                stageEndWith=stageEndWith!=1?stageEndWith-1:stageEndWith;
//                stageName="A"+stageEndWith;
//            }
            if(currentStageData.get().getStageId().length()==3){
                tempLastChar= currentStageData.get().getStageId().substring(currentStageData.get().getStageId().length()-1);
                stageEndWith=Integer.parseInt(tempLastChar);
                stageEndWith=stageEndWith!=1?stageEndWith-1:stageEndWith;
                stageName="DA"+stageEndWith;
            }else if(currentStageData.get().getStageId().length()==4){
                tempLastChar= currentStageData.get().getStageId().substring(currentStageData.get().getStageId().length()-1);
                stageEndWith=Integer.parseInt(tempLastChar);
                stageEndWith-=stageEndWith!=1?stageEndWith-1:stageEndWith;
                stageName="DCP"+stageEndWith;
            }
        }
        return workflowStageRepository.findByStageId(stageName);

    }

    private DeferralWorkflowEntity updateTransform(WFApprovalStatusData wfApprovalStatusData, DeferralWorkflowEntity wfApprovalStatusEntity) {
        DeferralWorkflowEntity entity=deferralWorkflowStatusRepository.findById(wfApprovalStatusEntity.getId()).orElse(null);
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
            log.error("Error updateTransform deferral Workflow Occurred : {}  "+wfApprovalStatusData,e.getMessage());
        }
        return entity;
    }

    private WorkFlowStageEntity changeDeferralStageValue(WFApprovalStatusData deferralWorkflowData) {

        String stageName="";
        int stageEndWith = 0;
        List<WorkFlowStageEntity> currentStageData = workflowStageRepository.findByStageValue(deferralWorkflowData.getStageId());
        if(currentStageData!=null){
            String tempLastChar;
            if(currentStageData.get(0).getStageId().length()==3){
                tempLastChar= currentStageData.get(0).getStageId().substring(currentStageData.get(0).getStageId().length()-1);
                stageEndWith=Integer.parseInt(tempLastChar);
                stageEndWith=stageEndWith!=6?stageEndWith+1:stageEndWith;
                stageName="DA"+stageEndWith;
            }else if(currentStageData.get(0).getStageId().length()==4){
                tempLastChar= currentStageData.get(0).getStageId().substring(currentStageData.get(0).getStageId().length()-1);
                stageEndWith=Integer.parseInt(tempLastChar);
                stageEndWith=stageEndWith!=3?stageEndWith+1:stageEndWith;
                stageName="DCP"+stageEndWith;
            }
        }
        return workflowStageRepository.findByStageId(stageName);
    }

    private DeferralWorkflowEntity transform(WFApprovalStatusData wfApprovalStatusData, DeferralWorkflowEntity pre, String nextTeamLead){
        DeferralWorkflowEntity entity=new DeferralWorkflowEntity();
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


    public Collection<DeferralWorkflowEntity> findAnchorAsDescOrder() {
        log.info(" | URL | findAnchorAsDescOrder | OPERATION | " + "GETById findExistingAnchor");
        try {
            return deferralWorkflowStatusRepository.findAnchorAsDescOrder();
        } catch (Exception ex) {
            log.error("Error updateTransform deferral Workflow Occurred : {}  ",ex.getMessage());
        }
        return null;
    }
}
