package org.example.service;

import com.sun.xml.internal.ws.client.ClientTransportException;
import org.example.config.ExceptionAdvice;
import org.example.exception.AppException;
import org.example.mapper.SettingMapper;
import org.example.mapper.UserMapper;
import org.example.model.Setting;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SettingMapper settingMapper;

    private static final DateFormat DF = new SimpleDateFormat("yyyyMMdd");

    @Value("${user.head.local-path}")
    private String headLocalPath;

    @Value("${user.head.remote-path}")
    private String headRemotePath;

    public String saveHead(MultipartFile headFile) {//保存在本地路径：拍脑门决定的路径
        //文件夹为当天：文件路径的间隔符和操作系统相关，可以使用File.separator，但是java也会根据操作系统自行设置
        Date now = new Date();
        String dirUri = "/"+DF.format(now);//20210203
        File dir = new File(headLocalPath+dirUri);
        if(!dir.exists()) dir.mkdirs();
        //保存在本地以天为单位的文件夹，保证文件唯一：随机字符串作为文件名，但是后缀还需要保留
        String suffix = headFile.getOriginalFilename()
                .substring(headFile.getOriginalFilename().lastIndexOf("."));
        String headName = UUID.randomUUID().toString()+suffix;
        String uri = dirUri+"/"+headName;
        try {
            headFile.transferTo(new File(headLocalPath+uri));
        } catch (IOException e) {
            throw new AppException("REG001", "上传用户头像出错");
        }
        return headRemotePath+uri;
    }

    //事务处理：多个更新必须，有部分查询+更新有时候也需要
    //内部实现：aop，方法前加入开启事务逻辑，方法执行后，抛异常rollback，没有异常commit
    //禁止使用try catch吃异常
    @Transactional//可以手动指定隔离级别和传播特性
    public void register(User user) {
        //数据库校验：校验用户名不能重复，省略，自行扩展
        User exist = userMapper.selectByUsername(user.getUsername());
        if(exist!=null)
            throw new AppException("LOG003", "用户存在");
        //插入user数据：插入后自增主键会按照useGeneratedKeys=true设置到对象属性
        int n = userMapper.insertSelective(user);

        //插入setting数据：登录后，进入设置页面，添加奖项和抽奖人员，需要setting_id
        Setting setting = new Setting();
        setting.setUserId(user.getId());
        setting.setBatchNumber(8);//每次抽奖的数量：设置一个默认值（业务决定，我们这里拍脑门）
        settingMapper.insertSelective(setting);
    }

    public User queryByUsername(String username) {

        return userMapper.selectByUsername(username);
    }
}
