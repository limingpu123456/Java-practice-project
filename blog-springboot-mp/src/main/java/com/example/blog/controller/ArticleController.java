package com.example.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.model.ArticleInfo;
import com.example.blog.model.UserInfo;
import com.example.blog.model.vo.ArticleInfoVO;
import com.example.blog.model.vo.CommentInfoVO;
import com.example.blog.service.ICommontService;
import com.example.blog.service.impl.ArticleServiceImpl;
import com.example.blog.util.AjaxResult;
import com.example.blog.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Description：
 * User: lmp
 * Date: 2023-10-09
 * Time: 23:02(李明浦)
 */
@RestController
@RequestMapping("/art")
public class ArticleController {

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private ICommontService commontService;

    private final static int _MAX_DESC_LENGTH = 120; // 简介的最大长度
    private final static int PAGE_SIZE = 2; // 每页显示的最大条数

    /**
     * 添加文章
     * @param articleInfo
     * @return
     */
    @RequestMapping("/add")
    public AjaxResult add(ArticleInfo articleInfo, HttpServletRequest request){
        //1.参数校验
        if(articleInfo == null || !StringUtils.hasLength(articleInfo.getTitle())||
                !StringUtils.hasLength(articleInfo.getContent())){
            //参数有误
            AjaxResult.fail(-1,"非法参数");
        }
        //2.组装数据[作者id，文章简介]
        UserInfo userInfo = SessionUtil.getUserInfo(request);
        if(userInfo == null || userInfo.getUid()<=0){
            return AjaxResult.fail(-2,"请先登录！");
        }
        articleInfo.setUid(userInfo.getUid());
        String desc = articleInfo.getContent();
        if(desc.length()>_MAX_DESC_LENGTH){
            desc = desc.substring(0,_MAX_DESC_LENGTH);
        }
        articleInfo.setDesc(desc);
        //3.进行数据库的添加操作
        boolean result = articleService.save(articleInfo);
        //4.将结果返回给前端
        return AjaxResult.succ(result?1:0);
    }

    @RequestMapping("/getinfobyaid")
    public AjaxResult getInfoByAid(Integer aid){
        //1.参数校验
        if(aid == null || aid <= 0){
            return AjaxResult.fail(-1,"参数有误！");
        }
        //2.查询数据库
        QueryWrapper<ArticleInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("aid",aid);
        articleService.getOne(queryWrapper);
        ArticleInfo articleInfo = articleService.getById(aid);
        //3.将结果返回给前端
        return AjaxResult.succ(articleInfo);
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
        List<ArticleInfo> list = articleService.list(queryWrapper);
        // 3.将结果返回给前端
        return AjaxResult.succ(list);
    }

    @RequestMapping("/update")
    public AjaxResult update(ArticleInfo articleInfo,HttpServletRequest request){
        //1.参数校验
        if(articleInfo == null || !StringUtils.hasLength(articleInfo.getTitle())||
                !StringUtils.hasLength(articleInfo.getContent()) ||
                articleInfo.getAid() == null || articleInfo.getAid() <= 0){
            //参数有误
            AjaxResult.fail(-1,"非法参数");
        }
        //2.组装数据[作者id，文章简介]
        UserInfo userInfo = SessionUtil.getUserInfo(request);
        if(userInfo == null || userInfo.getUid()<=0){
            return AjaxResult.fail(-2,"请先登录！");
        }
        articleInfo.setUid(userInfo.getUid());
        String desc = articleInfo.getContent();
        if(desc.length()>_MAX_DESC_LENGTH){
            desc = desc.substring(0,_MAX_DESC_LENGTH);
        }
        articleInfo.setDesc(desc);
        //3.执行数据库修改操作
        UpdateWrapper<ArticleInfo> updateWrapper  = new UpdateWrapper<>();
        updateWrapper.eq("aid",articleInfo.getAid());
        updateWrapper.eq("uid", userInfo.getUid());
        boolean result = articleService.update(articleInfo,updateWrapper);
        //4.将结果返回给前端
        return AjaxResult.succ(result ? 1 : 0);
    }

    /**
     * 文章分页查询
     * pindex 当前页码
     * psize  每页显示的最大条数
     * @return
     */

