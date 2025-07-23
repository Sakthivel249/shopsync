package com.super_market.controller;

import com.super_market.model.*;
import com.super_market.service.AdminService;
import com.super_market.service.CashierService;
import com.super_market.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/cashier")
@CrossOrigin(origins = "http://localhost:5173")
public class CashierController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CashierService cashierService;

    //Receipt Controller

    @PostMapping("/receipts")
    public ResponseEntity<?> createReceipt(@RequestHeader("loggedInEmail") String loggedInEmail,
                                           @RequestBody Receipt receipt) {
        Optional<Employee> employee = employeeService.getEmployeeByEmail(loggedInEmail);

        if (employee.isEmpty() || !RolePermissions.hasPermission(employee.get().getRole(), Permission.CASHIER_CREATE_RECEIPT)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        Receipt created = adminService.createReceipt(receipt);
        return ResponseEntity.ok(Collections.singletonMap("message", "Receipt created with ID " + created.getId()));
    }

    @DeleteMapping("/receipts/{id}")
    public ResponseEntity<?> deleteReceipt(@RequestHeader("loggedInEmail") String loggedInEmail,
                                           @PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeByEmail(loggedInEmail);

        if (employee.isEmpty() || !RolePermissions.hasPermission(employee.get().getRole(), Permission.CASHIER_DELETE_RECEIPT)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        adminService.deleteReceipt(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Receipt deleted successfully"));
    }

    @GetMapping("/receipts")
    public ResponseEntity<?> getAllReceipts(@RequestHeader("loggedInEmail") String loggedInEmail) {
        Optional<Employee> employee = employeeService.getEmployeeByEmail(loggedInEmail);

        if (employee.isEmpty() || !RolePermissions.hasPermission(employee.get().getRole(), Permission.CASHIER_READ_ALL_RECEIPTS)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        return ResponseEntity.ok(adminService.getAllReceipts());
    }

    @GetMapping("/receipts/{id}")
    public ResponseEntity<?> getReceiptById(@RequestHeader("loggedInEmail") String loggedInEmail,
                                            @PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeByEmail(loggedInEmail);

        if (employee.isEmpty() || !RolePermissions.hasPermission(employee.get().getRole(), Permission.CASHIER_READ_RECEIPT)) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "Access denied!"));
        }

        Optional<Receipt> receipt = adminService.getReceiptById(id);
        return receipt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body((Receipt) Collections.singletonMap("error", "Receipt not found")));
    }


}
