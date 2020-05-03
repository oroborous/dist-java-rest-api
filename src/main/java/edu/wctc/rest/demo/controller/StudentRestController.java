package edu.wctc.rest.demo.controller;

import edu.wctc.DateUtils;
import edu.wctc.rest.demo.StudentNotFoundException;
import edu.wctc.rest.demo.entity.Student;
import edu.wctc.rest.demo.StudentErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> studentList;

    // @PostConstruct called only once, after this bean (StudentRestController) is constructed
    // Need Common Annotations API if using Java 9 or higher
    @PostConstruct
    public void loadData() {
        // Create empty list
        studentList = new ArrayList<>();

        // Manually create some students
        studentList.add(new Student(101, "Stacy", "Read", "sread@wctc.edu", 4.0, DateUtils.parseWebDate("2015-07-01")));
        studentList.add(new Student(102, "Gigi", "Read", "gread@wctc.edu", 3.79, DateUtils.parseWebDate("2018-09-05")));
        studentList.add(new Student(103, "Hello", "Kitty", "hkitty@wctc.edu", 3.24, DateUtils.parseWebDate("2019-01-01")));
    }

    @GetMapping("/students")
    // defines endpoint for "/students" - return list of students
    public List<Student> getStudents() {
        return studentList;
    }

    @GetMapping("/students/{studentId}")
    // defines endpoint for "/students/{studentId} - return student by ID
    // Path variable must match parameter name by default
    public Student getStudent(@PathVariable int studentId) {
        for (Student student : studentList) {
            if (student.getId() == studentId) {
                return student;
            }
        }

        // return null;
        throw new StudentNotFoundException("Student ID not found: " + studentId);
    }

    @ExceptionHandler
    // handles the exception that is thrown by getStudent when an invalid ID
    // is requested
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exception) {
        StudentErrorResponse error = new StudentErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    // handles any other exceptions that are thrown by this controller, such as an
    // alphanumeric student ID that can't be parsed to an int
    public ResponseEntity<StudentErrorResponse> handleException(Exception exception) {
        StudentErrorResponse error = new StudentErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getMessage());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
