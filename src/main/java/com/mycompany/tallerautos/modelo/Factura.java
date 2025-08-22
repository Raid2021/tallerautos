package com.mycompany.tallerautos.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="facturas")
public class Factura {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @Column(unique=true, nullable=false)
  private String numero;

  private LocalDateTime emitida = LocalDateTime.now();

  @OneToOne(optional=false)
  private OrdenReparacion orden;

  private Double subtotal;
  private Double impuestos;
  private Double total;

  // getters/setters
  public Long getId(){return id;}
  public void setId(Long id){this.id=id;}
  public String getNumero(){return numero;}
  public void setNumero(String n){this.numero=n;}
  public LocalDateTime getEmitida(){return emitida;}
  public void setEmitida(LocalDateTime e){this.emitida=e;}
  public OrdenReparacion getOrden(){return orden;}
  public void setOrden(OrdenReparacion o){this.orden=o;}
  public Double getSubtotal(){return subtotal;}
  public void setSubtotal(Double s){this.subtotal=s;}
  public Double getImpuestos(){return impuestos;}
  public void setImpuestos(Double i){this.impuestos=i;}
  public Double getTotal(){return total;}
  public void setTotal(Double t){this.total=t;}
}
