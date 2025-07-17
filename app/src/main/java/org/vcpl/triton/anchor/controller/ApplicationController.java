package org.vcpl.triton.anchor.controller;

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
import org.vcpl.triton.anchor.data.ApplicationData;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.BeneficiaryEntity;
import org.vcpl.triton.anchor.service.ApplicationService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Api(tags="applicationDetails")
@RestController
@RequestMapping("anchor")
@Validated
public class ApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("applicationDetails")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/applicationDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ApplicationEntity> getAllApplicationDetails(@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info("Getting All ApplicationInfo");
        if(permissionService.validation(token)) {
            return this.applicationService.getAllApplicationDetails();
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("applicationDetails")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/anchorListDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ApplicationEntity> findByAnchor(@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info("Getting All ApplicationInfo");
        if(permissionService.validation(token)) {
                return this.applicationService.findByAnchor();
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("applicationDetails")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/cpListDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ApplicationEntity> findByCp(@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info("Getting All ApplicationInfo");
        if(permissionService.validation(token)) {
                return this.applicationService.findByCp();
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }


    @ApiOperation("applicationDetails")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/applicationDetails/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApplicationEntity findByIdApplicationDetails(@PathVariable long id,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        if(permissionService.validation(token)) {
            logger.info(" | URL | /applicationDetails/{id} | OPERATION | " + "GETById ApplicationInfo");
            return applicationService.getApplicationDetailsById(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("applicationDetails")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/applicationDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveApplicationDetails(@Valid @RequestBody ApplicationData applicationData,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        if(permissionService.validation(token)) {
            logger.info(" | URL | /applicationDetails | OPERATION | " + "POST ApplicationInfo");
            ApplicationEntity applicationEntity = applicationService.saveApplicationDetails(applicationData);
                return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", applicationEntity.getId()), new HttpHeaders(), OK);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", HttpServletResponse.SC_FORBIDDEN), new HttpHeaders(), OK);
        }
    }

    @ApiOperation("applicationDetails")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/application/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ApplicationEntity> findByApplicationId(@PathVariable long id,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /application/{id} | OPERATION | "+"POST ApplicationInfo");
        if(permissionService.validation(token)) {
                return applicationService.getApplicationDetails(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("applicationDetails")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/applicationIds/{custId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getApplicationIds(@PathVariable long custId,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /application/{id} | OPERATION | "+"POST ApplicationInfo");
        if(permissionService.validation(token)) {
            return applicationService.getApplicationIds(custId);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("applicationDetails")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getAllAppIds/{custId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllApplicationIds(@PathVariable Long custId,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException, ParseException {
        logger.info(" | URL | /application/{id} | OPERATION | "+"POST ApplicationInfo");
        if(permissionService.validation(token)) {
            return applicationService.findAllApplicationIds(custId);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

//    @ApiOperation("applicationDetails")
//    @RequestMapping(
//            method = RequestMethod.PUT,
//            value = "/applicationDetails/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApplicationEntity updateApplicationDetails(@PathVariable long id, @Valid @RequestBody ApplicationData applicationData) {
        ApplicationEntity applicationEntity = applicationService.updateApplicationDetails(applicationData,id);
        return applicationEntity;
    }

//    @ApiOperation("applicationDetails")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/applicationDetails/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteApplicationDetails(@PathVariable long id){
        return  applicationService.deleteApplicationDetails(id);
    }

}
