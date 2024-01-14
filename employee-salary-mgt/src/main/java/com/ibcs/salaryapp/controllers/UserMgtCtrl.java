package com.ibcs.salaryapp.controllers;

import com.ibcs.salaryapp.model.view.user.LoginResVm;
import com.ibcs.salaryapp.model.view.user.LoginVm;
import com.ibcs.salaryapp.model.view.user.UserVm;
import com.ibcs.salaryapp.model.view.util.ApiStatusVm;
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

import org.springframework.validation.annotation.Validated;

@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/v1/user")
public class UserMgtCtrl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserMgtService userMgtService;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiStatusVm> createUser(@Valid @RequestBody UserVm userVm) {
        logger.info("inside createUser API");
        try {
            ApiStatusVm status = userMgtService.createUser(userVm);
            return new ResponseEntity(status, (status.isJobDone()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        } catch (Exception e) {

            return new ResponseEntity(new ApiStatusVm("Error occured.", false, null), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "/update")
    public ResponseEntity<ApiStatusVm> updateEmployeeInfo(@Valid @RequestBody UserVm userVm) {
        logger.info("inside updateEmployeeInfo API");
        try {
            ApiStatusVm status = userMgtService.updateEmployeeInfo(userVm);
            return new ResponseEntity(status, (status.isJobDone()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity("Error occured", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/employee")
    public UserVm getEmployeeInfo(@Positive @RequestParam long userId) {
        return userMgtService.getEmployeeInfo(userId);
    }

    @GetMapping("/employees")
    public List<UserVm> getEmployees() {
        return userMgtService.getEmployees();
    }

    @DeleteMapping(value = "/remove")
    public ResponseEntity<ApiStatusVm> removeUser(@Positive @RequestParam long userId) {
        logger.info("inside removeUser API");
        return new ResponseEntity(userMgtService.removeUser(userId), HttpStatus.OK);
    }

    @GetMapping("/isMailExist")
    public ResponseEntity<ApiStatusVm> isMailExist(@RequestParam @NotBlank @Email String email) {
        logger.info(this.getClass().getName() + " :inside isMailExist method");
        return new ResponseEntity(userMgtService.isMailExist(email), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResVm> login(@Valid @RequestBody LoginVm user) {
        logger.info(this.getClass().getName() + " :inside login method");
        LoginResVm loginResVm = new LoginResVm();
        try {
            loginResVm = userMgtService.login(user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Exception in login: " + e.getMessage());
            loginResVm.setStatus(false);
            loginResVm.setMsg("User ID or Password Incorrect");
        }
        return new ResponseEntity(loginResVm, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiStatusVm> logout(@Valid @RequestParam String token) {
        logger.info(this.getClass().getName() + " :inside logout method");
        return new ResponseEntity(userMgtService.logout(token), HttpStatus.OK);
    }

}
