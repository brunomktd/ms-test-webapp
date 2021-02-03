package com.example.security.controller;

import java.util.Optional;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import com.example.security.exception.SenhaInvalidaException;
import com.example.security.model.Usuario;
import com.example.security.service.SpringUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.security.utils.ResponseUtil;
import com.example.security.dto.JwtAuthenticationDto;
import com.example.security.dto.TokenDto;
import com.example.security.jwt.TokenConfig;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private static final String TOKEN_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private TokenConfig tokenConfig;

    @Autowired
    private SpringUserDetailsService userDetailsService;

    /**
     * Gera e retorna um novo token
     * @param authenticationDto
     * @param result
     * @return
     * @throws AuthenticationException
     */
    @PostMapping
    public ResponseEntity<TokenDto> gerarTokenJwt(@RequestBody JwtAuthenticationDto authenticationDto,
                                                  BindingResult result,
                                                  @AuthenticationPrincipal UserDetails auth) throws AuthenticationException {

        Usuario usuarioEntity = new Usuario();
        usuarioEntity.setUsername(authenticationDto.getUsername());
        usuarioEntity.setSenha(authenticationDto.getSenha());

        ResponseUtil<TokenDto> response = new ResponseUtil<TokenDto>();

        if (result.hasErrors()) {
            log.error("Erro validando lançamento: {}.", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().build();
        }

        log.info("Gerando token para o username: {}.", authenticationDto.getUsername());

        try{
            UserDetails usuarioAutenticado = userDetailsService.autenticar(usuarioEntity);
            String token = tokenConfig.obterToken(usuarioAutenticado);

            TokenDto tokenDto = new TokenDto(token);
            response.setData(tokenDto);
                return ResponseEntity.ok(tokenDto);
        } catch (UsernameNotFoundException | SenhaInvalidaException  e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<TokenDto> gerarRefreshTokenJwt(HttpServletRequest request) {
        log.info("Gerando refresh token Jwt.");
        ResponseUtil<TokenDto> response = new ResponseUtil<TokenDto>();
        Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));

        if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
            token = Optional.of(token.get().substring(7));
        }

        if (!token.isPresent()) {
            response.getErrors().add("Token não informado.");
        } else if (!tokenConfig.tokenValido(token.get())) {
            response.getErrors().add("Token inválido ou expirado.");
        }

        if (response.getErrors() != null) {
            return ResponseEntity.badRequest().build();
        }

        String refreshedToken = tokenConfig.refreshToken(token.get());

        TokenDto refreshTokenDto = new TokenDto();
        refreshTokenDto.setToken(refreshedToken);

        response.setData(refreshTokenDto);

        return ResponseEntity.ok(refreshTokenDto);
    }

}