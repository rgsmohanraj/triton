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
import org.vcpl.triton.counterParty.data.FundReqMasterData;
import org.vcpl.triton.counterParty.entity.FundReqMasterEntity;
import org.vcpl.triton.counterParty.service.FundReqMasterService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Api(tags="fundRequirementMaster")
@RestController
@RequestMapping("counterParty")
public class FundReqMasterController {

    private static final Logger logger = LoggerFactory.getLogger(FundReqMasterController.class);

    @Autowired
    private FundReqMasterService fundRequirementMasterService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;


    @ApiOperation("fundRequirementMaster")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/fundReqMaster",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FundReqMasterEntity> getAllProduct(@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException
    {
        logger.info(" | URL | /fundReqMaster | OPERATION | " + "GET fundRequirementMaster");
        if(permissionService.validation(token)) {
        return this.fundRequirementMasterService.getAllProduct();

        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    return null;
        }

//    @ApiOperation("fundRequirementMaster")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/fundReqMaster/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public FundReqMasterEntity getByFundReqMasterId(@PathVariable long id) {
        logger.info(" | URL | /fundReqMaster/{id} | OPERATION | " + "GETById fundRequirementMaster");
    try {
        return fundRequirementMasterService.getFundReqMasterById(id);
    }catch ( Exception ex){
        logger.error(" | URL | /fundReqMaster/{id} | OPERATION | " + " Error |" + ex.getMessage());

    }return  null;
        }

//    @ApiOperation("fundRequirementMaster")
//    @RequestMapping(
//            method = RequestMethod.POST,
//            value = "/fundReqMaster",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveFundReqMaster(@Valid @RequestBody FundReqMasterData fundRequirementMasterData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /fundReqMaster | OPERATION | " + "POST fundRequirementMaster");
        if(permissionService.validation(token)) {
        FundReqMasterEntity fundRequirementMasterEntity = fundRequirementMasterService.saveFundReqMaster(fundRequirementMasterData);
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", fundRequirementMasterEntity.getId()), new HttpHeaders(), OK);

        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return   null;
    }

//        @ApiOperation("fundRequirementMaster")
//        @RequestMapping(
//                method = RequestMethod.DELETE,
//                value = "/fundReqMaster/{id}",
//                produces = MediaType.APPLICATION_JSON_VALUE)
        public String deleteFundReqMaster(@PathVariable long id){
            logger.info(" | URL | /fundReqMaster/{id} | OPERATION | " + "DELETE fundRequirementMaster");
    try {
        return fundRequirementMasterService.deleteFundReqMaster(id);
    }catch (Exception ex){
        logger.error(" | URL | /fundReqMaster/{id} | OPERATION | " + " Error |" + ex.getMessage());

     }return  null;
    }

//        @ApiOperation("fundRequirementMaster")
//        @RequestMapping(
//                method = RequestMethod.PUT,
//                value = "/fundReqMaster/{id}",
//                produces = MediaType.APPLICATION_JSON_VALUE)
        public FundReqMasterEntity updateFundReqMaster(@PathVariable long id, @Valid @RequestBody FundReqMasterData fundRequirementMasterData) {
            logger.info(" | URL | /fundReqMaster/{id} | OPERATION | " + "PUT fundRequirementMaster");

            FundReqMasterEntity fundRequirementMasterEntity = fundRequirementMasterService.updateFundReqMaster(fundRequirementMasterData,id);
            try {
                return fundRequirementMasterEntity;
            }catch (Exception ex){
                logger.error(" | URL | /fundReqMaster/{id} | OPERATION | " + " Error |" + ex.getMessage());

            }return null;
        }


}
