package com.cv.spring_workcv.services.impl;

import com.cv.spring_workcv.domain.Category;
import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.repository.RecruitmentRepository;
import com.cv.spring_workcv.services.RecruitmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RecruitmentServiceImpl implements RecruitmentService {


    @Autowired
    RecruitmentRepository recruitmentRepository;


    @Override
    public List<Recruitment> getAll(Sort sort) {
        return recruitmentRepository.findAll(sort);
    }

    @Override
    public Recruitment save(Recruitment recruitment) {
        try {
            return recruitmentRepository.save(recruitment);
        } catch (Exception e) {
            log.error("Error at [save]", e);
        }
        return null;
    }

    @Override
    public Page<Recruitment> getRecruitmentByCompany(Company company, Pageable pageable) {
        try {
            return recruitmentRepository.findRecruitmentsByCompany(company, pageable);
        } catch (Exception e) {
            log.error("Error at [getRecruitmentByCompany]", e);
        }
        return null;
    }

    @Override
    public List<Recruitment> getRecruitmentByCompany(Company company) {
        try {
            return recruitmentRepository.findRecruitmentsByCompany(company);
        } catch (Exception e) {
            log.error("Error at [getRecruitmentByCompany]", e);
        }
        return null;
    }

    @Override
    public Recruitment getRecruitmentById(int id) {
        try {
            return recruitmentRepository.findRecruitmentById(id);
        } catch (Exception e) {
            log.error("Error at [getRecruitmentById]", e);
        }
        return null;
    }

    @Override
    public List<Recruitment> getRelated(Category category) {
        try {
            return recruitmentRepository.findRecruitmentByCategory(category);
        } catch (Exception e) {
            log.error("Error at [getRelated]", e);
        }
        return null;
    }
    public Page<Recruitment> getList(Pageable pageable) {
        try {
            return recruitmentRepository.getList(pageable);
        } catch (Exception e) {
            log.error("Error at [getRecruitment]", e);
        }
        return null;
    }

    @Override
    public List<Recruitment> getAll() {
        return recruitmentRepository.findAll();
    }

    @Override
    public void delete(int id) {
        try {
            recruitmentRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error at [save]", e);
        }
    }

    @Override
    public Page<Recruitment> findRecruitmentByTitleContaining(String keySearch, Pageable pageable) {
        return recruitmentRepository.findRecruitmentByTitleContaining(keySearch, pageable);
    }

    @Override
    public List<Recruitment> findRecruitmentByTitleContaining(String keySearch) {
        return recruitmentRepository.findRecruitmentByTitleContaining(keySearch);
    }

    @Override
    public Page<Recruitment> findRecruitmentByAddressContaining(String keySearch, Pageable pageable) {
        return recruitmentRepository.findRecruitmentByAddressContaining(keySearch, pageable);
    }

    @Override
    public List<Recruitment> findRecruitmentByAddressContaining(String keySearch) {
        return recruitmentRepository.findRecruitmentByAddressContaining(keySearch);
    }

    @Override
    public Page<Recruitment> findRecruitmentByCategory(Category category, Pageable pageable) {
        return recruitmentRepository.findRecruitmentByCategory(category, pageable);
    }

    @Override
    public List<Recruitment> findRecruitmentByCategory(Category category) {
        return recruitmentRepository.findRecruitmentByCategory(category);
    }
}
