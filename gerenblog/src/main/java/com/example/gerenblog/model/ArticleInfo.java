package com.example.gerenblog.model;

import lombok.Data;
import org.springframework.stereotype.Repository;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-22
 * Time: 15:37(李明浦)
 */
@Repository
@Data
public class ArticleInfo {
    private int id;
    private String title;
    private String content;
    private String createtime;
    private String updatetime;
    private int uid;
    private int rcount;
    private int state;
}
