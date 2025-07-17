package org.vcpl.triton.workflow.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.workflow.data.WFApprovalStatusData;
import org.vcpl.triton.workflow.entity.DeferralWorkflowEntity;
import org.vcpl.triton.workflow.service.DeferralWorkflowService;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/deferralWorkflow")
@Slf4j
public class DeferralWorkflowController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private DeferralWorkflowService deferralWorkflowService;

    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getHistoryOfWFStatus/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeferralWorkflowEntity> getHistoryOfWFStatus(@RequestHeader(value="Authorization",defaultValue = "null") String token,@PathVariable Long id,HttpServletResponse response) throws UnsupportedEncodingException {
        if(permissionService.validation(token)){
            return this.deferralWorkflowService.getHistoryOfWFStatus(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getFinalDeferralWFStatus/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getFinalDefferalWFStatus(@RequestHeader(value="Authorization",defaultValue = "null") String token,@PathVariable String email,HttpServletResponse response) throws UnsupportedEncodingException {
        if(permissionService.validation(token)){
            return this.deferralWorkflowService.getLastDeferralWFStatus(email);
        }else{
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getFinalWFStatusByLead/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getFinalWFStatusByLead(@RequestHeader(value="Authorization",defaultValue = "null") String token,@PathVariable String email,HttpServletResponse response) throws UnsupportedEncodingException {
        if(permissionService.validation(token)) {
            return this.deferralWorkflowService.getLeadLastWFStatus(email, token);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getRemarks/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<DeferralWorkflowEntity> getRemarks(@RequestHeader(value="Authorization",defaultValue = "null") String token,@PathVariable Long id, HttpServletResponse response) throws UnsupportedEncodingException {
       if(permissionService.validation(token)) {
            return this.deferralWorkflowService.getRemarks(id);
       } else{
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
       }
       return null;
    }

    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/saveDeferralWorkflow",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferralWorkflowEntity saveDeferralWorkflow(@RequestHeader(value="Authorization",defaultValue = "null") String token,@RequestBody WFApprovalStatusData deferralWorkflowData, HttpServletResponse response) throws UnsupportedEncodingException {
        log.info("saveDeferralWorkflow");
        if(permissionService.validation(token)) {
            try {
                return deferralWorkflowService.saveDeferralWorkflow(deferralWorkflowData);
            } catch (Exception ex) {
                log.error(" Exception occurred: "+ ex.getMessage());
            }
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/changeAssigne/{id}/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferralWorkflowEntity changeAssigne(@PathVariable Map<Long, String> idWithEmail, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
       if(permissionService.validation(token)) {
        return deferralWorkflowService.changeAssigne(idWithEmail,response);
       }else{
           response.setStatus(HttpServletResponse.SC_FORBIDDEN);
       }
       return null;
    }


}
