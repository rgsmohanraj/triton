package org.vcpl.triton.anchor.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerInfoData {

    private Long id;

    @NotBlank(message = "customerName shouldn't be null")
    @NotNull
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "customerName should contain only Alphabets")
        private String customerName;

//    @NotBlank(message = "customerType shouldn't be null")
//    @NotNull
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "customerType should contain only Alphabets ")
//    private String customerType;


    @Pattern(regexp = "^[a-zA-Z ]*$",message = "product should contain only Alphabets ")
    @NotBlank(message = "Please Enter Product")
    @NotNull
    private String product;

    @Pattern(regexp = "^[a-zA-Z0-9_.-]*$",message = "status should contain only Alphabets ")
    @NotBlank(message = "Please Enter dedupe status")
    @NotNull
    private String dedupeStatus;

    @NotNull
    @NotBlank(message = "Please Enter PAN")
    @Pattern(regexp = "([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}$",message = "Please Enter valid Pan Number")
   private String pan;

//    @NotBlank(message = "Please Enter CIN")
//    @Pattern(regexp = "([L|U]{1})([0-9]{5})([A-Za-z]{2})([0-9]{4})([A-Za-z]{3})([0-9]{6})$",message = "Please Enter valid Cin Number ")
    private String  cin;

    private Date businessExpiry;

    private String createdBy;

    private String updatedBy;

    private Boolean status;

}
