package org.vcpl.triton.anchor.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BeneficiaryData {

    private Long id;

    @NotNull
    private Long appId;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "benType should contain only Alphabets ")
    @NotBlank(message = "benType shouldn't be null")
    private String benType;

    @NotNull
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "benName should contain only Alphabets ")
    @NotBlank(message = "benName shouldn't be null")
    private String benName;

//    @NotNull
//    @NotBlank(message = "bankCode shouldn't be null")
    private String bankCode;

    @NotNull
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "bankName should contain only Alphabets ")
    @NotBlank(message = "bankName shouldn't be null")
    private String bankName;

    @NotNull
//    @Pattern(regexp = "[0-9]{9,18}",message = "Enter Valid bankAcctNumber")
    @NotBlank(message = "bankAcctNumber shouldn't be null")
    private String bankAcctNumber;

    @NotNull
//    @Pattern(regexp="^[A-Z]{4}0[A-Z0-9]{6}$",message = "Enter Valid bankIfscCode")
    @NotBlank(message = "bankIfscCode shouldn't be null")
    private String bankIfscCode;

//    @NotNull
//    @NotBlank(message = "bankBranchCode shouldn't be null")
    private String bankBranchCode;

    @NotNull
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "bankBranch should contain only Alphabets ")
    @NotBlank(message = "bankBranchName shouldn't be null")
        private String bankBranchName;

    private String createdBy;

    private String createdAt;

    private String updatedBy;

    private String updatedAt;


}
