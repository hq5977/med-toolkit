package com.joey.core.util;

import com.joey.core.dto.PatientLiverDataDTO;

/**
 * @Author huangqiang
 * @Date 2024/5/20
 */
public class Test {

    public static void main(String[] args) {
        PatientLiverDataDTO patientLiverDataDTO = PatientLiverDataDTO.builder()
                .totalBilirubin(49.2)
                .serumBilirubin(58.0)
                .inr(1.57)
                .na(134.0)
                .build();
        int MELDScore = MELDCalculatorUtil.calculate(patientLiverDataDTO);
        System.out.println(MELDScore);
    }
}
