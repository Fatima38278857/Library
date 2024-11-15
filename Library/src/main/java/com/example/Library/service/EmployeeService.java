package com.example.Library.service;

import com.example.Library.entity.Employee;
import com.example.Library.repository.EmployeeRepository;
import com.example.Library.security.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<Employee> findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public ResponseEntity<String> registerEmployee(RegistrationRequest request) {
        String username = request.login(); // Используем правильный метод геттера
        String password = request.password();
        String confirmPassword = request.confirmPassword();

        // Проверяем на пустые значения
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя пользователя не может быть пустым");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Пароль не может быть пустым");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Пароли не совпадают");
        }
        if (employeeRepository.findByUsername(request.login()).isPresent()) {
            throw new IllegalArgumentException("Имя пользователя уже занято");
        }
        Employee register = new Employee(request.login(), passwordEncoder.encode(request.password()));
        employeeRepository.save(register);

        return ResponseEntity.ok("Пользователь успешно зарегистрирован!");
    }
}
