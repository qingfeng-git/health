package com.itheima.mapper;


import com.github.pagehelper.Page;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemMapper {

    public void add(CheckItem checkItem);

    Page<CheckItem> selectByCondition(String queryString);

    long selectById(Integer id);

    void delete(Integer id);


    CheckItem selectCheckitemById(Integer id);

    void Edit(CheckItem checkItem);
}
