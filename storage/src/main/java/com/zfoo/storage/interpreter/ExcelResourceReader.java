package com.zfoo.storage.interpreter;

import com.zfoo.storage.StorageContext;
import com.zfoo.storage.model.anno.Id;
import com.zfoo.util.ReflectionUtils;
import com.zfoo.util.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.convert.TypeDescriptor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.14 10:55
 */
public class ExcelResourceReader implements IResourceReader {

    private static final String ROW_SERVER = "SERVER";
    private static final String ROW_END = "END";

    private static final TypeDescriptor sourceType = TypeDescriptor.valueOf(String.class);

    @Override
    public <T> List<T> read(InputStream inputStream, Class<T> clazz) {
        Workbook wb = createWorkbook(inputStream, clazz);
        List<Sheet> numberOfSheets = numberOfValidSheets(wb, clazz);
        ArrayList<T> result = new ArrayList<>();
        int length = numberOfSheets.size();

        for (int i = 0; i < length; i++) {
            Sheet sheet = numberOfSheets.get(i);
            Collection<FieldInfo> fieldInfos = getFieldInfos(sheet, clazz);

            Iterator<Row> iterator = sheet.iterator();
            // 行数定位到ROW_SERVER
            while (iterator.hasNext()) {
                Row row = iterator.next();
                Cell firstCell = row.getCell(0);
                if (firstCell != null && getCellContent(firstCell).equals(ROW_SERVER)) {
                    break;
                }
            }

            // 从ROW_SERVER这行开始读取数据
            while (iterator.hasNext()) {
                Row row = iterator.next();
                T instance = ReflectionUtils.newInstance(clazz);
                Iterator<FieldInfo> fieldIterator = fieldInfos.iterator();
                while (fieldIterator.hasNext()) {
                    FieldInfo info = fieldIterator.next();
                    Cell cell = row.getCell(info.index);
                    if (cell != null) {
                        String content = getCellContent(cell);
                        if (!StringUtils.isEmpty(content)) {
                            inject(instance, info.field, content);
                        }
                    }

                    // 如果读的是id列的单元格，则判断当前id是否为空
                    if (info.field.isAnnotationPresent(Id.class)) {
                        if (cell == null || StringUtils.isEmpty(getCellContent(cell))) {
                            throw new RuntimeException("静态资源存在id未配置的项");
                        }
                    }
                }

                result.add(instance);

                // 如果某一行的第一个单元格为ROW_END，则结束读取
                Cell firstCell = row.getCell(0);
                if (firstCell != null) {
                    String firstCellContent = getCellContent(firstCell);
                    if (firstCellContent != null && firstCellContent.equals(ROW_END)) {
                        break;
                    }
                }
            }
        }
        return result;
    }

    private void inject(Object instance, Field field, String content) {
        TypeDescriptor targetType = new TypeDescriptor(field);
        Object value = StorageContext.getConversionService().convert(content, sourceType, targetType);
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, instance, value);
    }


    //这个方法有问题，当配置表配的服务器字段，但是代码里没有写这个字段是不会报错的
    private Collection<FieldInfo> getFieldInfos(Sheet sheet, Class<?> clazz) {
        Row fieldRow = getFieldRow(sheet);
        if (fieldRow == null) {
            throw new RuntimeException("无法获取资源[" + clazz.getSimpleName() + "]的Excel文件的属性控制列");
        } else {
            List<FieldInfo> result = new ArrayList<>();
            for (int i = 1; i < fieldRow.getLastCellNum(); i++) {
                Cell cell = fieldRow.getCell(i);
                if (cell != null) {
                    String name = getCellContent(cell);
                    if (!StringUtils.isEmpty(name)) {
                        try {
                            Field field = clazz.getDeclaredField(name);
                            FieldInfo info = new FieldInfo(i, field);
                            result.add(info);
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                            throw new RuntimeException("资源类:[" + clazz + "]的声明属性:[" + name + "]无法获取");
                        }
                    }
                }
            }
            return result;
        }
    }

    // 获取配置表的有效列名称
    private Row getFieldRow(Sheet sheet) {
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            Cell cell = row.getCell(0);
            if (cell != null) {
                String content = getCellContent(cell);
                if (content != null && content.equals(ROW_SERVER)) {
                    return row;
                }
            }
        }
        return null;
    }

    // 如果单元格的格式不是String的，则设置成String的
    private String getCellContent(Cell cell) {
        if (cell.getCellType() != CellType.STRING) {
            cell.setCellType(CellType.STRING);
        }
        return cell.getStringCellValue();
    }


    /**
     * 创建工作簿
     *
     * @param input
     * @param clazz
     * @return
     */
    private Workbook createWorkbook(InputStream input, Class<?> clazz) {
        try {
            return WorkbookFactory.create(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("静态资源[" + clazz.getSimpleName() + "]异常，无法读取文件");
        }
    }

    /**
     * 获取有效的表单，表单的第一行第一列的第一个单元格等于当前的类名（区分大小写），则匹配成功
     *
     * @param wb
     * @param clazz
     * @return
     */
    private List<Sheet> numberOfValidSheets(Workbook wb, Class<?> clazz) {
        List<Sheet> result = new ArrayList<>();
        String clazzName = clazz.getSimpleName();
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            if (sheet.getLastRowNum() <= 0) {
                continue;
            }

            Row row = sheet.getRow(0);

            if (row.getLastCellNum() <= 0) {
                continue;
            }

            Cell cell = row.getCell(0);

            if (cell == null) {
                continue;
            }

            // 如果表单的第一行第一列的第一个单元格等于当前的类名（区分大小写），则匹配成功
            if (clazzName.equals(getCellContent(cell))) {
                result.add(sheet);
            }
        }

        return result;
    }

    private static class FieldInfo {
        public final int index;
        public final Field field;

        public FieldInfo(int index, Field field) {
            this.index = index;
            this.field = field;
        }
    }
}
