package org.vcpl.triton.counterParty.controller;

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
import org.vcpl.triton.counterParty.data.CreditPolicyDetailsMasterData;
import org.vcpl.triton.counterParty.data.CreditPolicyMasterData;
import org.vcpl.triton.counterParty.entity.CreditPolicyConfigEntity;
import org.vcpl.triton.counterParty.entity.CreditPolicyFilters;
import org.vcpl.triton.counterParty.entity.CreditPolicyMasterEntity;
import org.vcpl.triton.counterParty.service.CreditPolicyMasterService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Api(tags="creditPolicyMaster")
@RestController
@RequestMapping("counterParty")
@Validated
public class CreditPolicyMasterController {

    private static final Logger logger = LoggerFactory.getLogger(CreditPolicyMasterController.class);


    @Autowired
    private CreditPolicyMasterService creditPolicyMasterService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired private PermissionService permissionService;


    @ApiOperation("creditPolicyMaster")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/creditPolicyMaster",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CreditPolicyMasterEntity> getAllCreditPolicyMaster(@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /creditPolicyMaster | OPERATION | " + "GET creditPolicyMaster");
        if(permissionService.validation(token)) {
            return this.creditPolicyMasterService.getAllCreditPolicyMaster();
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }


    @ApiOperation("creditPolicyMaster")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/creditPolicyFilters",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllCreditPolicyFilters(@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response, @RequestParam long id) throws UnsupportedEncodingException {
        logger.info(" | URL | /creditPolicyMaster | OPERATION | " + "GET creditPolicyMaster");
        if(permissionService.validation(token)) {
            return this.creditPolicyMasterService.getAllCreditPolicyFilter(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }
//    @ApiOperation("creditPolicyMaster")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/creditPolicyList",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public CreditPolicyConfigEntity getAllCreditPolicy(@RequestParam String anchorRelationship,@RequestParam String assessmentType,@RequestParam String cpType,@RequestParam String recourse) throws UnsupportedEncodingException {
//        logger.info(" | URL | /creditPolicyMaster | OPERATION | " + "GET creditPolicyMaster");
////        if(permissionService.validation(token)) {
//        CreditPolicyConfigEntity creditPolicyConfigEntity=  creditPolicyMasterService.getAllCreditPolicy(anchorRelationship,assessmentType,cpType,recourse);
//        return creditPolicyConfigEntity;
//        }else{
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        }
//        return null;
//    }


    @ApiOperation("creditPolicyMaster")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/creditPolicyMaster",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveCreditPolicyMaster(@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response,@RequestBody CreditPolicyDetailsMasterData creditPolicyDetailsMasterData, @RequestParam long apppId) throws UnsupportedEncodingException, ScriptException {
        logger.info(" | URL | /creditPolicyMaster | OPERATION | " + "GET creditPolicyMaster");
        if(permissionService.validation(token)) {
            return this.creditPolicyMasterService.creditPolicyFilter(creditPolicyDetailsMasterData,apppId);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }


//    @ApiOperation("creditPolicyMaster")
//    @RequestMapping(
//            method = RequestMethod.POST,
//            value = "/creditPolicyMaster",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveCreditPolicyMaster(@Valid @RequestBody CreditPolicyMasterData creditPolicyMasterData) {
        logger.info(" | URL | /creditPolicyMaster | OPERATION | " + "POST creditPolicyMaster");
        CreditPolicyMasterEntity creditPolicyMasterEntity = creditPolicyMasterService.saveCreditPolicyMaster(creditPolicyMasterData);
        try {
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", creditPolicyMasterEntity.getId()), new HttpHeaders(), OK);
        }catch (Exception ex){
            logger.error(" | URL | /creditPolicyMaster | OPERATION | " + " Error |" + ex.getMessage());
        }return  null;
    }

//    @ApiOperation("creditPolicyMaster")
//    @RequestMapping(
//            method = RequestMethod.PUT,
//            value = "/creditPolicyMaster/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public CreditPolicyMasterEntity updateCreditPolicyMaster(@PathVariable long id, @Valid @RequestBody CreditPolicyMasterData creditPolicyMasterData) {
        logger.info(" | URL | /creditPolicyMaster/{id} | OPERATION | " + "PUT creditPolicyMaster");
        CreditPolicyMasterEntity creditPolicyMasterEntity = creditPolicyMasterService.updateCreditPolicyMaster(creditPolicyMasterData,id);
        try {
            return creditPolicyMasterEntity;
        }catch (Exception ex){
            logger.error(" | URL | /creditPolicyMaster/{id} | OPERATION | " + " Error |" + ex.getMessage());
        }return null;
    }

//    @ApiOperation("creditPolicyMaster")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/creditPolicyMaster/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteCreditPolicyMaster(@PathVariable long id){
        logger.info(" | URL | /creditPolicyMaster/{id} | OPERATION | " + "DELETE creditPolicyMaster");
    try {
        return creditPolicyMasterService.deleteCreditPolicyMaster(id);
    }catch (Exception ex){
        logger.error(" | URL | /creditPolicyMaster/{id} | OPERATION | " + " Error |" + ex.getMessage());

    }return null;
        }
}
