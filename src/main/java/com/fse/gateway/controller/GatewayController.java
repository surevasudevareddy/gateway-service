package com.fse.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GatewayController {
    @RequestMapping("/fallback")
    public Mono fallback(){
        return Mono.just("Fallback");
    }
}
