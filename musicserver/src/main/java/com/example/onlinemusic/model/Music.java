package com.example.onlinemusic.model;

import lombok.Data;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-29
 * Time: 18:39(李明浦)
 */
@Data
public class Music {
    private int id;
    private String title;
    private String singer;
    private String time;
    private String url;
    private int userid;
}
