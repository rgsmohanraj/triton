package org.vcpl.triton.counterParty.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.counterParty.entity.SoftPolicyMasterTypeEntity;
import org.vcpl.triton.counterParty.service.SoftPolicyMasterTypeService;
import org.vcpl.triton.rbac.service.PermissionService;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Api(tags="softPolicyMasterType")
@RestController
@RequestMapping("counterParty")
public class SoftPolicyMasterTypeController {

    private static final Logger logger = LoggerFactory.getLogger(SoftPolicyMasterTypeController.class);


    @Autowired
    SoftPolicyMasterTypeService softPolicyMasterTypeService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("softPolicyMasterType")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/softPolicyMasterType",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SoftPolicyMasterTypeEntity> getsoftPolicyMasterType(@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /softPolicyMasterType | OPERATION | " + "GET softPolicyMasterType");
        if(permissionService.validation(token)) {
        return softPolicyMasterTypeService.getsoftPolicyMasterType();
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    return null;
        }


//    @ApiOperation("softPolicyMasterType")
//    @RequestMapping(
//            method = RequestMethod.POST,
//            value = "/softPolicyMasterType",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public SoftPolicyMasterTypeEntity createSoftPolicyMasterType(@RequestBody SoftPolicyMasterTypeEntity softPolicyMasterTypeEntity){

        logger.info(" | URL | /softPolicyMasterType | OPERATION | " + "POST softPolicyMasterType");
        try {
            return softPolicyMasterTypeService.createSoftPolicyMasterType(softPolicyMasterTypeEntity);
        }catch (Exception ex){
            logger.error(" | URL | /softPolicyMasterType | OPERATION | " + " Error |" + ex.getMessage());

        }return  null;
    }

//    @ApiOperation("softPolicyMasterType")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/softPolicyMasterType/{spTypeId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<SoftPolicyMasterTypeEntity> getSoftPolicyMasterTypeById(@PathVariable(value = "spTypeId") Long spTypeId){
        logger.info(" | URL | /softPolicyMasterType/{spTypeId | OPERATION | " + "GETById softPolicyMasterType");
        try {
            return softPolicyMasterTypeService.getSoftPolicyMasterTypeById(spTypeId);
        }catch (Exception ex){
            logger.error(" | URL | /softPolicyMasterType/{spTypeId | OPERATION | " + " Error |" + ex.getMessage());

        }return  null;
    }


//    @ApiOperation("softPolicyMasterType")
//    @RequestMapping(
//            method = RequestMethod.PUT,
//            value = "/softPolicyMasterType/{spTypeId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public  SoftPolicyMasterTypeEntity updateDocumentTypeById(@PathVariable(value = "spTypeId")Long spTypeId, @RequestBody SoftPolicyMasterTypeEntity softPolicyMasterTypeEntity){
        logger.info(" | URL | /softPolicyMasterType/{spTypeId} | OPERATION | " + "PUT softPolicyMasterType");
        try {
            return softPolicyMasterTypeService.updateDocumentTypeById(spTypeId, softPolicyMasterTypeEntity);
        }catch (Exception ex){
            logger.error(" | URL | /softPolicyMasterType/{spTypeId} | OPERATION | " + " Error |" + ex.getMessage());

        }return null;
    }

//    @ApiOperation("softPolicyMasterType")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/softPolicyMasterType/{dtId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteSoftPolicyMasterTypeById(@PathVariable(value = "spTypeId") long spTypeId){
        logger.info(" | URL | /softPolicyMasterType/{dtId} | OPERATION | " + "DELETE softPolicyMasterSubType");
    try {
        return softPolicyMasterTypeService.deleteSoftPolicyMasterTypeById(spTypeId);
    }catch (Exception ex){
        logger.error(" | URL | /softPolicyMasterType/{dtId} | OPERATION | " + " Error |" + ex.getMessage());

    }return  null;
        }
}
