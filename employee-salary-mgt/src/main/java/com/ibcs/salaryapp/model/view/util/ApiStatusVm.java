package com.ibcs.salaryapp.model.view.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiStatusVm {

    private String msg = "";

    private boolean jobDone = false;

    public ApiStatusVm() {
    }

}
