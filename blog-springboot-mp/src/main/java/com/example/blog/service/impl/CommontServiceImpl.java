package com.example.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.dao.CommontMapper;
import com.example.blog.model.CommentInfo;
import com.example.blog.model.vo.CommentInfoVO;
import com.example.blog.service.ICommontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description：
 * User: lmp
 * Date: 2023-10-15
 * Time: 11:24(李明浦)
 */
@Service
public class CommontServiceImpl extends ServiceImpl<CommontMapper, CommentInfo> implements ICommontService {

    @Autowired(required = false)
    private CommontMapper commontMapper;

    @Override
    public List<CommentInfoVO> getList(Integer aid) {
        return commontMapper.getList(aid);
    }
}
