package org.vcpl.triton.rbac.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.vcpl.triton.login.model.LoginResponse;
import org.vcpl.triton.login.service.LoginService;
import org.vcpl.triton.rbac.entity.GroupEntity;
import org.vcpl.triton.rbac.entity.KeycloakGroupsEntity;
//import org.vcpl.triton.rbac.entity.KeycloakUserEntity;
import org.vcpl.triton.rbac.repository.GroupRepositiory;
//import org.vcpl.triton.rbac.repository.KeyCloakUserRepository;
import org.vcpl.triton.rbac.repository.KeycloakGroupRepository;
import org.vcpl.triton.security.config.DatasourceForDbConnect;
import org.vcpl.triton.workflow.data.WFApprovalStatusData;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class GroupService implements IGroup {




    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private GroupRepositiory groupRepositiory;

    @Autowired
    private KeycloakGroupRepository keycloakGroupRepository;

//    @Autowired
//    private KeyCloakUserRepository keyCloakUserEntityRepository;

    @Autowired
    private LoginService loginService;

//    @Value("${keycloak.auth-server-url}")
    private String authURL;

//    @Value("${keycloak.admin-username}")
    private String adminUserName;

//    @Value("${keycloak.admin-password}")
    private String adminPassword;

//    @Value("${keycloak.admin-client-id}")
    private String adminClientId;

//    @Value("${keycloak.admin-grant-type}")
    private String adminGrantType;

    public GroupService(DatasourceForDbConnect data){
        this.adminGrantType = data.getKeycloakAdminType();
        this.adminClientId = data.getKeycloakAdminId();
        this.adminPassword = data.getKeycloakPassword();
        this.adminUserName =  data.getKeycloakUsername();
        this.authURL = data.getKeycloakAuthServerUrl();
    }


    @Override
    public GroupEntity getGroupById(long id) {
        GroupEntity entity = this.groupRepositiory.findById(id).orElse(null);
        return entity;
    }

//    @Override
//    public KeycloakGroupsEntity keycloakGroupsEntityByName(String groupName){
//        return this.keycloakGroupRepository.findByGroup(groupName);
//    }

    @Transactional
    public List<KeycloakGroupsEntity> saveGroupsFromKeycloak() {
        List<KeycloakGroupsEntity> keycloakGroupsEntities = new ArrayList<>();
        try {
            logger.info("Save Current Keycloak Group");
            ResponseEntity<LoginResponse> adminCli = getAdminCliAccessToken();
            String accessToken = adminCli.getBody().getAccess_token();
            if (accessToken.length() > 0) {
                keycloakGroupRepository.truncateKeyCloakGroups();
//                this.keycloakGroupRepository.deleteAll();
//                String keycloakUrl = authURL + "/admin/realms/ThinkIAM_DEV/groups";
                String keycloakUrl = authURL + "/admin/realms/ThinkIAM/groups";


                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + accessToken);

                HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.exchange(keycloakUrl, HttpMethod.GET, entity, String.class);
                JSONArray array = new JSONArray(response.getBody());
                for (int i = 0; i < array.length(); i++) {
                    KeycloakGroupsEntity keycloakGroupsEntity = new KeycloakGroupsEntity();
                    JSONObject object = array.getJSONObject(i);
                    keycloakGroupsEntity.setUserId(object.getString("id"));
                    keycloakGroupsEntity.setGroupName(object.getString("name"));
                    keycloakGroupsEntity.setGroupPath(object.getString("path"));
                    JSONArray jsonArray = (JSONArray) object.get("subGroups");
                    List<String> subGroup = new ArrayList<String>();
                    for (int j = 0; j < jsonArray.length(); j++) {
                        subGroup.add(jsonArray.getString(j));
                    }
                    keycloakGroupsEntity.setSubGroup(subGroup.toArray(new String[subGroup.size()]));
                    try {
                        this.keycloakGroupRepository.save(keycloakGroupsEntity);
//                        userEntities.addAll(saveUsersWithInGroup(keycloakGroupsEntity,accessToken));
//                        this.keyCloakUserEntityRepository.saveAll(userEntities);
                    } catch (Exception e) {
//                        this.keycloakGroupRepository.deleteAll();
//                        this.keyCloakUserEntityRepository.deleteAll();
                        e.printStackTrace();
                    }
                }
                return keycloakGroupsEntities;
            } else {
                return keycloakGroupsEntities;
            }
        } catch (Exception e) {
//            logger.error("Group Service ---- ",e.getMessage());
            e.printStackTrace();
        }
        return keycloakGroupsEntities;
    }

