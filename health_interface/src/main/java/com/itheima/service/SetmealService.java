package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    PageResult findPage(QueryPageBean queryPageBean);

    List<CheckGroup> findAllCheckGroup();

    Setmeal findByIdSetMeal(Integer id);

    List<Integer> findBycheckgroupIds(Integer id);

    void deleteById(Integer id);

    void add(Setmeal setmeal, Integer[] checkgroupIds);

    void update(Setmeal setmeal, Integer[] checkgroupIds);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);

    List<Map<String, Object>> getSetmealReport();
}
