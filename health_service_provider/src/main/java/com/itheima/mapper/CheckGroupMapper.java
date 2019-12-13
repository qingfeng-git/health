package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.List;
import java.util.Map;

public interface CheckGroupMapper {
    List<CheckItem> findAll();

    void add(CheckGroup checkGroup);

    void addcheckitem_id_AND_checkGroup_id(Map<String, Integer> map);

    Page<CheckGroup> pagefindAll(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findId(Integer id);

    void Edit(CheckGroup checkGroup);

    void deleteById(Integer id);

    void delete(Integer id);
}
