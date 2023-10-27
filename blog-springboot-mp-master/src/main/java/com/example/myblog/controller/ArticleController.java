package com.example.myblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myblog.model.ArticleInfo;
import com.example.myblog.model.UserInfo;
import com.example.myblog.model.vo.ArticleInfoVO;
import com.example.myblog.model.vo.CommentInfoVO;
import com.example.myblog.service.IArticleInfoService;
import com.example.myblog.service.ICommentInfoService;
import com.example.myblog.util.AjaxResult;
import com.example.myblog.util.SessionUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@RestController
@RequestMapping("/art")
public class ArticleController {
    @Resource
    private IArticleInfoService articleInfoService;
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;
    @Resource
    private ICommentInfoService commentInfoService;
    // 文章简介的长度
    private final int DESC_LENGTH = 120;
    // 分页每页显示的最大条数
    private final int PAGE_PAGE_SIZE = 2;

    /**
     * 文章添加
     *
     * @param articleInfo
     * @return
     */
    @RequestMapping("/add")
    public AjaxResult add(ArticleInfo articleInfo, HttpServletRequest request) {
        // 1.非空判断
        if (articleInfo == null || !StringUtils.hasLength(articleInfo.getTitle()) ||
                !StringUtils.hasLength(articleInfo.getContent())) {
            // 参数有误
            return AjaxResult.fail(-1, "参数有误");
        }
        // 获取作者id
        UserInfo userInfo = SessionUtil.getUserInfo(request);
        if (userInfo == null) {
            return AjaxResult.fail(-2, "用户未登录");
        }
        // 设置作者
        articleInfo.setUid(userInfo.getUid());
        // 设置简介
        String content = articleInfo.getContent();
        if (content.length() > DESC_LENGTH) {
            content = content.substring(0, DESC_LENGTH);
        }
        articleInfo.setDesc(content);
        // 2.添加文章到数据库
        boolean result = articleInfoService.save(articleInfo);
        // 3.将上一步的结果返回给调用方
        if (result) {
            // 添加文章成功
            return AjaxResult.succ(1);
        } else {
            // 添加文章失败
            return AjaxResult.fail(-1, "操作失败，请重试！");
        }
    }

    /**
     * 文章的分页查
     *
     * @param pindex 页码（当前第几页）
     * @param psize  每页显示的最大条数
     * @return
     */
    @RequestMapping("/list")
    public AjaxResult getList(Integer pindex, Integer psize) {
        // 1.参数校正
        if (pindex == null || pindex <= 0) {
            // 无效的参数
            pindex = 1;
        }
        if (psize == null || psize <= 0) {
            // 无效的参数
            psize = PAGE_PAGE_SIZE;
        }
        // 2.查询数据库得到分页数据
        Page page = new Page(pindex, psize);
        Page<ArticleInfo> result = articleInfoService.page(page);
        // 3.将分页的数据返回给前端
        return AjaxResult.succ(result);
    }

    /**
     * 查询文章的详情
     *
     * @param aid
     * @return
     */
    @RequestMapping("/detail")
    public AjaxResult getDetail(Integer aid) {
        // 1.参数效验
        if (aid == null || aid <= 0) {
            return AjaxResult.fail(-1, "非法参数！");
        }
        // 2.查询数据库中的文章信息
        ArticleInfoVO articleInfoVO = articleInfoService.getDetail(aid);
        if (articleInfoVO == null || articleInfoVO.getAid() <= 0) {
            return AjaxResult.fail(-2, "文章查询失败！");
        }
        // 3.组装数据：查询当前用户总共发表的文章数
        QueryWrapper<ArticleInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", articleInfoVO.getUid());
        articleInfoVO.setArtCount((int) articleInfoService.count(queryWrapper));
        // 4.将最终组装好的解决返回给前端
        return AjaxResult.succ(articleInfoVO);
    }

    /**
     * 阅读量 +1
     *
     * @param aid
     * @return
     */
    @RequestMapping("/update_rcount")
    public AjaxResult updateRCount(Integer aid) {
        // 1.参数效验
        if (aid == null || aid <= 0) {
            return AjaxResult.fail(-1, "参数有误！");
        }
        // 2.修改数据库
        int result = articleInfoService.updateRCount(aid);
        // 3.将结果返回给前端
        return AjaxResult.succ(result);
    }

    /**
     * @param aid
     * @return
     */
    @RequestMapping("/getinfobyaid")
    public AjaxResult getInfoByAid(Integer aid) {
        // 1.参数效验
        if (aid == null || aid <= 0) {
            return AjaxResult.fail(-1, "参数有误！");
        }
        // 2.查询数据库
        ArticleInfo articleInfo = articleInfoService.getById(aid);
        // 3.将结果返回给前端
        return AjaxResult.succ(articleInfo);
    }

