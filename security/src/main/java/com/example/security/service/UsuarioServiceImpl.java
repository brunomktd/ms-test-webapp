package com.example.security.service;

import com.example.security.model.Usuario;
import com.example.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public Optional<Usuario> buscaPorUsername(String username) {
        return Optional.ofNullable(this.usuarioRepository.findByUsername(username));
    }
}
