package com.example.onlinemusic.mapper;

import com.example.onlinemusic.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-29
 * Time: 15:23(李明浦)
 */
@Mapper
public interface UserMapper {
    User login(User loginUser);

    User selectByName(String username);
}
