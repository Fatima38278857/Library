package com.example.Library.controller;

import com.example.Library.entity.Employee;
import com.example.Library.security.JwtTokenUtil;
import com.example.Library.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(value = "http://localhost:8080")
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        Optional<Employee> optionalEmployee = employeeService.findByUsername(username);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();

            if (employeeService.checkPassword(password, employee.getPassword())) {
                String accessToken = jwtTokenUtil.generateToken(username, 10 * 60 * 1000); // 10 минут
                String refreshToken = jwtTokenUtil.generateToken(username, 30 * 60 * 1000); // 30 минут

                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                return tokens;
            }
        }

        throw new RuntimeException("Неверный логин или пароль");
    }

    @PostMapping("/refresh")
    public Map<String, String> refreshToken(@RequestParam String refreshToken) {
        if (jwtTokenUtil.isTokenExpired(refreshToken)) {
            throw new RuntimeException("Refresh token истек");
        }
        String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
        String newAccessToken = jwtTokenUtil.generateToken(username, 10 * 60 * 1000); //  access token на 10 минут

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", newAccessToken);
        return response;
    }
}
