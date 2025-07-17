package org.vcpl.triton.anchor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="anchor_program")
public class ProgramNormsEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "funding_type", nullable = false)
    @Pattern(regexp="^[a-zA-Z0-9 ]*$",message = "fundingType should contain only Alphabets")
    private String fundingType;

    @Column(name = "overall_prog_limit", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=15, fraction=3)
    private BigDecimal overallProgLimit;

    @Column(name = "regular_prog_limit", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=15, fraction=3)
    private BigDecimal regularProgLimit;

    @Column(name = "adhoc_prog_limit", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=15, fraction=3)
    private BigDecimal adhocProgLimit;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "anchor_onboarding_date", nullable = true)
//    @Pattern(regexp = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$",message = "Please Enter valid date")
    private Date anchorOnboardingDate;

    @Column(name = "cp_min_limit", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    private Double cpMinLimit;

    @Column(name = "cp_max_limit", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    private Double cpMaxLimit;

    @Column(name = "pricing_roi_min", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    private Double pricingRoiMin;

    @Column(name = "pricing_roi_max", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    private Double pricingRoiMax;

    @Column(name = "interest_app_tye", nullable = false)
    @Pattern(regexp = "^[ A-Za-z0-9_@./#&+-]*$",message = "interestAppTye should contain only Alphabets")
    private String interestAppTye;

    @Column(name = "interest_ownership", nullable = false)
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "interestOwnerShip should contain only Alphabets")
    private String interestOwnerShip;

    @Column(name = "penal_interest_ownership", nullable = false)
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "penalinterestOwnerShip should contain only Alphabets")
    private String penalInterestOwnerShip;

    @Column(name = "interest_payment_frequency", nullable = false)
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "interestPaymentFrequency should contain only Alphabets")
    private String interestPaymentFrequency;

    @Column(name = "penal_interest", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    private Double penalInterest;

    @Column(name = "tenure", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=15, fraction=3)
    private BigDecimal tenure;

    @Column(name = "invoice_ageing", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=15, fraction=3)
    private BigDecimal invoiceAgeing;

    @Column(name = "door_to_door", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=15, fraction=3)
    private BigDecimal doorToDoor;

    @Column(name = "funding_percent", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    private Double fundingPercent;

    @Column(name = "security_covg_primary", nullable = true)
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "securityCovgPrimary should contain only Alphabets")
    private String securityCovgPrimary;

    @Column(name = "security_covg_secondry", nullable = true)
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "securityCovgPrimary should contain only Alphabets")
    private String securityCovgSecondry;

    @Column(name = "processing_fees_min", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    private Double processingFeesMin;

    @Column(name = "processing_fees_max", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    private Double processingFeesMax;

    @Column(name = "interest_calculation", nullable = false)
    @Pattern(regexp = "^[ A-Za-z0-9_@./#&+-]*$",message = "interestCalculation should contain only Alphabets")
    private String interestCalculation;

    @Column(name = "counter_party_grace_period", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    private Double counterPartyGracePeriod;

    @Column(name = "max_drawdown", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    private Double maxDrawdown;

    @Column(name = "stop_supply_trigger_days", nullable = false)
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    private Double stopSupplyTriggerDays;

    @Column(name = "repayment_type", nullable = false)
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "repaymentType should contain only Alphabets")
    private String repaymentType;

//    @Column(name = "repayment_nature", nullable = false)
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "repaymentNature should contain only Alphabets")
//    private String repaymentNature;

    @Column(name = "sub_Product", nullable = false)
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "subProduct should contain only Alphabets")
    private String subProduct;

    @Column(name = "transaction_Type", nullable = false)
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "transactionType should contain only Alphabets")
    private String transactionType;


    @Column(name = "due_Date_Calculation", nullable = false)
    private String dueDateCalculation = "CALC_DUE_DT_FROM_FROM_INV_DATE";

    @Column(name = "interim_Review_Frequency", nullable = false)
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "interimReviewFrequency should contain only Alphabets")
    private Double interimReviewFrequency;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "next_interim_review_on", nullable = false)
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "nextInterimReviewOn should contain only Alphabets")
    private Date nextInterimReviewOn;

    @Column(name = "cmpd_Int", nullable = false)
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "cmpdInt should contain only Alphabets")
    private String cmpdInt;

    @Column(name = "cmp_Ovd_Int", nullable = false)
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "cmpOvdInt should contain only Alphabets")
    private String cmpOvdInt;

    @Column(name = "program_type", nullable = false)
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "programType should contain only Alphabets")
    private String programType;

//    @Column(name = "rate_penal_interest", nullable = false)
////    @Pattern(regexp = "^[a-zA-Z ]*$",message = "programType should contain only Alphabets")
//    private int ratePenalInterest;

    @Column(name = "interest_rate", nullable = false)
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "programType should contain only Alphabets")
    private Double interestRate;

    @Column(name = "product_expiry", nullable = false)
    private Integer productExpiry;

    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name = "expiry_date", nullable = true)
    private Date expiryDate;

    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;

    public void setDate(Date onBoarding, Date nextInterim) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(onBoarding != null) {
            String strDate = dateFormat.format(onBoarding);
            String strDate1 = strDate.concat(" 12:00:00");
            Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate1);
            this.anchorOnboardingDate = date1;
        }
        else if(nextInterim != null)
        {
            String strDate = dateFormat.format(nextInterim);
            String strDate1 = strDate.concat(" 12:00:00");
            Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate1);
            this.nextInterimReviewOn = date1;
        }
    }
}
