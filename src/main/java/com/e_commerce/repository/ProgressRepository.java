package com.e_commerce.repository;

import com.e_commerce.entity.Course;
import com.e_commerce.entity.Progress;
import com.e_commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

	Progress findByUserAndCourse(User user, Course course);
}
