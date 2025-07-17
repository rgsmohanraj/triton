package org.vcpl.triton.counterParty.data;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LimitEligibilityDetailsData {

    private Long id;

    @NotNull
    private Long custId;

    private Long appId;

    @NotNull
    @NotBlank(message = "product shouldn't be null in limitEligibility")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "product should contain only Alphabets in CpBasic")
    private String product;

    @NotNull
    @NotBlank(message = "currentLimit shouldn't be null in limitEligibility")
    @Pattern(regexp = "^[0-9]{1,10}(\\.[0-9]{0,2})?$", message = "Please Enter Valid currentLimit")
    @Min(value=0)
    private String currentLimit; //Regular Limit

    @NotNull
    @NotBlank(message = "proposedLimit shouldn't be null in limitEligibility")
    @Pattern(regexp = "^[0-9]{1,10}(\\.[0-9]{0,2})?$", message = "Please Enter Valid proposedLimit")
    @Min(value=0)
    private String proposedLimit;//Proposed Regular Limit

    @NotNull
    @NotBlank(message = "eligibleLimit shouldn't be null in limitEligibility")
    @Pattern(regexp = "^[0-9]{1,10}(\\.[0-9]{0,2})?$", message = "Please Enter Valid eligibleLimit")
    @Min(value=0)
    private String eligibleLimit;//Eligible Limit


    @NotNull
    @NotBlank(message = "adhocLimit shouldn't be null in limitEligibility")
    @Pattern(regexp = "^[0-9]{1,10}(\\.[0-9]{0,2})?$", message = "Please Enter Valid adhocLimit")
    @Min(value=0)
    private String adhocLimit; //Proposed Adhoc Limit


    @NotNull
    @NotBlank(message = "creditPeriod shouldn't be null in limitEligibility")
    @Pattern(regexp = "^[0-9]*$", message = "Please Enter Valid creditPeriod")
    @Min(value=0)
    @Size(min=0, max = 4)
    private String creditPeriod;

//    @NotNull
//    @NotBlank(message = "doortoDoor shouldn't be null in limitEligibility")
    @Pattern(regexp = "^[0-9]*$", message = "Please Enter Valid doortoDoor")
//    @Min(value=1)
    @Size(min=0, max = 4)
    private String doortoDoor;

//    @NotNull
//    @NotBlank(message = "invoiceAgeing shouldn't be null in limitEligibility")
    @Pattern(regexp = "^[0-9]*$", message = "Please Enter Valid invoiceAgeing")
//    @Min(value=1)
    private String invoiceAgeing;

//    @NotNull
//    @NotBlank(message = "margin shouldn't be null in limitEligibility")
//    @Pattern(regexp = "^[0-9]{1}[0-9]?(?:\\.\\d{1,2})?$|^0\\.\\d{1,2}?$|100", message = "Please Enter Valid margin")
//    @Min(value=0)
    private String margin;

    @NotNull
    @NotBlank(message = "expectedGrowth shouldn't be null in limitEligibility")
    @Pattern(regexp = "^-?\\d{1,3}(\\.\\d+)?$", message = "Please Enter Valid expectedGrowth")
//    @Min(value=0)
    private String expectedGrowth;

    @NotNull
    @NotBlank(message = "monthlyAverage shouldn't be null in limitEligibility")
    @Pattern(regexp = "^[0-9]{1,10}(\\.[0-9]{0,2})?$", message = "Please Enter Valid monthlyAverage")
    @Min(value=0)
    private String monthlyAverage;

//    @NotNull
//    @NotBlank(message = "calculatedLimitWoSetOff shouldn't be null in limitEligibility")
//    @Pattern(regexp = "^[0-9]*$", message = "Please Enter Valid calculatedLimitWoSetOff")
//    @Min(value=0)
    private String calculatedLimitWoSetOff;

    @NotNull
    @NotBlank(message = "approtionedLimits shouldn't be null in limitEligibility")
    @Pattern(regexp = "^[0-9]{1,10}(\\.[0-9]{0,2})?$", message = "Please Enter Valid approtionedLimits")
    @Min(value=0)
    private String approtionedLimits;

    @NotNull
    @NotBlank(message = "existingScfLimits shouldn't be null in limitEligibility")
    @Pattern(regexp = "^[0-9]{1,10}(\\.[0-9]{0,2})?$", message = "Please Enter Valid existingScfLimits")
    @Min(value=0)
    private String existingScfLimits;

    @NotNull
    @NotBlank(message = "modelLimit shouldn't be null in limitEligibility")
    @Pattern(regexp = "^[0-9]{1,10}(\\.[0-9]{0,2})?$", message = "Please Enter Valid modelLimit")
    @Min(value=0)
    private String modelLimit;

    @NotNull
    @NotBlank(message = "loginLimitAmount shouldn't be null in limitEligibility")
    @Pattern(regexp = "^[0-9]{1,10}(\\.[0-9]{0,2})?$", message = "Please Enter Valid customerRequestedAmount")
