package com.joey.core.util;

import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.StrUtil;
import com.joey.core.dto.PatientLiverDataDTO;
import com.joey.core.vo.PatientLiverDataAnalysisResultVo;

import java.util.Objects;

/**
 * 医学计算工具类
 * @Author huangqiang
 * @Date 2024/5/17
 */
public class MedCalculatorUtil {


    public static PatientLiverDataAnalysisResultVo calculate(PatientLiverDataDTO patientLiverDataDTO){

        // 计算 Child-Pugh评分
        int childScore =  ChildCalculatorUtil.calculate(patientLiverDataDTO);

        // 计算MELD评分
        int MELDScore = MELDCalculatorUtil.calculate(patientLiverDataDTO);

        PatientLiverDataAnalysisResultVo resultVo = PatientLiverDataAnalysisResultVo.builder()
                .build();

        return resultVo;
    }


}
