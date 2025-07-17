package org.vcpl.triton.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vcpl.triton.workflow.entity.DeferralWorkflowEntity;
import org.vcpl.triton.workflow.entity.WFApprovalStatusEntity;

import java.util.Collection;
import java.util.List;

public interface DeferralWorkflowStatusRepository extends JpaRepository<DeferralWorkflowEntity , Long> {

    @Query(value = "SELECT * FROM deferral_workflow_stage_approval_status c WHERE c.app_id =?1 order by c.id",nativeQuery = true)
    List<DeferralWorkflowEntity> findAllByApplicationId(long cusId);

    @Query(value = "SELECT * FROM deferral_workflow_stage_approval_status c WHERE c.app_id =?1 order by c.seq_no DESC LIMIT 1",nativeQuery = true)
    DeferralWorkflowEntity findByApplicationId(long cudId);

    @Query(value = "SELECT wf.* FROM deferral_workflow_stage_approval_status AS wf WHERE wf.app_id IN" +
            "(SELECT app.id FROM application_details_info AS app WHERE app.cust_id IN" +
            "(SELECT cust.id FROM customer_info AS cust)) AND wf.app_id = :appId order by id desc LIMIT 1", nativeQuery = true)
    DeferralWorkflowEntity getWorkflowByAppId(Long appId);

    @Query(value = "SELECT wfstatus.* FROM deferral_workflow_stage_approval_status AS wfstatus inner join workflow_stage AS wfstage ON wfstage.id=wfstatus.stage_id  AND application_status=0 AND wfstatus.user_info=?1",nativeQuery = true)
    List<DeferralWorkflowEntity> getLastWFStatus(String email);

    @Query(value = "SELECT * FROM deferral_workflow_stage_approval_status AS wf WHERE wf.application_status=0 AND wf.current_stage_approver IN ?1 order by id DESC;",nativeQuery = true)
    List<DeferralWorkflowEntity> getLeadLastWFStatus(List<?> stageName);

    @Query(value = "SELECT * FROM deferral_workflow_stage_approval_status WHERE app_id = ?1 order by seq_no",nativeQuery = true )
    Collection<DeferralWorkflowEntity> getRemarks(long appId);

    @Query(value = "SELECT * FROM deferral_workflow_stage_approval_status where (stage_id=29 AND application_status=2 OR application_status=-2\n" +
            " OR application_status=0 OR stage_id=34 AND application_status=2) group by app_id",nativeQuery = true)
    Collection<DeferralWorkflowEntity> findAnchorAsDescOrder();

    @Query(value = "SELECT stage.* FROM deferral_workflow_stage_approval_status AS stage WHERE stage.app_id=?2 AND stage.stage_id=?1 AND stage.application_status = 2 ORDER BY stage.id DESC LIMIT 1;", nativeQuery = true)
    DeferralWorkflowEntity findByPreviousAprroverStage(Long stageId,Long appId);

    @Query(value = "SELECT * FROM deferral_workflow_stage_approval_status WHERE app_id=?1 GROUP BY user_info;",nativeQuery = true)
    List<DeferralWorkflowEntity> getInvolvingDeferralPersons(Long appId);

    @Query(value = "SELECT w.* FROM deferral_workflow_stage_approval_status as w,application_details_info as a where w.app_id = a.id AND a.cust_id = ?1 AND (w.application_status=0 OR w.application_status=-2)",nativeQuery = true )
    List<DeferralWorkflowEntity> getWFStatus(long id);
}
