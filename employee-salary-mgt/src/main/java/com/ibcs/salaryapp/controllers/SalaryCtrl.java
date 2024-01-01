package com.ibcs.salaryapp.controllers;

import com.ibcs.salaryapp.model.domain.salary.EmpSalary;
import com.ibcs.salaryapp.model.domain.salary.EmployeeSalary;
import com.ibcs.salaryapp.model.view.empbank.EmpBankVm;
import com.ibcs.salaryapp.model.view.salary.DisbursedSalaryVm;
import com.ibcs.salaryapp.model.view.user.LoginResVm;
import com.ibcs.salaryapp.model.view.user.LoginVm;
import com.ibcs.salaryapp.model.view.user.UserVm;
import com.ibcs.salaryapp.model.view.util.ApiStatusVm;
import com.ibcs.salaryapp.service.empbank.EmpBankService;
import com.ibcs.salaryapp.service.salary.SalaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ibcs.salaryapp.service.usermgt.UserMgtService;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.validation.annotation.Validated;

@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/v1/salary")
public class SalaryCtrl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SalaryService salaryService;

    @GetMapping(value = "/monthly-details")
    public ResponseEntity<List<EmployeeSalary>> getDisburseSalaryDetails(@Positive @RequestParam int month, @Positive @RequestParam int year) {
        logger.info("inside getDisburseSalaryDetails API");
        List<EmployeeSalary> employeeSalarys = salaryService.getDisburseSalaryDetails(month, year);

        return (employeeSalarys != null && employeeSalarys.size() > 0) ? new ResponseEntity(employeeSalarys, HttpStatus.OK) : new ResponseEntity(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/initiate-disburse")
    public ResponseEntity<DisbursedSalaryVm> disburseMonthlySalary(@Positive @RequestParam int month, @Positive @RequestParam int year) {
        logger.info("inside disburseMonthlySalary API");
        return new ResponseEntity(salaryService.disburseMonthlySalary(month, year), HttpStatus.OK);
    }

    @PatchMapping(value = "/basic-salary")
    public ResponseEntity<ApiStatusVm> updateBasicSalary(@Positive @RequestParam double newBasicSalary) {
        logger.info("inside updateBasicSalary API");
        return new ResponseEntity(salaryService.updateBasicSalary(newBasicSalary), HttpStatus.OK);
    }

    @PatchMapping(value = "/salary-balance")
    public ResponseEntity<ApiStatusVm> updateComSalaryAcBalance(@Positive @RequestParam double newBalance) {
        logger.info("inside updateComSalaryAcBalance API");
        return new ResponseEntity(salaryService.updateComSalaryAcBalance(newBalance), HttpStatus.OK);
    }

//    @DeleteMapping(value = "/remove")
//    public ResponseEntity<ApiStatusVm> removeEmpBank(@PositiveOrZero @RequestParam long empBankId) {
//        logger.info("inside removeEmpBank API");
//        return new ResponseEntity(empBankService.removeEmpBank(empBankId), HttpStatus.OK);
//    }
//
//    @GetMapping(value = "")
//    public ResponseEntity<EmpBankVm> getEmpBank(@Valid @RequestParam long userId) {
//        logger.info("inside getEmpBank API");
//        EmpBankVm empBankVm = empBankService.getEmpBank(userId);
//
//        return (empBankVm != null) ? new ResponseEntity(empBankVm, HttpStatus.OK) : new ResponseEntity("No bank information found for this employee", HttpStatus.NOT_FOUND);
//    }
}