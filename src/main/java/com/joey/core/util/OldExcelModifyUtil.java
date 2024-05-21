package com.joey.core.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.joey.core.dto.ColumnIndexDTO;
import com.joey.core.dto.PatientLiverDataDTO;
import com.joey.core.vo.PatientLiverDataAnalysisResultVo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 修改Excel中的数据
 * @Author huangqiang
 * @Date 2024/5/16
 */
public class OldExcelModifyUtil {

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
        if (StrUtil.isNotBlank(filePathStr)) {
            try  {
                FileInputStream inputStream = new FileInputStream(filePathStr);
                Workbook workbook = new XSSFWorkbook(inputStream);
                // 获取第一个工作表
                Sheet sheet = workbook.getSheetAt(0);
                sheet.forEach(row->{
                    int rowNum = row.getRowNum();
                    if (0 != rowNum){
                        // 获取当前行数据
                        PatientLiverDataDTO patientLiverDataDTO  = getRowData(row,columnIndexDTO);
                        // 获取计算结果
                        PatientLiverDataAnalysisResultVo resultVo = MedCalculatorUtil.calculate(patientLiverDataDTO);
                        System.out.println("rowNum:"+rowNum+";入参:"+ JSONUtil.toJsonStr(patientLiverDataDTO)+"；出参："+ JSONUtil.toJsonStr(resultVo));
                        if (StrUtil.isNotEmpty(resultVo.getChildResultDesc())){
                            // 获取单元格
                            Cell cell = row.getCell(44);
                            if (cell == null) {
                                // 如果没有单元格，则创建它
                                cell = row.createCell(44);
                            }
                            // 设置单元格的新内容
                            cell.setCellValue(resultVo.getChildResultDesc());
                        }
                        if (StrUtil.isNotEmpty(resultVo.getChildResultLevel())){
                            // 获取单元格
                            Cell cell = row.getCell(45);
                            if (cell == null) {
                                // 如果没有单元格，则创建它
                                cell = row.createCell(45);
                            }
                            // 设置单元格的新内容
                            cell.setCellValue(resultVo.getChildResultLevel());
                        }
                        if (-1 != resultVo.getMELDScore()){
                            // 获取单元格
                            Cell cell = row.getCell(46);
                            if (cell == null) {
                                // 如果没有单元格，则创建它
                                cell = row.createCell(46);
                            }
                            // 设置单元格的新内容
                            cell.setCellValue(resultVo.getMELDScore());
                            // 设置单元格的新内容
                        }
                    }
                });
                // 关闭输入流
                inputStream.close();

                // 写入修改到新的Excel文件
                FileOutputStream outputStream = new FileOutputStream(filePathStr);
                workbook.write(outputStream);
                workbook.close();
                outputStream.close();
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
                .no(getCellValue(row,columnIndexDTO.getNo(),Double.class))
                .totalBilirubin(getCellValue(row,columnIndexDTO.getTotalBilirubin(),Double.class))
                .albumin(getCellValue(row,columnIndexDTO.getAlbumin(),Double.class))
                .inr(getCellValue(row,columnIndexDTO.getInr(),Double.class))
                .ascites(getCellValue(row,columnIndexDTO.getAscites(),String.class))
                .hepaticEncephalopathy(getCellValue(row,columnIndexDTO.getHepaticEncephalopathy(),String.class))
                .serumBilirubin(getCellValue(row,columnIndexDTO.getSerumBilirubin(),Double.class))
                .na(getCellValue(row,columnIndexDTO.getNa(),Double.class))
                .child(getCellValue(row,columnIndexDTO.getChild(),String.class))
                .meld(getCellValue(row,columnIndexDTO.getMeld(),Double.class))
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
        if (cell == null){
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
                if (StrUtil.isNotEmpty(stringValue)) {
                    result = (T) StrUtil.trim(stringValue);
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
        String filePath = "/Users/huangqiang/data/data7.xlsx";
        ColumnIndexDTO columnIndexDTO = ColumnIndexDTO.builder()
                .no(0)
                .totalBilirubin(34)
                .albumin(37)
                .inr(40)
                .ascites(22)
                .hepaticEncephalopathy(21)
                .serumBilirubin(38)
                .na(39)
                .child(42)
                .meld(43)
                .build();
        dataConvert(filePath,columnIndexDTO);
    }
}
