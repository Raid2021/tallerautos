/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tallerautos.repositorio;
import com.mycompany.tallerautos.modelo.Vehiculo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author raulp
 */
@Repository
public interface VehiculoRepository extends CrudRepository<Vehiculo, Long> {
}
