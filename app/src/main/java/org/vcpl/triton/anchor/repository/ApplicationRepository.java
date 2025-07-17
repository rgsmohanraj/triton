package org.vcpl.triton.anchor.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.anchor.entity.AnchorGstEntity;
import org.vcpl.triton.anchor.entity.ApplicationEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity,Long> {
    @Query(value = "SELECT * FROM application_details_info WHERE cust_id = ?1",nativeQuery = true)
    Collection<ApplicationEntity> findByAppId(Long id);

    @Query(value="DELETE FROM application_details_info WHERE id=?1",nativeQuery = true)
    boolean deleteById(long id);

    @Query(value = "SELECT * FROM application_details_info WHERE type = 1",nativeQuery = true)
    List<ApplicationEntity> findByAnchor();

    @Query(value = "SELECT * FROM application_details_info WHERE type = 2",nativeQuery = true)
    List<ApplicationEntity> findByCp();

    @Query(value = "SELECT * FROM application_details_info WHERE cust_id=:custId and app_type=:appType ORDER BY wf_type DESC LIMIT 1",nativeQuery = true)
    ApplicationEntity findByAppTypeCustId(long custId, int appType);

    @Query(value = "SELECT * FROM application_details_info WHERE cust_id =:id ORDER BY seq_no DESC LIMIT 1",nativeQuery = true)
    ApplicationEntity findByCustId(Long id);

    @Query(value = "SELECT * from application_details_info WHERE cust_id =:custId order by seq_no DESC LIMIT 2", nativeQuery = true)
    List<ApplicationEntity> findApplicationIds(Long custId);

    @Query(value = "SELECT * from application_details_info WHERE cust_id =:custId order by seq_no", nativeQuery = true)
    List<ApplicationEntity> findAllApplicationIds(Long custId);

    @Query(value = "SELECT cust_id from application_details_info WHERE id =:appId", nativeQuery = true)
    Long findCustIdByAppId(Long appId);

    @Query(value = "SELECT MAX(seq_no) from application_details_info WHERE cust_id =:custId", nativeQuery = true)
    Integer findMaxSeqNo(Long custId);
}
