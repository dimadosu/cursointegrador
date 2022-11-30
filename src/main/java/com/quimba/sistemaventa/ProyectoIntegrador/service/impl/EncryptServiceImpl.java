package com.quimba.sistemaventa.ProyectoIntegrador.service.impl;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Usuario;
import com.quimba.sistemaventa.ProyectoIntegrador.service.EncryptService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptServiceImpl implements EncryptService {


    @Override
    public String encryptPassword(String password) {
        //gensalt->tipo de encrytación alfanumerico
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    @Override
    public boolean verifyPassword(String originalPassword, Usuario usuario) {
        return BCrypt.checkpw(originalPassword,usuario.getPassword());
    }
}