    @RequestMapping("/list")
    public AjaxResult getList(Integer pindex,Integer psize){
        //1.参数矫正
        if(pindex == null || pindex <= 0){
            //无效的参数
            pindex = 1;
        }
        if(psize == null || psize <= 0){
            psize = PAGE_SIZE;
        }
        //2.查询数据库得到分页数据
        Page page = new Page(pindex,psize);
        Page<ArticleInfo> result = articleService.page(page);
        //3.将分页的数据返回给前端
        return AjaxResult.succ(result);
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
        ArticleInfo articleInfo = articleService.getById(aid);
        if (articleInfo == null) {
            return AjaxResult.fail(-1, "非法参数！");
        }
        if (articleInfo.getUid() != userInfo.getUid()) {
            // 当前文章不属于当前登陆用户，不能删除
            return AjaxResult.fail(-3, "不能删除别人的文章！");
        }
        // 3.执行数据库的删除操作
        boolean result = articleService.removeById(aid);
        // 4.返回给前端
        return AjaxResult.succ(result ? 1 : 0);
    }

    /**
     * 查询文章的详情
     * @param aid
     * @return
     */
    @RequestMapping("/detail")
    public AjaxResult getDetail(Integer aid){
        //1.参数校验
        if(aid == null || aid <= 0){
            return AjaxResult.fail(-1,"非法参数");
        }
        //2.查询数据库中的文章信息
        ArticleInfoVO articleInfoVO = articleService.getDetail(aid);
        if(articleInfoVO == null || articleInfoVO.getAid() <= 0){
            return AjaxResult.fail(-2,"文章查询失败");
        }
        //3.组装数据(因为总文章数需要单独查询，需要数据拼接)
        QueryWrapper<ArticleInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",articleInfoVO.getUid());
        articleInfoVO.setArtCount((int)articleService.count(queryWrapper));
        //4.将组装好的结果返回给前端
        return AjaxResult.succ(articleInfoVO);
    }

    /**
     * 更新文章阅读量
     * @param aid
     * @return
     */
    @RequestMapping("/update_rcount")
    public AjaxResult updateRCount(Integer aid){
        //1.参数校验
        if(aid == null || aid <= 0){
            return AjaxResult.fail(-1,"参数有误");
        }
        //2.修改数据库
        int result = articleService.updateRCount(aid);
        //3.将结果返回给前端
        return AjaxResult.succ(result);
    }

    /**
     * 详情页接口初始化合并方法
     * @param aid
     * @return
     */
    @RequestMapping("/total_init")
    public AjaxResult totalInit(Integer aid,HttpServletRequest request) throws ExecutionException, InterruptedException {
        //1.参数校验
        if(aid == null || aid <= 0){
            return AjaxResult.fail(-1,"非法参数");
        }
        //2.创建多个任务，放到线程池中执行
        //得到文章详情信息
        FutureTask<ArticleInfoVO> getArtinfo = new FutureTask<>(()->{
            //查询数据库中的文章信息
            ArticleInfoVO articleInfoVO = articleService.getDetail(aid);
            if(articleInfoVO != null){
                //组装数据(因为总文章数需要单独查询，需要数据拼接)
                QueryWrapper<ArticleInfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("uid",articleInfoVO.getUid());
                articleInfoVO.setArtCount((int)articleService.count(queryWrapper));
            }
            return articleInfoVO;
        });
        //得到当前文章的评论列表
        FutureTask<List<CommentInfoVO>> getCommList = new FutureTask<>(()->{
            return commontService.getList(aid);
        });
        //得到当前文章的阅读量
        FutureTask<Integer> upRCount = new FutureTask(()->{
            return articleService.updateRCount(aid);
        });
        //当前登录用户的信息返回给前端
        FutureTask<UserInfo> getSess = new FutureTask<>(()->{
            return SessionUtil.getUserInfo(request);
        });
        //将任务放到线程池中并发执行
        taskExecutor.submit(getArtinfo);
        taskExecutor.submit(getCommList);
        taskExecutor.submit(upRCount);
        taskExecutor.submit(getSess);
        //3.组装并发执行的结果，返回给前端
        ArticleInfoVO articleInfoVO = getArtinfo.get();
        List<CommentInfoVO> commentInfoVO = getCommList.get();
        UserInfo userInfo = getSess.get();
        HashMap<String,Object> resultMap = new HashMap<>();
        resultMap.put("articleinfo",articleInfoVO);
        resultMap.put("commentList",commentInfoVO);
        resultMap.put("userinfo",userInfo);
        return AjaxResult.succ(resultMap);
    }

}
