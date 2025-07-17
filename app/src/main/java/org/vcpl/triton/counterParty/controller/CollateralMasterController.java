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
import org.vcpl.triton.counterParty.data.CollateralMasterData;
import org.vcpl.triton.counterParty.entity.CollateralMasterEntity;
import org.vcpl.triton.counterParty.service.CollateralMasterService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Api(tags="collateralMaster")
@RestController
@RequestMapping("counterParty")

public class CollateralMasterController {

    private static final Logger logger = LoggerFactory.getLogger(CollateralDetailsController.class);


    @Autowired
    private CollateralMasterService collateralService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired private PermissionService permissionService;

    @ApiOperation("collateralMaster")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/collateralMaster",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CollateralMasterEntity> getAllProduct(@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /collateralMaster | OPERATION | " + "GET collateralMaster");
        if(permissionService.validation(token)) {

            return this.collateralService.getAllProduct();
        }
        else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

//    @ApiOperation("collateralMaster")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/collateralMaster/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public CollateralMasterEntity findByDueDiligenceMasterId(@PathVariable long id) {
        logger.info(" | URL | /collateralMaster/{id} | OPERATION | " + "GETById collateralMaster");
    try {
        return collateralService.getCollateralMasterById(id);
    }catch (Exception ex){
        logger.error(" | URL | /collateralMaster/{id} | OPERATION | " + " Error |" + ex.getMessage());

    }return  null;
        }

//    @ApiOperation("collateralMaster")
//    @RequestMapping(
//            method = RequestMethod.POST,
//            value = "/collateralMaster",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveDueDiligenceMaster(@Valid @RequestBody CollateralMasterData collateralData){
        logger.info(" | URL | /collateralMaster | OPERATION | " + "POST collateralMaster");
        CollateralMasterEntity collateralEntity = collateralService.saveCollateralMaster(collateralData);
        try {
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", collateralEntity.getId()), new HttpHeaders(), OK);
        }catch (Exception ex){
            logger.error(" | URL | /collateralMaster | OPERATION | " + " Error |" + ex.getMessage());

        }return null;
    }

//    @ApiOperation("collateralMaster")
//    @RequestMapping(
//            method = RequestMethod.PUT,
//            value = "/collateralMaster/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public CollateralMasterEntity updateCollateral(@PathVariable long id, @Valid @RequestBody CollateralMasterData collateralData) {
        logger.info(" | URL | /collateralMaster/{id} | OPERATION | " + "PUT collateralMaster");
        CollateralMasterEntity collateralEntity = collateralService.updateCollateralMaster(collateralData,id);
        try {
            return collateralEntity;
        }catch (Exception ex){
            logger.error(" | URL | /collateralMaster/{id} | OPERATION | " + " Error |" + ex.getMessage());
        }return null;
    }
//    @ApiOperation("collateralMaster")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/collateralMaster/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteAnchorAddress(@PathVariable long id){
        logger.info(" | URL | /collateralMaster/{id} | OPERATION | " + "DELETE collateralMaster");
        try {
            return collateralService.deleteCollateralMaster(id);
        }catch (Exception ex){
            logger.error(" | URL | /collateralMaster/{id} | OPERATION | " + " Error |" + ex.getMessage());
        }return null;
    }
}
