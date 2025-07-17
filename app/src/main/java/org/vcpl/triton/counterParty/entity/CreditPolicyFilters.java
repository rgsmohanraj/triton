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
@Table(name ="credit_policy_filters")
public class CreditPolicyFilters {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;


    @Column(name = "formula", nullable = false)
    private String formula;

    @Column(name = "createdBy", nullable = true)
    private String createdBy;

    @Column(name = "updatedBy", nullable = true)
    private String updatedBy;

    @Column(name = "createdAt", nullable = true)
    private Timestamp createdAt;

    @Column(name = "updatedAt", nullable = true)
    private Timestamp updatedAt;

    @ManyToOne(targetEntity = CreditPolicyMasterEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "display_name_id")
    private CreditPolicyMasterEntity creditPolicyMasterEntity;

    @ManyToOne(targetEntity = CreditPolicyConfigEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "config_id")
    private CreditPolicyConfigEntity creditPolicyConfigEntity;
}
