package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @PackageName: com.itheima.controller
 * @ClassName: CheckGroupController
 * @Author: QingFeng
 * @Date: 2019-11-30 20:57
 * @Description: //TODO
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    /**
     * @Description //新建检查组中的检查项
     * @Date 2019-11-30 21:04
     * @Param
     * @return
     **/
    @RequestMapping("/findAll")
    public Result findAll(){
        List<CheckItem> all = checkGroupService.findAll();
        if (all != null && all.size()>0){
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,all);
        }else {
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * @Description //添加检查组信息
     * @Date 2019-11-30 23:00
     * @Param
     * @return
     **/
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup ,Integer [] checkitemIds){
        try {
            checkGroupService.add(checkGroup,checkitemIds);
            return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    /**
     * @Description //分页查询
     * @Date 2019-12-1 10:51
     * @Param
     * @return
     **/
    @RequestMapping("/pagefindAll")
    public PageResult pagefindAll(@RequestBody QueryPageBean queryPageBean){
        return checkGroupService.pagefindAll(queryPageBean);
    }
    /**
     * @Description //根据id去查检查组信息
     * @Date 2019-12-1 12:11
     * @Param
     * @return
     **/
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            CheckGroup byId = checkGroupService.findById(id);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS,byId);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    /**
     * @Description //根据检查组id去查检查项id
     * @Date 2019-12-1 12:38
     * @Param
     * @return
     **/
    @RequestMapping("/findId")
    public Result findId(Integer id){
        try {
             List arr = checkGroupService.findId(id);
             return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS,arr);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }
    /**
     * @Description //添加检查组
     * @Date 2019-12-2 17:48
     * @Param
     * @return
     **/
    @RequestMapping("/Edit")
    public Result Edit(@RequestBody CheckGroup checkGroup ,Integer [] checkitemIds){
        try {
            checkGroupService.Edit(checkGroup,checkitemIds);
            return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    /**
     * @Description //删除检查组
     * @Date 2019-12-2 18:35
     * @Param
     * @return
     **/
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            checkGroupService.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
}
