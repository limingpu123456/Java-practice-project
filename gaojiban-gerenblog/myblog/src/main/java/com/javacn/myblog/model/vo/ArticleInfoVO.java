package com.javacn.myblog.model.vo;

import com.javacn.myblog.model.ArticleInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Description：
 * User: lmp
 * Date: 2023-10-04
 * Time: 19:19(李明浦)
 */
@Setter
@Getter
public class ArticleInfoVO extends ArticleInfo implements Serializable {
    private String photo;
    private String nickname;
    private int artCount;
}
