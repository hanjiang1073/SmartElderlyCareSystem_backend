package com.example.crud3.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crud3.entity.ElderEntity;

import com.example.crud3.mapper.ElderMapper;

import com.example.crud3.service.ElderService;
import org.springframework.stereotype.Service;

@Service("ElderService")
public class ElderServiceImpl extends ServiceImpl<ElderMapper, ElderEntity> implements ElderService {
}
