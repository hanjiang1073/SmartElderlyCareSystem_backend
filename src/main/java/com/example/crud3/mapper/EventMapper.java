package com.example.crud3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.crud3.entity.EventEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EventMapper extends BaseMapper<EventEntity> {
}
