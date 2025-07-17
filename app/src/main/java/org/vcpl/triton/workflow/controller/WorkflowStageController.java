/*
package org.vcpl.triton.workflow.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.anchor.entity.AnchorAddressEntity;
import org.vcpl.triton.validation.ResponseControllerService;
import org.vcpl.triton.workflow.data.WFApprovalStatusData;
import org.vcpl.triton.workflow.data.WorkflowstageData;
import org.vcpl.triton.workflow.entity.WFApprovalStatusEntity;
import org.vcpl.triton.workflow.entity.WorkFlowStageEntity;
import org.vcpl.triton.workflow.service.WorkflowStageService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Api(tags="Workflow Stages")
@RestController
@RequestMapping("workflowstage")
public class WorkflowStageController {

    @Autowired
    WorkflowStageService workflowStageService;

    @Autowired
    private ResponseControllerService responseControllerService;



    @ApiOperation("Workflow Stages")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getAllWorkflow",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WorkFlowStageEntity> getAllWorkflow()
    {
        return this.workflowStageService.getAllWorkFlowStages();
    }

    @ApiOperation("Workflow Stages")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getWorkflowStage/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WorkFlowStageEntity getAllWorkflowStage(@PathVariable long id){
        return workflowStageService.getWorkflowById(id);
    }

    @ApiOperation("Workflow Stages")
    @RequestMapping(
            method = RequestMethod.POST,
            value="/saveWorkflow",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveWorkflow(@Valid @RequestBody WorkflowstageData workflowstageData){
        WorkFlowStageEntity workflow=workflowStageService.saveWorkflow(workflowstageData);
        return new ResponseEntity<Object>(responseControllerService.getBody(OK,"Success","",workflow.getId()),new HttpHeaders(),OK );
    }

    @ApiOperation("Workflow")
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/deleteWorkflow/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteWorkflow(@PathVariable long id){
        return  workflowStageService.deleteWorkflow(id);
    }

    @ApiOperation("Workflow Stage")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/updateWorkflow/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WorkFlowStageEntity updateWorkflow(@PathVariable long id, @Valid @RequestBody WorkflowstageData workflowstageData) {
        WorkFlowStageEntity workFlowStageEntity = workflowStageService.updateWorkflow(id,workflowstageData);
        return workFlowStageEntity;
    }

}
*/
