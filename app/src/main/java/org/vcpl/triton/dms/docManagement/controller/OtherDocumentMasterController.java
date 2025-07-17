package org.vcpl.triton.dms.docManagement.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.dms.docManagement.data.DeferralReportsMasterData;
import org.vcpl.triton.dms.docManagement.data.OtherDocumentMasterData;
import org.vcpl.triton.dms.docManagement.entity.DeferralReportsEntity;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentMasterEntity;
import org.vcpl.triton.dms.docManagement.service.IOtherDocumentMaster;

import org.vcpl.triton.dms.docManagement.service.OtherDocumentMasterService;
import org.vcpl.triton.dms.docValidation.OtherDocValidationService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Api(tags = "DMS_Other_Document_Deferral_Master")
@RestController
@RequestMapping("dms")
@Validated
public class OtherDocumentMasterController {

    @Autowired
    IOtherDocumentMaster otherDocumentMaster;

    @Autowired
    OtherDocValidationService otherDocValidationService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private OtherDocumentMasterService otherDocumentMasterService;

    private static final Logger logger = LoggerFactory.getLogger(OtherDocumentMasterController.class);

    @ApiOperation("Other Document Master")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/otherDocumentMaster",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<OtherDocumentMasterEntity> getOtherDocumentMasters() {
        return otherDocumentMaster.getOtherDocumentMaster();
    }

    @ApiOperation("Other Document Master")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/otherDocumentMaster/{appId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<OtherDocumentMasterEntity> getOtherDocumentMaster(@PathVariable("appId") Long appId) {
        return otherDocumentMaster.getOtherDocumentMaster(appId);
    }

    @ApiOperation("Other Document Master")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/otherDocumentMaster",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> otherDocumentMaster(@Valid @RequestBody OtherDocumentMasterData otherDocumentMasterData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        String result = otherDocValidationService.otherDocCheck(otherDocumentMasterData);
        if(result.equals("success")){
            List<Long> value = otherDocumentMaster.saveOtherDocumentMaster(otherDocumentMasterData);
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", value), new HttpHeaders(), OK);
        }else{
            return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST,result, "SCF002", result), new HttpHeaders(), BAD_REQUEST);
        }
    }

    @ApiOperation("Other Document Master")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/otherDocumentMaster",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateOtherDocumentMaster(@Valid @RequestBody OtherDocumentMasterData otherDocumentMasterData) {
        String result = otherDocValidationService.otherDocCheck(otherDocumentMasterData);
        if(result.equals("success")){
        List<Long> value = otherDocumentMaster.updateOtherDocumentMaster(otherDocumentMasterData);
        return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", value), new HttpHeaders(), OK);
        }else{
            return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST,result, "SCF002", result), new HttpHeaders(), BAD_REQUEST);
        }
    }

    @ApiOperation("Other Document Master")
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/deleteOtherDocRecord/{appId}/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteOtherDocRecord(@PathVariable("appId") Long appId,@PathVariable("id") Long id){
        logger.info(" => URL : /deleteOtherDocRecord | OPERATION : DELETE | Id : {}",appId);
        String result = otherDocumentMaster.deleteOtherDocRecord(appId,id);
        if(result.equals("success")){
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", result), new HttpHeaders(), OK);
        }else if(result.equals("documentUnavailable")){
            return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, "Document unavailable so refresh the page and continue the flow", "", result), new HttpHeaders(), BAD_REQUEST);
        }else {
            return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, "Something went wrong, Please contact your admin.", "", result), new HttpHeaders(), BAD_REQUEST);
        }
    }

    @ApiOperation("Other Document Master")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/othersDeferralDocuments/{appId}/{status}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<OtherDocumentMasterEntity> otherDocumentMasterEntities(@PathVariable("appId") Long appId, @PathVariable("status") int status) {
        return otherDocumentMasterService.getOtherDeferralDocuments(appId, status);
    }

    // Deferral Ops Checker Update
    @ApiOperation("Other Document Master")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/otherDocsStatusUpdate/{appId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateOtherDocsStatus(@PathVariable("appId") Long appId){
        otherDocumentMasterService.updateOtherDocsStatus(appId);
    }

    // Deferral Ops Maker Update
    @ApiOperation("Other Document Master")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/otherDeferralUpdate",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateOtherDeferral(@Valid @RequestBody OtherDocumentMasterData otherDocumentMasterData){
    List<Long> value=otherDocumentMasterService.updateOtherDefDoc(otherDocumentMasterData);
    if(!value.isEmpty()){
        return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", value), new HttpHeaders(), OK);
    }else{
        return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, "Fail", "", value), new HttpHeaders(), BAD_REQUEST);
    }
}

// Update Deferral Approval Committee
    @ApiOperation("Other Document Master")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/updateOtherDeferralStatus",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateOtherDeferralStatus(@Valid @RequestBody OtherDocumentMasterData otherDocumentMasterData){
        otherDocumentMasterService.updateOtherDeferralStatus(otherDocumentMasterData);
    }

    @ApiOperation("Other Document Master")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/workflowDecisionMake/{appId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean workflowDecisionMakeForDefOpsChecker(@PathVariable("appId") Long appId){
        return otherDocumentMasterService.workflowDecisionDefOpsChecker(appId);
    }

    @ApiOperation("Other Document Master")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/otherDocsForOpsChecker/{appId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<OtherDocumentMasterEntity> getOtherDocsForOpsChecker(@PathVariable("appId") Long appId) {
        return otherDocumentMaster.getOtherDocsForOpsChecker(appId);
    }

}