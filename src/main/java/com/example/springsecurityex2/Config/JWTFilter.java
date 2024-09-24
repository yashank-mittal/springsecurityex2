package com.example.springsecurityex2.Config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.springsecurityex2.service.JWTService;
import com.example.springsecurityex2.service.MyUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    public JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
                String authHeader = request.getHeader("Authorization");
                String token = null;
                String username = null;

                if(authHeader != null && authHeader.startsWith("Bearer "))
                {
                    token = authHeader.substring(7);
                    username = jwtService.extractUserName(token);
                }

                //if username is not empty and user is not authenticated
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
                {
                    //Give UserDetail object from database
                    UserDetails userDetails = context.getBean(MyUserDetailService.class).loadUserByUsername(username); 

                    if(jwtService.validateToken(token, userDetails))
                    {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }

                filterChain.doFilter(request, response);
    }

}
