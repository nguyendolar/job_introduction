package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.FollowCompany;
import com.cv.spring_workcv.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FollowCompanyRepository extends PagingAndSortingRepository<FollowCompany, Integer> {

    FollowCompany save(FollowCompany followCompany);

    FollowCompany findFollowCompanyByCompanyAndUser(Company company, User user);

    List<FollowCompany> findFollowCompanyByUser(User user);

    Page<FollowCompany> findFollowCompanyByUser(User user, Pageable pageable);

    void deleteById(int id);
}
