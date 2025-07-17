package org.vcpl.triton.rbac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vcpl.triton.rbac.entity.GroupEntity;

import java.util.List;

public interface GroupRepositiory extends JpaRepository<GroupEntity,Long> {

    @Query(value = "SELECT * FROM rbac_group WHERE id IN (SELECT group_id FROM workflow_stage WHERE stage IN (SELECT stage.stage FROM workflow_stage AS stage WHERE group_id IN (SELECT id FROM rbac_group WHERE group_name=?1) AND approver_permission = 1))",nativeQuery = true)
    List<GroupEntity> findNextStageApprovers(String NextStageApprovers);

}
