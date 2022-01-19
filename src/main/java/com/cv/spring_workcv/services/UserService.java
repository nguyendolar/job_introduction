package com.cv.spring_workcv.services;


import com.cv.spring_workcv.domain.Role;
import com.cv.spring_workcv.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User save(User user);

    User checkEmailExist(String email);

    User checkLogin(String email,String password);

    User getUserById(int id);

    Page<User> findUserByFullNameContaining(String keySearch, Pageable pageable);

    List<User> findUserByFullNameContaining(String keySearch);

    List<User> findAllByRole(Role role);

    Page<User> findAllByRole(Role role,Pageable pageable);
}
