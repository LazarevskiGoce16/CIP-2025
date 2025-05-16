package com.example.service;

import com.example.model.Course;
import com.example.model.Grade;
import com.example.model.Student;
import com.example.repository.CourseRepository;
import com.example.repository.GradeRepository;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GradeRepository gradeRepository;

    public List<Student>  getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found!"));
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentByCourseId(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);

        if (optionalCourse.isPresent()) {
            return new ArrayList<>(optionalCourse.get().getStudents());
        } else {
            return new ArrayList<>();
        }
    }

    public Student addStudentToCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found!"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found!"));

        student.getCourses().add(course);
        course.getStudents().add(student);

        courseRepository.save(course);
        return studentRepository.save(student);
    }

    public Student removeStudentFromCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found!"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found!"));

        student.getCourses().remove(course);
        course.getStudents().remove(student);

        courseRepository.save(course);
        return studentRepository.save(student);
    }

    public Grade addGrade(Long courseId, Long studentId, Double gradeValue) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found!"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found!"));

        Grade grade = new Grade(gradeValue, student, course);
        return gradeRepository.save(grade);
    }

    public Double getAverageGrade(Long courseId) {
        List<Grade> grades = gradeRepository.findByCourseId(courseId);
        return grades.stream().collect(Collectors.averagingDouble(grade -> grade.getGrade())); // getGradeValue()
    }
}
