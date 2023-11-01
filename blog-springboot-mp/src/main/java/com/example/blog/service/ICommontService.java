package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.model.CommentInfo;
import com.example.blog.model.vo.CommentInfoVO;

import java.util.List;

/**
 * Description：
 * User: lmp
 * Date: 2023-10-15
 * Time: 11:23(李明浦)
 */
public interface ICommontService extends IService<CommentInfo> {
    List<CommentInfoVO> getList(Integer aid);
}
