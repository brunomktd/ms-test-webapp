package com.example.security.service;

import com.example.security.model.Usuario;

import java.util.Optional;

public interface UsuarioService {

    Optional<Usuario> buscaPorUsername(String username);
}
