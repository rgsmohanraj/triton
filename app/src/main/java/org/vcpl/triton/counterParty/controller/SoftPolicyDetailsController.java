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
import org.vcpl.triton.counterParty.data.SoftPolicyDetailsMasterData;
import org.vcpl.triton.counterParty.entity.SoftPolicyDetailsEntity;
import org.vcpl.triton.counterParty.service.SoftPolicyDetailsService;

import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;
import org.vcpl.triton.validation.SoftPolicyValidator;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Api(tags="softPolicyDetails")
@RestController
@RequestMapping("counterParty")
public class SoftPolicyDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(SoftPolicyDetailsController.class);


    @Autowired
    private SoftPolicyDetailsService softPolicyDetailsService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SoftPolicyValidator softPolicyValidator;

//    @ApiOperation("softPolicyDetails")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/softPolicyDetails",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SoftPolicyDetailsEntity> getAllSoftPolicyDetails() {
        logger.info(" | URL | /softPolicyDetails | OPERATION | " + "GET softPolicyDetails");
        try {
            return this.softPolicyDetailsService.getAllSoftPolicyDetails();
        } catch (Exception ex) {
            logger.error(" | URL | /softPolicyDetails | OPERATION | " + " Error |" + ex.getMessage());

        }
        return null;
    }

    @ApiOperation("softPolicyDetails")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/softPolicyDetails/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSoftPolicyById(@PathVariable long id) {
        logger.info(" | URL | /softPolicyDetails/{id} | OPERATION | " + "GETById softPolicyDetails");
        try {
            String result = softPolicyDetailsService.getSoftPolicyById(id);
            if(result.equals("No Value present")){
                return "empty";
            }else{
                return result;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(" | URL | /softPolicyDetails/{id} | OPERATION | " + " Error |" + ex.getMessage());

        }
        return null;
    }

    @ApiOperation("softPolicyDetails")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/softPolicyDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveFundReqMaster(@Valid @RequestBody SoftPolicyDetailsMasterData softPolicyDetailsMasterData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /softPolicyDetails | OPERATION | " + "POST softPolicyDetails");
        if(permissionService.validation(token, "CPA")) {
            Gson gson = new Gson();
            String json = gson.toJson(softPolicyDetailsMasterData, SoftPolicyDetailsMasterData.class);
            String  message = softPolicyValidator.validateSoftPolicy(json);
            if(message.equals("Validation Success")) {
                List<Long> result = softPolicyDetailsService.saveSoftPolicyDetails(softPolicyDetailsMasterData);
                return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", result), new HttpHeaders(), OK);
            }else {
                return new ResponseEntity<Object> (responseControllerService.getBody(BAD_REQUEST,message,"SCF002","Enter Valid Parameter"),new HttpHeaders(),BAD_REQUEST );
            }
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("softPolicyDetails")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/validateSoftPolicy",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> validateSoftPolicy(@Valid @RequestBody SoftPolicyDetailsMasterData softPolicyDetailsMasterData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /softPolicyDetails | OPERATION | " + "POST softPolicyDetails");
        Gson gson = new Gson();
        String json = gson.toJson(softPolicyDetailsMasterData, SoftPolicyDetailsMasterData.class);
        String  message = softPolicyValidator.validateSoftPolicy(json);
        if(message.equals("Validation Success")) {
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", message), new HttpHeaders(), OK);
        }else {
            return new ResponseEntity<Object> (responseControllerService.getBody(BAD_REQUEST,message,"SCF002","Enter Valid Parameter"),new HttpHeaders(),BAD_REQUEST );
        }
    }

    @ApiOperation("softPolicyDetails")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/runSoftPolicy/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String runSoftPolicy(@PathVariable("id") Long appId,@Valid @RequestBody SoftPolicyDetailsMasterData softPolicyDetailsMasterData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException, ScriptException {
        logger.info(" | URL | /softPolicyDetails | OPERATION | " + "POST softPolicyDetails");
        return softPolicyDetailsService.softPolicyFilter(softPolicyDetailsMasterData,appId);
    }

    @ApiOperation("softPolicyDetails")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/softPolicyDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateSoftPolicyDetails(@Valid @RequestBody SoftPolicyDetailsMasterData softPolicyDetailsMasterData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /softPolicyDetails/{id} | OPERATION | " + "PUT softPolicyDetails");
        if(permissionService.validation(token, "CPA")) {
            Gson gson = new Gson();
            String json = gson.toJson(softPolicyDetailsMasterData, SoftPolicyDetailsMasterData.class);
            String  message = softPolicyValidator.validateSoftPolicy(json);
            if(message.equals("Validation Success")) {
                List<Long> result = softPolicyDetailsService.updateSoftPolicyDetails(softPolicyDetailsMasterData);
                return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", result), new HttpHeaders(), OK);
            }else {
                return new ResponseEntity<Object> (responseControllerService.getBody(BAD_REQUEST,message,"SCF002","Enter Valid Parameter"),new HttpHeaders(),BAD_REQUEST );
            }
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }


//    @ApiOperation("softPolicyDetails")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/softPolicyDetails/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteFundReqMaster(@PathVariable long id) {
        logger.info(" | URL | /softPolicyDetails/{id} | OPERATION | " + "DELETE softPolicyDetails");
        try {
            return softPolicyDetailsService.deleteSoftPolicyDetails(id);
        }catch (Exception ex){
            logger.error(" | URL | /softPolicyDetails/{id} | OPERATION | " + " Error |" + ex.getMessage());

        }
        return  null;
    }

}
