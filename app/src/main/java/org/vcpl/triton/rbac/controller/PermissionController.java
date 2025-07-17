package org.vcpl.triton.rbac.controller;

import com.auth0.jwt.impl.ClaimsHolder;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.login.service.LoginService;
import org.vcpl.triton.rbac.entity.PermissionEntity;
import org.vcpl.triton.rbac.service.PermissionService;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Api(tags="Permission")
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @Autowired
    LoginService loginService;

    @ApiOperation("Token")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/permission",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PermissionEntity> getPermissionListByRole(@RequestHeader(value="Authorization") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        try {
            if (permissionService.validation(token)) {
                JSONObject json = new JSONObject();
                String jwt = loginService.decodeJWT(token);
                json = new JSONObject(jwt);
                List<?> role = loginService.getRole(json);
                return this.permissionService.getPermissionListByRole(role);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation("Token")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/tokenValidation")
    public String privateApi(@RequestHeader(value = "authorization", defaultValue = "") String auth) throws Exception {
        permissionService.tokenValidation(auth);
        return "validtion";
    }

}
