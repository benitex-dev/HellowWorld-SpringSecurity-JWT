package com.example.hellow_world.service;

import com.example.hellow_world.model.UserSec;

import java.util.List;
import java.util.Optional;

public interface IUserService {



        public List<UserSec> findAll();
        public Optional<UserSec> findById(Long id);
        public UserSec save(UserSec userSec);
        public void deleteById(Long id);
        public void update(UserSec userSec);

    //agregamos el método de encriptado
    public String encriptPassword(String password);


}
