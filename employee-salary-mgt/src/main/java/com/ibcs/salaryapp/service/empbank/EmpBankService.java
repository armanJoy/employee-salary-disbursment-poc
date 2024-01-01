package com.ibcs.salaryapp.service.empbank;

import com.ibcs.salaryapp.model.view.empbank.EmpBankVm;
import com.ibcs.salaryapp.model.view.util.ApiStatusVm;
import org.springframework.stereotype.Service;

@Service
public interface EmpBankService {

    ApiStatusVm addEmpBank(EmpBankVm empBankInfoVm);

    EmpBankVm updateEmpBank(EmpBankVm empBankInfoVm);

    ApiStatusVm removeEmpBank(long empBankId);

    ApiStatusVm removeEmpBankByUserId(long userId);

    EmpBankVm getEmpBank(long userId);

}
