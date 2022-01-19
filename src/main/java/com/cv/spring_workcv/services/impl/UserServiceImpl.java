package com.cv.spring_workcv.services.impl;

import com.cv.spring_workcv.domain.Role;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.repository.UserRepository;
import com.cv.spring_workcv.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAll() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            log.error("Error at [getAll]", e);
        }
        return null;
    }

    @Override
    public User save(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error at [save]", e);
        }
        return null;
    }

    @Override
    public User checkEmailExist(String email) {
        try {
            return userRepository.findUserByEmail(email);
        } catch (Exception e) {
            log.error("Error at [save]", e);
        }
        return null;
    }

    @Override
    public User checkLogin(String email, String password) {
        try {
            return userRepository.findUserByEmailAndPassword(email,password);
        } catch (Exception e) {
            log.error("Error at [checkLogin]", e);
        }
        return null;
    }

    @Override
    public User getUserById(int id) {
        try {
            return userRepository.findUserById(id);
        } catch (Exception e) {
            log.error("Error at [checkLogin]", e);
        }
        return null;
    }

    @Override
    public Page<User> findUserByFullNameContaining(String keySearch, Pageable pageable) {
        return userRepository.findUserByFullNameContaining(keySearch,pageable);
    }

    @Override
    public List<User> findUserByFullNameContaining(String keySearch) {
        return userRepository.findUserByFullNameContaining(keySearch);
    }

    @Override
    public List<User> findAllByRole(Role role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public Page<User> findAllByRole(Role role, Pageable pageable) {
        return userRepository.findAllByRole(role,pageable);
    }
}
