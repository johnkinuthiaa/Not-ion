package com.slippery.notion.service;

import com.slippery.notion.models.UserPrincipal;
import com.slippery.notion.models.Users;
import com.slippery.notion.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user =userRepository.findByUsername(username);
        if(user ==null){
            throw new UsernameNotFoundException("User was not found!");
        }
        return new UserPrincipal(user);
    }
}
