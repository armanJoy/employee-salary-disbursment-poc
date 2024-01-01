package com.ibcs.salaryapp.secuirty.config;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJwtTokenRepository extends CrudRepository<UserJwtToken, Long> {

    UserJwtToken findByUserId(String userId);

    UserJwtToken findByToken(String token);

    @Query(value = "DELETE FROM public.user_jwt_token WHERE token=?1 RETURNING user_id", nativeQuery = true)
    String removeUserJwt(String token);

}
