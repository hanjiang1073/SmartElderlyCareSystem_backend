package com.example.crud3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.crud3.entity.EventEntity;
import com.example.crud3.entity.FacetypeEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FacetypeMapper extends BaseMapper<FacetypeEntity> {
}
