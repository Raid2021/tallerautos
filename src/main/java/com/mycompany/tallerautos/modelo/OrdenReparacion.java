package com.mycompany.tallerautos.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="ordenes_reparacion")
public class OrdenReparacion {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime creada = LocalDateTime.now();

  @Enumerated(EnumType.STRING)
  private EstadoOrden estado = EstadoOrden.CREADA;

  @Column(length=1000)
  private String descripcion;

  private Double costoEstimado;
  private Double costoFinal;

  @ManyToOne(optional=false)
  private Vehiculo vehiculo;

  @ManyToOne(optional=false)
  private Cliente cliente;

  @ManyToOne
  private Usuario mecanicoAsignado;

  public enum EstadoOrden { CREADA, EN_PROCESO, COMPLETADA, CANCELADA }

  // getters/setters
  public Long getId() {return id;}
  public void setId(Long id){this.id=id;}
  public LocalDateTime getCreada(){return creada;}
  public void setCreada(LocalDateTime c){this.creada=c;}
  public EstadoOrden getEstado(){return estado;}
  public void setEstado(EstadoOrden e){this.estado=e;}
  public String getDescripcion(){return descripcion;}
  public void setDescripcion(String d){this.descripcion=d;}
  public Double getCostoEstimado(){return costoEstimado;}
  public void setCostoEstimado(Double c){this.costoEstimado=c;}
  public Double getCostoFinal(){return costoFinal;}
  public void setCostoFinal(Double c){this.costoFinal=c;}
  public Vehiculo getVehiculo(){return vehiculo;}
  public void setVehiculo(Vehiculo v){this.vehiculo=v;}
  public Cliente getCliente(){return cliente;}
  public void setCliente(Cliente c){this.cliente=c;}
  public Usuario getMecanicoAsignado(){return mecanicoAsignado;}
  public void setMecanicoAsignado(Usuario u){this.mecanicoAsignado=u;}
}
