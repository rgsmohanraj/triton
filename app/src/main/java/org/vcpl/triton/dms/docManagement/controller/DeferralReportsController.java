package org.vcpl.triton.dms.docManagement.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.dms.docManagement.data.DeferralReportsMasterData;
import org.vcpl.triton.dms.docManagement.entity.DeferralReportsEntity;
import org.vcpl.triton.dms.docValidation.DeferralDocValidationService;
import org.vcpl.triton.dms.docManagement.service.IDeferralReports;
import org.vcpl.triton.dms.docValidation.DocValidationService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "DMS_Deferral_Document_Reports")
@RestController
@RequestMapping("dms")
@Validated
public class DeferralReportsController {

    @Autowired
    IDeferralReports deferralReportsService;

    @Autowired
    private DocValidationService docValidationService;

    @Autowired
    private DeferralDocValidationService deferralDocValidationService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @ApiOperation("Deferral Document")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/deferralReport/{appId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<DeferralReportsEntity> getDeferralReport(@PathVariable("appId") Long appId) {
        return deferralReportsService.getDeferralReport(appId);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/deferralReports/{appId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getDeferralReports(@PathVariable("appId") Long appId) {
        return deferralReportsService.getDeferralReports(appId);
    }

    @ApiOperation("Deferral Document")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/deferralReport/{appId}/{status}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<DeferralReportsEntity> getDeferralReport(@PathVariable("appId") Long appId,@PathVariable("status") int status) {
        return deferralReportsService.getDeferralReport(appId,status);
    }

    @ApiOperation("Deferral Document")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/deferralReport",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deferralReport(@Valid @RequestBody DeferralReportsMasterData deferralReportsMasterData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        String result = deferralDocValidationService.deferralDocCheck(deferralReportsMasterData);
        if(result.equals("success")){
            List<Long> value = deferralReportsService.saveDeferralReports(deferralReportsMasterData);
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", value), new HttpHeaders(), OK);
        }else{
            return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST,result, "SCF002", result), new HttpHeaders(), BAD_REQUEST);
        }
    }

    @ApiOperation("Deferral Document")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/deferralReport",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateDeferralDoc(@Valid @RequestBody DeferralReportsMasterData deferralReportsMasterData){
        String result = deferralDocValidationService.deferralDocCheck(deferralReportsMasterData);
        if(result.equals("success")){
            List<Long> value = deferralReportsService.updateDeferralDoc(deferralReportsMasterData);
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", value), new HttpHeaders(), OK);
        }else{
            return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST,result, "SCF002", result), new HttpHeaders(), BAD_REQUEST);
        }
    }

    @ApiOperation("Deferral Document")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/deferralOpsMakerValidate",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateDeferralDocumentsValidate(@Valid @RequestBody DeferralReportsMasterData deferralReportsMasterData) {
        String result = deferralDocValidationService.deferralDoc(deferralReportsMasterData);
        if(result.equals("true")) {
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", result), new HttpHeaders(), OK);
        }else{
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Validation failed", "", String.valueOf(result)), new HttpHeaders(), OK);
        }

    }


        @ApiOperation("Deferral Document")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/deferralUpdate",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateDeferralDocuments(@Valid @RequestBody DeferralReportsMasterData deferralReportsMasterData){
        String result = deferralDocValidationService.deferralDoc(deferralReportsMasterData);
        if(result.equals("true")) {
            List<Long> value = deferralReportsService.updateDeferral(deferralReportsMasterData);
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", value), new HttpHeaders(), OK);
        }else{
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Validation failed", "", String.valueOf(result)), new HttpHeaders(), OK);
        }
    }

    @ApiOperation("Deferral Document")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/saveNewDeferralDate",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveNewDeferralDate(@Valid @RequestBody DeferralReportsMasterData deferralReportsMasterData){
        List<Long> value = deferralReportsService.saveNewDeferralDate(deferralReportsMasterData);
        return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", value), new HttpHeaders(), OK);
    }

    @ApiOperation("Deferral Document")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/updateDeferralStatus/{appId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateDeferralStatus(@PathVariable("appId") Long appId){
        deferralReportsService.updateDeferralStatus(appId);
    }

    @ApiOperation("Deferral Document")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/existingDeferralDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<DeferralReportsEntity> getDeferralDetails(@RequestParam Integer type,@RequestParam Integer status){
        return deferralReportsService.getDeferralDocuments(type, status);
    }

    @ApiOperation("Deferral Document")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/existingDeferralDetails/{type}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<DeferralReportsEntity> getDeferralDetailsWithType(@PathVariable("type") Integer type){
        return deferralReportsService.getDeferralDetails(type);
    }
}
