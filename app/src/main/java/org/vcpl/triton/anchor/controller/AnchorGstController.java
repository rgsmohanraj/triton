package org.vcpl.triton.anchor.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.anchor.data.AnchorGstMasterData;
import org.vcpl.triton.anchor.entity.AnchorGstEntity;
import org.vcpl.triton.anchor.service.AnchorGstService;
import org.vcpl.triton.rbac.service.PermissionService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

@Api(tags = "anchorGst")
@RestController
@RequestMapping("anchor")
@Validated
public class AnchorGstController {

    private static final Logger logger = LoggerFactory.getLogger(AnchorGstController.class);

    @Autowired
    private AnchorGstService anchorGstDetailsService;

    @Autowired
    private PermissionService permissionService;


    @ApiOperation("anchorGst")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/anchorGstFile/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<AnchorGstEntity> findByAnchorGst(@PathVariable long id, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /anchorGstFile/{id} | OPERATION | " + "GETByFid AnchorGST");
        if (permissionService.validation(token)) {
            return anchorGstDetailsService.getanchorGstDetails(id);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }


        @ApiOperation("anchorGst")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/anchorGst/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> saveAnchorGstDetails(@PathVariable long id, @Valid @RequestBody AnchorGstMasterData anchorGstMasterData) {
        logger.info(" | URL | anchorGst/{id} | OPERATION | " + "POST AnchorGST");
        return anchorGstDetailsService.saveAnchorGstDetails(anchorGstMasterData, id);
    }


    //    @ApiOperation("anchorGst")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/anchorGst/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteAnchorInfo(@PathVariable long id) {
        return anchorGstDetailsService.deleteAnchorDetails(id);
    }


        @ApiOperation("anchorGst")
        @RequestMapping(
            method = RequestMethod.PUT,
            value = "/anchorGst",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> updateAnchorGst(@Valid @RequestBody AnchorGstMasterData anchorGstMasterData) {
        return anchorGstDetailsService.updateAnchorGst(anchorGstMasterData);
    }


    //    @ApiOperation("anchorGst")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/anchorGst/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public AnchorGstEntity findByIdAnchorGst(@PathVariable long id) {
        return anchorGstDetailsService.getanchorGstDetailsById(id);
    }


    //    @ApiOperation("anchorGst")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/anchorGstFile_product",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AnchorGstEntity> getAllProduct() {
        return this.anchorGstDetailsService.getAllProduct();
    }


}


