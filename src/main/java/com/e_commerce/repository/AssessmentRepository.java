package com.e_commerce.repository;

import com.e_commerce.entity.Assessment;
import com.e_commerce.entity.Course;
import com.e_commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    List<Assessment> findByUserAndCourse(User user, Course course);

    List<Assessment> findByUser(User user);

    @Query("SELECT a.marks FROM Assessment a WHERE a.user.id = :userId AND a.course.id = :courseId")
    Integer findMarksByUserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);

}
