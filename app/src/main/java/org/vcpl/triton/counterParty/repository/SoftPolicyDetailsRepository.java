package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.counterParty.entity.SoftPolicyDetailsEntity;

import java.util.Collection;
import java.util.List;

@Repository
public interface SoftPolicyDetailsRepository extends JpaRepository<SoftPolicyDetailsEntity,Long> {

    @Query(value = "SELECT * FROM cp_soft_policy_details where app_id = ?1",nativeQuery = true)
    Collection<SoftPolicyDetailsEntity> findByFId(long id);
}
