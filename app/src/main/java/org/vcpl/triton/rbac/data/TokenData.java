package org.vcpl.triton.rbac.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenData {

//    @NotNull
//    private String browserId;

    @NotNull
    private String jwtToken;

    @NotNull
    private int flag;

    @NotNull
    private String refreshToken;

//    public Long getBrowserId() {
//        return browserId;
//    }
//
//    public void setBrowserId(Long browserId) {
//        this.browserId = browserId;
//    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String gen() {
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(20000)+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    }

}
