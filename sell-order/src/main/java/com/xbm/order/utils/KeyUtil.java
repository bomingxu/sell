package com.xbm.order.utils;


import java.util.Random;

public  class KeyUtil {

    public static synchronized String  getUniqueKey(){
        //一个随机的八位数
        Random random = new Random();
        Integer randomNumber = random.nextInt(90000000)+10000000;
        return new ObjectId().toHexString()+randomNumber;
    }


}
