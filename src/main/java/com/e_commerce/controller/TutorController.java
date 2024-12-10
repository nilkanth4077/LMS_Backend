package com.e_commerce.controller;

import com.e_commerce.dto.QuestionRequest;
import com.e_commerce.entity.Course;
import com.e_commerce.entity.Questions;
import com.e_commerce.repository.CourseRepository;
import com.e_commerce.repository.QuestionRepository;
import com.e_commerce.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutor")
@PreAuthorize("hasRole('TUTOR')")
public class TutorController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/course/update/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        return courseService.updateCourse(id, updatedCourse);
    }

    @DeleteMapping("/course/delete/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    @PostMapping("/questions/add")
    public ResponseEntity<String> addQuestion(@RequestBody QuestionRequest questionRequest) {
        Course course = courseRepository.findById(questionRequest.getCourseId()).orElse(null);

        Questions question = new Questions();
        question.setQuestion(questionRequest.getQuestion());
        question.setOption1(questionRequest.getOption1());
        question.setOption2(questionRequest.getOption2());
        question.setOption3(questionRequest.getOption3());
        question.setOption4(questionRequest.getOption4());
        question.setAnswer(questionRequest.getAnswer());
        question.setCourse(course);

        questionRepository.save(question);

        return new ResponseEntity<>("Question added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/course/add")
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

}
