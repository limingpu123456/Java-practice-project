package org.example.service;

import org.example.mapper.AwardMapper;
import org.example.mapper.SettingMapper;
import org.example.model.Award;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AwardService {

    @Autowired
    private AwardMapper awardMapper;
    @Autowired
    private SettingMapper settingMapper;

    public List<Award> queryBySettingId(Integer id) {
        return awardMapper.selectBySettingId(id);
    }

    @Transactional
    public int add(Award award, Integer userId) {
        //通过userId找settingId: 可以调用已有的selectByUserId
        Integer settingId = settingMapper.queryIdByUserId(userId);
        //设置award中settingId属性
        award.setSettingId(settingId);
        //插入一条award数据/对象
        return awardMapper.insertSelective(award);
    }

    @Transactional
    public int update(Award award) {
        return awardMapper.updateByPrimaryKeySelective(award);
    }

    @Transactional
    public int delete(Integer id) {
        return awardMapper.deleteByPrimaryKey(id);
    }
}
