package com.example.usermanager.mapper;

import com.example.usermanager.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-26
 * Time: 14:50(李明浦)
 */
@Mapper
public interface UserMapper {
    UserInfo login(@Param("loginname")String loginname, @Param("password")String password);

    //查询所有的用户信息
    List<UserInfo> getAll();

    //添加用户
    int add(UserInfo userInfo);

    //根据登录名查询用户信息
    UserInfo getUserByLoginName(@Param("loginname")String loginname);

    //根据uid查询用户信息
    UserInfo getUserByUid(@Param("uid") Integer uid);

    //更新数据
    int update(UserInfo userInfo);

    //删除
    int del(@Param("uid") Integer uid);

    int dels(List<Integer> ids);

    List<UserInfo> getListByPage(@Param("username") String username,
                                 @Param("address") String address,
                                 @Param("email") String email,
                                 @Param("limit") Integer limit,
                                 @Param("offset") Integer offset);

    int getListByPageCount(@Param("username") String username,
                           @Param("address") String address,
                           @Param("email") String email);
}
