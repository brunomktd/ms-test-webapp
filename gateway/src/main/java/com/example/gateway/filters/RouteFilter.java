package com.example.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
//        System.out.println("Esse é um route filter, são usados para encaminhar a solicitação.");
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return null;
    }
}
