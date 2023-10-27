package com.example.gerenblog.service;

import com.example.gerenblog.mapper.ArticleMapper;
import com.example.gerenblog.model.ArticleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-22
 * Time: 15:41(李明浦)
 */
@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    public List<ArticleInfo> getMyList(Integer uid){
        return articleMapper.getMyList(uid);
    }

    public ArticleInfo getDetail(Integer aid){
        return articleMapper.getDetail(aid);
    }

    public int update(Integer aid,Integer uid,String title,String content){
        return articleMapper.update(aid,uid,title,content);
    }

    public List<ArticleInfo> getList(Integer psize, Integer offset){
        return articleMapper.getList(psize,offset);
    }

    public int totalCount(){
        return articleMapper.getTotalCount();
    }

}
