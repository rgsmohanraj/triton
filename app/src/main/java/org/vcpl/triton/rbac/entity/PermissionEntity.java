package org.vcpl.triton.rbac.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vcpl.triton.workflow.entity.WorkFlowStageEntity;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="rbac_permission")
public class PermissionEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "view", nullable = false)
    private boolean isEdit;

    @Column(name = "is_submit", nullable = false)
    private boolean isSubmit;

    @Column(name = "approve", nullable = false)
    private boolean isApprove;

    @Column(name = "reject", nullable = false)
    private  boolean isReject;

    @Column(name = "is_return", nullable = false)
    private boolean isReturn;

    @ManyToOne(targetEntity = WorkFlowStageEntity.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "stage_id")
    private WorkFlowStageEntity stageId;

}
