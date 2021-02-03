package com.example.security.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptConfig {

    /**
     * Gera um hash usando o Bcrypt
     * @param senha
     * @return senha hash
     */
    public static String gerarBcrypt(String senha){
        if(senha == null){
            return senha;
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(senha);
    }

    /**
     * Verifica se a senha é válida
     * @param senha
     * @param senhaEncoded
     * @return boolean
     */
    public static boolean senhaValida(String senha, String senhaEncoded){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(senha, senhaEncoded);
    }

}
