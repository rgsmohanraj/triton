package org.vcpl.triton.anchor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vcpl.triton.anchor.entity.ProgramNormsEntity;
import org.vcpl.triton.counterParty.entity.ProposedProductsEntity;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProgramNormsRepository extends JpaRepository<ProgramNormsEntity,Long> {
    @Query(value = "SELECT * FROM anchor_program WHERE app_id = ?1",nativeQuery = true)
    Collection<ProgramNormsEntity> findByCiId(Long id);


//    @Query(value="SELECT * FROM anchor_program where app_id In (SELECT id FROM application_details_info WHERE cust_id = ?1)AND sub_product = ?2",nativeQuery = true)
    @Query(value = "SELECT p.* from anchor_program as p where p.sub_product = ?2 and p.app_id = (select a.id from application_details_info as a where cust_id=?1 order by seq_no desc limit 1)",nativeQuery = true)
    ProgramNormsEntity fidByAppId(long id,String productName);

//    @Query(value = "SELECT p.* FROM anchor_program as p,application_details_info as a WHERE p.app_id = a.id AND a.cust_id = ?1",nativeQuery = true)
    @Query(value = "select p.* from anchor_program as p where p.app_id = (select a.id from application_details_info as a where cust_id=?1 order by seq_no desc limit 1)",nativeQuery = true)
    Collection<ProgramNormsEntity> findByCustId(long id);

    @Query(value = "SELECT * FROM anchor_program WHERE app_id  =?1",nativeQuery = true)
    List<ProgramNormsEntity> getByAppId(Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM anchor_program where id =?1 and app_id =?2", nativeQuery = true)
    void deleteByIdAndAppId(Long id, Long appId);

    @Query(value = "SELECT MIN(product_expiry) FROM anchor_program WHERE app_id = ?1",nativeQuery = true)
    Integer getMinProductExpiry(Long id);
}
