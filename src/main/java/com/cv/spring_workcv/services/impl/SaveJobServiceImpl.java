package com.cv.spring_workcv.services.impl;

import com.cv.spring_workcv.domain.ApplyPost;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.SaveJob;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.repository.ApplyPostRepository;
import com.cv.spring_workcv.repository.SaveJobRepository;
import com.cv.spring_workcv.services.ApplyPostService;
import com.cv.spring_workcv.services.SaveJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SaveJobServiceImpl implements SaveJobService {

    @Autowired
    SaveJobRepository saveJobRepository;

    @Override
    public List<SaveJob> findSaveJobByRecruitment(Recruitment recruitment) {
        return saveJobRepository.findSaveJobByRecruitment(recruitment);
    }

    @Override
    public SaveJob save(SaveJob saveJob) {
        return saveJobRepository.save(saveJob);
    }

    @Override
    public SaveJob findSaveJobByUserAndRecruitment(User user, Recruitment recruitment) {
        return saveJobRepository.findSaveJobByUserAndRecruitment(user,recruitment);
    }

    @Override
    public List<SaveJob> findSaveJobByUser(User user) {
        return saveJobRepository.findSaveJobByUser(user);
    }

    @Override
    public Page<SaveJob> findSaveJobByUser(User user, Pageable pageable) {
        return saveJobRepository.findSaveJobByUser(user,pageable);
    }

    @Override
    public void deleteById(int id) {
        saveJobRepository.deleteById(id);
    }
}
