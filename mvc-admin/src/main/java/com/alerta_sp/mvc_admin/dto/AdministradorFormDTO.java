package com.alerta_sp.mvc_admin.dto;

import jakarta.validation.constraints.NotBlank;

public class AdministradorFormDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String senha;

    // Getters e Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
