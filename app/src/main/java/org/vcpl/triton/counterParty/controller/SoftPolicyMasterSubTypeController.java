package org.vcpl.triton.counterParty.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.counterParty.entity.SoftPolicyMasterSubTypeEntity;
import org.vcpl.triton.counterParty.service.SoftPolicyMasterSubTypeService;
import org.vcpl.triton.rbac.service.PermissionService;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Api(tags="softPolicyMasterSubType")
@RestController
@RequestMapping("counterParty")
public class SoftPolicyMasterSubTypeController {

    private static final Logger logger = LoggerFactory.getLogger(SoftPolicyMasterSubTypeController.class);

    @Autowired
    SoftPolicyMasterSubTypeService softPolicyMasterSubTypeService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("softPolicyMasterSubType")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/softPolicyMasterSubType",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SoftPolicyMasterSubTypeEntity> getAllSoftPolicyMasterSubType(@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /softPolicyMasterSubType | OPERATION | " + "GET softPolicyMasterSubType");
        if(permissionService.validation(token)) {
            return softPolicyMasterSubTypeService.getAllSoftPolicyMasterSubType();
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

//    @ApiOperation("softPolicyMasterSubType")
//    @RequestMapping(
//            method = RequestMethod.POST,
//            value = "/softPolicyMasterSubType/{spTypeId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public  SoftPolicyMasterSubTypeEntity createSoftPolicyMasterSubType(@PathVariable(value = "spTypeId") Long spTypeId, @RequestBody SoftPolicyMasterSubTypeEntity softPolicyMasterSubTypeEntity){
        logger.info(" | URL | /softPolicyMasterSubType/{spTypeId} | OPERATION | " + "GETById softPolicyMasterSubType");
        try {
            return softPolicyMasterSubTypeService.createSoftPolicyMasterSubType(spTypeId, softPolicyMasterSubTypeEntity);
        }catch (Exception ex){
            logger.error(" | URL | /softPolicyMasterSubType/{spTypeId} | OPERATION | " + " Error |" + ex.getMessage());

        }return null;
    }

//    @ApiOperation("softPolicyMasterSubType")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/softPolicyMasterSubType/{spSubTypeId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<SoftPolicyMasterSubTypeEntity> getSoftPolicyMasterSubTypeById(@PathVariable(value = "spSubTypeId") Long spSubTypeId){
        logger.info(" | URL | /softPolicyMasterSubType/{spSubTypeId} | OPERATION | " + "GETById softPolicyMasterSubType");
        try {
            return softPolicyMasterSubTypeService.getSoftPolicyMasterSubTypeById(spSubTypeId);
        }catch (Exception ex){
            logger.error(" | URL | /softPolicyMasterSubType/{spSubTypeId} | OPERATION | " + " Error |" + ex.getMessage());

        }return  null;

    }

//    @ApiOperation("softPolicyMasterSubType")
//    @RequestMapping(
//            method = RequestMethod.PUT,
//            value = "/softPolicyMasterSubType/{spSubTypeId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public SoftPolicyMasterSubTypeEntity updateSoftPolicyMasterSubType(@PathVariable(value = "spSubTypeId") Long spSubTypeId,@RequestBody SoftPolicyMasterSubTypeEntity softPolicyMasterSubTypeEntity){
        logger.info(" | URL | /softPolicyMasterSubType/{spSubTypeId} | OPERATION | " + "PUT softPolicyMasterSubType");
    try {
        return softPolicyMasterSubTypeService.updateSoftPolicyMasterSubType(spSubTypeId, softPolicyMasterSubTypeEntity);
    }catch (Exception ex){
        logger.error(" | URL | /softPolicyMasterSubType/{spSubTypeId} | OPERATION | " + " Error |" + ex.getMessage());

    }return  null;
        }

//    @ApiOperation("softPolicyMasterSubType")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/softPolicyMasterSubType/{spSubTypeId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteSoftPolicyMasterSubTypeById(@PathVariable(value = "spSubTypeId") long spSubTypeId){

        logger.info(" | URL | /softPolicyMasterSubType/{spSubTypeId} | OPERATION | " + "DELETE softPolicyMasterSubType");
    try {
        return softPolicyMasterSubTypeService.deleteSoftPolicyMasterSubTypeById(spSubTypeId);
    }catch (Exception ex){
        logger.error(" | URL | /softPolicyMasterSubType/{spSubTypeId} | OPERATION | " + " Error |" + ex.getMessage());
    }return  null;
        }
}
