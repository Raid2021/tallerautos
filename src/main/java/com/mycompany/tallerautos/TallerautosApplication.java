package com.mycompany.tallerautos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.password.PasswordEncoder; // <-- se queda porque lo usamos en el seed

import com.mycompany.tallerautos.modelo.Usuario;
import com.mycompany.tallerautos.modelo.Rol;
import com.mycompany.tallerautos.repositorio.UsuarioRepository;
import com.mycompany.tallerautos.repositorio.RolRepository;

import java.util.HashSet;

@SpringBootApplication
public class TallerautosApplication {

    public static void main(String[] args) {
        SpringApplication.run(TallerautosApplication.class, args);
    }


    @Bean
    CommandLineRunner initData(UsuarioRepository uRepo, RolRepository rRepo, PasswordEncoder enc){
      return args -> {
        Rol adminRole = rRepo.findByNombre("ROLE_ADMIN")
            .orElseGet(() -> rRepo.save(new Rol(null, "ROLE_ADMIN")));
        Rol userRole  = rRepo.findByNombre("ROLE_USER")
            .orElseGet(() -> rRepo.save(new Rol(null, "ROLE_USER")));

        if(uRepo.findByUsername("admin").isEmpty()){
          Usuario u = new Usuario();
          u.setUsername("admin");
          u.setPassword(enc.encode("admin"));

          if (u.getRoles() == null) {
            u.setRoles(new HashSet<>());
          }
          u.getRoles().add(adminRole);
          u.getRoles().add(userRole);

          uRepo.save(u);
        }
      };
    }
}
