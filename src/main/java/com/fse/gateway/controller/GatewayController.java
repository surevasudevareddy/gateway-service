package com.fse.gateway.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
@RequestMapping("/skill-tracker/api/v1/engineer/")
//@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class GatewayController {
    @RequestMapping("/fallback")
    public Mono fallback(){
        return Mono.just("Fallback");
    }
}
