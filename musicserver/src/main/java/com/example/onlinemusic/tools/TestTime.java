package com.example.onlinemusic.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-29
 * Time: 22:26(李明浦)
 */
public class TestTime {
    public static void main(String[] args) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sf.format(new Date());
        System.out.println(time);
    }
}
