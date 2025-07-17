
package org.vcpl.triton.workflow.service;

import org.vcpl.triton.workflow.data.WorkflowstageData;
import org.vcpl.triton.workflow.entity.WorkFlowStageEntity;

import java.util.List;

public interface IWorkflowStage {

    List<WorkFlowStageEntity> getAllWorkFlowStages();

    WorkFlowStageEntity saveWorkflow(WorkflowstageData workFlowStageEntity);

    public WorkFlowStageEntity getWorkflowById(long id);

    public String deleteWorkflow(long id);

    WorkFlowStageEntity updateWorkflow(long id,WorkflowstageData workflowstageData);
}

