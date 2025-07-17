package org.vcpl.triton.anchor.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.anchor.data.RemarksData;
import org.vcpl.triton.anchor.entity.RemarksEntity;
import org.vcpl.triton.anchor.service.RemarksService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import static org.springframework.http.HttpStatus.OK;


@Api(tags="remarks")
@RestController
@RequestMapping("anchor")
@Validated

public class RemarksController {

    @Autowired
    private RemarksService remarksService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;


    @ApiOperation("remarks")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/remarks",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveRemarks(@Valid @RequestBody RemarksData remarksData,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException{
        if(permissionService.validation(token)) {
            RemarksEntity remarksEntity=remarksService.saveRemarks(remarksData);
            return new ResponseEntity<Object>(responseControllerService.getBody(OK,"Success","",remarksEntity.getId()),new HttpHeaders(),OK );
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "Success", "", HttpServletResponse.SC_FORBIDDEN), new HttpHeaders(), OK);
        }
    }

    @ApiOperation("remarks")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/remarks/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<RemarksEntity> findByIdRemarks(@PathVariable long id,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        if(permissionService.validation(token)) {
            return remarksService.getRemarks(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }
}


