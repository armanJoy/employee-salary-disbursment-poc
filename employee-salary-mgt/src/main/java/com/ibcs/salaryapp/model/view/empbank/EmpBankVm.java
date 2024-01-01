package com.ibcs.salaryapp.model.view.empbank;

import com.ibcs.salaryapp.model.domain.empbank.EmpBank;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class EmpBankVm {

    private Long empBankId;

    private long userId;

    private String acName;

    private String acNumber;

    private String acType;

    private String bank;

    private String branch;

    public static EmpBankVm toView(EmpBank empBankInfo) {
        EmpBankVm empBankInfoVm = new EmpBankVm();
        empBankInfoVm.setEmpBankId(empBankInfo.getEmpBankId());
        empBankInfoVm.setUserId(empBankInfo.getUserId());
        empBankInfoVm.setAcName(empBankInfo.getAcName());
        empBankInfoVm.setAcNumber(empBankInfo.getAcNumber());
        empBankInfoVm.setAcType(empBankInfo.getAcType());
        empBankInfoVm.setBank(empBankInfo.getBank());
        empBankInfoVm.setBranch(empBankInfo.getBranch());
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
        return empBankInfo;
    }

}
