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
import org.vcpl.triton.counterParty.entity.CPBasicDetailsEntity;
import org.vcpl.triton.counterParty.service.CPBasicDeatilsService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@Api(tags="cpBasicDetails")
@RestController
@RequestMapping("counterParty")
public class CpBasicDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(CpBasicDetailsController.class);


    @Autowired
    private CPBasicDeatilsService cpBasicDeatilsService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;



    @ApiOperation("cpBasicDetails")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/cpBasic/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CPBasicDetailsEntity getAllCounterPartyDetails(@PathVariable long id) {
        logger.info(" | URL | /cpBasicDetails | OPERATION | " + "GET cpBasicDetails");
            return this.cpBasicDeatilsService.getCPDetails(id);
    }

    @ApiOperation("cpBasicDetails")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/cpBasicDetails/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<CPBasicDetailsEntity> getCPBasicDetailsById(@PathVariable long id,@RequestHeader(value = "Authorization") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /cpBasicDetails/{id} | OPERATION | " + "GETById cpBasicDetails");
        if(permissionService.validation(token)) {
            return cpBasicDeatilsService.getCPBasicDetailsById(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("cpBasicDetails")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/cpBasicDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApplicationEntity saveCPBasicDetails(@Valid @RequestBody CPBasicDetailsData cpBasicDetailsData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | cpBasicDetails | OPERATION | " + "POST cpBasicDetails");
        if(permissionService.validation(token,"BUSINESS")) {
//            try {
                ApplicationEntity cpBasicDetails = cpBasicDeatilsService.saveCPDetails(cpBasicDetailsData);
//                return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", cpBasicDetails.getId()), new HttpHeaders(), OK);
                return cpBasicDetails;
//            }catch (DataIntegrityViolationException e){
//                e.printStackTrace();
////                return new ResponseEntity<Object>(responseControllerService.getBody(INTERNAL_SERVER_ERROR, "Duplicate Data", "", e.getMessage()), new HttpHeaders(), INTERNAL_SERVER_ERROR);
//            }
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
         return  null;
    }

    @ApiOperation("cpBasicDetails")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/cpRenewalBasicDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveRenewalBasicDetails(@Valid @RequestBody CPBasicDetailsData cpBasicDetailsData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | cpBasicDetails | OPERATION | " + "POST cpBasicDetails");
        if(permissionService.validation(token,"BUSINESS")) {
            CPBasicDetailsEntity cpBasicDetailsEntity = cpBasicDeatilsService.saveRenewalBasicDetails(cpBasicDetailsData);
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", cpBasicDetailsEntity.getId()), new HttpHeaders(), OK);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return  null;
    }

//    @ApiOperation("cpBasicDetails")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/cpBasicDetails/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteCPBasicDetails(@PathVariable long id) {
        logger.info(" | URL | /cpBasicDetails/{id} | OPERATION | " + "DELETE cpBasicDetails");
    try {
        return cpBasicDeatilsService.deleteCPBasicDetails(id);
    }catch (Exception ex) {
        logger.error(" | URL | /cpBasicDetails/{id} | OPERATION | " + " Error |" + ex.getMessage());

    } return  null;
        }

    @ApiOperation("cpBasicDetails")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/cpBasicDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateCPBasicDetails(@Valid @RequestBody CPBasicDetailsData cpBasicDetailsData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /cpBasicDetails/{id} | OPERATION | " + "PUT cpBasicDetails");
        if(permissionService.validation(token,"BUSINESS,CPA")) {
            try {
                CPBasicDetailsEntity cpBasicDetailsEntity = cpBasicDeatilsService.updateCPBasicDetails(cpBasicDetailsData);
                return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", cpBasicDetailsEntity.getId()), new HttpHeaders(), OK);
            }catch (DataIntegrityViolationException e){
                e.printStackTrace();
                return new ResponseEntity<Object>(responseControllerService.getBody(INTERNAL_SERVER_ERROR, "Duplicate Data", "", e.getMessage()), new HttpHeaders(), INTERNAL_SERVER_ERROR);
            }
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return  null;
    }
}
