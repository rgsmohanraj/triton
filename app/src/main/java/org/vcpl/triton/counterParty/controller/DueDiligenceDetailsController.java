package org.vcpl.triton.counterParty.controller;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.counterParty.data.DueDiligenceDetailsData;
import org.vcpl.triton.counterParty.data.DueDiligenceDetailsMasterData;
import org.vcpl.triton.counterParty.entity.DueDiligenceDetailsEntity;
import org.vcpl.triton.counterParty.service.DueDiligenceDetailsService;

import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.DueDiligenceValidator;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Api(tags="DueDiligenceDetails")
@RestController
@RequestMapping("counterParty")

public class DueDiligenceDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(DueDiligenceDetailsController.class);


    @Autowired
     private DueDiligenceDetailsService dueDiligenceDetailsService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private DueDiligenceValidator dueDiligenceValidator;

    @Autowired
    private PermissionService permissionService;

//    @ApiOperation("DueDiligenceDetails")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/dueDiligenceDetails",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DueDiligenceDetailsEntity> getDueDiligenceDetials(){
        logger.info(" | URL | /dueDiligenceDetails | OPERATION | " + "GET DueDiligenceDetails");
        try {
            return this.dueDiligenceDetailsService.getDueDiligenceDetials();
        }catch (Exception ex){
            logger.error(" | URL | /dueDiligenceDetails | OPERATION | " + " Error |" + ex.getMessage());

        }return null;
    }

    @ApiOperation("DueDiligenceDetails")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/dueDiligenceDetails/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<DueDiligenceDetailsEntity> findByDueDiligenceDetailsId(@PathVariable long id,@RequestHeader(value = "Authorization") String token, HttpServletResponse response)throws UnsupportedEncodingException {
        logger.info(" | URL | /dueDiligenceDetails | OPERATION | " + "GETById DueDiligenceDetails");
        if (permissionService.validation(token)) {
            return dueDiligenceDetailsService.getdueDiligenceDetailsById(id);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }


    @ApiOperation("DueDiligenceDetails")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/dueDiligenceDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveDueDiligenceDetails(@Valid @RequestBody DueDiligenceDetailsMasterData dueDiligenceDetailsMasterData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /dueDiligenceDetails | OPERATION | " + "POST DueDiligenceDetails");
        if(permissionService.validation(token, "CPA,CREDIT UNDERWRITER,RISK UNDERWRITER")) {
        Gson gson = new Gson();
        String json = gson.toJson(dueDiligenceDetailsMasterData, DueDiligenceDetailsMasterData.class);
        String  message = dueDiligenceValidator.validateDueDiligence(json);
            if(message.equals("Validation Success")) {
                List<Long> result = dueDiligenceDetailsService.saveDueDiligenceDetails(dueDiligenceDetailsMasterData);
                return new ResponseEntity<Object>(responseControllerService.getBody(OK, message, "", result), new HttpHeaders(), OK);
            }else {
                return new ResponseEntity<Object> (responseControllerService.getBody(BAD_REQUEST,message,"SCF002","Enter Valid Parameter"),new HttpHeaders(),BAD_REQUEST );
            }
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return  null;
    }

    @ApiOperation("DueDiligenceDetails")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/dueDiligenceDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateDuediligenceDetails(@Valid @RequestBody DueDiligenceDetailsMasterData dueDiligenceDetailsMasterData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /dueDiligenceDetails | OPERATION | " + "Put DueDiligenceDetails");
        if(permissionService.validation(token, "CPA,CREDIT UNDERWRITER,RISK UNDERWRITER")) {
            Gson gson = new Gson();
            String json = gson.toJson(dueDiligenceDetailsMasterData, DueDiligenceDetailsMasterData.class);
            String  message = dueDiligenceValidator.validateDueDiligence(json);
            if(message.equals("Validation Success")) {
                List<Long> result = dueDiligenceDetailsService.updateDueDiligenceDetails(dueDiligenceDetailsMasterData);
                return new ResponseEntity<Object>(responseControllerService.getBody(OK, message, "", result), new HttpHeaders(), OK);
            }else {
                return new ResponseEntity<Object> (responseControllerService.getBody(BAD_REQUEST,message,"SCF002","Enter Valid Parameter"),new HttpHeaders(),BAD_REQUEST );
            }
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return  null;
    }

//    @ApiOperation("DueDiligenceDetails")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/dueDiligenceDetails/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteDueDiligenceDetails(@PathVariable long id){
        logger.info(" | URL | /dueDiligenceDetails/{id} | OPERATION | " + "DELETE DueDiligenceDetails");
        try {
            return dueDiligenceDetailsService.deleteDueDiligenceDetails(id);
        }catch (Exception ex){
            logger.error(" | URL | /dueDiligenceDetails/{id} | OPERATION | " + " Error |" + ex.getMessage());
        }
        return null;
    }
}
