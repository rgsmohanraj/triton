package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.counterParty.entity.CollateralMasterEntity;

@Repository
public interface CollateralMasterRepository extends JpaRepository<CollateralMasterEntity,Long> {
}
