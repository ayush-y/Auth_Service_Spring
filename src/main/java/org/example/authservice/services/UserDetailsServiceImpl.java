package org.example.authservice.services;

import org.example.authservice.helpers.AuthUserDetails;
import org.example.authservice.models.UserAccount;
import org.example.authservice.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class is responsible for loading the user in the form of user details object for auth.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<UserAccount> userAccount = userAccountRepository.findUserByEmail(email); //email is unique identifier
        if(userAccount.isPresent()){
            return new AuthUserDetails(userAccount.get());
        }else {
            throw new UsernameNotFoundException("Can not find user by the given email"+ email);
        }
    }
}
