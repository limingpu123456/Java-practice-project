package com.example.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myblog.model.ArticleInfo;
import com.example.myblog.model.vo.ArticleInfoVO;

public interface IArticleInfoService extends IService<ArticleInfo> {
    ArticleInfoVO getDetail(Integer aid);

    int updateRCount(Integer aid);
}
