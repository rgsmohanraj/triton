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
import org.vcpl.triton.counterParty.data.FundReqDetailsData;
import org.vcpl.triton.counterParty.data.FundReqDetailsMasterData;
import org.vcpl.triton.counterParty.entity.FundReqDetailsEntity;
import org.vcpl.triton.counterParty.service.FundReqDetailsService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Api(tags="fundReqDetail")
@RestController
@RequestMapping("counterParty")
public class FundReqDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(FundReqDetailsController.class);

    @Autowired
    private FundReqDetailsService fundReqDetailService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;


//    @ApiOperation("fundReqDetail")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/fundReqDetails",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FundReqDetailsEntity> getAllAnchorCreditPolicy()
    {
        logger.info(" | URL | /fundReqDetails | OPERATION | " + "GET fundReqDetail");
    try {
        return this.fundReqDetailService.getAllFundReqDetails();
    }catch ( Exception ex){
        logger.error(" | URL | /fundReqDetails | OPERATION | " + " Error |" + ex.getMessage());

    }return null;
        }

//    @ApiOperation("fundReqDetail")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/fundReqDetails/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public FundReqDetailsEntity getFundReqDetailsById(@PathVariable long id) {
        logger.info(" | URL | /fundReqDetails/{id} | OPERATION | " + "GETById fundReqDetail");
    try {
        return fundReqDetailService.getFundReqDetailsById(id);
    }catch (Exception ex){
        logger.error(" | URL | /fundReqDetails/{id} | OPERATION | " + " Error |" + ex.getMessage());
    }return  null;
        }

    @ApiOperation("fundReqDetail")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/fundReqDetails/{appId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FundReqDetailsEntity> getFundReqDetailsByFId(@PathVariable long appId) {
        logger.info(" | URL | /fundReqDetails/{id} | OPERATION | " + "GETById fundReqDetail");
        try {
            return fundReqDetailService.getFundReqDetailsByFId(appId);
        }catch (Exception ex){
            logger.error(" | URL | /fundReqDetails/{id} | OPERATION | " + " Error |" + ex.getMessage());
        }return  null;
    }

    @ApiOperation("fundReqDetail")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/fundReqDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveFundReqDetails(@Valid @RequestBody FundReqDetailsMasterData fundReqDetailsMasterData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /fundReqDetails | OPERATION | " + "POST fundReqDetail");
        if(permissionService.validation(token,"CREDIT UNDERWRITER,RISK UNDERWRITER,CPA")) {
        List<Long> result = fundReqDetailService.saveFundReqDetails(fundReqDetailsMasterData);
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", result), new HttpHeaders(), OK);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return  null;
    }

    @ApiOperation("fundReqDetail")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/fundReqDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateFundReqDetails(@Valid @RequestBody FundReqDetailsMasterData fundReqDetailsMasterData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /fundReqDetails/{id} | OPERATION | " + "PUT fundReqDetail");
        if(permissionService.validation(token,"CREDIT UNDERWRITER,RISK UNDERWRITER,CPA")) {
            List<Long> result = fundReqDetailService.updateFundReqDetails(fundReqDetailsMasterData);
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", result), new HttpHeaders(), OK);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return  null;
    }

//    @ApiOperation("fundReqDetail")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/fundReqDetails/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteFundReqDetails(@PathVariable long id){
        logger.info(" | URL | /fundReqDetails/{id} | OPERATION | " + "DELETE fundReqDetail");
    try {
        return fundReqDetailService.deleteFundReqDetails(id);
    }catch (Exception ex){
        logger.error(" | URL | /fundReqDetails/{id} | OPERATION | " + " Error |" + ex.getMessage());

    }return  null;
        }

}
