package org.vcpl.triton.rbac.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="rbac_token")
public class TokenEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "browser_id")
    private String browserId;

    @Lob
    @Column(name = "jwt_token",length = 20000, nullable = false)
    private String jwtToken;

    @Lob
    @Column(name = "refresh_token", length = 2000, nullable = false)
    private String refreshToken;

    @Column(name = "flag", nullable = false)
    private int flag;

    @CreationTimestamp
    @Column(name = "timestamp")
    private Timestamp timestamp;

//    @Column(name = "valid_time")
//    private long tokenCreationSecond;

    @Column(name = "expired_time")
    private Timestamp tokenExpireTime;

    private Boolean status;
}
