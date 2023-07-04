package com.example.crud3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.crud3.entity.StusEntity;
import com.example.crud3.entity.VolunteerEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VolunteerMapper extends BaseMapper<VolunteerEntity> {
}
