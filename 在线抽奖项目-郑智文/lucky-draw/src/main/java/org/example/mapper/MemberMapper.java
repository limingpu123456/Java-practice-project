package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.base.BaseMapper;
import org.example.model.Member;

import java.util.List;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
    List<Member> selectBySettingId(Integer id);
}