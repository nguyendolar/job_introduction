package com.cv.spring_workcv.services.impl;

import com.cv.spring_workcv.domain.Category;
import com.cv.spring_workcv.repository.CategoryRepository;
import com.cv.spring_workcv.services.CatergoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CatergoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll(Sort sort) {
        try {
            return categoryRepository.findAll(sort);
        } catch (Exception e) {
            log.error("Error at [getAll]", e);
        }
        return null;
    }

    @Override
    public Category getCategoryById(int id) {
        try {
            return categoryRepository.findCategoryById(id);
        } catch (Exception e) {
            log.error("Error at [getCategoryById]", e);
        }
        return null;
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
