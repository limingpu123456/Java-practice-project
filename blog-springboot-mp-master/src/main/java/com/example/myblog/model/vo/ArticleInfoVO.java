package com.example.myblog.model.vo;

import com.example.myblog.model.ArticleInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class ArticleInfoVO extends ArticleInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -7122236306076080498L;
    private String photo;
    private String nickname;
    private int artCount;
}
