package com.example.crud3.service;

import com.example.crud3.table.UserFile;

import java.util.List;

public interface UserFileService {

    List<UserFile> listFile(int pn); //获取数据库中的所有用户
}
