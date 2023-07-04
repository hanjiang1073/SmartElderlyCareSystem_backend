package com.example.crud3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("stus")
@Data

public class StusEntity {
    @TableId(value = "id", type = IdType.AUTO)

    private Integer id;

    @TableField("username")

    private String username;

    @TableField("password")

    private String password;
}



