package org.vcpl.triton.counterParty.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="cp_fund_requirement_master")
public class FundReqMasterEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "questions", nullable = false)
    private String questions;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "data_type", nullable = false)
    private String dataType;

    @Column(name="requirement_name",nullable = false)
    private String requirementName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name="status",nullable = false)
    private String status;

    @Column(name = "created_by", nullable = true)
    private String createdBy;

    @Column(name = "updated_by", nullable = true)
    private String updatedBy;

    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;

    @Column(name = "sequence_no", nullable = true)
    private String sequenceNo;


}
