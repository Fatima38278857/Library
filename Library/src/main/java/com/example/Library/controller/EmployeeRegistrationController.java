package com.example.Library.controller;

import com.example.Library.repository.EmployeeRepository;
import com.example.Library.security.AuthenticationRequest;
import com.example.Library.security.JwtTokenUtil;
import com.example.Library.security.RegistrationRequest;
import com.example.Library.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "API для регистрации и авторизации")
@CrossOrigin(value = "http://localhost:8080")
public class EmployeeRegistrationController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) {
        return employeeService.registerEmployee(request);
    }

    //
//        @Operation(summary = "Создание токена для пользователя",
//                description = "Аутентифицирует пользователя и возвращает JWT токен.")
//        @ApiResponses(value = {
//                @ApiResponse(responseCode = "200", description = "Токен успешно создан",
//                        content = @Content(mediaType = "application/json",
//                                schema = @Schema(type = "string"))),
//                @ApiResponse(responseCode = "401", description = "Недействительные учетные данные",
//                        content = @Content)
//        })
    @PostMapping("/create")
    public String createToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            // Аутентификация пользователя
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.login(), authenticationRequest.password()));

            if (authentication.isAuthenticated()) {
                // Получение имени пользователя из аутентификации
                String username = authentication.getName();

                // Генерация токена с использованием метода generateToken
                long expirationTime = 10 * 60 * 1000; // 10 минут в миллисекундах
                String token = jwtTokenUtil.generateToken(username, expirationTime);

                System.out.println("Аутентификация успешна для пользователя: " + username);
                System.out.println("Generated token: " + token);

                return token;
            } else {
                throw new Exception("Недействительные учетные данные");
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new Exception("Недействительные учетные данные", e);
        }
    }
//    @Operation(summary = "Регистрация нового сотрудника")
//    @PostMapping("/register")
//    public String registerEmployee(@RequestBody RegistrationRequest request) {
//        if (!request.password().equals(request.confirmPassword())) {
//            throw new RuntimeException("Пароли не совпадают");
//        }
//
//        String encodedPassword = passwordEncoder.encode(request.password());
//        Employee employee = new Employee();
//        employee.setUsername(request.login());
//        employee.setPassword(encodedPassword);
//
//        employeeRepository.save(employee);
//        return "Сотрудник зарегистрирован успешно";
//    }
}
