package com.example.crud3.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("emotion")
@Data
public class EmotionEntity {
    @TableField("time")

    private String time;


    @TableField("emotion")

    private String emotion;
}
