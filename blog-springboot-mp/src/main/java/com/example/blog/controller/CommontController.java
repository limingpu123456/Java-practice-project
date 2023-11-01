package com.example.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.blog.model.ArticleInfo;
import com.example.blog.model.CommentInfo;
import com.example.blog.model.UserInfo;
import com.example.blog.model.vo.CommentInfoVO;
import com.example.blog.service.IArticleService;
import com.example.blog.service.ICommontService;
import com.example.blog.service.impl.ArticleServiceImpl;
import com.example.blog.util.AjaxResult;
import com.example.blog.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description：
 * User: lmp
 * Date: 2023-10-15
 * Time: 11:42(李明浦)
 */
@RequestMapping("/comment")
@RestController
public class CommontController {

    @Autowired
    private ICommontService commontService;

    @Autowired
    private IArticleService articleService;

    /**
     * 查询某篇文章下的所有评论
     * @param aid
     * @return
     */
    @RequestMapping("/list")
    public AjaxResult getList(Integer aid){
        //1.参数校验
        if(aid == null || aid <= 0){
            return AjaxResult.fail(-1,"参数有误");
        }
        //2.查询数据库
        List<CommentInfoVO> list = commontService.getList(aid);
        //3.将结果返回给前端
        return AjaxResult.succ(list);
    }

    @RequestMapping("/add")
    public AjaxResult add(Long aid, String content, HttpServletRequest request){
        //1.参数校验
        if(aid == null || aid <= 0 || !StringUtils.hasLength(content)){
            //非法参数
            return AjaxResult.fail(-1,"非法参数");
        }
        //2.组装数据(将uid从session取出来)
        UserInfo userInfo = SessionUtil.getUserInfo(request);
        if(userInfo == null || userInfo.getUid() <= 0){
            //用户登录有问题
            return AjaxResult.fail(-2,"请先登录");
        }
        CommentInfo commentInfo = new CommentInfo();
        commentInfo.setAid(aid);
        commentInfo.setUid(userInfo.getUid());
        commentInfo.setContent(content);
        //3.将评论对象插入到数据库
        boolean result =  commontService.save(commentInfo);
        //4.将数据库执行的结果返回给前端
        return AjaxResult.succ(result?1:0);
    }

    @RequestMapping("/del")
    public AjaxResult del(Long cid,Long aid,HttpServletRequest request){
        //1.参数校验
        if(cid == null || cid <= 0 || aid == null || aid <= 0){
            return AjaxResult.fail(-1,"非法参数");
        }
        //2.校验归属人(判断当前文章是否属于当前登录用户)
        UserInfo userInfo = SessionUtil.getUserInfo(request);
        if(userInfo == null || userInfo.getUid()<=0){
            //无效登录用户
            return AjaxResult.fail(-2,"请先登录！");
        }

        //3.删除评论，操作数据库
        ArticleInfo articleInfo =  articleService.getById(aid);
        if(articleInfo == null || articleInfo.getAid() <= 0){
            return AjaxResult.fail(-3,"非法的文章id");
        }
        if(userInfo.getUid() != articleInfo.getUid()){
            return AjaxResult.fail(-4,"非法操作！");
        }
        //4.将结果返回给前端
        return AjaxResult.succ(commontService.removeById(cid)? 1 : 0) ;
    }
}
