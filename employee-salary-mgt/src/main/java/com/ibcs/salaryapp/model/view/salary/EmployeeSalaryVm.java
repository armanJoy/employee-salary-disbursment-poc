package com.ibcs.salaryapp.model.view.salary;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeSalaryVm {

    private long id;

    private long bankId;

    private String firstName;

    private String lastName;

    private int rank;

    private LocalDate joiningDate;

    private double salary;

    private double bonusPer = 0.0;

    private int month;

    private int year;

    private boolean disbursed;

    public EmployeeSalaryVm() {
    }

}
