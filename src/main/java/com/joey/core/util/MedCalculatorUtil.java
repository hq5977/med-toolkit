package com.joey.core.util;

import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
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

        // 获取MELD评分描述
        String meldResultDesc = getMELDResultDesc(MELDScore);

        PatientLiverDataAnalysisResultVo resultVo = PatientLiverDataAnalysisResultVo.builder()
                .childResultDesc(childResultDesc)
                .childResultLevel(childResultLevel)
                .MELDScore(MELDScore)
                .meldResultDesc(meldResultDesc)
                .build();
        // 同原结果进行比较
        compareToOldResult(patientLiverDataDTO,resultVo);

        return resultVo;
    }

    /**
     * 同原结果进行比较
     * @param patientLiverDataDTO
     * @param resultVo
     */
    private static void compareToOldResult(PatientLiverDataDTO patientLiverDataDTO, PatientLiverDataAnalysisResultVo resultVo) {

        if (StrUtil.isNotEmpty(patientLiverDataDTO.getChild())){
            String childResultDesc = resultVo.getChildResultDesc();
            String oldChildDesc = patientLiverDataDTO.getChild();
            if (!StrUtil.equals(childResultDesc,oldChildDesc)){
                System.err.println("Child与原结果不一致:"+ JSONUtil.toJsonStr(patientLiverDataDTO)+"；原结果："+oldChildDesc +"；新结果："+childResultDesc);
            }
        }

        if (null != patientLiverDataDTO.getMeld()){
            int score = resultVo.getMELDScore();
            int oldScore = (int) Math.round(patientLiverDataDTO.getMeld());
            if (score != oldScore){
                System.err.println("MELD与原结果不一致:"+ JSONUtil.toJsonStr(patientLiverDataDTO)+"；原结果："+oldScore+"；新结果："+score);
            }
        }
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
        String level = getChildResultLevel(childScore);
        return StrUtil.format("{}分，{}",childScore, level);
    }


    /**
     * 获取MELD评分结果描述
     * @param score
     * @return
     */
    public static String getMELDResultDesc(double score){
        String rate = "";
        if (score <= 9) {
            rate = "1.9%";
        } else if (score <= 19) {
            rate = "6.0%";
        } else if (score <= 29) {
            rate = "19.6%";
        } else if (score <= 39) {
            rate = "52.6%";
        } else {
            rate = "71.3%";
        }
        return StrUtil.format("{}分，{}",score , rate);
    }


}
