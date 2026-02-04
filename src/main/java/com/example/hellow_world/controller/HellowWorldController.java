package com.example.hellow_world.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@PreAuthorize("denyAll()")
public class HellowWorldController {

    @GetMapping("/holaseg")
    //@PreAuthorize("hasAuthority('READ')")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER') ")
    public String secHellowWorld(){

        return "endpoint seguro";
    }

    @GetMapping("/holanoseg")
    @PreAuthorize("permitAll()")
    public String noSecHellowWorld(){

        return "endpoint no seguro";
    }
}
