package com.cbl.cityrtgs.common.filter;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class MDCLogEnhancerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            //String requestId = request.getHeader("Request-Id");
            String remoteIP = request.getRemoteAddr();
            //String remoteIP = IPUtils.getRemoteIP(request);
            MDC.put("RemoteIP", remoteIP);
        }
        try {
            // call filter(s) upstream for the real processing of the request
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // it's important to always clean the cid from the MDC,
            // this Thread goes to the pool but it's loglines would still contain the cid.
            MDC.remove("RemoteIP");
        }
    }

    @Override
    public void destroy() {

    }
}
