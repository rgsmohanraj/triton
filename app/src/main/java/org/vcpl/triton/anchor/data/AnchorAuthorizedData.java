package org.vcpl.triton.anchor.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnchorAuthorizedData {

    @NotNull
    private Long appId;

    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "type should contain only Alphabets in Authorized Sheet")
    @NotBlank(message = "type shouldn't be null in Authorized Sheet")
    private String type;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "Name should contain only Alphabets in Authorized Sheet")
    @NotBlank(message = "Name shouldn't be null in Authorized Sheet")
    private String name;

    @NotNull
    @Pattern(regexp = "[0-9]{10}",message = "Please Check valid Mobile Number in Authorized Sheet")
    @NotBlank(message = "Please Enter Mobile Number")
    @Size(max=10,min=10,message="criteria not met")
    private String mobile;

    @NotNull
    @NotBlank(message ="Please Enter Email-id")
    @Email(message = "Enter valid EmailId in Authorized Sheet")
    @Pattern(regexp = "^([a-zA-Z0-9_.+-]+)@([a-zA-Z0-9_.+-]+\\.(com|co.in|in)$)",message = "Please Enter valid Email-id in Authorized Sheet")
    private String emailId;

    //@NotBlank(message = "Please Enter IndemnityDoc")
    private String indemnityDoc;

    private String createdBy;

    private String updatedBy;


}
