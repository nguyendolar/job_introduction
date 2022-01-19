package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.FollowCompany;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.models.CompanyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CompanyrRepository extends PagingAndSortingRepository<Company, Integer> {

    Company findCompanyByUser(User user);

    @Query(value = "SELECT a.id as 'id',a.name_company as 'name_company',a.logo as 'logo',COUNT(b.id) as 'openPosition' FROM company as a, recruitment as b WHERE a.id = b.company_id GROUP BY b.company_id ORDER BY COUNT(b.id) DESC LIMIT 5",nativeQuery = true)
    List<Object[]> getAll();

    Company findCompanyById(int id);

    Company save(Company company);

    List<Company> findAll();

    Page<Company> findAll(Pageable pageable);
}
