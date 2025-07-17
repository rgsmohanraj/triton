package org.vcpl.triton.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vcpl.triton.workflow.entity.WFApprovalStatusEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface WFApprovalStatusRepository extends JpaRepository<WFApprovalStatusEntity, Long> {

    @Query(value = "SELECT * FROM workflow_stage_approval_status c WHERE c.app_id =?1 order by c.seq_no",nativeQuery = true)
    List<WFApprovalStatusEntity> findAllByApplicationId(long cusId);

    @Query(value = "SELECT wfstatus.* FROM workflow_stage_approval_status AS wfstatus inner join workflow_stage AS wfstage ON wfstage.id=wfstatus.stage_id  AND application_status=0 AND wfstatus.user_info=?1",nativeQuery = true)
    List<WFApprovalStatusEntity> getLastWFStatus(String email);

    //    @Query(value = "SELECT wfstatus.* FROM workflow_stage_approval_status AS wfstatus inner join workflow_stage AS wfstage ON wfstage.id=wfstatus.stage_id AND (wfstatus.user_info=?1 OR wfstatus.current_stage_approver=?2) AND application_status=0 GROUP BY wfstatus.id",nativeQuery = true)
    @Query(value = "SELECT * FROM workflow_stage_approval_status AS wf WHERE wf.application_status=0 AND wf.current_stage_approver IN ?1 order by id DESC;",nativeQuery = true)
    List<WFApprovalStatusEntity> getLeadLastWFStatus(List<?> stageName);

    @Query(value = "SELECT * FROM workflow_stage_approval_status c WHERE c.app_id =?1 order by c.seq_no DESC LIMIT 1",nativeQuery = true)
    WFApprovalStatusEntity findByApplicationId(long cudId);

    @Query(value = "SELECT * FROM workflow_stage_approval_status\n" +
            "WHERE (stage_id = 26 AND application_status = 2 AND app_id NOT IN (\n" +
            "        SELECT app_id FROM workflow_stage_approval_status WHERE stage_id = 27))\n" +
            "   OR (stage_id = 26 AND application_status = 2 AND app_id IN (\n" +
            "        SELECT app_id FROM workflow_stage_approval_status WHERE stage_id = 27 AND application_status = 2\n" +
            "    )) GROUP BY app_id",nativeQuery = true)
    Collection<WFApprovalStatusEntity> findAnchor();

    @Query(value = "SELECT * FROM workflow_stage_approval_status\n" +
            "WHERE (stage_id = 21 AND application_status = 2 AND app_id NOT IN (\n" +
            "        SELECT app_id FROM workflow_stage_approval_status WHERE stage_id = 28))\n" +
            "   OR (stage_id = 21 AND application_status = 2 AND app_id IN (\n" +
            "        SELECT app_id FROM workflow_stage_approval_status WHERE stage_id = 28 AND application_status = 2\n" +
            "    )) GROUP BY app_id",nativeQuery = true)
    Collection<WFApprovalStatusEntity> findCounterParty();

    @Query(value = "SELECT wf.*,app.id AS applicationId FROM workflow_stage_approval_status AS wf,application_details_info AS app,workflow_stage AS wfstage" +
            "                       WHERE app.id = wf.app_id AND wf.application_status = :status group by app.id ORDER BY app.id DESC",nativeQuery = true)
    Collection<WFApprovalStatusEntity>findAnchorStatus(String status);

    @Query(value = "SELECT wf.* FROM workflow_stage_approval_status AS wf WHERE wf.app_id IN" +
            "(SELECT app.id FROM application_details_info AS app WHERE app.cust_id IN" +
            "(SELECT cust.id FROM customer_info AS cust)) AND wf.app_id = :appId order by id desc LIMIT 1", nativeQuery = true)
    WFApprovalStatusEntity getWorkflowByAppId(Long appId);

    @Query(value = "SELECT stage.* FROM workflow_stage_approval_status AS stage WHERE stage.app_id=?1 and stage.application_status = 2 and stage.current_stage_approver=?2 ORDER BY stage.id DESC LIMIT 1;", nativeQuery = true)
    Optional<WFApprovalStatusEntity> findByPreviousAprrover(Long appId, String curentStageApprover);

    @Query(value = "SELECT w.*, a.cust_id FROM workflow_stage_approval_status w JOIN application_details_info a ON w.app_id = a.id WHERE w.seq_no = (" +
            "SELECT MAX(seq_no) FROM workflow_stage_approval_status WHERE app_id = w.app_id AND (" +
            "(stage_id <> 18 AND application_status IN (2, -2, 0)) OR (stage_id = 18 AND application_status IN (-2, 0) AND approved_status = 0))) " +
            "AND a.seq_no = (SELECT MAX(seq_no) FROM application_details_info a1 WHERE a.cust_id = a1.cust_id) group by w.app_id;", nativeQuery = true)
    Collection<WFApprovalStatusEntity> findAnchorAsDescOrder();

    @Query(value = "SELECT stage.* FROM workflow_stage_approval_status AS stage WHERE stage.app_id=?1 and stage.application_status = 2 ORDER BY stage.id DESC LIMIT 1;", nativeQuery = true)
    Optional<WFApprovalStatusEntity> findByPreviousAprrover(Long appId);


    @Query(value = "SELECT stage.* FROM workflow_stage_approval_status AS stage WHERE stage.app_id=?2 AND stage.stage_id=?1 AND stage.application_status = 2 ORDER BY stage.id DESC LIMIT 2;", nativeQuery = true)
    List<WFApprovalStatusEntity> findByPreviousAprroverStage(Long stageId,Long appId);

    @Query(value = "SELECT * FROM workflow_stage_approval_status where app_id=?2 AND stage_id=?1 order by id desc limit 2;", nativeQuery = true)
    List<WFApprovalStatusEntity> findByPreviousAprroverStageByMultipleVerificationData(Long stageId,Long appId);


    @Query(value = "SELECT * FROM workflow_stage_approval_status c WHERE c.app_id =?1 AND c.approved_status=0 AND current_stage_approver=?2 ORDER BY c.id  DESC LIMIT 1",nativeQuery = true)
    Optional<WFApprovalStatusEntity> findApprovedPendingValue(long cusId,String currentApprovedTeam);

    @Query(value = "SELECT * FROM workflow_stage_approval_status c WHERE c.app_id =?1 AND c.approved_status=0 ORDER BY c.id",nativeQuery = true)
    List<WFApprovalStatusEntity> findApprovedPendingValue(long cusId);

    @Query(value = "SELECT * FROM workflow_stage_approval_status where app_id=?1 and current_stage_approver=?2 AND approved_status=0;",nativeQuery = true)
    Optional<WFApprovalStatusEntity>findReturnPreviousAprrover(Long appId, String currentStageApprover);

    @Query(value = "SELECT customer_name FROM customer_info where id In (SELECT cust_id from application_details_info where id = ?1)",nativeQuery = true)
    List<Object[]> findAnchorName(long id);

    @Query(value = "SELECT * FROM workflow_stage_approval_status WHERE app_id = ?1 order by seq_no",nativeQuery = true )
    Collection<WFApprovalStatusEntity> getRemarks(long appId);

    @Query(value = "SELECT w.* FROM workflow_stage_approval_status as w,application_details_info as a where w.app_id = a.id AND a.cust_id = ?1 AND (w.application_status=0 OR w.application_status=-2)",nativeQuery = true )
    List<WFApprovalStatusEntity> getWFStatus(long id);
}
