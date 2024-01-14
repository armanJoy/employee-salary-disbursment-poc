package com.ibcs.salaryapp.service.salary;

import com.ibcs.salaryapp.model.domain.empbank.EmpBank;
import com.ibcs.salaryapp.model.domain.salary.BasicSalary;
import com.ibcs.salaryapp.model.domain.salary.CompanyBankAc;
import com.ibcs.salaryapp.model.domain.salary.EmpSalary;
import com.ibcs.salaryapp.model.domain.salary.EmployeeSalary;
import com.ibcs.salaryapp.model.domain.user.UserInfo;
import com.ibcs.salaryapp.model.view.empbank.EmpBankVm;
import com.ibcs.salaryapp.model.view.salary.DisbursedSalaryVm;
import com.ibcs.salaryapp.model.view.salary.EmployeeSalaryVm;
import com.ibcs.salaryapp.model.view.util.ApiStatusVm;
import com.ibcs.salaryapp.repository.salary.BasicSalaryRepo;
import com.ibcs.salaryapp.repository.salary.CompanyBankAcRepo;
import com.ibcs.salaryapp.repository.salary.EmpSalaryRepo;
import com.ibcs.salaryapp.repository.user.UserInfoRepo;
import com.ibcs.salaryapp.service.empbank.EmpBankService;
import com.ibcs.salaryapp.util.AppConstant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    EmpBankService empBankService;

    @Autowired
    BasicSalaryRepo basicSalaryRepo;

    @Autowired
    CompanyBankAcRepo companyBankAcRepo;

    @Autowired
    EmpSalaryRepo empSalaryRepo;

    @Autowired
    UserInfoRepo userInfoRepo;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object getBasicSalary() {
        return basicSalaryRepo.findById(AppConstant.BASIC_SALARY_INFO_ID);
    }

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
    public Object getCompanySalaryAcBalance() {
        return companyBankAcRepo.findById(AppConstant.COMPANY_BANK_AC_ID);
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

//    @Override
//    public List<EmployeeSalary> getEmployeeSalaryDetails() {
//        List<EmployeeSalary> employeeSalarys = empSalaryRepo.getEmployeeSalaryDetails();
//        return employeeSalarys;
//    }
    @Override
    @Transactional
    public DisbursedSalaryVm disburseMonthlySalary(int month, int year) {
        DisbursedSalaryVm disbursedSalaryVm = new DisbursedSalaryVm();
        List<EmployeeSalary> disbursedEmployees = new ArrayList<>();

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
                empSalary.setBonusPer(employeeSalary.getBonusPer());
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
            disbursedSalaryVm.setDisbursedAmount(disbursedSalary);
            disbursedSalaryVm.setUndisbursedAmount(undisbursedSalary);
            disbursedSalaryVm.setRemainingBalance(companyBankAc.getBalance());
            disbursedSalaryVm.setDisburseComplete(undisbursedSalary == 0);

            if (employeeSalarys.size() == 0) {
                disbursedSalaryVm.setMsg("Already disbursed.");
            } else {
                disbursedSalaryVm.setMsg((undisbursedSalary == 0) ? "All salary disbursed successfully" : "Add more money to the company salary account");
            }

            disbursedEmployees = empSalaryRepo.getDisbursedSalaryInfo(month, year);
            disbursedSalaryVm.setDisbursedEmployees(disbursedEmployees);
        } catch (Exception e) {

        }

        return disbursedSalaryVm;
    }

    @Override
    public ApiStatusVm payDueSalary(EmpSalary dueSalary) {
        ApiStatusVm apiStatusVm = new ApiStatusVm();
        try {
            double balance = companyBankAcRepo.getCompanySalaryAcBalance(AppConstant.COMPANY_BANK_AC_ID);

            if (balance >= dueSalary.getSAmount()) {
                EmpBankVm empBank = empBankService.getEmpBank(dueSalary.getUserId());
                dueSalary.setDisbursed(true);
                if (empBank != null) {
                    dueSalary.setBankId(empBank.getEmpBankId());
                }

                empSalaryRepo.save(dueSalary);

                apiStatusVm.setJobDone(true);
                apiStatusVm.setMsg("Salary disbursed successfully");
                apiStatusVm.setData(dueSalary);
            } else {
                apiStatusVm.setJobDone(false);
                apiStatusVm.setMsg("Add more money to the company salary account");
            }
        } catch (Exception e) {
            apiStatusVm.setMsg("Error: " + e.getMessage());
            logger.info(this.getClass().getName() + " :inside updateComSalaryAcBalance method\nError: " + e.getMessage());
        }

        return apiStatusVm;
    }

    @Override
    public ApiStatusVm disburseSalaryToSpecificUsers(List<EmployeeSalaryVm> disbursableSalary) {
        List<EmployeeSalaryVm> disbursedEmployees = new ArrayList<>();
        ApiStatusVm apiStatusVm = new ApiStatusVm();

        double balance = companyBankAcRepo.getCompanySalaryAcBalance(AppConstant.COMPANY_BANK_AC_ID);

        double disbursedSalary = 0;
        double undisbursedSalary = 0;
        List<EmpSalary> disbursedSalaries = new ArrayList<>();
        for (EmployeeSalaryVm employeeSalary : disbursableSalary) {

            double salary = employeeSalary.getSalary();

            if (balance >= salary && salary > 0) {
                EmpSalary empSalary = new EmpSalary();
                empSalary.setUserId(employeeSalary.getId());
                empSalary.setBankId(employeeSalary.getBankId());
                empSalary.setSAmount(salary);
                empSalary.setBonusPer(employeeSalary.getBonusPer());
                empSalary.setSMonth(employeeSalary.getMonth());
                empSalary.setSYear(employeeSalary.getYear());
                empSalary.setDisbursed(true);
                balance -= salary;
                disbursedSalary += salary;
                disbursedSalaries.add(empSalary);

                employeeSalary.setDisbursed(true);
                employeeSalary.setBankId(employeeSalary.getBankId());
                disbursedEmployees.add(employeeSalary);
            } else {
                undisbursedSalary += salary > 0 ? salary : 0;
            }

        }
        if (disbursedSalaries.size() == 0) {
            apiStatusVm.setJobDone(false);
            apiStatusVm.setMsg("Insufficient balance. Need " + Math.abs(balance - undisbursedSalary) + " tk");
            apiStatusVm.setData(new ArrayList<>());

        } else {
            try {
                disbursedSalaries = empSalaryRepo.saveAll(disbursedSalaries);
                apiStatusVm.setData(disbursedEmployees);
                apiStatusVm.setJobDone(true);
                if (disbursedSalaries.size() == disbursableSalary.size()) {
                    apiStatusVm.setMsg("Salary disbursed successfully");
                } else {
                    apiStatusVm.setMsg("Disbursed " + disbursedSalaries.size() + " out of " + disbursableSalary.size() + " salaries. Need " + Math.abs(balance - undisbursedSalary) + " tk");
                }
                CompanyBankAc companyBankAc = companyBankAcRepo.updateCompanySalaryAcBalance((disbursedSalary * -1), AppConstant.COMPANY_BANK_AC_ID);

            } catch (Exception e) {
                apiStatusVm.setMsg("Error: " + e.getMessage());
                logger.info(this.getClass().getName() + " :inside updateComSalaryAcBalance method\nError: " + e.getMessage());
            }
        }
        return apiStatusVm;
    }

    @Override
    public List<EmployeeSalary> getMonthlySalaryDetailsForSpecificEmployees(int sMonth, int sYear, List<Long> users) {
        return empSalaryRepo.getAllEmployeeSalaryForSpecificMonth(sMonth, sYear, users);
    }

    @Override
    public List<EmpSalary> getEmployeeSalaryHistory(long empId) {
        List<EmpSalary> allSalaries = new ArrayList<>();
        List<EmpSalary> disbursedSalaries = empSalaryRepo.findAllByUserId(empId);

        UserInfo userInfo = userInfoRepo.findById(empId);
        BasicSalary basicSalary = basicSalaryRepo.findByBasicSalaryId(AppConstant.BASIC_SALARY_INFO_ID);
        final double salaryAmount = (basicSalary.getBalance() + ((6 - userInfo.getRank()) * 5000)) * AppConstant.ALLOWANCE_PERCENTAGE;

        LocalDate joiningDate = userInfo.getJoiningDate();
        while (joiningDate.getYear() < LocalDate.now().getYear() || (joiningDate.getYear() == LocalDate.now().getYear() && joiningDate.getMonthValue() < LocalDate.now().getMonthValue())) {
            final int monthVal = joiningDate.getMonthValue();
            final int yearVal = joiningDate.getYear();
            EmpSalary salary = disbursedSalaries.stream().filter(item -> item.getSMonth() == monthVal && item.getSYear() == yearVal).findFirst().orElse(EmpSalary.builder().disbursed(false).userId(empId).sMonth(monthVal).sYear(yearVal).sAmount(salaryAmount).build());
            allSalaries.add(salary);
            joiningDate = joiningDate.plusMonths(1);
        }
        allSalaries.sort(Comparator.comparingInt(EmpSalary::getSYear).thenComparingInt(EmpSalary::getSMonth).reversed());
        return allSalaries;
    }

}
