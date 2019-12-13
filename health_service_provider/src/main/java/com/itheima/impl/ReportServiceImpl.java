package com.itheima.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.OrderMapper;
import com.itheima.mapper.ReportMapper;
import com.itheima.service.ReportService;
import com.itheima.utils.DateUtils;
import com.sun.imageio.spi.InputStreamImageInputStreamSpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PackageName: com.itheima.impl
 * @ClassName: ReportServiceImpl
 * @Author: QingFeng
 * @Date: 2019-12-12 17:58
 * @Description: //TODO
 */
@Service(interfaceClass = ReportService.class)
@Transactional//查询作用,可以不使用事务声明
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private OrderMapper orderMapper;
    @Override
    public Map<String, Object> getBusinessReport() throws Exception {
        //得到本周周一的日期
        String string = DateUtils.parseDate2String(DateUtils.getThisWeekMonday(new Date()));
        //得到本月一号的日期
        String s = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        //得到本周最后一天
        String last = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());

        //得到本月最后一天
        String lastmonth = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());




        //得到日期(也就是今日日期)
        String reportDate =  DateUtils.parseDate2String(new Date());

        //查询今日新增会员数
        Integer todayNewMember = reportMapper.todayNewMember(reportDate);

        //查询总会员数
        Integer totalMember =  reportMapper.totalMember();

        //查询本周新增会员数
        Integer thisWeekNewMember =  reportMapper.thisWeekNewMember(string);

        //查询本月新增会员数
        Integer thisMonthNewMember =  reportMapper.thisMonthNewMember(s);


        //查询今日预约数
        Integer todayOrderNumber =  orderMapper.todayOrderNumber(reportDate);

        //查询今日到诊数
        Integer todayVisitsNumber =  orderMapper.todayVisitsNumber(reportDate);

        //本周预约数
        Map<String, String> map = new HashMap<>();
        map.put("diyi",string);
        map.put("zuihou",last);
        Integer thisWeekOrderNumber =orderMapper.thisWeekOrderNumber(map);

        //本周到诊数
        Integer thisWeekVisitsNumber =  orderMapper.thisWeekVisitsNumber(map);

        map = new HashMap<>();
        map.put("diyi",s);
        map.put("zuihou",lastmonth);

        //本月预约数
        Integer thisMonthOrderNumber =  orderMapper.thisMonthOrderNumber(map);

        //,本月到诊数
        Integer thisMonthVisitsNumber =orderMapper.thisMonthVisitsNumber(map);

        List<Map<String,Object>> hotSetmeal = orderMapper.hotSetmeal();

        Map<String, Object> result = new HashMap<>();

        result.put("reportDate",reportDate);

        result.put("totalMember",totalMember);
        result.put("todayNewMember",todayNewMember);
        result.put("thisWeekNewMember",thisWeekNewMember);
        result.put("thisMonthNewMember",thisMonthNewMember);

        result.put("todayOrderNumber",todayOrderNumber);
        result.put("todayVisitsNumber",todayVisitsNumber);
        result.put("thisWeekOrderNumber",thisWeekOrderNumber);
        result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        result.put("thisMonthOrderNumber",thisMonthOrderNumber);
        result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        result.put("hotSetmeal",hotSetmeal);

        return result;
    }
}
