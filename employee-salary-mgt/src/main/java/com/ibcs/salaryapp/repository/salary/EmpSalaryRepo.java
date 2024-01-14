package com.ibcs.salaryapp.repository.salary;

import com.ibcs.salaryapp.model.domain.salary.CompanyBankAc;
import com.ibcs.salaryapp.model.domain.salary.EmpSalary;
import com.ibcs.salaryapp.model.domain.salary.EmployeeSalary;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpSalaryRepo extends JpaRepository<EmpSalary, Long> {

    List<EmpSalary> findAllByUserId(long empUserId);

    @Query(value = "SELECT FROM public.emp_salary WHERE s_month=?1 AND s_year=?2 AND user_id IN (?3)", nativeQuery = true)
    List<EmpSalary> findAllBySMonthAndSYearAndUserIdIn(int sMonth, int sYear, List<Long> users);

    @Query(value = "SELECT a.id, a.first_name as firstName, a.last_name as lastName, a.joining_date as joiningDate, a.rank, b.emp_bank_id as bankId, CASE WHEN c.disbursed=true THEN c.s_amount ELSE (((SELECT balance from public.basic_salary WHERE basic_salary_id='basic-salary-info-0001') +((6-rank)*5000))*1.35) END as salary,  CASE WHEN c.disbursed=true THEN true ELSE false END AS disbursed,  CASE WHEN c.disbursed=true THEN c.bonus_per ELSE 0.0 END AS bonusPer, ?1 as month, ?2 as year  FROM public.user_info as a  LEFT JOIN public.emp_bank as b ON a.id=b.user_id and b.active=true  LEFT JOIN public.emp_salary as c ON a.id=c.user_id AND c.s_month=?1 AND c.s_year=?2 WHERE a.user_type='employee' AND a.active=true AND a.id IN (?3) ORDER BY a.rank ASC", nativeQuery = true)
    List<EmployeeSalary> getAllEmployeeSalaryForSpecificMonth(int sMonth, int sYear, List<Long> users);

    @Query(value = "SELECT a.id, a.first_name as firstName, a.last_name as lastName, a.rank, ((SELECT balance from public.basic_salary WHERE basic_salary_id='basic-salary-info-0001') +((6-rank)*5000)) as salary, b.emp_bank_id as bankId, 0.0 as bonusPer FROM public.user_info as a LEFT JOIN public.emp_bank as b ON a.id=b.user_id and b.active=true WHERE a.user_type='employee' AND a.active=true AND a.id NOT IN (SELECT user_id FROM public.emp_salary WHERE s_month=?1 AND s_year=?2 AND disbursed=true) ORDER BY a.rank DESC", nativeQuery = true)
    List<EmployeeSalary> getEmployeeSalary(int sMonth, int sYear);

    @Query(value = "SELECT a.id, a.first_name as firstName, a.last_name as lastName, a.rank, b.s_amount as salary, b.bank_id as bankId, b.bonus_per as bonusPer FROM public.user_info as a RIGHT JOIN public.emp_salary as b ON a.id=b.user_id AND b.s_month=?1 AND b.s_year=?2 and b.disbursed=true WHERE a.user_type='employee' AND a.active=true ORDER BY a.rank ASC", nativeQuery = true)
    List<EmployeeSalary> getDisbursedSalaryInfo(int sMonth, int sYear);

//    @Query(value = "SELECT a.id, a.first_name as firstName, a.last_name as lastName, a.rank, (((SELECT balance from public.basic_salary WHERE basic_salary_id='basic-salary-info-0001') +((6-rank)*5000))*1.35) as salary, b.emp_bank_id as bankId, b.bonus_per as bonusPer FROM public.user_info as a LEFT JOIN public.emp_bank as b ON a.id=b.user_id and b.active=true WHERE a.user_type='employee' AND a.active=true ORDER BY a.rank ASC", nativeQuery = true)
//    List<EmployeeSalary> getEmployeeSalaryDetails();
}
