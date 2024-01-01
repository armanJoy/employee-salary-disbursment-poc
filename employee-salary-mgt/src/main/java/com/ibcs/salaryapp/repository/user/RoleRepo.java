package com.ibcs.salaryapp.repository.user;

import com.ibcs.salaryapp.model.domain.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    Role findByRoleId(long roleId);

    Role findByRoleName(String roleName);

}
