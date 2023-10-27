package com.example.onlinemusic.mapper;

import com.example.onlinemusic.model.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-29
 * Time: 21:55(李明浦)
 */
@Mapper
public interface MusicMapper {
    int insert(String title,String singer,String time,String url,int userid);

    Music select(@Param("title")String title, @Param("singer") String singer);

    Music selectMusicById(@Param("id") Integer id);

    Integer deleteMusicById(@Param("id") Integer id);

    //模糊查询要起个别名
    List<Music> findByMusicByName(@Param("name") String name);

    List<Music> findByMusicByName();
}
