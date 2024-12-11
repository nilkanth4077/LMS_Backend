package com.e_commerce.controller;

import com.e_commerce.dto.*;
import com.e_commerce.entity.*;
import com.e_commerce.repository.CourseRepository;
import com.e_commerce.repository.QuestionRepository;
import com.e_commerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CartService cartService;

    @Autowired
    private DiscussionService discussionService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private LearningService learningService;

    @Autowired
    private ProgressService progressService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/progress/{userId}/{courseId}")
    public float getProgress(@PathVariable Long userId, @PathVariable Long courseId) {
        return progressService.getProgress(userId, courseId);
    }

    @GetMapping("/course/{id}")
    public Course getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PutMapping("/progress/update-progress")
    public ResponseEntity<String> updateProgress(@RequestBody ProgressRequest request) {
        return progressService.updateProgress(request);
    }

    @PutMapping("/progress/update-duration")
    public ResponseEntity<String> updateDuration(@RequestBody ProgressRequest request) {
        return progressService.updateDuration(request);
    }

    @GetMapping("/feedback/{courseId}")
    public List<Feedback> getFeedbacksForCourse(@PathVariable Long courseId) {
        return feedbackService.getFeedbacksForCourse(courseId);
    }

    @PostMapping("/feedback/submit")
    public String submitFeedback(@RequestBody FeedbackRequest fr) {
        return feedbackService.submitFeedback(fr);
    }

    @GetMapping("/discussion/{courseId}")
    public ResponseEntity<List<Discussion>> getDiscussionsAndCourse(
            @PathVariable Long courseId
    ) {
        Course course = courseService.getCourseById(courseId);
        List<Discussion> discussions = discussionService.getDiscussionsCourse(course);
        return ResponseEntity.ok(discussions);
    }

    @PostMapping("/discussion/addMessage")
    public ResponseEntity<Discussion> createDiscussion(
            @RequestBody DiscussionRequest discussionRequest
    ) {
        System.out.println(discussionRequest.getCourse_id() + " " + discussionRequest.getName() + " " + discussionRequest.getContent());
        Course course = courseService.getCourseById(discussionRequest.getCourse_id());
        Discussion discussion = new Discussion();
        discussion.setUName(discussionRequest.getName());
        discussion.setCourse(course);
        discussion.setContent(discussionRequest.getContent());
        Discussion createdDiscussion = discussionService.createDiscussion(discussion);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscussion);
    }

    @GetMapping("/{userId}/course/{courseId}")
    public ResponseEntity<List<Assessment>> getAssessmentsByUserAndCourse(
            @PathVariable Long userId,
            @PathVariable Long courseId
    ) {
        User user = userService.getUserById(userId);
        Course course = courseService.getCourseById(courseId);

        List<Assessment> assessments = assessmentService.getAssessmentsByUserAndCourse(user, course);
        return ResponseEntity.ok(assessments);
    }

    @GetMapping("/performance/{userId}")
    public ResponseEntity<List<Assessment>> getAssessmentsByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return assessmentService.getAssessmentByUser(user);
    }

    @PostMapping("/assessment/add/{userId}/{courseId}")
    public ResponseEntity<Assessment> addAssessmentWithMarks(
            @PathVariable Long userId,
            @PathVariable Long courseId,
            @RequestBody Assessment assessment) {

        User user = userService.getUserById(userId);
        Course course = courseService.getCourseById(courseId);
        return assessmentService.saveAssessment(user, course, assessment);
    }

    @GetMapping("/assessment/get/{userId}/{courseId}")
    public ResponseEntity<?> getMarks(@PathVariable Long userId, @PathVariable Long courseId) {
        Integer marks = assessmentService.getMarksByUserIdAndCourseId(userId, courseId);
        if (marks != null) {
            return ResponseEntity.ok(marks);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assessment not found for the given user and course.");
        }
    }

    @PostMapping("/cart/add")
    public Cart addToCart(@RequestBody CartRequest cartRequest) {
        return cartService.addToCart(cartRequest);
    }

    @DeleteMapping("/cart/remove/{id}")
    public void removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
    }

    @GetMapping("/learning/{userId}")
    public List<Course> getLearningCourses(@PathVariable Long userId) {
        return learningService.getLearningCourses(userId);
    }

    @GetMapping("/enrollments")
    public List<Learning> getEnrollments() {
        return learningService.getEnrollments();
    }

    @PostMapping("/course/enroll")
    public String enrollCourse(@RequestBody EnrollRequest enrollRequest) {
        System.out.println(enrollRequest.getCourseId() + " = " + enrollRequest.getUserId());
        return learningService.enrollCourse(enrollRequest);
    }

    @DeleteMapping("/course/unEnroll/{id}")
    public void unEnrollCourse(@PathVariable Long id) {
        learningService.unenrollCourse(id);
    }

    @GetMapping("/questions/{courseId}")
    public ResponseEntity<List<Questions>> getAllQuestionsForCourse(@PathVariable Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);

        if (course != null) {
            List<Questions> questions = questionRepository.findByCourse(course);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
