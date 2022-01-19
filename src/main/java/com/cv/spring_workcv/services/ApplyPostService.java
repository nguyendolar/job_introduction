package com.cv.spring_workcv.services;

import com.cv.spring_workcv.domain.ApplyPost;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApplyPostService {

    List<ApplyPost> getApplyPostsByRecruitment(Recruitment recruitment);

    ApplyPost save(ApplyPost applyPost);

    ApplyPost findApplyPostByRecruitmentAndUser(Recruitment recruitment, User user);

    List<ApplyPost> findApplyPostByUser(User user);

    Page<ApplyPost> findApplyPostByUser(User user, Pageable pageable);

    void deleteById(int id);
}
