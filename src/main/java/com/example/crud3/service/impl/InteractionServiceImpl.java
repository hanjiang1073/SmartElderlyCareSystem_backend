package com.example.crud3.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crud3.entity.InteractionEntity;
import com.example.crud3.mapper.InteractionMapper;
import com.example.crud3.service.InteractionService;
import org.springframework.stereotype.Service;

@Service
public class InteractionServiceImpl extends ServiceImpl<InteractionMapper, InteractionEntity> implements InteractionService {
}
