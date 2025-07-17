package org.vcpl.triton.anchor.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.vcpl.triton.anchor.entity.ApplicationEntity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProgramNormsData {

    private Long id;

    @NotNull
    private Long appId;

    @NotNull
    @NotBlank(message = "Please Enter FundingType ")
//    @Pattern(regexp = "^[a-zA-Z0-9_.-]*$",message = "fundingType should contain only Alphabets")
    private String fundingType;

    @NotNull
    //@NotBlank(message = "Please Enter OverallProgLimit ")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=15, fraction=3)
//    @Min(value=1)
    private BigDecimal overallProgLimit;

    @NotNull
    //@NotBlank(message = "Please Enter RegularProgLimit ")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=15, fraction=3)
    @Min(value=0)
    private BigDecimal regularProgLimit;

    @NotNull
//    @NotBlank(message = "Please Enter AdhocProgLimit ")
   // @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=15, fraction=3)
    //@Min(value=1)
    private BigDecimal adhocProgLimit;

    @NotNull
//    @NotBlank(message = "Please Enter CpMinLimit ")
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=15, fraction=3)
    @Min(value=0)
    private Double cpMinLimit;

    @NotNull
//    @NotBlank(message = "Please Enter CpMaxLimit ")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    @Min(value=0)
    private Double cpMaxLimit;

    @NotNull
//    @NotBlank(message = "Please Enter PricingRoiMax ")
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
//    @Min(value=1)
    private Double pricingRoiMax;

    @NotNull
    //@NotBlank(message = "Please Enter PricingRoiMin")
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
//    @Min(value=0)
    private Double pricingRoiMin;

    @NotNull
    @NotBlank(message = "Please Enter InterestAppTye")
    @Pattern(regexp = "^[ A-Za-z0-9_@./#&+-]*$",message = "interestAppTye should contain only Alphabets")
    private String interestAppTye;



    @NotNull
    @NotBlank(message = "Please Enter InterestOwnerShip")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "interestOwnerShip should contain only Alphabets")
    private String interestOwnerShip;

    @NotNull
    @NotBlank(message = "Please Enter penalInterestOwnerShip")
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "PenalinterestOwnerShip should contain only Alphabets")
    private String penalInterestOwnerShip;

    @NotNull
 //   @NotBlank(message = "Please Enter AnchorOnboardingDate")
    @JsonFormat(pattern="yyyy-MM-dd")
//    @Pattern(regexp = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")
    private Date anchorOnboardingDate;

    @NotNull
    @NotBlank(message = "Please Enter InterestPaymentFrequency")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "interestPaymentFrequency should contain only Alphabets")
    private String interestPaymentFrequency;

    @NotNull
    // @NotBlank(message = "Please Enter PenalInterest")
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    @Min(value=0)
    private Double penalInterest;

    @NotNull
    // @NotBlank(message = "Please Enter Tenure")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=15, fraction=3)
    @Min(value=1)
    private BigDecimal tenure;

    @NotNull
    // @NotBlank(message = "Please Enter Tenure")
  //  @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=15, fraction=3)
  //  @Min(value=1)
    private BigDecimal invoiceAgeing;

    @NotNull
//    @NotBlank(message = "Please Enter DoorToDoor")
  //  @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=15, fraction=3)
    //@Min(value=1)
    private BigDecimal doorToDoor;

    @NotNull
//    @NotBlank(message = "Please Enter FundingPercent")
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    @Min(value=0)
    private Double fundingPercent;

    @NotBlank(message = "Please Enter SecurityCovgPrimary")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "securityCovgPrimary should contain only Alphabets")
    private String securityCovgPrimary;

    @NotBlank(message = "Please Enter securityCovgSecondry")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "securityCovgPrimary should contain only Alphabets")
    private String securityCovgSecondry;

    @NotNull
    // @NotBlank(message = "Please Enter ProcessingFeesMin ")
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    private Double processingFeesMin;

    @NotNull
    // @NotBlank(message = "Please Enter ProcessingFeesMax ")
//    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    private Double processingFeesMax;

    @NotBlank(message = "Please Enter InterestCalculation ")
    @NotNull
    @Pattern(regexp = "^[ A-Za-z0-9_@./#&+-]*$",message = "interestCalculation should contain only Alphabets")
    private String interestCalculation;

    @NotNull
    // @NotBlank(message = "Please Enter CounterPartyGracePeriod ")
    //@DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    //@Min(value=1)
    private Double counterPartyGracePeriod;

    @NotNull
    // @NotBlank(message = "Please Enter MaxDrawdown")
   // @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
   // @Min(value=1)
    private Double maxDrawdown;

    @NotNull
  //  @DecimalMin(value = "0.0", inclusive = false)
//    @NotBlank(message = "Please Enter StopSupplyTriggerDays")
    @Digits(integer=10, fraction=3)
   // @Min(value=1)
    private Double stopSupplyTriggerDays;

    @NotNull
    @NotBlank(message = "Please Enter RepaymentType")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "promGuarPanOvd should contain only Alphabets")
    private String repaymentType;

//    @NotNull
//    @NotBlank(message = "Please Enter RepaymentNature")
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "promGuarPanOvd should contain only Alphabets")
//    private String repaymentNature;

    @Pattern(regexp = "^[a-zA-Z ]*$",message = "promGuarPanOvd should contain only Alphabets")
    @NotNull
    @NotBlank(message = "Please Enter SubProduct")
    private String subProduct;


    @Pattern(regexp = "^[a-zA-Z ]*$",message = "programType should contain only Alphabets")
    @NotNull
    @NotBlank(message = "Please Enter programType")
    private String programType;


    @NotNull
    @NotBlank(message = "Please Enter TransactionType")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "transactionType should contain only Alphabets")
    private String transactionType;
    @NotNull
    @NotBlank(message = "Please Enter DueDateCalculation")
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "dueDateCalculation should contain only Alphabets")
    private String dueDateCalculation;
    @NotNull
 //   @NotBlank(message = "Please Enter InterimReviewFrequency")
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "interimReviewFrequency should contain only Alphabets")
    private Double interimReviewFrequency;
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
 //   @NotBlank(message = "Please Enter NextInterimReviewOn")
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "nextInterimReviewOn should contain only Alphabets")
    private Date nextInterimReviewOn;
    @NotNull
    @NotBlank(message = "Please Enter CmpdInt")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "cmpdInt should contain only Alphabets")
    private String cmpdInt;
    @NotNull
    @NotBlank(message = "Please Enter CmpOvdInt")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "CmpOvdInt should contain only Alphabets")
    private String cmpOvdInt;

    @NotNull
//    @NotBlank(message = "Please Enter interestRate")
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "programType should contain only Alphabets")
    private Double interestRate;

    @NotNull
    @Min(value=1)
    private Integer productExpiry;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date expiryDate;

//    @NotNull
////    @NotBlank(message = "Please Enter ratePenalInterest")
////    @Pattern(regexp = "^[a-zA-Z ]*$",message = "programType should contain only Alphabets")
//    private int ratePenalInterest;

    private String createdBy;

    private String updatedBy;
}
