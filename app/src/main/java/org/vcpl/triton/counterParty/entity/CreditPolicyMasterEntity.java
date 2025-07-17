package org.vcpl.triton.counterParty.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vcpl.triton.anchor.entity.ProgramNormsEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="credit_policy_master")
public class CreditPolicyMasterEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name ="policy_name",nullable = false)
    private String policyName;

    @Column(name ="policy_type",nullable = false)
    private String policyType;

    @Column(name ="display_name",nullable = false)
    private String displayName;

    @Column(name ="mandatory",nullable = false)
    private String mandatory;

    @Column(name ="status",nullable = false)
    private String status;

    @Column(name ="data_type",nullable = false)
    private String dataType;

    @Column(name ="regex",nullable = false)
    private String regex;

    @Column(name = "filters")
    public String filters;

    @Column(name ="created_by",nullable = true)
    private String createdBy;

    @Column(name ="created_at",nullable = true)
    private Timestamp createdAt;

    @Column(name ="updated_by",nullable = true)
    private String updatedBy;

    @Column(name ="updated_at",nullable = true)
    private Timestamp updatedAt;

    @OneToMany(targetEntity = CreditPolicyFilters.class,fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<CreditPolicyFilters> creditPolicyFilters;
}
