package ru.project.aromalarservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "diffusers" )
public class Diffuser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String url;
    private String name;
    private String description;
    private Integer price;


}
