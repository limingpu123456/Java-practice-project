package com.example.myblog.model.vo;

import com.example.myblog.model.CommentInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class CommentInfoVO extends CommentInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 4317722138565370427L;
    private String nickname;
}
