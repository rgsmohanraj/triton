package org.vcpl.triton.anchor.controller;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.anchor.data.AnchorAddressMasterData;
import org.vcpl.triton.anchor.data.AnchorAuthorizedData;
import org.vcpl.triton.anchor.data.AnchorAuthorizedMasterData;
import org.vcpl.triton.anchor.entity.AnchorAuthorizedEntity;
import org.vcpl.triton.anchor.service.AnchorAuthorizedService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Api(tags="anchorAuthorized")
@RestController
@RequestMapping("anchor")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AnchorAuthorizedController {
    private static final Logger logger = LoggerFactory.getLogger(AnchorAuthorizedController.class);

    @Autowired
    private AnchorAuthorizedService anchorAuthorizedService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("anchorAuthorized")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/anchorAuthorizedFile/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<AnchorAuthorizedEntity> findByIdAnchorAuthorized(@PathVariable long id,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException  {
        logger.info(" | URL | /anchorAuthorizedFile/{id} | OPERATION | "+"GETByFid AnchorAuthorized");
        if(permissionService.validation(token)) {
            return anchorAuthorizedService.getanchorAuthorized(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }


    //    @ApiOperation("anchorAuthorized")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/anchorAuthorizedProduct",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AnchorAuthorizedEntity> getAllProduct()
    {
        return this.anchorAuthorizedService.getAllProduct();
    }



        @ApiOperation("anchorAuthorized")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/anchorAuthorized/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> saveAnchorAuthorized(@PathVariable long id, @Valid @RequestBody AnchorAuthorizedMasterData anchorAuthorizedMasterData){
        logger.info(" | URL | /anchorAuthorized | OPERATION | "+"POST anchorAuthorized");
        return anchorAuthorizedService.saveAnchorAuthorized(anchorAuthorizedMasterData, id);
    }


    //    @ApiOperation("anchorAuthorized")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/anchorAuthorized/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public AnchorAuthorizedEntity findAnchorAuthorizedById(@PathVariable long id) {
        logger.info(" | URL | /anchorAuthorized/{id} | OPERATION | "+"GETById anchorAuthorized");
            return anchorAuthorizedService.getAnchorAuthorizedById(id);
    }


        @ApiOperation("anchorAuthorized")
        @RequestMapping(
            method = RequestMethod.PUT,
            value = "/anchorAuthorized",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> updateAnchorAuthorizedEntity(@Valid @RequestBody AnchorAuthorizedMasterData anchorAuthorizedMasterData) {
        return  anchorAuthorizedService.updateAnchorAuthorizedEntity(anchorAuthorizedMasterData);
    }


    //    @ApiOperation("anchorAuthorized")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/anchorAuthorized/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteAnchorAuthorized(@PathVariable long id) {
        return anchorAuthorizedService.deleteAnchorAuthorized(id);
    }

}
