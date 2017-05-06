package Unmentionable.settings.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilterRequest implements Filter {


    private final Logger log = LoggerFactory.getLogger(CorsFilterRequest.class);

    public CorsFilterRequest() {
        log.info("SimpleCORSFilter init");
    }
    
    public static final String AUTHENTICATION_HEADER = "Authorization";
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    	
//    	req.setAttribute("AuthType", "ROLE_USER");
//    	req.setAttribute("authType", "ROLE_USER");
    	
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true"); //추가
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE, OPTIONS");        
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Authorization, Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");
        
        System.out.println("===================");
        System.out.println(request.getMethod());
        System.out.println(req);
        System.out.println(res);
        System.out.println(request);
        System.out.println(response);
        System.out.println("request.getRequestURL() :: " + request.getRequestURL());
        System.out.println("request.getAuthType() :: " + request.getAuthType());
        System.out.println("request.getContextPath() :: " + request.getContextPath());
        System.out.println(chain);
        System.out.println("===================");
        
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
        	chain.doFilter(req, res);
        	
        }
    	
    	
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
    
}
