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
public class AnchorKeyData {
    @NotNull
    private Long appId;

    private  Long id;

    @NotNull
    @NotBlank(message = "type shouldn't be null in KeyPerson Sheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "type should contain only Alphabets in KeyPerson Sheet")
    private String type;

    @NotNull
    @NotBlank(message = "name shouldn't be null in KeyPerson Sheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "name should contain only Alphabets in KeyPerson Sheet")
    private String name;

    @NotNull
    @NotBlank(message = "Mobile shouldn't be null in KeyPerson Sheet")
    @Pattern(regexp = "[0-9]{10}",message = "Please Check Valid Mobile Number sin KeyPerson Sheet")
    @Size(max=10,min=10,message="criteria not met")
    private String mobile;

    @NotBlank(message = "EmailId shouldn't be null in KeyPerson Sheet")
    @NotNull
    @Email(message = "Enter valid mailId in KeyPerson Sheet")
    @Pattern(regexp = "^([a-zA-Z0-9_.+-]+)@([a-zA-Z0-9_.+-]+\\.(com|co.in|in)$)",message = "Please Enter valid Email-id in KeyPerson Sheet")
    private String emailId;

}
