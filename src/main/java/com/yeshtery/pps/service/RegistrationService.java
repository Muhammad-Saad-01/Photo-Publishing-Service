package com.yeshtery.pps.service;

import com.yeshtery.pps.data.dto.UserDto;
import com.yeshtery.pps.data.entity.appuser.AppUser;
import com.yeshtery.pps.data.entity.appuser.AppUserRole;
import com.yeshtery.pps.data.entity.appuser.ConfirmationToken;
import com.yeshtery.pps.service.impl.AppUserServiceImpl;
import com.yeshtery.pps.service.impl.ConfirmationTokenServiceImpl;
import com.yeshtery.pps.service.impl.EmailSenderServiceServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
    private AppUserServiceImpl appUserServiceImpl;
    private EmailValidatorService emailValidatorService;
    private final ConfirmationTokenServiceImpl confirmationTokenServiceImpl;
    private final EmailSenderServiceServiceImpl emailSender;

    public String register(UserDto userDtoRequest) {
        boolean isValidEmail = emailValidatorService.test(userDtoRequest.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("This not Valid Email! try again!");
        }
        String token = appUserServiceImpl.signUpUser(
                new AppUser(
                        userDtoRequest.getFirstName(),
                        userDtoRequest.getLastName(),
                        userDtoRequest.getPassword(),
                        userDtoRequest.getEmail(),
                        AppUserRole.USER

                )
        );


        emailSender.send(
                userDtoRequest.getFirstName(),
                userDtoRequest.getEmail(),
                token);

        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenServiceImpl
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenServiceImpl.setConfirmedAt(token);
        appUserServiceImpl.enableAppUser(confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }
}
