package com.example.crud3.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Connect3RequestEntity {
    private boolean isOld;
    private String name;
    private Integer age;
    private String phone;
    private String desc;
}
