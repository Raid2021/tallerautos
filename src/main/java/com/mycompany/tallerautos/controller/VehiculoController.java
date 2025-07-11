/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tallerautos.repositorio;
import com.mycompany.tallerautos.modelo.Vehiculo;
import com.mycompany.tallerautos.modelo.Cliente;
import com.mycompany.tallerautos.repositorio.ClienteRepository;
import com.mycompany.tallerautos.repositorio.VehiculoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author raulp
 */
@Controller
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("vehiculo", new Vehiculo()); 
        model.addAttribute("clientes", clienteRepository.findAll());
        return "vehiculoForm";
    }

    @PostMapping("/guardar")
    public String guardarVehiculo(@ModelAttribute Vehiculo vehiculo) {
        vehiculoRepository.save(vehiculo);
        return "redirect:/clientes/lista";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Cliente.class, new java.beans.PropertyEditorSupport() {
            @Override
            public void setAsText(String idTexto) {
                Long id = Long.parseLong(idTexto);
                Cliente cliente = clienteRepository.findById(id).orElse(null);
                setValue(cliente);
            }
        });
    }
}