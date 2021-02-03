package com.example.webapp.config;

import com.example.webapp.vo.TokenVO;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    public static final int TIMEOUT = 1000;

    private HttpServletRequest request;

    public WebClientConfig(HttpServletRequest request) {
        this.request = request;
    }

    @Bean
    public WebClient mock(){
        return WebClient.builder()
                .baseUrl("https://testess.free.beeceptor.com")
//                .defaultHeader("Authorization", "Bearer " + token)
//                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }

    @Bean
    public WebClient auth(){
        return WebClient.builder()
                .baseUrl("http://localhost:8081/sso")
//                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }

    @Bean
    public WebClient resource(){
//        TokenVO token = (TokenVO) request.getSession().getAttribute("token");

        return WebClient.builder()
                .baseUrl("http://localhost:8081/micro2/api")
//                .defaultHeaders(header -> header.setBearerAuth(token.getToken()))
//                .defaultHeader("Authorization", "Bearer " + token)
//                .defaultCookie("cookieKey", "cookieValue")
                .filter(logInfo)
                .filter(tokenFilter)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }

    ExchangeFilterFunction logInfo = ((clientRequest, exchangeFunction) -> {
        HttpMethod method = clientRequest.method();
        log.info("Method: {}", method);
        log.info("Headers: {}", clientRequest.headers().toString());
        return exchangeFunction.exchange(clientRequest);
    });

//    ExchangeFilterFunction tokenFilter(Object token){
//        String tkn = "steste";
//
//        ExchangeFilterFunction tokenFilter = ((clientRequest, exchangeFunction) -> {
//            if(token != null){
//                clientRequest.headers().setBearerAuth(tkn);
//            }
//            return exchangeFunction.exchange(clientRequest);
//        });
//        return tokenFilter;
//    }

    ExchangeFilterFunction tokenFilter = ((clientRequest, exchangeFunction) -> {
        TokenVO token = (TokenVO) request.getSession().getAttribute("token");

        if(token != null){
            String tkn = token.getToken();
            clientRequest.headers().setBearerAuth(tkn);
        }
        return exchangeFunction.exchange(clientRequest);
    });


    TcpClient tcpClient = TcpClient
            .create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
            });

}
