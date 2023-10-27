package com.example.myblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 评论实体类
 */
@Setter
@Getter
@TableName("commentinfo")
public class CommentInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -5849198667119918959L;
    @TableId(type= IdType.AUTO)
    private long cid;
    private long aid;
    private long uid;
    private String content;
    private LocalDateTime createtime;
}
