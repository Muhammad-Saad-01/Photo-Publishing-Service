package com.yeshtery.pps;

import com.yeshtery.pps.data.entity.appuser.AppUser;
import com.yeshtery.pps.data.entity.appuser.AppUserRole;
import com.yeshtery.pps.service.impl.AppUserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class PhotoPublishingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoPublishingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(AppUserServiceImpl appUserServiceImpl) {
        AppUser admin = new AppUser(
                "Muhammad",
                "Saad",
                "adminUser",
                "muhammad-saad-01@outlook.com",
                AppUserRole.ADMIN);
        admin.setEnabled(true);
        AppUser user = new AppUser(
                "Muhammad",
                "Saad",
                "appUser",
                "hello@muhammad-saad.net",
                AppUserRole.USER);
        user.setEnabled(true);

        return args -> {
            appUserServiceImpl.signUpUser(admin);
            appUserServiceImpl.signUpUser(user);
        };
    }
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.yeshtery.pps")).build();
    }
}
