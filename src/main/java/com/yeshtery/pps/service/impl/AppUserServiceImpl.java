package com.yeshtery.pps.service.impl;

import com.yeshtery.pps.data.entity.appuser.AppUser;
import com.yeshtery.pps.data.entity.appuser.ConfirmationToken;
import com.yeshtery.pps.data.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ConfirmationTokenServiceImpl confirmationTokenServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser) {
        boolean isUserExist = appUserRepository.findByEmail(appUser.getEmail()).isPresent();

        if (isUserExist) {
//            throw new IllegalStateException("this email already token!");
            return "this email already token!";
        }


        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        ConfirmationToken confirmationToken = new ConfirmationToken(
                appUser
        );
        confirmationTokenServiceImpl.saveConfirmationToken(confirmationToken);
        // TODO: SEND EMAIL

        return confirmationToken.getToken();
    }


    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
