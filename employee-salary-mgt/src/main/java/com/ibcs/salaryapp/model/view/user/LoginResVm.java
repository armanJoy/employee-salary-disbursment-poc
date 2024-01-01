package com.ibcs.salaryapp.model.view.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResVm {

    private long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String UserRole;
    private String token;
    private String msg = "";
    private boolean status = true;

}
