package com.cv.spring_workcv.services;

import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.models.CompanyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {
    Company getCompanyByUser(User user);

    Company save(Company company);

    List<Object[]> getAll();

    Company findCompanyById(int id);

    List<Company> findAll();

    Page<Company> findAll(Pageable pageable);

}
