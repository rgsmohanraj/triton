package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.counterParty.entity.DueDiligenceMasterEntity;

@Repository
public interface DueDiligenceMasterRepository extends JpaRepository<DueDiligenceMasterEntity,Long>{
}
