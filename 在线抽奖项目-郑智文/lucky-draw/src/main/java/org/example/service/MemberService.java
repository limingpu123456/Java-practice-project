package org.example.service;

import org.example.mapper.MemberMapper;
import org.example.mapper.SettingMapper;
import org.example.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SettingMapper settingMapper;

    public List<Member> queryBySettingId(Integer id) {
        return memberMapper.selectBySettingId(id);
    }

    public int add(Member member, Integer userId) {
        Integer settingId = settingMapper.queryIdByUserId(userId);
        member.setSettingId(settingId);
        return memberMapper.insertSelective(member);
    }

    public int update(Member member) {
        return memberMapper.updateByPrimaryKeySelective(member);
    }

    public int delete(Integer id) {
        return memberMapper.deleteByPrimaryKey(id);
    }
}
