
package com.online_learning.controller;

import com.online_learning.model.Course;
import com.online_learning.repository.CourseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    @GetMapping("/courses")
    public List<Course> getCourses() {

        return courseRepository.getAllCourses();

    }


    @GetMapping("/course/{courseId}")
    public Course getCourse(@PathVariable int courseId) {

        return courseRepository.getCourseById(courseId);

    }


    @PostMapping("/add-course")
    public String addCourse(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam String category) {

        courseRepository.addCourse(
                title,
                description,
                price,
                category
        );

        return "Course added successfully";

    }


    @PutMapping("/update-course/{courseId}")
    public String updateCourse(
            @PathVariable int courseId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam String category) {

        courseRepository.updateCourse(
                courseId,
                title,
                description,
                price,
                category
        );

        return "Course updated successfully";

    }


    @DeleteMapping("/delete-course/{courseId}")
    public String deleteCourse(
            @PathVariable int courseId) {

        courseRepository.deleteCourse(courseId);

        return "Course deleted successfully";

    }

}

