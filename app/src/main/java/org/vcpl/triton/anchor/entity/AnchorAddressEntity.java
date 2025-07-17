package org.vcpl.triton.anchor.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="anchor_address")

public class AnchorAddressEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "address_line1", nullable = false)
    //@NotBlank(message = "addressLine1 shouldn't be null in Address Sheet")
    @NotNull(message = "addressLine1 shouldn't be null in Address Sheet")
    private String addressLine1;

    @Column(name = "address_line2", nullable = false)
    //@NotBlank(message = "addressLine2 shouldn't be null in Address Sheet")
    @NotNull(message = "addressLine2 shouldn't be null in Address Sheet")
    private String addressLine2;

    @Column(name = "city", nullable = false)
    @NotBlank(message = "city shouldn't be null in Address Sheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "City should contain only Alphabets in Address Sheet")
    @NotNull(message = "city shouldn't be null in Address Sheet")
    private String city;

    @Column(name = "state", nullable = false)
    @NotBlank(message = "state shouldn't be null in Address Sheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "State should contain only Alphabets in Address Sheet")
    @NotNull(message = "state shouldn't be null in Address Sheet")
    private String state;

    @Column(name = "country", nullable = false)
    @NotBlank(message = "country shouldn't be null in Address Sheet")
    @NotNull(message = "country shouldn't be null in Address Sheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "Country should contain only Alphabets in Address Sheet")
    private String country;

    @Column(name = "pin_code", nullable = false)
    @NotBlank(message = "pinCode shouldn't be null in Address Sheet")
    @Pattern(regexp = "[0-9]{6}",message = "Please Enter valid Pincode")
    private String pinCode;

    @Column(name = "update_by",nullable = true)
    private String updatedBy;

    @Column(name="created_by",nullable = true)
    private String createdBy;

    @Column(name="updated_at",nullable = true)
    private Timestamp updatedAt;

    @Column(name="created_at",nullable = true)
    private Timestamp createdAt;

    @ManyToOne(targetEntity = ApplicationEntity.class,fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;

}
