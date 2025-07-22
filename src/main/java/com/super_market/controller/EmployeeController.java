package com.super_market.controller;

import com.super_market.model.Employee;
import com.super_market.model.LoginRequest;
import com.super_market.repository.EmployeeRepository;
import com.super_market.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/login")
    public ResponseEntity<?> loginAndShowOptions(@RequestBody LoginRequest credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPassword();

        if (employeeService.login(email, password)) {
            Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);

            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();

                Set<String> options = employeeService.getOptionsForEmployee(employee);

                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login successful!");
                response.put("employeeName", employee.getF_name() + " " + employee.getL_name());
                response.put("role", employee.getRole().name());
                response.put("options", options);  // This will show all permissions (formatted nicely)

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404).body(Collections.singletonMap("error", "Employee data not found"));
            }
        } else {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Invalid credentials"));
        }
    }
}




