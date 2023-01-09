package com.yeshtery.pps.controller.photos;



import com.yeshtery.pps.data.entity.photo.PhotoCategory;
import com.yeshtery.pps.service.impl.PhotosServiceImp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@AllArgsConstructor
@Controller
public class CategoriesController {




    @GetMapping(value = "/categories")
    public String getAllCategories(Model model){
        log.info("{}:{}",getClass().getSimpleName(),"/categories");

        model.addAttribute("categories", PhotoCategory.values());

        return "/categories";
    }


}
