package com.itheima.mapper;

import com.itheima.pojo.Member;
import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

public interface MemberMapper {



    Member findByTelephone(String telephone);

    List<Order> findByCondition(Order order);

    void add(Member member1);


    int findcountBydata(String s);
}
