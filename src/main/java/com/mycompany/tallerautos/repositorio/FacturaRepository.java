package com.mycompany.tallerautos.repositorio;

import com.mycompany.tallerautos.modelo.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;  

public interface FacturaRepository extends JpaRepository<Factura, Long> {
  Optional<Factura> findByOrdenId(Long ordenId);
}
