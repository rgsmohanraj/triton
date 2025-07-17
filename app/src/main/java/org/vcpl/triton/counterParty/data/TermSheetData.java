package org.vcpl.triton.counterParty.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermSheetData {

    private Long id;

    private Long appId;

    @NotNull
    private Long custId;

    @NotNull
    @NotBlank(message = "product shouldn't be null in Termsheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "product should contain only Alphabets in CpBasic")
    private String product;

    @NotNull
    @NotBlank(message = "regularLimit shouldn't be null in Termsheet")
    @Pattern(regexp = "^[0-9]{1,10}(\\.[0-9]{0,2})?$", message = "Please Enter Valid regularLimit")
    @Min(value=0)
    private String regularLimit;

    @NotNull
    @NotBlank(message = "adhocLimit shouldn't be null in Termsheet")
    @Pattern(regexp = "^[0-9]{1,10}(\\.[0-9]{0,2})?$", message = "Please Enter Valid adhocLimit")
    @Min(value=0)
    private String adhocLimit;

    @NotNull
    @NotBlank(message = "creditPeriod shouldn't be null in Termsheet")
    @Pattern(regexp = "^[0-9]*$", message = "Please Enter Valid creditPeriod")
    @Min(value=0)
//    @Size(min=0, max = 4)
    private String creditPeriod;

//    @NotNull
//    @NotBlank(message = "doorToDoor shouldn't be null in Termsheet")
    @Pattern(regexp = "^[0-9]*$", message = "Please Enter Valid doorToDoor")
//    @Min(value=1)
    @Size(min=0, max = 4)
    private String doorToDoor;

//    @NotNull
//    @NotBlank(message = "invoiceAgeing shouldn't be null in Termsheet")
    @Pattern(regexp = "^[0-9]*$", message = "Please Enter Valid invoiceAgeing")
//    @Min(value=1)
    private String invoiceAgeing;

    @NotNull
    @NotBlank(message = "margin shouldn't be null in Termsheet")
    @Pattern(regexp = "^[0-9]{1}[0-9]?(?:\\.\\d{1,2})?$|^0\\.\\d{1,2}?$|100", message = "Please Enter Valid margin")
    @Min(value=0)
    private String margin;

    @NotNull
    @NotBlank(message = "interestRate shouldn't be null in Termsheet")
    @Pattern(regexp = "^[0-9]{0,2}(\\.[0-9]{0,2})?$", message = "Please Enter Valid interestRate")
    @Min(value=1)
   // @Size(min = 1, max = 5)
    private String interestRate;

    @NotNull
    @NotBlank(message = "pf shouldn't be null in Termsheet")
    @Pattern(regexp = "^[0-9]{0,2}(\\.[0-9]{0,2})?$", message = "Please Enter Valid pf")
//    @Min(value=1)
    private String pf;

    @NotNull
    @NotBlank(message = "renewal shouldn't be null in Termsheet")
    @Pattern(regexp = "^[0-9]{0,2}(\\.[0-9]{0,2})?$",message = "renewal should contain only Alphabets in CpBasic")
//    @Min(value=1)
    private String renewal;

    @NotNull
    @NotBlank(message = "interestRateType shouldn't be null in Termsheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "interestRateType should contain only Alphabets in CpBasic")
//    @Min(value=1)
    private String interestRateType;

    @NotNull
    @NotBlank(message = "renewalPeriod shouldn't be null in Termsheet")
    @Pattern(regexp = "^[0-9]*$", message = "Please Enter Valid renewalPeriod")
    @Min(value=1)
    private String renewalPeriod;

//    @NotNull
//    @NotBlank(message = "renewalPeriod shouldn't be null in Termsheet")
//    @Pattern(regexp = "^[0-9]*$", message = "Please Enter Valid renewalPeriod")
//    @Min(value=1)
    private String applyOfInterest;

//    @NotNull
//    @NotBlank(message = "renewalPeriod shouldn't be null in Termsheet")
//    @Pattern(regexp = "^[0-9]*$", message = "Please Enter Valid renewalPeriod")
//    @Min(value=1)
    private String interestBorneBy;

//    @NotNull
//    @NotBlank(message = "renewalPeriod shouldn't be null in Termsheet")
//    @Pattern(regexp = "^[0-9]*$", message = "Please Enter Valid renewalPeriod")
//    @Min(value=1)
    private String penaltyBorneBy;



//    @NotNull
//    @NotBlank(message = "renewalPeriod shouldn't be null in Termsheet")
//    @Pattern(regexp = "^[0-9]*$", message = "Please Enter Valid renewalPeriod")
//    @Min(value=1)
//    private String repymntFrom;

    @NotNull
    @NotBlank(message = "InvoiceFunding shouldn't be null in Termsheet")
    @Pattern(regexp = "^?\\d{1,3}(\\.\\d+)?$", message = "Please Enter Valid InvoiceFunding")
//    @Min(value=1)
    private String invoiceFunding;

    @NotNull
    @NotBlank(message = "GraceDays shouldn't be null in Termsheet")
//    @Pattern(regexp = "^[0-9]*$", message = "Please Enter Valid renewalPeriod")
//    @Min(value=1)
    private String graceDays;

    private String anchorName;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date expiryDate;

}
