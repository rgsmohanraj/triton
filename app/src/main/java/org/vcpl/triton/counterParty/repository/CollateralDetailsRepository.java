package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.anchor.entity.AnchorKeyEntity;
import org.vcpl.triton.counterParty.entity.CollateralDetailsEntity;

import java.util.Collection;

@Repository
public interface CollateralDetailsRepository extends JpaRepository<CollateralDetailsEntity,Long> {

    @Query(value = "SELECT * FROM cp_collateral_details WHERE app_id = ?1",nativeQuery = true)
    Collection<CollateralDetailsEntity> findByFId(Long id);
}
