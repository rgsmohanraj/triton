package org.vcpl.triton.counterParty.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="cp_proposed_products")
public class ProposedProductsEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "product", nullable = false)
    private String product;

    @Column(name = "type", nullable = true)
    private String type;

    @Column(name = "proposed", nullable = false)
    private String proposed;

    @Column(name = "vintage_with_anchor", nullable = true)
    private Double vintageWithAnchor;

    @Column(name = "min_monthly_anchor", nullable = true)
    private Double minMonthlyAnchor;

    @Column(name = "anchor_relationship", nullable = true)
    private String anchorRelationship;

//    @Column(name = "proposal_type", nullable = false)
//    private String proposalType;

    @Column(name = "createdBy", nullable = true)
    private String createdBy;

    @Column(name = "updatedBy", nullable = true)
    private String updatedBy;


    @Column(name = "createdAt", nullable = true)
    private Timestamp createdAt;

    @Column(name = "updatedAt", nullable = true)
    private Timestamp updatedAt;

    @Column(name = "credit_policy", nullable = true)
    private Boolean creditPolicyCheck;

    @ManyToOne(targetEntity = CustomerInfoEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "cust_id")
    private CustomerInfoEntity customerInfoEntity;

    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;

}
