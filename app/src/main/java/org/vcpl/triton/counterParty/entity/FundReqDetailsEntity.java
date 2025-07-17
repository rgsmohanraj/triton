package org.vcpl.triton.counterParty.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vcpl.triton.anchor.entity.ApplicationEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="cp_fund_requirement_details")
public class FundReqDetailsEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "created_by", nullable = true)
    private String createdBy;

    @Column(name = "updated_by", nullable = true)
    private String updatedBy;

    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;


    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;

    @ManyToOne(targetEntity = FundReqMasterEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "FundReq_id")
    private FundReqMasterEntity fundRequirementMasterEntity;



}
