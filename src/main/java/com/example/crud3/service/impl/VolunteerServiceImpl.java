package com.example.crud3.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.crud3.entity.VolunteerEntity;

import com.example.crud3.mapper.VolunteerMapper;

import com.example.crud3.service.VolunteerService;
import org.springframework.stereotype.Service;

@Service("VolunteerService")
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, VolunteerEntity> implements VolunteerService {
}
