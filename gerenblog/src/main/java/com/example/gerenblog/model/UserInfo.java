package com.example.gerenblog.model;

import lombok.Data;
import org.springframework.stereotype.Repository;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-22
 * Time: 15:33(李明浦)
 */
@Repository
@Data
public class UserInfo {
    private int id;
    private String username;
    private String password;
    private String photo;
    private String createtime;
    private String updatetime;
    private int state;
}
