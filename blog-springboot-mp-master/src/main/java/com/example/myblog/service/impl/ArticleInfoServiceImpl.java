package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.dao.ArticleInfoMapper;
import com.example.myblog.model.ArticleInfo;
import com.example.myblog.model.vo.ArticleInfoVO;
import com.example.myblog.service.IArticleInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ArticleInfoServiceImpl extends ServiceImpl<ArticleInfoMapper, ArticleInfo> implements IArticleInfoService {

    @Resource
    private ArticleInfoMapper articleInfoMapper;

    @Override
    public ArticleInfoVO getDetail(Integer aid) {
        return articleInfoMapper.getDetail(aid);
    }

    @Override
    public int updateRCount(Integer aid) {
        return articleInfoMapper.updateRCount(aid);
    }
}
