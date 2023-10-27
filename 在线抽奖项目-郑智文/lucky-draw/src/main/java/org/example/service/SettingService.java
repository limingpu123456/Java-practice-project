package org.example.service;

import org.example.mapper.SettingMapper;
import org.example.model.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SettingService {

    @Autowired
    private SettingMapper settingMapper;

    public Setting queryByUserId(Integer id) {
        return settingMapper.selectByUserId(id);
    }

    //Spring事务设置：默认的传播方式为required，当前没有事务，就创建，有就加入
    @Transactional
    public int update(Integer batchNumber, Integer userId) {
        return settingMapper.update(batchNumber, userId);
    }
}
