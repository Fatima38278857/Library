package com.example.Library.security;

import com.example.Library.entity.Employee;
import com.example.Library.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeRegistrationService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    public Employee registerEmployee(String username, String rawPassword) {
//        String encodedPassword = passwordEncoder.encode(rawPassword);
//        Employee employee = new Employee();
//        employee.setUsername(username);
//        employee.setPassword(encodedPassword);
//        return employeeRepository.save(employee);
//    }

}
