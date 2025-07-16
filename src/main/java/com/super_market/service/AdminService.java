package com.super_market.service;

import com.super_market.model.Employee;
import com.super_market.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee newDetails) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setF_name(newDetails.getF_name());
            employee.setL_name(newDetails.getL_name());
            employee.setEmail(newDetails.getEmail());
            employee.setPassword(newDetails.getPassword());
            employee.setRole(newDetails.getRole());
            return employeeRepository.save(employee);
        }).orElse(null);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
