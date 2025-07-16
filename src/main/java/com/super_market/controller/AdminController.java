package com.super_market.controller;

import com.super_market.model.Employee;
import com.super_market.model.Permission;
import com.super_market.model.RolePermissions;
import com.super_market.repository.EmployeeRepository;
import com.super_market.service.AdminService;
import com.super_market.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/employees")
    public ResponseEntity<?> createEmployee(@RequestHeader("loggedInEmail") String loggedInEmail,
                                            @RequestBody Employee employee) {
        Optional<Employee> admin = employeeService.getEmployeeByEmail(loggedInEmail);

        if (admin.isEmpty() || !RolePermissions.hasPermission(admin.get().getRole(), Permission.CREATE_EMPLOYEE)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        Employee created = adminService.createEmployee(employee);
        return ResponseEntity.ok(Collections.singletonMap("message", "Employee created successfully with ID " + created.getId()));
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<?> updateEmployee(@RequestHeader("loggedInEmail") String loggedInEmail,
                                            @PathVariable Long id,
                                            @RequestBody Employee employeeDetails) {
        Optional<Employee> admin = employeeService.getEmployeeByEmail(loggedInEmail);

        if (admin.isEmpty() || !RolePermissions.hasPermission(admin.get().getRole(), Permission.UPDATE_EMPLOYEE)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        Employee updated = adminService.updateEmployee(id, employeeDetails);
        if (updated != null) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Employee updated successfully"));
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "Employee not found"));
        }
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@RequestHeader("loggedInEmail") String loggedInEmail,
                                            @PathVariable Long id) {
        Optional<Employee> admin = employeeService.getEmployeeByEmail(loggedInEmail);

        if (admin.isEmpty() || !RolePermissions.hasPermission(admin.get().getRole(), Permission.DELETE_EMPLOYEE)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        adminService.deleteEmployee(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Employee deleted successfully"));
    }

    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees(@RequestHeader("loggedInEmail") String loggedInEmail) {
        Optional<Employee> admin = employeeService.getEmployeeByEmail(loggedInEmail);

        if (admin.isEmpty() || !RolePermissions.hasPermission(admin.get().getRole(), Permission.READ_ALL_EMPLOYEES)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        List<Employee> employees = adminService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<?> getEmployeeBYId(@RequestHeader("loggedInEmail") String loggedInEmail,@PathVariable long id) {
        Optional<Employee> admin = employeeService.getEmployeeByEmail(loggedInEmail);

        if (admin.isEmpty() || !RolePermissions.hasPermission(admin.get().getRole(), Permission.READ_ALL_EMPLOYEES)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        Optional<Employee> result = employeeRepository.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "Employee not found"));
        }
    }
}
