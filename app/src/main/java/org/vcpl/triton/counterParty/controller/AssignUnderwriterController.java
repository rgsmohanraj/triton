package org.vcpl.triton.counterParty.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.anchor.controller.AnchorAddressController;
import org.vcpl.triton.anchor.data.BeneficiaryData;
import org.vcpl.triton.anchor.entity.BeneficiaryEntity;
import org.vcpl.triton.counterParty.data.AssignUnderwriterData;
import org.vcpl.triton.counterParty.data.CommercialData;
import org.vcpl.triton.counterParty.entity.AssignUnderwriterEntity;
import org.vcpl.triton.counterParty.entity.CommercialEntity;
import org.vcpl.triton.counterParty.service.AssignUnderwriterService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@Api(tags="assignUnderwriter")
@RestController
@RequestMapping("counterParty")
public class AssignUnderwriterController {

    private static final Logger logger = LoggerFactory.getLogger(AssignUnderwriterController.class);

    @Autowired
    private AssignUnderwriterService assignUnderwriterService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("assignUnderwriter")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/assignUnderwriter",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<Object> saveAssignUnderwriter(@Valid @RequestBody AssignUnderwriterData assignUnderwriterData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /assignUnderwriter | OPERATION | " + "POST AssignUnderwriter");
        if (permissionService.validation(token)) {
            try {
                AssignUnderwriterEntity assignUnderwriterEntity = assignUnderwriterService.saveAssignUnderwriter(assignUnderwriterData);
                return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", assignUnderwriterEntity.getId()), new HttpHeaders(), OK);
            }catch (DataIntegrityViolationException e){
                e.printStackTrace();
                return new ResponseEntity<Object>(responseControllerService.getBody(INTERNAL_SERVER_ERROR, "Duplicate Data", "", e.getMessage()), new HttpHeaders(), INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("assignUnderwriter")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/assignUnderwriter/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<AssignUnderwriterEntity> findByIdAssignUnderwriter(@PathVariable long id, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /assignUnderwriter/{id} | OPERATION | " + "GET AssignUnderwriter");
        if (permissionService.validation(token)) {
            return assignUnderwriterService.getAssignUnderwriter(id);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }



    @ApiOperation("assignUnderwriter")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/assignUnderwriter",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AssignUnderwriterEntity updateAssignUnderwriter(@Valid @RequestBody AssignUnderwriterData assignUnderwriterData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        AssignUnderwriterEntity assignUnderwriterEntity = assignUnderwriterService.updateAssignUnderwriter(assignUnderwriterData);
        return assignUnderwriterEntity;
    }


}
