package com.ibcs.salaryapp.service.salary;

import com.ibcs.salaryapp.model.domain.salary.BasicSalary;
import com.ibcs.salaryapp.model.domain.salary.CompanyBankAc;
import com.ibcs.salaryapp.model.domain.salary.EmpSalary;
import com.ibcs.salaryapp.model.domain.salary.EmployeeSalary;
import com.ibcs.salaryapp.model.view.salary.DisbursedSalaryVm;
import com.ibcs.salaryapp.model.view.util.ApiStatusVm;
import com.ibcs.salaryapp.repository.salary.BasicSalaryRepo;
import com.ibcs.salaryapp.repository.salary.CompanyBankAcRepo;
import com.ibcs.salaryapp.repository.salary.EmpSalaryRepo;
import com.ibcs.salaryapp.util.AppConstant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    BasicSalaryRepo basicSalaryRepo;

    @Autowired
    CompanyBankAcRepo companyBankAcRepo;

    @Autowired
    EmpSalaryRepo empSalaryRepo;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ApiStatusVm updateBasicSalary(double newBasicSalary) {
        ApiStatusVm apiStatusVm = new ApiStatusVm();
        try {
            BasicSalary basicSalary = basicSalaryRepo.updateCompanySalaryAcBalance(newBasicSalary, AppConstant.BASIC_SALARY_INFO_ID);
            if (basicSalary != null && Double.compare(basicSalary.getBalance(), newBasicSalary) == 0) {
                apiStatusVm.setJobDone(true);
                apiStatusVm.setMsg("Basic salary updated");
            } else {
                apiStatusVm.setJobDone(false);
                apiStatusVm.setMsg("Couldn't update basic salary");
            }
        } catch (Exception e) {
            apiStatusVm.setMsg("Error: " + e.getMessage());
            logger.info(this.getClass().getName() + " :inside updateBasicSalary method\nError: " + e.getMessage());
        }

        return apiStatusVm;
    }

    @Override
    public ApiStatusVm updateComSalaryAcBalance(double newBalance) {
        ApiStatusVm apiStatusVm = new ApiStatusVm();
        try {
            CompanyBankAc companyBankAc = companyBankAcRepo.updateCompanySalaryAcBalance(newBalance, AppConstant.COMPANY_BANK_AC_ID);
            if (companyBankAc != null) {
                apiStatusVm.setJobDone(true);
                apiStatusVm.setMsg("Company salary account balance updated");
            } else {
                apiStatusVm.setJobDone(false);
                apiStatusVm.setMsg("Couldn't update Company salary account balance");
            }
        } catch (Exception e) {
            apiStatusVm.setMsg("Error: " + e.getMessage());
            logger.info(this.getClass().getName() + " :inside updateComSalaryAcBalance method\nError: " + e.getMessage());
        }

        return apiStatusVm;
    }

    @Override
    public List<EmployeeSalary> getDisburseSalaryDetails(int month, int year) {
        return empSalaryRepo.getDisbursedSalaryInfo(month, year);
    }

    @Override
    @Transactional
    public DisbursedSalaryVm disburseMonthlySalary(int month, int year) {
        DisbursedSalaryVm disbursedSalaryVm = new DisbursedSalaryVm();
        List<EmployeeSalary> disbursedEmployees = new ArrayList<>();
//        List<Object> paidEmployees = new ArrayList<>();
//        try {
//            empSalaryRepo.populateEmployeeSalary(month, year);
//            System.out.println("Salary populated");
//        } catch (Exception e) {
//            logger.info(this.getClass().getName() + " :inside disburseMonthlySalary method\nError: " + e.getMessage());
//            System.out.println("Salary populated");
//        }
        double balance = companyBankAcRepo.getCompanySalaryAcBalance(AppConstant.COMPANY_BANK_AC_ID);
        double disbursedSalary = 0;
        double undisbursedSalary = 0;
        List<EmployeeSalary> employeeSalarys = empSalaryRepo.getEmployeeSalary(month, year);
        List<EmpSalary> disbursedSalaries = new ArrayList<>();
        for (EmployeeSalary employeeSalary : employeeSalarys) {

            double salary = employeeSalary.getSalary() + (employeeSalary.getSalary() * (AppConstant.HOUSE_RENT_PERCENTAGE / 100.0)) + (employeeSalary.getSalary() * (AppConstant.MEDICAL_ALLOWANCE_PERCENTAGE / 100.0));
            if (balance >= salary && salary > 0) {
                EmpSalary empSalary = new EmpSalary();
                empSalary.setUserId(employeeSalary.getId());
                empSalary.setBankId(employeeSalary.getBankId());
                empSalary.setSAmount(salary);
                empSalary.setSMonth(month);
                empSalary.setSYear(year);
                empSalary.setDisbursed(true);
                disbursedSalaries.add(empSalary);
                balance -= salary;
                disbursedSalary += salary;
//                disbursedEmployees.add(employeeSalary);
            } else {
                undisbursedSalary += salary > 0 ? salary : 0;
            }

        }

        try {
            empSalaryRepo.saveAll(disbursedSalaries);
            CompanyBankAc companyBankAc = companyBankAcRepo.updateCompanySalaryAcBalance((disbursedSalary * -1), AppConstant.COMPANY_BANK_AC_ID);
//            disbursedSalaryVm.setDisbursedAmount(disbursedSalary);
            disbursedSalaryVm.setUndisbursedAmount(undisbursedSalary);
            disbursedSalaryVm.setRemainingBalance(companyBankAc.getBalance());
            disbursedSalaryVm.setDisburseComplete(undisbursedSalary == 0);
            disbursedSalaryVm.setMsg((undisbursedSalary == 0) ? "All salary disbursed successfully" : "Add more money to the company salary account");
            disbursedEmployees = empSalaryRepo.getDisbursedSalaryInfo(month, year);
            disbursedSalaryVm.setDisbursedEmployees(disbursedEmployees);
        } catch (Exception e) {

        }

        return disbursedSalaryVm;
    }

}
