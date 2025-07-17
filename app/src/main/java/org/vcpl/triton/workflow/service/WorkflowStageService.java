
package org.vcpl.triton.workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.rbac.repository.GroupRepositiory;
import org.vcpl.triton.workflow.data.WorkflowstageData;
import org.vcpl.triton.workflow.entity.WorkFlowStageEntity;
import org.vcpl.triton.workflow.repository.WorkflowStageRepository;

import java.util.List;

@Service
public class WorkflowStageService implements IWorkflowStage{

    @Autowired
    private WorkflowStageRepository workflowStageRepository;

    @Autowired
    private GroupRepositiory groupRepositiory;


    public List<WorkFlowStageEntity> getAllWorkFlowStages(){
        return this.workflowStageRepository.findAll();
    }

    public WorkFlowStageEntity saveWorkflow(WorkflowstageData workflowstageData){
       return workflowStageRepository.save(transform(workflowstageData));
    }

    private WorkFlowStageEntity transform(WorkflowstageData workflowstageData){
        WorkFlowStageEntity entity=new WorkFlowStageEntity();
//        entity.setStageId();
//        entity.setCusId(customerInfoRepository.getById(workflowstageData.getCusId()));
        entity.setGroupId(groupRepositiory.getById(workflowstageData.getGroupId()));
        return entity;
    }

    public WorkFlowStageEntity getWorkflowById(long id){
        return workflowStageRepository.findById(id).orElse(null);
    }

    @Override
    public String deleteWorkflow(long id) {
        workflowStageRepository.deleteById(id);
        return id +" Successfully Deleted";
    }

    @Override
    public WorkFlowStageEntity updateWorkflow(long id, WorkflowstageData workflowstageData) {
        WorkFlowStageEntity entity= workflowStageRepository.save(updateTransform(id,workflowstageData));
        return entity;
    }

    private WorkFlowStageEntity updateTransform(long id, WorkflowstageData workflowstageData) {
        WorkFlowStageEntity entity1=new WorkFlowStageEntity();
        entity1.setGroupId(groupRepositiory.getById(workflowstageData.getGroupId()));
        return entity1;
    }
}

