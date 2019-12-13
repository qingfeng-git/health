package com.itheima.jobs;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
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

public class PoiDemo1 {
    public static void main(String[] args) throws IOException {
        //获取工作薄
        XSSFWorkbook sheets = new XSSFWorkbook("D:\\桌面文件\\信息表.xlsx");
        //获取工作表
        XSSFSheet sheetAt = sheets.getSheetAt(0);
        //获取表中的最后的行号
        int lastRowNum = sheetAt.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            //获取每一行的行对象
            XSSFRow row = sheetAt.getRow(i);
            //获取每一行最后一个单元格
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j <lastCellNum ; j++) {
                //根据单元格来获取对应的值
                String value = row.getCell(j).getStringCellValue();
                System.out.println(value);
            }

        }
        sheets.close();
    }

}
