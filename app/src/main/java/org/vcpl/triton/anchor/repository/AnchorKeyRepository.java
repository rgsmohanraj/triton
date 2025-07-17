package org.vcpl.triton.anchor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.anchor.entity.AnchorKeyEntity;

import java.util.Collection;

@Repository

public interface AnchorKeyRepository extends JpaRepository<AnchorKeyEntity,Long> {
    @Query(value = "SELECT * FROM anchor_key WHERE app_id = ?1",nativeQuery = true)
    Collection<AnchorKeyEntity> findByCiId(Long id);

    @Query(value="DELETE FROM anchor_key WHERE app_id=?1",nativeQuery = true)
    void deleteByAppId(long id);
}
