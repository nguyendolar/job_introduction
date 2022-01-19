package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.FollowCompany;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.SaveJob;
import com.cv.spring_workcv.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SaveJobRepository extends PagingAndSortingRepository<SaveJob, Integer> {

    List<SaveJob> findSaveJobByRecruitment(Recruitment recruitment);

    SaveJob save(SaveJob saveJob);

    SaveJob findSaveJobByUserAndRecruitment(User user,Recruitment recruitment);

    List<SaveJob> findSaveJobByUser(User user);

    Page<SaveJob> findSaveJobByUser(User user, Pageable pageable);

    void deleteById(int id);

}
