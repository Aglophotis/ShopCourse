package ru.aglophotis.mirea.microservice.router;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.UrlPathHelper;

public class CustomerZuulFilter extends ZuulFilter {
    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        final String requestURI =
                this.urlPathHelper.getPathWithinApplication(ctx.getRequest());
        System.out.println(requestURI);
        if (requestURI.startsWith("/cart/item/")
                && ctx.getRequest() != null
                && !StringUtils.isBlank(ctx.getRequest().getMethod())) {

            if ("PUT".equals(ctx.getRequest().getMethod().toUpperCase())) {
                ctx.set("serviceId", "XYZ-SVC");
                ctx.setRouteHost(null);
                ctx.addOriginResponseHeader("X-Zuul-ServiceId", "http://localhost:8081" + requestURI);
                System.out.println("TEST");
            }
        } else {
            System.out.println("ERROR");
        }
        return null;
    }
}
