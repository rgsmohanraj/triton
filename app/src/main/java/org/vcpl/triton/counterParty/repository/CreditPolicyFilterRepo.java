package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.counterParty.entity.CreditPolicyFilters;

import java.util.List;

@Repository
public interface CreditPolicyFilterRepo extends JpaRepository<CreditPolicyFilters,Long> {

    @Query(value = "SELECT * FROM  credit_policy_filters  WHERE config_id IN(SELECT id" +
            "                                                                FROM  credit_policy_config" +
            "                                                                WHERE id = ?1);",nativeQuery = true)
    List<CreditPolicyFilters> findFilterId(long id);

    @Query(value = "SELECT * FROM credit_policy_filters WHERE config_id = ?1 and type = 1" +
            " and display_name_id in (SELECT id FROM credit_policy_master where mandatory = '1')",nativeQuery = true)
    List<CreditPolicyFilters> findFilter(long id);
}
