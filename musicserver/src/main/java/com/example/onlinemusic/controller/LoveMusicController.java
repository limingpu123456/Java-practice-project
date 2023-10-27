package com.example.onlinemusic.controller;

import com.example.onlinemusic.mapper.LoveMusicMapper;
import com.example.onlinemusic.model.Music;
import com.example.onlinemusic.model.User;
import com.example.onlinemusic.tools.Constant;
import com.example.onlinemusic.tools.ResponseBodyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-30
 * Time: 11:38(李明浦)
 */
@RestController
@RequestMapping("/lovemusic")
public class LoveMusicController {
    @Autowired
    private LoveMusicMapper loveMusicMapper;

    @Transactional
    @RequestMapping("/likemusic")
    public ResponseBodyMessage<Boolean> likeMusic(@RequestParam Integer musicid, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        Integer userid = user.getId();

        Music music  = loveMusicMapper.findLoveMusicByMusicIdAndUserId(userid,musicid);
        if(music!= null){
            return new ResponseBodyMessage<>(-1,"歌曲已收藏，请重新选择",false);
        }
        if(loveMusicMapper.insertLoveMusic(userid,musicid)){
            return new ResponseBodyMessage<>(0,"添加成功",true);
        }
        return new ResponseBodyMessage<>(-1,"添加失败，请稍后重试！",false);
    }

//    @RequestMapping("/findlovemusic")
//    public ResponseBodyMessage<List<Music>> findLoveMusic(@RequestParam(required = false) String title,
//                                                          HttpServletRequest request){
//        HttpSession session = request.getSession(false);
//        if(session== null || session.getAttribute(Constant.USERINFO_SESSION_KEY) == null){
//            return new ResponseBodyMessage<>(-1,"没有登录",null);
//        }
//        User user = (User)session.getAttribute(Constant.USERINFO_SESSION_KEY);
//        int userid = user.getId();
//
//        List<Music> musics = null;
//
//        if(title==null){
//            musics = loveMusicMapper.findLoveMusicByKeyAndUserId(userid);
//        }else{
//            musics = loveMusicMapper.findLoveMusicByKeyAndUserId(title,userid);
//        }
//        return new ResponseBodyMessage<>(0,"查询到了所有收藏的音乐",musics);
//    }

    @RequestMapping(value = "/findlovemusic")
    public ResponseBodyMessage<List<Music>> findLoveMusic(@RequestParam(required = false) String title,
                                                          HttpServletRequest request){
        // 获取 用户id
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        Integer user_id = user.getId();

        List<Music> list = null;
        if(title == null){
            list = loveMusicMapper.findLoveMusicByKeyAndUserId(user_id);
        }else{
            list = loveMusicMapper.findLoveMusicByKeyAndUserId(user_id,title);
        }
        return new ResponseBodyMessage<>(0,"查询到了相关收藏的音乐",list);
    }

    @RequestMapping("deletelovemusic")
    @Transactional
    public ResponseBodyMessage<Boolean> deleteLoveMusic(@RequestParam Integer musicid,HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session== null || session.getAttribute(Constant.USERINFO_SESSION_KEY) == null){
            return new ResponseBodyMessage<>(-1,"没有登录",null);
        }
        User user = (User)session.getAttribute(Constant.USERINFO_SESSION_KEY);
        int userid = user.getId();

        Integer ret = loveMusicMapper.deleteLoveMusic(userid,musicid);
        if(ret != 1){
            return new ResponseBodyMessage<>(-1,"取消收藏失败",false);
        }
        return new ResponseBodyMessage<>(0,"取消收藏成功",true);
    }
}
