package com.example.myblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myblog.model.CommentInfo;
import com.example.myblog.model.vo.CommentInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentInfoMapper extends BaseMapper<CommentInfo> {
    List<CommentInfoVO> getList(@Param("aid")Integer aid);


}
