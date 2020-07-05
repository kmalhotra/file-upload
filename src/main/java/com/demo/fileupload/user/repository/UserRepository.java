package com.demo.fileupload.user.repository;

import com.demo.fileupload.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    public User findByEmailAndEnabled(String email, Boolean enabled);
}
