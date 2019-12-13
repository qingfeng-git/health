package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import com.itheima.service.SetmealService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.applet.Main;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @PackageName: com.itheima.controller
 * @ClassName: ReportController
 * @Author: QingFeng
 * @Date: 2019-12-11 9:34
 * @Description: //TODO
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;

    @Reference
    private ReportService reportService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        Calendar instance = Calendar.getInstance();
        List<String> list = new ArrayList<>();
        //获取到截止今日之前的12个月份数
        instance.add(Calendar.MONTH, -12);
        for (int i = 0; i < 12; i++) {
            instance.add(Calendar.MONTH, 1);
            Date time = instance.getTime();
            list.add(new SimpleDateFormat("yyyy.MM").format(time));
        }
        Map<String, Object> map = new HashMap<>();
        //存入月份数
        map.put("months", list);
        //存入会员数
        List<Integer> list1 = memberService.findcountBydata(list);
        map.put("memberCount", list1);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
    }


    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        //调用方法查询套餐和id的集合对象
        List<Map<String, Object>> list = setmealService.getSetmealReport();
        //新建一个将于要返回到页面的map集合数据
        Map<String, Object> map = new HashMap<>();
        map.put("setmealCount", list);
        List<String> list1 = new ArrayList<>();
        for (Map<String, Object> objectMap : list) {
            String name = (String) objectMap.get("name");
            list1.add(name);
        }
        map.put("setmealNames", list1);
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
    }


    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            //前台要求的数据格式,使用map集合返回前台
            Map<String, Object> map = reportService.getBusinessReport();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        //获取到运营数据
        try {
            Map<String, Object> result = reportService.getBusinessReport();
            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            String s = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
            //将文件加载进内存,得到工作薄
            XSSFWorkbook sheets = new XSSFWorkbook(new FileInputStream(new File(s)));
            //获取工作表
            XSSFSheet sheetAt = sheets.getSheetAt(0);

            //写入日期
            XSSFRow row = sheetAt.getRow(2);
            XSSFCell cell = row.getCell(5);
            cell.setCellValue(reportDate);
            //写会员数据
            row = sheetAt.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);
            row.getCell(7).setCellValue(totalMember);

            row = sheetAt.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);
            row.getCell(7).setCellValue(thisMonthNewMember);

            row = sheetAt.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);
            row.getCell(7).setCellValue(todayVisitsNumber);

            row = sheetAt.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);
            row.getCell(7).setCellValue(thisWeekVisitsNumber);

            row = sheetAt.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);
            row.getCell(7).setCellValue(thisMonthVisitsNumber);
            //得到表单12行
            int rowNum = 12;
            for (Map map : hotSetmeal) {
                //得到套餐集合对象
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheetAt.getRow(rowNum);
                row.getCell(4).setCellValue(name);
                row.getCell(5).setCellValue(setmeal_count);
                row.getCell(6).setCellValue(proportion.doubleValue());
                rowNum++;
            }
            //得到输出流,将文件写出到本地,根据响应体,响应回到浏览器
            OutputStream outputStream = response.getOutputStream();

            //设置两个响应头
            response.setContentType("application/vnd.ms-excel");//代表的是Excel文件类型
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");//指定以附件形式进行下载
            //将文件对象通过输出流写到本地
            sheets.write(outputStream);

            outputStream.flush();
            outputStream.close();
            sheets.close();


        } catch (Exception e) {
            e.printStackTrace();
           // return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL,null);
        }
    }
}
