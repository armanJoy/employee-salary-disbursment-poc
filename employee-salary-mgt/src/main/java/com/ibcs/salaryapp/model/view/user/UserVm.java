package com.ibcs.salaryapp.model.view.user;

import com.ibcs.salaryapp.util.AppConstant;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserVm {

    private long id;

    @Email
    @NotEmpty(message = "email is required")
    private String userEmail;

    @NotEmpty(message = "password is required")
    private String password;

    @NotEmpty(message = "first name is required")
    private String firstName;

    @NotEmpty(message = "last name is required")
    private String lastName;

    @Pattern(regexp = AppConstant.RX_COUNTRY_CODE_SPACE_NUMBER, message = "phoneNumber should be <country code><one white space><10 digit number>")
    private String phone;

    @NotEmpty(message = "address is required")
    private String address;

    @NotEmpty(message = "userType is required")
    private String userType;

    @Positive(message = "rank should be between 1 to 6")
    @Max(value = 6)
    private int rank;

    public String getUserEmail() {
        return userEmail.trim().toLowerCase();
    }

}
