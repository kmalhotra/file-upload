package com.demo.fileupload;

import com.demo.fileupload.user.Role;
import com.demo.fileupload.user.User;
import com.demo.fileupload.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
public class ApplicationInitialiser {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Bean
    public void initialiseData() {
        String firstUserEmail = "fileupload1@demo.com";
        User firstUser = userRepository.findByEmailAndEnabled(firstUserEmail, true);

        if (null == firstUser) {
            String passoword = passwordEncoder.encode("secretPassword");
            userRepository.save(new User(firstUserEmail, passoword, new HashSet<Role>() {{
                add(Role.ROLE_USER);
            }}));
        }
    }
}
