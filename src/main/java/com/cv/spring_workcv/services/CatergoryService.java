package com.cv.spring_workcv.services;

import com.cv.spring_workcv.domain.Category;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CatergoryService {
    List<Category> getAll(Sort sort);

    Category getCategoryById(int id);

    Category save(Category category);
}
