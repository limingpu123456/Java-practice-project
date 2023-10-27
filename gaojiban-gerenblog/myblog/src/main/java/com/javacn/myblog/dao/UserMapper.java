package com.javacn.myblog.dao;

import com.javacn.myblog.model.UserInfo;
import com.javacn.myblog.model.vo.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Description：
 * User: lmp
 * Date: 2023-08-29
 * Time: 22:48(李明浦)
 */
@Mapper
public interface UserMapper {

    //添加用户
    int reg(UserInfo userInfo);

    //根据登录蜜名查询用户对象
    //@Param给xml用的
     UserInfo getUserByLoginName(@Param("loginname")String loginname);
}
