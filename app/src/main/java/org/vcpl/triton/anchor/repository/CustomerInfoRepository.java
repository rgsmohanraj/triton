package org.vcpl.triton.anchor.repository;

import com.sun.source.doctree.SeeTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.vcpl.triton.anchor.data.CustomerInfoData;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;

import java.util.List;

@Repository
//@Transactional(noRollbackFor = Exception.class)
//@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = {Throwable.class})
public interface CustomerInfoRepository extends JpaRepository<CustomerInfoEntity,Long> {
    @Query(value = "SELECT cust.*,app.id AS applicationId FROM workflow_stage_approval_status AS wf,application_details_info AS app,workflow_stage AS wfstage,customer_info AS cust" +
            "                 WHERE app.id = wf.app_id AND wf.application_status = 2 AND wf.stage_id IN (SELECT wfstage.id" +
            "                 FROM workflow_stage AS wfstage " +
            "                 WHERE wfstage.stage= :stage) AND cust.id IN (SELECT ap.cust_id" +
            "                 FROM application_details_info AS ap " +
            "                 WHERE type = :type) AND (cust.customer_name LIKE %:query% OR cust.pan LIKE %:query% OR cust.cin LIKE %:query% )AND app.cust_id = cust.id group by app.id",nativeQuery = true)
    List<Object[]> searchCustomerInfoSQL(Integer type,String stage,String query);

    @Query(value = "SELECT cust.*,app.id AS applicationId FROM workflow_stage_approval_status AS wf,application_details_info AS app,workflow_stage AS wfstage,customer_info AS cust" +
            "               WHERE app.id = wf.app_id AND wf.application_status = :status AND cust.id IN (SELECT ap.cust_id" +
            "               FROM application_details_info AS ap " +
            "                      WHERE type = :type) AND (cust.customer_name LIKE %:query% OR cust.pan LIKE %:query% OR cust.cin LIKE %:query% )AND app.cust_id = cust.id group by app.id",nativeQuery = true)
    List<Object[]> searchCustomerInfoInProgressAndRejectSQL(Integer type,String status,String query);


    @Query(value = "SELECT * FROM customer_info c WHERE c.pan =?1",nativeQuery = true)
    List<CustomerInfoEntity> deDupeCustomerInfo(String pan);

    @Query(value = "SELECT * FROM customer_info c WHERE c.pan =?1 or c.cin = ?2 ",nativeQuery = true)
    List<CustomerInfoEntity> customerInfoDetails(String pan,String cin);

    @Query(value = "SELECT * FROM customer_info c WHERE c.pan =?1 or c.cin = ?2",nativeQuery = true)
    List<CustomerInfoEntity> customerInfo(String pan,String cin);

    @Query(value="DELETE FROM customer_info where id=?1",nativeQuery = true)
    void deleteById(long id);

    @Query(value = "SELECT c.customer_name,app.* FROM customer_info AS c,application_details_info AS app WHERE c.status='Active' AND c.id=app.cust_id;",nativeQuery = true)
    List<Object[]> getAllActiveData();

    @Query(value = "SELECT customer_name FROM customer_info WHERE id=?1",nativeQuery = true)
    String anchorNameLimitEligibility(Long id);

    @Query(value = "SELECT c.* from customer_info as c, application_details_info as a where c.id = a.cust_id and a.type = 2 and c.status=1 and business_expiry <= curdate() + interval 30 Day group by c.id",nativeQuery = true)
    List<CustomerInfoEntity> viewExpiredCustomerDetails();

    @Query(value = "SELECT * FROM customer_info where dedupe_status = 'Active' and status = 1 and id in (select cust_id from application_details_info where type = :customerType);",nativeQuery = true)
    List<CustomerInfoEntity> viewActiveCustomers(int customerType);

    @Query(value = "SELECT * FROM customer_info where dedupe_status = 'Active';",nativeQuery = true)
    List<CustomerInfoEntity> getAllCustomers();
}
