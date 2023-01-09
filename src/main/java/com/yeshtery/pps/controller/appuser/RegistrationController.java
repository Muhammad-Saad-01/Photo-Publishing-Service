package com.yeshtery.pps.controller.appuser;

import com.yeshtery.pps.data.dto.UserDto;
import com.yeshtery.pps.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/api/v1/auth/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final Log logger = LogFactory.getLog(RegistrationController.class);
    @PostMapping()
    public String register(@ModelAttribute UserDto requestUserDto) {
        logger.info(requestUserDto.toString());
        registrationService.register(requestUserDto);
        return "redirect:/confirmation";
    }


    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        logger.info("confirmed User Token: "+token);
        registrationService.confirmToken(token);
        return "redirect:/home";
    }

}
