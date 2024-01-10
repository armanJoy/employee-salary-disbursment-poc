package com.ibcs.salaryapp.model.view.user;

import com.ibcs.salaryapp.model.view.empbank.EmpBankVm;
import com.ibcs.salaryapp.util.AppConstant;
import java.time.LocalDate;
import javax.validation.Valid;
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
    private String email;

    @NotEmpty(message = "password is required")
    private String password;

    @NotEmpty(message = "first name is required")
    private String firstName;

    @NotEmpty(message = "last name is required")
    private String lastName;

    @NotEmpty(message = "Gender is required")
    private String gender;

    @Pattern(regexp = AppConstant.RX_COUNTRY_CODE_SPACE_NUMBER, message = "Phone should be 11 diigit")
    private String phone;

    @NotEmpty(message = "address is required")
    private String address;

    @NotEmpty(message = "nid is required")
    @Size(min = 13, max = 17, message = "nid must be 13 or 17 digit")
    private String nid;

    @PastOrPresent(message = "joiningDate must be past or present date")
    private LocalDate joiningDate;

    @NotEmpty(message = "userType is required")
    private String userType;

    @Positive(message = "rank should be between 1 to 6")
    @Max(value = 6)
    private int rank;

    @Valid
    private EmpBankVm empBankInfo;

    public String getEmail() {
        return email.trim().toLowerCase();
    }

}
