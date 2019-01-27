package ru.aglophotis.mirea.microservice.router;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class PreZuulFilter extends ZuulFilter {
    @Override
    public int filterOrder() {
        return 1; // run before PreDecoration
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        System.out.println("URL: " + request.getRequestURI());
        System.out.println("Method: " + request.getMethod());
        System.out.println("Resuest: " + new Date());
        return null;
    }
}
