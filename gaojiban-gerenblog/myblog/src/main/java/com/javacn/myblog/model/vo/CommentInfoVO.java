package com.javacn.myblog.model.vo;

import com.javacn.myblog.model.CommentInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Description：
 * User: lmp
 * Date: 2023-10-04
 * Time: 19:19(李明浦)
 */
@Setter
@Getter
public class CommentInfoVO extends CommentInfo implements Serializable {
    private String nickname;
}
