package sk.stopangin.realtimegamem.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Allows cross origin for testing swagger docs using swagger-ui from local file
 * system
 */

@Component
public class CrossOriginFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(CrossOriginFilter.class);



    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp,
                                    FilterChain chain) throws ServletException, IOException {

        log.info("Applying CORS filter");
        HttpServletResponse response = (HttpServletResponse) resp;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "0");
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

        // Called by the web container to indicate to a filter that it is being
        // taken out of service.
        // We do not want to do anything here.
    }
}