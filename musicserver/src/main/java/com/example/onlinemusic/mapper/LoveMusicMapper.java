package com.example.onlinemusic.mapper;

import com.example.onlinemusic.model.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-30
 * Time: 11:14(李明浦)
 */
@Mapper
public interface LoveMusicMapper {
    Music findLoveMusicByMusicIdAndUserId(@Param("userid") Integer userid, @Param("musicid") Integer musicid);

    boolean insertLoveMusic(@Param("userid") Integer userid,@Param("musicid") Integer musicid);

    List<Music> findLoveMusicByKeyAndUserId(@Param("userid") Integer user_id);

    List<Music> findLoveMusicByKeyAndUserId( @Param("userid") Integer user_id,@Param("title") String title);

    Integer deleteLoveMusic(@Param("userid") Integer userid,@Param("musicid") Integer musicid);
}
