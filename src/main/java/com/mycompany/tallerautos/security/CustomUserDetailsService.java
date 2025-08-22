package com.mycompany.tallerautos.security;

import com.mycompany.tallerautos.modelo.Usuario;
import com.mycompany.tallerautos.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario u = usuarioRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

    Set<GrantedAuthority> auths = u.getRoles().stream()
      .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getNombre().toUpperCase()))
      .collect(Collectors.toSet());

    return org.springframework.security.core.userdetails.User
      .withUsername(u.getUsername())
      .password(u.getPassword())
      .authorities(auths)
      .accountLocked(!u.isActivo())
      .disabled(!u.isActivo())
      .build();
  }
}
