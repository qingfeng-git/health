package com.itheima.service;


import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckGroupService {


    List<CheckItem> findAll();

    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult pagefindAll(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);

    List findId(Integer id);

    void Edit(CheckGroup checkGroup, Integer[] checkitemIds);

    void delete(Integer id);
}
