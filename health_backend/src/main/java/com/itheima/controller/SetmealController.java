package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @PackageName: com.itheima.controller
 * @ClassName: SetmealController
 * @Author: QingFeng
 * @Date: 2019-12-2 19:32
 * @Description: //TODO
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;


    /**
     * @Description //分页查询
     * @Date 2019-12-2 19:52
     * @Param
     * @return
     **/
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.findPage(queryPageBean);
    }
    /**
     * @Description //查询所有检查组,用于数据回显
     * @Date 2019-12-2 21:20
     * @Param
     * @return
     **/
    @RequestMapping("/findAllCheckGroup")
    public List<CheckGroup> findAllCheckGroup(){
        return setmealService.findAllCheckGroup();
    }
    /**
     * @Description //根据id查询套餐
     * @Date 2019-12-2 22:16
     * @Param
     * @return
     **/
    @RequestMapping("/findByIdSetMeal")
    public Setmeal findByIdSetMeal(Integer id){
        //根据id查询套餐
        return setmealService.findByIdSetMeal(id);
    }

    /**
     * @Description //根据套餐id查询中间表id
     * @Date 2019-12-2 22:29
     * @Param
     * @return
     **/
    @RequestMapping("/findBycheckgroupIds")
    public List<Integer> findBycheckgroupIds(Integer id){
        return setmealService. findBycheckgroupIds(id);
    }
    /**
     * @Description //根据id删除套餐
     * @Date 2019-12-2 22:56
     * @Param
     * @return
     **/
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        try {
            setmealService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
    }
    /**
     * @Description //上传照片
     * @Date 2019-12-3 18:17
     * @Param
     * @return
     **/
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile ){
        //获取到照片文件名称
        String originalFilename = imgFile.getOriginalFilename();
        //得到最后一个.的索引
        int i = originalFilename.lastIndexOf(".");
        //根据索引位置开始分割文件名称,得到.jpg
        String substring = originalFilename.substring(i);
        //随机生成一个文件名
        String s = UUID.randomUUID().toString() + substring;
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),s);


            //将图片名称存入redis集合中,基于redis的set集合存储
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,s);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,s);
    }
    /**
     * @Description //添加检查套餐和检查组
     * @Date 2019-12-3 18:56
     * @Param
     * @return
     **/
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal , Integer [] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    /**
     * @Description //编辑套餐
     * @Date 2019-12-3 20:18
     * @Param
     * @return
     **/
    @RequestMapping("/update")
    public Result update(@RequestBody Setmeal setmeal , Integer [] checkgroupIds){
        try {
            setmealService.update(setmeal,checkgroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
    }
}
