package com.itheima.jobs;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @PackageName: com.itheima.jobs
 * @ClassName: PoiDemo
 * @Author: QingFeng
 * @Date: 2019-12-4 10:33
 * @Description: //TODO
 */

public class PoiDemo2 {
    public static void main(String[] args) throws IOException {
        //创建一个工作薄
        XSSFWorkbook sheets = new XSSFWorkbook();
        //创建一个工作表,并指定表名
        XSSFSheet rows = sheets.createSheet("清风");
        //创建行
        XSSFRow row = rows.createRow(0);
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("学号");
        row.createCell(2).setCellValue("姓名");
        //创建行
        XSSFRow row1 = rows.createRow(1);
        row1.createCell(0).setCellValue("heima01");
        row1.createCell(1).setCellValue("heima01");
        row1.createCell(2).setCellValue("清风");
        //创建行
        XSSFRow row2 = rows.createRow(2);
        row2.createCell(0).setCellValue("heima02");
        row2.createCell(1).setCellValue("heima02");
        row2.createCell(2).setCellValue("qingfeng");

        //将内存中的文件写到磁盘上
        FileOutputStream stream = new FileOutputStream("D:\\桌面文件\\信息.xlsx");
        sheets.write(stream);
        stream.flush();
        stream.close();
        sheets.close();


    }

}
