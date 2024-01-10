package com.ibcs.salaryapp.model.view.salary;

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

    private double salary;

    private int month;

    private int year;

    private boolean disbursed;

    public EmployeeSalaryVm() {
    }

}
