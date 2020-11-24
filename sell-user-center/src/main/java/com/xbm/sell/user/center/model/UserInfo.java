package com.xbm.sell.user.center.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class UserInfo {

    /**
     * seller_id
     */
    @Id
    private String id;

    /**
     * username
     */
    private String username;

    /**
     * password
     */
    private String password;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 1买家2卖家
     */
    private Integer role;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}
