package org.vcpl.triton.rbac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vcpl.triton.rbac.entity.PermissionEntity;

import java.util.List;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

    @Query(value = "SELECT * FROM rbac_permission AS p WHERE p.role IN ?1 ORDER BY p.id",nativeQuery = true)
    List<PermissionEntity>  findByRole(List<?> role);

    @Query(value = "SELECT * FROM rbac_permission AS p where p.role IN ?1 ORDER BY p.id",nativeQuery = true)
    List<PermissionEntity> getPermissionList(List<?> role);

}
