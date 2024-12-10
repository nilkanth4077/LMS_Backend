package com.e_commerce.repository;

import com.e_commerce.entity.Course;
import com.e_commerce.entity.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    List<Discussion> findByCourse(Course course);
}
