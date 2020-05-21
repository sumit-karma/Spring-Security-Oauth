package com.northout.oauthserver.security.service;


import com.northout.oauthserver.model.AuthUserDetail;
import com.northout.oauthserver.model.User;
import com.northout.oauthserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        optionalUser.orElseThrow(()-> new UsernameNotFoundException("Username or password Wrong"));

        UserDetails userDetails = new AuthUserDetail(optionalUser.get());

        /**
         * To check account validity
         */
        new AccountStatusUserDetailsChecker().check(userDetails);

        return userDetails;
    }
}
