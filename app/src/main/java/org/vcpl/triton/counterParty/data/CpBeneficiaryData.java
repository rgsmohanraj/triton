package org.vcpl.triton.counterParty.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CpBeneficiaryData {

    private Long id;

    @NotNull
    private Long appId;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "CpbenType should contain only Alphabets ")
    @NotBlank(message = "benType shouldn't be null")
    private String cpBeneficiaryType;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "CpbenName should contain only Alphabets ")
    @NotBlank(message = "benName shouldn't be null ")
    private  String cpBeneficiaryName;

    @NotNull
    @NotBlank(message = "bankAcctNumber shouldn't be null")
//    @NotxBlank(message = "Please Enter cpBeneficiaryAcct ")
//    @DecimalMin(value = "9", inclusive = false)
//    @Digits(integer=18,fraction = 0)
    @Min(value=1)
    private  long cpBeneficiaryAcct;

    @NotNull
    @NotBlank(message = "Cp Beneficiary Acct Type shouldn't be null ")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "Please Enter valid Cp Beneficiary Acct Type ")
    private  String cpBeneficiaryAcctType;

    @NotNull
    @Pattern(regexp="^[A-Z]{4}0[A-Z0-9]{6}$",message = "Enter Valid bankIfscCode")
    @NotBlank(message = "bankIfscCode shouldn't be null")
    private  String cpBeneficiaryIfsc;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "bankName should contain only Alphabets ")
    @NotBlank(message = "bankName shouldn't be null")
    private  String cpBeneficiaryBankName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "bankBranch should contain only Alphabets ")
    @NotBlank(message = "bankBranch shouldn't be null")
    private String cpBeneficiaryBranch;

    @NotNull
    @NotBlank(message = "Cp Beneficiary MICR shouldn't be null ")
    @Pattern(regexp = "^[0-9]{9}$",message = "Please Enter valid cpBeneficiaryMicr")
    @Size(max=9,min=9,message="criteria not met")
    private String cpBeneficiaryMicr;

    @NotNull
    @NotBlank(message = "Cp Beneficiary MICR shouldn't be null ")
    @Pattern(regexp = "([a-zA-Z]){8}([0-9]){3}$",message = "Please Enter cpBeneficiarySwiftCode")
    @Size(max=11,min=11,message="criteria not met")
    private String cpBeneficiarySwiftCode;


}