//    public List<KeycloakUserEntity> saveUsersWithInGroup(KeycloakGroupsEntity keycloakGroupsEntity, String accessToken) {
////        ResponseEntity<LoginResponse> accessToken =  getAdminCliAccessToken();
//        if (accessToken.length() > 0) {
//            String keycloakUrl = authURL + "/admin/realms/ThinkIAM/groups/" + keycloakGroupsEntity.getUserId() + "/members";
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Authorization", "Bearer " + accessToken);
//            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<String> response = restTemplate.exchange(keycloakUrl, HttpMethod.GET, entity, String.class);
//            JSONArray array = new JSONArray(response.getBody());
//            List<KeycloakUserEntity> user = new ArrayList<>();
//            for (int i = 0; i < array.length(); i++) {
//                KeycloakUserEntity userEntity = new KeycloakUserEntity();
//                JSONObject object = array.getJSONObject(i);
//                userEntity.setUserId(object.getString("id"));
//                userEntity.setEmail(object.getString("email"));
//                userEntity.setFirstName(object.getString("firstName"));
//                userEntity.setLastName(object.getString("lastName"));
//                userEntity.setVerified((Boolean) object.get("emailVerified"));
//                userEntity.setUsername(object.getString("username"));
//                userEntity.setUserCreationTimestamp(new Timestamp((Long) object.get("createdTimestamp")));
//                userEntity.setGroupId(keycloakGroupRepository.findById(keycloakGroupsEntity.getId()).orElseThrow());
////                this.keyCloakUserEntityRepository.save(userEntity);
//                user.add(userEntity);
//            }
//            return user;
//        }
//        return null;
//    }

    public ResponseEntity<LoginResponse> getAdminCliAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", adminClientId);
