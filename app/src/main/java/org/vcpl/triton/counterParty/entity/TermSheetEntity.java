package org.vcpl.triton.counterParty.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="cp_term_sheet")
public class TermSheetEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "product", nullable = false)
    private String product;

    @Column(name = "regular_limit", nullable = false)
    private String regularLimit;

    @Column(name = "adhocLimit", nullable = false)
    private String adhocLimit;

    @Column(name = "creditPeriod", nullable = false)
    private String creditPeriod;

    @Column(name = "doorToDoor", nullable = true)
    private String doorToDoor;

    @Column(name = "invoiceAgeing", nullable = true)
    private String invoiceAgeing;

    @Column(name = "margin", nullable = false)
    private String margin;

    @Column(name = "interestRate", nullable = false)
    private String interestRate;

    @Column(name = "pf", nullable = false)
    private String pf;

    @Column(name = "renewal", nullable = false)
    private String renewal;

    @Column(name = "interestRateType", nullable = false)
    private String interestRateType;

    @Column(name = "renewalPeriod", nullable = false)
    private String renewalPeriod;

    @Column(name = "applyOfInterest", nullable = false)
    private String applyOfInterest;

    @Column(name = "interestBorneBy", nullable = false)
    private String interestBorneBy;

    @Column(name = "penaltyBorneBy", nullable = false)
    private String penaltyBorneBy;

//    @Column(name = "repymntFrom", nullable = true)
//    private String repymntFrom;

    @Column(name = "invoiceFunding", nullable = false)
    private String invoiceFunding;

    @Column(name = "graceDays", nullable = false)
    private String graceDays;

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

    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name = "expiry_date", nullable = true)
    private Date expiryDate;

}
