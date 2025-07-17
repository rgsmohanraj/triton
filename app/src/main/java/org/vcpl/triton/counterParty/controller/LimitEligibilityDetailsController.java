package org.vcpl.triton.counterParty.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.counterParty.data.LimitEligibilityDetailsData;
import org.vcpl.triton.counterParty.data.LimitEligibilityMasterData;
import org.vcpl.triton.counterParty.entity.LimitEligibilityDetailsEntity;
import org.vcpl.triton.counterParty.service.LimitEligibilityDetailsService;

import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Api(tags = "limitEligibility")
@RestController
@RequestMapping("counterParty")
public class LimitEligibilityDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(LimitEligibilityDetailsController.class);


    @Autowired
    private LimitEligibilityDetailsService limitEligibilityDetailsService;

    @Autowired
    ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;

    //    @ApiOperation("LimitEligibility")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/limitEligibility",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LimitEligibilityDetailsEntity> getLimitEligibilityDetails() {
        logger.info(" | URL | /limitEligibility | OPERATION | " + "GET LimitEligibility");
        try {
            return this.limitEligibilityDetailsService.getLimitEligibilityDetails();
        } catch (Exception ex) {
            logger.error(" | URL | /limitEligibility | OPERATION | " + " Error |" + ex.getMessage());

        }
        return null;
    }

    //    @ApiOperation("LimitEligibility")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/limitEligibility/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public LimitEligibilityDetailsEntity findByLimitEligibilityId(@PathVariable long id) {
        logger.info(" | URL | /limitEligibility/{id} | OPERATION | " + "GETById LimitEligibility");
        try {
            return limitEligibilityDetailsService.getlimitEligibilityDetailsById(id);
        } catch (Exception ex) {
            logger.error(" | URL | /limitEligibility/{id} | OPERATION | " + " Error |" + ex.getMessage());

        }
        return null;
    }

    @ApiOperation("LimitEligibility")
    @RequestMapping(method = RequestMethod.POST,
            value = "/limitEligibility/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> saveLimitEligibilityDetails(@PathVariable long id, @Valid @RequestBody LimitEligibilityMasterData limitEligibilityMasterData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /limitEligibility/{id} | OPERATION | " + "POST LimitEligibility");
        if (permissionService.validation(token, "CREDIT UNDERWRITER,RISK UNDERWRITER,CPA")) {
            return limitEligibilityDetailsService.saveLimitEligibilityDetails(limitEligibilityMasterData, id);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("LimitEligibility")
    @RequestMapping(method = RequestMethod.GET,
            value = "/limitEligibilityById/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LimitEligibilityDetailsEntity> findByLimitEligibilityDetails(@PathVariable long id, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /limitEligibilityById | OPERATION | " + "GET LimitEligibility");
        if (permissionService.validation(token)) {
            List<LimitEligibilityDetailsEntity> limitEligibilityDetailsEntities = limitEligibilityDetailsService.getTermSheetById(id);
            return limitEligibilityDetailsEntities;

        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("LimitEligibility")
    @RequestMapping(method = RequestMethod.GET,
            value = "/limitEligibilityList/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String findBylimitEligibilityList(@PathVariable long id, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /limitEligibilityById | OPERATION | " + "GET LimitEligibility");
        if (permissionService.validation(token)) {
            String limitEligibilityDetailsEntities = limitEligibilityDetailsService.getAnchorList(id);
            return limitEligibilityDetailsEntities;

        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("LimitEligibility")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/limitEligibility",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> updateLimitEligibilityDetails(@Valid @RequestBody LimitEligibilityMasterData limitEligibilityMasterData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /limitEligibility/{id} | OPERATION | " + "PUT LimitEligibility");
        if (permissionService.validation(token, "CREDIT UNDERWRITER,RISK UNDERWRITER,CPA")) {
            return limitEligibilityDetailsService.updateLimitEligibilityDetails(limitEligibilityMasterData);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

//    @ApiOperation("LimitEligibility")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/limitEligibility/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteLimitEligibilityDetails(@PathVariable long id) {
        logger.info(" | URL | /limitEligibility/{id} | OPERATION | " + "DELETE LimitEligibility");
        try {
            return limitEligibilityDetailsService.deleteLimitEligibilityDetails(id);
        } catch (Exception ex) {
            logger.error(" | URL | /limitEligibility/{id} | OPERATION | " + " Error |" + ex.getMessage());

        }
        return null;
    }
}