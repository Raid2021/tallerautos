package com.mycompany.tallerautos.controller;

import com.mycompany.tallerautos.modelo.*;
import com.mycompany.tallerautos.repositorio.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/ordenes")
public class OrdenController {

  private final OrdenReparacionRepository ordenRepo;
  private final VehiculoRepository vehiculoRepo;
  private final ClienteRepository clienteRepo;

  public OrdenController(OrdenReparacionRepository o, VehiculoRepository v, ClienteRepository c){
    this.ordenRepo = o; this.vehiculoRepo = v; this.clienteRepo = c;
  }

  @GetMapping({"", "/"})
  public String lista(Model model){
    model.addAttribute("ordenes", ordenRepo.findAll());
    return "ordenes/lista";
  }

  @GetMapping("/nuevo")
  public String nuevo(Model model){
    model.addAttribute("orden", new OrdenReparacion());
    model.addAttribute("vehiculos", vehiculoRepo.findAll());
    model.addAttribute("clientes", clienteRepo.findAll());
    model.addAttribute("estados", OrdenReparacion.EstadoOrden.values());
    return "ordenes/form";
  }

  @PostMapping
  public String guardar(@ModelAttribute OrdenReparacion orden, RedirectAttributes ra){
    ordenRepo.save(orden);
    ra.addFlashAttribute("ok","Orden guardada");
    return "redirect:/ordenes";
  }

  @GetMapping("/editar/{id}")
  public String editar(@PathVariable Long id, Model model){
    var orden = ordenRepo.findById(id).orElseThrow();
    model.addAttribute("orden", orden);
    model.addAttribute("vehiculos", vehiculoRepo.findAll());
    model.addAttribute("clientes", clienteRepo.findAll());
    model.addAttribute("estados", OrdenReparacion.EstadoOrden.values());
    return "ordenes/form";
  }

  @PostMapping("/eliminar/{id}")
  public String eliminar(@PathVariable Long id, RedirectAttributes ra){
    ordenRepo.deleteById(id);
    ra.addFlashAttribute("ok","Orden eliminada");
    return "redirect:/ordenes";
  }
}
