package com.ibcs.salaryapp.model.domain.salary;

import com.ibcs.salaryapp.model.domain.util.TimeAuditor;
import com.ibcs.salaryapp.util.AppConstant;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BasicSalary")
@Getter
@Setter
public class BasicSalary extends TimeAuditor implements Serializable {

    @Id
    @Column(name = "basicSalaryId")
    private String basicSalaryId = AppConstant.BASIC_SALARY_INFO_ID;

    @Column(name = "balance", nullable = false)
    private double balance;

}
