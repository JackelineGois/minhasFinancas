package com.project.demo.service;

import com.project.demo.entities.Usuario;

public interface UsuarioService {
    
    Usuario autenticar(String email, String senha);

    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);

    
    
}
