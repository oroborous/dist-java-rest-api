package edu.wctc.rest.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private double gpa;

    private Address address;
    private LocalDate dateEnrolled;
    private List<String> languages;


    public Student(int id, String firstName, String lastName, String email, double gpa, LocalDate dateEnrolled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gpa = gpa;
        this.dateEnrolled = dateEnrolled;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", gpa=" + gpa +
                ", dateEnrolled=" + dateEnrolled +
                ", languages=" + languages +
                '}';
    }
}
