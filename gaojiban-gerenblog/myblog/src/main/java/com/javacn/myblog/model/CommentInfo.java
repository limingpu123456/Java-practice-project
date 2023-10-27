package com.javacn.myblog.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Description：评论实体类
 * User: lmp
 * Date: 2023-08-21
 * Time: 22:39(李明浦)
 */
@Setter
@Getter
public class CommentInfo implements Serializable {
    private long cid;
    private long aid;
    private long uid;
    private String content;
    private LocalDateTime createtime;
}
