package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vcpl.triton.counterParty.entity.ProposedProductsEntity;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static javax.swing.DropMode.ON;
import static org.apache.poi.xddf.usermodel.chart.LayoutTarget.INNER;

@Repository
public interface ProposedProductsRepository extends JpaRepository<ProposedProductsEntity, Long> {
    @Query(value = "SELECT * FROM cp_proposed_products WHERE app_id = ?1 order by id",nativeQuery = true)
    Collection<ProposedProductsEntity> findByFId(long id);

    @Query(value = "SELECT * FROM cp_proposed_products WHERE app_id = ?1 and credit_policy IS NOT FALSE order by id",nativeQuery = true)
    Collection<ProposedProductsEntity> findActiveProposedProducts(long id);

    @Query(value = "SELECT * FROM cp_proposed_products WHERE app_id = ?1",nativeQuery = true)
    Optional<ProposedProductsEntity> getEntityListByAppId(Long appId);

    @Query(value = "SELECT * FROM cp_proposed_products WHERE app_id = ?1",nativeQuery = true)
    Collection<ProposedProductsEntity> getByAppId(Long appId);

    @Query(value = "SELECT B.id as anchor_id,C.id as credit_app_id FROM cp_proposed_products AS A,customer_info AS B,application_details_info AS C,credit_norms_details D WHERE A.cust_id=C.cust_id AND A.cust_id = B.ID AND B.ID = C.cust_id AND C.id=D.app_id AND A.app_id = ?1 GROUP BY C.id",nativeQuery = true)
    List<Object[]> findCreditNormsId(long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cp_proposed_products where id=?1",nativeQuery = true)
    void deleteProposedById(long id);

//    @Query(value =  "SELECT customer_name FROM customer_info INNER JOIN application_details_info ON customer_info.customer_name = application_details_info.cust_id" ,nativeQuery = true);
//    List<ProposedProductsEntity> getByid(long id);



}
