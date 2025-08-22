package com.mycompany.tallerautos.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="citas")
public class Cita {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime fechaHora;

  @Enumerated(EnumType.STRING)
  private EstadoCita estado = EstadoCita.PENDIENTE;

  @ManyToOne(optional=false) private Cliente cliente;
  @ManyToOne(optional=false) private Vehiculo vehiculo;

  @Column(length=500) private String motivo;

  public enum EstadoCita { PENDIENTE, CONFIRMADA, CANCELADA }


  public Long getId(){return id;}
  public void setId(Long id){this.id=id;}
  public LocalDateTime getFechaHora(){return fechaHora;}
  public void setFechaHora(LocalDateTime f){this.fechaHora=f;}
  public EstadoCita getEstado(){return estado;}
  public void setEstado(EstadoCita e){this.estado=e;}
  public Cliente getCliente(){return cliente;}
  public void setCliente(Cliente c){this.cliente=c;}
  public Vehiculo getVehiculo(){return vehiculo;}
  public void setVehiculo(Vehiculo v){this.vehiculo=v;}
  public String getMotivo(){return motivo;}
  public void setMotivo(String m){this.motivo=m;}
}
