package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SetmealMapper {
    Page<Setmeal> findPage(String queryString);

    List<CheckGroup> findAllCheckGroup();

    Setmeal findByIdSetMeal(Integer id);

    List<Integer> findBycheckgroupIds(Integer id);

    void deleteByIdSetmealAndCheckGroup(Integer id);

    void deleteById(Integer id);

    void addSetmeal(Setmeal setmeal);

    void addSetmealAndcheckgroup(HashMap<String, Integer> map);

    void update(Setmeal setmeal);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);

    List<Map<String, Object>> getSetmealReport();
}
