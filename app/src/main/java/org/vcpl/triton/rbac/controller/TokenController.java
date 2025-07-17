package org.vcpl.triton.rbac.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.anchor.data.AnchorAddressData;
import org.vcpl.triton.anchor.entity.AnchorAddressEntity;
import org.vcpl.triton.rbac.data.TokenData;
import org.vcpl.triton.rbac.entity.TokenEntity;
import org.vcpl.triton.rbac.service.TokenService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.nio.file.AccessDeniedException;

import static org.springframework.http.HttpStatus.OK;

@Api(tags="Token Validator")
@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private TokenService tokenService;

    @ApiOperation("Token")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/saveToken",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveToken(@Valid @RequestBody TokenData tokenData) throws UnsupportedEncodingException {
        TokenEntity tokenEntity = this.tokenService.saveToken(tokenData);
        return new ResponseEntity<Object>(responseControllerService.getBody(OK,"Success","200",tokenEntity.getBrowserId()),new HttpHeaders(),OK );
    }

    @ApiOperation("Token")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/validatedUser/{browserId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTokenDetailsByBrowserId(@PathVariable String browserId, HttpServletResponse response) throws UnsupportedEncodingException {
        return this.tokenService.getTokenDetailsByBrowserId(browserId,response);
    }

    @ApiOperation("Token")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/validateToken",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean validateToken(@RequestParam String jwt) throws AccessDeniedException {
        return this.tokenService.validate(jwt);
    }
}
