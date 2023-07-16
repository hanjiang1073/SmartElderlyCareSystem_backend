package com.example.crud3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.crud3.entity.EventEntity;
import com.example.crud3.entity.InteractionEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InteractionMapper extends BaseMapper<InteractionEntity> {
}
