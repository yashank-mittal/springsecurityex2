package com.example.springsecurityex2.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springsecurityex2.service.MyUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    public JWTFilter jwtfilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
        .csrf(customizer -> customizer.disable())
        .authorizeHttpRequests(request -> request
        .requestMatchers("register","login")
        .permitAll()
        .anyRequest().authenticated())
        //.formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        //it is used before UsernamePaswordAuthrnticationFilter
        .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class)
        .build();

    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     // UserDetails user1 = User
        // .withDefaultPasswordEncoder()
        // .username("yash")
        // .password("yash@12")
        // .roles("USER")
        // .build();

        // UserDetails user2 = User
        // .withDefaultPasswordEncoder()
        // .username("ravi")
        // .password("r@12")
        // .roles("Admin")
        // .build();
        // return new InMemoryUserDetailsManager(user1,user2);
  //  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
    dao.setPasswordEncoder(new BCryptPasswordEncoder(12));
    dao.setUserDetailsService(myUserDetailService);
    return dao;
  }

  //AuhenticationManager manages authenticationProvider

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
  {
    return config.getAuthenticationManager();
  }


    
}
