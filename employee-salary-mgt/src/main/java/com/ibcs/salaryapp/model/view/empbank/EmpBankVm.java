package com.ibcs.salaryapp.model.view.empbank;

import com.ibcs.salaryapp.model.domain.empbank.EmpBank;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class EmpBankVm {

    private Long empBankId;

    private long userId;

    @NotEmpty(message = "account holder name required")
    private String acName;

    @NotEmpty(message = "account number required")
    private String acNumber;

    @NotEmpty(message = "account type required")
    private String acType;

    @NotEmpty(message = "bank name required")
    private String bank;

    @NotEmpty(message = "branch name required")
    private String branch;

    @NotEmpty(message = "routing required")
    private String routing;

    public static EmpBankVm toView(EmpBank empBankInfo) {
        EmpBankVm empBankInfoVm = new EmpBankVm();
        empBankInfoVm.setEmpBankId(empBankInfo.getEmpBankId());
        empBankInfoVm.setUserId(empBankInfo.getUserId());
        empBankInfoVm.setAcName(empBankInfo.getAcName());
        empBankInfoVm.setAcNumber(empBankInfo.getAcNumber());
        empBankInfoVm.setAcType(empBankInfo.getAcType());
        empBankInfoVm.setBank(empBankInfo.getBank());
        empBankInfoVm.setBranch(empBankInfo.getBranch());
        empBankInfoVm.setRouting(empBankInfo.getRouting());
        return empBankInfoVm;
    }

    public static EmpBank toDomain(EmpBankVm empBankInfoVm) {
        EmpBank empBankInfo = new EmpBank();
        if (empBankInfoVm.getEmpBankId() != null) {
            empBankInfo.setEmpBankId(empBankInfoVm.getEmpBankId());
        }
        empBankInfo.setUserId(empBankInfoVm.getUserId());
        empBankInfo.setAcName(empBankInfoVm.getAcName());
        empBankInfo.setAcNumber(empBankInfoVm.getAcNumber());
        empBankInfo.setAcType(empBankInfoVm.getAcType());
        empBankInfo.setBank(empBankInfoVm.getBank());
        empBankInfo.setBranch(empBankInfoVm.getBranch());
        empBankInfo.setRouting(empBankInfoVm.getRouting());
        return empBankInfo;
    }

}
