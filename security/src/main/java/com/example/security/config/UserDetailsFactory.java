package com.example.security.config;

import com.example.security.constants.PerfilEnum;
import com.example.security.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsFactory {
    private UserDetailsFactory(){}

    /**
     * Converte e gera um UserDetails customizado com base nos dados do usuário.
     * @param usuario
     * @return CustomUserDetails
     */
    public static UserDetails create (Usuario usuario){
        return new User(usuario.getUsername(), usuario.getSenha(), convertRoleToGrantAuthorities(usuario.getRole()));
    }

    /**
     * Converte a role do Usuário para o formato utilizado pelo Spring Security GrantedAuthority.
     * @param perfilEnum
     * @return List<GrantedAuthority>
     */
    private static List<GrantedAuthority> convertRoleToGrantAuthorities(PerfilEnum perfilEnum) {
//        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(perfilEnum.name());
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(perfilEnum.toString()));
        return authorities;
    }


}
