package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.FollowCompany;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.Role;
import com.cv.spring_workcv.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    List<User> findAll();

    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email,String password);

    User findUserById(int id);

    Page<User> findUserByFullNameContaining(String keySearch, Pageable pageable);

    List<User> findUserByFullNameContaining(String keySearch);

    List<User> findAllByRole(Role role);

    Page<User> findAllByRole(Role role,Pageable pageable);
}
