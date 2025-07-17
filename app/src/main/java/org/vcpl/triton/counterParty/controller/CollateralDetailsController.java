package org.vcpl.triton.counterParty.controller;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.counterParty.data.CollateralDetailsData;
import org.vcpl.triton.counterParty.data.CollateralDetailsMasterData;
import org.vcpl.triton.counterParty.data.CollateralMasterData;
import org.vcpl.triton.counterParty.data.DueDiligenceDetailsMasterData;
import org.vcpl.triton.counterParty.entity.CollateralDetailsEntity;
import org.vcpl.triton.counterParty.service.CollateralDetailsService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.CollateralValidator;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "collateralDetails")
@RestController
@RequestMapping("counterParty")
public class CollateralDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(CollateralDetailsController.class);


    @Autowired
    private CollateralDetailsService collateralDetailsService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private CollateralValidator collateralValidator;

    @Autowired
    private PermissionService permissionService;

    //    @ApiOperation("collateralDetails")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/collateralDetails",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CollateralDetailsEntity> getAllProduct() {
        logger.info(" | URL | /collateralDetails | OPERATION | " + "GET collateralDetails");
        try {
            return this.collateralDetailsService.getCollateralDetails();
        } catch (Exception ex) {
            logger.error(" | URL | /collateralDetails | OPERATION | " + " Error |" + ex.getMessage());
        }
        return null;
    }

    //    @ApiOperation("collateralDetails")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/collateralDetails/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public CollateralDetailsEntity findByCollateralDetailsId(@PathVariable long id) {
        logger.info(" | URL | /collateralDetails/{id} | OPERATION | " + "GETById collateralDetails");
        try {
            return collateralDetailsService.getCollateralDetailsById(id);
        } catch (Exception ex) {
            logger.error(" | URL | /collateralDetails/{id} | OPERATION | " + " Error |" + ex.getMessage());

        }
        return null;
    }

    @ApiOperation("collateralDetails")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/collateral/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<CollateralDetailsEntity> findByCollateralFId(@PathVariable long id, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /collateral/{id} | OPERATION | " + "GETById collateralDetails");

        if (permissionService.validation(token)) {
            return collateralDetailsService.getCollateralById(id);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }


    @ApiOperation("collateralDetails")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/collateralDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveDueDiligenceMaster(@Valid @RequestBody CollateralDetailsMasterData collateralDetailsMasterData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /collateralDetails | OPERATION | " + "POST collateralDetails");

        if (permissionService.validation(token, "CREDIT UNDERWRITER,RISK UNDERWRITER,CPA")) {
            Gson gson = new Gson();
            String json = gson.toJson(collateralDetailsMasterData, CollateralDetailsMasterData.class);
            String message = collateralValidator.validateCollateral(json);
            if (message.equals("Validation Success")) {
                List<Long> result = collateralDetailsService.saveCollateralDetails(collateralDetailsMasterData);
                return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", result), new HttpHeaders(), OK);
            } else {
                return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, message, "SCF002", "Enter Valid Parameter"), new HttpHeaders(), BAD_REQUEST);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("collateralDetails")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/collateralDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateCollateral(@Valid @RequestBody CollateralDetailsMasterData collateralDetailsMasterData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /collateralDetails | OPERATION | " + "PUT collateralDetails");

        if (permissionService.validation(token, "CREDIT UNDERWRITER,RISK UNDERWRITER,CPA")) {
            Gson gson = new Gson();
            String json = gson.toJson(collateralDetailsMasterData, CollateralDetailsMasterData.class);
            String message = collateralValidator.validateCollateral(json);
            if (message.equals("Validation Success")) {
                List<Long> result = collateralDetailsService.updateCollateralDetails(collateralDetailsMasterData);
                return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", result), new HttpHeaders(), OK);
            } else {
                return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, message, "SCF002", "Enter Valid Parameter"), new HttpHeaders(), BAD_REQUEST);
            }
        }else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

//    @ApiOperation("collateralDetails")
//    @RequestMapping(
//            method = RequestMethod.PUT,
//            value = "/collateralDetails/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public CollateralDetailsEntity updateCollateral(@PathVariable long id, @Valid @RequestBody CollateralDetailsData collateralDetailsData) {
//        logger.info(" | URL | /collateralDetails/{id} | OPERATION | " + "PUT collateralDetails");
//        CollateralDetailsEntity collateralDetailsEntity = collateralDetailsService.updateCollateralDetails(collateralDetailsData, id);
//        try {
//            return collateralDetailsEntity;
//        } catch (Exception ex) {
//            logger.error(" | URL | /collateralDetails/{id} | OPERATION | " + " Error |" + ex.getMessage());
//
//        }
//        return null;
//    }

    //    @ApiOperation("collateralDetails")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/collateralDetails/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteAnchorAddress(@PathVariable long id) {
        logger.info(" | URL | /collateralDetails/{id} | OPERATION | " + "DELETE collateralDetails");
        try {
            return collateralDetailsService.deleteCollateralDetails(id);
        } catch (Exception ex) {
            logger.error(" | URL | /collateralDetails/{id} | OPERATION | " + " Error |" + ex.getMessage());

        }
        return null;
    }
}
