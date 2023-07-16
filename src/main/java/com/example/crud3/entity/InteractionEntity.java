package com.example.crud3.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("interaction")
@Data
public class InteractionEntity {
    @TableField("time")
    private String time;


    @TableField("message")
    private String message;
}
