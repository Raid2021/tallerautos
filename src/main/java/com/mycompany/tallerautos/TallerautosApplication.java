package com.mycompany.tallerautos;

import com.mycompany.tallerautos.modelo.Rol;
import com.mycompany.tallerautos.modelo.Usuario;
import com.mycompany.tallerautos.repositorio.RolRepository;
import com.mycompany.tallerautos.repositorio.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TallerautosApplication {

    public static void main(String[] args) {
        SpringApplication.run(TallerautosApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(RolRepository rolRepo,
                               UsuarioRepository usuarioRepo,
                               PasswordEncoder enc) {
        return args -> {

            Rol adminRol = rolRepo.findByNombre("ADMIN").orElseGet(() -> {
                Rol r = new Rol();
                r.setNombre("ADMIN");
                return rolRepo.save(r);
            });

            Rol mecanicoRol = rolRepo.findByNombre("MECANICO").orElseGet(() -> {
                Rol r = new Rol();
                r.setNombre("MECANICO");
                return rolRepo.save(r);
            });

            Rol clienteRol = rolRepo.findByNombre("CLIENTE").orElseGet(() -> {
                Rol r = new Rol();
                r.setNombre("CLIENTE");
                return rolRepo.save(r);
            });

            usuarioRepo.findByUsername("admin@local").orElseGet(() -> {
                Usuario u = new Usuario();
                u.setNombre("Administrador");           
                u.setUsername("admin@local");
                u.setPassword(enc.encode("admin"));     
                u.setActivo(true);
                u.getRoles().add(adminRol);
                return usuarioRepo.save(u);
            });
        };
    }
}
