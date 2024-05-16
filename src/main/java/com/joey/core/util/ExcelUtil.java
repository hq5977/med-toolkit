package com.joey.core.util;

import com.alibaba.excel.util.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
                sheet.forEach(row -> {
                    // 获取当前行的最后一列索引
                    int lastColumnNum = row.getLastCellNum() - 1;
                    for (Cell cell : row) {
                        if (cell == null){

                        }

                    }
                    row.forEach(cell -> {
                        if (cell != null) {
                            CellType cellType = cell.getCellType();
                            switch (cellType) {
                                case STRING:
                                    String stringValue = cell.getStringCellValue();
                                    if (StringUtils.isBlank(stringValue)) {
                                        cell.setCellValue(SLASH);
                                    }
                                    break;
                                case BLANK:
                                    int columnIndex = cell.getColumnIndex();
                                    if (lastColumnNum != columnIndex) {
                                        cell.setCellValue(SLASH);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                });
                workbook.write(outputStream);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "/Users/huangqiang/data/myTest.xlsx";
        dataConvert(filePath);
    }
}
