package org.vcpl.triton.counterParty.controller;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.counterParty.data.CollateralDetailsMasterData;
import org.vcpl.triton.counterParty.data.CreditPolicyDetailsMasterData;
import org.vcpl.triton.counterParty.data.SoftPolicyDetailsMasterData;
import org.vcpl.triton.counterParty.entity.CreditPolicyDetailsEntity;
import org.vcpl.triton.counterParty.service.CreditPolicyDetailsService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.CreditPolicyValidator;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Api(tags="CreditPolicyDetails")
@RestController
@RequestMapping("counterParty")
@Validated
public class CreditPolicyDetailsController {

    @Autowired
    private CreditPolicyDetailsService creditPolicyDetailsService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private CreditPolicyValidator creditPolicyValidator;

    @Autowired
    private PermissionService permissionService;

    private static final Logger logger = LoggerFactory.getLogger(CreditPolicyDetailsController.class);


    @ApiOperation("CreditPolicyDetails")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/creditPolicyDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveCreditPolicyDetails(@Valid @RequestBody CreditPolicyDetailsMasterData creditPolicyDetailsMasterData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /CreditPolicyDetails | OPERATION | " + "POST CreditPolicyDetails");
        if(permissionService.validation(token, "CREDIT UNDERWRITER,RISK UNDERWRITER,CPA")) {
            Gson gson = new Gson();
            String json = gson.toJson(creditPolicyDetailsMasterData, CreditPolicyDetailsMasterData.class);
            String  message = creditPolicyValidator.validateCreditPolicy(json);
                if(message.equals("Validation Success")) {
                    List<Long> result = creditPolicyDetailsService.saveCreditPolicyDetails(creditPolicyDetailsMasterData);
                    return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", result), new HttpHeaders(), OK);
                }else {
                    return new ResponseEntity<Object> (responseControllerService.getBody(BAD_REQUEST,message,"SCF002","Enter Valid Parameter"),new HttpHeaders(),BAD_REQUEST );
                }
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return  null;
    }

    @ApiOperation("CreditPolicyDetails")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/validateCreditPolicy",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> validateCreditPolicy(@Valid @RequestBody CreditPolicyDetailsMasterData creditPolicyDetailsMasterData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /validateCreditPolicy | OPERATION | " + "POST softPolicyDetails");
        Gson gson = new Gson();
        String json = gson.toJson(creditPolicyDetailsMasterData, CreditPolicyDetailsMasterData.class);
        String  message = creditPolicyValidator.validateCreditPolicy(json);
        if(message.equals("Validation Success")) {
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, message, "", "Success"), new HttpHeaders(), OK);
        }else {
            return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, message, "", "Fail"), new HttpHeaders(), BAD_REQUEST);
        }
    }

//    @ApiOperation("CreditPolicyDetails")
//    @RequestMapping(
//            method = RequestMethod.POST,
//            value = "/runCreditPolicy/{appId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String runCreditPolicy(@PathVariable long appId, @Valid @RequestBody CreditPolicyDetailsMasterData creditPolicyDetailsMasterData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /runCreditPolicy | OPERATION | " + "POST softPolicyDetails");
        return creditPolicyDetailsService.creditPolicyFilter(creditPolicyDetailsMasterData,appId);
    }

    @ApiOperation("CreditPolicyDetails")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/creditPolicyDetails/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String findByCreditPolicy (@PathVariable long id, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /CreditPolicyDetails/{id} | OPERATION | " + "GETByFId cpDebtProfile");
        if(permissionService.validation(token)) {
//            return creditPolicyDetailsService.findByCpCreditPolicyDetails(id);
            try {
                String result = creditPolicyDetailsService.findByCpCreditPolicyDetails(id);
                if(result.equals("No Value present")){
                    JSONObject json=new JSONObject();
                    json.put("creditPolicyArray","empty");
                    return json.toString();
                }else{
                    return result;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error(" | URL | /softPolicyDetails/{id} | OPERATION | " + " Error |" + ex.getMessage());

            }
            return null;
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return  null;
    }

    @ApiOperation("CreditPolicyDetails")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/creditPolicyDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateCreditPolicyDetails(@Valid @RequestBody CreditPolicyDetailsMasterData creditPolicyDetailsMasterData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /creditPolicyDetails | OPERATION | " + "PUT creditPolicyDetails");

        if (permissionService.validation(token, "CREDIT UNDERWRITER,RISK UNDERWRITER,CPA")) {
            Gson gson = new Gson();
            String json = gson.toJson(creditPolicyDetailsMasterData, CreditPolicyDetailsMasterData.class);
            String  message = creditPolicyValidator.validateCreditPolicy(json);
            if (message.equals("Validation Success")) {
                List<Long> result = creditPolicyDetailsService.updateCreditPolicyDetails(creditPolicyDetailsMasterData);
                return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", result), new HttpHeaders(), OK);
            } else {
                return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, message, "SCF002", "Enter Valid Parameter"), new HttpHeaders(), BAD_REQUEST);
            }
        }else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }
}
