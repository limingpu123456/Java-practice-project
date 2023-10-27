package com.example.myblog.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myblog.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<UserInfo> {

    // 添加用户
    int reg(UserInfo userInfo);

    // 根据登录名查询用户对象
    UserInfo getUserByLoginName(@Param("loginname")String name);

}











