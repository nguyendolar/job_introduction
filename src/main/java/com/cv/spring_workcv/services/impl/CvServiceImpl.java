package com.cv.spring_workcv.services.impl;

import com.cv.spring_workcv.domain.Cv;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.repository.CvRepository;
import com.cv.spring_workcv.services.CvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CvServiceImpl implements CvService {

    @Autowired
    CvRepository cvRepository;

    @Override
    public Cv saveFile(MultipartFile file, User user) {
        String docName = file.getOriginalFilename();
        try {
//            Cv cv = new Cv(docName, file.getContentType(), file.getBytes(),user);
            Cv cv = new Cv();
            return cvRepository.save(cv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Cv save(Cv cv) {
        return cvRepository.save(cv);
    }

    @Override
    public Cv getFile(User user) {
        return cvRepository.findCvByUser(user);
    }

    @Override
    public Cv getFileById(int id) {
        return cvRepository.findCvById(id);
    }

    @Override
    public Cv getCvbyFileName(String fileName) {
        return cvRepository.findByFileName(fileName);
    }

    @Override
    public Cv lastCv() {
        return cvRepository.lastCv();
    }

    @Override
    public void deleteCv(int id) {
        cvRepository.deleteById(id);
    }

    @Override
    public void deleteCvByUser(User user) {
        cvRepository.deleteCvByUser(user);
    }
}
