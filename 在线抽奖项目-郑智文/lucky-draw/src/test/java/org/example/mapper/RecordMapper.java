package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.base.BaseMapper;
import org.example.model.Record;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RecordMapper extends BaseMapper<Record> {
}