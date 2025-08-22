/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tallerautos.controller;

import com.mycompany.tallerautos.modelo.Rol;
import com.mycompany.tallerautos.modelo.Usuario;
import com.mycompany.tallerautos.repositorio.RolRepository;
import com.mycompany.tallerautos.repositorio.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 *
 * @author raulp
 */
@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final PasswordEncoder enc;

    public UsuarioController(UsuarioRepository usuarioRepo, RolRepository rolRepo, PasswordEncoder enc){
        this.usuarioRepo = usuarioRepo; this.rolRepo = rolRepo; this.enc = enc;
    }

    @GetMapping("/lista")  
    public String lista(Model model){
        model.addAttribute("usuarios", usuarioRepo.findAll());
        return "usuario/lista"; 
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolRepo.findAll());
        return "usuario/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("usuario") Usuario u, BindingResult br, Model model){
        if(u.getId()==null){ 
            u.setPassword(enc.encode(u.getPassword()));
        } else {
            var db = usuarioRepo.findById(u.getId()).orElseThrow();
            if(u.getPassword()==null || u.getPassword().isBlank()) u.setPassword(db.getPassword());
            else u.setPassword(enc.encode(u.getPassword()));
        }
        usuarioRepo.save(u);
        return "redirect:/usuarios/lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){
        model.addAttribute("usuario", usuarioRepo.findById(id).orElseThrow());
        model.addAttribute("roles", rolRepo.findAll());
        return "usuario/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id){
        usuarioRepo.deleteById(id);
        return "redirect:/usuarios/lista";
    }
}

