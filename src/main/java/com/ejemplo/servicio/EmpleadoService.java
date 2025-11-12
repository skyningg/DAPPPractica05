package com.ejemplo.servicio;

import com.ejemplo.modelo.Empleado;
import com.ejemplo.repositorio.EmpleadoRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class EmpleadoService {
    @Inject
    EmpleadoRepository repo;
    public List<Empleado> listar() { return repo.listar(); }
    public void guardar(Empleado e) { if (e.getClave()==null) repo.crear(e);
    else repo.actualizar(e); }
    public void eliminar(Long id) { repo.eliminar(id); }
}
