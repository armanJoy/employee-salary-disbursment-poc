package com.ibcs.salaryapp.service.usermgt;

import com.ibcs.salaryapp.model.domain.user.Role;
import com.ibcs.salaryapp.model.domain.user.UserRole;
import com.ibcs.salaryapp.model.domain.user.UserInfo;
import com.ibcs.salaryapp.model.view.user.LoginResVm;
import com.ibcs.salaryapp.model.view.user.LoginVm;
import com.ibcs.salaryapp.model.view.user.UserVm;
import com.ibcs.salaryapp.model.view.util.ApiStatusVm;
import com.ibcs.salaryapp.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.ibcs.salaryapp.secuirty.config.TokenManager.generateToken;
import com.ibcs.salaryapp.secuirty.UserAuthResponse;
import com.ibcs.salaryapp.model.domain.user.UserJwtToken;
import com.ibcs.salaryapp.repository.user.UserJwtTokenRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import com.ibcs.salaryapp.repository.user.UserRoleRepo;
import com.ibcs.salaryapp.repository.user.RoleRepo;
import com.ibcs.salaryapp.repository.user.UserInfoRepo;
import com.ibcs.salaryapp.service.empbank.EmpBankService;
import java.util.List;

@Service
public class UserMgtServiceImpl implements UserMgtService {

    @Autowired
    private UserJwtTokenRepository userJwtTokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserInfoRepo userInfoRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    UserRoleRepo userRoleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmpBankService empBankService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional
    public ApiStatusVm createUser(UserVm userVm) {
        ApiStatusVm apiStatusVm = new ApiStatusVm();

        UserInfo isUserExist = userInfoRepo.findByUserEmailAndActive(userVm.getUserEmail(), true);

        if (isUserExist != null) {
            apiStatusVm.setMsg("User already exist");
            apiStatusVm.setJobDone(false);
        } else {
            UserInfo usersLogin = convertUserCreationVmToUsersLoginDomain(userVm);
            apiStatusVm.setMsg("User created");
            apiStatusVm.setJobDone(true);
            if (usersLogin != null && StringUtils.isNotBlank(usersLogin.getUserEmail())) {
                try {
                    usersLogin = userInfoRepo.save(usersLogin);
                    UserRole userRole = saveUserRole(usersLogin.getId(), userVm.getUserType());
                } catch (Exception e) {
                    logger.error("Error occured during User creation: " + e.getMessage());
                    apiStatusVm.setMsg("Error occured during User creation");
                    apiStatusVm.setJobDone(false);
                }

            }
        }

        return apiStatusVm;
    }

    @Override
    public UserVm updateEmployeeInfo(UserVm userVm) {
        UserInfo usersLogin = userInfoRepo.findById(userVm.getId());
        if (StringUtils.isNotBlank(userVm.getFirstName())) {
            usersLogin.setFirstName(userVm.getFirstName());
        }
        if (StringUtils.isNotBlank(userVm.getLastName())) {
            usersLogin.setLastName(userVm.getLastName());
        }

        if (StringUtils.isNotBlank(userVm.getPhone())) {
            usersLogin.setPhone(userVm.getPhone());
        }

        if (StringUtils.isNotBlank(userVm.getAddress())) {
            usersLogin.setAddress(userVm.getAddress());
        }

        if (StringUtils.isNotBlank(userVm.getUserType())) {
            usersLogin.setUserType(userVm.getUserType());
        }

        usersLogin.setRank(userVm.getRank());
        usersLogin = userInfoRepo.save(usersLogin);
        return convertUserLoginToUserVm(usersLogin);
    }

