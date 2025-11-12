package com.ejemplo.modelo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "empleados")
public class Empleado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clave;

    private String nombre;
    private String direccion;
    private String telefono;

    @Column(unique = true)
    private String usuario;

    private String contrasena;

    // Getters y setters

    public Long getClave() { return clave; }
    public void setClave(Long clave) { this.clave = clave; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    @Override
    public String toString() {
        return "Empleado{" +
                "clave=" + clave +
                ", nombre='" + nombre + '\'' +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}
