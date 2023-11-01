package com.example.blog.util;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Description：进行密码加盐操作
 * User: lmp
 * Date: 2023-10-14
 * Time: 13:31(李明浦)
 */
public class PasswordUtil {
    /**
     * 加盐算法
     * @param password 原密码
     * @return 新密码
     */
    public static String encrypt(String password){
        //1.生成盐值
        String salt = UUID.randomUUID().toString().replace("-","");
        //2.使用加密算法将盐值+原密码进行加密
        String finalPassword = DigestUtils.md5DigestAsHex((salt+password).getBytes(StandardCharsets.UTF_8));
        //3.将盐值和加密后的密码一起返回
        String dbPassword = salt + "$" + finalPassword;
        return dbPassword;
    }

    /**
     * 密码验证
     * @param inputPassword 用户输入的密码
     * @param dbPassword 数据库里面记录的密码
     * @return
     */
    public static boolean decrypt(String inputPassword,String dbPassword){
        //1.验证参数
        if(!StringUtils.hasLength(inputPassword) || !StringUtils.hasLength(dbPassword)
        || dbPassword.length() != 65 || !dbPassword.contains("$")){
            return false;
        }
        //2.将用户输入的密码和数据库的盐值进行加密，得到待验证的加密密码
        String[] dbPasswordArray = dbPassword.split("\\$");
        String salt = dbPasswordArray[0];
        String finalPassword = dbPasswordArray[1];
        String userPassword = DigestUtils.md5DigestAsHex((salt+inputPassword).getBytes(StandardCharsets.UTF_8));
        //3.将待验证的加密密码和数据库中加密的密码进行对比
        if(userPassword.equals(finalPassword)){
            return true;
        }
        return false;
    }

    /**
     * 测试代码
     * @param args
     */
    public static void main(String[] args) {
        String password = "123456";
        System.out.println("加盐1"+encrypt(password));
        System.out.println("加盐2"+encrypt(password));
        System.out.println("加盐3"+encrypt(password));
        System.out.println("8fec2c114a9c4c1b9bdea04d6c15abf6$064ca3bd9c6e241906a9a143c6e310bd".length());
    }
}
