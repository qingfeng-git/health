package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @PackageName: com.itheima.controller
 * @ClassName: OrderController
 * @Author: QingFeng
 * @Date: 2019-12-8 18:46
 * @Description: //TODO
 */
@RestController
@RequestMapping("/ordercode")
public class OrderController {

    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/order")
    public Result order(@RequestBody Map map) {
        //获取手机号码
        String telephone = (String) map.get("telephone");
        //从redis缓存中获取到验证码
        String s = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        //获取前台页面用户输入的验证码
        String validateCode = (String) map.get("validateCode");
        //校验验证码是否正确
        if (s != null && validateCode != null && s.equals(validateCode)) {
            //正确手机号为正确手机号,则调用服务 用户完成预约
            //首先设置用户为微信预约
            map.put("orderType", Order.ORDERTYPE_WEIXIN);

            Result result = null;
            try {
                result = orderService.order(map);
            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }

            if (result.isFlag()) {
                //如果返回true证明客户预约成功了,给客户发送预约成功短信
                try {
                    SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, (String) map.get("orderDate"));
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
            return result;
        } else {
            //如果不成功,则提示用户错误信息
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

    }
}
