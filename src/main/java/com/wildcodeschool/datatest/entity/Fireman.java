package com.wildcodeschool.datatest.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Fireman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(mappedBy = "fireman")
    private List<Fire> fires;

    public Fireman(String name, List<Fire> fires) {
        this.name = name;
        this.fires = fires;
    }

    public Fireman(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Fire> getFires() {
        return fires;
    }

    public void setFires(List<Fire> fires) {
        this.fires = fires;
    }
}
