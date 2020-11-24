package com.xbm.sell.user.center.controller;

import com.xbm.sell.common.constant.CookieConstant;
import com.xbm.sell.common.constant.RedisConstant;
import com.xbm.sell.common.enums.ResultEnum;
import com.xbm.sell.common.utils.CookieUtil;
import com.xbm.sell.common.utils.ResultVoUtil;
import com.xbm.sell.common.vo.ResultVo;
import com.xbm.sell.user.center.enums.RoleEnum;
import com.xbm.sell.user.center.model.UserInfo;
import com.xbm.sell.user.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/loginController")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/buyerLogin")
    public ResultVo buyerLogin(HttpServletResponse response, @RequestParam String openid){
        //1.校验用户是否存在
        UserInfo userInfo = userService.getOneByOpenid(openid);
        if(userInfo == null){
            return ResultVoUtil.error(ResultEnum.USER_NOT_EXIT);
        }
        //2.校验用户类型是否正确
        if(RoleEnum.BUYER.getCode() != userInfo.getRole()){
            return ResultVoUtil.error(ResultEnum.USER_ROLE_ERROR);
        }
        //3.cookie里设置openid
        CookieUtil.set(response, CookieConstant.OPENID,openid,CookieConstant.expire);

        return ResultVoUtil.success();
    }

    @GetMapping("/sellerLogin")
    public ResultVo sellerLogin(HttpServletRequest request,HttpServletResponse response, @RequestParam String openid){
        //校验cookie是否存在
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie != null && stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())).equals(openid)){
            return ResultVoUtil.success();
        }
        //1.校验用户是否存在
        UserInfo userInfo = userService.getOneByOpenid(openid);
        if(userInfo == null){
            return ResultVoUtil.error(ResultEnum.USER_NOT_EXIT);
        }
        //2.校验用户类型是否正确
        if(RoleEnum.SELLER.getCode() != userInfo.getRole()){
            return ResultVoUtil.error(ResultEnum.USER_ROLE_ERROR);
        }
        //3.redis设置key=UUID, value=xyz
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE,uuid),openid,CookieConstant.expire, TimeUnit.SECONDS);
        //4.cookie里设置openid
        CookieUtil.set(response, CookieConstant.TOKEN,uuid,CookieConstant.expire);

        return ResultVoUtil.success();
    }

}
