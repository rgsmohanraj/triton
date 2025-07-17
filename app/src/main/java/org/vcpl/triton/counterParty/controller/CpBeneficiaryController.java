package org.vcpl.triton.counterParty.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.counterParty.data.CPBasicDetailsData;
import org.vcpl.triton.counterParty.data.CommercialData;
import org.vcpl.triton.counterParty.data.CpBeneficiaryData;
import org.vcpl.triton.counterParty.entity.CPBasicDetailsEntity;
import org.vcpl.triton.counterParty.entity.CommercialEntity;
import org.vcpl.triton.counterParty.entity.CpBeneficiaryEntity;
import org.vcpl.triton.counterParty.service.CpBeneficiaryService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@Api(tags="cpBeneficiary")
@RestController
@RequestMapping("counterParty")
public class CpBeneficiaryController {

    private static final Logger logger = LoggerFactory.getLogger(CpBeneficiaryController.class);

    @Autowired
    private CpBeneficiaryService cpBeneficiaryService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("cpBeneficiary")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/cpBeneficiary/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CpBeneficiaryEntity getCPBeneficiaryDetailsById(@PathVariable long id, @RequestHeader(value = "Authorization") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /cpBeneficiaryDetails/{id} | OPERATION | " + "GETById cpBeneficiaryDetails");
        if(permissionService.validation(token)) {
            return cpBeneficiaryService.getCPBeneficiaryDetailsById(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }


    @ApiOperation("cpBeneficiary")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/cpBeneficiary",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveCPBeneficiaryDetails(@Valid @RequestBody CpBeneficiaryData cpBeneficiaryData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | cpBeneficiary | OPERATION | " + "POST cpBeneficiary");
        if(permissionService.validation(token,"BUSINESS")) {
            try {
                CpBeneficiaryEntity cpBeneficiaryEntity = cpBeneficiaryService.saveCpBeneficiaryDetails(cpBeneficiaryData);
                return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", cpBeneficiaryEntity.getId()), new HttpHeaders(), OK);
            }catch (DataIntegrityViolationException e){
                e.printStackTrace();
                return new ResponseEntity<Object>(responseControllerService.getBody(INTERNAL_SERVER_ERROR, "Duplicate Data", "", e.getMessage()), new HttpHeaders(), INTERNAL_SERVER_ERROR);
            }
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return  null;
    }

    @ApiOperation("cpBeneficiary")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/cpBeneficiary",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CpBeneficiaryEntity updateCpBeneficiaryDetails(@Valid @RequestBody CpBeneficiaryData cpBeneficiaryData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        CpBeneficiaryEntity cpBeneficiaryEntity = cpBeneficiaryService.updateCpBeneficiaryDetails(cpBeneficiaryData);
        return cpBeneficiaryEntity;
    }


}
