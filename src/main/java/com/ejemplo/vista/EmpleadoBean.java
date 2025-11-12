package com.ejemplo.vista;

import com.ejemplo.modelo.Empleado;
import com.ejemplo.servicio.EmpleadoService;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named(value = "empleadoBean")
@ViewScoped
public class EmpleadoBean implements Serializable {

    @Inject EmpleadoService service;

    private List<Empleado> empleados;

    private Empleado seleccionado;

    @PostConstruct
    public void init() {
        empleados = service.listar();
        seleccionado = new Empleado();
    }

    public void nuevo() { seleccionado = new Empleado(); }

    public void guardar() {
        service.guardar(seleccionado);
        empleados = service.listar();
        PrimeFaces.current().executeScript("PF('dlg').hide()");
    }

    public void eliminar() {
        service.eliminar(seleccionado.getClave());
        empleados = service.listar();
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public Empleado getSeleccionado() {
        return seleccionado;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public void setSeleccionado(Empleado seleccionado) {
        this.seleccionado = seleccionado;
    }
}
