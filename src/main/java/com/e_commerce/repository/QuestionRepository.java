package com.e_commerce.repository;

import com.e_commerce.entity.Course;
import com.e_commerce.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Questions, Long> {

	List<Questions> findByCourse(Course course);
}
