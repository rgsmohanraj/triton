package org.vcpl.triton.anchor.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.*;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class AnchorAddressData {

        @NotNull
        private Long appId;

        private Long id;

        @Column(name = "address_line1", nullable = false)
        //@NotBlank(message = "addressLine1 shouldn't be null in Address Sheet")
        @NotNull(message = "addressLine1 shouldn't be null in Address Sheet")
        private String addressLine1;

        @Column(name = "address_line2", nullable = false)
        //@NotBlank(message = "addressLine2 shouldn't be null in Address Sheet")
        @NotNull(message = "addressLine2 shouldn't be null in Address Sheet")
        private String addressLine2;

        @Pattern(regexp = "^[a-zA-Z ]*$",message = "City should contain only Alphabets in Address Sheet")
        @NotBlank(message = "city shouldn't be null in Address Sheet")
        @NotNull
        private String city;


        @Pattern(regexp = "^[a-zA-Z ]*$",message = "State should contain only Alphabets in Address Sheet")
        @NotBlank(message = "state shouldn't be null in Address Sheet")
        @NotNull
        private String state;

        @NotBlank(message = "country shouldn't be null in Address Sheet")
        @NotNull
        @Pattern(regexp = "^[a-zA-Z ]*$",message = "Country should contain only Alphabets in Address Sheet")
        private String country;

        @Pattern(regexp = "[0-9]{6}",message = "Please Enter valid Pincode")
        @NotNull
        @NotBlank(message = "Please Enter Valid Pincode")
        private String pinCode;

        private String createdBy;

        private String updatedBy;


}




