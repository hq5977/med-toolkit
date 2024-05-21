package com.joey.core.util;

import com.joey.core.dto.PatientLiverDataDTO;

/**
 * 测试单列的计算结果
 * @Author huangqiang
 * @Date 2024/5/20
 */
public class Test {

    public static void main(String[] args) {
        PatientLiverDataDTO patientLiverDataDTO = PatientLiverDataDTO.builder()
                .totalBilirubin(15.8)
                .serumBilirubin(60.0)
                .inr(1.1)
                .na(145.6)
                .build();
        int MELDScore = MELDCalculatorUtil.calculate(patientLiverDataDTO);
        System.out.println(MELDScore);
    }
}
