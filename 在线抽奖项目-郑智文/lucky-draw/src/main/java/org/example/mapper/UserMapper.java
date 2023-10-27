package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.base.BaseMapper;
import org.example.model.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User selectByUsername(String username);
}