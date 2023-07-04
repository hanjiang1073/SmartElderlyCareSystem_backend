package com.example.crud3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("event")
@Data

public class EventEntity {
    @TableId(value = "eventid", type = IdType.AUTO)

    private Integer eventid;

    @TableField("type")

    private Integer type;

    @TableField("desc")

    private String desc;
}
