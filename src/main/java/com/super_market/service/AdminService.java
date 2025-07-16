package com.super_market.service;

import com.super_market.model.Employee;
import com.super_market.model.Product;
import com.super_market.model.Section;
import com.super_market.repository.EmployeeRepository;
import com.super_market.repository.ProductRepository;
import com.super_market.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SectionRepository sectionRepository;

    //Employee Management

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

    //Product Management

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product newDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(newDetails.getName());
            product.setDescription(newDetails.getDescription());
            product.setPrice(newDetails.getPrice());
            product.setQuantity(newDetails.getQuantity());
            product.setCategory(newDetails.getCategory());
            return productRepository.save(product);
        }).orElse(null);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    //Section Management

    public Section createSection(Section section) {
        return sectionRepository.save(section);
    }

    public Section updateSection(Long id, Section newDetails) {
        return sectionRepository.findById(id).map(section -> {
            section.setName(newDetails.getName());
            section.setDescription(newDetails.getDescription());
            return sectionRepository.save(section);
        }).orElse(null);
    }

    public void deleteSection(Long id) {
        sectionRepository.deleteById(id);
    }

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public Optional<Section> getSectionById(Long id) {
        return sectionRepository.findById(id);
    }
}
