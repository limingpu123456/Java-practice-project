package com.example.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.model.ArticleInfo;
import com.example.blog.model.vo.ArticleInfoVO;
import org.apache.ibatis.annotations.Param;

/**
 * Description：
 * User: lmp
 * Date: 2023-10-09
 * Time: 22:30(李明浦)
 */
public interface ArticleMapper extends BaseMapper<ArticleInfo> {
    ArticleInfoVO getDetail(@Param("aid") Integer aid);

    int updateRCount(@Param("aid") Integer aid);
}
