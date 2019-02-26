package com.zfoo.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/12/18
 */
@Ignore
public class ExcelTest {

    @Test
    public void createExcelTest() {
        //第一步创建workbook
        HSSFWorkbook wb = new HSSFWorkbook();

        //第二步创建sheet
        HSSFSheet sheet = wb.createSheet("测试");

        //第三步创建行row:添加表头0行
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = wb.createCellStyle();
        //style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //居中


        //第四步创建单元格
        HSSFCell cell = row.createCell(0); //第一个单元格
        cell.setCellValue("姓名");
        cell.setCellStyle(style);

        cell = row.createCell(1);         //第二个单元格
        cell.setCellValue("年龄");
        cell.setCellStyle(style);


        //第五步插入数据
        for (int i = 0; i < 5; i++) {
            //创建行
            row = sheet.createRow(i + 1);
            //创建单元格并且添加数据
            row.createCell(0).setCellValue("aa" + i);
            row.createCell(1).setCellValue(i);
        }

        //第六步将生成excel文件保存到指定路径下
        try {
            FileOutputStream target = new FileOutputStream("target.xls");
            wb.write(target);
            target.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Excel文件生成成功...");
    }

    @Test
    public void readExcelTest() throws IOException {
        Workbook wb = WorkbookFactory.create(new File("target.xls"));

        // 只读取第一个工作簿
        Sheet sheet = wb.getSheetAt(0);

        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row row = iterator.next();

            StringBuilder builder = new StringBuilder();
            builder.append(row.getCell(0));
            builder.append(" - ");
            builder.append(row.getCell(1));

            System.out.println(builder.toString());
        }

        System.out.println("Excel文件读取成功");
    }

    /*
     默认poi返回的为DOUBLE，某些单元格是整型，需要先转为Long判断下，再返回string
     */
    public String cellToString(Cell cell) {
        // 返回布尔类型的值
        if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.NUMERIC) {
            // 返回数值类型的值
            Object inputValue;
            Long longVal = Math.round(cell.getNumericCellValue());
            Double doubleVal = cell.getNumericCellValue();
            // 判断是否含有小数位.0
            if (Double.parseDouble(longVal + ".0") == doubleVal) {
                inputValue = longVal;
            } else {
                inputValue = doubleVal;
            }

            //格式化为四位小数，按自己需求选择；
            DecimalFormat df = new DecimalFormat("#.####");
            //返回String类型
            return df.format(inputValue);
        } else {
            // 返回字符串类型的值
            return String.valueOf(cell.getStringCellValue());
        }
    }

}
