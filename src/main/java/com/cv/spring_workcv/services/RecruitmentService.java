package com.cv.spring_workcv.services;

import com.cv.spring_workcv.domain.Category;
import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecruitmentService{
    List<Recruitment> getAll(Sort sort);

    Recruitment save(Recruitment recruitment);

    Page<Recruitment> getRecruitmentByCompany(Company company, Pageable pageable);

    List<Recruitment> getRecruitmentByCompany(Company company);

    Recruitment getRecruitmentById(int id);

    List<Recruitment> getRelated(Category category);

    Page<Recruitment> getList(Pageable pageable);

    List<Recruitment> getAll();

    void delete(int id);

    Page<Recruitment> findRecruitmentByTitleContaining(String keySearch,Pageable pageable);

    List<Recruitment> findRecruitmentByTitleContaining(String keySearch);

    Page<Recruitment> findRecruitmentByAddressContaining(String keySearch,Pageable pageable);

    List<Recruitment> findRecruitmentByAddressContaining(String keySearch);

    Page<Recruitment> findRecruitmentByCategory(Category category,Pageable pageable);

    List<Recruitment> findRecruitmentByCategory(Category category);
}
