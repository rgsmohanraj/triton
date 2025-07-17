package org.vcpl.triton.anchor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.anchor.entity.AnchorAuthorizedEntity;

import java.util.Collection;

@Repository
public interface AnchorAuthorizedRepository extends JpaRepository<AnchorAuthorizedEntity, Long> {
    @Query(value = "SELECT * FROM anchor_authorized WHERE app_id = ?1",nativeQuery = true)
    Collection<AnchorAuthorizedEntity> findByCiId (Long id);

    @Query(value="DELETE FROM anchor_authorized WHERE app_id=?1",nativeQuery = true)
    void deleteByAppId(long id);

}
