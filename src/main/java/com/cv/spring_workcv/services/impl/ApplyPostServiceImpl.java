package com.cv.spring_workcv.services.impl;

import com.cv.spring_workcv.domain.ApplyPost;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.repository.ApplyPostRepository;
import com.cv.spring_workcv.services.ApplyPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyPostServiceImpl implements ApplyPostService {

    @Autowired
    ApplyPostRepository applyPostRepository;

    @Override
    public List<ApplyPost> getApplyPostsByRecruitment(Recruitment recruitment) {
        return applyPostRepository.findApplyPostByRecruitment(recruitment);
    }

    @Override
    public ApplyPost save(ApplyPost applyPost) {
        return applyPostRepository.save(applyPost);
    }

    @Override
    public ApplyPost findApplyPostByRecruitmentAndUser(Recruitment recruitment, User user) {
        return applyPostRepository.findApplyPostByRecruitmentAndUser(recruitment,user);
    }

    @Override
    public List<ApplyPost> findApplyPostByUser(User user) {
        return applyPostRepository.findApplyPostByUser(user);
    }

    @Override
    public Page<ApplyPost> findApplyPostByUser(User user, Pageable pageable) {
        return applyPostRepository.findApplyPostByUser(user,pageable);
    }

    @Override
    public void deleteById(int id) {
        applyPostRepository.deleteById(id);
    }
}
