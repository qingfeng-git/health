package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @PackageName: com.itheima.controller
 * @ClassName: CheckItemContorller
 * @Author: QingFeng
 * @Date: 2019-11-29 19:01
 * @Description: //TODO
 */

@RestController
@RequestMapping("/checkitem")
public class CheckItemContorller {

    @Reference
    private CheckItemService checkItemService;

    /**
     * @return com.itheima.entity.Result
     * @Description //添加用户
     * @Date 2019-11-29 20:53
     * @Param [checkItem]
     **/
    @RequestMapping("/add.do")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * @return com.itheima.entity.Result
     * @Description //分页查询
     * @Date 2019-11-29 20:54
     * @Param [checkItem]
     **/
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkItemService.pageQuery(queryPageBean);
    }

    /**
     * @return
     * @Description //根据ID删除用户
     * @Date 2019-11-30 12:31
     * @Param
     **/
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            checkItemService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * @Description //根据id查询数据,做为数据的回显
     * @Date 2019-11-30 18:12
     * @Param
     * @return
     **/
    @RequestMapping("/selectCheckitemById")
    public Result selectCheckitemById(Integer id){
        try {
            CheckItem checkItem = checkItemService.selectCheckitemById(id);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS,checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    /**
     * @Description //根据id修改客户信息
     * @Date 2019-11-30 19:00
     * @Param
     * @return
     **/
    @RequestMapping("/Edit")
    public Result Edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.Edit(checkItem);

        } catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
}
