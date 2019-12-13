package com.itheima.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.mapper.CheckItemMapper;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @PackageName: com.itheima.impl
 * @ClassName: CheckItemServiceImpl
 * @Author: QingFeng
 * @Date: 2019-11-29 19:28
 * @Description: //TODO
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemMapper checkItemMapper;

    /**
     * @Description //添加检查项方法
     * @Date 2019-11-29 19:40
     * @Param [checkItem]
     * @return com.itheima.entity.Result
     **/
    public void add(CheckItem checkItem) {
         checkItemMapper.add(checkItem);
    }

    /**
     * @Description //分页查询
     * @Date 2019-11-29 21:00
     * @Param [queryPageBean]
     * @return void
     **/

    public PageResult pageQuery(QueryPageBean queryPageBean) {
        //将页面传输过来的数据赋值给分页助手里面
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //设置分页助手,分页查询下面的第一条sql语句,并完成分页
        PageHelper.startPage(currentPage,pageSize);


        Page<CheckItem> page = checkItemMapper.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> result = page.getResult();

        PageResult pageResult = new PageResult(total, result);
        return pageResult;
    }

    /**
     * @Description //根据id删除检查项
     * @Date 2019-11-30 12:37
     * @Param [id]
     * @return void
     **/
    @Override
    public void delete(Integer id) {
        //想要删除客户,首先要判断id是否有另一张表关联
        long count = checkItemMapper.selectById(id);
        if (count > 0){
            throw new RuntimeException();
        }else {
            checkItemMapper.delete(id);
        }
    }
    /**
     * @Description //根据id查询信息完成数据回显
     * @Date 2019-11-30 18:16
     * @Param [id]
     * @return com.itheima.entity.Result
     **/
    @Override
    public CheckItem selectCheckitemById(Integer id) {
        return checkItemMapper.selectCheckitemById(id);
    }
    /**
     * @Description //修改客户信息
     * @Date 2019-11-30 19:04
     * @Param [checkItem]
     * @return void
     **/
    @Override
    public void Edit(CheckItem checkItem) {
        checkItemMapper.Edit(checkItem);
    }
}
