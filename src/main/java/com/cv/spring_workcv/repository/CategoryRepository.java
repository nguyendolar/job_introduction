package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    List<Category> findAll(Sort sort);

    Category findCategoryById(int id);

    Category save(Category category);
}
