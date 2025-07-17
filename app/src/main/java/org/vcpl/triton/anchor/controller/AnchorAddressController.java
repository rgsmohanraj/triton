package org.vcpl.triton.anchor.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.anchor.data.AnchorAddressData;
import org.vcpl.triton.anchor.data.AnchorAddressMasterData;
import org.vcpl.triton.anchor.entity.AnchorAddressEntity;
import org.vcpl.triton.anchor.service.AnchorAddressService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;


@Api(tags="anchorAddress")
@RestController
@RequestMapping("anchor")
public class AnchorAddressController {

    private static final Logger logger = LoggerFactory.getLogger(AnchorAddressController.class);

    @Autowired
    private AnchorAddressService anchorAddressDetailsService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;




    @ApiOperation("anchorAddress")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/anchorAddressFile/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<AnchorAddressEntity> findByIdAnchorAddress(@PathVariable long id,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /anchorAddressFile/{id} | OPERATION | "+"GETByFid AnchorAddress");
        if(permissionService.validation(token)) {
            return anchorAddressDetailsService.getanchorAddressDetailsById(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getPincodeDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPincodeDetails(@RequestParam String pinCode) throws UnsupportedEncodingException
    {
        String result = anchorAddressDetailsService.getPincodeDetails(pinCode);
        if(!result.equals("Fail")){
            return result;
        }else{
            return "PinCode not available in India";
        }
    }

    @ApiOperation("anchorAddress")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/anchorAddress",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AnchorAddressEntity saveAnchorAddress(@Valid @RequestBody AnchorAddressData anchorAddressData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /anchorAddress | OPERATION | "+"POST AnchorAddress");
        if(permissionService.validation(token)) {
            AnchorAddressEntity anchorAddressEntity = anchorAddressDetailsService.saveAnchorAddressDetails(anchorAddressData);
            return anchorAddressEntity;
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
    }

    @ApiOperation("anchorAddress")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/anchorAddress",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AnchorAddressEntity updateAnchorAddress(@Valid @RequestBody AnchorAddressData anchorAddressData) {
        AnchorAddressEntity anchorAddressDetailsEntity = anchorAddressDetailsService.updateAnchorAddress(anchorAddressData);
        return anchorAddressDetailsEntity;
    }

    //    @ApiOperation("anchorAddress")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/anchorAddress/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteAnchorAddress(@PathVariable long id){
        return  anchorAddressDetailsService.deleteAnchorAddress(id);
    }


    //    @ApiOperation("anchorAddress")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/anchorAddress/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public AnchorAddressEntity findByAnchorAddressId(@PathVariable long id) {
        logger.info(" | URL | /anchorAddress/{id} | OPERATION | "+"GETById AnchorAddress");
        return anchorAddressDetailsService.getAddressDetailsById(id);
    }

    //    @ApiOperation("anchorAddress")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/anchorAddressProduct",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AnchorAddressEntity> getAllProduct(@RequestHeader(value="Authorization",defaultValue = "null") String token,HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /anchorAddressFile_product | OPERATION | "+"GET AnchorAddress");
//        if(permissionService.validation(token)) {
        return this.anchorAddressDetailsService.getAllProduct();
//        }else{
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        }
    }

//    @ApiOperation("anchorAddress")
//    @RequestMapping(
//            method = RequestMethod.PUT,
//            value = "/anchorRenewalAddress",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> updateMultipleAddress(@RequestBody AnchorAddressMasterData anchorAddressMasterData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /anchorAddressFile_product | OPERATION | " + "PUT AnchorAddress");
        return anchorAddressDetailsService.updateMultipleAddress(anchorAddressMasterData);
    }

}
