package org.vcpl.triton.rbac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vcpl.triton.rbac.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT usr.* FROM rbac_user AS usr inner join rbac_group AS grp ON grp.id=usr.group_id where user_name=?1",nativeQuery = true)
    UserEntity findByMailId(String mailId);
}
