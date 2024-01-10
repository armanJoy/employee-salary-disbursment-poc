package com.ibcs.salaryapp.model.view.salary;

import com.ibcs.salaryapp.model.domain.salary.EmployeeSalary;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisbursedSalaryVm {

    private String msg;

    private boolean disburseComplete;

    private double disbursedAmount;
    private double remainingBalance;

    private double undisbursedAmount;

    private List<EmployeeSalary> disbursedEmployees = new ArrayList<>();

    public DisbursedSalaryVm() {
    }

}
