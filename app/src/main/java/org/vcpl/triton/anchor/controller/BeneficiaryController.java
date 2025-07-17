package org.vcpl.triton.anchor.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.anchor.data.BeneficiaryData;
import org.vcpl.triton.anchor.entity.BeneficiaryEntity;
import org.vcpl.triton.anchor.repository.BeneficiaryRepository;
import org.vcpl.triton.anchor.service.BeneficiaryService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Api(tags = "anchorBeneficiary")
@RestController
@RequestMapping("anchor")
@Validated
public class BeneficiaryController {

    @Autowired
    private BeneficiaryService beneficiaryDetailsService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;



    private static final Logger logger = LoggerFactory.getLogger(BeneficiaryController.class);

    @ApiOperation("anchorBeneficiary")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/anchorBeneficiaryFile/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<BeneficiaryEntity> findByIdAnchorBeneficiary(@PathVariable long id, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /anchorBeneficiaryFile/{id} | OPERATION | " + "GET AnchorBeneficiary");
        if (permissionService.validation(token)) {
            return beneficiaryDetailsService.getBeneficiary(id);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("anchorBeneficiary")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/bankDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllBankDetails( @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /anchorBeneficiaryFile/{id} | OPERATION | " + "GET AnchorBeneficiary");
        if (permissionService.validation(token)) {
            return beneficiaryDetailsService.getBankDetails();
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("anchorBeneficiary")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/branchDetails/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String  getAllBranchDetails(@PathVariable long id, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /anchorBeneficiaryFile/{id} | OPERATION | " + "GET AnchorBeneficiary");
        if (permissionService.validation(token)) {
            return beneficiaryDetailsService.getBranchDetails(id);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("anchorBeneficiary")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/bankDetails/{ifsc}/{bankCode}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String  getBankDetails(@PathVariable String ifsc,@PathVariable String bankCode, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /bankDetails/{ifsc} | OPERATION | " + "GET AnchorBeneficiary");
        if (permissionService.validation(token)) {
            return beneficiaryDetailsService.getBankDetailsWithIfsc(ifsc,bankCode);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("anchorBeneficiary")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/anchorBeneficiary",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<Object> saveBeneficiaryDetails(@Valid @RequestBody BeneficiaryData beneficiaryData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /anchorBeneficiary | OPERATION | " + "POST AnchorBeneficiary");
        if (permissionService.validation(token, "OPERATION MAKER")) {
            try {
                //String beneficiaryEntity = beneficiaryRepository.findDuplicateAcctNo(beneficiaryData.getBankAcctNumber());
//                if(beneficiaryEntity == null) {
                    BeneficiaryEntity beneficiaryFileEntity = beneficiaryDetailsService.saveBeneficiaryDetails(beneficiaryData);
                    return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", beneficiaryFileEntity.getId()), new HttpHeaders(), OK);
//                }else{
//                    return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, "Duplicate Bank Account No", "", beneficiaryEntity), new HttpHeaders(), BAD_REQUEST);
//                }
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<Object>(responseControllerService.getBody(INTERNAL_SERVER_ERROR, "Some Technical Error", "", e.getMessage()), new HttpHeaders(), INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    //    @ApiOperation("anchorBeneficiary")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/anchorBeneficiary/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<BeneficiaryEntity> findByIdBeneficiary(@PathVariable long id) {
        return beneficiaryDetailsService.getBeneficiaryDetailById(id);
    }

    @ApiOperation("anchorBeneficiary")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/anchorBeneficiary",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateBeneficiaryDetails(@Valid @RequestBody BeneficiaryData beneficiaryData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        try {
            BeneficiaryEntity beneficiaryDetailsEntity = beneficiaryDetailsService.updateBeneficiaryDetails(beneficiaryData);
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", beneficiaryDetailsEntity.getId()), new HttpHeaders(), OK);
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            return new ResponseEntity<Object>(responseControllerService.getBody(INTERNAL_SERVER_ERROR, "Some Technical Error", "", e.getMessage()), new HttpHeaders(), INTERNAL_SERVER_ERROR);
        }
    }

    //    @ApiOperation("anchorBeneficiary")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/anchorBeneficiary/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteBeneficiaryDetails(@PathVariable long id) {
        return beneficiaryDetailsService.deleteBeneficiaryDetails(id);
    }

    //    @ApiOperation("anchorBeneficiary")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/beneficiaryDetailsFile_product",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BeneficiaryEntity> getAllProduct() {
        return this.beneficiaryDetailsService.getAllProduct();
    }

}
