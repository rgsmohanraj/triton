package org.vcpl.triton.rbac.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import org.vcpl.triton.rbac.entity.GroupEntity;
import org.vcpl.triton.rbac.entity.KeycloakGroupsEntity;
//import org.vcpl.triton.rbac.entity.KeycloakUserEntity;
import org.vcpl.triton.rbac.service.GroupService;
import org.vcpl.triton.security.config.DatasourceForDbConnect;
import org.vcpl.triton.workflow.data.WFApprovalStatusData;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Api(tags="RBAC")
@RestController
@RequestMapping("group")
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    GroupService groupService;

//    @Value("${keycloak.auth-server-url}")
    private String authURL;



    private Keycloak getKeycloakInstance() {
        return Keycloak.getInstance(
                "http://10.100.10.32:8080/auth",
                "master",
                "admin",
                "admin",
                "ThinkIAM",
                "Cj7d397ypEqNxOmzZUes74JCY8ssVhuq");
    }


    @ApiOperation("RBAC")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/group/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupEntity getAllWorkflowStage(@PathVariable long id) {
        return groupService.getGroupById(id);
    }

    @ApiOperation("RBAC")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getCreditUnderWriterUsers",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllUsersFromUnderWriter() throws UnsupportedEncodingException {
        return groupService.getAllUserFromUnderWriter();
    }

    @ApiOperation("RBAC")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/users",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserRepresentation> getUsers() {
        try {
            Keycloak keycloak = getKeycloakInstance();
            return keycloak.realm("ThinkIAM").users().list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation("RBAC")
    @RequestMapping(value = "/saveKeycloakGroups",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<KeycloakGroupsEntity> saveGroupsFromKeycloak() {
        return this.groupService.saveGroupsFromKeycloak();
    }

    @ApiOperation("RBAC")
    @RequestMapping(value = "/getGroups",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<KeycloakGroupsEntity> getGroupsFromKeycloak() {
        return this.groupService.getGroupsFromKeycloak();
    }

    @ApiOperation("RBAC")
    @RequestMapping(value = "/groupOfUsers/{groupName}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupOfUsers(@RequestHeader(value="Authorization") String token, HttpServletResponse response,@PathVariable String groupName) throws UnsupportedEncodingException {
        return this.groupService.groupOfUsers(groupName);
    }

    @ApiOperation("RBAC")
    @RequestMapping(value = "/changeEmail",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WFApprovalStatusData changeNextStageLeadEmail(@RequestBody WFApprovalStatusData wfApprovalStatusData) throws UnsupportedEncodingException {
        return this.groupService.changeNextStageLeadEmail(wfApprovalStatusData);
    }

    @ApiOperation("RBAC")
    @RequestMapping(value = "/getKeycloakUserByGroupName/{groupName}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getKeycloakUserByGroupName(@PathVariable String groupName) {
        return this.groupService.getUsersByGroup(groupName);
    }

}
