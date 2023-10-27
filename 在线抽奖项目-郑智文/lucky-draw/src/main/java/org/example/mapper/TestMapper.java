package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.User;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-30
 * Time: 17:02(李明浦)
 */
@Mapper
public interface TestMapper {
    User query(Integer id);
}
