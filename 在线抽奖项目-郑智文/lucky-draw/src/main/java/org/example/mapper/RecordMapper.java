package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.base.BaseMapper;
import org.example.model.Record;

import java.util.List;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    int batchInsert(@Param("memberIds") List<Integer> memberIds,
                    @Param("awardId") Integer awardId);

    int deleteByMemberId(Integer memberId);

    int deleteByAwardId(Integer id);

    int deleteByUserId(Integer id);
}