package com.example.security.service;

import com.example.security.config.SecurityConfig;
import com.example.security.config.UserDetailsFactory;
import com.example.security.exception.AuthenticationEntryPointException;
import com.example.security.exception.SenhaInvalidaException;
import com.example.security.model.Usuario;
import com.example.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SpringUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SecurityConfig securityConfig;

    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getUsername());
        boolean senhasBatem = securityConfig.passwordEncoder().matches(usuario.getSenha(), user.getPassword());

        if(senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);

        if(usuario == null){
            throw new UsernameNotFoundException("Username não encontrado");
        }

        UserDetails userDetails = UserDetailsFactory.create(usuario);
        return userDetails;
    }
}
