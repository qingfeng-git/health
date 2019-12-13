package com.itheima.service;


import com.itheima.entity.Result;
import com.itheima.pojo.Member;

import java.util.Map;

public interface OrderService {

    Result order(Map map) throws Exception;


    Map findById(Integer id)throws Exception;

    Member findBytelephone(String telephone);

    void add(Member member);

    void delet(String string);
}
