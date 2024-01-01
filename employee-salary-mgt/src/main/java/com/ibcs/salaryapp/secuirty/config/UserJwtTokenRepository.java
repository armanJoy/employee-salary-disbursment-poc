package com.ibcs.salaryapp.secuirty.config;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJwtTokenRepository extends CrudRepository<UserJwtToken, Long> {

    UserJwtToken findByUserId(String userId);

    UserJwtToken findByToken(String token);

}
