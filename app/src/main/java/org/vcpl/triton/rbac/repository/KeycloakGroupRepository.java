package org.vcpl.triton.rbac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.vcpl.triton.rbac.entity.KeycloakGroupsEntity;

import java.util.List;

public interface KeycloakGroupRepository extends JpaRepository<KeycloakGroupsEntity,Long> {

    @Query(value = "SELECT * FROM keycloak_group AS keygroup where keygroup.group_name IN ?1 ORDER BY keygroup.id", nativeQuery = true)
    List<KeycloakGroupsEntity> findByGroupName(List<String> groups);

    @Query(value = "SELECT * FROM keycloak_group AS keygroup where keygroup.group_name =?1  ORDER BY keygroup.id", nativeQuery = true)
    KeycloakGroupsEntity findByGroup(String groups);

    @Query(value = "SELECT * FROM keycloak_group AS keygroup where keygroup.group_name IN ?1  ORDER BY keygroup.id", nativeQuery = true)
    List<KeycloakGroupsEntity> findByGroup(List<?> groups);

    @Modifying
    @Query(value = "truncate table keycloak_group",nativeQuery = true)
    void truncateKeyCloakGroups();

}
