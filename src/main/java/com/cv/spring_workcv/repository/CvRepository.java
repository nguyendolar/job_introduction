package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.Cv;
import com.cv.spring_workcv.domain.FollowCompany;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CvRepository extends JpaRepository<Cv, Integer> {
    Cv findCvByUser(User user);

    Cv findCvById(int id);

    Cv findByFileName(String fileName);

    @Transactional
    void deleteCvByUser(User user);

    @Query(value = "SELECT * FROM cv ORDER BY id DESC LIMIT 1",nativeQuery = true)
    Cv lastCv();
}
