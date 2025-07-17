package org.vcpl.triton.counterParty.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CPBasicDetailsData {

    //Update
    private Long id;
    private Long appId;
    private Long custId;

    @NotNull
    @NotBlank(message = "PAN shouldn't be null in cpbasic")
    @Pattern(regexp = "([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}$",message = "Please Enter valid Pan Number in cpbasic")
    @Size(max=10,min=10,message="criteria not met")
    private String panNumber;

    @NotNull
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "companyName should contain only Alphabets in CpBasic")
    @NotBlank(message = "companyName shouldn't be null in CpBasic")
    private String companyName;

//    @Pattern(regexp = "([0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1})$",message = "please Enter valid Gst Number in cpbasic")
//    @Size(max=15,min=15,message="criteria not met")
    private String gstNumber;

//    @NotNull
//    @NotBlank(message = "CIN shouldn't be null in Basic Details in Sheet")
//    @Pattern(regexp = "([L|U]{1})([0-9]{5})([A-Za-z]{2})([0-9]{4})([A-Za-z]{3})([0-9]{6})$",message = "Please Enter valid CIN Number in cpbasic")
//    @Size(max=21,min=21,message="criteria not met")
    private String cinNumber;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "constitution should contain only Alphabets in CpBasic")
    @NotBlank(message = "constitution shouldn't be null in CpBasic")
    private String constitution;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "city should contain only Alphabets in CpBasic")
    @NotBlank(message = "city shouldn't be null in CpBasic")
    private String city;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "state should contain only Alphabets in CpBasic")
    @NotBlank(message = "state shouldn't be null in CpBasic")
    private String state;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "source should contain only Alphabets in CpBasic")
    @NotBlank(message = "source shouldn't be null in CpBasic")
    private String source;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "subSource should contain only Alphabets in CpBasic")
    @NotBlank(message = "subSource shouldn't be null in CpBasic")
    private String subSource;

    @NotNull
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "rmName should contain only Alphabets in CpBasic")
    @NotBlank(message = "rmName shouldn't be null in CpBasic")
    private String rmName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "cusContName should contain only Alphabets in CpBasic")
    @NotBlank(message = "cusContName shouldn't be null in CpBasic")
    private String cusContName;

    @NotNull
    @NotBlank(message = "cusContNumber shouldn't be null in CpBasic")
    @Pattern(regexp = "[0-9]{10}",message = "Name should contain only Alphabets in Authorized Sheet")
    @Size(max=10,min=10,message="criteria not met")
    @Min(value= 1111111111)
    private String cusContactNumber;

    @NotNull
    @NotBlank(message = "Enter valid EmailId in CpBasic")
    @Pattern(regexp = "^([a-zA-Z0-9_.+-]+)@([a-zA-Z0-9_.+-]+\\.[a-zA-Z0-9_.+-]+$)",message = "Please Enter valid Email-id in CpBasic Sheet")
//    @Pattern(regexp = "^[a-zA-Z0-9-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",message = "Please Enter valid Email-id in CpBasic Sheet")
    private String cusContactEmail;

    @NotNull
    @NotBlank(message = "Enter valid Activity")
//    @Pattern(regexp = "^(.+)+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",message = "Please Enter valid Email-id in CpBasic Sheet")
//    @Pattern(regexp = "^[a-zA-Z0-9-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",message = "Please Enter valid Email-id in CpBasic Sheet")
    private String activity;

    @NotNull
    @NotBlank(message = "Enter Valid CpType")
    private String counterPartyType;

    private String assessmentType;

    private String anchorRelationShip;

    private String totalInwardCheques;

    private String facilityTenure;

    private String createdBy;

    private String updatedBy;

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getCinNumber() {
        return cinNumber;
    }

    public void setCinNumber(String cinNumber) {
        this.cinNumber = cinNumber;
    }

    public String getConstitution() {
        return constitution;
    }

    public void setConstitution(String constitution) {
        this.constitution = constitution;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSubSource() {
        return subSource;
    }

    public void setSubSource(String subSource) {
        this.subSource = subSource;
    }

    public String getRmName() {
        return rmName;
    }

    public void setRmName(String rmName) {
        this.rmName = rmName;
    }

    public String getCusContName() {
        return cusContName;
    }

    public void setCusContName(String cusContName) {
        this.cusContName = cusContName;
    }

    public String getCus_contact_number() {
        return cusContactNumber;
    }

    public String getCusContactEmail() {
        return cusContactEmail;
    }

    public void setCusContactEmail(String cusContactEmail) {
        this.cusContactEmail = cusContactEmail;
    }

    public String getCusContactNumber() {
        return cusContactNumber;
    }

    public void setCusContactNumber(String cusContactNumber) {
        this.cusContactNumber = cusContactNumber;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
