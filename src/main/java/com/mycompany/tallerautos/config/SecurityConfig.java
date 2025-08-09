package com.mycompany.tallerautos.config;

import com.mycompany.tallerautos.repositorio.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(UsuarioRepository usuarioRepo){
        return username -> usuarioRepo.findByUsername(username)
            .map(u -> org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword())
                .accountLocked(!u.isActivo())
                .roles(u.getRoles().stream()
                    .map(r -> r.getNombre().replace("ROLE_", ""))
                    .toArray(String[]::new))
                .build())
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(a -> a
                .requestMatchers(
                    "/css/**", "/js/**", "/img/**", "/webjars/**",
                    "/", "/index", "/login", "/error", "/favicon.ico"
                ).permitAll()

                .requestMatchers("/usuarios/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")              
                .loginProcessingUrl("/login")    
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(l -> l
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .exceptionHandling(e -> e

                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))

                .accessDeniedHandler((req, res, ex) -> res.sendRedirect("/login?denied"))
            );


        return http.build();
    }
}
