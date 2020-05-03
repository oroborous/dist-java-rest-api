package edu.wctc.rest;

import edu.wctc.entity.Donut;
import edu.wctc.exception.DonutNotFoundException;
import edu.wctc.exception.RestErrorResponse;
import edu.wctc.rest.demo.StudentErrorResponse;
import edu.wctc.rest.demo.StudentNotFoundException;
import edu.wctc.service.DonutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class DonutRestController {
    @Autowired
    private DonutService donutService;

    // mapping for GET /api/donuts
    @GetMapping("/donuts")
    public List<Donut> getDonuts() {
        return donutService.getDonuts();
    }

    // mapping for GET /api/donuts/XXX
    @GetMapping("/donuts/{donutId}")
    public Donut getDonut(@PathVariable int donutId) {
        Donut theDonut = donutService.getDonut(donutId);
        return theDonut;
    }

    // mapping for POST /api/donuts
    @PostMapping("/donuts")
    public Donut addDonut(@RequestBody Donut theDonut) {
        // Manually set ID to 0
        // Hibernate's saveOrUpdate() will insert if ID is null or 0
        // If client included an ID, we want to ignore it (set it to 0) because
        // POST always means create
        theDonut.setId(0);

        // Send -1 (meaning none) for image id
        donutService.saveDonut(theDonut, -1);

        // Return newly created donut to the client
        // It will have its ID assigned
        return theDonut;
    }

    // mapping for PUT /api/donuts
    @PutMapping("/donuts")
    public Donut updateDonut(@RequestBody Donut theDonut) {
        // Send -1 (meaning none) for image id
        donutService.saveDonut(theDonut, -1);

        // Return newly created donut to the client
        // It will have its ID assigned
        return theDonut;
    }

    // mapping for DELETE /api/donuts/XXX
    @DeleteMapping("/donuts/{donutId}")
    public String deleteDonut(@PathVariable int donutId) {
        Donut tempDonut = donutService.getDonut(donutId);

        // throw exception if null (not found)
        if (tempDonut == null) {
            throw new DonutNotFoundException("Donut ID not found: " + donutId);
        }

        donutService.deleteDonut(donutId);

        return "Deleted donut ID: " + donutId;
    }

    @ExceptionHandler
    public ResponseEntity<RestErrorResponse> handleException(DonutNotFoundException exception) {
        RestErrorResponse error = new RestErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<RestErrorResponse> handleException(Exception exception) {
        RestErrorResponse error = new RestErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getMessage());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
