package org.vcpl.triton.workflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vcpl.triton.rbac.entity.GroupEntity;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="workflow_stage")
public class WorkFlowStageEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "stage", nullable = false, unique = true)
    private String stageId;

    @Column(name = "client_type", nullable = false)
    private long clientType;

    @ManyToOne(targetEntity = GroupEntity.class,fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "group_id")
    private GroupEntity groupId;

    @Column(name="approver_permission")
    private boolean approverPermission;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public long getClientType() {
        return clientType;
    }

    public void setClientType(long clientType) {
        this.clientType = clientType;
    }

    public GroupEntity getGroupId() {
        return groupId;
    }

    public void setGroupId(GroupEntity groupId) {
        this.groupId = groupId;
    }

    public boolean isApproverPermission() {
        return approverPermission;
    }

    public void setApproverPermission(boolean approverPermission) {
        this.approverPermission = approverPermission;
    }
}
