package org.vcpl.triton.counterParty.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="cp_limit_eligibility_details")
public class LimitEligibilityDetailsEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "product", nullable = false)
    private String product;

    @Column(name = "current_limit", nullable = false)
    private String currentLimit;

    @Column(name = "proposed_limit", nullable = false)
    private String proposedLimit;

    @Column(name = "eligible_limit", nullable = false)
    private String eligibleLimit;

    @Column(name = "adhoc_limit", nullable = false)
    private String adhocLimit;

    @Column(name = "credit_period", nullable = false)
    private String creditPeriod;

    @Column(name = "door_to_Door", nullable = true)
    private String doorToDoor;

    @Column(name = "invoice_Ageing", nullable = true)
    private String invoiceAgeing;

    @Column(name = "margin", nullable = true)
    private String margin;

    @Column(name = "expected_growth", nullable = false)
    private String expectedGrowth;

    @Column(name = "monthly_average", nullable = false)
    private String monthlyAverage;

    @Column(name = "calculated_limit_wo_set_off", nullable = true)
    private String calculatedLimitWoSetOff;

    @Column(name = "approtioned_limits", nullable = false)
    private String approtionedLimits;

    @Column(name = "existing_scf_limits", nullable = false)
    private String existingScfLimits;

    @Column(name = "model_limit", nullable = false)
    private String modelLimit;

    @Column(name = "customer_requested_amount", nullable = false)
    private String customerRequestedAmount;

    @Column(name = "anchor_recommended_amount", nullable = false)
    private String anchorRecommendedAmount;

    @Column(name = "receivables", nullable = true)
    private BigDecimal receivables;

    @Column(name = "creditor", nullable = true)
    private BigDecimal creditor;

    @Column(name = "inventory", nullable = true)
    private BigDecimal inventory;

    @Column(name = "expected_monthly_turnover_with_anchor", nullable = false)
    private BigDecimal expectedMonthlyTurnOverWithAnchor;

    @Column(name = "model_adhoc_limit", nullable = false)
    private BigDecimal modelAdhocLimit;

    @Column(name = "createdBy", nullable = true)
    private String createdBy;

    @Column(name = "updatedBy", nullable = true)
    private String updatedBy;
    
    @Column(name = "createdAt", nullable = true)
    private Timestamp createdAt;

    @Column(name = "updatedAt", nullable = true)
    private Timestamp updatedAt;

    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;

    @ManyToOne(targetEntity = CustomerInfoEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "cust_id")
    private CustomerInfoEntity customerInfoEntity;

}