//    @Min(value=0)
    private String loginLimitAmount; //customerRequestedAmount
//    private String customerRequestedAmount; (As per initial brd it was customerRequestedAmount now we're changing into login limit amount as new brd)

    @NotNull
    @NotBlank(message = "anchorRecommendedAmount shouldn't be null in limitEligibility")
    @Pattern(regexp = "^[0-9]{1,10}(\\.[0-9]{0,2})?$", message = "Please Enter Valid anchorRecommendedAmount")
    @Min(value=0)
    private String anchorRecommendedAmount;

//    @NotNull
    @Digits(integer=12, fraction=2)
//    @Pattern(regexp = "^[0-9]{1,10}(\\.[0-9]{0,2})?$", message = "Please Enter Valid receivables")
    private BigDecimal receivables;

//    @NotNull
    @Digits(integer=12, fraction=2)
    private BigDecimal creditor;

//    @NotNull
    @Digits(integer=12, fraction=2)
    private BigDecimal inventory;

    @NotNull
    @Digits(integer=12, fraction=2)
    private BigDecimal expectedMonthlyTurnOverWithAnchor;

    @NotNull
    @Digits(integer=12, fraction=2)
    private BigDecimal modelAdhocLimit;

    private String anchorName;


    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCurrentLimit() {
        return currentLimit;
    }

    public void setCurrentLimit(String currentLimit) {
        this.currentLimit = currentLimit;
    }

    public String getEligibleLimit() {
        return eligibleLimit;
    }

    public void setEligibleLimit(String eligibleLimit) {
        this.eligibleLimit = eligibleLimit;
    }

    public String getProposedLimit() {
        return proposedLimit;
    }

    public void setProposedLimit(String proposedLimit) {
        this.proposedLimit = proposedLimit;
    }

    public String getAdhocLimit() {
        return adhocLimit;
    }

    public void setAdhocLimit(String adhocLimit) {
        this.adhocLimit = adhocLimit;
    }

    public String getCreditPeriod() {
        return creditPeriod;
    }

    public void setCreditPeriod(String creditPeriod) {
        this.creditPeriod = creditPeriod;
    }

    public String getDoortoDoor() {
        return doortoDoor;
    }

    public void setDoortoDoor(String doortoDoor) {
        this.doortoDoor = doortoDoor;
    }

    public String getInvoiceAgeing() {
        return invoiceAgeing;
    }

    public void setInvoiceAgeing(String invoiceAgeing) {
        this.invoiceAgeing = invoiceAgeing;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getExpectedGrowth() {
        return expectedGrowth;
    }

    public void setExpectedGrowth(String expectedGrowth) {
        this.expectedGrowth = expectedGrowth;
    }

    public String getMonthlyAverage() {
        return monthlyAverage;
    }

    public void setMonthlyAverage(String monthlyAverage) {
        this.monthlyAverage = monthlyAverage;
    }

    public String getCalculatedLimitWoSetOff() {
        return calculatedLimitWoSetOff;
    }

    public void setCalculatedLimitWoSetOff(String calculatedLimitWoSetOff) {
        this.calculatedLimitWoSetOff = calculatedLimitWoSetOff;
    }

    public String getApprotionedLimits() {
        return approtionedLimits;
    }

    public void setApprotionedLimits(String approtionedLimits) {
        this.approtionedLimits = approtionedLimits;
    }

    public String getExistingScfLimits() {
        return existingScfLimits;
    }

    public void setExistingScfLimits(String existingScfLimits) {
        this.existingScfLimits = existingScfLimits;
    }

    public String getModelLimit() {
        return modelLimit;
    }

    public void setModelLimit(String modelLimit) {
        this.modelLimit = modelLimit;
    }

    public String getLoginLimitAmount() {
        return loginLimitAmount;
    }

    public void setLoginLimitAmount(String loginLimitAmount) {
        this.loginLimitAmount = loginLimitAmount;
    }

    public String getAnchorRecommendedAmount() {
        return anchorRecommendedAmount;
    }

    public void setAnchorRecommendedAmount(String anchorRecommendedAmount) {
        this.anchorRecommendedAmount = anchorRecommendedAmount;
    }

}
