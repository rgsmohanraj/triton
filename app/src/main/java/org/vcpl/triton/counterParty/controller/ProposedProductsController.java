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
import org.vcpl.triton.anchor.data.AnchorGstMasterData;
import org.vcpl.triton.anchor.entity.AnchorGstEntity;
import org.vcpl.triton.counterParty.data.ProposedProductsData;
import org.vcpl.triton.counterParty.data.ProposedProductsMasterData;
import org.vcpl.triton.counterParty.entity.ProposedProductsEntity;
import org.vcpl.triton.counterParty.service.ProposedProductsService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;
import org.vcpl.triton.workflow.entity.WFApprovalStatusEntity;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Api(tags="proposedProducts")
@RestController
@RequestMapping("counterParty")
public class ProposedProductsController {

    private static final Logger logger = LoggerFactory.getLogger(ProposedProductsController.class);


    @Autowired
    private ProposedProductsService proposedProductsService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;

//    @ApiOperation("proposedProducts")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/proposedProductDetails",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProposedProductsEntity> getProposedProduct() {
        logger.info(" | URL | /proposedProductDetails | OPERATION | " + "GET proposedProducts");
        try {
            return this.proposedProductsService.getProposedProduct();
        } catch (Exception ex) {
            logger.error(" | URL | /proposedProductDetails | OPERATION | " + " Error |" + ex.getMessage());
        }
        return null;
    }

//    @ApiOperation("proposedProducts")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/proposedProductDetail/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProposedProductsEntity findByProposedProductId(@PathVariable long id) {
        logger.info(" | URL | /proposedProductDetail/{id} | OPERATION | " + "GETById proposedProducts");
        try {
            return proposedProductsService.getProposedProductById(id);
        } catch (Exception ex) {
            logger.error(" | URL | /proposedProductDetail/{id} | OPERATION | " + " Error |" + ex.getMessage());

        }
        return null;
    }

    @ApiOperation("proposedProducts")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/proposedProductsById/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ProposedProductsEntity> findProposedProductsById(@PathVariable long id, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /proposedProductsById/{id} | OPERATION | " + "GETById proposedProducts");
        if(permissionService.validation(token)) {
            return proposedProductsService.findProposedProductsByCiId(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }




    @ApiOperation("proposedProducts")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getCreditNormsIds/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String findCreditNormsId(@PathVariable long id,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /getCreditNormsIds/{id} | OPERATION | " + "GETById proposedProducts");
        if (permissionService.validation(token)) {
            return proposedProductsService.findCreditNormsId(id);

        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("Work Flow Check")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/gotoRiskOrCredit/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String gotoRiskOrCredit(@PathVariable long id,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /gotoRiskOrCredit/{id} |GET OPERATION | " + "gotoRiskOrCredit ");
        if (permissionService.validation(token)) {
            return proposedProductsService.gotoRiskOrCredit(id);
        }else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }


    @ApiOperation("proposedProducts")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/proposedProductDetails/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> saveAnchorGstDetails(@Valid @RequestBody ProposedProductsMasterData proposedProductsMasterData, @PathVariable long id, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /proposedProductDetails/{id} | OPERATION | " + "POST proposedProducts");
        if(permissionService.validation(token,"BUSINESS")) {
//        try {
            return proposedProductsService.saveProposedProduct(proposedProductsMasterData, id);
//        }
//        catch (Exception ex) {
//            logger.error(" | URL | /proposedProductDetails/{id} | OPERATION | " + " Error |" + ex.getMessage());
//        }
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("proposedProducts")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/proposedProductDetails/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> updateProposedProduct(@Valid @RequestBody ProposedProductsMasterData proposedProductsMasterData, @PathVariable Long id, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /proposedProductDetails/{id} | OPERATION | " + "PUT proposedProducts");
        if(permissionService.validation(token,"BUSINESS")) {
            return proposedProductsService.updateProposedProduct(proposedProductsMasterData,id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("proposedProducts")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/updateCreditProposedProduct",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> updateCreditProposedProduct(@Valid @RequestBody ProposedProductsMasterData proposedProductsMasterData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /updateCreditProposedProduct | OPERATION | " + "PUT proposedProducts");
//        if(permissionService.validation(token,"BUSINESS")) {
            return proposedProductsService.updateCreditProposedProduct(proposedProductsMasterData);
//        }else{
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        }
//        return null;
    }

    @ApiOperation("proposedProducts")
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/proposedProductDetails/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteProposedProduct(@PathVariable long id) {
        logger.info(" => URL : /proposedProductDetails/{id} | OPERATION : DELETE | Id :",id);
        String result = proposedProductsService.deleteProposedProduct(id);
        if(result.equals("success")){
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", id), new HttpHeaders(), OK);
        }else {
            return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, "Something went wrong, Please contact your admin.", "", id), new HttpHeaders(), BAD_REQUEST);
        }

    }

    /*
    Delete API for Counter Party Renewal & Enhancement.
    Deletes the entry only for new app ID.
     */
    @ApiOperation("proposedProducts")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/proposedProductDelete/{custId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String proposedProductDelete(@RequestBody ProposedProductsData proposedProductsData, @PathVariable("custId") Long custId, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" => URL : /proposedProductDetails/{custId} | OPERATION : DELETE | Id :",proposedProductsData.getAppId());
        if(permissionService.validation(token,"BUSINESS")) {
            return proposedProductsService.proposedProductDelete(proposedProductsData, custId);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

}
