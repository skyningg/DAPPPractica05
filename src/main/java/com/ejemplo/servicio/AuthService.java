package com.ejemplo.servicio;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.xml.bind.DatatypeConverter;

import com.ejemplo.modelo.Empleado;

@ApplicationScoped
public class AuthService implements Serializable { 
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager em;

    // ==================== HASH DE CONTRASEÑA ====================
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar hash de contraseña", e);
        }
    }

    // ==================== LOGIN ====================
    public Empleado login(String usuario, String contrasena) {
        String hash = hashPassword(contrasena); // comparamos hash
        try {
            return em.createQuery(
                    "SELECT e FROM Empleado e WHERE e.usuario = :usuario AND e.contrasena = :contrasena",
                    Empleado.class)
                    .setParameter("usuario", usuario)
                    .setParameter("contrasena", hash)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // ==================== CREAR USUARIO ====================
    public Empleado crearUsuario(String usuario, String contrasena, String nombre, String telefono, String direccion) {
        Empleado e = new Empleado();
        e.setUsuario(usuario);
        e.setContrasena(hashPassword(contrasena)); // guardamos hash
        e.setNombre(nombre);
        e.setTelefono(telefono);
        e.setDireccion(direccion);

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(e);
            em.flush(); // asegura que se escriba inmediatamente
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al crear usuario", ex);
        }

        return e;
    }

    // ==================== VALIDACIÓN SIMPLE DE TOKEN ====================
    public boolean validarToken(String token) {
        return token != null;
    }
}