//            map.add("client_secret", clientSec);
        map.add("grant_type", adminGrantType);
        map.add("username", adminUserName);
        map.add("password", adminPassword);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(authURL + "/realms/master/protocol/openid-connect/token", httpEntity, LoginResponse.class);
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    public List<KeycloakGroupsEntity> getGroupsFromKeycloak() {
        return this.keycloakGroupRepository.findAll();
    }

    @Override
    public String getAllUserFromUnderWriter() throws UnsupportedEncodingException {
        ResponseEntity<LoginResponse> token = getAdminCliAccessToken();
        String[] groupName = {"CP_CREDIT_UNDERWRITER_LEAD", "CP_RISK_UNDERWRITER_LEAD"};
        List<KeycloakGroupsEntity> keycloakGroupsEntities = new ArrayList<>();
        for (int i = 0; i < groupName.length; i++) {
            KeycloakGroupsEntity group = keycloakGroupsEntityByName(groupName[i]);
            keycloakGroupsEntities.add(group);
        }
        return groupOfUsers(token.getBody().getAccess_token(), keycloakGroupsEntities);
    }

    public String groupOfUsers(String token, List<KeycloakGroupsEntity> group) throws UnsupportedEncodingException {
        JSONObject json = new JSONObject();
        JSONArray array1 = new JSONArray();

        if (group != null) {
            for (KeycloakGroupsEntity key : group) {
                if (token.length() > 0) {
//                    String keycloakUrl = authURL + "/admin/realms/ThinkIAM_DEV/groups/" + key.getUserId() + "/members";
                    String keycloakUrl = authURL + "/admin/realms/ThinkIAM/groups/" + key.getUserId() + "/members";
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", "Bearer " + token);
                    HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<String> response = restTemplate.exchange(keycloakUrl, HttpMethod.GET, entity, String.class);
                    JSONArray array = new JSONArray(response.getBody());
                    array1.put(array);
                }
            }
        }
        json.put("userDetails", array1);
        return json.toString();
    }

    public List<KeycloakGroupsEntity> getKeycloakGroupByRoleName(List<?> role) {
        return keycloakGroupRepository.findByGroup(role);
    }

    public String groupOfUsers(String token) throws UnsupportedEncodingException {
        JSONObject json = new JSONObject();
        JSONArray array1 = new JSONArray();
//        String jwt = loginService.decodeJWT(token);
//        JSONObject json1 = new JSONObject(jwt);
//        List<?> role = loginService.getRole(json1);
        List<String> role = new ArrayList<>();
        role.add(token);
        List<KeycloakGroupsEntity> keycloakGroupsEntities = this.getKeycloakGroupByRoleName(role);
        ResponseEntity<LoginResponse> accessToken = getAdminCliAccessToken();
        if (keycloakGroupsEntities.size() > 0) {
            for (KeycloakGroupsEntity key : keycloakGroupsEntities) {
                if (accessToken.getBody().getAccess_token().length() > 0) {
//                    String keycloakUrl = authURL + "/admin/realms/ThinkIAM_DEV/groups/" + key.getUserId() + "/members";
                    String keycloakUrl = authURL + "/admin/realms/ThinkIAM/groups/" + key.getUserId() + "/members";

                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", "Bearer " + accessToken.getBody().getAccess_token());
                    HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<String> response = restTemplate.exchange(keycloakUrl, HttpMethod.GET, entity, String.class);
                    JSONArray array = new JSONArray(response.getBody());
                    array1.put(array);
                }
            }
        }
        json.put("userDetails", array1);
        return json.toString();
    }

    public WFApprovalStatusData changeNextStageLeadEmail(WFApprovalStatusData wfApprovalStatusData) throws UnsupportedEncodingException {
        ResponseEntity<LoginResponse> token = getAdminCliAccessToken();
        KeycloakGroupsEntity group = keycloakGroupsEntityByName(wfApprovalStatusData.getNextApproverInfo());
        if (token.getBody().getAccess_token().length() > 0) {
//            String keycloakUrl = authURL + "/admin/realms/ThinkIAM_DEV/groups/" + group.getUserId() + "/members";
            String keycloakUrl = authURL + "/admin/realms/ThinkIAM/groups/" + group.getUserId() + "/members";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token.getBody().getAccess_token());
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(keycloakUrl, HttpMethod.GET, entity, String.class);
            JSONArray array = new JSONArray(response.getBody());
            JSONObject json;
            for (int i = 0; i < array.length(); i++) {
                JSONObject json1 = array.getJSONObject(i);
                try {
                    json = (JSONObject) json1.get("attributes");
                    JSONArray flag = (JSONArray) json.get("LEAD");
                    if (flag.get(0).equals("true")) {
                        wfApprovalStatusData.setNextApproverInfo(json1.get("email").toString());
                        break;
                    }
                } catch (Exception e) {
//                    e.printStackTrace();
                    continue;
                }
            }
        }
        return wfApprovalStatusData;
    }

    public KeycloakGroupsEntity keycloakGroupsEntityByName(String groupName) {
        return this.keycloakGroupRepository.findByGroup(groupName);
    }

    public String getUsersByGroup(String groupName) {
        JSONObject json = new JSONObject();
        JSONArray array1 = new JSONArray();
        ResponseEntity<LoginResponse> token = getAdminCliAccessToken();
        if (token.getBody().getAccess_token().length() > 0) {
            KeycloakGroupsEntity group = keycloakGroupRepository.findByGroup(groupName);
            if (group != null) {
                String keycloakUrl = authURL + "/admin/realms/ThinkIAM/groups/" + group.getUserId() + "/members";
//                String keycloakUrl = authURL + "/admin/realms/ThinkIAM_DEV/groups/" + group.getUserId() + "/members";
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + token.getBody().getAccess_token());
                HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.exchange(keycloakUrl, HttpMethod.GET, entity, String.class);
                JSONArray array = new JSONArray(response.getBody());
                array1.put(array);
            }
        }
        json.put("userDetails", array1);
        return json.toString();
    }
}
