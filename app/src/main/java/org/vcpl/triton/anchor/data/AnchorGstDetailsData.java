package org.vcpl.triton.anchor.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnchorGstDetailsData {

    @NotNull
    private Long appId;

    private  Long id;

    @NotBlank(message = "gstNumber shouldn't be null in GST Sheet")
    @NotNull
    @Pattern(regexp = "([0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1})$",message = "please Enter valid Gst Number in GST Sheet")
    @Size(max=15,min=15,message="criteria not met")
    private String gstNumber;

    @NotNull
    @NotBlank(message = "gstAccntHolderName shouldn't be null in GST Sheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "ACCOUNT HOLDER NAME should contain only Alphabets in GST Sheet")
    private String gstAccntHolderName;

    @NotNull(message = "addressLine1 shouldn't be null in Address Sheet")
    //@NotBlank(message = "addressLine1 shouldn't be null in Address Sheet")
    private String addressLine1;

    @NotNull(message = "addressLine2 shouldn't be null in Address Sheet")
    //@NotBlank(message = "addressLine2 shouldn't be null in Address Sheet")
    private String addressLine2;

    @NotBlank(message = "city shouldn't be null in GST Sheet")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "City should contain only Alphabets in GST Sheet")
    private String city;

    @NotBlank(message = "state shouldn't be null in GST Sheet")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "State should contain only Alphabets in GST Sheet")
    private String state;

    @NotBlank(message = "country shouldn't be null in GST Sheet")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "Country should contain only Alphabets in GST Sheet")
    private String country;

    @NotBlank(message = "pincode shouldn't be null in GST Sheet")
    @NotNull
    @Pattern(regexp = "[0-9]{6}",message = "Please Enter valid Pincode")
    @Size(max=6,min=6,message="criteria not met")
    private String pinCode;


}
