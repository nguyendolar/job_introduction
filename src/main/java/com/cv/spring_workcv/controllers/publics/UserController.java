package com.cv.spring_workcv.controllers.publics;

import com.cloudinary.Cloudinary;
import com.cv.spring_workcv.constant.CommonConstants;
import com.cv.spring_workcv.domain.*;
import com.cv.spring_workcv.services.CompanyService;
import com.cv.spring_workcv.services.CvService;
import com.cv.spring_workcv.services.RoleService;
import com.cv.spring_workcv.services.UserService;
import com.cv.spring_workcv.utils.FileUtil;
import com.cv.spring_workcv.utils.MailUtil;
import com.cv.spring_workcv.utils.Middleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("user")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    CvService cvService;

    @Autowired
    CompanyService companyService;

    @Autowired
    RoleService roleService;


    @Autowired
    public JavaMailSenderImpl javaMailSenderImpl;

    @GetMapping({"/profile/{id}" })
    public ModelAndView profile(@PathVariable int id,HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView();
        boolean auth = Middleware.middleware(request);
        if (auth) {
            User user = userService.getUserById(id);
            Company company = companyService.getCompanyByUser(user);
            Cv cv = cvService.getFile(user);
            mv = new ModelAndView("public/profile");
            mv.addObject("userInformation",user);
            mv.addObject("companyInformation",company);
            mv.addObject("Cv",cv);
        } else {
            mv = new ModelAndView("redirect:/auth/login");
        }

        return mv;
    }

    @PostMapping("/confirm-account")
    public  ModelAndView comfirm(HttpServletRequest request, RedirectAttributes rd){
        String email = request.getParameter("email");
        User user = userService.checkEmailExist(email);
        String link = "http://localhost:8080/user/confirm/" + email;
        String html = "<div  class=\"container-fluid\" style=\"text-align: center\">\n" +
                "    <p style=\"font-size: 20px;font-weight: bold;color: #aaa;margin-top: 10px\">Confirm email login</p>\n" +
                "    <div style=\"width: 600px;height: 400px;border-radius: 5px;\n" +
                "    box-shadow: rgba(0, 0, 0, 0.4) 0px 0px 10px;margin: 20px auto;padding: 15px\">\n" +
                "        <p style=\"line-height: 35px;font-size: 16px\">Xin chào, <span style=\"font-weight: bold;color: black\" >"+user.getFullName()+"</span><br>\n" +
                "        <div style=\"height: 50px ;width: 100px;background-color: #7d90f6;\n" +
                "            border-radius: 5px;line-height:50px;padding-left:50px;margin: 10px auto;display: flex\">\n" +
                "            <a href="+link+" style=\"color: white;text-decoration: none\">Confirm</a>\n" +
                "        </div>\n" +
                "\n" +
                "        <p>Contact WorkCV:<br></p>\n" +
                "            - Phone number:<span style=\"color:#5f80ec\">(024) 6680 5588</span><br>\n" +
                "            - Email: <a href=\"#\" style=\"color:#5f80ec\"> hotro@workcv.vn</a>\n" +
                "    </div>\n" +
                "</div>";

        try {
            MailUtil.sendHtmlMail(this.javaMailSenderImpl,email,"Bấm vào đường dẫn để xác thực tài khoản",html);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        String url = "redirect:profile/" + user.getId();
        rd.addFlashAttribute(CommonConstants.CONFIRM_AWAIT,
                messageSource.getMessage("confirm_await", null, Locale.getDefault()));
        rd.addFlashAttribute(CommonConstants.CONFIRM_ACCOUNT,
                messageSource.getMessage("confirm_success", null, Locale.getDefault()));
        ModelAndView mv = new ModelAndView(url);
        return mv;
    }

    @GetMapping("/confirm/{email}")
    public ModelAndView confirmAccount(@PathVariable String email){
        ModelAndView mv = new ModelAndView();
        User user = userService.checkEmailExist(email);
        if (user.getStatus() == 1) {
            mv = new ModelAndView("redirect:/");
        } else {
            user.setStatus(1);
            userService.save(user);
            String url = "redirect:/user/profile/" + user.getId();
            mv = new ModelAndView(url);
        }
        return mv;
    }

    @PostMapping("/upload")
    public @ResponseBody String upload(@RequestParam("file") MultipartFile image,HttpServletRequest request){
        Map config = new HashMap();
        String email = request.getParameter("email");
        User user = userService.checkEmailExist(email);
        config.put("resource_type","auto");

        if(image.isEmpty()) {
            return "Error";
        } else {
            try {
                Map r = this.cloudinary.uploader().upload(image.getBytes(), config);
                System.out.println(r);
                String nameImage = (String) r.get("secure_url");
                user.setImage(nameImage);
                userService.save(user);
                return nameImage;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @PostMapping("/upload-company")
    public @ResponseBody String uploadCompany(@RequestParam("file") MultipartFile image,HttpServletRequest request){
        Map config = new HashMap();
        String email = request.getParameter("email");
        User user = userService.checkEmailExist(email);
        config.put("resource_type","auto");
        Company company1 = companyService.getCompanyByUser(user);
        if(image.isEmpty()) {
            return "Error";
        } else {
            try {
                    Map r = this.cloudinary.uploader().upload(image.getBytes(), config);
                    System.out.println(r);
                    String nameImage = (String) r.get("secure_url");
                    company1.setLogo(nameImage);
                    companyService.save(company1);
                    return nameImage;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @PostMapping("/update-profile")
    public ModelAndView updateProfile(@ModelAttribute("user") User user, RedirectAttributes rd){
        User userCheck = userService.checkEmailExist(user.getEmail());
        user.setId(userCheck.getId());
        user.setPassword(userCheck.getPassword());
        user.setRole(userCheck.getRole());
        user.setStatus(userCheck.getStatus());
        user.setImage(userCheck.getImage());
        String url = "redirect:profile/" + userCheck.getId();
        userService.save(user);
        rd.addFlashAttribute(CommonConstants.SUCCESS,
                messageSource.getMessage("update_success", null, Locale.getDefault()));
        ModelAndView mv = new ModelAndView(url);
        return mv;
    }

    @PostMapping("/uploadCv")
    public @ResponseBody String uploadCv(@RequestParam("file") MultipartFile file,HttpServletRequest request,RedirectAttributes rd){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
        String fileName = file.getOriginalFilename().toUpperCase();
        System.out.println(file.getOriginalFilename());
        boolean extension = fileName.endsWith(".PDF");
        System.out.println(extension);
        if(extension){
            String name =  FileUtil.uploadPdf(request,file);
            Cv check = cvService.getFile(user);
            if (Objects.isNull(check)) {
                Cv cv = new Cv();
                cv.setUser(user);
                cv.setFileName(name);
                cvService.save(cv);
                Cv cv1 = cvService.lastCv();
                System.out.println(cv1.getFileName());
                user.setCv(cv1);
                user.setStatus(1);
                userService.save(user);
            } else {
                check.setFileName(name);
                cvService.save(check);
            }
            return name;
        }else{
            return "false";
        }
    }

    @PostMapping("/deleteCv")
    public ModelAndView deleteCv(HttpServletRequest request,RedirectAttributes rd){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
        String id = request.getParameter("idCv");
        cvService.deleteCvByUser(user);
        user.setCv(null);
        userService.save(user);
        rd.addFlashAttribute(CommonConstants.SUCCESS,
                messageSource.getMessage("delete_success", null, Locale.getDefault()));
        String url = "redirect:profile/" + user.getId();
        ModelAndView mv = new ModelAndView(url);
        return mv;
    }

    @PostMapping("/search")
    public ModelAndView search(@RequestParam("keySearch") String keySearch, HttpServletRequest request, Model model, @RequestParam("page") Optional<Integer> page){
        String url = "redirect:/user/search/" + keySearch;
        ModelAndView mv = new ModelAndView(url);
        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        Page<User> recruitments = userService.findUserByFullNameContaining(keySearch,pageable);
        List<User> recruitmentList = userService.findUserByFullNameContaining(keySearch);
        int numberPage = recruitmentList.size() / 5;
        if (recruitmentList.size() % 5 != 0){
            numberPage = numberPage +1;
        }
        List<User> recruitmentSize = recruitmentList.stream().limit(numberPage).collect(Collectors.toList());
        mv.addObject("list", recruitments);
        mv.addObject("keySearch", keySearch);
        model.addAttribute("recruitmentList", recruitmentSize);
        mv.addObject("numberPage",page.orElse(0).intValue());
        return mv;
    }

    @GetMapping("/search/{keySearch}")
    public ModelAndView getSearch(@PathVariable String keySearch, HttpServletRequest request, Model model,@RequestParam("page") Optional<Integer> page){
        ModelAndView mv = new ModelAndView("public/result-search-user");
        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        Page<User> recruitments = userService.findUserByFullNameContaining(keySearch,pageable);
        List<User> recruitmentList = userService.findUserByFullNameContaining(keySearch);
        int numberPage = recruitmentList.size() / 5;
        if (recruitmentList.size() % 5 != 0){
            numberPage = numberPage +1;
        }
        List<User> recruitmentSize = recruitmentList.stream().limit(numberPage).collect(Collectors.toList());
        mv.addObject("list", recruitments);
        mv.addObject("keySearch", keySearch);
        model.addAttribute("recruitmentList", recruitmentSize);
        mv.addObject("numberPage",page.orElse(0).intValue());
        return mv;
    }

    @GetMapping("/getCv/{idUser}")
    public ModelAndView getCv(@PathVariable int idUser,RedirectAttributes rd){
        User user = userService.getUserById(idUser);
        Cv cv = cvService.getFile(user);
        ModelAndView mv = new ModelAndView();
        if(cv != null){
            String url = "redirect:/resources/uploads/" + cv.getFileName();
            mv = new ModelAndView(url);
        }else{
            rd.addFlashAttribute(CommonConstants.SUCCESS,
                    messageSource.getMessage("delete_success", null, Locale.getDefault()));
        }
        return mv;
    }

    @GetMapping("/list-candidate")
    public ModelAndView getCandidate(HttpServletRequest request, Model model,@RequestParam("page") Optional<Integer> page){
        ModelAndView mv = new ModelAndView("public/list-user");
        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        Role role = roleService.getRoleById(1);
        Page<User> recruitments = userService.findAllByRole(role,pageable);
        List<User> recruitmentList = userService.findAllByRole(role);
        int numberPage = recruitmentList.size() / 5;
        if (recruitmentList.size() % 5 != 0){
            numberPage = numberPage +1;
        }
        List<User> recruitmentSize = recruitmentList.stream().limit(numberPage).collect(Collectors.toList());
        mv.addObject("list", recruitments);
        mv.addObject("activeUser",true);
        model.addAttribute("recruitmentList", recruitmentSize);
        mv.addObject("numberPage",page.orElse(0).intValue());
        return mv;
    }

}
