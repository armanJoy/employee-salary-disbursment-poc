package com.ibcs.salaryapp.repository.user;

import com.ibcs.salaryapp.model.domain.user.UserInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {

    UserInfo findTop1ByUserEmailOrNidAndActive(String email, String nid, boolean isActive);

    UserInfo findTop1ByUserEmailAndActive(String email, boolean isActive);

    UserInfo findById(long id);

    List<UserInfo> findAllByUserTypeAndActiveOrderByRankAsc(String userType, boolean active);

    @Query(value = "UPDATE public.user_info SET active=false WHERE id=?1 RETURNING active", nativeQuery = true)
    boolean deactiveUser(long userId);

}
