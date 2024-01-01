package com.ibcs.salaryapp.repository.user;

import com.ibcs.salaryapp.model.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Long> {

    UserRole findByUserId(long userId);

}
