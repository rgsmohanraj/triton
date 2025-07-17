package org.vcpl.triton.counterParty.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.anchor.data.AnchorGstMasterData;
import org.vcpl.triton.anchor.entity.AnchorGstEntity;
import org.vcpl.triton.counterParty.data.CPDebtProfileData;
import org.vcpl.triton.counterParty.data.CPDebtProfileMasterData;
import org.vcpl.triton.counterParty.entity.CPDebtProfileEntity;
import org.vcpl.triton.counterParty.service.CPDebtProfileService;

import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Api(tags="cpDebtProfile")
@RestController
@RequestMapping("counterParty")
public class CPDebtProfileController {

    private static final Logger logger = LoggerFactory.getLogger(CPDebtProfileController.class);


    @Autowired
    private CPDebtProfileService cpDebtProfileService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;

//    @ApiOperation("cpDebtProfile")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/cpDebtProfile",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CPDebtProfileEntity> getAllCPDebtProfile(){
        logger.info(" | URL | /cpDebtProfile | OPERATION | " + "GET cpDebtProfile");
    try {
        return this.cpDebtProfileService.getAllCPDebtProfile();
    }catch (Exception ex){
        logger.error(" | URL | /cpDebtProfile | OPERATION | " + " Error |" + ex.getMessage());
    }
    return  null;
        }

    @ApiOperation("cpDebtProfile")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/cpDebtProfile/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<CPDebtProfileEntity> findByCpDebtProfile (@PathVariable long id, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /cpDebtProfile/{id} | OPERATION | " + "GETById cpDebtProfile");
        if(permissionService.validation(token)) {
        return cpDebtProfileService.findByCpDebtProfile(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    return  null;
    }

    @ApiOperation("cpDebtProfile")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/cpDebtProfile/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> saveCpDebtProfile(@PathVariable long id,@Valid @RequestBody CPDebtProfileMasterData cpDebtProfileMasterData, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /cpDebtProfile/{id} | OPERATION | " + "POST cpDebtProfile");
        try {
            if (permissionService.validation(token, "CPA,CREDIT COMMITTEE")) {
                return cpDebtProfileService.saveCpDebtProfile(cpDebtProfileMasterData, id);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation("cpDebtProfile")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/cpDebtProfile/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> updateCpDebtProfile(@Valid @RequestBody CPDebtProfileMasterData cpDebtProfileMasterData,@PathVariable Long id, @RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /cpDebtProfile/{id} | OPERATION | " + "PUT cpDebtProfile");
        try{
            return cpDebtProfileService.updateCpDebtProfile(cpDebtProfileMasterData,id);
        }catch (Exception ex){
            logger.error(" | URL | /cpDebtProfile/{id} | OPERATION | " + " Error |" + ex.getMessage());
        }
        return  null;
    }

//    @ApiOperation("cpDebtProfile")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/cpDebtProfile/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteCPDebtProfile(@PathVariable long id){
        logger.info(" | URL | /cpDebtProfile/{id} | OPERATION | " + "DELETE cpDebtProfile");
        try {
            return cpDebtProfileService.deleteCPDebtProfile(id);
        }catch (Exception ex){
            logger.error(" | URL | /cpDebtProfile/{id} | OPERATION | " + " Error |" + ex.getMessage());
        }
        return  null;
    }

    @ApiOperation("cpDebtProfile")
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/cpDebtProfile/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteDebtProfile(@PathVariable long id) {
        String result = cpDebtProfileService.deleteDebtProfile(id);
        if(result.equals("success")){
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", id), new HttpHeaders(), OK);
        }else {
            return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, "Something went wrong, Please contact your admin.", "", id), new HttpHeaders(), BAD_REQUEST);
        }

    }

}
