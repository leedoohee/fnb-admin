package com.fnbadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/main/index")
    public String index(){
        return "index.html";
    }

    @GetMapping("/main/tables")
    public String tables(){
        return "tables.html";
    }
}
