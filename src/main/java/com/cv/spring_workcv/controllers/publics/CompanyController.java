package com.cv.spring_workcv.controllers.publics;

import com.cv.spring_workcv.constant.CommonConstants;
import com.cv.spring_workcv.domain.*;
import com.cv.spring_workcv.services.*;
import com.cv.spring_workcv.utils.FileUtil;
import com.cv.spring_workcv.utils.Middleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("user")
public class CompanyController {

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @Autowired
    RecruitmentService recruitmentService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    CatergoryService catergoryService;

    @Autowired
    FollowCompanyService followCompanyService;

    Middleware middleware = new Middleware();

    @PostMapping("/update-company")
    public ModelAndView updateProfile(@ModelAttribute("company") Company company, HttpServletRequest request,  RedirectAttributes rd){
        String user_id = request.getParameter("user_id");
        User userCheck = userService.getUserById(Integer.parseInt(user_id));
        Company company1 = companyService.getCompanyByUser(userCheck);
        company.setUser(userCheck);
//        String nameImage = "";
//        nameImage = FileUtil.uploadPdf(request,file);
//        if (nameImage == "null") {
//            company.setLogo(company1.getLogo());
//        } else {
//            company.setLogo(nameImage);
//        }
        company.setId(company.getId());
        String url = "redirect:profile/" + user_id;
        companyService.save(company);
        rd.addFlashAttribute(CommonConstants.SUCCESS,
                messageSource.getMessage("update_success", null, Locale.getDefault()));
        ModelAndView mv = new ModelAndView(url);
        return mv;
    }

    @GetMapping("/list-post")
    public  ModelAndView getListPost(HttpServletRequest request, Model model,@RequestParam("page") Optional<Integer> page){

        ModelAndView mv = new ModelAndView();
        boolean auth = Middleware.middleware(request);
        if (auth) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
            Company company = companyService.getCompanyByUser(user);
            Sort sort = Sort.by("id").descending();
            Pageable pageable = PageRequest.of(page.orElse(0), 5, sort);
            Page<Recruitment> recruitments =  recruitmentService.getRecruitmentByCompany(company,pageable);
            List<Recruitment> recruitmentList = recruitmentService.getRecruitmentByCompany(company);
            int numberPage = recruitmentList.size() / 5;
            if (recruitmentList.size() % 5 != 0){
                numberPage = numberPage +1;
            }
            List<Recruitment> recruitmentSize = recruitmentList.stream().limit(numberPage).collect(Collectors.toList());
            model.addAttribute("list", recruitments);
            model.addAttribute("recruitmentList", recruitmentSize);
            model.addAttribute("numberPage",page.orElse(0).intValue());
            mv = new ModelAndView("public/post-list");
        } else {
            mv = new ModelAndView("redirect:/auth/login");
        }
        return mv;
    }

    @GetMapping("/detail-company/{id}")
    public ModelAndView getDetail(@PathVariable int id,HttpServletRequest request){
        ModelAndView mv = new ModelAndView("public/detail-company");
        Company company = companyService.findCompanyById(id);
        mv.addObject("company",company);
        return mv;
    }

    @GetMapping("/company-post/{id}")
    public ModelAndView getCompanyPost(@PathVariable int id,HttpServletRequest request,Model model,@RequestParam("page") Optional<Integer> page){
        ModelAndView mv = new ModelAndView();
        boolean auth = Middleware.middleware(request);
        if (auth) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
            Company company = companyService.findCompanyById(id);
            Sort sort = Sort.by("id").descending();
            Pageable pageable = PageRequest.of(page.orElse(0), 5, sort);
            Page<Recruitment> recruitments =  recruitmentService.getRecruitmentByCompany(company,pageable);
            List<Recruitment> recruitmentList = recruitmentService.getRecruitmentByCompany(company);
            int numberPage = recruitmentList.size() / 5;
            if (recruitmentList.size() % 5 != 0){
                numberPage = numberPage +1;
            }
            List<Recruitment> recruitmentSize = recruitmentList.stream().limit(numberPage).collect(Collectors.toList());
            model.addAttribute("list", recruitments);
            model.addAttribute("recruitmentList", recruitmentSize);
            model.addAttribute("company", company);
            model.addAttribute("numberPage",page.orElse(0).intValue());
            mv = new ModelAndView("public/post-company");
        } else {
            mv = new ModelAndView("redirect:/auth/login");
        }
        return mv;
    }

    @PostMapping("/follow-company")
    public @ResponseBody String save(HttpServletRequest request){

        boolean check = middleware.middleware(request);
        if(check){
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
            String idCompany = request.getParameter("idCompany");
            Company company = companyService.findCompanyById(Integer.parseInt(idCompany));
            FollowCompany checkFl = followCompanyService.findFollowCompanyByCompanyAndUser(company,user);
            if (Objects.nonNull(checkFl))  {
                return "exist";
            }else{
                FollowCompany fl = new FollowCompany();
                fl.setUser(user);
                fl.setCompany(company);
                followCompanyService.save(fl);
                return "true";
            }
        }else{
            return "false";
        }
    }

    @GetMapping("/get-list-company")
    public ModelAndView getList(HttpServletRequest request, Model model, @RequestParam("page") Optional<Integer> page){
        boolean check = middleware.middleware(request);
        ModelAndView mv = new ModelAndView();
        if (check){
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
            mv = new ModelAndView("public/list-follow-company");
            Pageable pageable = PageRequest.of(page.orElse(0), 5);
            Page<FollowCompany> saveJobs = followCompanyService.findFollowCompanyByUser(user,pageable);
            List<FollowCompany> saveJobList = followCompanyService.findFollowCompanyByUser(user);
            int numberPage = saveJobList.size() / 5;
            if (saveJobList.size() % 5 != 0){
                numberPage = numberPage +1;
            }
            List<FollowCompany> saveJobSize = saveJobList.stream().limit(numberPage).collect(Collectors.toList());
            mv.addObject("saveJobList",saveJobs);
            model.addAttribute("recruitmentList", saveJobSize);
            mv.addObject("numberPage",page.orElse(0).intValue());
        }else{
            mv = new ModelAndView("redirect:/");
        }
        return mv;
    }

    @GetMapping("/delete-follow/{id}")
    public  ModelAndView delete(@PathVariable int id, RedirectAttributes rd){
        ModelAndView mv = new ModelAndView("redirect:/user/get-list-company");
        followCompanyService.deleteById(id);
        rd.addFlashAttribute(CommonConstants.SUCCESS,
                messageSource.getMessage("login_success", null, Locale.getDefault()));
        return mv;
    }
}
