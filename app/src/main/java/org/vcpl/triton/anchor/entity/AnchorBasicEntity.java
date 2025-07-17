package org.vcpl.triton.anchor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="anchor_basic")
public class AnchorBasicEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "anchor_name", nullable = false)
    @NotBlank(message = "anchorName shouldn't be null in Basic Details Sheet")
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "anchorName should contain only Alphabets in Basic Details Sheet")
    @NotNull(message = "anchorName shouldn't be null in Basic Details Sheet")
    private String anchorName;

    @Column(name = "industry", nullable = false)
    @NotBlank(message = "industry shouldn't be null in Basic Details Sheet")
    @NotNull(message = "Industry shouldn't be null in Basic Details Sheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "Industry should contain only Alphabets in Basic Details Sheet")
    private String industry;

    @Column(name = "sector", nullable = false)
    @NotBlank(message = "sector shouldn't be null in Basic Details Sheet")
    @NotNull(message = "Sector shouldn't be null in Basic Details Sheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "Sector should contain only only Alphabets in Basic Details in Sheet")
    private String sector;

    @Column(name = "cin", nullable = false)
    @NotBlank(message = "cin shouldn't be null in Basic Details Sheet")
    @NotNull(message = "CIN shouldn't be null in Basic Details in Sheet")
    @Pattern(regexp = "([L|U]{1})([0-9]{5})([A-Za-z]{2})([0-9]{4})([A-Za-z]{3})([0-9]{6})$",message = "Please Enter valid CIN Number in Basic Details Sheet")
    private String cin;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name ="incorp_date",nullable = false)
  //  @NotNull(message = "Incorp Date shouldn't be null in Basic Details Sheet")
    //    @NotBlank(message = "IncorpDate shouldn't be null in Basic Details Sheet")
   // @Pattern(regexp = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$", message="Enter a valid  Date")
    private Date incorpDate;

    @Column(name = "pan", nullable = false)
    @NotBlank(message = "pan shouldn't be null in Basic Details Sheet")
    @NotNull(message = "PAN shouldn't be null in Basic Details Sheet")
    @Pattern(regexp = "([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}$",message = "Please Enter valid Pan Number in Basic Details Sheet")
    private String pan;

    @Column(name = "activity", nullable = false)
    @NotBlank(message = "activity shouldn't be null in Basic Details Sheet")
    @NotNull(message = "ACtivity shouldn't be null in Basic Details Sheet")
    @Pattern(regexp = "^[ A-Za-z0-9_@./#&+-]*$",message = "Activity should contain only only Alphabets in Basic Details in Sheet")
    private String activity;

    @Column(name = "constitution", nullable = false)
    @NotBlank(message = "constitution shouldn't be null in Basic Details Sheet")
    @NotNull(message = "Constitution shouldn't be null in Basic Details Sheet")
    @Pattern(regexp = "^[ A-Za-z0-9_@./#&+-]*$",message = "Constitution should contain only only Alphabets in Basic Details in Sheet")
    private String constitution;

//    @Column(name = "business_expiry", nullable = false)
//    @NotBlank(message = "businessExpiry shouldn't be null in Basic Details Sheet")
//    @NotNull(message = "businessExpiry shouldn't be null in Basic Details Sheet")
//    @Pattern(regexp = "[0-9]{2}",message = "BusinessExpiry should contain only only Alphabets in Basic Details in Sheet")
    private String businessExpiry;


    @Column(name = "created_by",nullable = true)
    private String createdBy;

    @Column(name = "updated_by",nullable = true)
    private String updatedBy;

    @Column(name = "created_at",nullable = true)
    private Timestamp createdAt;

    @Column(name = "updated_at",nullable = true)
    private Timestamp updatedAt;

    @ManyToOne(targetEntity = ApplicationEntity.class,fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;

    public void setTestIncorpDate(Date incorpDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(incorpDate);
        String strDate1 = strDate.concat(" 12:00:00");
        Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate1);
        this.incorpDate = date1;
    }
}
