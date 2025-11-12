package com.ejemplo.vista;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.ejemplo.modelo.Empleado;
import com.ejemplo.servicio.AuthService;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // ==================== CAMPOS LOGIN ====================
    private String usuarioLogin;
    private String contrasenaLogin;

    // ==================== CAMPOS REGISTRO ====================
    private String usuarioReg;
    private String contrasenaReg;
    private String nombre;
    private String telefono;
    private String direccion;

    private Empleado empleadoAutenticado;

    @Inject
    private AuthService authService;

    // ==================== LOGIN ====================
    public String iniciarSesion() {
        System.out.println("[LOGIN] Intentando login con usuario: " + usuarioLogin);

        empleadoAutenticado = authService.login(usuarioLogin, contrasenaLogin);
        if (empleadoAutenticado != null) {
            System.out.println("[LOGIN] Éxito. Empleado autenticado: " + empleadoAutenticado.getNombre());
            return "empleados.xhtml?faces-redirect=true";
        } else {
            System.out.println("[LOGIN] Fallo. Usuario o contraseña incorrectos: " + usuarioLogin);
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña incorrectos")
            );
            return null;
        }
    }

    // ==================== CREAR USUARIO ====================
    public String crearUsuario() {
        System.out.println("[REGISTRO] Intentando crear usuario: " + usuarioReg);

        if (usuarioReg == null || usuarioReg.isEmpty() || contrasenaReg == null || contrasenaReg.isEmpty()) {
            System.out.println("[REGISTRO] Fallo. Usuario o contraseña vacíos");
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debes ingresar usuario y contraseña")
            );
            return null;
        }

        authService.crearUsuario(usuarioReg, contrasenaReg, nombre, telefono, direccion);
        System.out.println("[REGISTRO] Usuario creado correctamente: " + usuarioReg);

        // Limpiar campos después de registrar
        usuarioReg = contrasenaReg = nombre = telefono = direccion = null;

        FacesContext.getCurrentInstance().addMessage(
            null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario creado correctamente")
        );

        // Redirigir a login.xhtml después de crear usuario
        return "login.xhtml?faces-redirect=true";
    }

    // ==================== VERIFICAR AUTENTICACIÓN ====================
    public boolean estaAutenticado() {
        return empleadoAutenticado != null;
    }

    // ==================== REDIRECCIÓN SI YA ESTÁ AUTENTICADO ====================
    public void redirigirSiYaAutenticado() throws IOException {
        if (estaAutenticado()) {
            System.out.println("[REDIRECCIÓN] Usuario ya autenticado: " + empleadoAutenticado.getNombre());
            FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .redirect("empleados.xhtml");
        } else {
            System.out.println("[REDIRECCIÓN] No hay usuario autenticado");
        }
    }

    // ==================== CERRAR SESIÓN ====================
    public void cerrarSesion() {
        System.out.println("[CERRAR SESIÓN] Cerrando sesión del usuario: " +
            (empleadoAutenticado != null ? empleadoAutenticado.getNombre() : "ninguno"));

        empleadoAutenticado = null;
        usuarioLogin = contrasenaLogin = null;
        usuarioReg = contrasenaReg = nombre = telefono = direccion = null;

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ==================== GETTERS Y SETTERS ====================
    public String getUsuarioLogin() { return usuarioLogin; }
    public void setUsuarioLogin(String usuarioLogin) { this.usuarioLogin = usuarioLogin; }
    public String getContrasenaLogin() { return contrasenaLogin; }
    public void setContrasenaLogin(String contrasenaLogin) { this.contrasenaLogin = contrasenaLogin; }

    public String getUsuarioReg() { return usuarioReg; }
    public void setUsuarioReg(String usuarioReg) { this.usuarioReg = usuarioReg; }
    public String getContrasenaReg() { return contrasenaReg; }
    public void setContrasenaReg(String contrasenaReg) { this.contrasenaReg = contrasenaReg; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Empleado getEmpleadoAutenticado() { return empleadoAutenticado; }
}
