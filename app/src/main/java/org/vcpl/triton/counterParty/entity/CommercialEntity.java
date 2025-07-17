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
@Table(name ="cp_commercial_table")
public class CommercialEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "product_remarks", nullable = true)
    private String productRemarks;

    @Column(name="anchor_remarks",nullable = true)
    private String anchorNameRemarks;

    @Column(name="regular_limits_remarks",nullable = true)
    private String regularLimitRemarks;

    @Column(name="adhoc_limit_remarks",nullable = true)
    private String adhocLimitRemarks;

    @Column(name="credit_period_remarks",nullable = true)
    private String creditPeriodRemarks;

    @Column(name="door_remarks",nullable = true)
    private String doorRemarks;

    @Column(name="invoice_ageing_remarks",nullable = true)
    private String invoiceAgeingRemarks;

    @Column(name="margin_remarks",nullable = true)
    private String marginRemarks;

    @Column(name="interest_rate_remarks",nullable = true)
    private String interestRateRemarks;

    @Column(name="pf_remarks",nullable = true)
    private String pfRemarks;

    @Column(name="renewal_remarks",nullable = true)
    private String renewalRemarks;

    @Column(name="interest_type_remarks",nullable = true)
    private String interestTypeRemarks;

    @Column(name="renewal_period_remarks",nullable = true)
    private String renewalPeriodRemarks;



//    @Column(name="condition_product",nullable = false)
//    private String conditionProduct;
//
//    @Column(name="condition_anchor_name",nullable = false)
//    private String conditionAnchorName;
//
//    @Column(name="condition_regular_limit",nullable = false)
//    private String conditionRegularLimit;
//
//    @Column(name="condition_adhoc_limit",nullable = false)
//    private String conditionAdhocLimit;
//
//    @Column(name="condition_credit_period",nullable = false)
//    private String conditionCreditPeriod;
//
//    @Column(name="condition_door",nullable = false)
//    private String conditionDoor;
//
//    @Column(name="condition_invoice_ageing",nullable = false)
//    private String conditionInvoiceAgeing;
//
//    @Column(name="condition_margin",nullable = false)
//    private String conditionMargin;
//
//    @Column(name="condition_interest_rate",nullable = false)
//    private String conditionInterestRate;
//
//    @Column(name="condition_pf",nullable = false)
//    private String conditionPf;
//
//    @Column(name="condition_renewal",nullable = false)
//    private String conditionRenewal;
//
//    @Column(name="condition_interest_type",nullable = false)
//    private String conditionInterestType;
//
//    @Column(name="condition_renewal_period",nullable = false)
//    private String conditionRenewalPeriod;
//
    @Column(name="created_by",nullable = true)
    private String createdBy;

    @Column(name="updated_by",nullable = true)
    private String updatedBy;

    @Column(name="updated_at",nullable = true)
    private Timestamp updatedAt;

    @Column(name="created_at",nullable = true)
    private Timestamp createdAt;


    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;

}