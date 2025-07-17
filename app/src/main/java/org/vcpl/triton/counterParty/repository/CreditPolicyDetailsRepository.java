package org.vcpl.triton.counterParty.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.counterParty.entity.CPDebtProfileEntity;
import org.vcpl.triton.counterParty.entity.CreditPolicyDetailsEntity;

import java.util.Collection;

@Repository
public interface CreditPolicyDetailsRepository extends JpaRepository<CreditPolicyDetailsEntity ,Long> {

    @Query(value = "SELECT * FROM cp_credit_policy_details WHERE app_id = ?1",nativeQuery = true)
    Collection<CreditPolicyDetailsEntity> findByAppId(long id);
}
