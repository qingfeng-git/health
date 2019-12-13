package com.itheima.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.mapper.SetmealMapper;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PackageName: com.itheima.impl
 * @ClassName: SetmealServiceImpl
 * @Author: QingFeng
 * @Date: 2019-12-2 19:48
 * @Description: //TODO
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private JedisPool jedisPool;

    /**
     * @return com.itheima.entity.PageResult
     * @Description //分页查询
     * @Date 2019-12-2 20:14
     * @Param [queryPageBean]
     **/
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Setmeal> page = setmealMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page);
    }

    @Override
    public List<CheckGroup> findAllCheckGroup() {

        return setmealMapper.findAllCheckGroup();
    }

    @Override
    public Setmeal findByIdSetMeal(Integer id) {


        return setmealMapper.findByIdSetMeal(id);
    }

    @Override
    public List<Integer> findBycheckgroupIds(Integer id) {

        return setmealMapper.findBycheckgroupIds(id);
    }

    @Override
    public void deleteById(Integer id) {
        //根据套餐id解除中间表关系
        setmealMapper.deleteByIdSetmealAndCheckGroup(id);
        //根据套餐id删除套餐
        setmealMapper.deleteById(id);
    }


    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //先添加检查套餐
        setmealMapper.addSetmeal(setmeal);
        //根据套餐回写过来的id,再去添加中间表关系
        if(checkgroupIds != null && checkgroupIds.length > 0){
            addsetmealAndcheckgroupIds(setmeal,checkgroupIds);
            savePic2Redis(setmeal.getImg());
        }

    }
    /**
     * @Description //编辑套餐
     * @Date 2019-12-3 20:22
     * @Param [setmeal, checkgroupIds]
     * @return void
     **/
    @Override
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        //修改套餐内容
        setmealMapper.update(setmeal);
        //根据套餐id删除中间表关系
        setmealMapper.deleteByIdSetmealAndCheckGroup(setmeal.getId());
        //再将建立新的中间表关系
        addsetmealAndcheckgroupIds(setmeal,checkgroupIds);

    }

    @Override
    public List<Setmeal> getSetmeal() {


        List<Setmeal> setmeal = setmealMapper.getSetmeal();
        return setmeal;
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealMapper.findById(id);
    }

    @Override
    public List<Map<String, Object>> getSetmealReport() {

        List<Map<String, Object>> list =  setmealMapper.getSetmealReport();
        return list;
    }

    //封装一个添加方法
    private void addsetmealAndcheckgroupIds(Setmeal setmeal, Integer[] checkgroupIds) {
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            HashMap<String, Integer> map = new HashMap<>();
            for (Integer checkgroupId : checkgroupIds) {
                map.put("setmeal_id", setmeal.getId());
                map.put("checkgroup_id", checkgroupId);
                setmealMapper.addSetmealAndcheckgroup(map);
            }
        }
    }
    //图片往redis缓存里存入的方法
    private void savePic2Redis(String pic){
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic);
    }
}
