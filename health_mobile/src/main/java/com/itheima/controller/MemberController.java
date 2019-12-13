package com.itheima.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @PackageName: com.itheima.controller
 * @ClassName: MemberController
 * @Author: QingFeng
 * @Date: 2019-12-9 11:08
 * @Description: //TODO
 */
@RestController
@RequestMapping("/login")
public class MemberController {
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/check")
    public Result check(HttpServletResponse response,@RequestBody Map map) {
        String telephone = (String) map.get("telephone");//获取手机号码
        String validateCode = (String) map.get("validateCode");//获取验证码
        //获取redis中存入的验证码
        String s = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (s == null || !s.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        } else {
            //判断当前用户是否为会员
            Member member = orderService.findBytelephone(telephone);
            if (member == null) {
                //如果member为空,就给他自动创建注册会员,需要重新创建个对象
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                orderService.add(member);
            }
            //将用户保存到cookies
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            cookie.setPath("/");//路径
            cookie.setMaxAge(60 * 60 * 24 * 30);//有效期30天
            response.addCookie(cookie);
            //保存会员信息到redis中
            String json = JSON.toJSON(member).toString();
            jedisPool.getResource().setex(telephone, 60 * 30, json);
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        }
    }
}
