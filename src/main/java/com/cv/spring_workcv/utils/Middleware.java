package com.cv.spring_workcv.utils;

import com.cv.spring_workcv.constant.CommonConstants;
import com.cv.spring_workcv.domain.User;
import org.apache.catalina.connector.RequestFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

public class Middleware {

    public  static boolean middleware( HttpServletRequest request){;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
        if (Objects.nonNull(user)) {
            return true;
        } else {
            return false;
        }
    }
}
