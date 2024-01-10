package com.ibcs.salaryapp.repository.salary;

import com.ibcs.salaryapp.model.domain.salary.BasicSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicSalaryRepo extends JpaRepository<BasicSalary, String> {

    @Query(value = "UPDATE basic_salary SET balance=?1 WHERE basic_salary_id=?2 RETURNING *", nativeQuery = true)
    BasicSalary updateCompanySalaryAcBalance(double newBasicSalary, String companyBankAcId);

    BasicSalary findByBasicSalaryId(String basicSalaryId);

}
