package org.vcpl.triton.rbac.service;

import org.vcpl.triton.rbac.entity.GroupEntity;
import org.vcpl.triton.rbac.entity.KeycloakGroupsEntity;
import org.vcpl.triton.workflow.data.WFApprovalStatusData;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IGroup {
   public GroupEntity getGroupById(long id);

   List<KeycloakGroupsEntity> saveGroupsFromKeycloak();

   List<KeycloakGroupsEntity> getGroupsFromKeycloak ();

//   List<KeycloakUserEntity> getAllUserFromUnderWriter();

   List<KeycloakGroupsEntity> getKeycloakGroupByRoleName(List<?> role);

   WFApprovalStatusData changeNextStageLeadEmail(WFApprovalStatusData wfApprovalStatusData) throws UnsupportedEncodingException;

   String getAllUserFromUnderWriter() throws UnsupportedEncodingException;

   KeycloakGroupsEntity keycloakGroupsEntityByName(String groupName);

   String getUsersByGroup(String groupName);
}
