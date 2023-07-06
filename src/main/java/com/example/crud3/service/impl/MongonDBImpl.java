package com.example.crud3.service.impl;

import com.example.crud3.mapper.UserFileMapper;
import com.example.crud3.table.UserFile;
import com.example.crud3.table.UserFileExample;
import com.example.crud3.service.MonggoDB;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import static com.example.crud3.utils.Global.*;
@Service
public class MongonDBImpl implements MonggoDB {
    @Autowired
    private GridFsTemplate gridfsTemplate;
    @Autowired
    UserFileMapper userFileMapper;
    @Override
    public Vector<String> findFileById(int userid) {
        Vector<String> result = new Vector<>();

        UserFileExample fileExample = new UserFileExample();
        fileExample.createCriteria().andUseridEqualTo(userid);
        List<UserFile> userFileList = userFileMapper.selectByExample(fileExample);
        for(int i =0;i<userFileList.size();i++){
            result.add(userFileList.get(i).getFileid());
        }
        return  result;
    }

    @Override
    public String uploadFile(int userid,String filepath) {
        File file = new File(filepath);
        try {
            InputStream content =new FileInputStream(file);
            ObjectId id = gridfsTemplate.store(content, file.getName(), "image/jpg");
            UserFile userFile = new UserFile();
            userFile.setFileid(id.toString());
            userFile.setUserid(userid);
            userFile.setFiledate(new Date());
            userFile.setFilename(file.getName());
            userFileMapper.insert(userFile);
            return id.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int deleteFile(String fileid) {
        Query query = new Query(Criteria.where("_id").is(fileid));
        if (query != null) {
            gridfsTemplate.delete(query);
            UserFileExample userFileExample = new UserFileExample();
            userFileExample.createCriteria().andFileidEqualTo(fileid);
            userFileMapper.deleteByExample(userFileExample);
            return SUCCESS;
        } else {
            System.out.println("没有此项数据");
            return FAIL;
        }
    }

    @Override
    public GridFSFile findOneFile(String id) {
        return gridfsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));
    }

    @Override
    public ObjectId upload(MultipartFile blobFile, int userid) {
        try {
            ObjectId id = gridfsTemplate.store(blobFile.getInputStream(), blobFile.getContentType());
            UserFile userFile = new UserFile();
            userFile.setFileid(id.toString());
            userFile.setUserid(userid);
            userFile.setFilename(blobFile.getName());
            userFileMapper.insert(userFile);
            return id;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
