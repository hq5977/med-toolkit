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
        // 获取Child-Pugh评分 结果描述
        String childResultDesc = getChildResultDesc(childScore);

        // 获取Child-Pugh评分 的分级
        String childResultLevel = getChildResultLevel(childScore);

        // 计算MELD评分
        int MELDScore = MELDCalculatorUtil.calculate(patientLiverDataDTO);

        PatientLiverDataAnalysisResultVo resultVo = PatientLiverDataAnalysisResultVo.builder()
                .childResultDesc(childResultDesc)
                .childResultLevel(childResultLevel)
                .MELDScore(MELDScore)
                .build();

        return resultVo;
    }

    /**
     * 获取Child-Pugh评分 的分级
     * @param childScore
     * @return
     */
    private static String getChildResultLevel(int childScore) {
        if (childScore == -1){
            return null;
        }
        if (childScore == 5 || childScore == 6){
            return "A级";
        }else if (childScore == 7 || childScore == 8 || childScore == 9){
            return "B级";
        }else if (childScore >=10){
            return  "C级";
        }
        return null;
    }

    /**
     * 获取Child-Pugh评分 结果描述
     * @param childScore
     * @return
     */
    private static String getChildResultDesc(int childScore) {
        if (childScore == -1){
            return null;
        }
        String level = "";
        if (childScore == 5 || childScore == 6){
            level = "A级";
        }else if (childScore == 7 || childScore == 8 || childScore == 9){
            level = "B级";
        }else if (childScore >=10){
            level = "C级";
        }else {
            return null;
        }
        return StrUtil.concat(false,String.valueOf(childScore),"分：",level);
    }


}
