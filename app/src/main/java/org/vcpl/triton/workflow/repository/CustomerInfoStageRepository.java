//package org.vcpl.triton.workflow.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
//
//
//import java.util.List;
//
//public interface CustomerInfoStageRepository extends JpaRepository<CustomerInfoStagesEntity,Long> {
//
//    @Query(value = "SELECT * FROM customer_info_stages c WHERE c.cus_id =?1",nativeQuery = true)
//    CustomerInfoStagesEntity getCusInfoByCusId(long cus_id);
//
//}
