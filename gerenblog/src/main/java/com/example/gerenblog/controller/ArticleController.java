package com.example.gerenblog.controller;

import com.example.gerenblog.common.AjaxResult;
import com.example.gerenblog.common.Constant;
import com.example.gerenblog.common.SessionUtil;
import com.example.gerenblog.model.ArticleInfo;
import com.example.gerenblog.model.UserInfo;
import com.example.gerenblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-22
 * Time: 15:40(李明浦)
 */
@RestController
@RequestMapping("/art")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/mylist")
    public List<ArticleInfo> myList(HttpServletRequest request){
        UserInfo userInfo = SessionUtil.getLoginUser(request);
        if(userInfo != null){
            return articleService.getMyList(userInfo.getId());
        }
        return null;
    }

    @RequestMapping("/detail")
    public Object getDetail(Integer aid){
        if(aid != null && aid > 0){
            return AjaxResult.success(articleService.getDetail(aid));
        }
        return AjaxResult.fail(-1,"查询失败");
    }

    @RequestMapping("/detailbyid")
    public Object getDetailById(HttpServletRequest request,Integer aid){
        if(aid != null && aid > 0){
            ArticleInfo articleInfo = articleService.getDetail(aid);
            UserInfo userInfo = SessionUtil.getLoginUser(request);
            if(userInfo != null && articleInfo != null && userInfo.getId() == articleInfo.getUid()){
                return AjaxResult.success(articleInfo);
            }
        }
        return AjaxResult.fail(-1,"查询失败");
    }

    @RequestMapping("/update")
    public int update(HttpServletRequest request,Integer aid,String title,String content){
        //todo：非空校验
        UserInfo userInfo = SessionUtil.getLoginUser(request);
        if(userInfo != null && userInfo.getId() > 0){
           return articleService.update(aid,userInfo.getId(),title,content);
        }
        return 0;
    }

    @RequestMapping("/list")
    public List<ArticleInfo> getList(Integer pindex,Integer psize){
        if(pindex == null || psize == null){
             return null;
        }
        int offset = (pindex - 1)* psize;
        return articleService.getList(psize,offset);
    }

    @RequestMapping("/totalpage")
    public Integer totalPage(Integer psize){
        if(psize != null){
            return (int) Math.ceil(articleService.totalCount() * 1.0 / psize);
        }
        return null;
    }
}
