package com.lab56.orchid_management.dtos;

import jakarta.validation.constraints.*;

public record RegisterRequest(
    @NotBlank String fullName,
    @NotBlank @Email String email,
    @NotBlank @Size(min = 8) String password
) {}
