package com.yeshtery.pps.controller.appuser;

import com.yeshtery.pps.data.entity.appuser.AppUser;
import com.yeshtery.pps.data.entity.appuser.AppUserRole;
import com.yeshtery.pps.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Controller
public class AuthorizationController {

    private final Log logger = LogFactory.getLog(RegistrationController.class);

    @PostMapping("/login_success_handler")
    public String loginSuccessHandler( ) {
        logger.info("Login Success.....");
        //TODO: perform audit action

        return "/home";
    }

    @PostMapping("/login_failure_handler")
    public String loginFailureHandler() {
        //TODO: perform audit action
        logger.info("Login Failed.....");
        return "login";
    }
}
