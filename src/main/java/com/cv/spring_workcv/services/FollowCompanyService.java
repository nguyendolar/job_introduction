package com.cv.spring_workcv.services;

import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.FollowCompany;
import com.cv.spring_workcv.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FollowCompanyService {
    FollowCompany save(FollowCompany followCompany);
    FollowCompany findFollowCompanyByCompanyAndUser(Company company, User user);
    List<FollowCompany> findFollowCompanyByUser(User user);
    Page<FollowCompany> findFollowCompanyByUser(User user, Pageable pageable);
    void deleteById(int id);
}
