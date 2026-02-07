package com.example.hellow_world.service;

import com.example.hellow_world.dto.AuthLoginRequestDTO;
import com.example.hellow_world.dto.AuthResponseDTO;
import com.example.hellow_world.model.UserSec;
import com.example.hellow_world.repository.IUserRepository;
import com.example.hellow_world.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //tenemos un objeto de tipo UserSec y necesitamos retornar uno de tipo UserDetails
        //traer el usuario desde la base de datos
        UserSec userSec = userRepo.findUserEntityByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("El usuario "+username+" no fue encontrado"));
        //creamos una lista para los permisos de usuario
        List<SimpleGrantedAuthority> authorityList=new ArrayList<>();

        //traer roler y convertirlos en SimpleGrantedAuthority
        userSec.getRolesList()
                        .forEach(role->authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));
        //traer permisos y convertirlos en SimpleGrantedAuthority
        userSec.getRolesList().stream()
                .flatMap(role->role.getPermissionList().stream())
                .forEach(permission->authorityList.add(new SimpleGrantedAuthority(permission.getPermissionName())));

        return new User(
                userSec.getUsername(),
                userSec.getPassword(),
                userSec.isEnabled(),
                userSec.isAccountNotExpired(),
                userSec.isCredentialNotExpired(),
                userSec.isAccountNotLocked(),
                authorityList
        );
    }
    public AuthResponseDTO loginUser (AuthLoginRequestDTO authLoginRequest){

        //recuperamos nombre de usuario y contraseña
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate (username, password);

        //si todo sale bien
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(username, "login ok", accessToken, true);
        return authResponseDTO;

    }
    public Authentication authenticate (String username, String password) {
        //con esto debo buscar el usuario
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        // si no es igual
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }


}
