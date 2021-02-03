package com.example.webapp.service;

import com.example.webapp.vo.TokenVO;
import com.example.webapp.vo.LoginVO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class LoginService {

    private final WebClient wAuth;

    public LoginService(@Qualifier("auth") WebClient webClientAuth) {
        this.wAuth = webClientAuth;
    }

    public TokenVO auth(LoginVO login) {
        TokenVO token = wAuth.post()
                .uri("/auth")
                .body(Mono.just(login), LoginVO.class)
                .retrieve()
                .bodyToMono(TokenVO.class)
                .block();
        return token;
    }
}
