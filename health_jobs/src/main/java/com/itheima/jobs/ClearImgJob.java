package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * 自定义Job，实现定时清理垃圾图片
 */
public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;
    public void run(){
        //根据Redis中保存的两个set集合进行差值计算，获得垃圾图片名称集合
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (set!=null){
            for (String s : set) {
                //调用工具类来删除图片
                QiniuUtils.deleteFileFromQiniu(s);
                //再从redis集合中删除图片
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,s);
                System.out.println("删除了"+s);
            }
        }
    }
}
