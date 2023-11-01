package com.example.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.model.CommentInfo;
import com.example.blog.model.vo.CommentInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description：
 * User: lmp
 * Date: 2023-10-15
 * Time: 11:21(李明浦)
 */
public interface CommontMapper extends BaseMapper<CommentInfo> {
    List<CommentInfoVO> getList(@Param("aid") Integer aid);
}
