package com.example.crud3.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crud3.entity.BanareaEntity;
import com.example.crud3.entity.ElderEntity;
import com.example.crud3.mapper.BanareaMapper;
import com.example.crud3.mapper.ElderMapper;
import com.example.crud3.service.BanareaService;
import com.example.crud3.service.ElderService;
import org.springframework.stereotype.Service;

@Service
public class BanareaServiceImpl extends ServiceImpl<BanareaMapper, BanareaEntity> implements BanareaService {
}
