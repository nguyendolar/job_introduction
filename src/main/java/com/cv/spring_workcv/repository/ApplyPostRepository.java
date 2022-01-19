package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.ApplyPost;
import com.cv.spring_workcv.domain.FollowCompany;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ApplyPostRepository extends PagingAndSortingRepository<ApplyPost, Integer> {

    List<ApplyPost> findApplyPostByRecruitment(Recruitment recruitment);

    ApplyPost save(ApplyPost applyPost);

    ApplyPost findApplyPostByRecruitmentAndUser(Recruitment recruitment, User user);

    List<ApplyPost> findApplyPostByUser(User user);

    Page<ApplyPost> findApplyPostByUser(User user, Pageable pageable);

    void deleteById(int id);

}
