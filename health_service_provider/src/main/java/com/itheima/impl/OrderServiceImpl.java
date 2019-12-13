package com.itheima.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.mapper.MemberMapper;
import com.itheima.mapper.OrderMapper;
import com.itheima.mapper.OrderSettingMapper;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import com.itheima.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @PackageName: com.itheima.impl
 * @ClassName: OrderServiceImpl
 * @Author: QingFeng
 * @Date: 2019-12-8 18:49
 * @Description: //TODO
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderSettingMapper orderSettingMapper;


    /*idCard	111111111111111
   name	123
   orderDate	2019-12-09
   setmealId	6
   sex	2
   telephone	13711111111
   validateCode	123*/
    @Override
    public Result order(Map map) throws Exception {
        //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        OrderSetting orderSetting = orderMapper.findByOrderDate(DateUtils.parseString2Date(orderDate));
        if (orderSetting == null) {
            //没有为空,说明当天没有设置预约信息,给用户
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        int number = orderSetting.getNumber();//可预约人数
        int reservations = orderSetting.getReservations();//已预约人数
        if (reservations >= number) {
            //已经预约满了,无法进行预约
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约 则无法完成再次预约
        //获取到用户前台传过来的电话号码
        String telephone = (String) map.get("telephone");
        //根据电话去查询预约用户详情
        Member member = memberMapper.findByTelephone(telephone);
        if (member != null) {
            //说明有用户信息,来判断是否同一个用户在同一天预约了同一个套餐
            Integer id = member.getId();//用户id
            Date order_Date = DateUtils.parseString2Date(orderDate);//预约日期
            String setmealId = (String) map.get("setmealId");//套餐id
            Order order = new Order(id, order_Date, Integer.parseInt(setmealId));
            List<Order> list = memberMapper.findByCondition(order);
            if (list != null && list.size() > 0) {
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        } else {
            //4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注 册并进行预约
            member = new Member();
            member.setName((String) map.get("name"));//设置姓名
            member.setIdCard((String) map.get("idCard"));//设置身份证
            member.setSex((String) map.get("sex"));//设置性别
            member.setPhoneNumber(telephone);//手机号
            member.setRegTime(new Date());//注册时间
            memberMapper.add(member);//自动完成会员注册
        }
        //5、预约成功，更新当日的已预约人数
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(DateUtils.parseString2Date(orderDate));//预约日期
        order.setOrderType((String) map.get("orderType"));//预约类型
        order.setOrderStatus(Order.ORDERSTATUS_NO);//到诊状态
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));//套餐ID
        orderMapper.add(order);
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingMapper.Edit(orderSetting);

        return new Result(true, MessageConstant.ORDER_SUCCESS, order.getId());
    }

    @Override
    public Map findById(Integer id) throws Exception {

        Map map = orderMapper.findById(id);
        if (map != null) {
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate", DateUtils.parseDate2String(orderDate));
        }
        return map;
    }

    @Override
    public Member findBytelephone(String telephone) {
        return orderMapper.findBytelephone(telephone);
    }

    @Override
    public void add(Member member) {
        if (member.getPassword() != null){
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        orderMapper.adds(member);
    }

    @Override
    public void delet(String string) {
        orderMapper.delet(string);
    }

}
