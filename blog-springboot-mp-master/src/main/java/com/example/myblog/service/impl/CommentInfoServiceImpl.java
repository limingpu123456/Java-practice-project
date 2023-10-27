package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.dao.CommentInfoMapper;
import com.example.myblog.model.CommentInfo;
import com.example.myblog.model.vo.CommentInfoVO;
import com.example.myblog.service.ICommentInfoService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentInfoServiceImpl extends ServiceImpl<CommentInfoMapper, CommentInfo> implements ICommentInfoService  {

    @Resource
    private CommentInfoMapper commentInfoMapper;

    @Override
    public List<CommentInfoVO> getList(Integer aid) {
        return commentInfoMapper.getList(aid);
    }
}
