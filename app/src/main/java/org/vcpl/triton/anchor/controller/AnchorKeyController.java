package org.vcpl.triton.anchor.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.anchor.data.AnchorKeyMasterData;
import org.vcpl.triton.anchor.entity.AnchorKeyEntity;
import org.vcpl.triton.anchor.service.AnchorKeyService;
import org.vcpl.triton.rbac.service.PermissionService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

@Api(tags="anchorKey")
@RestController
@RequestMapping("anchor")
@Validated

public class AnchorKeyController {

    private static final Logger logger = LoggerFactory.getLogger(AnchorKeyController.class);

    @Autowired
    private AnchorKeyService anchorKeyService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("anchorKey")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/anchorKeyFile/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<AnchorKeyEntity> findByIdAnchorKey(@PathVariable long id,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /anchorKeyFile/{id} | OPERATION | "+"GETByFid AnchorKeyPerson");
        if(permissionService.validation(token)) {
            return anchorKeyService.getanchorKey(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    //    @ApiOperation("anchorKey")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/anchorKeyFile_product",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AnchorKeyEntity> getAllProduct()
    {
        return this.anchorKeyService.getAllProduct();
    }




//    @ApiOperation("anchorKey")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/anchorKey/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public AnchorKeyEntity findAnchorById(@PathVariable long id) {
        return anchorKeyService.getAnchorById(id);
    }


//    @ApiOperation("anchorKey")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/anchorKey/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteAnchor(@PathVariable long id) {
        return anchorKeyService.deleteAnchor(id);
    }


    @ApiOperation("anchorKey")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/anchorKey/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> saveAnchorKeyDetails(@PathVariable long id, @Valid @RequestBody AnchorKeyMasterData anchorKeyMasterData){
        return anchorKeyService.saveAnchorKeyDetails(anchorKeyMasterData, id);
    }


    @ApiOperation("anchorKey")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/anchorKey",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> updateAnchor(@Valid @RequestBody AnchorKeyMasterData anchorKeyMasterData) {
        return anchorKeyService.updateAnchor(anchorKeyMasterData);
    }


}