    /**
     * 修改操作
     *
     * @param articleInfo
     * @param request
     * @return
     */
    @RequestMapping("/update")
    public AjaxResult update(ArticleInfo articleInfo, HttpServletRequest request) {
        // 1.参数效验
        if (articleInfo == null || !StringUtils.hasLength(articleInfo.getTitle()) ||
                !StringUtils.hasLength(articleInfo.getContent()) ||
                articleInfo.getAid() == null || articleInfo.getAid() <= 0) {
            // 参数有误
            return AjaxResult.fail(-1, "参数有误");
        }
        // 2.组装数据
        // 获取作者id
        UserInfo userInfo = SessionUtil.getUserInfo(request);
        if (userInfo == null) {
            return AjaxResult.fail(-2, "用户未登录");
        }
//        // 设置作者
//        articleInfo.setUid(userInfo.getUid());
        // 设置简介
        String content = articleInfo.getContent();
        if (content.length() > DESC_LENGTH) {
            content = content.substring(0, DESC_LENGTH);
        }
        articleInfo.setDesc(content);
        // 3.执行数据库修改操作
        UpdateWrapper<ArticleInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("aid", articleInfo.getAid());
        updateWrapper.eq("uid", userInfo.getUid());  // 文章归属人效验
        boolean result = articleInfoService.update(articleInfo, updateWrapper);
        // 4.将结果返回给前端
        return AjaxResult.succ(result ? 1 : 0);
    }

    /**
     * 获取当前登录用户的所有文章
     *
     * @return
     */
    @RequestMapping("/mylist")
    public AjaxResult myList(HttpServletRequest request) {
        // 1.得到当前登录用户
        UserInfo userInfo = SessionUtil.getUserInfo(request);
        if (userInfo == null) {
            return AjaxResult.fail(-1, "请先登录！");
        }
        // 2.查询当前用户发表的所有文章
        QueryWrapper<ArticleInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", userInfo.getUid());
        queryWrapper.orderByDesc(true, "aid");
        List<ArticleInfo> list = articleInfoService.list(queryWrapper);
        // 3.将结果返回给前端
        return AjaxResult.succ(list);
    }

    /**
     * 删除文章
     *
     * @param aid
     * @return
     */
    @RequestMapping("/del")
    public AjaxResult del(Long aid, HttpServletRequest request) {
        // 1.效验参数
        if (aid == null || aid <= 0) {
            return AjaxResult.fail(-1, "非法参数！");
        }
        // 2.效验文章的归属人（不是我的文章是不能删除的）
        UserInfo userInfo = SessionUtil.getUserInfo(request);
        if (userInfo == null) {
            return AjaxResult.fail(-2, "请先登录！");
        }
        ArticleInfo articleInfo = articleInfoService.getById(aid);
        if (articleInfo == null) {
            return AjaxResult.fail(-1, "非法参数！");
        }
        if (articleInfo.getUid() != userInfo.getUid()) {
            // 当前文章不属于当前登陆用户，不能删除
            return AjaxResult.fail(-3, "不能删除别人的文章！");
        }
        // 3.执行数据库的删除操作
        boolean result = articleInfoService.removeById(aid);
        // 4.返回给前端
        return AjaxResult.succ(result ? 1 : 0);
    }

    /**
     * 详情页接口初始化合并方法
     *
     * @param aid
     * @return
     */
    @RequestMapping("/total_init")
    public AjaxResult totalInit(Integer aid, HttpServletRequest request) throws ExecutionException, InterruptedException {
        // 1.参数效验
        if (aid == null || aid <= 0) {
            return AjaxResult.fail(-1, "非法参数！");
        }
        // 2.创建多个任务，放到线程池中进行执行
        // 2.1 得到文章详情信息
        FutureTask<ArticleInfoVO> getArtinfo = new FutureTask<>(() -> {
            // 查询数据库中的文章信息
            ArticleInfoVO articleInfoVO = articleInfoService.getDetail(aid);
            if (articleInfoVO != null) {
                // 组装数据：查询当前用户总共发表的文章数
                QueryWrapper<ArticleInfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("uid", articleInfoVO.getUid());
                articleInfoVO.setArtCount((int) articleInfoService.count(queryWrapper));
            }
            return articleInfoVO;
        });
        // 2.2 得到当前文章的评论列表
        FutureTask<List<CommentInfoVO>> getCommList = new FutureTask<>(() -> {
            return commentInfoService.getList(aid);
        });
        // 2.3 当前文章阅读量 ++
        FutureTask<Integer> upRCount = new FutureTask(() -> {
            return articleInfoService.updateRCount(aid);
        });
        // 2.4 当前登录用户的信息返回给前端
        FutureTask<UserInfo> getSess = new FutureTask<>(() -> {
            return SessionUtil.getUserInfo(request);
        });
        // 将任务放到线程池中并发执行
        taskExecutor.submit(getArtinfo);
        taskExecutor.submit(getCommList);
        taskExecutor.submit(upRCount);
        taskExecutor.submit(getSess);
        // 3.组装并发执行的结果，返回给前端
        ArticleInfoVO articleInfoVO = getArtinfo.get();
        List<CommentInfoVO> commentList = getCommList.get();
        UserInfo userInfo = getSess.get();
        // 组装并发任务返回的结果
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("articleinfo", articleInfoVO);
        resultMap.put("commentList", commentList);
        resultMap.put("userinfo", userInfo);
        return AjaxResult.succ(resultMap);
    }

}
