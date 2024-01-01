package com.ibcs.salaryapp.service.empbank;

import com.ibcs.salaryapp.model.domain.empbank.EmpBank;
import com.ibcs.salaryapp.model.view.empbank.EmpBankVm;
import com.ibcs.salaryapp.model.view.util.ApiStatusVm;
import com.ibcs.salaryapp.repository.empbank.EmpBankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmpBankServiceImpl implements EmpBankService {

    @Autowired
    EmpBankRepo empBankRepo;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ApiStatusVm addEmpBank(EmpBankVm empBankInfoVm) {
        ApiStatusVm apiStatusVm = new ApiStatusVm();
        try {
            EmpBank empBankInfo = EmpBankVm.toDomain(empBankInfoVm);
            empBankRepo.save(empBankInfo);
            apiStatusVm.setJobDone(true);
            apiStatusVm.setMsg("Added bank to employee");
        } catch (Exception e) {
            apiStatusVm.setJobDone(false);
            apiStatusVm.setMsg("Error: " + e.getMessage());
        }

        return apiStatusVm;
    }

    @Override
    public EmpBankVm updateEmpBank(EmpBankVm empBankInfoVm) {

        EmpBank empBankInfo = EmpBankVm.toDomain(empBankInfoVm);
        empBankInfo = empBankRepo.save(empBankInfo);
        return EmpBankVm.toView(empBankInfo);
    }

    @Override
    public ApiStatusVm removeEmpBank(long empBankId) {
        ApiStatusVm apiStatusVm = new ApiStatusVm();
        try {
            boolean isActive = empBankRepo.deactiveEmpBankAc(empBankId);
            apiStatusVm.setJobDone(true);
            apiStatusVm.setMsg("Removed Employee Bank Account");

        } catch (Exception e) {
            apiStatusVm.setJobDone(false);
            apiStatusVm.setMsg("Couldn't remove bank account. Error: " + e.getMessage());
        }

        return apiStatusVm;
    }

    @Override
    public ApiStatusVm removeEmpBankByUserId(long userId) {
        ApiStatusVm apiStatusVm = new ApiStatusVm();
        try {
            EmpBank removedEmpBank = empBankRepo.deactiveEmpBankAcByUserId(userId);
            apiStatusVm.setJobDone(true);
            apiStatusVm.setMsg("Removed Employee Bank Account");

        } catch (Exception e) {
            apiStatusVm.setJobDone(false);
            apiStatusVm.setMsg("Couldn't remove bank account. Error: " + e.getMessage());
        }

        return apiStatusVm;
    }

    @Override
    public EmpBankVm getEmpBank(long userId) {
        EmpBank empBank = empBankRepo.findByUserIdAndActive(userId, true);
        return (empBank != null) ? EmpBankVm.toView(empBank) : null;
    }

}
