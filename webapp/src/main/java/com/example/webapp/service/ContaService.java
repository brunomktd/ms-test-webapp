package com.example.webapp.service;


import com.example.webapp.vo.ContaVO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
public class ContaService {


    private WebClient wResource;
    private final WebClient wMock;
    private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJST0xFX0FETUlOIiwiY3JlYXRlZCI6MTYxMjE5OTU2NjA4MywiZXhwIjoxNjEyODA0MzY2fQ.herqRX_w_CK66pwoSy7obTK3Y1Wt7MT5CXS3U2xXsLtJvQOQrp92Pp-cH6rxQQzb8Zg08XO6LDxtdgjvnDC2KA";


    public ContaService(@Qualifier("resource") WebClient webClientResource, @Qualifier("mock") WebClient wMock) {
        this.wResource = webClientResource;
        this.wMock = wMock;
    }

    public void cadastrar(ContaVO novaConta) {
        wResource.post()
                .uri("/admin/contas")
//                .header("Authorization", "Bearer " + token)
                .body(Mono.just(novaConta), ContaVO.class)
                .retrieve()
                .bodyToMono(ContaVO.class)
                .block();
    }


    public void depositar(Integer id, Integer saldo) {
        ContaVO conta = wResource
                .put()
                .uri("/protect/contas/" + id + "?valor=" + saldo)
//                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(ContaVO.class)
                .block();
    }

    public void excluir(Integer id) {
        wResource.delete()
                .uri("/admin/contas/" + id)
//                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public Flux<ContaVO> findAllReactive() {

        Flux<ContaVO> list = wResource
                .get()
                .uri("/admin/contas")
//                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(ContaVO.class);

        return list.delayElements(Duration.ofSeconds(2));
    }

    public List<ContaVO> findAll() {

        List<ContaVO> list = wResource
                .get()
                .uri("/admin/contas")
//                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(ContaVO.class)
                .collectList()
                .block();

        return list;
    }
}
