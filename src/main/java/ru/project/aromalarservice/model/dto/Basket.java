package ru.project.aromalarservice.model.dto;

import ru.project.aromalarservice.model.entity.Diffuser;

import java.util.ArrayList;
import java.util.List;


public class Basket {
    private List<Diffuser> diffusers = new ArrayList<>();

    public List<Diffuser> getDiffusers() {
        return diffusers;
    }
}
