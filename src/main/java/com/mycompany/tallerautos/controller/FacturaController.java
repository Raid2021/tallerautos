package com.mycompany.tallerautos.controller;

import com.mycompany.tallerautos.modelo.*;
import com.mycompany.tallerautos.repositorio.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

  private final FacturaRepository facturaRepo;
  private final OrdenReparacionRepository ordenRepo;

  public FacturaController(FacturaRepository f, OrdenReparacionRepository o){
    this.facturaRepo=f; this.ordenRepo=o;
  }

    @GetMapping({"", "/"})
    public String lista(Model model){
      model.addAttribute("facturas", facturaRepo.findAll());
      model.addAttribute("ordenes", ordenRepo.findAll());
      return "facturas/lista";
    }

    @PostMapping("/generar")
    public String generar(@RequestParam Long ordenId, RedirectAttributes ra) {
      var orden = ordenRepo.findById(ordenId).orElse(null);
      if (orden == null) {
        ra.addFlashAttribute("error", "La orden no existe.");
        return "redirect:/facturas";
      }

      var existente = facturaRepo.findByOrdenId(ordenId).orElse(null);
      if (existente != null) {
        ra.addFlashAttribute("info", "La factura de esa orden ya existe: " + existente.getNumero());
        return "redirect:/facturas";
      }


      double subtotal = (orden.getCostoFinal() != null) ? orden.getCostoFinal()
                       : (orden.getCostoEstimado() != null ? orden.getCostoEstimado() : 0.0);


      double impuestos = java.math.BigDecimal.valueOf(subtotal)
          .multiply(new java.math.BigDecimal("0.13"))
          .setScale(2, java.math.RoundingMode.HALF_UP)
          .doubleValue();

      double total = java.math.BigDecimal.valueOf(subtotal)
          .add(java.math.BigDecimal.valueOf(impuestos))
          .setScale(2, java.math.RoundingMode.HALF_UP)
          .doubleValue();

      var factura = new Factura();
      factura.setOrden(orden);
      factura.setEmitida(java.time.LocalDateTime.now());
      factura.setSubtotal(subtotal);    
      factura.setImpuestos(impuestos);
      factura.setTotal(total);

      long count = facturaRepo.count() + 1;
      factura.setNumero(String.format("F%06d", count));

      facturaRepo.save(factura);

      ra.addFlashAttribute("ok", "Factura generada: " + factura.getNumero());
      return "redirect:/facturas";
    }


  

}
