package com.demo.fileupload.authentication;

import com.demo.fileupload.authentication.ApiUserDetails;
import com.demo.fileupload.user.User;
import com.demo.fileupload.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApiUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndEnabled(username, true);
        return new ApiUserDetails(user.getUsername(), user.getPassword(), user.getAuthorities(), user.getId());
    }
}
