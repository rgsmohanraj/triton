package org.vcpl.triton.counterParty.data;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommercialData {

    private Long id;

    @NotNull
    private Long appId;

    private String productRemarks;

    private String anchorNameRemarks;

    private String regularLimitRemarks;

    private String adhocLimitRemarks;

    private String creditPeriodRemarks;

    private String doorRemarks;

    private String invoiceAgeingRemarks;

    private String marginRemarks;

    private String interestRateRemarks;

    private String pfRemarks;

    private String renewalRemarks;

    private String interestTypeRemarks;

    private String renewalPeriodRemarks;



//    @NotNull
//    private String conditionProduct;
//    @NotNull
//    private String conditionAnchorName;
//    @NotNull
//    private String conditionRegularLimit;
//    @NotNull
//    private String conditionAdhocLimit;
//    @NotNull
//    private String conditionCreditPeriod;
//    @NotNull
//    private String conditionDoor;
//    @NotNull
//    private String conditionInvoiceAgeing;
//    @NotNull
//    private String conditionMargin;
//    @NotNull
//    private String conditionInterestRate;
//    @NotNull
//    private String conditionPf;
//    @NotNull
//    private String conditionRenewal;
//    @NotNull
//    private String conditionInterestType;
//    @NotNull
//    private String conditionRenewalPeriod;



}
