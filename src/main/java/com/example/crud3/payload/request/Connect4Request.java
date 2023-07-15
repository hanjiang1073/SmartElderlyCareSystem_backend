package com.example.crud3.payload.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Connect4Request {
    private  String id;
    private  String img;
    private  boolean isOld;
}