package com.example.crud3.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crud3.entity.EventEntity;
import com.example.crud3.mapper.EventMapper;
import com.example.crud3.service.EventService;
import org.springframework.stereotype.Service;

@Service("EventService")
public class EventServiceImpl extends ServiceImpl<EventMapper, EventEntity> implements EventService {
}
