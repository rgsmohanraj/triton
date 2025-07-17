package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.vcpl.triton.counterParty.entity.CreditPolicyConfigEntity;

import java.util.List;

@Repository
public interface CreditPolicyConfigRepo extends JpaRepository<CreditPolicyConfigEntity,Long> {
    @Query(value = "SELECT * FROM credit_policy_config  WHERE ((anchor_relationship LIKE %:anchorRelationship%) AND assessment_type= :assessmentType AND cp_type = :cpType AND recourse = :recourse)",nativeQuery = true)
    CreditPolicyConfigEntity findConfigId(String anchorRelationship,String assessmentType,String cpType,String recourse);

//    @Query(value = "SELECT * FROM credit_policy_config  WHERE ((anchor_relationship LIKE %:anchorRelationship%) AND assessment_type= :assessmentType AND cp_type = :cpType AND recourse = :recourse)",nativeQuery = true)
//    CreditPolicyConfigEntity findConfig(String anchorRelationship,String assessmentType,String cpType,String recourse);

}
