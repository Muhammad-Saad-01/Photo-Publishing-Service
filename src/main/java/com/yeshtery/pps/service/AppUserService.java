package com.yeshtery.pps.service;

import com.yeshtery.pps.data.entity.appuser.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AppUserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    String signUpUser(AppUser appUser);
    int enableAppUser(String email);
}
