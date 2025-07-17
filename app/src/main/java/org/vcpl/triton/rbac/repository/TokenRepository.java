package org.vcpl.triton.rbac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.rbac.entity.TokenEntity;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    @Query(value = "SELECT * FROM rbac_token AS t WHERE t.browser_id=?1 ORDER BY t.id DESC LIMIT 1",nativeQuery = true)
    TokenEntity getTokenDetailsByBrowserId(String browserId);

//    @Query(value = "DELETE FROM rbac_token WHERE refresh_token=?1")
//    void deleteRefreshToken(String refreshToken);

}
