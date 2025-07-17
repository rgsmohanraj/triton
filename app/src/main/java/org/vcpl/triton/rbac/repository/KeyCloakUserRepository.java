package org.vcpl.triton.rbac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.vcpl.triton.rbac.entity.KeycloakGroupsEntity;
//import org.vcpl.triton.rbac.entity.KeycloakUserEntity;

import java.util.List;

//public interface KeyCloakUserRepository extends JpaRepository<KeycloakUserEntity,Long> {
//    @Query(value = "SELECT * FROM keycloak_user AS key_user where key_user.group_id = ?1  ORDER BY key_user.id",nativeQuery = true)
//    List<KeycloakUserEntity> findByUserDetailsBygroupName(Long groupId);

//}
