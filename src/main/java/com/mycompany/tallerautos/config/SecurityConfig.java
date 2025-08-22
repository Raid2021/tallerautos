package com.mycompany.tallerautos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/", "/login", "/error",
                         "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
        .requestMatchers("/facturas/**").hasRole("ADMIN")
        .requestMatchers("/ordenes/**").hasAnyRole("ADMIN","MECANICO")
        .requestMatchers("/citas/**").hasAnyRole("ADMIN","CLIENTE")
        .anyRequest().authenticated()
      )
      .formLogin(form -> form
        .loginPage("/login")          
        .loginProcessingUrl("/login")  
        .defaultSuccessUrl("/", true)
        .failureUrl("/login?error")
        .permitAll()
      )
      .logout(logout -> logout
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?logout")
        .permitAll()
      );
    return http.build();
  }
}
