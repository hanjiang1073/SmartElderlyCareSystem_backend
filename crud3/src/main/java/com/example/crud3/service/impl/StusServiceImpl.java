package com.example.crud3.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crud3.entity.StusEntity;
import com.example.crud3.mapper.StusMapper;
import com.example.crud3.service.StusService;
import org.springframework.stereotype.Service;

@Service("StusService")
public class StusServiceImpl extends ServiceImpl<StusMapper, StusEntity> implements StusService {
}
