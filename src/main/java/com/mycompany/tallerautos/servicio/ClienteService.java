/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tallerautos.servicio;

import com.mycompany.tallerautos.modelo.Cliente;
import com.mycompany.tallerautos.repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
/**
 *
 * @author raulp
 */

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void guardar(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public Iterable<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}

