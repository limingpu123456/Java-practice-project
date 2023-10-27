package com.example.usermanager.controller;

import com.example.usermanager.model.UserInfo;
import com.example.usermanager.service.UserService;
import com.example.usermanager.util.ConstVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-25
 * Time: 12:23(李明浦)
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public boolean login(HttpServletRequest request, String loginname, String password){
        if(StringUtils.hasLength(loginname) && StringUtils.hasLength(password)){
            UserInfo userInfo = userService.login(loginname,password);
            if(userInfo!=null && userInfo.getUid()>0){
                //存储session
                HttpSession session = request.getSession(true);
                session.setAttribute(ConstVariable.USER_SESSION_KEY,userInfo);
                return true;
            }
        }
        return false;
    }

    @RequestMapping("/list")
    public List<UserInfo> getAll(){
        return userService.getAll();
    }

    @RequestMapping("/listbypage")
    public HashMap<String,Object> getListByPage(String username,String address,String email, Integer pindex,Integer psize){
        HashMap<String,Object> result = new HashMap<>();
        if(pindex == null || pindex < 1){
            pindex = 1;
        }
        if(psize == null || psize <= 0){
            psize = 2;
        }
        if(!StringUtils.hasLength(username)){
            username = null;
        }
        if(!StringUtils.hasLength(email)){
            email = null;
        }
        if(!StringUtils.hasLength(address)){
            address = null;
        }
        //表示参数是有效的
        int offset = (pindex - 1) * psize;
        List<UserInfo> list = userService.getListByPage(username,address,email,psize,offset);
        int totalCount = userService.getListByPageCount(username,address,email);
        //从数据库中获取的到数据，一层一层传递给controller，最后返回给前端，前端调用接口，通过回调函数获取后端返回的数据
        result.put("list",list);
        result.put("count",totalCount);
        System.out.println(result);
        return result;
    }

    //添加用户
    @RequestMapping("/add")
    public int add(HttpServletRequest request,UserInfo userInfo){
        int result = 0;
        //1.非空校验
        if(userInfo == null || !StringUtils.hasLength(userInfo.getUsername()) || !StringUtils.hasLength(userInfo.getLoginname())
        || !StringUtils.hasLength(userInfo.getPassword())) return 0;
        //2.判断必须为超级管理员才能进行添加操作
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute(ConstVariable.USER_SESSION_KEY) == null) return 0;
        UserInfo loginUser = (UserInfo) session.getAttribute(ConstVariable.USER_SESSION_KEY);
        if(!loginUser.isIsadmin()) return result;
        //3.登录名是否为重复
        UserInfo loginNameUser = userService.getUserByLoginName(userInfo.getLoginname());
        if(loginNameUser!=null && loginNameUser.getUid()>0) return result;
        //4.添加用户到数据库
        result = userService.add(userInfo);
        return result;
    }

    @RequestMapping("/getuserbyuid")
    public UserInfo getUserByUid(Integer uid){
        UserInfo userInfo = null;
        //非空校验
        if(uid == null || uid <= 0){
            return userInfo;
        }
        //查询数据库
        userInfo = userService.getUserByUid(uid);
        //将密码隐藏掉
        userInfo.setPassword("");
        return userInfo;
    }

    @RequestMapping("/update")
    public int update(UserInfo userInfo,HttpServletRequest request){
        int result = 0;
        //1.非空校验
        if(userInfo == null ||  userInfo.getUid()<=0 ||
                !StringUtils.hasLength(userInfo.getUsername())) return 0;
        //2.判断必须为超级管理员才能进行添加操作
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute(ConstVariable.USER_SESSION_KEY) == null) return 0;
        UserInfo loginUser = (UserInfo) session.getAttribute(ConstVariable.USER_SESSION_KEY);
        if(!loginUser.isIsadmin()) return result;
        //4.添加用户到数据库
        result = userService.update(userInfo);
        return result;
    }

    @RequestMapping("/del")
    public int del(Integer uid){
        if(uid == null) return 0;
        int result = 0;
        result = userService.del(uid);
        return result;
    }

    @Transactional
    @RequestMapping("/delbyids")
    public int dels(String ids,HttpServletRequest request){
        if(!StringUtils.hasLength(ids)) return 0;
        String[] idsArr =  ids.split(",");
        if(idsArr == null || idsArr.length <= 0) return 0;
        List<Integer> idsList = new ArrayList<>();
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute(ConstVariable.USER_SESSION_KEY) == null)
        {
            return 0;
        }
        int uid = ((UserInfo)session.getAttribute(ConstVariable.USER_SESSION_KEY)).getUid();
        for(String item : idsArr){
            if(StringUtils.hasLength(item)){
                int thisuid = Integer.valueOf(item);
                if(uid == thisuid){
                    return 0;
                }
                idsList.add(thisuid);
            }
        }
        int result =userService.dels(idsList);
        System.out.println("删除多条数据结果" + result);
        //回滚事务
        //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return result;
    }
}
