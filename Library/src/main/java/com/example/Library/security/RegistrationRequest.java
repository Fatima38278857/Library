package com.example.Library.security;



public record RegistrationRequest(String login, String password, String confirmPassword) {
}
