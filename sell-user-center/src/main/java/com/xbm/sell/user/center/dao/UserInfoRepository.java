package com.xbm.sell.user.center.dao;

import com.xbm.sell.user.center.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {

    UserInfo findByOpenid(String openid);

}
