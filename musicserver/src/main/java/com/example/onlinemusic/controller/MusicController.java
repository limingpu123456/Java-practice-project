package com.example.onlinemusic.controller;

import com.example.onlinemusic.mapper.MusicMapper;
import com.example.onlinemusic.model.Music;
import com.example.onlinemusic.model.User;
import com.example.onlinemusic.tools.Constant;
import com.example.onlinemusic.tools.ResponseBodyMessage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-29
 * Time: 18:41(李明浦)
 */
@RestController
@RequestMapping("/music")
public class MusicController {

    private String SAVE_PATH="I:/java/music/";

    @Autowired
    private MusicMapper musicMapper;

    @RequestMapping("/upload")
    @Transactional
    public ResponseBodyMessage<Boolean> insertMusic(@RequestParam("singer") String singer,
                                                    @RequestParam("filename") MultipartFile file,
                                                    HttpServletRequest request){
        //1.检查是否登陆了
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute(Constant.USERINFO_SESSION_KEY) == null){
            System.out.println("没有登录");
            return new ResponseBodyMessage<>(-1,"请登陆后上传",false);
        }


        String fileNameAndType = file.getOriginalFilename();
        //先查询数据库当中,是否有当前音乐[歌曲名+歌手]
        int index = fileNameAndType.lastIndexOf(".");
        String title = fileNameAndType.substring(0,index);

        Music music = musicMapper.select(title,singer);
        if(music != null){
            return new ResponseBodyMessage<>(-1,"此歌曲已上传,请重新选择",false);
        }

        String path = SAVE_PATH + fileNameAndType;

        File dest = new File(path);
        if(!dest.exists()){
            dest.mkdir();
        }

        try {
            file.transferTo(dest);
//            return new ResponseBodyMessage<>(0,"上传成功",true);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseBodyMessage<>(-1,"服务器上传失败",false);
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        //进行数据库的上传
        User user = (User)session.getAttribute(Constant.USERINFO_SESSION_KEY);
        int userid = user.getId();

        String url = "/music/get?path=" + title;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sf.format(new Date());
        try {
            int ret = 0;
            ret = musicMapper.insert(title,singer,time,url,userid);
            if(ret == 1){
                return new ResponseBodyMessage<>(0,"数据库上传成功",true);
            }else{
                return new ResponseBodyMessage<>(-1,"数据库上传失败",false);
            }
        }catch (BindingException e){
            dest.delete();
            return new ResponseBodyMessage<>(-1,"数据库上传失败",false);
        }
    }

    @RequestMapping("/get")
    public ResponseEntity<byte[]> get(String path){
        File file = new File(SAVE_PATH+"/"+path);
        byte[] a = null;
        try {
            a = Files.readAllBytes(file.toPath());
            if(a==null){
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping("/delete")
    public ResponseBodyMessage<Boolean> delete(@RequestParam(value = "id",required = false) Integer id){
        if(id == null){
            return new ResponseBodyMessage<>(-1,"无效id",false);
        }
        Music music = musicMapper.selectMusicById(id);
        if(music == null){
            return new ResponseBodyMessage<>(-1,"你要删除的歌曲不存在，请重新选择",false);
        }
        Integer ret = musicMapper.deleteMusicById(id);
        if(0 == ret){
            return new ResponseBodyMessage<>(-1,"删除失败",false);
        }
        int index = music.getUrl().lastIndexOf("=");
        String fileName = music.getUrl().substring(index + 1);
        File file = new File(SAVE_PATH+fileName+".txt");
        if(file.delete()){
            return new ResponseBodyMessage<>(-1,"删除成功",false);
        }
        return new ResponseBodyMessage<>(-1,"删除失败",false);
    }

    @RequestMapping("/deletesel")
    @Transactional
    public ResponseBodyMessage<Boolean> deleteSelMusic(@RequestParam("ids[]")List<Integer> ids){
        int sum = 0 ;
        for(int i = 0 ; i < ids.size() ; i++){
            Music music = musicMapper.selectMusicById(ids.get(i));
            if(music == null){
                sum+=1;
                continue;
            }
            int ret = musicMapper.deleteMusicById(ids.get(i));
            if(1 == ret){
                int index = music.getUrl().lastIndexOf("=");
                String fileName = music.getUrl().substring(index + 1);
                File file = new File(SAVE_PATH + fileName + ".txt");
                if(file.delete()){
                    sum++;
                    continue;
                }
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseBodyMessage<>(-1,"批量删除失败！当前成功删除了"+sum+"首歌",false);
            }
            return new ResponseBodyMessage<>(-1,"批量删除失败！当前成功删除了"+sum+"首歌",false);
        }
        return new ResponseBodyMessage<>(0,"批量删除成功！一共删除了"+sum+"首歌",true);
    }

    @RequestMapping("/findmusic")
    public ResponseBodyMessage<List<Music>> findMusic(@RequestParam(required = false) String name){
        List<Music> list = null;
        if(name == null){
            list = musicMapper.findByMusicByName();
        }else{
            list = musicMapper.findByMusicByName(name);
        }
        return new ResponseBodyMessage<>(0,"查询到了歌曲信息",list);
    }
}
