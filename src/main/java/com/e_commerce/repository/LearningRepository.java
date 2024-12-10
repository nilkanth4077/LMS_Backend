package com.e_commerce.repository;

import com.e_commerce.entity.Course;
import com.e_commerce.entity.Learning;
import com.e_commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningRepository extends JpaRepository<Learning, Long> {

	Learning findByUserAndCourse(User user, Course course);
}