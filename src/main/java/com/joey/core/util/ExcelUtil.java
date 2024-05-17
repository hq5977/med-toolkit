package com.joey.core.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.util.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author huangqiang
 * @Date 2024/5/16
 */
public class ExcelUtil {

    /**
     * 斜杠符号.
     */
    private static final String SLASH = "/";

    /**
     * 数据转换.
     *
     * @param filePathStr 文件路径
     */
    private static void  dataConvert(String filePathStr) {
        if (StringUtils.isNotBlank(filePathStr)) {
            Path filePath = Paths.get(filePathStr);
            try (InputStream inputStream = Files.newInputStream(filePath);
                 Workbook workbook = new XSSFWorkbook(inputStream);
                 FileOutputStream outputStream = new FileOutputStream(filePathStr)) {
                // 获取第一个工作表
                Sheet sheet = workbook.getSheetAt(0);
                Set<String> ascites = new HashSet<>();
                Set<String> HE = new HashSet<>();
                sheet.forEach(row -> {
                    String ascitesValue = getCellValue(row, 22, String.class);
                    if (StrUtil.isNotEmpty(ascitesValue)){
                        ascites.add(ascitesValue);
                    }
                    String HEValue = getCellValue(row, 23, String.class);
                    if (StrUtil.isNotEmpty(HEValue)){
                        HE.add(HEValue);
                    }

                });
                System.out.println("ascites:"+ascites);
                System.out.println("HE:"+HE);
                workbook.write(outputStream);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "/Users/huangqiang/data/data1.xlsx";
        dataConvert(filePath);
    }

    /**
     * 获取当前行指定列下标的数据
     * @param row
     * @param columnIndex
     * @param clazz
     * @return
     * @param <T>
     */
    private static <T> T getCellValue(Row row, Integer columnIndex, Class<T> clazz) {
        if (null == columnIndex){
            return null;
        }
        Cell cell = row.getCell(columnIndex);
        if (null==cell){
            return null;
        }
        return getValue(cell, clazz);
    }

    /**
     * 获取cell的值
     *
     * @param cell
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> T getValue(Cell cell, Class<T> clazz) {
        CellType cellType = cell.getCellType();
        T result = null;
        switch (cellType) {
            case STRING:
                String stringValue = cell.getStringCellValue();
                if (StrUtil.isEmpty(stringValue)) {
                    result = (T) stringValue;
                }
                break;
            case NUMERIC:
                Double numericCellValue = cell.getNumericCellValue();
                result = (T) numericCellValue;
                break;
            default:
                break;
        }
        return result;
    }
}
