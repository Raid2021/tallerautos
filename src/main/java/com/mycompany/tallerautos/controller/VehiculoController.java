package com.mycompany.tallerautos.controller;

import com.mycompany.tallerautos.modelo.Vehiculo;
import com.mycompany.tallerautos.modelo.Cliente;


import com.mycompany.tallerautos.repositorio.VehiculoRepository;
import com.mycompany.tallerautos.repositorio.ClienteRepository;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoController {

    private final VehiculoRepository vehiculoRepo;
    private final ClienteRepository clienteRepo;

    public VehiculoController(VehiculoRepository vehiculoRepo, ClienteRepository clienteRepo) {
        this.vehiculoRepo = vehiculoRepo;
        this.clienteRepo = clienteRepo;
    }

    @GetMapping("/lista")
    public String lista(Model model) {
        model.addAttribute("vehiculos", vehiculoRepo.findAll());
        return "vehiculos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(@RequestParam(required = false) Long clienteId, Model model) {
        Vehiculo v = new Vehiculo();
        model.addAttribute("vehiculo", v);

        if (clienteId != null) {
            var cliente = clienteRepo.findById(clienteId).orElse(null);
            if (cliente == null) {
                model.addAttribute("error", "Cliente no encontrado");
                return "redirect:/vehiculos/lista";
            }
            model.addAttribute("clienteId", clienteId);
            model.addAttribute("cliente", cliente);
        } else {
            model.addAttribute("clientes", clienteRepo.findAll());
        }
        return "vehiculos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("vehiculo") Vehiculo vehiculo,
                          @RequestParam(required = false) Long clienteId,
                          RedirectAttributes ra,
                          Model model) {

        if (clienteId != null) {
            var cliente = clienteRepo.findById(clienteId).orElse(null);
            if (cliente == null) {
                ra.addFlashAttribute("error", "Cliente no encontrado");
                return "redirect:/vehiculos/lista";
            }
            vehiculo.setCliente(cliente);
        } else if (vehiculo.getCliente() != null && vehiculo.getCliente().getId() != null) {
            var cliente = clienteRepo.findById(vehiculo.getCliente().getId()).orElse(null);
            vehiculo.setCliente(cliente);
        } else {

            model.addAttribute("clientes", clienteRepo.findAll());
            model.addAttribute("error", "Selecciona un cliente");
            return "vehiculos/form";
        }

        vehiculoRepo.save(vehiculo);
        ra.addFlashAttribute("ok", "Vehículo guardado correctamente");
        return "redirect:/vehiculos/lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var v = vehiculoRepo.findById(id).orElse(null);
        if (v == null) {
            model.addAttribute("error", "Vehículo no encontrado");
            return "redirect:/vehiculos/lista";
        }
        model.addAttribute("vehiculo", v);
        model.addAttribute("clientes", clienteRepo.findAll());
        return "vehiculos/form";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        vehiculoRepo.deleteById(id);
        ra.addFlashAttribute("ok", "Vehículo eliminado");
        return "redirect:/vehiculos/lista";
    }
}
