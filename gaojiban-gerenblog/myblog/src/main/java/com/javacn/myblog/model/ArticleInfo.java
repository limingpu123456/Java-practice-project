package com.javacn.myblog.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Description：文章实体类
 * User: lmp
 * Date: 2023-08-21
 * Time: 22:36(李明浦)
 */
@Setter
@Getter
public class ArticleInfo implements Serializable {
    private Long aid;
    private String title;
    private LocalDateTime createtime;
    private LocalDateTime updatetime;
    private String desc;
    private String content;
    private int state;
    private long uid;
    private long rcount;
}
