package com.example.myblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myblog.model.ArticleInfo;
import com.example.myblog.model.vo.ArticleInfoVO;
import org.apache.ibatis.annotations.Param;

public interface ArticleInfoMapper extends BaseMapper<ArticleInfo> {
    ArticleInfoVO getDetail(@Param("aid")Integer aid);

    int updateRCount(@Param("aid")Integer aid);
}
