package com.example.gerenblog.mapper;

import com.example.gerenblog.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-22
 * Time: 15:42(李明浦)
 */
@Mapper
public interface UserMapper {
    public int add(@Param("username")String username,
                   @Param("password")String password);

    public UserInfo login(@Param("username")String username,
                          @Param("password")String password);
}
