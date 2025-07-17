package org.vcpl.triton.workflow.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;
import org.vcpl.triton.workflow.data.WFApprovalStatusData;
import org.vcpl.triton.workflow.entity.WFApprovalStatusEntity;
import org.vcpl.triton.workflow.service.WFApprovalStatusService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@Api("Workflow Approval Status")
@RestController
@RequestMapping("/wfApprovalStatus")
public class WFApprovalStatusController {

    private static final Logger logger = LoggerFactory.getLogger(WFApprovalStatusController.class);


    @Autowired
    ResponseControllerService responseControllerService;

    @Autowired
    WFApprovalStatusService wfApprovalStatusService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getWFStatus/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getWFStatus(@PathVariable long id){
        return this.wfApprovalStatusService.getWFStatus(id);
    }

    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getHistoryOfWFStatus/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WFApprovalStatusEntity> getHistoryOfWFStatus(@PathVariable long id){
        logger.info("URL : /getHistoryOfWFStatus/{id} | OPERATION : GET");
        return this.wfApprovalStatusService.getHistoryOfWFStatus(id);
    }

    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getFinalWFStatus/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getFinalWFStatus(@PathVariable String email){
        logger.info("URL : /getFinalWFStatus/{email} | OPERATION : GET | Email : {}",email);
        return this.wfApprovalStatusService.getLastWFStatus(email);
    }

    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getFinalWFStatusByLead/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getFinalWFStatusByLead(@RequestHeader(value="Authorization",defaultValue = "null") String token,@PathVariable String email,HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info("URL : /getFinalWFStatusByLead/{email} | OPERATION : GET | Email : {}",email);
        if(permissionService.validation(token)) {
            return this.wfApprovalStatusService.getLeadLastWFStatus(email, token);
        }
        return null;
    }

    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getRemarks/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<WFApprovalStatusEntity> getRemarks(@PathVariable long id){
        logger.info("URL : /getRemarks/{id} | OPERATION : GET");
        return this.wfApprovalStatusService.getRemarks(id);
    }

    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/saveWFApprovalSatge",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveWFApprovalSatge(@Valid @RequestBody WFApprovalStatusData wfApprovalStatusData) throws UnsupportedEncodingException {
        logger.info("URL : /saveWFApprovalSatge | OPERATION : POST | Login Id : [{}] | Login Action : [{}]",wfApprovalStatusData.getApproverInfo(),wfApprovalStatusData.getStatus());
//        logger.info("URL : /saveWFApprovalSatge | OPERATION : POST | Username : [{}]",wfApprovalStatusData.getApproverInfo()+" | Status : [{}]",wfApprovalStatusData.getApprovedStatus());
        WFApprovalStatusEntity entity = this.wfApprovalStatusService.saveWFApproval(wfApprovalStatusData);
        return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", entity.getId()), new HttpHeaders(), OK);
    }

    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/saveWFRenewalFlow/{genType}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveWFRenewalFlow(@PathVariable("genType") Boolean genType,@Valid @RequestBody WFApprovalStatusData wfApprovalStatusData) throws UnsupportedEncodingException {
        WFApprovalStatusEntity entity = this.wfApprovalStatusService.saveWFRenewalFlow(genType,wfApprovalStatusData);
        return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", entity.getId()), new HttpHeaders(), OK);
    }

    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/findAnchorName/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveWFApprovalSatge(@PathVariable long id) throws UnsupportedEncodingException {
        logger.info("URL : /findAnchorName/{id} | OPERATION : GET");
        String entity = this.wfApprovalStatusService.findAnchorName(id);
        return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", entity.toString()), new HttpHeaders(), OK);
    }


//    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/existingAnchor",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<WFApprovalStatusEntity> findExistingAnchor(@RequestParam String stage,@RequestParam String status,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info("URL : /existingAnchor | OPERATION : GET");
        if(permissionService.validation(token)) {
            try {
                return wfApprovalStatusService.findExistingAnchor(stage,status);
            } catch (Exception ex) {
                logger.error(" | URL | /existingAnchor | OPERATION | " + " Error |" + ex.getMessage());
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
    public WFApprovalStatusEntity changeAssigne(@PathVariable Map<Long, String> idWithEmail, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info("URL : /changeAssigne/{id}/{email} | OPERATION : POST | Email : [{}] ",idWithEmail.get("email"));
//       if(permissionService.validation(token)) {
        return wfApprovalStatusService.changeAssigne(idWithEmail,response);
//       }
//       return null;
    }

    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/existingAnchorInDesc",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<WFApprovalStatusEntity> findAnchorAsDescOrder(@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /findAnchorAsDescOrder | OPERATION : GET");
        if(permissionService.validation(token)) {
            try {
                return wfApprovalStatusService.findAnchorAsDescOrder();
            } catch (Exception ex) {
                logger.error("URL | /findAnchorAsDescOrder | OPERATION : GET | Message : {}",ex.getMessage());
            }
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("Workflow Approval Status")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/onBoardedCustomers/{customerType}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WFApprovalStatusEntity> findOnboardedCustomers(@PathVariable int customerType, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /onBoardedCustomers | OPERATION | " + "GETById onBoardedCustomers");
        if(permissionService.validation(token)) {
            try {
                return wfApprovalStatusService.findOnboardedCustomers(customerType);
            } catch (Exception ex) {
                logger.error(" | URL | /onBoardedCustomers | OPERATION | " + " Error |" + ex.getMessage());
            }
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }
}
