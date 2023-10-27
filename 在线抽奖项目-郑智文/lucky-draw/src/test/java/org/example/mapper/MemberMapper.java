package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.base.BaseMapper;
import org.example.model.Member;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MemberMapper extends BaseMapper<Member> {
}