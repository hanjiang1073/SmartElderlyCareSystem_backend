package com.example.crud3.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("facetype")
@Data
public class FacetypeEntity {
    @TableField("time")
    private String time;

    @TableField("types")
    private String types;

    @TableField("name")
    private String name;
}
