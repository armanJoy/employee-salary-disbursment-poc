package com.ibcs.salaryapp.controllers;

import com.ibcs.salaryapp.model.view.empbank.EmpBankVm;
import com.ibcs.salaryapp.model.view.user.LoginResVm;
import com.ibcs.salaryapp.model.view.user.LoginVm;
import com.ibcs.salaryapp.model.view.user.UserVm;
import com.ibcs.salaryapp.model.view.util.ApiStatusVm;
import com.ibcs.salaryapp.service.empbank.EmpBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ibcs.salaryapp.service.usermgt.UserMgtService;
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
@RequestMapping("/v1/empbank")
public class EmpBankCtrl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EmpBankService empBankService;

    @PostMapping(value = "/add")
    public ResponseEntity<ApiStatusVm> addEmpBank(@Valid @RequestBody EmpBankVm empBankInfoVm) {
        logger.info("inside addEmpBank API");
        return new ResponseEntity(empBankService.addEmpBank(empBankInfoVm), HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<EmpBankVm> updateEmpBank(@Valid @RequestBody EmpBankVm empBankInfoVm) {
        logger.info("inside updateEmpBank API");
        return new ResponseEntity(empBankService.updateEmpBank(empBankInfoVm), HttpStatus.OK);
    }

    @DeleteMapping(value = "/remove")
    public ResponseEntity<ApiStatusVm> removeEmpBank(@PositiveOrZero @RequestParam long empBankId) {
        logger.info("inside removeEmpBank API");
        return new ResponseEntity(empBankService.removeEmpBank(empBankId), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<EmpBankVm> getEmpBank(@Valid @RequestParam long userId) {
        logger.info("inside getEmpBank API");
        EmpBankVm empBankVm = empBankService.getEmpBank(userId);

        return (empBankVm != null) ? new ResponseEntity(empBankVm, HttpStatus.OK) : new ResponseEntity("No bank information found for this employee", HttpStatus.NOT_FOUND);
    }

}
