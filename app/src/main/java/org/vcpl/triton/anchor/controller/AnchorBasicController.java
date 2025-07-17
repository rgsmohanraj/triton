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
import org.vcpl.triton.anchor.data.AnchorBasicDetailsData;
import org.vcpl.triton.anchor.entity.AnchorBasicEntity;
import org.vcpl.triton.anchor.service.AnchorBasicService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@Api(tags="anchorBasic")
@RestController
@RequestMapping("anchor")
public class AnchorBasicController {

    private static final Logger logger = LoggerFactory.getLogger(AnchorBasicController.class);

    @Autowired
    private AnchorBasicService anchorBasicDetailsService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("anchorBasic")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/anchorBasicProduct",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AnchorBasicEntity> getAllProduct(@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException
    {
        if(permissionService.validation(token)) {
            logger.info(" | URL | /anchorBasicProduct | OPERATION | "+"GET AnchorBasic");
            return this.anchorBasicDetailsService.getAllProduct();
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }


    @ApiOperation("anchorBasic")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/anchorBasicFile/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<AnchorBasicEntity> findByIdAnchorBasic(@PathVariable long id,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /anchorBasicFile/{id} | OPERATION | "+"GETByFid AnchorBasic");
        if(permissionService.validation(token)) {
            return anchorBasicDetailsService.getanchorBasicDetails(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("anchorBasic")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/anchorBasic",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveBasicDetails(@Valid @RequestBody AnchorBasicDetailsData anchorBasicDetailsData,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        logger.info(" | URL | /anchorBasic | OPERATION | "+"POST AnchorBasic");
        if(permissionService.validation(token)) {

            AnchorBasicEntity anchorBasicFileEntity=anchorBasicDetailsService.saveBasicDetails(anchorBasicDetailsData);
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", anchorBasicFileEntity.getId()), new HttpHeaders(), OK);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ResponseEntity<Object>(responseControllerService.getBody(FORBIDDEN, "FORBIDDEN", "", HttpServletResponse.SC_FORBIDDEN), new HttpHeaders(), FORBIDDEN);
        }
    }

//    @ApiOperation("anchorBasic")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/anchorBasic/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public AnchorBasicEntity findByIdAnchorDetails(@PathVariable long id,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info("Getting anchorBasic by id");
        if (permissionService.validation(token)) {
            return anchorBasicDetailsService.getAnchorDetailsById(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("anchorBasic")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/anchorBasic",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AnchorBasicEntity updateAnchorDetails(@Valid @RequestBody AnchorBasicDetailsData anchorBasicDetailsData,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException  {
        if (permissionService.validation(token)) {
            logger.info("Getting anchorBasic By Id");
            return anchorBasicDetailsService.updateAnchorDetails(anchorBasicDetailsData);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return  null;
    }

}
