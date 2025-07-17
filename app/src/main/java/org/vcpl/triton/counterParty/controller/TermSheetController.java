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
import org.vcpl.triton.anchor.entity.AnchorAddressEntity;
import org.vcpl.triton.anchor.data.AnchorGstMasterData;
import org.vcpl.triton.counterParty.data.TermSheetData;
import org.vcpl.triton.counterParty.data.TermSheetMasterData;
import org.vcpl.triton.counterParty.entity.TermSheetEntity;
import org.vcpl.triton.counterParty.service.TermSheetService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Api(tags="termSheet")
@RestController
@RequestMapping("counterParty")
public class TermSheetController {

    private static final Logger logger = LoggerFactory.getLogger(TermSheetController.class);


    @Autowired
    private TermSheetService termSheetService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;

//    @ApiOperation("termSheet")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/termSheet",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TermSheetEntity> getAllProduct()
    {
        logger.info(" | URL | /termSheet | OPERATION | " + "GET termSheet");
    try {
        return this.termSheetService.getAllProduct();
    }catch (Exception ex){
        logger.error(" | URL | /termSheet | OPERATION | " + " Error |" + ex.getMessage());

    }return null;
        }

//    @ApiOperation("termSheet")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/termSheet/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public TermSheetEntity getByTermSheetId(@PathVariable long id) {
        logger.info(" | URL | /termSheet/{id} | OPERATION | " + "GETById termSheet");
    try {
        return termSheetService.getAddressDetailsById(id);
    }catch (Exception ex){
        logger.error(" | URL | /termSheet/{id} | OPERATION | " + " Error |" + ex.getMessage());

    }return null;
        }

    @ApiOperation("termSheet")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/termSheetGet/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TermSheetEntity> findByIdTermSheet(@PathVariable long id, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /termSheetGet/{id} | OPERATION | " + "GETById termSheet");
        if(permissionService.validation(token)) {
        List<TermSheetEntity> termSheetEntities =  termSheetService.getTermSheetById(id);
            return termSheetEntities;
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return  null;
    }


    @ApiOperation("termSheet")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/termSheet/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> saveTermSheet(@Valid @RequestBody TermSheetMasterData termSheetMasterData,@PathVariable long id,  @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /termSheet/{id} | OPERATION | " + "POST termSheet");
        if(permissionService.validation(token, "CREDIT UNDERWRITER,RISK UNDERWRITER,CPA")) {
            return termSheetService.saveTermSheet(termSheetMasterData, id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

//    @ApiOperation("termSheet")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/termSheet/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteTermSheet(@PathVariable long id){
        logger.info(" | URL | /termSheet/{id} | OPERATION | " + "DELETE termSheet");
    try {
        return termSheetService.deleteTermSheet(id);
    }catch (Exception ex){
        logger.error(" | URL | /termSheet/{id} | OPERATION | " + " Error |" + ex.getMessage());

    }return  null;
        }

    @ApiOperation("termSheet")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/updateTermSheet",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> updateTermSheet(@Valid @RequestBody TermSheetMasterData termSheetMasterData) {
        logger.info(" | URL | /termSheet/{id} | OPERATION | " + "PUT termSheet");
    try {
        return termSheetService.updateTermSheet(termSheetMasterData);
    }catch (Exception ex){
        logger.error(" | URL | /termSheet/{id} | OPERATION | " + " Error |" + ex.getMessage());

    }return null;
        }

}
