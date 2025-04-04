package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = new Student("Student", 21, null,
                LocalDate.now().minusYears(20).plusDays(15),
                List.of("Course1", "Course2"),
                Map.of("Course1", 2d, "Course2", 3d));

        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        objectMapper.registerModule(module);

        String json = objectMapper.writeValueAsString(student);
        System.out.println("Serialized student: " + json);

        Student deserializedStudent = objectMapper.readValue(json, Student.class);
        System.out.println("Deserialized student name: " + deserializedStudent.getName());
        System.out.println("Deserialized student age: " + deserializedStudent.getAge());
        System.out.println("Deserialized student email: " + deserializedStudent.getEmail());
        deserializedStudent.getCourses().forEach(System.out::println);
        deserializedStudent.getGrades().forEach((key, value) -> {
            System.out.println("Course name: " + key + ", grade: " + value);
        });
        System.out.println("Deserialized b-day: " + deserializedStudent.getBirthday());
    }
}