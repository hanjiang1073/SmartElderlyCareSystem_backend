package com.example.crud3.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crud3.entity.BanareaEntity;
import com.example.crud3.entity.EmotionEntity;
import com.example.crud3.mapper.BanareaMapper;
import com.example.crud3.mapper.EmotionMapper;
import com.example.crud3.service.BanareaService;
import com.example.crud3.service.EmotionService;
import org.springframework.stereotype.Service;

@Service
public class EmotionServiceImpl  extends ServiceImpl<EmotionMapper, EmotionEntity> implements EmotionService {
}
