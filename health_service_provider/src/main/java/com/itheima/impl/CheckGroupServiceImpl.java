package com.itheima.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.mapper.CheckGroupMapper;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


/**
 * @PackageName: com.itheima.impl
 * @ClassName: CheckGroupServiceImpl
 * @Author: QingFeng
 * @Date: 2019-11-30 21:01
 * @Description: //TODO
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupMapper checkGroupMapper;


    /**
     * @return java.util.List<com.itheima.pojo.CheckItem>
     * @Description //新建检查组中的检查项
     * @Date 2019-11-30 21:08
     * @Param []
     **/
    public List<CheckItem> findAll() {
        return checkGroupMapper.findAll();
    }

    /**
     * @return void
     * @Description //添加检查组信息
     * @Date 2019-11-30 23:06
     * @Param [checkGroup, checkitemIds]
     **/
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //调用方法进行checkGroup数据的插入,然后得到封装进checkGroup对象的id数据
        checkGroupMapper.add(checkGroup);
        //得到主键id值
        addUtil(checkGroup,checkitemIds);
       /* Integer checkGroup_id = checkGroup.getId();
        //新建一个MAP集合将查询到的数据存入到map集合中
        Map<String, Integer> map = new HashMap<>();
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                map.put("checkitem_id", checkitemId);
                map.put("checkgroup_id", checkGroup_id);
                //调用添加方法,将数据插入到第三张表里面
                checkGroupMapper.addcheckitem_id_AND_checkGroup_id(map);
            }
        } else {
            throw new RuntimeException();
        }*/
    }

    /**
     * @return com.itheima.entity.PageResult
     * @Description //分页查询
     * @Date 2019-12-1 12:13
     * @Param [queryPageBean]
     **/
    @Override
    public PageResult pagefindAll(QueryPageBean queryPageBean) {
        //根据前台传过来的条件.来确定当前页码,和每页要显示几条
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //分页插件会自动将查询到的结果集来进行分页
        Page<CheckGroup> page = checkGroupMapper.pagefindAll(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * @return void
     * @Description //根据检查组id去查信息
     * @Date 2019-12-1 12:13
     * @Param [id]
     **/
    @Override
    public CheckGroup findById(Integer id) {

        return checkGroupMapper.findById(id);

    }

    /**
     * @return java.lang.Integer[]
     * @Description //根据检查组id去查检查项id
     * @Date 2019-12-1 12:41
     * @Param [id]
     **/
    @Override
    public List<Integer> findId(Integer id) {
        List<Integer> id1 = checkGroupMapper.findId(id);
        return id1;
    }

    /**
     * @return void
     * @Description //修改检查组
     * @Date 2019-12-2 17:52
     * @Param [checkGroup, id]
     **/
    @Override
    public void Edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //根据检查组id修改检查组信息
        checkGroupMapper.Edit(checkGroup);
        //根据检查组id删除中间表信息
        checkGroupMapper.deleteById(checkGroup.getId());
        //根据前台传入的检查项id数组,在进行id的更新
        addUtil(checkGroup, checkitemIds);
    }
    /**
     * @Description //删除检查组,以及中间表
     * @Date 2019-12-2 18:38
     * @Param [id]
     * @return void
     **/
    @Override
    public void delete(Integer id) {
        //首先先要删除中间表关系
        checkGroupMapper.deleteById(id);
        //在删除检查组
        checkGroupMapper.delete(id);

    }

    /**
     * @Description //提取一个插入中间表的方法
     * @Date 2019-12-2 18:12
     * @Param [checkGroup, checkitemIds]
     * @return void
     **/
    private void addUtil(CheckGroup checkGroup, Integer[] checkitemIds) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                hashMap.put("checkitem_id", checkitemId);
                hashMap.put("checkgroup_id", checkGroup.getId());
                checkGroupMapper.addcheckitem_id_AND_checkGroup_id(hashMap);
            }
        } else {
            throw new RuntimeException();
        }
    }
}
