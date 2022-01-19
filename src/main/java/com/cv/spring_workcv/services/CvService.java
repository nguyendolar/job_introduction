package com.cv.spring_workcv.services;

import com.cv.spring_workcv.domain.Cv;
import com.cv.spring_workcv.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface CvService {

    Cv saveFile(MultipartFile file, User user);

    Cv save(Cv cv);

    Cv getFile(User user);

    Cv getFileById(int id);

    Cv getCvbyFileName(String fileName);

    Cv lastCv();

    void deleteCv(int id);

    void deleteCvByUser(User user);
}
