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
import org.vcpl.triton.counterParty.data.DueDiligenceMasterData;
import org.vcpl.triton.counterParty.entity.DueDiligenceMasterEntity;
import org.vcpl.triton.counterParty.service.DueDiligenceMasterService;


import org.vcpl.triton.rbac.service.PermissionService;

import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Api(tags="dueDiligenceMaster")
@RestController
@RequestMapping("counterParty")


public class DueDelegenceMasterController {

    private static final Logger logger = LoggerFactory.getLogger(DueDelegenceMasterController.class);

    @Autowired
    private DueDiligenceMasterService dueDiligenceMasterService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("dueDiligenceMaster")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/dueDiligenceMaster",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DueDiligenceMasterEntity> getAllProduct(@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /dueDiligenceMaster | OPERATION | " + "GET creditPolicyMaster");
        if(permissionService.validation(token)) {
        return this.dueDiligenceMasterService.getAllProduct();

        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
           return null;
        }

//    @ApiOperation("dueDiligenceMaster")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/dueDiligenceMaster/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public DueDiligenceMasterEntity findByDueDiligenceMasterId(@PathVariable long id) {
        logger.info(" | URL | /dueDiligenceMaster/{id} | OPERATION | " + "GET dueDiligenceMaster");
    try {
        return dueDiligenceMasterService.getdueDiligenceMasterById(id);
    }catch (Exception ex){
        logger.error(" | URL | /dueDiligenceMaster/{id} | OPERATION | " + " Error |" + ex.getMessage());
    }return null;
        }

//    @ApiOperation("dueDiligenceMaster")
//    @RequestMapping(
//            method = RequestMethod.POST,
//            value = "/dueDiligenceMaster",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveDueDiligenceMaster(@Valid @RequestBody DueDiligenceMasterData dueDiligenceMaster) {
        logger.info(" | URL | /dueDiligenceMaster | OPERATION | " + "POST dueDiligenceMaster");
        DueDiligenceMasterEntity dueDiligenceMasterEntity = dueDiligenceMasterService.saveDueDiligenceMaster(dueDiligenceMaster);
        try {
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", dueDiligenceMasterEntity.getId()), new HttpHeaders(), OK);
        }catch (Exception ex){
            logger.error(" | URL | /dueDiligenceMaster | OPERATION | " + " Error |" + ex.getMessage());
        }return  null;
    }


//    @ApiOperation("dueDiligenceMaster")
//    @RequestMapping(
//            method = RequestMethod.PUT,
//            value = "/dueDiligenceMaster/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public DueDiligenceMasterEntity updateDuediligenceMaster(@PathVariable long id, @Valid @RequestBody DueDiligenceMasterData dueDiligenceMasterData) {
        logger.info(" | URL | /dueDiligenceMaster/{id} | OPERATION | " + "PUT dueDiligenceMaster");
        DueDiligenceMasterEntity dueDiligenceMasterEntity = dueDiligenceMasterService.updateduediligenceMaster(dueDiligenceMasterData,id);
        try {
            return dueDiligenceMasterEntity;
        }catch (Exception ex) {
            logger.error(" | URL | /dueDiligenceMaster/{id} | OPERATION | " + " Error |" + ex.getMessage());

        }return null;
    }
//    @ApiOperation("dueDiligenceMaster")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/dueDiligenceMaster/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteAnchorAddress(@PathVariable long id){
        logger.info(" | URL | /dueDiligenceMaster/{id} | OPERATION | " + "DELETE dueDiligenceMaster");
    try {
        return dueDiligenceMasterService.deleteDueDiligenceMaster(id);
    }catch (Exception ex){
        logger.error(" | URL | /dueDiligenceMaster/{id} | OPERATION | " + " Error |" + ex.getMessage());

    }return  null;
        }
}
