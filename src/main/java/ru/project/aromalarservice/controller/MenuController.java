package ru.project.aromalarservice.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.project.aromalarservice.model.entity.Diffuser;
import ru.project.aromalarservice.repositiria.DiffuserRepository;

import java.util.List;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;


@Controller
@AllArgsConstructor

public class MenuController {
    private DiffuserRepository diffuserRepository;


    @GetMapping("/")
    public String getIndex(Model model){
       List<Diffuser> diffusers =  diffuserRepository.findAll();
       model.addAttribute("diffusers",diffusers);
        return "index";

    }





}
