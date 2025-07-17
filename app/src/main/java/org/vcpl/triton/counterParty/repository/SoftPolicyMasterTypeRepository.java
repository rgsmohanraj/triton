package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.counterParty.entity.SoftPolicyMasterTypeEntity;

@Repository
public interface SoftPolicyMasterTypeRepository extends JpaRepository<SoftPolicyMasterTypeEntity,Long> {
}
