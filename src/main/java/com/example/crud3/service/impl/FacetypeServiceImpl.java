package com.example.crud3.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crud3.entity.FacetypeEntity;
import com.example.crud3.mapper.FacetypeMapper;
import com.example.crud3.service.FacetypeService;
import org.springframework.stereotype.Service;

@Service
public class FacetypeServiceImpl extends ServiceImpl<FacetypeMapper, FacetypeEntity> implements FacetypeService {
}
