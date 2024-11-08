package com.example.crudspringboot.dto;

import com.example.crudspringboot.model.Post;
import com.example.crudspringboot.model.Tag;

import java.util.stream.Collectors;

public class TagDTO {
    private Long id;
    private String name;

    // Constructor
    public TagDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

