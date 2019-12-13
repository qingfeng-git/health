package com.itheima.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.OrderService;
import com.itheima.utils.POIUtils;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;
import java.util.Map;

/**
 * @PackageName: com.itheima.controller
 * @ClassName: ValidateCodeController
 * @Author: QingFeng
 * @Date: 2019-12-7 12:50
 * @Description: //TODO
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        //首先生成一个随机的验证码
        Integer integer = ValidateCodeUtils.generateValidateCode(4);
        try {
            //发送验证码短信
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,integer.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return new  Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //获取到的验证码存入到redis缓存当中,用于后期界面输入的验证码和存入缓存中的验证码相比,并设置过期时间
        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,300,integer.toString());

        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Map map = orderService.findById(id);
            //查询预约信息成功
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            //查询预约信息失败
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        //生成一个验证码
        Integer code = ValidateCodeUtils.generateValidateCode(6);

        //发送验证短信
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //将验证码保存到reddis中
        jedisPool.getResource().setex(telephone+RedisMessageConstant.SENDTYPE_LOGIN,60*5,code.toString());
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

}
