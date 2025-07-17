package org.vcpl.triton.anchor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.anchor.entity.AnchorGstEntity;

import java.util.Collection;

@Repository
public interface AnchorGstRepository extends JpaRepository<AnchorGstEntity,Long> {
    @Query(value = "SELECT * FROM anchor_gst WHERE app_id = ?1",nativeQuery = true)
    Collection<AnchorGstEntity> findByCiId(Long id);

    @Query(value="DELETE FROM anchor_gst WHERE app_id=?1",nativeQuery = true)
    void deleteByAppId(long id);
}
