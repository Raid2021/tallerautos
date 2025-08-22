package com.mycompany.tallerautos.controller;

import com.mycompany.tallerautos.modelo.Cita;
import com.mycompany.tallerautos.repositorio.CitaRepository;
import com.mycompany.tallerautos.repositorio.ClienteRepository;
import com.mycompany.tallerautos.repositorio.VehiculoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/citas")
public class CitaController {

  private final CitaRepository citaRepo;
  private final ClienteRepository clienteRepo;
  private final VehiculoRepository vehiculoRepo;

  public CitaController(CitaRepository c, ClienteRepository cl, VehiculoRepository v){
    this.citaRepo = c; this.clienteRepo = cl; this.vehiculoRepo = v;
  }

  @GetMapping({"", "/"})
  public String lista(Model model){
    model.addAttribute("citas", citaRepo.findAll());
    return "citas/lista";
  }

  @GetMapping("/nuevo")
  public String nuevo(Model model){
    model.addAttribute("cita", new Cita());
    model.addAttribute("clientes", clienteRepo.findAll());
    model.addAttribute("vehiculos", vehiculoRepo.findAll());
    model.addAttribute("slots", slotsDisponibles(LocalDate.now()));
    return "citas/form";
  }

  @PostMapping
  public String guardar(@ModelAttribute Cita cita){
    citaRepo.save(cita);
    return "redirect:/citas";
  }


  private List<LocalDateTime> slotsDisponibles(LocalDate dia){
    List<LocalDateTime> slots = new ArrayList<>();
    for(int h=8; h<=17; h++){
      slots.add(LocalDateTime.of(dia, LocalTime.of(h,0)));
    }
    var ocupadas = citaRepo.findAll();
    slots.removeIf(s -> ocupadas.stream().anyMatch(c -> c.getFechaHora()!=null && c.getFechaHora().equals(s)));
    return slots;
  }
}
