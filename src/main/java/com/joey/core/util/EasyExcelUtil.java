package com.joey.core.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.util.StringUtils;
import com.joey.core.dto.ColumnIndexDTO;
import com.joey.core.dto.PatientLiverDataDTO;
import com.joey.core.vo.PatientLiverDataAnalysisResultVo;
import org.apache.poi.ss.formula.functions.T;
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

import static cn.hutool.poi.excel.cell.CellUtil.getCellValue;

/**
 * @Author huangqiang
 * @Date 2024/5/16
 */
public class EasyExcelUtil {

    /**
     * 斜杠符号.
     */
    private static final String SLASH = "/";

    /**
     * 数据转换.
     *
     * @param filePathStr 文件路径
     */
    private static void dataConvert(String filePathStr, ColumnIndexDTO columnIndexDTO) {
        if (StringUtils.isNotBlank(filePathStr)) {
            Path filePath = Paths.get(filePathStr);
            try (InputStream inputStream = Files.newInputStream(filePath);
                 Workbook workbook = new XSSFWorkbook(inputStream);
                 FileOutputStream outputStream = new FileOutputStream(filePathStr)) {
                // 获取第一个工作表
                Sheet sheet = workbook.getSheetAt(0);
                sheet.forEach(row -> {
                    // 获取当前行数据
                    PatientLiverDataDTO patientLiverDataDTO  = getRowData(row,columnIndexDTO);
                    // 获取计算结果
                    PatientLiverDataAnalysisResultVo resultVo = MedCalculatorUtil.calculate(patientLiverDataDTO);
                });
                workbook.write(outputStream);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    /**
     * 获取当前行数据
     * @param row
     * @param columnIndexDTO
     * @return
     */
    private static PatientLiverDataDTO getRowData(Row row, ColumnIndexDTO columnIndexDTO) {

        PatientLiverDataDTO patientLiverDataDTO= PatientLiverDataDTO.builder()
                .totalBilirubin(getCellValue(row,columnIndexDTO.getTotalBilirubin(),Float.class))
                .albumin(getCellValue(row,columnIndexDTO.getTotalBilirubin(),Float.class))
                .inr(getCellValue(row,columnIndexDTO.getTotalBilirubin(),Float.class))
                .ascites(getCellValue(row,columnIndexDTO.getTotalBilirubin(),String.class))
                .hepaticEncephalopathy(getCellValue(row,columnIndexDTO.getTotalBilirubin(),String.class))
                .serumBilirubin(getCellValue(row,columnIndexDTO.getTotalBilirubin(),Float.class))
                .na(getCellValue(row,columnIndexDTO.getTotalBilirubin(),Float.class))
                .build();

        return patientLiverDataDTO;
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

    public static void main(String[] args) {
        String filePath = "/Users/huangqiang/data/myTest.xlsx";
        dataConvert(filePath,null);
    }
}
