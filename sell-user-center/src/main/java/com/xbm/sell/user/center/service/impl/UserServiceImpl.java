package com.xbm.sell.user.center.service.impl;

import com.xbm.sell.user.center.dao.UserInfoRepository;
import com.xbm.sell.user.center.model.UserInfo;
import com.xbm.sell.user.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository sellerInfoRepository;

    @Override
    public UserInfo getOneByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }
}
