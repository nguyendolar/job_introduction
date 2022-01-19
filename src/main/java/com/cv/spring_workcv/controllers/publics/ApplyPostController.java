package com.cv.spring_workcv.controllers.publics;

import com.cv.spring_workcv.constant.CommonConstants;
import com.cv.spring_workcv.domain.*;
import com.cv.spring_workcv.services.ApplyPostService;
import com.cv.spring_workcv.services.CvService;
import com.cv.spring_workcv.services.RecruitmentService;
import com.cv.spring_workcv.services.UserService;
import com.cv.spring_workcv.utils.FileUtil;
import com.cv.spring_workcv.utils.Middleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class ApplyPostController {

    @Autowired
    ApplyPostService applyPostService;

    @Autowired
    CvService cvService;

    @Autowired
    RecruitmentService recruitmentService;

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    Middleware middleware = new Middleware();

    @PostMapping("/apply-job")
    public @ResponseBody String apply(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        boolean check = middleware.middleware(request);
        if(check){
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
            System.out.println(file);
            String idRecuitement = request.getParameter("idRe");
            String text = request.getParameter("text");
            Recruitment recruitment = recruitmentService.getRecruitmentById(Integer.parseInt(idRecuitement));
            ApplyPost applyPostCheck = applyPostService.findApplyPostByRecruitmentAndUser(recruitment,user);
            String name = "";
            name =  FileUtil.uploadPdf(request,file);

            if (Objects.nonNull(applyPostCheck))  {
                return "exist";
            }else{
                ApplyPost applyPost = new ApplyPost();
                    applyPost.setUser(user);
                    applyPost.setText(text);
                    applyPost.setStatus(0);
                    applyPost.setCreatedAt(java.time.LocalDate.now().toString());
                    applyPost.setRecruitment(recruitment);
                    applyPost.setNameCv(name);
                    applyPostService.save(applyPost);
                return "true";
            }
        }else{
            return "false";
        }
    }
    @PostMapping("/apply-job1")
    public @ResponseBody String apply1(HttpServletRequest request){
        boolean check = middleware.middleware(request);
        if(check){
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
            String idRecuitement = request.getParameter("idRe");
            String text = request.getParameter("text");
            Recruitment recruitment = recruitmentService.getRecruitmentById(Integer.parseInt(idRecuitement));
            ApplyPost applyPostCheck = applyPostService.findApplyPostByRecruitmentAndUser(recruitment,user);

            if (Objects.nonNull(applyPostCheck))  {
                return "exist";
            }else{
                ApplyPost applyPost = new ApplyPost();
                    applyPost.setNameCv(user.getCv().getFileName());
                    applyPost.setUser(user);
                    applyPost.setText(text);
                    applyPost.setStatus(0);
                    applyPost.setRecruitment(recruitment);
                    applyPost.setCreatedAt(java.time.LocalDate.now().toString());
                    applyPostService.save(applyPost);
                return "true";
            }
        }else{
            return "false";
        }
    }
    @GetMapping("/approve/{idUser}/{idRe}")
    public ModelAndView approve(@PathVariable int idUser,@PathVariable int idRe, RedirectAttributes rd){
        String url = "redirect:/recruitment/detail/"+idRe;
        ModelAndView mv = new ModelAndView(url);
        User user = userService.getUserById(idUser);
        Recruitment recruitment = recruitmentService.getRecruitmentById(idRe);
        ApplyPost applyPost = applyPostService.findApplyPostByRecruitmentAndUser(recruitment,user);
        applyPost.setStatus(1);
        applyPostService.save(applyPost);
        rd.addFlashAttribute(CommonConstants.SUCCESS,
                messageSource.getMessage("login_success", null, Locale.getDefault()));
        return mv;
    }

    @GetMapping("/get-list-apply")
    public ModelAndView getList(HttpServletRequest request, Model model, @RequestParam("page") Optional<Integer> page){
        boolean check = middleware.middleware(request);
        ModelAndView mv = new ModelAndView();
        if (check){
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
            Pageable pageable = PageRequest.of(page.orElse(0), 5);
            Page<ApplyPost> saveJobs = applyPostService.findApplyPostByUser(user,pageable);
            List<ApplyPost> saveJobList = applyPostService.findApplyPostByUser(user);
            int numberPage = saveJobList.size() / 5;
            if (saveJobList.size() % 5 != 0){
                numberPage = numberPage +1;
            }
            mv = new ModelAndView("public/list-apply-job");
            List<ApplyPost> saveJobSize = saveJobList.stream().limit(numberPage).collect(Collectors.toList());
            mv.addObject("saveJobList",saveJobs);
            model.addAttribute("recruitmentList", saveJobSize);
            mv.addObject("numberPage",page.orElse(0).intValue());
        }else{
            mv = new ModelAndView("redirect:/");
        }

        return mv;
    }

    @GetMapping("/delete-apply/{id}")
    public  ModelAndView delete(@PathVariable int id, RedirectAttributes rd){
        ModelAndView mv = new ModelAndView("redirect:/user/get-list-apply");
        applyPostService.deleteById(id);
        rd.addFlashAttribute(CommonConstants.SUCCESS,
                messageSource.getMessage("login_success", null, Locale.getDefault()));
        return mv;
    }
}
