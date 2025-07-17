package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.counterParty.entity.CreditPolicyFilters;
import org.vcpl.triton.counterParty.entity.CreditPolicyMasterEntity;

import java.util.List;

@Repository

public interface CreditPolicyMasterRepository extends JpaRepository<CreditPolicyMasterEntity,Long> {

    @Query(value = "SELECT * FROM credit_policy_config WHERE  anchor_relationship = ?1 AND assessment_type = ?2 AND cp_type = ?3 AND recourse = ?4",nativeQuery = true)
    List<Object[]> findConfigId(String anchorRelationship, String assessmentType, String cpType, String recourse);


    @Query(value = "SELECT * FROM  credit_policy_filters  WHERE config_id IN(SELECT id" +
            "                                                                FROM  credit_policy_config" +
            "                                                                WHERE id = ?1);",nativeQuery = true)
    List<CreditPolicyFilters> findFilterId(long id);



}
