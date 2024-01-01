package com.ibcs.salaryapp.repository.salary;

import com.ibcs.salaryapp.model.domain.salary.CompanyBankAc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyBankAcRepo extends JpaRepository<CompanyBankAc, String> {

    @Query(value = "UPDATE public.company_bank_ac SET balance=(balance+?1) WHERE com_bank_id=?2 RETURNING *", nativeQuery = true)
    CompanyBankAc updateCompanySalaryAcBalance(double newBalance, String companyBankAcId);

    @Query(value = "SELECT balance FROM public.company_bank_ac WHERE com_bank_id=?1", nativeQuery = true)
    double getCompanySalaryAcBalance(String companyBankAcId);

}
