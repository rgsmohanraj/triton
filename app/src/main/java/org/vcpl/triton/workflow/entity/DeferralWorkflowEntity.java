package org.vcpl.triton.workflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vcpl.triton.anchor.entity.ApplicationEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="deferral_workflow_stage_approval_status")
public class DeferralWorkflowEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name="app_id", nullable = false)
    private ApplicationEntity appId;

    @ManyToOne(targetEntity = WorkFlowStageEntity.class, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name="stage_id", nullable = false)
    private WorkFlowStageEntity stage;

    @Column(name= "application_status", nullable = false)
    private long status;

    @Column(name = "user_info", nullable = false)
//    @Email(message = "Enter valid user email")
//    @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$",message = "Please Enter valid Email-id in Authorized Sheet")
    private String approverInfo;

    @Column(name = "timestamp", nullable = false)
    private String date;

    @Column(name = "remarks")
    private  String remarks;

    @Column(name = "seq_no", nullable = false)
    private int seqNumber;

    @Column(name = "current_stage_approver")
    private String currentStageTeam;

    @Column(name = "approved_status")
    private Boolean approvedStatus;

    private String createdTime;

    private String updatedTime;

    @PrePersist
    public void prePersist() {
        createdTime = LocalDateTime.now().toString();
        updatedTime = LocalDateTime.now().toString();
    }

    @PreUpdate
    public void preUpdate() {
        updatedTime = LocalDateTime.now().toString();
    }

}
