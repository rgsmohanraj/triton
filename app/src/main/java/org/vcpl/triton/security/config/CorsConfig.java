package org.vcpl.triton.security.config;


import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
//@PropertySource(value={"CorsConfig"})
//@PropertySource(value={"CorsConfig.properties"})

public class CorsConfig implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        final HttpServletResponse response = (HttpServletResponse) res;
        final HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200/");
        response.setHeader("Access-Control-Allow-Origin", "https://triton.vivriticapital.com");
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT,GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers","Content-Type");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Content-Type", "application/json");
        if ("OPTIONS".equalsIgnoreCase(request.getMethod()))
        {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else
        {
            chain.doFilter(req, res);
        }

    }



}
