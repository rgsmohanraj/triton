package org.vcpl.triton.anchor.controller;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.anchor.data.CreditNormsDetailsMasterData;
import org.vcpl.triton.anchor.entity.CreditNormsDetailsEntity;
import org.vcpl.triton.anchor.service.CreditNormsDetailsService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.CreditNormsValidator;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Api(tags="creditNormsDetails")
@RestController
@RequestMapping("anchor")
public class CreditNormsDetailsController {
    @Autowired
    private CreditNormsDetailsService creditNormsDetailsService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private CreditNormsValidator creditNormsValidator;

    @Autowired
    private PermissionService permissionService;

//    @ApiOperation("creditNormsDetails")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/creditNormsDetails",
//            produces = MediaType.APPLICATION_JSON_VALUE)

    public List<CreditNormsDetailsEntity> getCreditNormsDetails(@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException
    {
        if(permissionService.validation(token)) {
            return this.creditNormsDetailsService.getCreditNormsDetails();
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

//    @ApiOperation("creditNormsDetails")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/creditNormsDetails/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public CreditNormsDetailsEntity findByCreditNormsDetailsId(@PathVariable long id,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        if(permissionService.validation(token)) {
            return creditNormsDetailsService.getCreditNormsDetailsById(id);
        } else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("creditNorms")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/creditNormsDetailsByFId/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CreditNormsDetailsEntity> findByIdcreditNormsDetails(@PathVariable long id,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        if(permissionService.validation(token)) {
            return creditNormsDetailsService.findByIdcreditNormsDetails(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("creditNormsDetails")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/creditNormsDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveCreditNormsDetails(@Valid @RequestBody CreditNormsDetailsMasterData creditNormsDetailsMasterData,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        Gson gson = new Gson();
        if(permissionService.validation(token,"CPA")) {
            String json = gson.toJson(creditNormsDetailsMasterData,CreditNormsDetailsMasterData.class);
            String  message = creditNormsValidator.validateCreditNorms(json);
            if(message.equals("Validation Success")) {
                List<Long> result = creditNormsDetailsService.saveCreditNormsDetails(creditNormsDetailsMasterData);
                return new ResponseEntity<Object> (responseControllerService.getBody(OK,message,"SCF001",result),new HttpHeaders(),OK );
            }else {
                return new ResponseEntity<Object> (responseControllerService.getBody(BAD_REQUEST,message,"SCF002","Enter Valid Parameter"),new HttpHeaders(),BAD_REQUEST );
            }
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("creditNormsDetails")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/creditNormsDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateCreditNormsDetails(@Valid @RequestBody CreditNormsDetailsMasterData creditNormsDetailsMasterData,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        if(permissionService.validation(token,"CPA")) {
            Gson gson = new Gson();
            String json = gson.toJson(creditNormsDetailsMasterData,CreditNormsDetailsMasterData.class);
            String  message = creditNormsValidator.validateCreditNorms(json);
            if(message.equals("Validation Success")) {
                List<Long> result = creditNormsDetailsService.updateCreditNormsDetails(creditNormsDetailsMasterData);
                return new ResponseEntity<Object> (responseControllerService.getBody(OK,message,"SCF001",result),new HttpHeaders(),OK );
            }else {
                return new ResponseEntity<Object> (responseControllerService.getBody(BAD_REQUEST,message,"SCF002","Enter Valid Parameter"),new HttpHeaders(),BAD_REQUEST );
            }
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

//    @ApiOperation("creditNormsDetails")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/creditNormsDetails/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteCreditNormsDetails(@PathVariable long id){
        return  creditNormsDetailsService.deleteCreditNormsDetails(id);
    }
}
