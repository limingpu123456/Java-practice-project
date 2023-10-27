package com.example.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myblog.model.CommentInfo;
import com.example.myblog.model.vo.CommentInfoVO;

import java.util.List;

public interface ICommentInfoService extends IService<CommentInfo> {
    List<CommentInfoVO> getList(Integer aid);
}
