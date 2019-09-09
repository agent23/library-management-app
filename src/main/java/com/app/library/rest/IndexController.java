package com.app.library.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IndexController {

    @GetMapping("/ping")
    public String index(){
        return "<h2>Library Application is Alive</h2>";
    }
}
