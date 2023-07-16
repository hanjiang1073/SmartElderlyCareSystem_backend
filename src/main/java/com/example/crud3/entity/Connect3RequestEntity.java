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
    private String age;
    private String phone;
    private String desc;
}
