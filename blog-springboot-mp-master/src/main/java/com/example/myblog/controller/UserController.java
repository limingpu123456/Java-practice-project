package com.example.myblog.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.myblog.model.ArticleInfo;
import com.example.myblog.model.UserInfo;
import com.example.myblog.model.vo.UserInfoVO;
import com.example.myblog.service.IArticleInfoService;
import com.example.myblog.service.IUserService;
import com.example.myblog.util.AjaxResult;
import com.example.myblog.util.AppVar;
import com.example.myblog.util.PasswordUtil;
import com.example.myblog.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 用户控制器 -> 所有前端请求会先经过此控制
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IArticleInfoService articleInfoService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${imagepath}")
    private String imagePath; // 图片的保存地址

    @RequestMapping("/reg")
    public AjaxResult reg(UserInfoVO userInfoVO) {
        // 1.效验参数的正确性
        // 1.1 非空效验
        if (userInfoVO == null || !StringUtils.hasLength(userInfoVO.getLoginname()) ||
                !StringUtils.hasLength(userInfoVO.getPassword()) ||
                !StringUtils.hasLength(userInfoVO.getCheckCode())) {
            // 非法参数
            return AjaxResult.fail(-1, "非法参数");
        }
        // 密码加盐
        userInfoVO.setPassword(PasswordUtil.encrypt(userInfoVO.getPassword()));
        // todo:1.2 验证验证码是否正确
        // 2.调用服务执行添加操作（添加到数据库）
        int result = userService.reg(userInfoVO);
        // 3.将执行结果返回给前端
        return AjaxResult.succ(result);
    }

    @RequestMapping("/login")
    public AjaxResult login(UserInfoVO userInfoVO, HttpSession session) {
        // 1.非空效验
        if (userInfoVO == null || !StringUtils.hasLength(userInfoVO.getLoginname()) ||
                !StringUtils.hasLength(userInfoVO.getCheckCode()) ||
                !StringUtils.hasLength(userInfoVO.getCodeKey())) {
            // 参数有误
            return AjaxResult.fail(-1, "非法参数");
        }
        // redis 里面 key 对应的真是的验证码
        String redisCodeValue = (String) redisTemplate.opsForValue().get(userInfoVO.getCodeKey());
        if (!StringUtils.hasLength(redisCodeValue) || !redisCodeValue.equals(userInfoVO.getCheckCode())) {
            // 验证码不正确
            return AjaxResult.fail(-1, "验证码错误");
        }
        // 清除 redis 中的验证码
        redisTemplate.opsForValue().set(userInfoVO.getCodeKey(), "");
        // 2.根据登录名查询到用户对象
        UserInfo userInfo = userService.getUserByLoginName(userInfoVO.getLoginname());
        // 3.判断用户对象是否为空，说明当前登录名不存在
        if (userInfo == null || userInfo.getUid() <= 0) {
            // 用户对象为null或者用户为无效对象
            return AjaxResult.fail(-1, "用户名或密码错误！");
        }
        // 4.如果用户对象不为空，使用用户输入的密码和用户对象的真实密码
        // 进行比较
        if (PasswordUtil.decrypt(userInfoVO.getPassword(), userInfo.getPassword())) {
            // 5.如果输入密码和真实的密码相同，将用户对象存储到 Session
            session.setAttribute(AppVar.SESSION_KEY_USERINFO, userInfo);
            userInfo.setPassword(""); // 传递对象之前，先将敏感信息密码去除
            return AjaxResult.succ(userInfo);
        } else {
            // 6.如果输入的密码和真实密码不相同，说明密码错误
            return AjaxResult.fail(-1, "用户名或密码错误！");
        }
    }

    @RequestMapping("/logout")
    public AjaxResult logout(HttpSession session) {
        try {
            // 清楚当前登录用户的 session 信息
            session.removeAttribute(AppVar.SESSION_KEY_USERINFO);
        } catch (Exception e) {
            return AjaxResult.fail(-1, "清楚失败");
        }
        return AjaxResult.succ(1);
    }

    @RequestMapping("/getuser")
    public AjaxResult getUser() {
        List<UserInfo> list = userService.list();
        System.out.println(list);
        return AjaxResult.succ(list);
    }

    @RequestMapping("/getsess")
    public AjaxResult getSess(HttpServletRequest request) {
        UserInfo userInfo = SessionUtil.getUserInfo(request);
        if (userInfo == null || userInfo.getUid() <= 0) {
            return AjaxResult.fail(-2, "当前用户未登录！");
        }
        return AjaxResult.succ(userInfo);
    }

    /**
     * 查询当前文章是否属于当前登录用户
     */
    @RequestMapping("/isartbyme")
    public AjaxResult isArtByMe(Integer aid, HttpServletRequest request) {
        if (aid == null || aid <= 0) {
            return AjaxResult.fail(-1, "参数有误！");
        }
        UserInfo userInfo = SessionUtil.getUserInfo(request);
        if (userInfo == null || userInfo.getUid() <= 0) {
            return AjaxResult.fail(-2, "当前用户未登录！");
        }
        ArticleInfo articleInfo = articleInfoService.getById(aid);
        if (articleInfo != null && articleInfo.getAid() >= 0
                && articleInfo.getUid() == userInfo.getUid()) {
            // 文章是当前登录用户的
            return AjaxResult.succ(1);
        }
        return AjaxResult.succ(0);
    }

    @RequestMapping("/save_photo")
    public AjaxResult savePhoto(MultipartFile file, HttpServletRequest request) {
        // 1.保存图片到服务器
        // 得到图片的后缀
        String imageType = file.getOriginalFilename().
                substring(file.getOriginalFilename().lastIndexOf("."));
        // 生成图片的名称
        String imgName = UUID.randomUUID().toString()
                .replace("-", "") + imageType;
        try {
            file.transferTo(new File(imagePath + imgName));
        } catch (IOException e) {
            return AjaxResult.fail(-1, "图片上传失败！");
        }
        String imgUrl = "/image/" + imgName;
        // 2.将图片地址保存到数据库
        UserInfo userInfo = SessionUtil.getUserInfo(request);
        if (userInfo == null || userInfo.getUid() <= 0) {
            // 未登录
            return AjaxResult.fail(-2, "请先登录！");
        }
        UpdateWrapper<UserInfo> wrapper = new UpdateWrapper<>();
        wrapper.set(true, "photo", imgUrl);
        wrapper.eq("uid", userInfo.getUid());
        boolean result = userService.update(wrapper);
        if (result) {
            // 图片保存成功
            // 更新头像到 session 中
            userInfo.setPhoto(imgUrl);
            HttpSession session = request.getSession();
            session.setAttribute(AppVar.SESSION_KEY_USERINFO, userInfo);
            return AjaxResult.succ(imgUrl);
        } else {
            return AjaxResult.fail(-3, "数据库修改失败");
        }
    }

    /**
     * 个人中心修改昵称或密码
     *
     * @param nickname
     * @param oldpassword
     * @param password
     * @param isUpdatePassword
     * @return
     */
    @RequestMapping("/update")
    public AjaxResult update(String nickname, String oldpassword,
                             String password, Boolean isUpdatePassword,
                             HttpServletRequest request) {
        // 1.参数效验
        if (!StringUtils.hasLength(nickname)) {
            return AjaxResult.fail(-1, "非法参数！");
        }
        // 是否要修改密码
        if (isUpdatePassword) {
            // 修改原密码
            if (!StringUtils.hasLength(oldpassword) || !StringUtils.hasLength(password)) {
                return AjaxResult.fail(-1, "非法参数！");
            }
        }
        // 2.组装数据（从 Session 获取用户信息）
        UserInfo userInfo = SessionUtil.getUserInfo(request);
        if (userInfo == null || userInfo.getUid() <= 0) {
            return AjaxResult.fail(-2, "请先登录！");
        }
        userInfo.setNickname(nickname);
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        // 判断是否修改密码，如果修改密码，则需要验证用户输入的原密码是否正确
        if (isUpdatePassword) {
            UserInfo dbUser = userService.getById(userInfo.getUid());
            // 需要修改密码，验证原密码是否正确
            boolean checkPassword = PasswordUtil.decrypt(oldpassword, dbUser.getPassword());
            if (!checkPassword) {
                return AjaxResult.fail(-3, "原密码输入错误！");
            }
            // 修改密码
            password = PasswordUtil.encrypt(password); // 加盐之后的密码
            updateWrapper.set("password", password);
        }
        // 3.修改数据库
        updateWrapper.eq("uid", userInfo.getUid());
        updateWrapper.set("nickname", nickname);
        boolean result = userService.update(updateWrapper);
        // 4.将结果返回给前端
        return AjaxResult.succ(result ? 1 : 0);
    }

}














