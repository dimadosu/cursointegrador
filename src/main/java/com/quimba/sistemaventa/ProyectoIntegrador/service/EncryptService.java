package com.quimba.sistemaventa.ProyectoIntegrador.service;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Usuario;

public interface EncryptService {

    //método que encripta la contraseña
    String encryptPassword(String password);

    //método para comparar la contraseña original y la encriptada
    boolean verifyPassword(String originalPassword, Usuario usuario);
}
