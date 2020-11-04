package com.example.centrumtelefonii.auth;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.security.authentication.AuthenticationManager;
        import org.springframework.security.authentication.BadCredentialsException;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.AuthenticationException;
        import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

        import javax.servlet.FilterChain;
        import javax.servlet.ServletException;
        import javax.servlet.ServletRequest;
        import javax.servlet.ServletResponse;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.IOException;

/**
 * Filter responsible for getting the api key off of incoming requests that need to be authorized.
 */
public class ApiKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {
    private static final Logger LOG = LoggerFactory.getLogger(ApiKeyAuthFilter.class);

    private final String headerName;

    public ApiKeyAuthFilter(final String headerName) {
        this.headerName = headerName;
    }

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res= (HttpServletResponse) response;
//
//        System.out.println(req.getHeader("api_key"));
//        String principal = req.getHeader("api_key");
////        String principal = (String) req.getHeader(headerName);
//        if (!principalRequestValue.equals(principal)) {
//            throw new BadCredentialsException("The API key was not found or not the expected value.");
//        }
////        authentication.setAuthenticated(true);
////        return authentication;
//        chain.doFilter(request, response);
//    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(headerName);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        // No creds when using API key
        return "N/A";
    }

}

