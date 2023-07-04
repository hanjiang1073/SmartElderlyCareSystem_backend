package com.example.crud3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("volunteer")
@Data

public class VolunteerEntity {
    @TableId(value = "volunteerid", type = IdType.AUTO)

    private Integer volunteerid;

    @TableField("name")

    private String name;

    @TableField("age")

    private Integer age;

    @TableField("phone")

    private String phone;

    @TableField("description")

    private String description;

    @TableField("vector")

    private String vector;
}
