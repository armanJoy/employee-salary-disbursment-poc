package com.ibcs.salaryapp.repository.empbank;

import com.ibcs.salaryapp.model.domain.empbank.EmpBank;
import com.ibcs.salaryapp.model.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpBankRepo extends JpaRepository<EmpBank, Long> {

    EmpBank findByEmpBankIdAndUserIdAndActive(long empBankId, long userId, boolean isActive);

    EmpBank findByUserIdAndActive(long userId, boolean isActive);

    @Query(value = "UPDATE public.emp_bank SET active=false WHERE emp_bank_id=?1 RETURNING active", nativeQuery = true)
    boolean deactiveEmpBankAc(long bankId);

    @Query(value = "UPDATE public.emp_bank SET active=false WHERE user_id=?1 RETURNING *", nativeQuery = true)
    EmpBank deactiveEmpBankAcByUserId(long userId);

}
