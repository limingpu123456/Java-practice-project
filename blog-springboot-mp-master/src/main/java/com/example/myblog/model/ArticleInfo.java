package com.example.myblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章的实体类
 */
@Setter
@Getter
@TableName("articleinfo")
public class ArticleInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -52722551515977433L;
    @TableId(type = IdType.AUTO)
    private Long aid;
    private String title;
    private LocalDateTime createtime;
    private LocalDateTime updatetime;
    @TableField("`desc`")
    private String desc;
    private String content;
    private int state;
    private long uid;
    private long rcount;
}
