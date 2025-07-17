package org.vcpl.triton.counterParty.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CPDebtProfileData {

    private Long id;

    private Long appId;

//    @NotNull
//    @NotBlank(message = "sequence shouldn't be null in CpDebtProfile")
//    @Pattern(regexp = "^[0-9]$", message = "Please Enter Valid sequence")
//    private String seq;

    @NotNull
    @NotBlank(message = "bankFI shouldn't be null in CpDebtProfile")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "bankFI should contain only Alphabets ")
    private String bankFI;

    @NotNull
    @NotBlank(message = "facilityType shouldn't be null in CpDebtProfile")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "facilityType should contain only Alphabets ")
    private String facilityType;

    @NotNull
    @NotBlank(message = "tenure shouldn't be null in CpDebtProfile")
    @Pattern(regexp = "^[0-9]*$", message = "Please Enter Valid tenure")
    @Size(max=4,min=1,message="criteria not met")
    private String tenure;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotBlank(message = "sanctionDate shouldn't be null in CpDebtProfile")
//    @Pattern(regexp = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$",message = "Please Enter Valid date")
    private String sanctionDate;

    @NotNull
    @NotBlank(message = "sanctionLimit shouldn't be null in CpDebtProfile")
    @Pattern(regexp = "[+-]?([0-9]*[.])?[0-9]+", message = "Please Enter Valid tenure")
    @Size(max=10,min=1,message="criteria not met")
    @Min(value=1)
    private String sanctionLimit;

    @NotNull
    @NotBlank(message = "outstandingOnDate shouldn't be null in CpDebtProfile")
    @Pattern(regexp = "[+-]?([0-9]*[.])?[0-9]+",message = "Please Enter valid date ")
    @Size(max=10,min=1,message="criteria not met")
    @Min(value=1)
    private String outstandingOnDate;

    @NotNull
    @NotBlank(message = "emi shouldn't be null in CpDebtProfile")
    @Pattern(regexp = "[+-]?([0-9]*[.])?[0-9]+", message = "Please Enter Valid emi")
//    @Min(value=1, message = "Value should be greater 1 digit")
//    @Max(value=8, message = "Value should be less than 8 digit")
    @Size(max=10,min=1,message="criteria not met")
    @Min(value=0)
    private String emi;

    @NotNull
    @NotBlank(message = "interestRate shouldn't be null in CpDebtProfile")
    @Pattern(regexp = "^[0-9]{0,2}(\\.[0-9]{0,2})?$", message = "Please Enter Valid interestRate")
    private String interestRate;

    @NotNull
    @NotBlank(message = "security shouldn't be null in CpDebtProfile")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Please Enter Valid security")
    private String security;


    @NotNull
    @NotBlank(message = "specificLimit shouldn't be null in CpDebtProfile")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$",message = "specificLimit should contain only AlphaNumeric in CpBasic")
    private String specificLimit;

//    @NotNull
//    @NotBlank(message = "cmpdInt shouldn't be null in CpDebtProfile")
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "specificLimit should contain only Alphabets in CpBasic")
//    private String cmpdInt;

//    @NotNull
//    @NotBlank(message = "cmpdOvdInt shouldn't be null in CpDebtProfile")
////    @Pattern(regexp = "^[a-zA-Z ]*$",message = "specificLimit should contain only Alphabets in CpBasic")
//    private String cmpdOvdInt;

    @NotNull
//    @Pattern(regexp = "/^[A-Za-z0-9_@./#&+-]*$/",message = "remarks should contain only Alphabets in CpDebtProfile")
    @NotBlank(message = "remarks shouldn't be null in CpBasic")
    private String remarks;

    private String updatedBy;

    private String createdBy;

    public String getBankFI() {
        return bankFI;
    }

    public void setBankFI(String bankFI) {
        this.bankFI = bankFI;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public String getSanctionDate() {
        return sanctionDate;
    }

    public void setSanctionDate(String sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    public String getSanctionLimit() {
        return sanctionLimit;
    }

    public void setSanctionLimit(String sanctionLimit) {
        this.sanctionLimit = sanctionLimit;
    }

    public String getOutstandingOnDate() {
        return outstandingOnDate;
    }

    public void setOutstandingOnDate(String outstandingOnDate) {
        this.outstandingOnDate = outstandingOnDate;
    }

    public String getEmi() {
        return emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }


    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getSpecificLimit() {
        return specificLimit;
    }

    public void setSpecificLimit(String specificLimit) {
        this.specificLimit = specificLimit;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
