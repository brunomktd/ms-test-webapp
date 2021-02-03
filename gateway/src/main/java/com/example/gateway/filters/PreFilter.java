package com.example.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class PreFilter extends ZuulFilter {

    @Override
    public String filterType() {
        // ("pre/post/route/error")
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
//        System.out.println("Esse é um pré filtro, executado logo no inicio da requisição");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        System.out.println("Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString() + " QueryString: " + request.getQueryString());
        return null;
    }
}
