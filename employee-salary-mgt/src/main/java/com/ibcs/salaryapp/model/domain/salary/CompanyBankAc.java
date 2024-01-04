package com.ibcs.salaryapp.model.domain.salary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibcs.salaryapp.model.domain.util.TimeAuditor;
import com.ibcs.salaryapp.util.AppConstant;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CompanyBankAc")
@Getter
@Setter
public class CompanyBankAc extends TimeAuditor implements Serializable {

    @JsonIgnore
    @Id
    @Column(name = "comBankId")
    private String comBankId = AppConstant.COMPANY_BANK_AC_ID;

    @Column(name = "balance", nullable = false)
    private double balance;

    @Column(name = "acName", nullable = false, length = 80)
    private String acName;

    @Column(name = "acNumber", nullable = false, length = 20)
    private String acNumber;

    @Column(name = "acType", nullable = false, length = 20)
    private String acType;

    @Column(name = "bank", nullable = false, length = 80)
    private String bank;

    @Column(name = "branch", nullable = false, length = 80)
    private String branch;

}
