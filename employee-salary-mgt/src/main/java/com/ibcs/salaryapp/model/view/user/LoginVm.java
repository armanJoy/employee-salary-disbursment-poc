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
    private String userEmail;

    @NotBlank(message = "password is required")
    private String password;

    public String getUserEmail() {
        return userEmail != null ? userEmail.trim().toLowerCase() : userEmail;
    }

}
