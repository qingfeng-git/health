package com.itheima.jobs;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

/**
 * @PackageName: com.itheima.jobs
 * @ClassName: PoiDemo
 * @Author: QingFeng
 * @Date: 2019-12-4 10:33
 * @Description: //TODO
 */

public class PoiDemo {
    public static void main(String[] args) throws IOException {
        //获取工作薄
        XSSFWorkbook sheets = new XSSFWorkbook("D:\\桌面文件\\信息表.xlsx");
        //获取工作表
        XSSFSheet sheetAt = sheets.getSheetAt(0);
        //遍历工作表得到行
        for (Row cells : sheetAt) {
            //遍历行,得到单元格
            for (Cell cell : cells) {
                //获取单元格中的内容
                String value = cell.getStringCellValue();
                System.out.println(value);
            }
        }
        sheets.close();
    }   

}
