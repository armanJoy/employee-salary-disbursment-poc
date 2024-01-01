package com.ibcs.salaryapp.service.usermgt;

import com.ibcs.salaryapp.model.domain.user.UserRole;
import com.ibcs.salaryapp.model.domain.user.UserInfo;
import com.ibcs.salaryapp.model.view.user.LoginResVm;
import com.ibcs.salaryapp.model.view.user.LoginVm;
import com.ibcs.salaryapp.model.view.user.UserVm;
import com.ibcs.salaryapp.model.view.util.ApiStatusVm;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface UserMgtService {

    ApiStatusVm createUser(UserVm userVm);

    UserVm updateEmployeeInfo(UserVm userVm);

    List<UserVm> getEmployees();

    ApiStatusVm removeUser(long userId);

    ApiStatusVm isMailExist(String email);

    LoginResVm login(LoginVm loginVm);

    ApiStatusVm logout(String token);

    UserInfo convertUserCreationVmToUsersLoginDomain(UserVm userVm);

    UserVm convertUserLoginToUserVm(UserInfo usersLogin);

    String getUserRole(long userId);

    UserRole saveUserRole(long userId, String role);

    void saveContext(String uid, String token);

}
