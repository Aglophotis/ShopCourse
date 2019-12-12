package ru.aglophotis.mirea.microservice.router;

import com.google.common.io.CharStreams;
import com.netflix.ribbon.proxy.annotation.Http;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class PostZuulFilter extends ZuulFilter {

    public String filterType() {
        return "post";
    }

    public int filterOrder() {
        return 999;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();

        HttpServletResponse response = context.getResponse();
        System.out.println("Response: " + new Date());
        try (final InputStream responseDataStream = context.getResponseDataStream()) {
            final String responseData = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));
            context.setResponseBody(responseData);
            System.out.println("Response body: " + responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("---------------------------------------------------------------------");
        if (context.getRequest().getMethod().equals(Http.HttpMethod.POST.toString())) {
            response.setHeader("Access-Control-Allow-Origin", context.getRequest().getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD, PATCH, PUT");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
            context.setResponse(response);
        }
        return null;
    }
}