    @Override
    public List<UserVm> getEmployees() {
        List<UserInfo> employees = userInfoRepo.findAllByUserType(AppConstant.EMPLOYEE_ROLE);
        return employees.stream().map(item -> convertUserLoginToUserVm(item)).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public ApiStatusVm removeUser(long userId) {
        ApiStatusVm apiStatusVm = new ApiStatusVm();
        try {
            ApiStatusVm bankRemoveStatus = empBankService.removeEmpBankByUserId(userId);
            boolean isActive = userInfoRepo.deactiveUser(userId);
            apiStatusVm.setJobDone(true);
            apiStatusVm.setMsg("Removed Employee");

        } catch (Exception e) {
            apiStatusVm.setJobDone(false);
            apiStatusVm.setMsg("Couldn't remove Employee. Error: " + e.getMessage());
        }
        return apiStatusVm;
    }

    @Override
    @Transactional
    public LoginResVm login(LoginVm loginVm) {
        LoginResVm loginResVm = new LoginResVm();

        try {
            Authentication authentication = null;
            try {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginVm.getUserEmail(), loginVm.getPassword()));
            } catch (Exception e) {
                logger.info("Exception in authentication: " + e.getMessage());
            }
            if (authentication != null && authentication.isAuthenticated()) {
                final UserAuthResponse userDetails = (UserAuthResponse) authentication.getPrincipal();

//                userDetails.setDeviceId(loginVm.getDeviceId());
                final String token = generateToken(userDetails);

                saveContext(userDetails.getUsername(), token);

                // if (user != null && (passwordEncoder.matches(loginVm.getPassword(),
                // user.getPassword()) || loginVm.getPassword().equals(user.getAutoPassword())))
                // {
                UserInfo user = userDetails.getUsersLogin();
                loginResVm.setToken(token);
                loginResVm.setUserId(user.getId());
                loginResVm.setFirstName(user.getFirstName());
                loginResVm.setLastName(user.getLastName());
                loginResVm.setEmail(user.getUserEmail());
                String userRole = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));
                loginResVm.setUserRole(userRole);
                loginResVm.setMsg(AppConstant.SUCCESSFUL);
                loginResVm.setStatus(true);

            } else {

                loginResVm.setStatus(false);
                loginResVm.setMsg("User ID or Password Incorrect");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Exception in login: " + e.getMessage());
            loginResVm.setStatus(false);
            loginResVm.setMsg("User ID or Password Incorrect");
        }

        return loginResVm;
    }

    @Override
    public ApiStatusVm logout(String token) {
        ApiStatusVm apiStatusVm = new ApiStatusVm();
        try {
            String userMail = userJwtTokenRepository.removeUserJwt(token);
            apiStatusVm.setJobDone(true);
            apiStatusVm.setMsg("User: " + userMail + " logout from the system");
        } catch (Exception e) {
            apiStatusVm.setMsg("Logout unsuccessful. Error: " + e.getMessage());
        }

        return apiStatusVm;
    }

    @Override
    public UserInfo convertUserCreationVmToUsersLoginDomain(UserVm userVm) {
        UserInfo usersLogin = new UserInfo();
        usersLogin.setUserEmail(userVm.getUserEmail());
        usersLogin.setPassword(passwordEncoder.encode(userVm.getPassword()));
        usersLogin.setFirstName(userVm.getFirstName());
        usersLogin.setLastName(userVm.getLastName());
        usersLogin.setPhone(userVm.getPhone());
        usersLogin.setAddress(userVm.getAddress());
        usersLogin.setUserType(userVm.getUserType());
        usersLogin.setRank(userVm.getRank());

        return usersLogin;
    }

    @Override
    public UserVm convertUserLoginToUserVm(UserInfo usersLogin) {
        UserVm user = null;
        if (usersLogin != null) {
            user = new UserVm();
            user.setId(usersLogin.getId());
            user.setUserEmail(usersLogin.getUserEmail());
            user.setPhone(usersLogin.getPhone());
            user.setFirstName(usersLogin.getFirstName());
            user.setLastName(usersLogin.getLastName());
            user.setPhone(usersLogin.getPhone());
            user.setAddress(usersLogin.getAddress());
            user.setUserType(usersLogin.getUserType());
            user.setRank(usersLogin.getRank());
        }

        return user;
    }

    @Override
    public String getUserRole(long userId) {
        String roleName = null;
        UserRole userRole = userRoleRepo.findByUserId(userId);
        if (userRole != null) {
            Role role = roleRepo.findByRoleId(userRole.getRoleId());
            if (role != null) {
                roleName = role.getRoleName();
            }
        }
        return roleName;

    }

    @Override
    public UserRole saveUserRole(long userId, String role) {
        UserRole userRole = userRoleRepo.findByUserId(userId);

        if (userRole == null) {
            userRole = new UserRole();
            Role roleInfo = roleRepo.findByRoleName(role);
            if (roleInfo != null) {
                userRole.setRoleId(roleInfo.getRoleId());
                userRole.setUserId(userId);
                userRole = userRoleRepo.save(userRole);
            } else {
                throw new RuntimeException("Invalid role provided");
            }
        }

        return userRole;
    }

    @Override
    public ApiStatusVm isMailExist(String email) {
        email = (StringUtils.isNotBlank(email)) ? email.trim().toLowerCase() : "";

        UserInfo user = userInfoRepo.findByUserEmailAndActive(email, true);

        if (user != null) {
            return new ApiStatusVm("User exist with this email", true);
        } else {
            return new ApiStatusVm("No user with this email", false);
        }
    }

    @Override
    public void saveContext(String uid, String token) {

        UserJwtToken userJwtToken = new UserJwtToken();

        userJwtToken = userJwtTokenRepository.findByUserId(uid);

        if (userJwtToken == null) {
            userJwtToken = new UserJwtToken();
        }

        userJwtToken.setUserId(uid);
        userJwtToken.setToken(token);

        userJwtTokenRepository.save(userJwtToken);
    }

}
