package com.example.hellow_world.service;

import com.example.hellow_world.model.UserSec;
import com.example.hellow_world.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private IUserRepository userRepo;

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
}
