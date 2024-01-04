package com.ibcs.salaryapp.service.salary;

import com.ibcs.salaryapp.model.domain.salary.EmpSalary;
import com.ibcs.salaryapp.model.domain.salary.EmployeeSalary;
import com.ibcs.salaryapp.model.view.salary.DisbursedSalaryVm;
import com.ibcs.salaryapp.model.view.util.ApiStatusVm;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface SalaryService {

    Object getBasicSalary();

    ApiStatusVm updateBasicSalary(double newBasicSalary);

    Object getCompanySalaryAcBalance();

    ApiStatusVm updateComSalaryAcBalance(double newBasicSalary);

    DisbursedSalaryVm disburseMonthlySalary(int month, int year);

    List<EmployeeSalary> getDisburseSalaryDetails(int month, int year);

    List<EmployeeSalary> getEmployeeSalaryDetails();

}
