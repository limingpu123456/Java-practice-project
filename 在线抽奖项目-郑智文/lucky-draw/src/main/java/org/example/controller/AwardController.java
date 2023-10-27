package org.example.controller;

import org.example.model.Award;
import org.example.model.User;
import org.example.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/award")
public class AwardController {

    @Autowired
    private AwardService awardService;

    @PostMapping("/add")
    public Object add(@RequestBody Award award, HttpSession session){
        User user = (User) session.getAttribute("user");
        int n = awardService.add(award, user.getId());
        return award.getId();//插入后，返回给前端自增主键id
    }

    @PostMapping("/update")
    public Object update(@RequestBody Award award){
        int n = awardService.update(award);
        return null;
    }

    @GetMapping("/delete/{id}")
    public Object delete(@PathVariable Integer id){
        int n = awardService.delete(id);
        return null;
    }
}
