package ru.project.aromalarservice.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "diffusers" )
public class Diffuser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;


}
