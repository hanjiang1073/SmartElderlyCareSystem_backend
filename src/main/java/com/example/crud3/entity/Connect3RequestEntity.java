package com.example.crud3.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Connect3RequestEntity {
    private String name;
    private String age;
    private String description;
    private String phone;
    private boolean isOld;


    public Connect3RequestEntity(String name, String age, String description, String phone, boolean isOld) {
        this.name = name;
        this.age = age;
        this.description = description;
        this.phone = phone;
        this.isOld = isOld;
    }
}


