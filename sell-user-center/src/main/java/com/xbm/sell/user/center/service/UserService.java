package com.xbm.sell.user.center.service;

import com.xbm.sell.user.center.model.UserInfo;

public interface UserService {

    UserInfo getOneByOpenid(String openid);

}
