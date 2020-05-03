package edu.wctc.rest.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.wctc.entity.Donut;
import edu.wctc.rest.demo.entity.Address;
import edu.wctc.rest.demo.entity.Book;
import edu.wctc.rest.demo.entity.Student;

import java.io.File;
import java.io.IOException;

public class RestDemoApplication {
    private ObjectMapper mapper;

    public RestDemoApplication() {
        // Create Jackson JSON mapper
        ObjectMapper mapper = new ObjectMapper();

        // Enable pretty-print output
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Support for Java 8 temporal classes like LocalDate
        mapper.registerModule(new JavaTimeModule());
    }

    public static void main(String[] args) throws IOException {
        new RestDemoApplication().readWriteStudent();
    }

    public void readWriteAddress() throws IOException {
        File input = new File("rest-data/address-in.json");

        Address anAddress = mapper.readValue(input, Address.class);

        System.out.println(anAddress);

        File output = new File("rest-data/address-out.json");

        mapper.writeValue(output, anAddress);
    }

    public void readWriteBook() throws IOException {
        File input = new File("rest-data/book-in.json");

        Book aBook = mapper.readValue(input, Book.class);

        System.out.println(aBook);

        File output = new File("rest-data/book-out.json");

        mapper.writeValue(output, aBook);
    }

    public void readWriteDonut() throws IOException {
        File input = new File("rest-data/donut-in.json");

        Donut aDonut = mapper.readValue(input, Donut.class);

        System.out.println(aDonut);

        File output = new File("rest-data/donut-out.json");

        mapper.writeValue(output, aDonut);
    }

    public void readWriteStudent() throws IOException {
        File input = new File("rest-data/student-in.json");

        Student aStudent = mapper.readValue(input, Student.class);

        System.out.println(aStudent);

        File output = new File("rest-data/student-out.json");

        mapper.writeValue(output, aStudent);
    }
}
