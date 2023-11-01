package com.example.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.dao.ArticleMapper;
import com.example.blog.model.ArticleInfo;
import com.example.blog.model.vo.ArticleInfoVO;
import com.example.blog.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description：
 * User: lmp
 * Date: 2023-10-09
 * Time: 22:35(李明浦)
 */
@Service
public class ArticleServiceImpl  extends ServiceImpl<ArticleMapper, ArticleInfo> implements IArticleService {
    @Autowired(required = false)
    private ArticleMapper articleMapper;

    @Override
    public ArticleInfoVO getDetail(Integer aid) {
        return articleMapper.getDetail(aid);
    }

    @Override
    public int updateRCount(Integer aid) {
        return articleMapper.updateRCount(aid);
    }
}
