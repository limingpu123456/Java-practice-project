package com.example.myblog.controller;

import com.example.myblog.model.ArticleInfo;
import com.example.myblog.model.CommentInfo;
import com.example.myblog.model.UserInfo;
import com.example.myblog.model.vo.CommentInfoVO;
import com.example.myblog.service.IArticleInfoService;
import com.example.myblog.service.ICommentInfoService;
import com.example.myblog.util.AjaxResult;
import com.example.myblog.util.SessionUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 评论控制器
 */
@RequestMapping("/comment")
@RestController
public class CommentController {
    @Resource
    private ICommentInfoService commentInfoService;
    @Resource
    private IArticleInfoService articleInfoService;

    /**
     * 查询某篇文章下的所有评论
     *
     * @param aid
     * @return
     */
    @RequestMapping("/list")
    public AjaxResult getList(Integer aid) {
        // 1.参数效验
        if (aid == null && aid <= 0) {
            return AjaxResult.fail(-1, "参数有误！");
        }
        // 2.查询数据库
        List<CommentInfoVO> list = commentInfoService.getList(aid);
        // 3.将结果返回给前端
        return AjaxResult.succ(list);
    }

    @RequestMapping("/add")
    public AjaxResult add(Long aid, String content, HttpServletRequest request) {
        // 1.参数效验
        if (aid == null || aid <= 0 || !StringUtils.hasLength(content)) {
            // 非法参数
            return AjaxResult.fail(-1, "非法参数");
        }
        // 2.组装数据（将 uid 从 session 中获取出来）
        UserInfo userInfo = SessionUtil.getUserInfo(request);
        if (userInfo == null || userInfo.getUid() <= 0) {
            // 用户登录有问题
            return AjaxResult.fail(-2, "请先登录！");
        }
        CommentInfo commentInfo = new CommentInfo();
        commentInfo.setAid(aid);
        commentInfo.setUid(userInfo.getUid());
        commentInfo.setContent(content);
        // 3.将评论对象插入到数据库
        boolean result = commentInfoService.save(commentInfo);
        // 4.将数据库执行的结果返回给前端
        return AjaxResult.succ(result ? 1 : 0);
    }

    @RequestMapping("/del")
    public AjaxResult del(Long cid, Long aid, HttpServletRequest request) {
        // 1.参数效验
        if (cid == null || cid <= 0 || aid == null || aid <= 0) {
            return AjaxResult.fail(-1, "非法参数！");
        }
        // 2.效验权限（判断当前的文章是否属于当前登录用户）
        // 2.1 从 session 获取到用户 id
        UserInfo userInfo = SessionUtil.getUserInfo(request);
        if (userInfo == null || userInfo.getUid() <= 0) {
            // 无效登录用户
            return AjaxResult.fail(-2, "请先登录！");
        }
        // 2.2 从文章中获取到用户 id
        ArticleInfo articleInfo = articleInfoService.getById(aid);
        if (articleInfo == null || articleInfo.getAid() <= 0) {
            return AjaxResult.fail(-3, "非法的文章id");
        }
        // 2.3 判断文章uid是否等于session uid
        //   如果相等，说明当前文章属于当前登录用户，可以进行删除操作
        //   否则是不能进行删除操作的
        if (userInfo.getUid() != articleInfo.getUid()) {
            return AjaxResult.fail(-4, "非法操作！");
        }
        // 3.删除文章（操作数据库）
        boolean result = commentInfoService.removeById(cid);
        // 4.将结果返回给前端
        return AjaxResult.succ(result ? 1 : 0);
    }

}
