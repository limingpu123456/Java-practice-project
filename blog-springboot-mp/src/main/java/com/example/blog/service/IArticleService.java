package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.model.ArticleInfo;
import com.example.blog.model.vo.ArticleInfoVO;

/**
 * Description：
 * User: lmp
 * Date: 2023-10-09
 * Time: 22:34(李明浦)
 */
public interface IArticleService extends IService<ArticleInfo> {
    ArticleInfoVO getDetail(Integer aid);

    int updateRCount(Integer aid);
}
