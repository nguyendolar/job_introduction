package com.cv.spring_workcv.controllers.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * TODO: Class description
 */
@Controller
public class CustomErrorController implements ErrorController {
    private static final String PATH = "/errors";

    /**
     * TODO: Method description
     *
     * @return
     */
    @RequestMapping(value = PATH)
    public String error() {
        return "error.404";
    }
}

