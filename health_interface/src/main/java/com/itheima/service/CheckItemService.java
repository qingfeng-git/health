package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;

public interface CheckItemService {

    public void add(CheckItem checkItem);
    PageResult pageQuery(QueryPageBean queryPageBean);

    void delete(Integer id);

    CheckItem selectCheckitemById(Integer id);

    void Edit(CheckItem checkItem);
}
