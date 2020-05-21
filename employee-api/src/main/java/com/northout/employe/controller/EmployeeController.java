package com.northout.employe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @GetMapping(value = "/employees/v1/name/{name}")
    @PreAuthorize("hasAuthority('ROLE_employee')")
    public ResponseEntity<String> getName(@PathVariable String name){

        return new ResponseEntity<>(name + ", You can access this resource!", HttpStatus.OK);
    }

    @GetMapping("/employees")
    public String[] getMessages() {
        return  new String[] {"Sumit Karma", "Sachin Tendulkar", "Wasim Akram"};
    }

}
