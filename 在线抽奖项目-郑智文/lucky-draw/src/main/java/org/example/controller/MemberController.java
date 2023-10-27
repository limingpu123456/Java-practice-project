package org.example.controller;

import org.example.model.Member;
import org.example.model.User;
import org.example.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/add")
    public Object add(@RequestBody Member member, HttpSession session){
        User user = (User) session.getAttribute("user");
        int n = memberService.add(member, user.getId());
        return member.getId();
    }

    @PostMapping("/update")
    public Object update(@RequestBody Member member){
        int n = memberService.update(member);
        return null;
    }

    @GetMapping("/delete/{id}")
    public Object delete(@PathVariable Integer id){
        int n = memberService.delete(id);
        return null;
    }
}
