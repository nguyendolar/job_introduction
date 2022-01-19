package com.cv.spring_workcv.controllers.publics;

import com.cv.spring_workcv.constant.CommonConstants;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.SaveJob;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.services.RecruitmentService;
import com.cv.spring_workcv.services.SaveJobService;
import com.cv.spring_workcv.services.UserService;
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

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("save-job")
public class SaveJobController {

    @Autowired
    SaveJobService saveJobService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    RecruitmentService recruitmentService;

    @Autowired
    UserService userService;

    Middleware middleware = new Middleware();

    @PostMapping("/save")
    public @ResponseBody String save(HttpServletRequest request){

        boolean check = middleware.middleware(request);
        if(check){
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
            String idRecuitement = request.getParameter("idRe");
            Recruitment recruitment = recruitmentService.getRecruitmentById(Integer.parseInt(idRecuitement));
            SaveJob checkSave = saveJobService.findSaveJobByUserAndRecruitment(user,recruitment);
            if (Objects.nonNull(checkSave))  {
                return "exist";
            }else{
                SaveJob saveJob = new SaveJob();
                saveJob.setUser(user);
                saveJob.setRecruitment(recruitment);
                saveJobService.save(saveJob);
                return "true";
            }
        }else{
            return "false";
        }

    }

    @GetMapping("/get-list")
    public ModelAndView getList(HttpServletRequest request, Model model, @RequestParam("page") Optional<Integer> page){
        boolean check = middleware.middleware(request);
        ModelAndView mv = new ModelAndView();
        if (check){
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
            Pageable pageable = PageRequest.of(page.orElse(0), 5);
            Page<SaveJob> saveJobs = saveJobService.findSaveJobByUser(user,pageable);
            List<SaveJob> saveJobList = saveJobService.findSaveJobByUser(user);
            int numberPage = saveJobList.size() / 5;
            if (saveJobList.size() % 5 != 0){
                numberPage = numberPage +1;
            }
            mv = new ModelAndView("public/list-save-job");
            List<SaveJob> saveJobSize = saveJobList.stream().limit(numberPage).collect(Collectors.toList());
            mv.addObject("saveJobList",saveJobs);
            model.addAttribute("recruitmentList", saveJobSize);
            mv.addObject("numberPage",page.orElse(0).intValue());
        }else{
            mv = new ModelAndView("redirect:/");
        }

        return mv;
    }

    @GetMapping("/delete/{id}")
    public  ModelAndView delete(@PathVariable int id, RedirectAttributes rd){
        ModelAndView mv = new ModelAndView("redirect:/save-job/get-list");
        saveJobService.deleteById(id);
        rd.addFlashAttribute(CommonConstants.SUCCESS,
                messageSource.getMessage("login_success", null, Locale.getDefault()));
        return mv;
    }
}
