package org.vcpl.triton.anchor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.anchor.data.CreditNormsMasterData;
import org.vcpl.triton.anchor.entity.CreditNormsMasterEntity;
import org.vcpl.triton.anchor.service.CreditNormsMasterService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Api(tags="creditNormsMaster")
@RestController
@RequestMapping("anchor")
public class CreditNormsMasterController {

    @Autowired
    private CreditNormsMasterService creditNormsMasterService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("creditNormsMaster")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/creditNormsMaster",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public List<CreditNormsMasterEntity> getAllCreditNormsMaster(@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        if (permissionService.validation(token)) {
            return this.creditNormsMasterService.getAllCreditNormsMaster();
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

//    @ApiOperation("creditNormsMaster")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/creditNormsMaster/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public CreditNormsMasterEntity findByCreditNormsMasterId(@PathVariable long id,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        if (permissionService.validation(token)) {
            return creditNormsMasterService.findCreditNormsMasterById(id);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

//    @ApiOperation("creditNormsMaster")
//    @RequestMapping(
//            method = RequestMethod.POST,
//            value = "/creditNormsMaster",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveCreditNormsMaster(@Valid @RequestBody CreditNormsMasterData creditNormsMasterData,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        if (permissionService.validation(token)) {
            CreditNormsMasterEntity creditNormsMasterEntity=creditNormsMasterService.saveCreditNormsMaster(creditNormsMasterData);
            return new ResponseEntity<Object>(responseControllerService.getBody(OK,"Success","",creditNormsMasterEntity.getId()),new HttpHeaders(),OK );
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

//    @ApiOperation("creditNormsMaster")
//    @RequestMapping(
//            method = RequestMethod.PUT,
//            value = "/creditNormsMaster/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public CreditNormsMasterEntity updateCreditNormsMaster(@PathVariable long id, @Valid @RequestBody CreditNormsMasterData creditNormsMasterData,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        if (permissionService.validation(token)) {
            CreditNormsMasterEntity creditNormsMasterEntity = creditNormsMasterService.updateCreditNormsMaster(creditNormsMasterData,id);
            return creditNormsMasterEntity;
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

//    @ApiOperation("creditNormsMaster")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/creditNormsMaster/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteCreditNormsMaster(@PathVariable long id,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        if (permissionService.validation(token)) {
            return  creditNormsMasterService.deleteCreditNormsMaster(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }
}
