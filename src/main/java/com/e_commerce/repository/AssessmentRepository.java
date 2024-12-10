package com.e_commerce.repository;

import com.e_commerce.entity.Assessment;
import com.e_commerce.entity.Course;
import com.e_commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    List<Assessment> findByUserAndCourse(User user, Course course);

	List<Assessment> findByUser(User user);
}
