package com.example.crud3.service;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import java.util.Vector;



public interface MonggoDB {
    Vector<String> findFileById(int userid); //查询用户在MongoDB中所有图片的id

    String uploadFile(int userid,String filepath);//上传图片，更新userfile和MongoDB两张表

    int deleteFile(String fileid); //删除，更新userfile和MongoDB两张表

    GridFSFile findOneFile(String id); //得到一个图片

    ObjectId upload(MultipartFile blobFile,int userid);
}
