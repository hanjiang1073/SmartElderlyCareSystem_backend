package com.example.crud3.service.impl;

import com.example.crud3.mapper.UserFileMapper;
import com.example.crud3.table.UserFile;
import com.example.crud3.service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserFileServiceImpl implements UserFileService {

    @Autowired
    private UserFileMapper fileMapper;
    @Override
    public List<UserFile> listFile(int pn) {
//        PageHelper.startPage(pn,5); //每页显示5个数据
        return fileMapper.selectByExample(null);
    }
}
