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
    Connect4Request(  String id,String img , boolean isOld)
    {
        this.id = id;
        this.img = img;
        this.isOld = isOld;
    }
}