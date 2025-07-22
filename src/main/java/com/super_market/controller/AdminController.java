package com.super_market.controller;

import com.super_market.model.*;
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
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private EmployeeRepository employeeRepository;

    //Employee Controller

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

    // Product Controller

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestHeader("loggedInEmail") String loggedInEmail,
                                           @RequestBody Product product) {
        Optional<Employee> admin = employeeService.getEmployeeByEmail(loggedInEmail);

        if (admin.isEmpty() || !RolePermissions.hasPermission(admin.get().getRole(), Permission.CREATE_PRODUCT)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        Product created = adminService.createProduct(product);
        return ResponseEntity.ok(Collections.singletonMap("message", "Product created successfully with ID " + created.getId()));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@RequestHeader("loggedInEmail") String loggedInEmail,
                                           @PathVariable Long id,
                                           @RequestBody Product productDetails) {
        Optional<Employee> admin = employeeService.getEmployeeByEmail(loggedInEmail);

        if (admin.isEmpty() || !RolePermissions.hasPermission(admin.get().getRole(), Permission.UPDATE_PRODUCT)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        Product updated = adminService.updateProduct(id, productDetails);
        if (updated != null) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Product updated successfully"));
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "Product not found"));
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@RequestHeader("loggedInEmail") String loggedInEmail,
                                           @PathVariable Long id) {
        Optional<Employee> admin = employeeService.getEmployeeByEmail(loggedInEmail);

        if (admin.isEmpty() || !RolePermissions.hasPermission(admin.get().getRole(), Permission.DELETE_PRODUCT)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        adminService.deleteProduct(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Product deleted successfully"));
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(@RequestHeader("loggedInEmail") String loggedInEmail) {
        Optional<Employee> admin = employeeService.getEmployeeByEmail(loggedInEmail);

        if (admin.isEmpty() || !RolePermissions.hasPermission(admin.get().getRole(), Permission.READ_ALL_PRODUCTS)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        List<Product> products = adminService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@RequestHeader("loggedInEmail") String loggedInEmail,
                                            @PathVariable Long id) {
        Optional<Employee> admin = employeeService.getEmployeeByEmail(loggedInEmail);

        if (admin.isEmpty() || !RolePermissions.hasPermission(admin.get().getRole(), Permission.READ_ALL_PRODUCTS)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        Optional<Product> result = adminService.getProductById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "Product not found"));
        }
    }

    //Section Controller

    @PostMapping("/sections")
    public ResponseEntity<?> createSection(@RequestHeader("loggedInEmail") String loggedInEmail,
                                           @RequestBody Section section) {
        Optional<Employee> admin = employeeService.getEmployeeByEmail(loggedInEmail);

        if (admin.isEmpty() || !RolePermissions.hasPermission(admin.get().getRole(), Permission.CREATE_SECTION)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        Section created = adminService.createSection(section);
        return ResponseEntity.ok(Collections.singletonMap("message", "Section created successfully with ID " + created.getId()));
    }

    @PutMapping("/sections/{id}")
    public ResponseEntity<?> updateSection(@RequestHeader("loggedInEmail") String loggedInEmail,
                                           @PathVariable Long id,
                                           @RequestBody Section sectionDetails) {
        Optional<Employee> admin = employeeService.getEmployeeByEmail(loggedInEmail);

        if (admin.isEmpty() || !RolePermissions.hasPermission(admin.get().getRole(), Permission.UPDATE_SECTION)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        Section updated = adminService.updateSection(id, sectionDetails);
        if (updated != null) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Section updated successfully"));
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "Section not found"));
        }
    }

    @DeleteMapping("/sections/{id}")
    public ResponseEntity<?> deleteSection(@RequestHeader("loggedInEmail") String loggedInEmail,
                                           @PathVariable Long id) {
        Optional<Employee> admin = employeeService.getEmployeeByEmail(loggedInEmail);

        if (admin.isEmpty() || !RolePermissions.hasPermission(admin.get().getRole(), Permission.DELETE_SECTION)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        adminService.deleteSection(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Section deleted successfully"));
    }

    @GetMapping("/sections")
    public ResponseEntity<?> getAllSections(@RequestHeader("loggedInEmail") String loggedInEmail) {
        Optional<Employee> admin = employeeService.getEmployeeByEmail(loggedInEmail);

        if (admin.isEmpty() || !RolePermissions.hasPermission(admin.get().getRole(), Permission.READ_ALL_SECTIONS)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        List<Section> sections = adminService.getAllSections();
        return ResponseEntity.ok(sections);
    }

    @GetMapping("/sections/{id}")
    public ResponseEntity<?> getSectionById(@RequestHeader("loggedInEmail") String loggedInEmail,
                                            @PathVariable Long id) {
        Optional<Employee> admin = employeeService.getEmployeeByEmail(loggedInEmail);

        if (admin.isEmpty() || !RolePermissions.hasPermission(admin.get().getRole(), Permission.READ_ALL_SECTIONS)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        Optional<Section> result = adminService.getSectionById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "Section not found"));
        }
    }

    //Receipt Controller

    @PostMapping("/receipts")
    public ResponseEntity<?> createReceipt(@RequestHeader("loggedInEmail") String loggedInEmail,
                                           @RequestBody Receipt receipt) {
        Optional<Employee> employee = employeeService.getEmployeeByEmail(loggedInEmail);

        if (employee.isEmpty() || !RolePermissions.hasPermission(employee.get().getRole(), Permission.CREATE_RECEIPT)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        Receipt created = adminService.createReceipt(receipt);
        return ResponseEntity.ok(Collections.singletonMap("message", "Receipt created with ID " + created.getId()));
    }

    @DeleteMapping("/receipts/{id}")
    public ResponseEntity<?> deleteReceipt(@RequestHeader("loggedInEmail") String loggedInEmail,
                                           @PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeByEmail(loggedInEmail);

        if (employee.isEmpty() || !RolePermissions.hasPermission(employee.get().getRole(), Permission.DELETE_RECEIPT)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        adminService.deleteReceipt(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Receipt deleted successfully"));
    }

    @GetMapping("/receipts")
    public ResponseEntity<?> getAllReceipts(@RequestHeader("loggedInEmail") String loggedInEmail) {
        Optional<Employee> employee = employeeService.getEmployeeByEmail(loggedInEmail);

        if (employee.isEmpty() || !RolePermissions.hasPermission(employee.get().getRole(), Permission.READ_ALL_RECEIPTS)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        return ResponseEntity.ok(adminService.getAllReceipts());
    }

    @GetMapping("/receipts/{id}")
    public ResponseEntity<?> getReceiptById(@RequestHeader("loggedInEmail") String loggedInEmail,
                                            @PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeByEmail(loggedInEmail);

        if (employee.isEmpty() || !RolePermissions.hasPermission(employee.get().getRole(), Permission.READ_RECEIPT)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        Optional<Receipt> receipt = adminService.getReceiptById(id);
        return receipt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body((Receipt) Collections.singletonMap("error", "Receipt not found")));
    }


}
