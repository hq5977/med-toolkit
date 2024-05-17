package com.joey.core.util;

import cn.hutool.core.util.StrUtil;
import com.joey.core.dto.PatientLiverDataDTO;

/**
 * MELD 计算工具类
 * @Author huangqiang
 * @Date 2024/5/17
 */
public class MELDCalculatorUtil {


    /**
     * 计算MELD评分
     * @param dataDTO
     * @return
     */
    public static int calculate(PatientLiverDataDTO dataDTO) {
        // 血清肌酐
        Float totalBilirubin = dataDTO.getTotalBilirubin();
        // 血清胆红素
        Float serumBilirubin = dataDTO.getSerumBilirubin();
        // 国际标准化比率(INR)
        Float inr = dataDTO.getInr();
        // 血钠
        Float na = dataDTO.getNa();
        if (null == totalBilirubin || null == serumBilirubin || null== inr|| null == na){
            return -1;
        }
        return 0;
    }
}
