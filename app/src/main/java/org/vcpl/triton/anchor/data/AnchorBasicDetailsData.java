package org.vcpl.triton.anchor.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnchorBasicDetailsData {

    @NotNull
    private Long appId;

    private  Long id;

//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "anchorName should contain only Alphabets in Basic Details Sheet")
    @NotBlank(message = "anchorName shouldn't be null in Basic Details Sheet")
    @NotNull
    private String anchorName;

    @NotBlank(message = "Industry shouldn't be null in Basic Details Sheet")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "Industry should contain only Alphabets in Basic Details Sheet")
    private String industry;

    @NotBlank(message = "Sub-Industry shouldn't be null in Basic Details Sheet")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "Sector should contain only only Alphabets in Basic Details in Sheet")
    private String subIndustry;

    @NotBlank(message = "CIN shouldn't be null in Basic Details in Sheet")
    @NotNull
    @Pattern(regexp = "([L|U]{1})([0-9]{5})([A-Za-z]{2})([0-9]{4})([A-Za-z]{3})([0-9]{6})$",message = "Please Enter valid CIN Number in Basic Details Sheet")
    @Size(max=21,min=21,message="criteria not met")
    private String cin;

    @JsonFormat(pattern="yyyy-MM-dd")
//    @NotBlank(message = "Please Enter IncorporationDate")
    @NotNull
    //@Pattern(regexp = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$",message = "Please Enter valid incorporationDate in Basic Details Sheet")
    private Date incorporationDate;

    @NotBlank(message = "PAN shouldn't be null in Basic Details Sheet")
    @NotNull
    @Pattern(regexp = "([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}$",message = "Please Enter valid Pan Number in Basic Details Sheet")
    @Size(max=10,min=10,message="criteria not met")
    private String pan;

    @NotBlank(message = "Activity shouldn't be null in Basic Details Sheet")
    @NotNull
    @Pattern(regexp = "^[ A-Za-z0-9_@./#&+-]*$",message = "Activity should contain only only Alphabets in Basic Details in Sheet")
    private String activity;

    @NotBlank(message = "Constitution shouldn't be null in Basic Details Sheet")
    @NotNull
    @Pattern(regexp = "^[ A-Za-z0-9_@./#&+-]*$",message = "Constitution should contain only only Alphabets in Basic Details in Sheet")
    private String constitution;

//    @NotBlank(message = "Facility Tenure shouldn't be null in Basic Details Sheet")
    @NotNull
//    @Pattern(regexp = "[0-9]{4}",message = "Facility Tenure should contain only only Numbers in Basic Details in Sheet")
    private String businessExpiry;


    private String createdBy;

    private String updatedBy;


}
