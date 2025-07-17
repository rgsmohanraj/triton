package org.vcpl.triton.rbac.service;

import io.jsonwebtoken.Claims;
import org.vcpl.triton.rbac.entity.PermissionEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;


public interface IPermisssion {

    List<PermissionEntity> getPermissionListByRole(List<?> role);

    boolean validation(String token) throws UnsupportedEncodingException;

    Claims tokenValidation(String token) throws Exception;
}
