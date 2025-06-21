package it.epicode.SpringSecurity.JWT.dto;


import it.epicode.SpringSecurity.JWT.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private Role role;
}
