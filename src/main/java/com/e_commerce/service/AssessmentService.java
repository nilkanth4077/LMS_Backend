package com.e_commerce.service;

import com.e_commerce.entity.Assessment;
import com.e_commerce.entity.Course;
import com.e_commerce.entity.User;
import com.e_commerce.repository.AssessmentRepository;
import com.e_commerce.repository.CourseRepository;
import com.e_commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AssessmentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    public List<Assessment> getAssessmentsByUserAndCourse(User user, Course course) {
        return assessmentRepository.findByUserAndCourse(user, course);
    }

    public ResponseEntity<List<Assessment>> getAssessmentByUser(User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assessmentRepository.findByUser(user));
    }

    public Assessment createAssessment(Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    public void addMarks(Assessment assessment, int marks) {
        assessment.setMarks(marks);
    }

    public ResponseEntity<Assessment> saveAssessment(User user, Course course, Assessment assessment) {
        List<Assessment> existingAssessments = getAssessmentsByUserAndCourse(user, course);
        if (!existingAssessments.isEmpty()) {
            Assessment existingAssessment = existingAssessments.get(0);
            int newMarks = assessment.getMarks();

            System.out.println("Existing Marks: " + existingAssessment.getMarks());
            System.out.println("New Marks: " + newMarks);

            if (newMarks > existingAssessment.getMarks()) {
                addMarks(existingAssessment, newMarks);
                Assessment updatedAssessment = createAssessment(existingAssessment);
                return ResponseEntity.status(HttpStatus.CREATED).body(updatedAssessment);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } else {
            assessment.setUser(user);
            assessment.setCourse(course);
            Assessment savedAssessment = createAssessment(assessment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAssessment);
        }
    }

    public Integer getMarksByUserIdAndCourseId(Long userId, Long courseId) {
        return assessmentRepository.findMarksByUserIdAndCourseId(userId, courseId);
    }
}
