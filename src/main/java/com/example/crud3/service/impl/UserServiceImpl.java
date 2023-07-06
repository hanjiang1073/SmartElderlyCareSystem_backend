package com.example.crud3.service.impl;

import com.example.crud3.mapper.UserFileMapper;
import com.example.crud3.mapper.UserMapper;
import com.example.crud3.table.*;
import com.example.crud3.service.UserService;
import com.example.crud3.utils.Week;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.example.crud3.utils.Global.*;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    UserFileMapper userFileMapper;
    @Override
    public int modifyInfo(int ID, User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUseridEqualTo(ID);
        if(userMapper.updateByExampleSelective(user,userExample)>0)
            return SUCCESS;
        else
            return FAIL;
    }

    @Override
    public int modifyPassword(int ID, String pass) {
        User user = new User();
        user.setPassword(pass);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUseridEqualTo(ID);
        if(userMapper.updateByExampleSelective(user,userExample)>0)
            return SUCCESS;
        else
            return FAIL;
    }

    @Override
    public int AssignRights(int rights, int id) {
        User user = new User();
        user.setRights(rights);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUseridEqualTo(id);
        if(userMapper.updateByExampleSelective(user,userExample)>0)
            return SUCCESS;
        else return  FAIL;
    }

        public Map<Integer, List<User>> getUserListByRightsForAssignContract() {
            return null;
        }

    @Override
    public User registerUser(User user) {
        // 判断是否已存在该用户
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(user.getUsername());
        List<User> userList = userMapper.selectByExample(userExample);
        if (!userList.isEmpty())
        {
            return null;
        }

        // 否则注册为新的用户
        userMapper.insertSelective(user);
        return user;
    }

    @Override
    public User loginUser(String username, String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andPasswordEqualTo(password).andUsernameEqualTo(username);
        List<User> users =userMapper.selectByExample(userExample);
        if(users!=null){
            if(users.size()==1) return users.get(0);
            else return null;
        }
        else return null;
    }


    @Override
        public String retrievePassword(String username) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUsernameEqualTo(username);
            List<User> users = userMapper.selectByExample(userExample);
            if (users!=null){
                if (users.size()!=0) return users.get(0).getPassword();
                else return null;
            }
            return null;
        }

        @Override
        public List<User> listUser(int pn) {
           // PageHelper.startPage(pn,5); //每页显示5个数据
            return userMapper.selectByExample(null);
        }

        @Override
        public List<User> listUser() {
            return userMapper.selectByExample(null);
        }

        @Override
        public List<User> listUserSelective(User user, int pn) {
            return null;
        }

        @Override
        public User getUserById(Integer id) {
            return userMapper.selectByPrimaryKey(id);
        }

        @Override
        public User getUserByUserName(String username) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUsernameEqualTo(username);
            List<User> users = userMapper.selectByExample(userExample);
            if (users.size()!=0){
                return users.get(0);
            }
            return null;
        }

        @Override
        public boolean ifExistUser(String username)
        {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUsernameEqualTo(username);
            List<User> users = userMapper.selectByExample(userExample);
            if (!users.isEmpty())
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        @Override
        public boolean ifExistUser(Integer id)
        {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUseridEqualTo(id);
            List<User> users = userMapper.selectByExample(userExample);
            if (!users.isEmpty())
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        @Override
        public int deleteUser(Integer userID){
            return userMapper.deleteByPrimaryKey(userID);
        }

    @SuppressWarnings("deprecation")
    @Override
    public Week getUserFile(int id) {
        UserFileExample userFileExample = new UserFileExample();
        userFileExample.createCriteria().andUseridEqualTo(id);
        List<UserFile> userFiles = userFileMapper.selectByExample(userFileExample);
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        Week week = new Week();
        for(int i=0;i<userFiles.size();i++){
            Date t = userFiles.get(i).getFiledate();
            switch (c.get(Calendar.DATE)-t.getDate()){
                case 0:week.tod++;break;
                case 1:week.mon++;break;
                case 2:week.tes++;break;
                case 3:week.wes++;break;
                case 4:week.ths++;break;
                case 5:week.fri++;break;
                case 6:week.sat++;break;
                case 7:week.sun++;break;
            }
        }
        return week;
    }


}
