package org.vcpl.triton.counterParty.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.counterParty.data.SoftPolicyFilterMasterData;
import org.vcpl.triton.counterParty.entity.SoftPolicyFilterMasterEntity;
import org.vcpl.triton.counterParty.service.SoftPolicyFilterMasterService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Api(tags="SoftPolicyFilterMaster")
@RestController
@RequestMapping("counterParty")

public class SoftPolicyFilterMasterController {

    private static final Logger logger = LoggerFactory.getLogger(SoftPolicyFilterMasterController.class);

    @Autowired
    private SoftPolicyFilterMasterService softPolicyFilterMasterService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired private PermissionService permissionService;

    @ApiOperation("SoftPolicyFilterMaster")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/softPolicyFilterMaster",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SoftPolicyFilterMasterEntity> getAllFilters(@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /softPolicyFilterMaster | OPERATION | " + "GET softPolicyFilterMaster");
        if(permissionService.validation(token)) {
            List<SoftPolicyFilterMasterEntity> softPolicyFilterMasterEntities = softPolicyFilterMasterService.getAllFilters();
            return softPolicyFilterMasterEntities;
        }
        else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("SoftPolicyFilterMaster")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/softPolicyFilterMaster/{type}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SoftPolicyFilterMasterEntity> findBySoftPolicyFilterType(@PathVariable("type") String type) {
        logger.info(" | URL | /softPolicyFilterMaster/{id} | OPERATION | " + "GETById softPolicyFilterMaster");
        try {
            return softPolicyFilterMasterService.findBySoftPolicyFilterType(type);
        }catch (Exception ex){
            logger.error(" | URL | /softPolicyFilterMaster/{type} | OPERATION | " + " Error |" + ex.getMessage());

        }return  null;
    }

//    @ApiOperation("SoftPolicyFilterMaster")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/softPolicyFilterMasterById/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public SoftPolicyFilterMasterEntity findBySoftPolicyFilterId(@PathVariable long id) {
        logger.info(" | URL | /softPolicyFilterMaster/{id} | OPERATION | " + "GETById softPolicyFilterMaster");
        try {
            return softPolicyFilterMasterService.getsoftPolicyFilterMasterById(id);
        }catch (Exception ex){
            logger.error(" | URL | /softPolicyFilterMaster/{id} | OPERATION | " + " Error |" + ex.getMessage());

        }return  null;
    }

//    @ApiOperation("SoftPolicyFilterMaster")
//    @RequestMapping(
//            method = RequestMethod.POST,
//            value = "/softPolicyFilterMaster",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveSoftPolicyFilter(@Valid @RequestBody SoftPolicyFilterMasterData softPolicyFilterMasterData){
        logger.info(" | URL | /softPolicyFilterMaster | OPERATION | " + "POST softPolicyFilterMaster");
        SoftPolicyFilterMasterEntity softPolicyFilterMasterEntity = softPolicyFilterMasterService.saveSoftPolicyFilterMaster(softPolicyFilterMasterData);
        try {
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", softPolicyFilterMasterEntity.getId()), new HttpHeaders(), OK);
        }catch (Exception ex){
            logger.error(" | URL | /softPolicyFilterMaster | OPERATION | " + " Error |" + ex.getMessage());

        }return null;
    }

//        @ApiOperation("SoftPolicyFilterMaster")
//    @RequestMapping(
//            method = RequestMethod.PUT,
//            value = "/softPolicyFilterMaster/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public SoftPolicyFilterMasterEntity updateSoftPolicyFilter(@PathVariable long id, @Valid @RequestBody SoftPolicyFilterMasterData softPolicyFilterMasterData) {
        logger.info(" | URL | /softPolicyFilterMaster/{id} | OPERATION | " + "PUT softPolicyFilterMaster");
        SoftPolicyFilterMasterEntity softPolicyFilterMasterEntity = softPolicyFilterMasterService.updateSoftPolicyFilterMaster(softPolicyFilterMasterData,id);
        try {
            return softPolicyFilterMasterEntity;
        }catch (Exception ex){
            logger.error(" | URL | /softPolicyFilterMaster/{id} | OPERATION | " + " Error |" + ex.getMessage());
        }return null;
    }

//    @ApiOperation("SoftPolicyFilterMaster")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/softPolicyFilterMaster/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteAnchorAddress(@PathVariable long id){
        logger.info(" | URL | /softPolicyFilterMaster/{id} | OPERATION | " + "DELETE softPolicyFilterMaster");
        try {
            return softPolicyFilterMasterService.deleteSoftPolicyFilter(id);
        }catch (Exception ex){
            logger.error(" | URL | /softPolicyFilterMaster/{id} | OPERATION | " + " Error |" + ex.getMessage());
        }return null;
    }
}
