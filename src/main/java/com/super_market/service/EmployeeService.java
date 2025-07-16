package com.super_market.service;

import com.super_market.model.Employee;
import com.super_market.model.Permission;
import com.super_market.model.RolePermissions;
import com.super_market.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean login(String email, String password) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        return employee.isPresent() && employee.get().getPassword().equals(password);
    }

    public Set<String> getOptionsForEmployee(Employee employee) {
        Set<Permission> permissions = RolePermissions.getPermissionsForRole(employee.getRole());
        Set<String> options = new HashSet<>();

        for (Permission permission : permissions) {
            options.add(formatPermission(permission));
        }
        return options;
    }

    public Optional<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }


    private String formatPermission(Permission permission) {
        // Converts PERMISSION_NAME to "Permission name"
        String formatted = permission.name().replace("_", " ").toLowerCase();
        return Character.toUpperCase(formatted.charAt(0)) + formatted.substring(1);
    }
}
