package com.ibcs.salaryapp.model.view.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginVm {

    @NotEmpty(message = "email is required")
    @Email
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    public String getEmail() {
        return email != null ? email.trim().toLowerCase() : email;
    }

//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//}
}
