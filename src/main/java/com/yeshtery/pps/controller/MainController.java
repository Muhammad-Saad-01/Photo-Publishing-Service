package com.yeshtery.pps.controller;

import com.yeshtery.pps.controller.appuser.RegistrationController;
import com.yeshtery.pps.data.dto.UserDto;
import com.yeshtery.pps.service.PhotosService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MainController {
    private final Log logger = LogFactory.getLog(RegistrationController.class);
    private PhotosService photoService;
    @GetMapping({"/index", "/"})
    public ModelAndView index() {
        logger.info("/  :: index");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login( ) {

        logger.info("/login  :: sign-in");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");

        return modelAndView;
    }
    @GetMapping("/register")
    public String registration(Model model) {
        logger.info("/register");
        UserDto user = new UserDto();

        model.addAttribute("user", user);
        return "registration";
    }


    @GetMapping("/home")
    public ModelAndView home() {
        logger.info("/home");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView admin() {
        logger.info("/admin");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        return modelAndView;

    }
    @GetMapping("/admin/all")
    public String listPhotos(Model model) {
        model.addAttribute("photos", photoService.getAllPhotos());
        return "admin";
    }
    @GetMapping("/admin/pending")
    public ModelAndView pending() {
        logger.info("/admin");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pending");
        return modelAndView;
    }



}
