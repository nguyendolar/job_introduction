package com.cv.spring_workcv.controllers.publics;

import com.cv.spring_workcv.constant.CommonConstants;
import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.Role;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.services.CompanyService;
import com.cv.spring_workcv.services.RoleService;
import com.cv.spring_workcv.services.UserService;
import com.cv.spring_workcv.utils.EncrytedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Objects;

@Controller
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @Autowired
    RoleService roleService;

    EncrytedPasswordUtils encrytedPasswordUtils = new EncrytedPasswordUtils();

    @GetMapping({"/login","/register"})
    public ModelAndView login()
    {
        ModelAndView mv = new ModelAndView("public/login");
        return mv;
    }

    @PostMapping({"/register"})
    public ModelAndView register(@ModelAttribute("user") User user, @RequestParam("role_id") int role_id,
                                 @RequestParam("rePassword") String rePassword) {
        ModelAndView mv = new ModelAndView("public/login");
        Role role = roleService.getRoleById(role_id);
        user.setRole(role);

        if (!user.getPassword().equalsIgnoreCase(rePassword)) {
            mv.addObject(CommonConstants.MSG_REGISTER_ERROR, messageSource.getMessage("password_and_repassword", null, Locale.getDefault()));
        } else {
            String passwordMD5 = encrytedPasswordUtils.md5(user.getPassword());
            user.setPassword(passwordMD5);
            User checkExistEmail = userService.checkEmailExist(user.getEmail());
            if (!Objects.isNull(checkExistEmail)) {
                mv.addObject(CommonConstants.MSG_REGISTER_ERROR, messageSource.getMessage("email_exited", null, Locale.getDefault()));
            } else {
                user.setStatus(1);
                User userSave = userService.save(user);
                User userCompany = userService.checkEmailExist(userSave.getEmail());
                Company company = new Company();
                company.setUser(userCompany);
                companyService.save(company);
                mv.addObject(CommonConstants.MSG_REGISTER_SUCCESS, messageSource.getMessage("register_success", null, Locale.getDefault()));
            }
        }
        return mv;

    }

    @PostMapping({"/login"})
    public ModelAndView login(@ModelAttribute("user") User user, RedirectAttributes rd, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("public/login");
        String passwordMd5 = encrytedPasswordUtils.md5(user.getPassword());
        User userLogin = userService.checkLogin(user.getEmail(),passwordMd5);
        if (Objects.isNull(userLogin)) {
            mv.addObject(CommonConstants.MSG_REGISTER_ERROR, messageSource.getMessage("login_error", null, Locale.getDefault()));
        } else {
            rd.addFlashAttribute(CommonConstants.MSG_REGISTER_SUCCESS,
                    messageSource.getMessage("login_success", null, Locale.getDefault()));
            HttpSession session = request.getSession();
            session.setAttribute(CommonConstants.SESSION_USER, userLogin);
            mv.addObject("role_id",userLogin.getRole().getId());
            mv = new ModelAndView("redirect:/index");
        }
        return mv;
    }
    @GetMapping(value = "/logout")
    public ModelAndView getLogout(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("redirect:/auth/login");
        HttpSession session = request.getSession(false);
        session.invalidate();
        return mv;
    }
}
