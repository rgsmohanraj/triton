package org.vcpl.triton.workflow.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vcpl.triton.rbac.entity.GroupEntity;
import org.vcpl.triton.workflow.entity.WorkFlowStageEntity;

import javax.persistence.EntityManager;
import java.util.List;

public interface WorkflowStageRepository extends JpaRepository<WorkFlowStageEntity, Long> {

    @Query(value = "select stage.* from workflow_stage as stage where stage.stage in (select tempStage.stage from workflow_stage as tempStage where tempStage.id=?1)",nativeQuery = true)
    List<WorkFlowStageEntity> findByStageValue(Long StageId);

//    @Query(value = "select * from workflow_stage WHERE stage = :stage)",nativeQuery = true)
//    Long findByStageId(String stage);

    @Query(value = "SELECT stage.* FROM workflow_stage AS stage WHERE stage.stage = ?1 ORDER BY stage.id DESC LIMIT 1", nativeQuery = true)
    WorkFlowStageEntity findByStageId(String stage);



}
