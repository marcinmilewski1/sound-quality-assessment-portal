package com.sqap.gateway.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

public class CustomErrorFilter extends ZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(CustomErrorFilter.class);
    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return -1; // Needs to run before SendErrorFilter which has filterOrder == 0
    }

    @Override
    public boolean shouldFilter() {
        // only forward to errorPath if it hasn't been forwarded to already
        return RequestContext.getCurrentContext().getThrowable() != null;
    }

    @Override
    public Object run() {
        try {
            LOG.error("Custom error filter runing ");
            RequestContext ctx = RequestContext.getCurrentContext();

            Throwable throwable = ctx.getThrowable();
            if (throwable != null) {
                LOG.error("Zuul failure detected: " + throwable.getMessage(), throwable);
                // Populate context with new response values
                ctx.setResponseBody("Zuul exception (gateway):" + throwable.getMessage() + " cause: " + throwable.getCause());
                ctx.getResponse().setContentType("application/json");
                ctx.setResponseStatusCode(500); //Can set any error code as excepted
            }
        }
        catch (Exception ex) {
            LOG.error("Exception filtering in custom error filter", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }
}
