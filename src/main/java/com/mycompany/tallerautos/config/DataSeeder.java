package com.mycompany.tallerautos.config;

import com.mycompany.tallerautos.modelo.Rol;
import com.mycompany.tallerautos.modelo.Usuario;
import com.mycompany.tallerautos.repositorio.RolRepository;
import com.mycompany.tallerautos.repositorio.UsuarioRepository;
import java.util.HashSet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

  @Bean
  CommandLineRunner seedData(UsuarioRepository usuarios, RolRepository roles, PasswordEncoder encoder) {
    return args -> {
      // Roles base
      Rol admin = roles.findByNombre("ADMIN").orElseGet(() -> {
        Rol r = new Rol();
        r.setNombre("ADMIN");
        return roles.save(r);
      });
      Rol mecanico = roles.findByNombre("MECANICO").orElseGet(() -> {
        Rol r = new Rol();
        r.setNombre("MECANICO");
        return roles.save(r);
      });
      Rol cliente = roles.findByNombre("CLIENTE").orElseGet(() -> {
        Rol r = new Rol();
        r.setNombre("CLIENTE");
        return roles.save(r);
      });

      // Usuario admin demo
      usuarios.findByUsername("admin@demo.com").orElseGet(() -> {
        Usuario u = new Usuario();
        u.setUsername("admin@demo.com");
        u.setPassword(encoder.encode("Admin*123"));
        u.setActivo(true);
        var rs = new HashSet<Rol>();
        rs.add(admin);
        u.setRoles(rs);
        return usuarios.save(u);
      });

      // Usuario mecánico demo
      usuarios.findByUsername("mec@demo.com").orElseGet(() -> {
        Usuario u = new Usuario();
        u.setUsername("mec@demo.com");
        u.setPassword(encoder.encode("Mec*123"));
        u.setActivo(true);
        var rs = new HashSet<Rol>();
        rs.add(mecanico);
        u.setRoles(rs);
        return usuarios.save(u);
      });

      // Usuario cliente demo
      usuarios.findByUsername("cli@demo.com").orElseGet(() -> {
        Usuario u = new Usuario();
        u.setUsername("cli@demo.com");
        u.setPassword(encoder.encode("Cli*123"));
        u.setActivo(true);
        var rs = new HashSet<Rol>();
        rs.add(cliente);
        u.setRoles(rs);
        return usuarios.save(u);
      });
    };
  }
}
