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
import org.vcpl.triton.anchor.data.BeneficiaryData;
import org.vcpl.triton.anchor.entity.BeneficiaryEntity;
import org.vcpl.triton.counterParty.data.CommercialData;
import org.vcpl.triton.counterParty.data.CommercialMasterData;
import org.vcpl.triton.counterParty.data.TermSheetMasterData;
import org.vcpl.triton.counterParty.entity.CommercialEntity;
import org.vcpl.triton.counterParty.service.CommercialCcService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Api(tags="CpCommercial")
@RestController
@RequestMapping("counterParty")
@Validated
public class CommercialCcController {

    private static final Logger logger = LoggerFactory.getLogger(CommercialCcController.class);


    @Autowired
    private CommercialCcService commercialCcService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;


//    @ApiOperation("CpCommercial")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/commercialCc",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommercialEntity> getAllCommercialCc()
    {
        logger.info(" | URL | /commercialCc | OPERATION | " + "GET CpCommercial");
        try {
            return this.commercialCcService.getAllCommercialCc();
        }catch (Exception ex){
            logger.error(" | URL | /commercialCc | OPERATION | " + " Error |" + ex.getMessage());

        }return null;
    }

//    @ApiOperation("CpCommercial")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/commercialCc/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public CommercialEntity getAllCommercialCcById(@PathVariable long id) {
//        logger.info(" | URL | /commercialCc/{id} | OPERATION | " + "GETById CpCommercial");
//        try {
//            return commercialCcService.getAllCommercialCcById(id);
//        } catch (Exception ex) {
//            logger.error(" | URL | /commercialCc/{id} | OPERATION | " + " Error |" + ex.getMessage());
//
//        }return null;
//        }


    @ApiOperation("CpCommercial")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/commercialCc/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<CommercialEntity> findByIdCommercialCc(@PathVariable long id, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /commercialCc/{id} | OPERATION | " + "GET CommercialCc");
        if (permissionService.validation(token)) {
            return commercialCcService.getCommercial(id);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }




    @ApiOperation("CpCommercial")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/commercialCc/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> saveCommercialCc(@Valid @RequestBody CommercialMasterData commercialMasterData, @PathVariable long id, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /commercialCc| OPERATION | " + "POST commercial");
        if(permissionService.validation(token, "BUSINESS")) {
            return commercialCcService.saveCommercialCc(commercialMasterData, id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }


//    @ApiOperation("CpCommercial")
//    @RequestMapping(
//            method = RequestMethod.PUT,
//            value = "/commercialCc",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public CommercialEntity updateCommercialDetails(@Valid @RequestBody CommercialData commercialData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
//        CommercialEntity commercialEntity = commercialCcService.updateCommercialDetails(commercialData);
//        return commercialEntity;
//    }


    @ApiOperation("CpCommercial")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/commercialCc",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> updateCommercialDetails(@Valid @RequestBody CommercialMasterData commercialMasterData) {
        logger.info(" | URL | /commercialCc/{id} | OPERATION | " + "PUT commercialCc");
        try {
            return commercialCcService.updateCommercialDetails(commercialMasterData);
        }catch (Exception ex){
            logger.error(" | URL | /termSheet/{id} | OPERATION | " + " Error |" + ex.getMessage());

        }return null;
    }



}
